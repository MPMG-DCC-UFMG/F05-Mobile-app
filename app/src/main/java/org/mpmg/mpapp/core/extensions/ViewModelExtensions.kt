package org.mpmg.mpapp.core.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, function: (T) -> Unit) {
    liveData.observe(this, androidx.lifecycle.Observer { function(it) })
}