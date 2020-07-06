package org.mpmg.mpapp.ui.screens.upload.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.ui.screens.upload.delegates.UploadPublicWorkItemDelegate
import org.mpmg.mpapp.ui.screens.upload.models.PublicWorkUploadUI
import org.mpmg.mpapp.ui.shared.delegates.StatusAdapterDelegate
import org.mpmg.mpapp.ui.viewmodels.SendViewModel

class UploadPublicWorkAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val publicWorkSendList = mutableListOf<PublicWorkUploadUI>()

    private val delegates = listOf(
        StatusAdapterDelegate(R.layout.item_empty_collect_list),
        UploadPublicWorkItemDelegate()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegate = delegates[viewType]
        return delegate.onCreateViewHolder(
            LayoutInflater.from(parent.context),
            parent
        )
    }

    override fun getItemCount(): Int {
        return if (publicWorkSendList.isEmpty()) 1 else publicWorkSendList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val delegate = delegates[holder.itemViewType]
        when (holder.itemViewType) {
            0 -> delegate.onBindViewHolder(holder, null)
            else -> delegate.onBindViewHolder(holder, publicWorkSendList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (publicWorkSendList.isEmpty()) 0 else 1
    }

    fun updatePublicWorksList(list: List<PublicWorkUploadUI>) {
        publicWorkSendList.clear()
        publicWorkSendList.addAll(list)
        notifyDataSetChanged()
    }
}