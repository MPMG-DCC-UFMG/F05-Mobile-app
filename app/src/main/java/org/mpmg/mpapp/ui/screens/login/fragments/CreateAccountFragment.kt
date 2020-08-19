package org.mpmg.mpapp.ui.screens.login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_create_account.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.FragmentCreateAccountBinding
import org.mpmg.mpapp.ui.MainActivity
import org.mpmg.mpapp.ui.screens.login.models.CreateAccountUI
import org.mpmg.mpapp.ui.shared.models.RequestStatus
import org.mpmg.mpapp.ui.viewmodels.CreateAccountViewModel
import org.mpmg.mpapp.ui.viewmodels.LoginViewModel

class CreateAccountFragment : Fragment() {

    private val TAG = CreateAccountFragment::class.java.name

    private val createAccountViewModel: CreateAccountViewModel by viewModel()
    private lateinit var createAccountUI: CreateAccountUI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createAccountUI = CreateAccountUI(context = requireContext())
        val binding: FragmentCreateAccountBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_account, container, false)
        binding.lifecycleOwner = this
        binding.createAccount = createAccountUI
        binding.createAccountViewModel = createAccountViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModels()
        setupListeners()
    }

    private fun setupViewModels() {
        createAccountViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status ?: return@Observer
            if (status == RequestStatus.SUCCESS) {
                showSnackbar(getString(R.string.message_user_created))
                findNavController().navigateUp()
            } else if (status == RequestStatus.FAILED) {
                showSnackbar(getString(R.string.message_user_creation_failed))
            }
        })
    }

    private fun setupListeners() {
        materialButton_createAccountFragment_createAccount.setOnClickListener {
            handleCreateUser()
        }
    }

    private fun handleCreateUser() {
        createAccountViewModel.createUser(createAccountUI)
    }

    private fun showSnackbar(message: String) {
        val parentActivity = this.requireActivity()
        if (parentActivity is MainActivity) {
            parentActivity.launchSnackbar(message)
        }
    }
}