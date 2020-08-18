package org.mpmg.mpapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import org.mpmg.mpapp.domain.repositories.city.ICityRepository

class CityViewModel(cityRepository: ICityRepository) : ViewModel() {

    private var citiesList = cityRepository.listCitiesLive()

    fun getCitiesList() = citiesList
}