package com.example.recetapp

import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.*
import com.example.recetapp.networking.NetworkStatusTracker
import com.example.recetapp.networking.UIResponseState
import com.example.recetapp.networking.map
import com.example.recetapp.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val networkStatusTracker: NetworkStatusTracker
): ViewModel() {

    val viewState: LiveData<UIResponseState> get() = _viewState
    private val _viewState: MutableLiveData<UIResponseState> = MutableLiveData()
    val internetState = networkStatusTracker.networkStatus.map(
        onUnavailable = { MyState.Error },
        onAvailable = { MyState.Fetched },
    ).asLiveData(Dispatchers.IO)

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

sealed class MyState {
    object Fetched : MyState()
    object Error : MyState()
}