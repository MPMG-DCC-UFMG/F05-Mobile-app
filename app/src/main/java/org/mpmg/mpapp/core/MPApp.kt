package org.mpmg.mpapp.core

import android.app.Application
import com.facebook.stetho.Stetho
import io.sentry.Sentry
import io.sentry.android.AndroidSentryClientFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.mpmg.mpapp.BuildConfig
import org.mpmg.mpapp.R

class MPApp : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
        initSentry()
        initStetho()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MPApp)
            modules(repositoriesModules, dataSourceModules, networkModule, viewModelModules)
        }
    }

    private fun initSentry() {
        Sentry.init(getString(R.string.sentry_key), AndroidSentryClientFactory(this))
            .environment = BuildConfig.ENVIRONMENT
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}