package org.mpmg.mpapp.ui.screens.publicwork.models

import android.view.View
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress

data class PublicWorkListItem(
    val publicWorkAndAddress: PublicWorkAndAddress
) {
    val name = publicWorkAndAddress.publicWork.name
    val addressString = publicWorkAndAddress.address.toString()
    val sentVisibility = getVisibility(publicWorkAndAddress.publicWork.toSend)
    val checkVisibility = getVisibility(publicWorkAndAddress.publicWork.idCollect != null)

    private fun getVisibility(state: Boolean): Int {
        return if (state) View.VISIBLE else View.INVISIBLE
    }

    private fun getDistanceString(distance: Float?): String {
        return if (distance != null) {
            if (distance > 1000) {
                "%.2f km".format(distance / 1000)
            } else {
                "%.2f m".format(distance)
            }
        } else {
            "-- m"
        }
    }
}