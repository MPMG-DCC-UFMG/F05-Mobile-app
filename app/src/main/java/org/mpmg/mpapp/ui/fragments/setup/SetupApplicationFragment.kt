package org.mpmg.mpapp.ui.fragments.setup

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.viewmodels.ConfigurationViewModel

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
        Handler().postDelayed({
            configurationViewModel.startConfigFilesDownload()
        }, 200)
    }

    private fun setupViewModels() {
        configurationViewModel.getStepsFinished().observe(viewLifecycleOwner, Observer { setup ->
            setup ?: return@Observer

            if (setup.all { true }) {
                navigateToList()
            }
        })
    }

    private fun navigateToList() {
        findNavController().navigate(R.id.action_setupApplicationFragment_to_baseFragment)
    }
}