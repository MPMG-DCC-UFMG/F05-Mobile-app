package org.mpmg.mpapp.ui.shared.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.mpmg.mpapp.core.interfaces.BaseDelegate
import org.mpmg.mpapp.core.interfaces.BaseModel

class StatusAdapterDelegate(val layout: Int) : BaseDelegate<BaseModel> {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        return StatusViewHolder(
            inflater.inflate(
                layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, delegateObject: BaseModel) {
        //ignore
    }

    data class StatusViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView)
}