package org.mpmg.mpapp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_selector.*
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.DialogSelectorBinding
import org.mpmg.mpapp.ui.dialogs.adapters.SelectorOptionsAdapter
import org.mpmg.mpapp.ui.dialogs.models.SelectorOptions

class SelectorDialog(
    private val title: String?,
    private val options: List<String>,
    private val selectionMode: SelectionMode,
    private val onCloseClickListener: (() -> Unit)? = null,
    private val onNegativeClickListener: (() -> Unit)? = null,
    private val onPositiveClickListener: ((selectedIndex: List<Int>) -> Unit)? = null,
    private val selectedOptionListener: ((selectedIndex: List<Int>) -> Unit)? = null,
    private val previousSelectedOptions: Set<Int> = emptySet()
) :
    DialogFragment() {

    private lateinit var selectorOptionsAdapter: SelectorOptionsAdapter
    private lateinit var dialogOptions: List<SelectorOptions>
    private var selectedOptions = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.MPAppTheme_DialogStyle);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogSelectorBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_selector, container, false)
        binding.title = title
        binding.selectionMode = selectionMode
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        fillOptions()
        setupListeners()
    }

    private fun setupListeners() {
        imageView_singleSelectionDialog_close.setOnClickListener {
            onCloseClickListener?.invoke()
            dismiss()
        }
        materialButton_singleSelectionDialog_cancel.setOnClickListener {
            onNegativeClickListener?.invoke()
            dismiss()
        }
        materialButton_singleSelectionDialog_confirm.setOnClickListener {
            if(selectionMode == SelectionMode.MULTIPLE){
                handleMultipleSelectionMode()
            }
            onPositiveClickListener?.invoke(selectedOptions.toList())
            dismiss()
        }
    }

    private fun initRecyclerView() {
        selectorOptionsAdapter = SelectorOptionsAdapter()
        recyclerView_singleSelectionDialog.adapter = selectorOptionsAdapter
        recyclerView_singleSelectionDialog.layoutManager = LinearLayoutManager(activity)
    }

    private fun fillOptions() {
        dialogOptions = options.mapIndexed { index, s ->
            SelectorOptions(
                value = s,
                mSelected = previousSelectedOptions.contains(index),
                selectionMode = selectionMode
            ) {
                handlePositionClicked(it)
            }
        }
        if (dialogOptions.isNotEmpty()) {
            dialogOptions.last().showDivider = false
        }
        selectorOptionsAdapter.setSelectorOptions(dialogOptions)
    }

    private fun handlePositionClicked(position: Int) {
        when (selectionMode) {
            SelectionMode.SINGLE -> {
                handleSingleSelectionMode(position)
            }
            SelectionMode.MULTIPLE -> {
                handleMultipleSelectionMode()
            }
        }
    }

    private fun handleSingleSelectionMode(position: Int) {
        selectedOptions.add(position)
        selectedOptionListener?.invoke(selectedOptions.toList())
        dismiss()
    }

    private fun handleMultipleSelectionMode() {
        selectedOptions =
            dialogOptions.withIndex().filter { it.value.selected }.map { it.index }.toMutableSet()
    }

    enum class SelectionMode {
        SINGLE, MULTIPLE
    }

    class Builder(private var childFragmentManager: FragmentManager) {
        private var title: String? = null
        private var options: List<String> = emptyList()
        private var selectionMode: SelectionMode = SelectionMode.SINGLE
        private var selectedOptions: Set<Int> = emptySet()
        private var onCloseClickListener: (() -> Unit)? = null
        private var onNegativeClickListener: (() -> Unit)? = null
        private var onPositiveClickListener: ((selectedIndex: List<Int>) -> Unit)? = null
        private var selectedOptionListener: ((selectedIndex: List<Int>) -> Unit)? = null

        fun withTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun withOptions(options: List<String>): Builder {
            this.options = options
            return this
        }

        fun withSelectionMode(selectionMode: SelectionMode): Builder {
            this.selectionMode = selectionMode
            return this
        }

        fun withOnCloseClickListener(listener: (() -> Unit)): Builder {
            this.onCloseClickListener = listener
            return this
        }

        fun withOnNegativeClickListener(listener: (() -> Unit)): Builder {
            this.onNegativeClickListener = listener
            return this
        }

        fun withOnPositiveClickListener(listener: ((selectedIndex: List<Int>) -> Unit)): Builder {
            this.onPositiveClickListener = listener
            return this
        }

        fun withSelectedOptionListener(listener: ((selectedIndex: List<Int>) -> Unit)): Builder {
            this.selectedOptionListener = listener
            return this
        }

        fun withSelectedOptions(selectedIndex: List<Int>): Builder {
            this.selectedOptions = selectedIndex.toSet()
            return this
        }

        fun show() = SelectorDialog(
            title,
            options,
            selectionMode,
            onCloseClickListener,
            onNegativeClickListener,
            onPositiveClickListener,
            selectedOptionListener,
            selectedOptions
        ).show(
            childFragmentManager,
            SelectorDialog::class.simpleName
        )
    }
}