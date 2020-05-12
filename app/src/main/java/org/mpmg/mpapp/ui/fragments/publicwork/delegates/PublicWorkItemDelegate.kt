package org.mpmg.mpapp.ui.fragments.publicwork.delegates

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_public_work_list.view.*
import org.koin.core.KoinComponent
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.interfaces.BaseDelegate
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAdress
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel

class PublicWorkItemDelegate(val locationViewModel: LocationViewModel) : BaseDelegate<BaseModel>,
    KoinComponent {

    private var mPublicWorkItemDelegateListener: PublicWorkItemDelegateListener? = null

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        return PublicWorkItemViewHolder(
            inflater.inflate(
                R.layout.item_public_work_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, delegateObject: BaseModel?) {
        delegateObject as PublicWorkAndAdress
        with(holder as PublicWorkItemViewHolder) {
            mName.text = delegateObject.publicWork.name
            mAddress.text = delegateObject.address.toString()

            mSent.visibility = getVisibility(delegateObject.publicWork.isSent)
            mCheck.visibility = getVisibility(delegateObject.publicWork.idCollect != null)

            mContainer.setOnClickListener {
                mPublicWorkItemDelegateListener?.onPublicWorkClicked(delegateObject)
            }

            setupObservers(holder, mDistance, delegateObject.address.getLocation())
        }
    }

    private fun setupObservers(
        holder: PublicWorkItemViewHolder,
        distanceTextView: TextView,
        position: Location?
    ) {
        locationViewModel.getCurrentLocationLiveData()
            .observe(holder.itemView.context as LifecycleOwner,
                Observer { location ->
                    val distance = getDistance(location, position)
                    distanceTextView.text = getDistanceString(distance)
                })
    }

    private fun getDistance(point1: Location?, point2: Location?): Float? {
        return if (point1 != null && point2 != null) point1.distanceTo(point2) else null
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

    private fun getVisibility(state: Boolean): Int {
        return if (state) View.VISIBLE else View.INVISIBLE
    }

    fun setPublicWorkItemDelegateListener(listener: PublicWorkItemDelegateListener) {
        mPublicWorkItemDelegateListener = listener
    }

    interface PublicWorkItemDelegateListener {
        fun onPublicWorkClicked(publicWork: PublicWorkAndAdress)
    }

    class PublicWorkItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val mName = itemView.textView_itemPublicWorkList_name
        val mAddress = itemView.textView_itemPublicWorkList_address
        val mContainer = itemView.cardView_itemPublicWorkList_mainContainer
        val mDistance = itemView.textView_itemPublicWorkList_distance
        val mSent = itemView.imageView_itemPublicWorkList_sent
        val mCheck = itemView.imageView_itemPublicWorkList_check
    }
}