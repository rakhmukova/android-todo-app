package com.example.todoapp.ui.edititem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.ui.util.theme.ExtendedTheme

@Composable
fun TopBar(onSave: () -> Unit, onClose: () -> Unit) {
    TopAppBar(
        backgroundColor = ExtendedTheme.colors.backPrimary,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            CloseButton(onClose = onClose)
            Spacer(modifier = Modifier.weight(1f))
            SaveButton(onSave = onSave)
        }
    }
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar(onSave = {}, onClose = {})
}
