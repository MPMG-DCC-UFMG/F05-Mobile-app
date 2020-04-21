package org.mpmg.mpapp.ui.fragments.photo

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_photo.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.models.Photo
import org.mpmg.mpapp.ui.viewmodels.CollectViewModel
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel
import org.mpmg.mpapp.ui.viewmodels.PhotoViewModel
import java.io.File
import java.io.IOException
import java.net.URI

class PhotoAddFragment : Fragment() {

    private val TAG = PhotoAddFragment::class.java.name

    private val RC_IMAGE_CAPTURE = 602

    private val photoViewModel: PhotoViewModel by sharedViewModel()
    private val collectViewModel: CollectViewModel by sharedViewModel()
    private val locationViewModel: LocationViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_photo, container, false)
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

        materialButton_collectMainFragment_confirmPhoto.setOnClickListener {
            addPhotoToCollect()
            activity?.onBackPressed()
        }
    }

    private fun setupObservers() {
        photoViewModel.getPhoto().observe(viewLifecycleOwner, Observer { photo ->
            photo ?: return@Observer

            handlePhotoType(photo)
            handlePhotoTumbnail(photo)
        })

        locationViewModel.getCurrentLocationLiveData()
            .observe(viewLifecycleOwner, Observer { location ->
                location ?: return@Observer

            })
    }

    private fun handlePhotoTumbnail(photo: Photo) {
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
        activity?.let {
            val optionsArray = arrayOf("Outros")
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.dialog_type_photo_title))
                .setItems(optionsArray) { _, which ->
                    photoViewModel.setPhotoType(optionsArray[which])
                }

            builder.create().show()
        }
    }

    private fun dispatchTakePictureIntent() {
        context?.let { currContext ->
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(currContext.packageManager)?.also {
                    val photoFile: File? = try {
                        photoViewModel.createPhotoFile(currContext)
                    } catch (ex: IOException) {
                        Log.d(TAG, "An error occur when creating photo file")
                        null
                    }

                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            currContext,
                            "org.mpmg.mpapp.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, RC_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    private fun addPhotoToCollect() {
        val photo = photoViewModel.getPhoto().value ?: return
        collectViewModel.addPhoto(photo)
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
}