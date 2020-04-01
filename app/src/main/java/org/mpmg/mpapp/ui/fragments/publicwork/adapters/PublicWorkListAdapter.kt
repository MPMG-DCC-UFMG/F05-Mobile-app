package org.mpmg.mpapp.ui.fragments.publicwork.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.interfaces.BaseDelegate
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.models.PublicWork
import org.mpmg.mpapp.ui.fragments.publicwork.adapters.PublicWorkListAdapter.PublicWorkListState.*
import org.mpmg.mpapp.ui.fragments.publicwork.delegates.PublicWorkItemDelegate
import org.mpmg.mpapp.ui.shared.delegates.StatusAdapterDelegate

class PublicWorkListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val publicWorksList = mutableListOf<PublicWork>()

    enum class PublicWorkListState {
        EMPTY_PUBLIC_WORK_LIST {
            override fun delegate() = StatusAdapterDelegate(R.layout.item_empty_list)
        },
        DEFAULT_PUBLIC_WORK_LIST {
            override fun delegate() = PublicWorkItemDelegate()
        };

        abstract fun delegate(): BaseDelegate<BaseModel>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val type = PublicWorkListState.values()[viewType]
        return type.delegate().onCreateViewHolder(
            LayoutInflater.from(parent.context),
            parent
        )
    }

    override fun getItemCount(): Int {
        return publicWorksList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = PublicWorkListState.values()[holder.itemViewType]
        type.delegate().onBindViewHolder(holder, publicWorksList[position])
    }

    override fun getItemViewType(position: Int): Int {
        val type =
            if (publicWorksList.isEmpty()) EMPTY_PUBLIC_WORK_LIST else DEFAULT_PUBLIC_WORK_LIST
        return type.ordinal
    }

    fun updatePublicWorksList(list: List<PublicWork>) {
        publicWorksList.clear()
        publicWorksList.addAll(list)
        notifyDataSetChanged()
    }
}