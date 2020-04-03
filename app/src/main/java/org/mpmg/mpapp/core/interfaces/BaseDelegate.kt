package org.mpmg.mpapp.core.interfaces

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface BaseDelegate<T : BaseModel> {

    fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, delegateObject: T?)
}