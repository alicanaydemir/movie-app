package com.aydemir.movieapp.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aydemir.movieapp.R
import com.aydemir.movieapp.core.BaseActivity
import com.aydemir.movieapp.databinding.ActivityMainBinding
import com.aydemir.movieapp.util.extensions.setVisibilityBottom
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun prepareView(savedInstanceState: Bundle?) {
        initBottomNav()
        initObserver()
    }

    override fun setSplash() {
        installSplashScreen()
    }

    private fun initBottomNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.navHostFragment
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            setVisibilityBottom(binding = binding, destinationId = destination.id)
        }
        binding.bottomNavigationView.setOnItemReselectedListener { }
    }

    private fun initObserver() {

    }

    //--------------------------------------------------------------//

}