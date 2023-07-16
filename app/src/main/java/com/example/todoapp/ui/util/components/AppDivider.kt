package com.example.todoapp.ui.util.components

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.util.theme.ExtendedTheme

@Composable
fun AppDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        color = ExtendedTheme.colors.supportSeparator,
        thickness = 0.5.dp
    )
}
