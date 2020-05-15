package org.mpmg.mpapp.domain.repositories.config

import org.mpmg.mpapp.domain.repositories.config.datasources.ILocalConfigDataSource
import org.mpmg.mpapp.domain.repositories.config.datasources.IRemoteConfigDataSource

interface IConfigRepository : IRemoteConfigDataSource, ILocalConfigDataSource {

}