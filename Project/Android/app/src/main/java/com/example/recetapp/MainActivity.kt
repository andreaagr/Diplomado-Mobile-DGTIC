package com.example.recetapp

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.recetapp.databinding.ActivityMainBinding
import com.example.recetapp.databinding.DrawerHeaderLayoutBinding
import com.example.recetapp.networking.UIResponseState
import com.example.recetapp.ui.InternetFragmentDialog
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val intentSenderRequestActivityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    viewModel.signInWithFirebase(result)
                }
        }
    private val navViewHeaderBinding by lazy {
        DrawerHeaderLayoutBinding.bind(binding.navDrawerView.getHeaderView(0))
    }
    private val navController by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
            ?.findNavController()
    }
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard
            ), binding.drawerLayout
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        navController?.let {
            setupActionBarWithNavController(it, appBarConfiguration)
            with(binding) {
                navView.setupWithNavController(it)
                setupWithNavController(navDrawerView, it)
            }
        }
        binding.navDrawerView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.sign_out_item -> {
                    if (viewModel.isUserAuthenticated()) {
                        viewModel.signOut()
                        hideUserDetails()
                    }
                }
                R.id.favorite_recipes -> {
                    navController?.navigate(R.id.savedRecipesFragment)
                }
                else -> { }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        viewModel.viewState.observe(this) {
            handleUIResponse(it)
        }
        viewModel.internetState.observe(this) {
            when(it) {
                MyState.Error -> {
                    InternetFragmentDialog(this, getString(R.string.no_internet_connection_change_message)).startDialog()
                }
                else -> {}
            }
        }
        setupBackPressedCallback()
        checkInternetConnection()
    }

    private fun handleUIResponse(response: UIResponseState) {
        when(response) {
            is UIResponseState.Success<*> -> {
                val firebaseUser = response.content as FirebaseUser?
                if (firebaseUser != null) {
                    showUserDetails(firebaseUser)
                } else {
                    showSignInError()
                }
            }
            is UIResponseState.Error -> showSignInError()
            else -> {}
        }
    }

    override fun onStart() {
        super.onStart()
        with(navViewHeaderBinding) {
            if (viewModel.isUserAuthenticated()) {
                viewModel.getCurrentUser()?.let { showUserDetails(it) }
            } else {
                hideUserDetails()
                signInButton.setOnClickListener {
                    signIn()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp(appBarConfiguration) ?: false || super.onSupportNavigateUp()
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
                            finish()
                        }
                    }
                }
            }
        )
    }

    private fun signIn() {
        viewModel.signIn(intentSenderRequestActivityResultLauncher) { showSignInError() }
    }

    private fun showUserDetails(user: FirebaseUser) {
        with(navViewHeaderBinding) {
            signInButton.visibility = View.GONE
            emailTextView.visibility = View.VISIBLE
            fullNameTextView.visibility = View.VISIBLE
            emailTextView.text = user.email
            fullNameTextView.text = user.displayName
            Glide.with(this@MainActivity)
                .load(user.photoUrl)
                .circleCrop()
                .into(profileImageView)
        }
    }

    private fun hideUserDetails() {
        with(navViewHeaderBinding) {
            signInButton.visibility = View.VISIBLE
            emailTextView.visibility = View.GONE
            fullNameTextView.visibility = View.GONE
            profileImageView.setImageResource(R.drawable.profile)
        }
    }

    private fun showSignInError() {
        Toast.makeText(
            this@MainActivity,
            getString(R.string.toast_sign_in_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun checkInternetConnection() {
        if (!viewModel.hasInternetConnection()) {
            val dialog = InternetFragmentDialog(this, getString(R.string.no_internet_connection))
            dialog.startDialog()
        }
    }
}