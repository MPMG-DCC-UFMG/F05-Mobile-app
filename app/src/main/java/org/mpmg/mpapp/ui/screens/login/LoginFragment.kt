package org.mpmg.mpapp.ui.screens.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.Constants.GOOGLE_SIGIN_CLIENT_ID
import org.mpmg.mpapp.ui.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    private val TAG = LoginFragment::class.java.name

    private val loginViewModel: LoginViewModel by sharedViewModel()

    private val RC_GOOGLE_SIGN_IN = 601

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userSigned = loginViewModel.checkUserLogged()
        if (userSigned) {
            navigateSetupAppFragment()
        }

        setupListeners()
    }

    private fun setupListeners() {
        button_loginFragment_googleSignIn.setOnClickListener {
            signInGoogle()
        }

        button_loginFragment_twitterSignIn.setOnClickListener {
            signInTwitter()
        }
    }

    private fun signInGoogle() {
        context?.let {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(GOOGLE_SIGIN_CLIENT_ID)
                    .requestEmail()
                    .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(it, gso)

            val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
        }
    }

    private fun signInTwitter() {
        context?.let {
            try {
                val provider = OAuthProvider.newBuilder("twitter.com")
                val firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.startActivityForSignInWithProvider(requireActivity(), provider.build())
                    .addOnSuccessListener {
                        val userName = it.user?.displayName ?: throw NullPointerException()
                        val email = it.user?.email ?: ""
                        storeUser(userEmail = email, userName = userName)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "signInResult to twitter:failed " + e.message)
                    }
            } catch (e: Exception) {
                Log.w(TAG, "signInResult:failed code=" + e.message)
            }

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
        }
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java) ?: throw NullPointerException()
            val email = account.email ?: throw NullPointerException()
            storeUser(email, account.displayName ?: throw NullPointerException())
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
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
}