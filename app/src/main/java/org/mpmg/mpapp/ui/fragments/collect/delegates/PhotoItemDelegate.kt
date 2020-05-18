package org.mpmg.mpapp.ui.fragments.collect.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_photo_list.view.*
import kotlinx.android.synthetic.main.item_public_work_list.view.*
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.interfaces.BaseDelegate
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.database.models.PublicWork
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAdress

class PhotoItemDelegate : BaseDelegate<BaseModel> {

    private var mPublicWorkItemDelegateListener: PhotoItemDelegateDelegateListener? = null

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        return PhotoItemDelegateViewHolder(
            inflater.inflate(
                R.layout.item_photo_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, delegateObject: BaseModel?) {
        delegateObject as Photo
        with(holder as PhotoItemDelegateViewHolder) {
            mPhotoType.text = delegateObject.type ?: ""

            val filepath = delegateObject.filepath
            filepath?.let {
                Glide.with(mThumbnail).load(it).into(mThumbnail)
            }

            mContainer.setOnClickListener {
                mPublicWorkItemDelegateListener?.onPhotoClicked(delegateObject.id)
            }
        }
    }

    fun setPhotoItemDelegateListener(listener: PhotoItemDelegateDelegateListener) {
        mPublicWorkItemDelegateListener = listener
    }

    interface PhotoItemDelegateDelegateListener {
        fun onPhotoClicked(photoId: String)
    }

    class PhotoItemDelegateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mPhotoType = itemView.textView_itemPhotoList_photoType
        val mThumbnail = itemView.imageView_itemPhotoList_thumbnail
        val mContainer = itemView.cardView_itemPhotoList_mainContainer
    }
}