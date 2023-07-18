package com.example.todoapp.ui.main.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.util.theme.Blue
import kotlinx.coroutines.delay

private const val NUM_OF_SECONDS = 5
private const val SECOND = 1000L

@Composable
fun DeleteSnackbar(snackbarHostState: SnackbarHostState) {
    val countdownSeconds = remember { mutableStateOf(NUM_OF_SECONDS) }

    val offsetY by animateDpAsState(
        targetValue = if (snackbarHostState.currentSnackbarData != null) 0.dp else 100.dp,
        animationSpec = tween(durationMillis = 300)
    )

    LaunchedEffect(snackbarHostState.currentSnackbarData) {
        if (snackbarHostState.currentSnackbarData != null) {
            countdownSeconds.value = NUM_OF_SECONDS
            while (countdownSeconds.value > 0) {
                delay(SECOND)
                countdownSeconds.value--
            }
            snackbarHostState.currentSnackbarData?.dismiss()
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = {
            Snackbar(
                modifier = Modifier
                    .padding(16.dp)
                    .offset(y = offsetY),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.widthIn(max = 200.dp)
                    ) {
                        Text(
                            text = snackbarHostState.currentSnackbarData?.message ?: "",
                            modifier = Modifier.padding(end = 16.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = stringResource(id = R.string.delete_countdown, countdownSeconds.value),
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                    Button(
                        onClick = { snackbarHostState.currentSnackbarData?.performAction() },
                        modifier = Modifier.padding(start = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = Blue
                        ),
                        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
            }
        }
    )
}
