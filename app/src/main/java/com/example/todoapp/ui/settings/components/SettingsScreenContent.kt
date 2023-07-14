package com.example.todoapp.ui.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.edititem.components.TopBar
import com.example.todoapp.ui.settings.SettingsViewModel
import com.example.todoapp.ui.util.theme.ExtendedTheme

@Composable
fun SettingsScreenContent(
    settingsViewModel: SettingsViewModel,
    onSave: () -> Unit,
    onClose: () -> Unit
) {
    val appTheme by settingsViewModel.appTheme.collectAsState()
    val notificationsEnabled by settingsViewModel.notificationsEnabled.collectAsState()

    Scaffold(
        backgroundColor = ExtendedTheme.colors.backPrimary,
        topBar = {
            TopBar(onSave = onSave, onClose = onClose)
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            ThemePicker(appTheme = appTheme, onThemeChange = { settingsViewModel.setAppTheme(it) })
            Spacer(modifier = Modifier.height(12.dp))
            Divider(thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 12.dp))
            Spacer(modifier = Modifier.height(12.dp))
            NotificationsRegulator(
                notificationsEnabled = notificationsEnabled,
                onNotificationsEnabledChange = {
                    settingsViewModel.setNotificationsEnabled(it)
                }
            )
        }
    }
}
