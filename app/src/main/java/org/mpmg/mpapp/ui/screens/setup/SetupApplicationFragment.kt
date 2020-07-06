package org.mpmg.mpapp.ui.screens.setup

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.work.Operation
import androidx.work.WorkInfo
import androidx.work.WorkInfo.State.*
import kotlinx.android.synthetic.main.fragment_setup_application.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.FragmentCollectMainBinding
import org.mpmg.mpapp.databinding.FragmentSetupApplicationBinding
import org.mpmg.mpapp.ui.viewmodels.ConfigurationViewModel
import org.mpmg.mpapp.workers.LoadServerDataWorker

class SetupApplicationFragment : Fragment() {

    private val TAG = SetupApplicationFragment::class.java.name

    private val configurationViewModel: ConfigurationViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSetupApplicationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setup_application, container, false)
        binding.configurationViewModel = configurationViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startFilesDownload()
        setupListeners()
    }

    private fun setupListeners() {
        materialButton_setupFragment_continue.setOnClickListener {
            navigateToList()
        }

        materialButton_setupFragment_tryAgain.setOnClickListener {
            startFilesDownload()
        }
    }

    private fun startFilesDownload() {
        configurationViewModel.progressMessage.value =
            getString(R.string.progress_starting_download_data)
        configurationViewModel.showTryAgain.value = false
        configurationViewModel.startConfigFilesDownload()
            .observe(viewLifecycleOwner, Observer { info ->
                info ?: return@Observer

                configurationViewModel.showTryAgain.value = false
                when (info.state) {
                    BLOCKED, ENQUEUED, RUNNING -> {
                        val message = info.progress.getString(LoadServerDataWorker.Message)
                        configurationViewModel.progressMessage.value = message
                    }
                    SUCCEEDED -> navigateToList()
                    FAILED, CANCELLED -> {
                        configurationViewModel.showTryAgain.value = true
                        configurationViewModel.progressMessage.value =
                            getString(R.string.progress_download_failed_try_again)
                    }
                }
            })
    }

    private fun navigateToList() {
        findNavController().navigate(R.id.action_setupApplicationFragment_to_homeFragment)
    }
}