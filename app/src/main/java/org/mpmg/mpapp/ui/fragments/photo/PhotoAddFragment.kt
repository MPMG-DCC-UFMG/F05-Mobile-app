package org.mpmg.mpapp.ui.fragments.photo

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_add_photo.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.viewmodels.CollectViewModel
import org.mpmg.mpapp.ui.viewmodels.PhotoViewModel

class PhotoAddFragment : Fragment() {

    private val TAG = PhotoAddFragment::class.java.name

    private val RC_IMAGE_CAPTURE = 602

    private val photoViewModel: PhotoViewModel by sharedViewModel()
    private val collectViewModel: CollectViewModel by sharedViewModel()

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
        photoViewModel.getBitmap().observe(viewLifecycleOwner, Observer { bitmap ->
            bitmap ?: return@Observer

            imageView_addPhotoFragment_thumbnail.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView_addPhotoFragment_thumbnail.setImageBitmap(bitmap)
        })
    }

    private fun dispatchTakePictureIntent() {
        context?.let {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(it.packageManager)?.also {
                    startActivityForResult(takePictureIntent, RC_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun addPhotoToCollect() {
        val photo = photoViewModel.getPhoto().value ?: return
        collectViewModel.addPhoto(photo)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            photoViewModel.setBitmap(imageBitmap)
        }
    }
}