package org.mpmg.mpapp.ui.screens.home.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.interfaces.BaseDelegate
import org.mpmg.mpapp.databinding.ItemHomeOptionBinding
import org.mpmg.mpapp.ui.screens.home.models.HomeOptions

class HomeOptionsDelegate : BaseDelegate<HomeOptions> {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding: ItemHomeOptionBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_home_option,
            parent,
            false
        )
        return HomeOptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, delegateObject: HomeOptions?) {
        with(holder as HomeOptionsViewHolder) {
            holder.bind(delegateObject)

            delegateObject?.let {
                optionIcon.setImageResource(delegateObject.iconResourceId)

                container.setOnClickListener {
                    delegateObject.onClick?.invoke()
                }
            }
        }
    }

    class HomeOptionsViewHolder(val binding: ItemHomeOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val container: CardView = binding.cardViewItemHomeOption
        val optionIcon: ImageView = binding.imageViewItemHomeOption

        fun bind(option: HomeOptions?) {
            binding.homeOption = option
            binding.executePendingBindings()
        }

    }
}