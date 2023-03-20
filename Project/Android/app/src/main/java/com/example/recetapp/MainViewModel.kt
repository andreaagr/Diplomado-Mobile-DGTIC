package com.example.recetapp

import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recetapp.networking.UIResponseState
import com.example.recetapp.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    val viewState: LiveData<UIResponseState> get() = _viewState
    private val _viewState: MutableLiveData<UIResponseState> = MutableLiveData()

    fun signIn(
        intentSenderRequest: ActivityResultLauncher<IntentSenderRequest>,
        doOnError: () -> Unit
    ) {
        viewModelScope.launch {
            authRepository.startOneTapClient(intentSenderRequest, doOnError)
        }
    }

    fun signInWithFirebase(activityResult: ActivityResult) {
        viewModelScope.launch {
            _viewState.postValue(authRepository.signInWithFirebase(activityResult))
        }
    }

    fun signOut() {
        authRepository.signOut()
    }

    fun isUserAuthenticated(): Boolean {
        return authRepository.isUserAuthenticated()
    }

    fun getCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }
}