package com.example.todoapp.ui.util.theme.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.ui.util.theme.White

@Composable
@Preview
fun TypographyPreview() {
    Column(
        modifier = Modifier
            .background(White)
    ) {
        Text(text = "Large title — 32/38", style = MaterialTheme.typography.h1)
        Text(text = "Title — 20/32", style = MaterialTheme.typography.h2)
        Text(text = "BUTTON — 14/24", style = MaterialTheme.typography.button)
        Text(text = "Body — 16/20", style = MaterialTheme.typography.body1)
        Text(text = "Subhead — 14/20", style = MaterialTheme.typography.subtitle1)
    }
}
