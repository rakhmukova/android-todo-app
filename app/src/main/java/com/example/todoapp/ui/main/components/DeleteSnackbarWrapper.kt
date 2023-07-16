package com.example.todoapp.ui.main.components

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.util.ChangeItemAction
import com.example.todoapp.ui.main.MainViewModel

private suspend fun showSnackbarWithAction(
    snackbarHostState: SnackbarHostState,
    todoItem: TodoItem,
    onAction: (TodoItem) -> Unit
) {
    val message = todoItem.text
    val snackbarResult = snackbarHostState.showSnackbar(
        message,
        duration = SnackbarDuration.Long
    )
    when (snackbarResult) {
        SnackbarResult.ActionPerformed -> onAction(todoItem)
        SnackbarResult.Dismissed -> {}
    }
}

@Composable
fun DeleteSnackbarWrapper(
    mainViewModel: MainViewModel
) {
    val changeItemState by mainViewModel.changeItemState.collectAsState()
    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    LaunchedEffect(changeItemState) {
        when (val data = changeItemState.data) {
            // todo: handle success only (fix in repo)
            is ChangeItemAction.Delete -> {
                data.todoItem?.let {
                    showSnackbarWithAction(
                        snackbarHostState,
                        it,
                        mainViewModel::recoverTodoItem
                    )
                }
            }
            else -> {}
        }
    }

    DeleteSnackbar(snackbarHostState)
}
