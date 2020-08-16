package org.mpmg.mpapp.domain.repositories.config.datasources

interface ILocalConfigDataSource {

    fun currentTypeWorksVersion(): Int

    fun currentPublicWorkVersion(): Int

    fun currentTypePhotosVersion(): Int

    fun currentAssociationVersion(): Int

    fun currentWorkStatusVersion(): Int

    fun saveTypePhotosVersion(typePhotosVersion: Int)

    fun savePublicWorkVersion(publicWorkVersion: Int)

    fun saveTypeWorksVersion(typeWorksVersion: Int)

    fun saveAssociationsVersion(associationVersion: Int)

    fun saveWorkStatusVersion(workStatusVersion: Int)

    fun setLoggedUserEmail(email: String)

    fun getLoggedUserEmail(): String
}