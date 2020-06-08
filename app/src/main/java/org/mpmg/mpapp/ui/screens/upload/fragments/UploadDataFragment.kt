package org.mpmg.mpapp.ui.screens.upload.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_public_work_send.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.screens.upload.adapters.UploadPublicWorkAdapter
import org.mpmg.mpapp.ui.viewmodels.SendViewModel

class UploadDataFragment : Fragment() {

    private val TAG = UploadDataFragment::class.java.name

    private val sendViewModel: SendViewModel by viewModel()

    private lateinit var uploadPublicWorkAdapter: UploadPublicWorkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_public_work_send, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendViewModel.loadPublicWorksToSend()
        setupRecyclerView()
        setupListeners()
        setupViewModels()
    }

    private fun setupRecyclerView() {
        initAdapter()
        recyclerView_sendFragment_list.adapter = uploadPublicWorkAdapter
        recyclerView_sendFragment_list.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun initAdapter() {
        uploadPublicWorkAdapter = UploadPublicWorkAdapter(sendViewModel)
    }

    private fun setupListeners() {
        materialButton_sendFragment_send.setOnClickListener {
            sendViewModel.sendData()
        }
    }

    private fun setupViewModels() {
        sendViewModel.getPublicWorkList()
            .observe(viewLifecycleOwner, Observer { publicWorkList ->
                publicWorkList ?: return@Observer

                uploadPublicWorkAdapter.updatePublicWorksList(publicWorkList)
            })
    }

}