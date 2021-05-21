package com.pratham.project.fileio

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pratham.project.fileio.databinding.ActivityMainBinding
import com.pratham.project.fileio.ui.home.HomeViewModel
import com.pratham.project.fileio.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navigationMenuItems = listOf(R.id.navigation_home, R.id.navigation_hashtags, R.id.navigation_profile)
    val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = Navigation.findNavController(this, R.id.dashboardNav)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNav.isVisible = navigationMenuItems.contains(destination.id)
        }
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        try{
            intent?.data?.let {
                findNavController(R.id.dashboardNav).navigate(it)
            }
        }catch (e: Exception){

        }
    }
}