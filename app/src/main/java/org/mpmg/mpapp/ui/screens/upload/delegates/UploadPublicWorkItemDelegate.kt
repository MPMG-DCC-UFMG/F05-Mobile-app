package org.mpmg.mpapp.ui.screens.upload.delegates

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkManager
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.interfaces.BaseDelegate
import org.mpmg.mpapp.core.interfaces.BaseModel
import org.mpmg.mpapp.databinding.ItemUploadPublicWorkBinding
import org.mpmg.mpapp.ui.screens.upload.models.PublicWorkUploadUI
import org.mpmg.mpapp.ui.viewmodels.SendViewModel
import org.mpmg.mpapp.workers.PublicWorkUpload

class UploadPublicWorkItemDelegate(sendViewModel: SendViewModel) : BaseDelegate<BaseModel> {

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

            setupViewModels(holder.itemView.context, delegateObject)
        }
    }

    private fun setupViewModels(context: Context, publicWork: PublicWorkUploadUI) {
        val lifecycleOwner = context as LifecycleOwner
        publicWork.workerInfoId.observe(lifecycleOwner, Observer { workerInfoId ->
            workerInfoId ?: return@Observer
            WorkManager.getInstance(context).getWorkInfoByIdLiveData(workerInfoId)
                .observe(lifecycleOwner, Observer { workInfo ->
                    workInfo ?: return@Observer

                    val workData = workInfo.progress
                    publicWork.progress = workData.getInt(PublicWorkUpload.Progress, 0)
                    publicWork.status = workData.getString(PublicWorkUpload.Message) ?: "Enviando"
                    publicWork.workState = workInfo.state
                })
        })
    }

    class UploadPublicWorkViewHolder(val binding: ItemUploadPublicWorkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lifecycleOwner: LifecycleOwner, publicWork: PublicWorkUploadUI) {
            binding.publicWork = publicWork
            binding.lifecycleOwner = lifecycleOwner
            binding.executePendingBindings()
        }
    }
}