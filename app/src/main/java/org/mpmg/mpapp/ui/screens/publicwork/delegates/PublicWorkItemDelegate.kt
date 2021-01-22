package org.mpmg.mpapp.ui.screens.publicwork.delegates

import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.interfaces.BaseDelegate
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.databinding.ItemPublicWorkListBinding
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.ui.screens.publicwork.models.PublicWorkListItem
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel

class PublicWorkItemDelegate(val locationViewModel: LocationViewModel) : BaseDelegate<BaseModel> {

    private var mPublicWorkItemDelegateListener: PublicWorkItemDelegateListener? = null

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding: ItemPublicWorkListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_public_work_list, parent, false
        )
        return PublicWorkItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, delegateObject: BaseModel?) {
        delegateObject as PublicWorkAndAddress
        with(holder as PublicWorkItemViewHolder) {

            mContainer.setOnClickListener {
                mPublicWorkItemDelegateListener?.onPublicWorkClicked(delegateObject)
            }

            bind(PublicWorkListItem(delegateObject))
            setupObservers(holder, mDistance, delegateObject.address.getLocation())
        }
    }

    private fun setupObservers(
        holder: PublicWorkItemViewHolder,
        distanceTextView: TextView,
        position: Location?
    ) {
        locationViewModel.getCurrentLocationLiveData()
            .observe(holder.itemView.context as LifecycleOwner) { location ->
                val distance = getDistance(location, position)
                distanceTextView.text = getDistanceString(distance)
                setDistanceColor(distanceTextView, distance)
            }
    }

    private fun setDistanceColor(textView: TextView, distance: Float?) {
        val colorResourceId =
            if (distance == null || distance > 1000)
                R.color.colorGreyMedium
            else
                R.color.colorGreenMP

        textView.setTextColor(ContextCompat.getColor(textView.context, colorResourceId))
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

    fun setPublicWorkItemDelegateListener(listener: PublicWorkItemDelegateListener) {
        mPublicWorkItemDelegateListener = listener
    }

    interface PublicWorkItemDelegateListener {
        fun onPublicWorkClicked(publicWork: PublicWorkAndAddress)
    }

    class PublicWorkItemViewHolder(private val binding: ItemPublicWorkListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mContainer = binding.cardViewItemPublicWorkListMainContainer
        val mDistance = binding.textViewItemPublicWorkListDistance

        fun bind(publicWork: PublicWorkListItem) {
            binding.publicWork = publicWork
            binding.executePendingBindings()
        }
    }
}