package com.example.todoapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.ui.main.MainActivity
import com.example.todoapp.ui.settings.components.SettingsScreenContent
import com.example.todoapp.ui.util.theme.AppTheme

class SettingsFragment : Fragment() {
    private val settingsViewModel: SettingsViewModel by viewModels {
        (activity as MainActivity).activityComponent.viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    SettingsScreenContent(
                        settingsViewModel = settingsViewModel,
                        onClose = { navigateToItemsFragment() },
                        onSave = { navigateToItemsFragment() }
                    )
                }
            }
        }
    }

    private fun navigateToItemsFragment() {
        findNavController().navigate(R.id.action_SettingsFragment_to_TodoItemsFragment)
    }
}
