package org.mpmg.mpapp.core

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository
import org.mpmg.mpapp.domain.repositories.publicwork.local.ILocalPublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.local.LocalPublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.user.IUserRepository
import org.mpmg.mpapp.domain.repositories.user.UserRepository
import org.mpmg.mpapp.domain.repositories.user.local.ILocalUserDataSource
import org.mpmg.mpapp.domain.repositories.user.local.LocalUserDataSource
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel
import org.mpmg.mpapp.ui.viewmodels.LoginViewModel
import org.mpmg.mpapp.ui.viewmodels.PublicWorkViewModel

val viewModelModules = module {
    viewModel { LoginViewModel(androidApplication(), get()) }
    viewModel { LocationViewModel() }
    viewModel { PublicWorkViewModel(get()) }
}

val repositoriesModules = module {
    single { UserRepository(get()) as IUserRepository }
    single { PublicWorkRepository(get()) as IPublicWorkRepository }
}

val dataSourceModules = module {
    single { LocalUserDataSource(androidApplication()) as ILocalUserDataSource }
    single { LocalPublicWorkDataSource(androidApplication()) as ILocalPublicWorkDataSource }
}