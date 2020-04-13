package org.mpmg.mpapp.core

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mpmg.mpapp.domain.apis.MPApi
import org.mpmg.mpapp.domain.repositories.config.ConfigRepository
import org.mpmg.mpapp.domain.repositories.config.IConfigRepository
import org.mpmg.mpapp.domain.repositories.collect.ICollectRepository
import org.mpmg.mpapp.domain.repositories.collect.CollectRepository
import org.mpmg.mpapp.domain.repositories.collect.local.ILocalCollectDataSource
import org.mpmg.mpapp.domain.repositories.collect.local.ILocalPhotoDataSource
import org.mpmg.mpapp.domain.repositories.collect.local.LocalCollectDataSource
import org.mpmg.mpapp.domain.repositories.collect.local.LocalPhotoDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.IPublicWorkRepository
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository
import org.mpmg.mpapp.domain.repositories.publicwork.local.ILocalPublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.local.LocalPublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.typework.ITypeWorkRepository
import org.mpmg.mpapp.domain.repositories.typework.TypeWorkRepository
import org.mpmg.mpapp.domain.repositories.typework.local.ILocalTypeWorkDataSource
import org.mpmg.mpapp.domain.repositories.typework.local.LocalTypeWorkDataSource
import org.mpmg.mpapp.domain.repositories.user.IUserRepository
import org.mpmg.mpapp.domain.repositories.user.UserRepository
import org.mpmg.mpapp.domain.repositories.user.local.ILocalUserDataSource
import org.mpmg.mpapp.domain.repositories.user.local.LocalUserDataSource
import org.mpmg.mpapp.ui.viewmodels.*

val viewModelModules = module {
    viewModel { LoginViewModel(androidApplication(), get(), get()) }
    viewModel { LocationViewModel() }
    viewModel { PublicWorkViewModel(get()) }
    viewModel { ConfigurationViewModel(get(), get()) }
    viewModel { TypeWorkViewModel(get()) }
    viewModel { CollectViewModel(get(), get()) }
    viewModel { PhotoViewModel() }
}

val repositoriesModules = module {
    single { UserRepository(get()) as IUserRepository }
    single { PublicWorkRepository(get()) as IPublicWorkRepository }
    single { TypeWorkRepository(get()) as ITypeWorkRepository }
    single { ConfigRepository(get(), androidApplication()) as IConfigRepository }
    single { CollectRepository(get(), get()) as ICollectRepository }
}

val dataSourceModules = module {
    single { LocalUserDataSource(androidApplication()) as ILocalUserDataSource }
    single { LocalPublicWorkDataSource(androidApplication()) as ILocalPublicWorkDataSource }
    single { LocalTypeWorkDataSource(androidApplication()) as ILocalTypeWorkDataSource }
    single { LocalPhotoDataSource(androidApplication()) as ILocalPhotoDataSource }
    single { LocalCollectDataSource(androidApplication()) as ILocalCollectDataSource }
}

val apiModules = module {
    single { MPApi() }
}