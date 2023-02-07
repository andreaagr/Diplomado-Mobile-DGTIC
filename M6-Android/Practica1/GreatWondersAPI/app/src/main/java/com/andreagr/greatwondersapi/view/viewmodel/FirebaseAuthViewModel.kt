package com.andreagr.greatwondersapi.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreagr.greatwondersapi.ResourceManager
import com.andreagr.greatwondersapi.util.UIResponseState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FirebaseAuthViewModel @Inject constructor(
    private val resourceManager: ResourceManager
): ViewModel() {
    val viewState: LiveData<UIResponseState> get() = _viewState
    private val _viewState: MutableLiveData<UIResponseState> = MutableLiveData()
    private val auth by lazy { FirebaseAuth.getInstance() }

    fun getCurrentUser() = auth.currentUser

    fun createAccount(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.postValue(UIResponseState.Loading)
            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (password.count() < 7) {
                    _viewState.postValue(UIResponseState.Error(resourceManager.badPasswordError))
                } else {
                    try {
                        val data = auth.createUserWithEmailAndPassword(email, password).await()
                        _viewState.postValue(UIResponseState.Success(data))
                    } catch (error: Throwable) {
                        Log.e("Create", error.message.toString())
                        _viewState.postValue(UIResponseState.Error(error.message.toString()))
                    }
                }
            } else {
                _viewState.postValue(UIResponseState.Error(resourceManager.emptyFieldsError))
            }
        }
    }

    fun authenticateUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                try {
                    _viewState.postValue(UIResponseState.Loading)
                    val data = auth.signInWithEmailAndPassword(email, password).await()
                    _viewState.postValue(UIResponseState.Success(data))
                } catch (error: Throwable) {
                    Log.e("Login", error.message.toString())
                    _viewState.postValue(UIResponseState.Error(resourceManager.unknownError))
                }
            } else {
                _viewState.postValue(UIResponseState.Error(resourceManager.emptyFieldsError))
            }
        }
    }

    fun recoverPassword(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                auth.sendPasswordResetEmail(email).await()
                _viewState.postValue(UIResponseState.Success(resourceManager.recoverySuccess))
            } catch (error: Throwable) {
                _viewState.postValue(UIResponseState.Error(resourceManager.unknownError))
            }
        }
    }
}