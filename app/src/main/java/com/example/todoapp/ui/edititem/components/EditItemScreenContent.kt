package com.example.todoapp.ui.edititem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.edititem.EditTodoItemViewModel
import com.example.todoapp.ui.edititem.ScreenState
import com.example.todoapp.ui.util.components.AppDivider
import com.example.todoapp.ui.util.theme.ExtendedTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditItemScreenContent(
    editTodoItemViewModel: EditTodoItemViewModel,
    onClose: () -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    val todoItem by editTodoItemViewModel.todoItem.collectAsState()
    val screenState by editTodoItemViewModel.screenState.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    if (screenState == ScreenState.LOADING) {
        LoadingScreen()
    } else {
        PriorityBottomSheet(
            sheetState = bottomSheetState,
            onPriorityChange = {
                editTodoItemViewModel.updatePriority(it)
            }
        ) {
            Scaffold(
                backgroundColor = ExtendedTheme.colors.backPrimary,
                topBar = {
                    TopBar(
                        onSave = onSave,
                        onClose = onClose
                    )
                },
            ) { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
                    DescriptionField(
                        text = todoItem.text,
                        onTextChange = { editTodoItemViewModel.updateDescription(it) }
                    )
                    PriorityPicker(
                        priority = todoItem.priority,
                        onPriorityClick = { scope.launch { bottomSheetState.show() } }
                    )
                    AppDivider(modifier = Modifier.padding(16.dp))
                    DeadlinePicker(
                        deadline = todoItem.deadline,
                        onDeadlineChange = { editTodoItemViewModel.updateDeadline(it) }
                    )
                    AppDivider()
                    DeleteButton(onDelete = onDelete)
                }
            }
        }
    }
}
