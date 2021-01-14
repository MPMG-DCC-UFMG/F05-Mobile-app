package org.mpmg.mpapp.ui.screens.login.fragments

import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentCreateAccountBinding
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.login.viewmodels.CreateAccountViewModel

class CreateAccountFragment : MVVMFragment<CreateAccountViewModel, FragmentCreateAccountBinding>() {

    private val TAG = CreateAccountFragment::class.java.name

    override val viewModel: CreateAccountViewModel by viewModel()
    override val layout: Int = R.layout.fragment_create_account

    override fun initBindings() {
        viewModel.startAccountUI(requireContext())
        binding.createAccount = viewModel.createAccountUI
        binding.createAccountViewModel = viewModel
    }

    override fun initObservers() {
        observe(viewModel.userCreated) {
            if (it) {
                findNavController().navigateUp()
            }
        }
    }

    override fun initListeners() {
        with(binding) {
            materialButtonCreateAccountFragmentCreateAccount.setOnClickListener {
                handleCreateUser()
            }
        }
    }

    private fun handleCreateUser() {
        viewModel.createUser()
    }
}