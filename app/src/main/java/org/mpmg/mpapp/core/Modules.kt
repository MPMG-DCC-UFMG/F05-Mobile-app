package org.mpmg.mpapp.core

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mpmg.mpapp.ui.viewmodels.LoginViewModel

var viewModelModules = module {
    viewModel { LoginViewModel(androidApplication()) }
}