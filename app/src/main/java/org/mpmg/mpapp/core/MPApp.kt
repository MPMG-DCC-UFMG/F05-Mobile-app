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
        startLocationService()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MPApp)
            modules(viewModelModules, repositoriesModules, dataSourceModules)
        }
    }

    private fun startLocationService() {
        val intent = Intent(this, LocationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}