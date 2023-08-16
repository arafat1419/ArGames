package com.arafat1419.argames.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.arafat1419.argames.R
import com.arafat1419.argames.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavBar()
    }

    override fun onBackPressed() {
        val navController = Navigation.findNavController(this, R.id.fragment_container)
        when (navController.currentDestination?.id) {
            navController.graph.startDestinationId -> super.onBackPressed()
            else -> {
                navController.navigateUp()
            }
        }
        return
    }

    private fun setupBottomNavBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.main_nav)

        binding.mainBottomNavigation.setupWithNavController(navController)

        navController.graph = navGraph
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.favoriteFragment -> showBottomNav(true)
                else -> showBottomNav(false)
            }
        }
    }

    private fun showBottomNav(state: Boolean) {
        binding.mainBottomNavigation.visibility = if (state) View.VISIBLE else View.GONE
    }
}