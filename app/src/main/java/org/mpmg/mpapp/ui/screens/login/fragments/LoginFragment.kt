package org.mpmg.mpapp.ui.screens.login.fragments

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentLoginBinding
import org.mpmg.mpapp.helpers.TextHelpers
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.login.fragments.LoginFragment.SignWith.*
import org.mpmg.mpapp.ui.screens.login.models.LoginUI
import org.mpmg.mpapp.ui.screens.login.viewmodels.LoginViewModel


class LoginFragment : MVVMFragment<LoginViewModel, FragmentLoginBinding>() {

    private val TAG = LoginFragment::class.java.name

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginUI: LoginUI

    override val viewModel: LoginViewModel by viewModel()
    override val layout: Int = R.layout.fragment_login

    companion object {
        const val RC_GOOGLE_SIGN_IN = 601
    }

    override fun initBindings() {
        super.initBindings()
        firebaseAuth = FirebaseAuth.getInstance()
        binding.loginViewModel = viewModel
        binding.loginUI = viewModel.loginUI
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setupListeners()
        setupFacebookLogin()
        setupTexts()
    }

    override fun initObservers() {
        observe(viewModel.navigateToSetup) {
            navigateSetupAppFragment()
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


    private fun setupListeners() {
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

    private fun handleButtonSingInClick(siginWith: SignWith) {
        viewModel.isLoading.value = true
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

    private fun setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    handleFacebookSignIn(loginResult.accessToken)
                }

                override fun onCancel() {
                    viewModel.isLoading.value = false
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(exception: FacebookException) {
                    viewModel.isLoading.value = false
                    Log.d(TAG, "facebook:onError", exception)
                }
            }
        )
    }

    private fun signInGoogle() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_sign_in))
                .requestEmail()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }

    private fun signInFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(
            this,
            arrayListOf("email", "user_birthday", "public_profile")
        )
    }

    private fun signInTwitter() {
        try {
            val provider = OAuthProvider.newBuilder("twitter.com")
            firebaseAuth.startActivityForSignInWithProvider(requireActivity(), provider.build())
                .addOnSuccessListener {
                    viewModel.isLoading.value = false
                    val userName = it.user?.displayName ?: throw NullPointerException()
                    val email = it.user?.email ?: ""
                    viewModel.storeUser(userEmail = email, userName = userName)
                }
                .addOnFailureListener { e ->
                    viewModel.isLoading.value = false
                    Log.w(TAG, "signInResult to twitter:failed " + e.message)
                }
        } catch (e: Exception) {
            Log.w(TAG, e)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_GOOGLE_SIGN_IN -> {
                data ?: return
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleGoogleSignInResult(task)
            }
            else -> {
                callbackManager.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun handleSigIn(task: Task<AuthResult>) {
        viewModel.handleSigIn(task, requireActivity())
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java) ?: return

            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity()) { task ->
                    handleSigIn(task)
                }
        } catch (e: ApiException) {
            viewModel.isLoading.value = false
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun handleFacebookSignIn(token: AccessToken) {
        try {
            val credential = FacebookAuthProvider.getCredential(token.token)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity()) { task ->
                    handleSigIn(task)
                }
        } catch (e: java.lang.Exception) {
            viewModel.isLoading.value = false
            Log.w(TAG, e)
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