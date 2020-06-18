package org.mpmg.mpapp.ui.screens.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.mpmg.mpapp.ui.screens.home.delegates.HomeOptionsDelegate
import org.mpmg.mpapp.ui.screens.home.models.HomeOptions


class HomeOptionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val homeOptionsDelegate = HomeOptionsDelegate()

    private val optionsList = mutableListOf<HomeOptions>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return homeOptionsDelegate.onCreateViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        homeOptionsDelegate.onBindViewHolder(
            holder,
            optionsList[position]
        )
    }

    fun setOptions(options: List<HomeOptions>) {
        optionsList.clear()
        optionsList.addAll(options)
        notifyDataSetChanged()
    }
}