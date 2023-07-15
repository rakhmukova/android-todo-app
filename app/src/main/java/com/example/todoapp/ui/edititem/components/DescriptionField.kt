package com.example.todoapp.ui.edititem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.util.theme.ExtendedTheme

@Composable
fun DescriptionField(text: String, onTextChange: (String) -> Unit) {
    // todo: fix round borders
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .wrapContentWidth(align = Alignment.CenterHorizontally),
        elevation = 8.dp
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = onTextChange,
            textStyle = MaterialTheme.typography.body1,
            minLines = 3,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = ExtendedTheme.colors.backSecondary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = ExtendedTheme.colors.labelPrimary
            ),
            placeholder = { Text(stringResource(R.string.initial_description)) }
        )
    }
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
