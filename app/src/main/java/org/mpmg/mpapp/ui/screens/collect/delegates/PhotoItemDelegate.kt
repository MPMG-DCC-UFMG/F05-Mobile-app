package org.mpmg.mpapp.ui.screens.collect.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.interfaces.BaseDelegate
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.databinding.ItemPhotoListBinding
import org.mpmg.mpapp.domain.database.models.Photo

class PhotoItemDelegate : BaseDelegate<BaseModel> {

    private var mPublicWorkItemDelegateListener: PhotoItemDelegateDelegateListener? = null

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding: ItemPhotoListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_photo_list, parent, false
        )
        return PhotoItemDelegateViewHolder(binding)
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

    class PhotoItemDelegateViewHolder(private val binding: ItemPhotoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mPhotoType = binding.textViewItemPhotoListPhotoType
        val mThumbnail = binding.imageViewItemPhotoListThumbnail
        val mContainer = binding.cardViewItemPhotoListMainContainer
    }
}