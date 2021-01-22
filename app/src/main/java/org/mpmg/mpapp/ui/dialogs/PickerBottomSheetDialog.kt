package org.mpmg.mpapp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.BottomSheetPickerBinding


class PickerBottomSheetDialog(
    private val options: List<String>,
    private val confirmButtonText: String,
    private val negativeButtonText: String,
    private val onNegativeClickListener: (() -> Unit)? = null,
    private val onPositiveClickListener: ((selectedIndex: Int) -> Unit)? = null
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetPickerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_picker, container, false)
        binding.lifecycleOwner = this
        binding.confirmButtonText = confirmButtonText
        binding.negativeButtonText = negativeButtonText
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPicker()
        setupListeners()
    }

    private fun setupPicker() {
        with(binding.pickerBottomSheetPicker) {
            maxValue = options.size - 1
            minValue = 0
            displayedValues = options.toTypedArray()
        }

    }

    private fun setupListeners() {
        with(binding) {
            materialButtonBottomSheetPickerCancel.setOnClickListener {
                onNegativeClickListener?.invoke()
            }

            materialButtonBottomSheetPickerConfirm.setOnClickListener {
                val selectedIndex = pickerBottomSheetPicker.value
                onPositiveClickListener?.invoke(selectedIndex)
            }
        }

    }

    class Builder(private var childFragmentManager: FragmentManager) {

        private var confirmButtonText: String = ""
        private var negativeButtonText: String = ""
        private var onNegativeClickListener: (() -> Unit)? = null
        private var onPositiveClickListener: ((selectedIndex: Int) -> Unit)? = null
        private var options: List<String> = emptyList()


        fun withConfirmButtonText(text: String): Builder {
            this.confirmButtonText = text
            return this
        }

        fun withNegativeButtonText(text: String): Builder {
            this.negativeButtonText = text
            return this
        }

        fun withOnNegativeClickListener(listener: (() -> Unit)): Builder {
            this.onNegativeClickListener = listener
            return this
        }

        fun withOnPositiveClickListener(listener: ((selectedIndex: Int) -> Unit)): Builder {
            this.onPositiveClickListener = listener
            return this
        }

        fun withOptions(options: List<String>): Builder {
            this.options = options
            return this
        }

        fun show() = PickerBottomSheetDialog(
            options = options,
            confirmButtonText = confirmButtonText,
            negativeButtonText = negativeButtonText,
            onNegativeClickListener = onNegativeClickListener,
            onPositiveClickListener = onPositiveClickListener
        ).show(childFragmentManager, PickerBottomSheetDialog::class.simpleName)
    }
}