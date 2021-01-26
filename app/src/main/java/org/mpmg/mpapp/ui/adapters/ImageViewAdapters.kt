package org.mpmg.mpapp.ui.adapters

import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter


@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("mp:tint")
fun setImageViewTint(imageView: ImageView, resource: Int) {
    imageView.setColorFilter(ContextCompat.getColor(imageView.context, resource), PorterDuff.Mode.SRC_ATOP);
}