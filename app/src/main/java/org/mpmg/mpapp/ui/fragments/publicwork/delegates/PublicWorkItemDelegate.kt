package org.mpmg.mpapp.ui.fragments.publicwork.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_public_work_list.view.*
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.interfaces.BaseDelegate
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.domain.models.PublicWork

class PublicWorkItemDelegate : BaseDelegate<BaseModel> {

    private var mPublicWorkItemDelegateListener: PublicWorkItemDelegateListener? = null

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        return AreaItemViewHolder(
            inflater.inflate(
                R.layout.item_public_work_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, delegateObject: BaseModel) {
        delegateObject as PublicWork
        with(holder as AreaItemViewHolder) {
            mName.text = delegateObject.name

            mContainer.setOnClickListener {
                mPublicWorkItemDelegateListener?.onPublicWorkClicked(delegateObject.id)
            }
        }
    }

    fun setPublicWorkItemDelegateListener(listener: PublicWorkItemDelegateListener) {
        mPublicWorkItemDelegateListener = listener
    }

    interface PublicWorkItemDelegateListener {
        fun onPublicWorkClicked(publicWorkId: String)
    }

    class AreaItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mName = itemView.textView_itemPublicWorkList_name
        val mContainer = itemView.cardView_itemPublicWorkList_mainContainer
    }
}