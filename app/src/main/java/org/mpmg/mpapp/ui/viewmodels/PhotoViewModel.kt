package org.mpmg.mpapp.ui.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mpmg.mpapp.domain.models.Photo
import java.io.File
import java.io.IOException

class PhotoViewModel : ViewModel() {

    private var mCurrentPhoto = MutableLiveData<Photo>()
    private var mCurrentBitmap = MutableLiveData<Bitmap>()

    fun setPhoto(photo: Photo?) {
        mCurrentPhoto.value = if (photo == null) {
            mCurrentBitmap.value = null
            Photo()
        } else {
            photo
        }
    }

    fun setBitmap(bitmap: Bitmap) {
        mCurrentBitmap.value = bitmap
    }

    fun getPhoto(): LiveData<Photo> = mCurrentPhoto

    fun getBitmap(): LiveData<Bitmap> = mCurrentBitmap

    @Throws(IOException::class)
    fun createPhotoFile(context: Context): File? {
        mCurrentPhoto.value?.let {
            val storageDir: File =
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: return null
            return File.createTempFile("MP_${it.id}", ".jpg", storageDir).apply {
                it.filename = absolutePath
                mCurrentPhoto.value = it
            }
        } ?: return null
    }
}