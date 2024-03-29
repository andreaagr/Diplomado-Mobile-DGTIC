package com.example.recetapp.repository

import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.example.recetapp.networking.UIResponseState
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val oneTapClient: SignInClient,
    private val signInRequest: BeginSignInRequest,
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun startOneTapClient(
        intentSenderRequestActivityResultLauncher: ActivityResultLauncher<IntentSenderRequest>,
        doOnError: () -> Unit
    ) {
        try {
            val result = oneTapClient.beginSignIn(signInRequest).await()
            IntentSenderRequest.Builder(result.pendingIntent).build().also { intentSenderRequest ->
                intentSenderRequestActivityResultLauncher.launch(
                    intentSenderRequest
                )
            }
        } catch (e: Exception) {
            doOnError()
        }
    }

    override suspend fun signInWithFirebase(activityResult: ActivityResult): UIResponseState {
        val googleCredential = oneTapClient.getSignInCredentialFromIntent(activityResult.data)
        val idToken = googleCredential.googleIdToken
        if (idToken != null) {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            return try {
                firebaseAuth.signInWithCredential(firebaseCredential).await()
                UIResponseState.Success(firebaseAuth.currentUser)
            } catch (e: Exception) {
                UIResponseState.Error(e.message.toString())
            }
        }
        return UIResponseState.Error("Failed to log in, please try again later")
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun isUserAuthenticated() = firebaseAuth.currentUser != null

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}