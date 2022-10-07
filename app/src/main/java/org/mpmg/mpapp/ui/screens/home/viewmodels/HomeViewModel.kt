package org.mpmg.mpapp.ui.screens.home.viewmodels

import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseAuth
import org.mpmg.mpapp.domain.repositories.publicwork.InspectionRepository
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository
import org.mpmg.mpapp.ui.screens.base.MVVMViewModel

class HomeViewModel(publicWorkRepository: PublicWorkRepository) : MVVMViewModel() {

    val countToSendLive = publicWorkRepository.countPublicWorkToSendLive().asLiveData()

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}