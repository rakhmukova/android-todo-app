package com.example.todoapp.ui.edititem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.util.theme.ExtendedTheme

@Composable
fun DescriptionField(text: String, onTextChange: (String) -> Unit) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Transparent, shape = RoundedCornerShape(5.dp)),
        textStyle = MaterialTheme.typography.body1.copy(color = ExtendedTheme.colors.labelPrimary),
        cursorBrush = SolidColor(ExtendedTheme.colors.labelPrimary),
        minLines = 3,
        decorationBox = { innerTextField ->
            Card(
                elevation = 4.dp,
                modifier = Modifier.padding(16.dp),
                backgroundColor = ExtendedTheme.colors.backSecondary,
                contentColor = ExtendedTheme.colors.labelPrimary
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    if (text.isEmpty()) {
                        Text(text = stringResource(id = R.string.initial_description))
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Preview
@Composable
fun EmptyDescriptionFieldPreview() {
    DescriptionField(text = "", onTextChange = {})
}

@Preview
@Composable
fun DescriptionFieldPreview() {
    DescriptionField(text = "Task description", onTextChange = {})
}
