package org.mpmg.mpapp.ui.screens.photo.fragments

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named
import org.mpmg.mpapp.BuildConfig
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentAddPhotoBinding
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.database.models.TypePhoto
import org.mpmg.mpapp.ui.dialogs.SelectorDialog
import org.mpmg.mpapp.ui.dialogs.WarningDialog
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.collect.fragments.CollectMainFragment
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel
import org.mpmg.mpapp.ui.screens.photo.viewmodels.PhotoViewModel
import java.io.File
import java.io.IOException

class PhotoAddFragment : MVVMFragment<PhotoViewModel, FragmentAddPhotoBinding>() {

    private val TAG = PhotoAddFragment::class.java.name

    private val RC_IMAGE_CAPTURE = 602

    private val locationViewModel: LocationViewModel by sharedViewModel()

    private val session = getKoin().getOrCreateScope(CollectMainFragment.sessionName, named(TAG))

    private lateinit var navigationController: NavController
    private lateinit var typePhotos: List<TypePhoto>

    override val viewModel: PhotoViewModel by session.inject()
    override val layout: Int = R.layout.fragment_add_photo

    private val args: PhotoAddFragmentArgs by navArgs()

    override fun initBindings() {
        navigationController = requireActivity().findNavController(R.id.nav_host_fragment)
        binding.photoViewModel = viewModel
    }

    override fun initViews(savedInstanceState: Bundle?) {
        viewModel.initPhoto(args.photoId, args.collectId)
    }

    override fun initObservers() {

        observe(viewModel.typePhotos) {
            typePhotos = it
        }

        observe(locationViewModel.getCurrentLocationLiveData()) {
            viewModel.updatePhotoLocation(it)
        }

        observe(viewModel.currentPhoto) {
            handlePhotoThumbnail(it)
        }

        observe(viewModel.photoType) {
            handlePhotoType(it)
        }

    }

    override fun initListeners() {
        with(binding) {
            imageViewAddPhotoFragmentThumbnail.setOnClickListener {
                dispatchTakePictureIntent()
            }

            materialButtonAddPhotoFragmentConfirmPhoto.setOnClickListener {
                addPhotoToCollect()
            }

            materialButtonAddPhotoFragmentDeletePhoto.setOnClickListener {
                showDeleteDialog()
            }
        }
    }

    private fun handlePhotoThumbnail(photo: Photo) {
        Glide.with(this)
            .load(photo.filepath ?: R.drawable.ic_image_default)
            .into(binding.imageViewAddPhotoFragmentThumbnail)
    }

    private fun handlePhotoType(photoType: String?) {
        photoType?.let {
            binding.textViewAddPhotoFragmentPhotoType.text = it
        } ?: run {
            launchTypeSelectDialog()
        }
    }

    private fun launchTypeSelectDialog() {
        val optionsArray = typePhotos.map { it.name }.toTypedArray()
        val builder = SelectorDialog.Builder(childFragmentManager)
        builder.withTitle(getString(R.string.dialog_type_photo_title))
            .withOptions(optionsArray.toList())
            .withSelectionMode(SelectorDialog.SelectionMode.SINGLE)
            .withOnCloseClickListener {
                navigateBack()
            }.withSelectedOptionListener {
                val selectedOption = optionsArray.get(it.first())
                viewModel.setPhotoType(selectedOption)
            }
            .show()
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                val photoFile: File? = try {
                    viewModel.createPhotoFile(requireContext())
                } catch (ex: IOException) {
                    Log.d(TAG, "An error occur when creating photo file")
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "${BuildConfig.APPLICATION_ID}.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, RC_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun showDeleteDialog() {
        val builder = WarningDialog.Builder(childFragmentManager)
        builder.withTitle(getString(R.string.dialog_delete_photo))
            .withMessage(getString(R.string.dialog_delete_photo_message))
            .withOnPositiveClickListener {
                deletePhoto()
            }
            .show()
    }

    private fun deletePhoto() {
        viewModel.deletePhoto()
        navigateBack()
    }

    private fun addPhotoToCollect() {
        viewModel.addPhotoToCollect()
        navigateBack()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_IMAGE_CAPTURE) {
            when (resultCode) {
                RESULT_OK -> {
                    viewModel.compressPhoto()
                    showSnackbar(getString(R.string.snackbar_photo_add_success))
                }
                RESULT_CANCELED -> {
                    viewModel.updatePhotoPath(null)
                    showSnackbar(getString(R.string.snackbar_photo_add_canceled))
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        viewModel.launchSnackbar(message)
    }

    private fun navigateBack() {
        navigationController.navigate(R.id.action_photoAddFragment_pop)
    }
}