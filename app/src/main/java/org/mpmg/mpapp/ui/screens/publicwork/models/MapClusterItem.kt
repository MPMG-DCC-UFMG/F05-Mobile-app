package org.mpmg.mpapp.ui.screens.publicwork.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress

data class MapClusterItem(
    private val publicWorkAndAddress: PublicWorkAndAddress,
    private val location: LatLng
) : ClusterItem {

    override fun getPosition(): LatLng = location

    override fun getTitle(): String = publicWorkAndAddress.publicWork.name

    override fun getSnippet(): String? = publicWorkAndAddress.address.toString()
}
