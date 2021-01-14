package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.database.models.City
import org.mpmg.mpapp.domain.repositories.city.CityRepository

class CityViewModel(private val cityRepository: CityRepository) : ViewModel() {

    private lateinit var citiesList : List<City>

    fun getCitiesList() = citiesList

    fun refreshCities(){
        viewModelScope.launch(Dispatchers.IO) {
            citiesList = cityRepository.listCities()
        }
    }
}