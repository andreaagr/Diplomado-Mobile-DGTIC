package com.andreagr.semana2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.andreagr.semana2.databinding.ActivityAnimalDetailBinding
import com.andreagr.semana2.model.Animal

class AnimalDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimalDetailBinding
    private val args: AnimalDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val animal: Animal = args.animalSelected
        binding.animalPhotoImageView.setImageResource(animal.image)
        binding.nameTextView.text = animal.name
    }
}