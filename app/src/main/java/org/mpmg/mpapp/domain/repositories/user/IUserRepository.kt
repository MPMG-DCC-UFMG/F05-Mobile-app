package org.mpmg.mpapp.domain.repositories.user

import org.mpmg.mpapp.domain.repositories.user.datasources.ILocalUserDataSource
import org.mpmg.mpapp.domain.repositories.user.datasources.IRemoteUserDataSource

interface IUserRepository : ILocalUserDataSource, IRemoteUserDataSource {
}