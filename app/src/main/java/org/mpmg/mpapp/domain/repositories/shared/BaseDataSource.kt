package org.mpmg.mpapp.domain.repositories.shared

import android.content.Context
import org.mpmg.mpapp.domain.database.MPDatabase

abstract class BaseDataSource(val applicationContext: Context) {

    protected fun mpDatabase(): MPDatabase? = MPDatabase.getInstance(applicationContext)
}