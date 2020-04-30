package org.mpmg.mpapp.core

import android.app.Application
import io.sentry.Sentry
import io.sentry.android.AndroidSentryClientFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.mpmg.mpapp.R

class MPApp : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
        initSentry()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MPApp)
            modules(viewModelModules, repositoriesModules, dataSourceModules, apiModules)
        }
    }

    private fun initSentry() {
        Sentry.init(getString(R.string.sentry_key), AndroidSentryClientFactory(this))
    }
}