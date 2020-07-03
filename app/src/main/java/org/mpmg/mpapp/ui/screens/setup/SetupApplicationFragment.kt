package org.mpmg.mpapp.ui.screens.setup

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.work.Operation
import androidx.work.WorkInfo
import kotlinx.android.synthetic.main.fragment_setup_application.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
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
        return inflater.inflate(R.layout.fragment_setup_application, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModels()
    }

    private fun setupViewModels() {
        configurationViewModel.startConfigFilesDownload()
            .observe(viewLifecycleOwner, Observer { info ->
                info ?: return@Observer

                if (info.state == WorkInfo.State.SUCCEEDED) {
                    navigateToList()
                } else {
                    val message = info.progress.getString(LoadServerDataWorker.Message)
                    textView_setupFragment.text = message ?: "Carregando ..."
                }
            })
    }

    private fun navigateToList() {
        findNavController().navigate(R.id.action_setupApplicationFragment_to_homeFragment)
    }
}