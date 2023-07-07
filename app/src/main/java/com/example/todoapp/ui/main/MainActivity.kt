package com.example.todoapp.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.todoapp.R
import com.example.todoapp.TodoApp
import com.example.todoapp.data.DataState
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.di.component.ActivityComponent
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var activityComponent: ActivityComponent
        private set

    private val mainViewModel: MainViewModel by viewModels {
        activityComponent.viewModelFactory
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent = (applicationContext as TodoApp).appComponent.activityComponent
        activityComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupViewModel()
    }

    // todo: pass only message to activity?
    private fun setupViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.changeItemState.collectLatest {
                    when (it) {
                        is DataState.Error -> {
                            showSnackbar(getString(R.string.update_error))
                        }
                        else -> {}
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.syncWithBackend.collect {
                    when (it) {
                        false -> {
                            showSnackbar(getString(R.string.upload_error))
                        }
                        true -> {}
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, SNACKBAR_DURATION).show()
    }

    companion object {
        private const val SNACKBAR_DURATION = 1000
    }
}
