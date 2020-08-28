package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.TypePhoto
import org.mpmg.mpapp.domain.repositories.typephoto.ITypePhotoRepository

class TypePhotoViewModel(private val typePhotoRepository: ITypePhotoRepository) : ViewModel() {

    lateinit var typePhotos: List<TypePhoto>

    fun refreshTypePhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            typePhotos = typePhotoRepository.listAllTypePhotos()
        }
    }
}