package org.mpmg.mpapp.core

import android.app.Application
import android.content.Intent
import android.os.Build
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.mpmg.mpapp.core.services.LocationService

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