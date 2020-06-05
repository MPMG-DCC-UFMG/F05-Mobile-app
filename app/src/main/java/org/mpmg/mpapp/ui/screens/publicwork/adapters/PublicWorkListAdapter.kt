package org.mpmg.mpapp.ui.screens.publicwork.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.ui.screens.publicwork.delegates.PublicWorkItemDelegate
import org.mpmg.mpapp.ui.shared.delegates.StatusAdapterDelegate
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel

class PublicWorkListAdapter(locationViewModel: LocationViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    PublicWorkItemDelegate.PublicWorkItemDelegateListener {

    private val publicWorksList = mutableListOf<PublicWorkAndAddress>()

    private var mListener: PublicWorkListAdapterListener? = null

    private val delegates = listOf(
        StatusAdapterDelegate(R.layout.item_empty_list),
        PublicWorkItemDelegate(locationViewModel)
    )

    init {
        setupListeners()
    }

    private fun setupListeners() {
        val publicWorkItemDelegate = delegates[1] as PublicWorkItemDelegate
        publicWorkItemDelegate.setPublicWorkItemDelegateListener(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegate = delegates[viewType]
        return delegate.onCreateViewHolder(
            LayoutInflater.from(parent.context),
            parent
        )
    }

    override fun getItemCount(): Int {
        return if (publicWorksList.isEmpty()) 1 else publicWorksList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val delegate = delegates[holder.itemViewType]
        when (holder.itemViewType) {
            0 -> delegate.onBindViewHolder(holder, null)
            else -> delegate.onBindViewHolder(holder, publicWorksList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (publicWorksList.isEmpty()) 0 else 1
    }

    fun updatePublicWorksList(list: List<PublicWorkAndAddress>) {
        publicWorksList.clear()
        publicWorksList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onPublicWorkClicked(publicWork: PublicWorkAndAddress) {
        mListener?.onPublicWorkClicked(publicWork)
    }

    fun setPublicWorkListAdapterListener(listener: PublicWorkListAdapterListener) {
        mListener = listener
    }

    interface PublicWorkListAdapterListener {
        fun onPublicWorkClicked(publicWork: PublicWorkAndAddress)
    }
}