package org.mpmg.mpapp.ui.screens.photo

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_photo.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.BuildConfig
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.FragmentAddPhotoBinding
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.ui.dialogs.SelectorDialog
import org.mpmg.mpapp.ui.viewmodels.CollectViewModel
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel
import org.mpmg.mpapp.ui.viewmodels.PhotoViewModel
import java.io.File
import java.io.IOException

class PhotoAddFragment : Fragment() {

    private val TAG = PhotoAddFragment::class.java.name

    private val RC_IMAGE_CAPTURE = 602

    private val photoViewModel: PhotoViewModel by sharedViewModel()
    private val collectViewModel: CollectViewModel by sharedViewModel()
    private val locationViewModel: LocationViewModel by sharedViewModel()

    private var navigationController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navigationController = activity?.findNavController(R.id.nav_host_fragment)

        val binding: FragmentAddPhotoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_photo, container, false)
        binding.photoViewModel = photoViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        imageView_addPhotoFragment_thumbnail.setOnClickListener {
            dispatchTakePictureIntent()
        }

        materialButton_addPhotoFragment_confirmPhoto.setOnClickListener {
            addPhotoToCollect()
        }

        materialButton_addPhotoFragment_deletePhoto.setOnClickListener {
            deletePhoto()
        }
    }

    private fun setupObservers() {
        photoViewModel.getPhoto().observe(viewLifecycleOwner, Observer { photo ->
            photo ?: return@Observer

            handlePhotoType(photo)
            handlePhotoThumbnail(photo)
        })

        locationViewModel.getCurrentLocationLiveData()
            .observe(viewLifecycleOwner, Observer { location ->
                location ?: return@Observer
                photoViewModel.updatePhotoLocation(location)
            })
    }

    private fun handlePhotoThumbnail(photo: Photo) {
        photo.filepath?.let {
            Glide.with(this).load(it).into(imageView_addPhotoFragment_thumbnail)
        }
    }

    private fun handlePhotoType(photo: Photo) {
        photo.type?.let {
            textView_addPhotoFragment_photoType.text = it
        } ?: run {
            launchTypeSelectDialog()
        }
    }

    private fun launchTypeSelectDialog() {
        val optionsArray = photoViewModel.typePhotos.map { it.name }.toTypedArray()
        val builder = SelectorDialog.Builder(childFragmentManager)
        builder.withTitle(getString(R.string.dialog_type_photo_title))
            .withOptions(optionsArray.toList())
            .withSelectionMode(SelectorDialog.SelectionMode.SINGLE)
            .withOnCloseClickListener {
                navigateBack()
            }.withSelectedOptionListener {
                val selectedOption = optionsArray.get(it.first())
                photoViewModel.setPhotoType(selectedOption)
            }
            .show()
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                val photoFile: File? = try {
                    photoViewModel.createPhotoFile(requireContext())
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

    private fun deletePhoto() {
        val photo = photoViewModel.getPhoto().value ?: return
        collectViewModel.deletePhoto(photo)
        navigateBack()
    }

    private fun addPhotoToCollect() {
        val photo = photoViewModel.getPhoto().value ?: return
        collectViewModel.addPhoto(photo)
        navigateBack()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_IMAGE_CAPTURE) {
            when (resultCode) {
                RESULT_OK -> {
                    showSnackbar(getString(R.string.snackbar_photo_add_success))
                }
                RESULT_CANCELED -> {
                    showSnackbar(getString(R.string.snackbar_photo_add_canceled))
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            constraintLayout_addPhotoFragment_mainContainer,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun navigateBack() {
        navigationController?.navigate(R.id.action_photoAddFragment_pop)
    }
}