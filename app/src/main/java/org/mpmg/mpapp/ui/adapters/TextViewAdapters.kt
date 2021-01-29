package org.mpmg.mpapp.ui.adapters

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("mp:textColor")
fun setTextViewColor(textView: TextView, resource: Int) {
    textView.setTextColor(ContextCompat.getColor(textView.context, resource))
}