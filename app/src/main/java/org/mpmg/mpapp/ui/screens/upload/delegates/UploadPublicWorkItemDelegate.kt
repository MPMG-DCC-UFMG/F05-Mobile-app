package org.mpmg.mpapp.ui.screens.upload.delegates

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import androidx.work.WorkInfo.State.*
import androidx.work.WorkManager
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.interfaces.BaseDelegate
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.databinding.ItemUploadPublicWorkBinding
import org.mpmg.mpapp.ui.screens.upload.models.PublicWorkUploadUI
import org.mpmg.mpapp.workers.PublicWorkUploadWorker

class UploadPublicWorkItemDelegate : BaseDelegate<BaseModel> {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding: ItemUploadPublicWorkBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_upload_public_work,
            parent,
            false
        )
        return UploadPublicWorkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, delegateObject: BaseModel?) {
        delegateObject as PublicWorkUploadUI
        with(holder as UploadPublicWorkViewHolder) {
            holder.bind(holder.itemView.context as LifecycleOwner, delegateObject)

            setupViewModels(holder, delegateObject)
        }
    }

    private fun setupViewModels(
        holder: UploadPublicWorkViewHolder,
        publicWork: PublicWorkUploadUI
    ) {
        val lifecycleOwner = holder.itemView.context as LifecycleOwner
        publicWork.workerInfoId.observe(lifecycleOwner, Observer { workerInfoId ->
            workerInfoId ?: return@Observer
            WorkManager.getInstance(holder.itemView.context).getWorkInfoByIdLiveData(workerInfoId)
                .observe(lifecycleOwner, Observer { workInfo ->
                    workInfo ?: return@Observer

                    val workData = workInfo.progress
                    publicWork.progress = workData.getInt(PublicWorkUploadWorker.Progress, 0)
                    publicWork.status = workData.getString(PublicWorkUploadWorker.Message)
                        ?: getMessageByState(holder.itemView.context, workInfo.state)
                    publicWork.workState = workInfo.state
                    setUploadTextColor(holder.uploadTextView, workInfo.state)
                })
        })
    }

    private fun getMessageByState(context: Context, workState: WorkInfo.State): String {
        return when (workState) {
            RUNNING -> context.resources.getString(R.string.progress_running_upload)
            SUCCEEDED -> context.resources.getString(R.string.progress_success_upload)
            BLOCKED, ENQUEUED, FAILED, CANCELLED -> context.resources.getString(R.string.progress_fail_upload)
        }
    }

    private fun setUploadTextColor(textView: TextView, workState: WorkInfo.State) {
        val colorResourceId = when (workState) {
            ENQUEUED, BLOCKED, CANCELLED, RUNNING -> R.color.colorWhite
            SUCCEEDED -> R.color.colorGreenMP
            FAILED -> R.color.colorRed
        }
        textView.setTextColor(ContextCompat.getColor(textView.context, colorResourceId))
    }

    class UploadPublicWorkViewHolder(val binding: ItemUploadPublicWorkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val uploadTextView: TextView = binding.textViewUploadItemPublicWorkName

        fun bind(lifecycleOwner: LifecycleOwner, publicWork: PublicWorkUploadUI) {
            binding.publicWork = publicWork
            binding.lifecycleOwner = lifecycleOwner
            binding.executePendingBindings()
        }
    }
}