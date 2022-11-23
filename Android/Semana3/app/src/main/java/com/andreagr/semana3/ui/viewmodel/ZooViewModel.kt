package com.andreagr.semana3.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreagr.semana3.db.SqlHelper
import com.andreagr.semana3.model.ZooAnimal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZooViewModel @Inject constructor(
    private val sqlHelper: SqlHelper
): ViewModel() {
    val zooList: LiveData<List<ZooAnimal>> get() = _zooList
    private val _zooList: MutableLiveData<List<ZooAnimal>> = MutableLiveData()

    init {
        _zooList.value = sqlHelper.getAllAnimals()
    }

    fun addAnimal(animal: ZooAnimal) {
        sqlHelper.insert(animal)
    }

    fun deleteAnimal(id: Int) {
        sqlHelper.deleteAnimal(id)
    }

    fun updateAnimal(zooAnimal: ZooAnimal) {
        sqlHelper.updateAnimal(zooAnimal)
    }

    fun syncAnimals() {
        viewModelScope.launch {
            _zooList.postValue(sqlHelper.getAllAnimals())
        }
    }
}