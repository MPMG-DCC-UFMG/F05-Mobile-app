package org.mpmg.mpapp.ui.screens.setup.fragments


import android.os.Bundle
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentSetupApplicationBinding
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.setup.viewmodels.ConfigurationViewModel

class SetupApplicationFragment :
    MVVMFragment<ConfigurationViewModel, FragmentSetupApplicationBinding>() {

    private val TAG = SetupApplicationFragment::class.java.name

    override val viewModel: ConfigurationViewModel by viewModel()

    override val layout: Int = R.layout.fragment_setup_application

    override fun initBindings() {
        binding.configurationViewModel = viewModel
    }

    override fun initViews(savedInstanceState: Bundle?) {
        startFilesDownload()
    }

    override fun initObservers() {
        observe(viewModel.navigateToList) {
            navigateToList()
        }
    }

    override fun initListeners() {
        with(binding) {
            materialButtonSetupFragmentContinue.setOnClickListener {
                navigateToList()
            }

            materialButtonSetupFragmentTryAgain.setOnClickListener {
                startFilesDownload()
            }
        }
    }


    private fun startFilesDownload() {
        viewModel.startConfigFilesDownload(requireContext()).observe(viewLifecycleOwner) {
            it ?: return@observe

            viewModel.handleNewWorkInfo(requireContext(), it)
        }
    }

    private fun navigateToList() {
        findNavController().navigate(R.id.action_setupApplicationFragment_to_homeFragment)
    }
}