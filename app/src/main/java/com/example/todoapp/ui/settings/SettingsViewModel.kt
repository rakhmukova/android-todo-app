package com.example.todoapp.ui.settings

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.repository.LocalSettingsRepository
import com.example.todoapp.data.util.AppTheme
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val localSettingsRepository: LocalSettingsRepository
) : ViewModel() {
    val appTheme = localSettingsRepository.appTheme
    val notificationsEnabled = localSettingsRepository.notificationsEnabled

    fun setAppTheme(appTheme: AppTheme) {
        localSettingsRepository.setAppTheme(appTheme)
    }

    fun setNotificationsEnabled(notificationsEnabled: Boolean) {
        localSettingsRepository.setNotificationsEnabled(notificationsEnabled)
    }
}
