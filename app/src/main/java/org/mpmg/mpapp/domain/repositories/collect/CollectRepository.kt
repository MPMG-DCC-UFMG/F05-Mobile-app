package org.mpmg.mpapp.domain.repositories.collect

import androidx.lifecycle.LiveData
import androidx.room.Transaction
import org.mpmg.mpapp.domain.database.models.Collect
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.network.models.CollectRemote
import org.mpmg.mpapp.domain.network.models.ImageUploadResponse
import org.mpmg.mpapp.domain.network.models.PhotoRemote
import org.mpmg.mpapp.domain.network.models.ResponseRemote
import org.mpmg.mpapp.domain.repositories.collect.datasources.ILocalCollectDataSource
import org.mpmg.mpapp.domain.repositories.collect.datasources.ILocalPhotoDataSource
import org.mpmg.mpapp.domain.repositories.collect.datasources.IRemoteCollectDataSource
import org.mpmg.mpapp.domain.repositories.collect.datasources.IRemotePhotoDataSource
import java.io.File

class CollectRepository(
    private val localPhotoDataSource: ILocalPhotoDataSource,
    private val localCollectDataSource: ILocalCollectDataSource,
    private val remoteCollectDataSource: IRemoteCollectDataSource,
    private val remotePhotoDataSource: IRemotePhotoDataSource
) :
    ICollectRepository {

    override fun listPhotosByCollectionIDLive(collectionId: String): LiveData<List<Photo>> {
        return localPhotoDataSource.listPhotosByCollectionIDLive(collectionId)
    }

    override fun getPhotoByID(photoId: String): Photo? {
        return localPhotoDataSource.getPhotoByID(photoId)
    }

    override fun insertPhotos(photos: List<Photo>) {
        localPhotoDataSource.insertPhotos(photos)
    }

    @Transaction
    override fun insertCollect(collect: Collect, photos: List<Photo>) {
        localCollectDataSource.insertCollect(collect)
        localPhotoDataSource.insertPhotos(photos)
    }

    override fun getCollect(collectId: String): Collect? {
        return localCollectDataSource.getCollectById(collectId)
    }

    override fun getCollectByPublicWork(publicWorkId: String): Collect? {
        return localCollectDataSource.getCollectByPublicIdAndStatus(publicWorkId, false)
    }

    override fun listPhotosByCollectionID(collectionId: String): List<Photo> {
        return localPhotoDataSource.listPhotosByCollectionID(collectionId)
    }

    override fun deletePhotoById(photoId: String) {
        localPhotoDataSource.deletePhotoById(photoId)
    }

    override suspend fun sendCollect(collectRemote: CollectRemote): ResponseRemote {
        return remoteCollectDataSource.sendCollect(collectRemote)
    }

    override suspend fun sendImage(image: File): ImageUploadResponse {
        return remotePhotoDataSource.sendImage(image)
    }

    override suspend fun sendPhoto(photo: PhotoRemote): ResponseRemote {
        return remotePhotoDataSource.sendPhoto(photo)
    }

    override fun markCollectSent(collectId: String) {
        localCollectDataSource.markCollectSent(collectId)
    }

    override fun deleteCollectById(collectId: String) {
        val photos = localPhotoDataSource.listPhotosByCollectionID(collectId)
        photos.forEach {
            deletePhotoById(photoId = it.id)
        }
        localCollectDataSource.deleteCollectById(collectId)
    }
}