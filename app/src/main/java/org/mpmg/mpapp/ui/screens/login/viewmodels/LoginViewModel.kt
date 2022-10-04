package org.mpmg.mpapp.ui.screens.login.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.events.SingleLiveEvent
import org.mpmg.mpapp.domain.database.dao.UserDAO
import org.mpmg.mpapp.domain.database.models.User
import org.mpmg.mpapp.domain.repositories.config.ConfigRepository
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource
import org.mpmg.mpapp.domain.repositories.user.UserRepository
import org.mpmg.mpapp.ui.screens.base.MVVMViewModel
import org.mpmg.mpapp.ui.screens.login.fragments.LoginFragment.Companion.RC_GOOGLE_SIGN_IN
import org.mpmg.mpapp.ui.screens.login.models.LoginUI
import org.mpmg.mpapp.ui.shared.models.RequestStatus
import java.io.File
import java.io.FileWriter


class LoginViewModel(
    private val userRepository: UserRepository,
    private val configRepository: ConfigRepository,
    //private val userDAO: UserDAO
) : MVVMViewModel() {

    private val TAG = LoginViewModel::class.java.canonicalName
    private val firebaseAuth = FirebaseAuth.getInstance()

    val isLoading = SingleLiveEvent<Boolean>()
    val loginUI = LoginUI(_email = "joao@email.com", _password = "85201145", _valid = true)
    val navigateToSetup = SingleLiveEvent<Boolean>()


    lateinit var callbackManager: CallbackManager

    init {
        isLoading.value = false
        if (checkUserLogged()) {
            navigateToSetup.postValue(true)
        }
    }

    fun checkUserLogged(): Boolean = checkFirebaseSignedAccount()

    private fun checkFirebaseSignedAccount(): Boolean {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser

        return firebaseUser?.email?.let {
            logIn(it)
            true
        } ?: false
    }

   /* private fun checkLocalSignedAccount(): Boolean {
        val user = userDAO.listAllUsers()[1]

        return user?.email?.let {
            logIn(it)
            true
        } ?: false
    }*/

    fun addUserToDb(userName: String, userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertUser(
                User(
                    name = userName,
                    email = userEmail
                )
            )
        }
    }

    fun authWithMPServer(userName: String, password: String) {
        isLoading.postValue(true)
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.login(userName, password)
            }.onSuccess {
                isLoading.postValue(false)
                navigateToSetup.postValue(true)
            }.onFailure {
                isLoading.postValue(false)
            }
        }
    }

    fun logIn(userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            configRepository.setLoggedUserEmail(userEmail)

        }
    }

    fun signInTwitter(activity: Activity) {
        isLoading.postValue(true)
        try {
            val provider = OAuthProvider.newBuilder("twitter.com")
            firebaseAuth.startActivityForSignInWithProvider(activity, provider.build())
                .addOnSuccessListener {
                    isLoading.postValue(false)
                    val userName = it.user?.displayName ?: throw NullPointerException()
                    val email = it.user?.email ?: ""
                    storeUser(userEmail = email, userName = userName)
                }
                .addOnFailureListener { e ->
                    isLoading.postValue(false)
                    Log.w(TAG, "signInResult to twitter:failed " + e.message)
                }
        } catch (e: Exception) {
            Log.w(TAG, e)
        }
    }

    fun singInMPServer() {
        viewModelScope.launch(Dispatchers.IO) {
            authWithMPServer(loginUI.email, loginUI.password)
//            val file = File( "storage/emulated/0/", "${loginUI.email}.txt")
//            FileWriter(file)
        }
    }

    fun signInFacebook(activity: Activity) {
        isLoading.postValue(true)
        LoginManager.getInstance().logInWithReadPermissions(
            activity,
            arrayListOf("email", "user_birthday", "public_profile")
        )
    }

    fun signInGoogle(activity: Activity) {
        isLoading.postValue(true)
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.google_sign_in))
                .requestEmail()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)

        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }

    fun handleSigIn(task: Task<AuthResult>, activity: Activity) {
        isLoading.postValue(false)
        if (task.isSuccessful) {
            val user = firebaseAuth.currentUser ?: throw NullPointerException()
            storeUser(
                user.email ?: throw NullPointerException(),
                user.displayName ?: throw NullPointerException()
            )
        } else {
            launchSnackbar(activity.getString(R.string.message_fail_to_authenticate))
        }
    }

    fun handleGoogleSignInResult(
        completedTask: Task<GoogleSignInAccount>,
        activity: Activity
    ) {
        try {
            val account = completedTask.getResult(ApiException::class.java) ?: return

            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    handleSigIn(task, activity)
                }
        } catch (e: ApiException) {
            isLoading.postValue(false)
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    fun handleFacebookSignIn(token: AccessToken, activity: Activity) {
        try {
            val credential = FacebookAuthProvider.getCredential(token.token)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    handleSigIn(task, activity)
                }
        } catch (e: java.lang.Exception) {
            isLoading.postValue(false)
            Log.w(TAG, e)
        }
    }

    fun setupFacebookLogin(activity: Activity) {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    handleFacebookSignIn(loginResult.accessToken, activity)
                }

                override fun onCancel() {
                    isLoading.postValue(false)
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(exception: FacebookException) {
                    isLoading.postValue(false)
                    Log.d(TAG, "facebook:onError", exception)
                }
            }
        )
    }

    fun storeUser(userEmail: String, userName: String) {
        logIn(userEmail)
        addUserToDb(userName, userEmail)

        navigateToSetup.postValue(true)
    }
}