package org.mpmg.mpapp.core

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mpmg.mpapp.domain.network.api.MPApi
import org.mpmg.mpapp.domain.network.retrofit.provideMPApi
import org.mpmg.mpapp.domain.network.retrofit.provideOkHttpClient
import org.mpmg.mpapp.domain.network.retrofit.provideRetrofit
import org.mpmg.mpapp.domain.repositories.config.ConfigRepository
import org.mpmg.mpapp.domain.repositories.config.IConfigRepository
import org.mpmg.mpapp.domain.repositories.collect.ICollectRepository
import org.mpmg.mpapp.domain.repositories.collect.CollectRepository
import org.mpmg.mpapp.domain.repositories.collect.datasources.ILocalCollectDataSource
import org.mpmg.mpapp.domain.repositories.collect.datasources.ILocalPhotoDataSource
import org.mpmg.mpapp.domain.repositories.collect.datasources.LocalCollectDataSource
import org.mpmg.mpapp.domain.repositories.collect.datasources.LocalPhotoDataSource
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
    viewModel { ConfigurationViewModel(get(), get(), get()) }
    viewModel { TypeWorkViewModel(get()) }
    viewModel { CollectViewModel(get(), get(), get()) }
    viewModel { PhotoViewModel() }
    viewModel { SendViewModel(androidApplication()) }
}

val repositoriesModules = module {
    single { UserRepository(get()) as IUserRepository }
    single { PublicWorkRepository(get(), get()) as IPublicWorkRepository }
    single { TypeWorkRepository(get()) as ITypeWorkRepository }
    single { ConfigRepository(get(), get()) as IConfigRepository }
    single { CollectRepository(get(), get()) as ICollectRepository }
}

val networkModule = module {
    factory { provideOkHttpClient() }
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
    single { RemoteConfigDataSource(get()) as IRemoteConfigDataSource }
    single { RemotePublicWorkDataSource(get()) as IRemotePublicWorkDataSource }
}