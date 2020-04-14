package org.mpmg.mpapp.ui.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.location.Location
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

    fun getPhoto(): LiveData<Photo> = mCurrentPhoto

    fun setPhotoType(type: String) {
        mCurrentPhoto.value?.let {
            it.type = type
            mCurrentPhoto.value = it
        }
    }

    fun updatePhotoLocation(location: Location) {
        mCurrentPhoto.value?.let {
            it.latitude = location.latitude
            it.longitude = location.longitude
        }
    }

    @Throws(IOException::class)
    fun createPhotoFile(context: Context): File? {
        mCurrentPhoto.value?.let {
            val filePath = it.filepath
            return if (filePath != null) {
                File(filePath)
            } else {
                val storageDir: File =
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: return null
                File.createTempFile("MP_${it.id}", ".jpg", storageDir).apply {
                    it.filepath = absolutePath
                    mCurrentPhoto.value = it
                }
            }
        } ?: return null
    }
}