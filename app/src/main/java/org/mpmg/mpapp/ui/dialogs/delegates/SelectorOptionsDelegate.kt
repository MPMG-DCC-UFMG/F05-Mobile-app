package org.mpmg.mpapp.ui.dialogs.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.interfaces.BaseDelegate
import org.mpmg.mpapp.databinding.ItemDialogSelectorOptionBinding
import org.mpmg.mpapp.ui.dialogs.models.SelectorOptions

class SelectorOptionsDelegate : BaseDelegate<SelectorOptions> {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding: ItemDialogSelectorOptionBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_dialog_selector_option,
            parent,
            false
        )
        return SelectorOptionsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        delegateObject: SelectorOptions?
    ) {
        with(holder as SelectorOptionsViewHolder) {
            holder.bind(delegateObject)

            container.setOnClickListener {
                holder.checkbox.toggle()
                delegateObject?.onClick?.invoke(adapterPosition)
            }
        }
    }

    class SelectorOptionsViewHolder(val binding: ItemDialogSelectorOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val container: ConstraintLayout = binding.constraintLayoutSelectorOptionDialog
        val checkbox: CheckBox = binding.checkBoxSelectorOptionDialog

        fun bind(option: SelectorOptions?) {
            binding.selectorOption = option
            binding.executePendingBindings()
        }

    }
}