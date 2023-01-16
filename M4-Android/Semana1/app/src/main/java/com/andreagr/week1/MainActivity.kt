package com.andreagr.week1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.andreagr.week1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonEnviar.setOnClickListener {
                val nombre = editTextName.text.toString()
                val apellido = editTextLastName.text.toString()
                val edad = editTextAge.text.toString()

                if (nombre.isNotEmpty() && apellido.isNotEmpty() && edad.isNotEmpty()) {
                    val intent = Intent(
                        this@MainActivity,
                        IntentActivity::class.java
                    ).apply {
                        putExtra("nombre", nombre)
                        putExtra("apellido", apellido)
                        putExtra("edad", edad)
                    }

                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Para continuar completa todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}