package org.mpmg.mpapp.ui.screens.collect.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.mpmg.mpapp.R
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.ui.screens.collect.delegates.PhotoItemDelegate
import org.mpmg.mpapp.ui.shared.delegates.StatusAdapterDelegate

class PhotoListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    PhotoItemDelegate.PhotoItemDelegateDelegateListener {

    private val photoList = mutableListOf<Photo>()

    private var mListener: PhotoListAdapterListener? = null

    private val delegates = listOf(
        StatusAdapterDelegate(R.layout.item_empty_collect_list),
        PhotoItemDelegate()
    )

    init {
        setupListeners()
    }

    private fun setupListeners() {
        val photoItemDelegate = delegates[1] as PhotoItemDelegate
        photoItemDelegate.setPhotoItemDelegateListener(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegate = delegates[viewType]
        return delegate.onCreateViewHolder(
            LayoutInflater.from(parent.context),
            parent
        )
    }

    override fun getItemCount(): Int {
        return if (photoList.isEmpty()) 1 else photoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val delegate = delegates[holder.itemViewType]
        when (holder.itemViewType) {
            0 -> delegate.onBindViewHolder(holder, null)
            else -> delegate.onBindViewHolder(holder, photoList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (photoList.isEmpty()) 0 else 1
    }

    fun updatePhotoList(list: List<Photo>) {
        photoList.clear()
        photoList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onPhotoClicked(photoId: String) {
        val photo = photoList.find { it.id == photoId } ?: return
        mListener?.onPhotoClicked(photo)
    }

    fun setPhotoListAdapterListener(listener: PhotoListAdapterListener) {
        mListener = listener
    }

    interface PhotoListAdapterListener {
        fun onPhotoClicked(photo: Photo)
    }
}