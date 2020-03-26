package org.mpmg.mpapp.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MPApp : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MPApp)
            modules(viewModelModules, repositoriesModules, dataSourceModules)
        }
    }
}