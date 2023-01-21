package com.andreagr.greatwondersapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GreatWonderViewModel: ViewModel() {
    val greatWonders: LiveData<List<GreatWonder>> get() = _greatWonders
    private val _greatWonders: MutableLiveData<List<GreatWonder>> = MutableLiveData()
}