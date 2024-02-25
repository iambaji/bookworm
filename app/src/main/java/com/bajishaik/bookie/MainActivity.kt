package com.bajishaik.bookie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bajishaik.bookie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = supportFragmentManager.findFragmentById(R.id.nav_host)?.findNavController() ?: return

        binding.bottomNav.setupWithNavController(
            navController
        )
    }
}