package org.mpmg.mpapp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.dialog_warning.*
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.DialogWarningBinding

class WarningDialog(
    private val title: String?,
    private val message: String,
    private val onCloseClickListener: (() -> Unit)? = null,
    private val onNegativeClickListener: (() -> Unit)? = null,
    private val onPositiveClickListener: (() -> Unit)? = null
) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.MPAppTheme_DialogStyle);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogWarningBinding = DataBindingUtil.inflate(inflater,R.layout.dialog_warning,container,false)
        binding.title = title
        binding.message = message
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        imageView_warningDialog_close.setOnClickListener {
            onCloseClickListener?.invoke()
            dismiss()
        }
        materialButton_warningDialog_cancel.setOnClickListener {
            onNegativeClickListener?.invoke()
            dismiss()
        }
        materialButton_warningDialog_confirm.setOnClickListener {
            onPositiveClickListener?.invoke()
            dismiss()
        }
    }

    class Builder(private var childFragmentManager: FragmentManager){
        private var title: String? = null
        private var message: String = ""
        private var onCloseClickListener: (() -> Unit)? = null
        private var onNegativeClickListener: (() -> Unit)? = null
        private var onPositiveClickListener: (() -> Unit)? = null

        fun withTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun withMessage(message: String): Builder {
            this.message = message
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

        fun withOnPositiveClickListener(listener: (() -> Unit)): Builder {
            this.onPositiveClickListener = listener
            return this
        }

        fun show() = WarningDialog(
            title,
            message,
            onCloseClickListener,
            onNegativeClickListener,
            onPositiveClickListener
        ).show(
            childFragmentManager,
            WarningDialog::class.simpleName
        )
    }
}