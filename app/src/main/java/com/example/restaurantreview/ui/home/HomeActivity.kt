package com.example.restaurantreview.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.restaurantreview.R
import com.example.restaurantreview.databinding.ActivityHomeBinding
import com.example.restaurantreview.ui.settings.SettingsViewModel
import com.example.restaurantreview.ui.ViewModelFactory

class HomeActivity : AppCompatActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe isDarkMode
        settingsViewModel.isDarkMode.observe(this) { isDarkMode ->
            val theme = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(theme)
        }

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_favorite, R.id.navigation_settings
        ))
        navView.setupWithNavController(navController)
    }
}