package org.mpmg.mpapp.core

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mpmg.mpapp.domain.network.retrofit.*
import org.mpmg.mpapp.domain.repositories.association.AssociationRepository
import org.mpmg.mpapp.domain.repositories.association.datasources.LocalAssociationDataSource
import org.mpmg.mpapp.domain.repositories.city.CityRepository
import org.mpmg.mpapp.domain.repositories.city.datasources.LocalCityDataSource
import org.mpmg.mpapp.domain.repositories.config.ConfigRepository
import org.mpmg.mpapp.domain.repositories.collect.CollectRepository
import org.mpmg.mpapp.domain.repositories.collect.datasources.*
import org.mpmg.mpapp.domain.repositories.config.datasources.LocalConfigDataSource
import org.mpmg.mpapp.domain.repositories.config.datasources.RemoteConfigDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.LocalPublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.RemotePublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.typephoto.TypePhotoRepository
import org.mpmg.mpapp.domain.repositories.typephoto.datasources.LocalTypePhotoDataSource
import org.mpmg.mpapp.domain.repositories.typework.TypeWorkRepository
import org.mpmg.mpapp.domain.repositories.typework.datasources.LocalTypeWorkDataSource
import org.mpmg.mpapp.domain.repositories.user.UserRepository
import org.mpmg.mpapp.domain.repositories.user.datasources.LocalUserDataSource
import org.mpmg.mpapp.domain.repositories.user.datasources.RemoteUserDataSource
import org.mpmg.mpapp.domain.repositories.workstatus.WorkStatusRepository
import org.mpmg.mpapp.domain.repositories.workstatus.datasources.LocalWorkStatusDataSource
import org.mpmg.mpapp.ui.screens.collect.viewmodels.CollectFragmentViewModel
import org.mpmg.mpapp.ui.screens.login.viewmodels.CreateAccountViewModel
import org.mpmg.mpapp.ui.screens.login.viewmodels.LoginViewModel
import org.mpmg.mpapp.ui.viewmodels.*

val viewModelModules = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { LocationViewModel() }
    viewModel { PublicWorkViewModel(get()) }
    viewModel { ConfigurationViewModel(androidApplication()) }
    viewModel { TypeWorkViewModel(get()) }
    viewModel { CollectViewModel(get(), get(), get()) }
    viewModel { PhotoViewModel() }
    viewModel { SendViewModel(androidApplication(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { WorkStatusViewModel(get()) }
    viewModel { CityViewModel(get()) }
    viewModel { TypePhotoViewModel(get()) }

    // ViewModels for UI
    viewModel { CollectFragmentViewModel() }
    viewModel { CreateAccountViewModel(get()) }
}

val repositoriesModules = module {
    single { UserRepository(get(), get()) }
    single { PublicWorkRepository(get(), get()) }
    single { TypeWorkRepository(get()) }
    single { ConfigRepository(get(), get()) }
    single { CollectRepository(get(), get(), get(), get()) }
    single { TypePhotoRepository(get()) }
    single { AssociationRepository(get()) }
    single { WorkStatusRepository(get()) }
    single { CityRepository(get()) }
}

val networkModule = module {
    factory { provideClient() }
    single { provideRetrofit(get()) }
    factory { provideMPApi(get()) }
}

val dataSourceModules = module {
    single { LocalPublicWorkDataSource(androidApplication()) }
    single { LocalTypeWorkDataSource(androidApplication()) }
    single { LocalPhotoDataSource(androidApplication()) }
    single { LocalCollectDataSource(androidApplication()) }
    single { LocalConfigDataSource(androidApplication()) }
    single { LocalTypePhotoDataSource(androidApplication()) }
    single { LocalAssociationDataSource(androidApplication()) }
    single { LocalWorkStatusDataSource(androidApplication()) }
    single { LocalCityDataSource(androidApplication()) }
    single { LocalUserDataSource(androidApplication()) }

    single { RemoteConfigDataSource(get()) }
    single { RemotePublicWorkDataSource(get()) }
    single { RemoteCollectDataSource(get()) }
    single { RemotePhotoDataSource(get()) }
    single { RemoteUserDataSource(get()) }
}