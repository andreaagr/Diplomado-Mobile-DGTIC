package com.example.recetapp.repository

import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.example.recetapp.ui.UIResponseState
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun startOneTapClient(
        intentSenderRequestActivityResultLauncher: ActivityResultLauncher<IntentSenderRequest>,
        doOnError: () -> Unit
    )

    suspend fun signInWithFirebase(activityResult: ActivityResult): UIResponseState

    fun signOut()

    fun isUserAuthenticated(): Boolean

    fun getCurrentUser(): FirebaseUser?
}