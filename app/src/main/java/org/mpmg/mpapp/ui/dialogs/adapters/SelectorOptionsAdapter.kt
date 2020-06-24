package org.mpmg.mpapp.ui.dialogs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.mpmg.mpapp.ui.dialogs.delegates.SelectorOptionsDelegate
import org.mpmg.mpapp.ui.dialogs.models.SelectorOptions

class SelectorOptionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mOptions: MutableList<SelectorOptions> = mutableListOf()

    private val selectorOptionsDelegate = SelectorOptionsDelegate()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return selectorOptionsDelegate.onCreateViewHolder(
            LayoutInflater.from(parent.context),
            parent
        )
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        selectorOptionsDelegate.onBindViewHolder(
            holder,
            mOptions[position]
        )
    }

    fun setSelectorOptions(list: List<SelectorOptions>) {
        mOptions.clear()
        mOptions.addAll(list)
        notifyDataSetChanged()
    }
}