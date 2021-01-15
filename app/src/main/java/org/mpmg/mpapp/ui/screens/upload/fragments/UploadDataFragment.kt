package org.mpmg.mpapp.ui.screens.upload.fragments

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentPublicWorkSendBinding
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.upload.adapters.UploadPublicWorkAdapter
import org.mpmg.mpapp.ui.screens.upload.viewmodels.SendViewModel

class UploadDataFragment : MVVMFragment<SendViewModel, FragmentPublicWorkSendBinding>() {

    private val TAG = UploadDataFragment::class.java.name

    private lateinit var uploadPublicWorkAdapter: UploadPublicWorkAdapter

    override val viewModel: SendViewModel by viewModel()
    override val layout: Int = R.layout.fragment_public_work_send

    override fun initViews() {
        viewModel.loadPublicWorksToSend()
        setupRecyclerView()
    }

    override fun initObservers() {
        observe(viewModel.publicWorksMediated) { publicWorkList ->

            uploadPublicWorkAdapter.updatePublicWorksList(publicWorkList)

            setupSendButtonVisibility(publicWorkList.size)
        }
    }

    override fun initListeners() {
        with(binding) {
            materialButtonSendFragmentSend.setOnClickListener {
                viewModel.sendData()
            }
        }
    }


    private fun setupRecyclerView() {
        uploadPublicWorkAdapter = UploadPublicWorkAdapter()
        with(binding) {
            recyclerViewSendFragmentList.adapter = uploadPublicWorkAdapter
            recyclerViewSendFragmentList.layoutManager = LinearLayoutManager(requireActivity())
        }
    }


    private fun setupSendButtonVisibility(listSize: Int) {
        with(binding) {
            materialButtonSendFragmentSend.visibility =
                if (listSize == 0) View.GONE else View.VISIBLE
        }
    }

}