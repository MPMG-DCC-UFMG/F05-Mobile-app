package org.mpmg.mpapp.ui.screens.photo.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.fragment_add_photo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.core.events.SingleLiveEvent
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.database.models.TypePhoto
import org.mpmg.mpapp.domain.repositories.collect.CollectRepository
import org.mpmg.mpapp.domain.repositories.typephoto.TypePhotoRepository
import org.mpmg.mpapp.ui.screens.base.MVVMViewModel
import org.mpmg.mpapp.ui.screens.photo.models.PhotoActions
import org.mpmg.mpapp.ui.screens.photo.models.PhotoBundle
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class PhotoViewModel(
    private val collectRepository: CollectRepository,
    private val typePhotoRepository: TypePhotoRepository
) : MVVMViewModel() {

    private var _currentPhoto = MutableLiveData<Photo>()
    val currentPhoto: LiveData<Photo> = _currentPhoto

    private var mCurrentBitmap = MutableLiveData<Bitmap>()

    val typePhotos = MutableLiveData<List<TypePhoto>>()
    val photoType = MutableLiveData<String?>()

    private val COMPRESSION_QUALITY = 40
    private lateinit var collectId: String

    val photoBundle = SingleLiveEvent<PhotoBundle>()

    init {
        fetchPhotoTypes()
    }

    private fun fetchPhotoTypes() {
        viewModelScope.launch(Dispatchers.IO) {
            typePhotos.postValue(typePhotoRepository.listAllTypePhotos())
        }
    }

    fun initPhoto(photoId: String?, collectId: String) {
        this.collectId = collectId
        startPhoto(photoId)
    }

    private fun startPhoto(photoId: String?) {
        if (photoId == null) {
            newPhoto()
        } else {
            loadPhoto(photoId)
        }
    }

    private fun newPhoto() {
        mCurrentBitmap.value = null
        val photo = Photo(idCollect = collectId)
        _currentPhoto.postValue(photo)
        handlePhotoType(photo)
    }

    private fun loadPhoto(photoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val photo = collectRepository.getPhotoByID(photoId)
            photo?.let {
                _currentPhoto.postValue(photo)
                handlePhotoType(photo)
            }
        }
    }

    fun updatePhotoPath(filePath: String?) {
        _currentPhoto.value?.let {
            it.filepath = filePath
            _currentPhoto.postValue(it)
        }
    }

    private fun handlePhotoType(photo: Photo) {
        photoType.postValue(photo.type)
    }

    fun setPhotoType(type: String) {
        _currentPhoto.value?.let {
            it.type = type
            _currentPhoto.postValue(it)
            photoType.postValue(type)
        }
    }

    fun updatePhotoLocation(location: Location) {
        _currentPhoto.value?.let {
            it.latitude = location.latitude
            it.longitude = location.longitude
        }
    }

    @Throws(IOException::class)
    fun createPhotoFile(context: Context): File? {
        _currentPhoto.value?.let {
            val filePath = it.filepath
            return if (filePath != null) {
                File(filePath)
            } else {
                val storageDir: File =
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: return null
                File(storageDir, "MP_${it.id}.jpg").apply {
                    it.filepath = absolutePath
                    _currentPhoto.value = it
                }
            }
        } ?: return null
    }

    fun compressPhoto() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentPhoto.value?.let { photo ->
                val filePath = photo.filepath
                filePath?.let {
                    val bitmap = BitmapFactory.decodeFile(it)
                    val byteOutputStream = ByteArrayOutputStream()
                    bitmap.compress(CompressFormat.JPEG, COMPRESSION_QUALITY, byteOutputStream)

                    val compressed = File(it)
                    val fileOutputStream = FileOutputStream(compressed)
                    fileOutputStream.write(byteOutputStream.toByteArray())
                    fileOutputStream.flush()
                    fileOutputStream.close()
                }
            }
        }
    }

    fun deletePhoto() {
        _currentPhoto.value?.let {
            photoBundle.postValue(PhotoBundle(it, PhotoActions.DELETE))
        }
    }

    fun addPhotoToCollect() {
        _currentPhoto.value?.let {
            photoBundle.postValue(PhotoBundle(it, PhotoActions.ADD))
        }
    }
}