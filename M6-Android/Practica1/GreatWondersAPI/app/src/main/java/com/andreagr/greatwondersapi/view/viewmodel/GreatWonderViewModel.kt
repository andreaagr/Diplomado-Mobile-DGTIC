package com.andreagr.greatwondersapi.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreagr.greatwondersapi.repository.GreatWonderRepository
import com.andreagr.greatwondersapi.util.UIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GreatWonderViewModel @Inject constructor(
    private val repository: GreatWonderRepository
): ViewModel() {
    val viewState: LiveData<UIResponseState> get() = _viewState
    private val _viewState: MutableLiveData<UIResponseState> = MutableLiveData()

    fun loadElements() {
        viewModelScope.launch {
            delay(1800)
            _viewState.value = UIResponseState.Loading
            _viewState.postValue(repository.getRemoteGreatWonders())
        }
    }
}