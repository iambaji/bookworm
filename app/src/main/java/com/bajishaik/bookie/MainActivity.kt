package com.bajishaik.bookie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bajishaik.bookie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = supportFragmentManager.findFragmentById(R.id.nav_host)?.findNavController() ?: return

        binding.bottomNav.setupWithNavController(
            navController
        )

        setUpObservers()
        handleIntent(intent)
    }

    private fun setUpObservers(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.onEach {
                    binding.progressBar.isVisible = it.isLoading
                }.launchIn(this)
            }
        }
    }

    private fun handleIntent(intent: Intent){
        when (intent.action) {
            Intent.ACTION_SEND -> {
                if(intent.type?.startsWith("application") == true){
                    handleImport(intent)
                }
            }
        }
    }

    private fun handleImport(intent: Intent){
        val uri = intent.getParcelableExtra(Intent.EXTRA_STREAM) as? Uri
        if (uri != null) {
            viewModel.importBook(uri)
        }
    }
}