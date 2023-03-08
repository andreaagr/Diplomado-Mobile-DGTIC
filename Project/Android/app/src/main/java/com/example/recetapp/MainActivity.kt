package com.example.recetapp

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recetapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
            ?.findNavController()
    }
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            ), binding.drawerLayout
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        setSupportActionBar(binding.toolbar)
        navController?.let {
            setupActionBarWithNavController(it, appBarConfiguration)
            with(binding) {
                navView.setupWithNavController(it)
                navDrawerView.setupWithNavController(it)
            }
        }

        setupBackPressedCallback()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp(appBarConfiguration) ?:  false || super.onSupportNavigateUp()
    }

    private fun setupBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    with(binding) {
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START)
                        } else {
                            onBackPressedDispatcher.onBackPressed()
                        }
                    }
                }
            }
        )
    }
}