package org.mpmg.mpapp.domain.repositories.config.datasources

interface ILocalConfigDataSource {

    fun saveTypeWorksVersion(typeWorksVersion: Int)

    fun currentTypeWorksVersion(): Int

    fun savePublicWorkVersion(publicWorkVersion: Int)

    fun currentPublicWorkVersion(): Int

    fun currentTypePhotosVersion(): Int

    fun saveTypePhotosVersion(typePhotosVersion: Int)

    fun saveAssociationsVersion(associationVersion: Int)

    fun setLoggedUserEmail(email: String)

    fun getLoggedUserEmail(): String
}