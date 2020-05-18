package org.mpmg.mpapp.domain.repositories.collect

import androidx.lifecycle.LiveData
import androidx.room.Transaction
import org.mpmg.mpapp.domain.database.models.Collect
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.repositories.collect.datasources.ILocalCollectDataSource
import org.mpmg.mpapp.domain.repositories.collect.datasources.ILocalPhotoDataSource

class CollectRepository(
    private val localPhotoDataSource: ILocalPhotoDataSource,
    private val localCollectDataSource: ILocalCollectDataSource
) :
    ICollectRepository {

    override fun listPhotosByCollectionIDLive(collectionId: String): LiveData<List<Photo>> {
        return localPhotoDataSource.listPhotosByCollectionIDLive(collectionId)
    }

    override fun getPhotoByID(photoId: String): Photo {
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
        localPhotoDataSource
    }
}