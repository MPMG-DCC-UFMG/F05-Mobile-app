package org.mpmg.mpapp.ui.fragments.publicwork.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_public_work_send.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.viewmodels.SendViewModel

class PublicWorkSendFragment : Fragment() {

    private val TAG = PublicWorkSendFragment::class.java.name

    private val sendViewModel: SendViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_public_work_send, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        materialButton_sendFragment_send.setOnClickListener {
            sendViewModel.sendData()
        }
    }

}