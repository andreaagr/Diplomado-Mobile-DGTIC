package com.andreagr.week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andreagr.week1.databinding.ActivityIntentBinding

class IntentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(intent.extras) {
            val nombre = this?.get("nombre").toString()
            val apellido = this?.get("apellido").toString()
            val edad = this?.get("edad").toString()
            printData(
                nombre,
                apellido,
                edad
            )
        }
    }

    fun printData(nombre: String, apellido: String, edad: String) {
        with(binding) {
            textViewName.text = nombre
            textViewLastName.text = apellido
            textViewAge.text = edad
        }
    }
}