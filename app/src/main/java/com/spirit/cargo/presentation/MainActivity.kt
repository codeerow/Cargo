package com.spirit.cargo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.spirit.cargo.R
import com.spirit.cargo.databinding.ActivityMainBinding
import com.spirit.cargo.domain.core.navigation.Navigation
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding by viewBinding(ActivityMainBinding::bind)

    private val navigationHolder by inject<Navigation<AppCompatActivity>>()
    private val navController get() = findNavController(R.id.nav_host_fragment_content_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        navigationHolder.attach(this)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
