package com.andreagr.semana4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andreagr.semana4.model.Animal

class AnimalViewModel: ViewModel() {
    val zooList: LiveData<List<Animal>> get() = _zooList
    private val _zooList: MutableLiveData<List<Animal>> = MutableLiveData()

    init {
        _zooList.value = listOf(
            Animal(
                1,
                "https://cdn.pixabay.com/photo/2017/11/06/15/30/elephants-2923917__480.jpg",
                "Elefante",
                1579.88f,
                "M",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec accumsan metus finibus tellus volutpat mattis. Phasellus volutpat a ipsum a tempor. Aliquam in porttitor lorem. Cras posuere massa eu sem pellentesque, eleifend porttitor erat feugiat"
            ),
            Animal(
                2,
                "https://cdn.pixabay.com/photo/2018/01/02/09/42/koala-3055832__480.jpg",
                "Koala",
                75.79f,
                "F",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec accumsan metus finibus tellus volutpat mattis. Phasellus volutpat a ipsum a tempor. Aliquam in porttitor lorem. Cras posuere massa eu sem pellentesque, eleifend porttitor erat feugiat"
            ),
            Animal(
                3,
                "https://cdn.pixabay.com/photo/2014/11/03/11/07/lion-515028__480.jpg",
                "León",
                150.34f,
                "M",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec accumsan metus finibus tellus volutpat mattis. Phasellus volutpat a ipsum a tempor. Aliquam in porttitor lorem. Cras posuere massa eu sem pellentesque, eleifend porttitor erat feugiat"),
            Animal(
                4,
                "https://cdn.pixabay.com/photo/2017/09/26/23/04/monkey-2790452__480.jpg",
                "Mono",
                31.92f,
                "F",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec accumsan metus finibus tellus volutpat mattis. Phasellus volutpat a ipsum a tempor. Aliquam in porttitor lorem. Cras posuere massa eu sem pellentesque, eleifend porttitor erat feugiat"),
            Animal(
                5,
                "https://cdn.pixabay.com/photo/2017/04/11/21/34/giraffe-2222908__480.jpg",
                "Jirafa",
                102.46f,
                "M",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec accumsan metus finibus tellus volutpat mattis. Phasellus volutpat a ipsum a tempor. Aliquam in porttitor lorem. Cras posuere massa eu sem pellentesque, eleifend porttitor erat feugiat"),
            Animal(
                6,
                "https://cdn.pixabay.com/photo/2013/11/01/11/13/dolphin-203875__480.jpg",
                "Delfín",
                58.87f,
                "F",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec accumsan metus finibus tellus volutpat mattis. Phasellus volutpat a ipsum a tempor. Aliquam in porttitor lorem. Cras posuere massa eu sem pellentesque, eleifend porttitor erat feugiat"),
        )
    }
}