package org.mpmg.mpapp.ui.screens.login.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.FragmentLoginBinding
import org.mpmg.mpapp.helpers.TextHelpers
import org.mpmg.mpapp.ui.MainActivity
import org.mpmg.mpapp.ui.screens.login.fragments.LoginFragment.SignWith.*
import org.mpmg.mpapp.ui.screens.login.models.LoginUI
import org.mpmg.mpapp.ui.shared.models.RequestStatus
import org.mpmg.mpapp.ui.viewmodels.LoginViewModel


class LoginFragment : Fragment() {

    private val TAG = LoginFragment::class.java.name

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginUI: LoginUI

    private val RC_GOOGLE_SIGN_IN = 601

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginUI = LoginUI()
        firebaseAuth = FirebaseAuth.getInstance()
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.loginViewModel = loginViewModel
        binding.loginUI = loginUI
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userSigned = loginViewModel.checkUserLogged()
        if (userSigned) {
            navigateSetupAppFragment()
        }

        setupListeners()
        setupFacebookLogin()
        setupTexts()
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
        textView_loginFragment_createAccount.text = spannableString
    }


    private fun setupListeners() {
        button_loginFragment_googleSignIn.setOnClickListener {
            handleButtonSingInClick(GOOGLE)
        }

        button_loginFragment_twitterSignIn.setOnClickListener {
            handleButtonSingInClick(TWITTER)
        }

        button_loginFragment_facebookSignIn.setOnClickListener {
            handleButtonSingInClick(FACEBOOK)
        }

        textView_loginFragment_createAccount.setOnClickListener {
            navigateCreateAccountFragment()
        }

        materialButton_loginFragment_loginMP.setOnClickListener {
            handleButtonSingInClick(MPSERVER)
        }
    }

    private fun handleButtonSingInClick(siginWith: SignWith) {
        loginViewModel.isLoading.value = true
        when (siginWith) {
            GOOGLE -> signInGoogle()
            FACEBOOK -> signInFacebook()
            TWITTER -> signInTwitter()
            MPSERVER -> singInMPServer()
        }
    }

    private fun singInMPServer() {
        loginViewModel.isLoading.value = true
        loginViewModel.authWithMPServer(loginUI.email, loginUI.password).observe(viewLifecycleOwner,
            Observer {
                it ?: return@Observer

                if (it == RequestStatus.SUCCESS) {
                    loginViewModel.isLoading.value = false
                    storeUser(loginUI.email, loginUI.email)
                } else if (it == RequestStatus.FAILED) {
                    loginViewModel.isLoading.value = false
                    launchSnackbar(getString(R.string.message_fail_to_authenticate))
                }
            })
    }

    private fun setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    handleFacebookSignIn(loginResult.accessToken)
                }

                override fun onCancel() {
                    loginViewModel.isLoading.value = false
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(exception: FacebookException) {
                    loginViewModel.isLoading.value = false
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
                    loginViewModel.isLoading.value = false
                    val userName = it.user?.displayName ?: throw NullPointerException()
                    val email = it.user?.email ?: ""
                    storeUser(userEmail = email, userName = userName)
                }
                .addOnFailureListener { e ->
                    loginViewModel.isLoading.value = false
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
        loginViewModel.isLoading.value = false
        if (task.isSuccessful) {
            val user = firebaseAuth.currentUser ?: throw NullPointerException()
            storeUser(
                user.email ?: throw NullPointerException(),
                user.displayName ?: throw NullPointerException()
            )
        } else {
            launchSnackbar(getString(R.string.message_fail_to_authenticate))
        }
    }

    private fun launchSnackbar(message: String) {
        val parentActivity = this.requireActivity()
        if (parentActivity is MainActivity) {
            parentActivity.launchSnackbar(message)
        }
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
            loginViewModel.isLoading.value = false
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
            loginViewModel.isLoading.value = false
            Log.w(TAG, e)
        }
    }

    private fun storeUser(userEmail: String, userName: String) {
        loginViewModel.logIn(userEmail)
        loginViewModel.addUserToDb(userName, userEmail)
        navigateSetupAppFragment()
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