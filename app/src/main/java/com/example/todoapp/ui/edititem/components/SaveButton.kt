package com.example.todoapp.ui.edititem.components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.util.theme.Blue

@Composable
fun SaveButton(onSave: () -> Unit, isSaveEnabled: Boolean) {
    Button(
        content = { Text(text = stringResource(R.string.save_task).uppercase()) },
        onClick = onSave,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Blue,
            disabledBackgroundColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.small,
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
        enabled = isSaveEnabled
    )
}

@Preview
@Composable
fun SaveButtonPreview() {
    SaveButton(onSave = {}, isSaveEnabled = true)
}
