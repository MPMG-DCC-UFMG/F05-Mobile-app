package org.mpmg.mpapp.ui.screens.login.fragments

import android.content.Intent
import android.text.SpannableStringBuilder
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentLoginBinding
import org.mpmg.mpapp.helpers.TextHelpers
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.login.fragments.LoginFragment.SignWith.*
import org.mpmg.mpapp.ui.screens.login.viewmodels.LoginViewModel


class LoginFragment : MVVMFragment<LoginViewModel, FragmentLoginBinding>() {

    private val TAG = LoginFragment::class.java.name

    companion object {
        const val RC_GOOGLE_SIGN_IN = 601
    }

    override val viewModel: LoginViewModel by viewModel()
    override val layout: Int = R.layout.fragment_login

    override fun initBindings() {
        binding.loginViewModel = viewModel
        binding.loginUI = viewModel.loginUI
    }

    override fun initViews() {
        setupTexts()
        viewModel.setupFacebookLogin(requireActivity())
    }

    override fun initListeners() {
        with(binding) {
            buttonLoginFragmentGoogleSignIn.setOnClickListener {
                handleButtonSingInClick(GOOGLE)
            }

            buttonLoginFragmentTwitterSignIn.setOnClickListener {
                handleButtonSingInClick(TWITTER)
            }

            buttonLoginFragmentFacebookSignIn.setOnClickListener {
                handleButtonSingInClick(FACEBOOK)
            }

            textViewLoginFragmentCreateAccount.setOnClickListener {
                navigateCreateAccountFragment()
            }

            materialButtonLoginFragmentLoginMP.setOnClickListener {
                handleButtonSingInClick(MPSERVER)
            }
        }

    }

    private fun setupTexts() {
        val spannableString = SpannableStringBuilder()
        spannableString.append(getString(R.string.text_create_account))
        TextHelpers.applyColorPattern(
            spannableString,
            0,
            getString(R.string.span_create_account),
            requireActivity().getColor(R.color.colorGreenMP)
        )
        with(binding) {
            textViewLoginFragmentCreateAccount.text = spannableString
        }
    }

    override fun initObservers() {
        observe(viewModel.navigateToSetup) {
            navigateSetupAppFragment()
        }
    }

    private fun handleButtonSingInClick(siginWith: SignWith) {
        when (siginWith) {
            GOOGLE -> signInGoogle()
            FACEBOOK -> signInFacebook()
            TWITTER -> signInTwitter()
            MPSERVER -> singInMPServer()
        }
    }

    private fun singInMPServer() {
        viewModel.singInMPServer()
    }

    private fun signInGoogle() {
        viewModel.signInGoogle(requireActivity())
    }

    private fun signInFacebook() {
        viewModel.signInFacebook(requireActivity())
    }

    private fun signInTwitter() {
        viewModel.signInTwitter(requireActivity())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_GOOGLE_SIGN_IN -> {
                data ?: return
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                viewModel.handleGoogleSignInResult(task, requireActivity())
            }
            else -> {
                viewModel.callbackManager.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun navigateSetupAppFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_setupApplicationFragment)
    }

    private fun navigateCreateAccountFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment)
    }

    enum class SignWith {
        GOOGLE, FACEBOOK, TWITTER, MPSERVER
    }
}