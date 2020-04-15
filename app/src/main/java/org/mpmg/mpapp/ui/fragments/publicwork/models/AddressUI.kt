package org.mpmg.mpapp.ui.fragments.publicwork.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import org.mpmg.mpapp.domain.models.Address

data class AddressUI(
    var id: String? = null,
    private var _street: String = "",
    private var _neighborhood: String = "",
    private var _number: String = "",
    private var _latitude: Double = 0.0,
    private var _longitude: Double = 0.0,
    private var _city: String = "",
    private var _cep: String = ""
) : BaseObservable() {

    constructor(address: Address) : this() {
        id = address.id
        _street = address.street
        _neighborhood = address.neighborhood
        _number = address.number
        _latitude = address.latitude ?: 0.0
        _longitude = address.longitude ?: 0.0
        _city = address.city
        _cep = address.cep
    }

    var street: String
        @Bindable get() = _street
        set(value) {
            _street = value
            notifyPropertyChanged(BR.street)
        }

    var neighborhood: String
        @Bindable get() = _neighborhood
        set(value) {
            _neighborhood = value
            notifyPropertyChanged(BR.neighborhood)
        }

    var number: String
        @Bindable get() = _number
        set(value) {
            _number = value
            notifyPropertyChanged(BR.number)
        }

    var latitude: Double
        @Bindable get() = _latitude
        set(value) {
            _latitude = value
            notifyPropertyChanged(BR.latitude)
        }

    var longitude: Double
        @Bindable get() = _longitude
        set(value) {
            _longitude = value
            notifyPropertyChanged(BR.longitude)
        }

    var city: String
        @Bindable get() = _city
        set(value) {
            _city = value
            notifyPropertyChanged(BR.city)
        }

    var cep: String
        @Bindable get() = _cep
        set(value) {
            _cep = value
            notifyPropertyChanged(BR.cep)
        }

    fun isValid(): Boolean {
        return number.isNotBlank() && city.isNotBlank() && cep.isNotBlank() && latitude != 0.0 && longitude != 0.0
    }

    fun toAddressDB(): Address {
        val addressDB = Address(
            street = this.street,
            neighborhood = this.neighborhood,
            cep = this.cep,
            latitude = this.latitude,
            longitude = this.longitude,
            number = this.number,
            city = this.city
        )

        id?.let { addressDB.id = it }

        return addressDB
    }
}