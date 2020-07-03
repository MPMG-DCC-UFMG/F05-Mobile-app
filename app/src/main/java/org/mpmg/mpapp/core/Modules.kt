package org.mpmg.mpapp.core

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mpmg.mpapp.domain.network.retrofit.*
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
import org.mpmg.mpapp.domain.repositories.user.datasources.LocalUserDataSource
import org.mpmg.mpapp.ui.viewmodels.*

val viewModelModules = module {
    viewModel { LoginViewModel(androidApplication(), get(), get()) }
    viewModel { LocationViewModel() }
    viewModel { PublicWorkViewModel(get()) }
    viewModel { ConfigurationViewModel(androidApplication()) }
    viewModel { TypeWorkViewModel(get()) }
    viewModel { CollectViewModel(get(), get(), get()) }
    viewModel { PhotoViewModel(get()) }
    viewModel { SendViewModel(androidApplication(), get()) }
    viewModel { HomeViewModel(get()) }
}

val repositoriesModules = module {
    single { UserRepository(get()) as IUserRepository }
    single { PublicWorkRepository(get(), get()) as IPublicWorkRepository }
    single { TypeWorkRepository(get()) as ITypeWorkRepository }
    single { ConfigRepository(get(), get()) as IConfigRepository }
    single { CollectRepository(get(), get(), get(), get()) as ICollectRepository }
    single { TypePhotoRepository(get()) as ITypePhotoRepository }
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

    single { RemoteConfigDataSource(get()) as IRemoteConfigDataSource }
    single { RemotePublicWorkDataSource(get()) as IRemotePublicWorkDataSource }
    single { RemoteCollectDataSource(get()) as IRemoteCollectDataSource }
    single { RemotePhotoDataSource(get()) as IRemotePhotoDataSource }
}