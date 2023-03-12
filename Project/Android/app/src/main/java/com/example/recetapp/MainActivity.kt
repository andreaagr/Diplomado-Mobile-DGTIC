package com.example.recetapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.recetapp.databinding.ActivityMainBinding
import com.example.recetapp.databinding.DrawerHeaderLayoutBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private val oneTapClient by lazy { Identity.getSignInClient(this) }
    private val signInRequest by lazy {
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.my_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
    private val intentSenderRequestActivityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val googleCredential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = googleCredential.googleIdToken
                    if (idToken != null) {
                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        try {
                            lifecycleScope.launch(Dispatchers.IO) {
                                auth.signInWithCredential(firebaseCredential).await()
                                withContext(Dispatchers.Main) {
                                    val user = auth.currentUser
                                    if (user != null) {
                                        showUserDetails(user)
                                    } else {
                                        showSignInError()
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            Log.d("SignIn Error", e.message.toString())
                            showSignInError()
                        }
                    }
                }
            } catch (e: ApiException) {
                Log.d("SignIn Error", e.message.toString())
                showSignInError()
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
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            ), binding.drawerLayout
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        navController?.let {
            setupActionBarWithNavController(it, appBarConfiguration)
            with(binding) {
                navView.setupWithNavController(it)
                navDrawerView.setupWithNavController(it)
            }
        }
        binding.navDrawerView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.sign_out_item -> {
                    auth.signOut()
                    hideUserDetails()
                }
                else -> { }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        setupBackPressedCallback()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        with(navViewHeaderBinding) {
            if (currentUser != null) {
                showUserDetails(currentUser)
            } else {
                signInButton.visibility = View.VISIBLE
                hideUserDetails()
                signInButton.setOnClickListener {
                    //println("In click listener")
                    lifecycleScope.launch(Dispatchers.IO) {
                        signIn()
                    }
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

    private suspend fun signIn() {
        try {
            val result = oneTapClient.beginSignIn(signInRequest).await()
            IntentSenderRequest.Builder(result.pendingIntent).build()
                .also { intentSenderRequest ->
                    intentSenderRequestActivityResultLauncher.launch(
                        intentSenderRequest
                    )
                }
        } catch (e: Exception) {
            Log.d("SignIn Error", e.message.toString())
            showSignInError()
        }
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
}