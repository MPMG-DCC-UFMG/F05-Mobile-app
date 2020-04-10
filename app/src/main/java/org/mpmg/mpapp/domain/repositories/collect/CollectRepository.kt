package org.mpmg.mpapp.domain.repositories.collect

import androidx.lifecycle.LiveData
import androidx.room.Transaction
import org.mpmg.mpapp.domain.models.Collect
import org.mpmg.mpapp.domain.models.Photo
import org.mpmg.mpapp.domain.repositories.collect.local.ILocalCollectDataSource
import org.mpmg.mpapp.domain.repositories.collect.local.ILocalPhotoDataSource

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
}