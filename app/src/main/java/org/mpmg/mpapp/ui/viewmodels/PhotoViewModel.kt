package org.mpmg.mpapp.ui.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.location.Location
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.database.models.TypePhoto
import org.mpmg.mpapp.domain.repositories.typephoto.ITypePhotoRepository
import java.io.File
import java.io.IOException

class PhotoViewModel(private val typePhotoRepository: ITypePhotoRepository) : ViewModel() {

    private var mCurrentPhoto = MutableLiveData<Photo>()
    private var mCurrentBitmap = MutableLiveData<Bitmap>()

    val typePhotos = mutableListOf<TypePhoto>()

    init {
        loadTypePhotos()
    }

    private fun loadTypePhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val types = typePhotoRepository.listAllTypePhotos()
            typePhotos.addAll(types)
        }
    }

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
                File(storageDir, "MP_${it.id}.jpg").apply {
                    it.filepath = absolutePath
                    mCurrentPhoto.value = it
                }
            }
        } ?: return null
    }
}