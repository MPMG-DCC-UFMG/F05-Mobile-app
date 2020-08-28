package org.mpmg.mpapp.core

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mpmg.mpapp.domain.network.retrofit.*
import org.mpmg.mpapp.domain.repositories.association.AssociationRepository
import org.mpmg.mpapp.domain.repositories.association.IAssociationRepository
import org.mpmg.mpapp.domain.repositories.association.datasources.ILocalAssociationDataSource
import org.mpmg.mpapp.domain.repositories.association.datasources.LocalAssociationDataSource
import org.mpmg.mpapp.domain.repositories.city.CityRepository
import org.mpmg.mpapp.domain.repositories.city.ICityRepository
import org.mpmg.mpapp.domain.repositories.city.datasources.ILocalCityDataSource
import org.mpmg.mpapp.domain.repositories.city.datasources.LocalCityDataSource
import org.mpmg.mpapp.domain.repositories.config.ConfigRepository
import org.mpmg.mpapp.domain.repositories.config.IConfigRepository
import org.mpmg.mpapp.domain.repositories.collect.ICollectRepository
import org.mpmg.mpapp.domain.repositories.collect.CollectRepository
import org.mpmg.mpapp.domain.repositories.collect.datasources.*
import org.mpmg.mpapp.domain.repositories.config.datasources.ILocalConfigDataSource
import org.mpmg.mpapp.domain.repositories.config.datasources.IRemoteConfigDataSource
import org.mpmg.mpapp.domain.repositories.config.datasources.LocalConfigDataSource
import org.mpmg.mpapp.domain.repositories.config.datasources.RemoteConfigDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.ILocalPublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.IRemotePublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.LocalPublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.RemotePublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.typephoto.ITypePhotoRepository
import org.mpmg.mpapp.domain.repositories.typephoto.TypePhotoRepository
import org.mpmg.mpapp.domain.repositories.typephoto.datasources.ILocalTypePhotoDataSource
import org.mpmg.mpapp.domain.repositories.typephoto.datasources.LocalTypePhotoDataSource
import org.mpmg.mpapp.domain.repositories.typework.ITypeWorkRepository
import org.mpmg.mpapp.domain.repositories.typework.TypeWorkRepository
import org.mpmg.mpapp.domain.repositories.typework.datasources.ILocalTypeWorkDataSource
import org.mpmg.mpapp.domain.repositories.typework.datasources.LocalTypeWorkDataSource
import org.mpmg.mpapp.domain.repositories.user.IUserRepository
import org.mpmg.mpapp.domain.repositories.user.UserRepository
import org.mpmg.mpapp.domain.repositories.user.datasources.ILocalUserDataSource
import org.mpmg.mpapp.domain.repositories.user.datasources.IRemoteUserDataSource
import org.mpmg.mpapp.domain.repositories.user.datasources.LocalUserDataSource
import org.mpmg.mpapp.domain.repositories.user.datasources.RemoteUserDataSource
import org.mpmg.mpapp.domain.repositories.workstatus.IWorkStatusRepository
import org.mpmg.mpapp.domain.repositories.workstatus.WorkStatusRepository
import org.mpmg.mpapp.domain.repositories.workstatus.datasources.ILocalWorkStatusDataSource
import org.mpmg.mpapp.domain.repositories.workstatus.datasources.LocalWorkStatusDataSource
import org.mpmg.mpapp.ui.screens.collect.viewmodels.CollectFragmentViewModel
import org.mpmg.mpapp.ui.viewmodels.*

val viewModelModules = module {
    viewModel { LoginViewModel(androidApplication(), get(), get()) }
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
    single { UserRepository(get(), get()) as IUserRepository }
    single { PublicWorkRepository(get(), get()) as IPublicWorkRepository }
    single { TypeWorkRepository(get()) as ITypeWorkRepository }
    single { ConfigRepository(get(), get()) as IConfigRepository }
    single { CollectRepository(get(), get(), get(), get()) as ICollectRepository }
    single { TypePhotoRepository(get()) as ITypePhotoRepository }
    single { AssociationRepository(get()) as IAssociationRepository }
    single { WorkStatusRepository(get()) as IWorkStatusRepository }
    single { CityRepository(get()) as ICityRepository }
}

val networkModule = module {
    factory { provideClient() }
    single { provideRetrofit(get()) }
    factory { provideMPApi(get()) }
}

val dataSourceModules = module {
    single { LocalUserDataSource(androidApplication()) as ILocalUserDataSource }
    single { LocalPublicWorkDataSource(androidApplication()) as ILocalPublicWorkDataSource }
    single { LocalTypeWorkDataSource(androidApplication()) as ILocalTypeWorkDataSource }
    single { LocalPhotoDataSource(androidApplication()) as ILocalPhotoDataSource }
    single { LocalCollectDataSource(androidApplication()) as ILocalCollectDataSource }
    single { LocalConfigDataSource(androidApplication()) as ILocalConfigDataSource }
    single { LocalTypePhotoDataSource(androidApplication()) as ILocalTypePhotoDataSource }
    single { LocalAssociationDataSource(androidApplication()) as ILocalAssociationDataSource }
    single { LocalWorkStatusDataSource(androidApplication()) as ILocalWorkStatusDataSource }
    single { LocalCityDataSource(androidApplication()) as ILocalCityDataSource }

    single { RemoteConfigDataSource(get()) as IRemoteConfigDataSource }
    single { RemotePublicWorkDataSource(get()) as IRemotePublicWorkDataSource }
    single { RemoteCollectDataSource(get()) as IRemoteCollectDataSource }
    single { RemotePhotoDataSource(get()) as IRemotePhotoDataSource }
    single { RemoteUserDataSource(get()) as IRemoteUserDataSource }
}