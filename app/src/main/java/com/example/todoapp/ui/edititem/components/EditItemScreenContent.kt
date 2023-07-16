package com.example.todoapp.ui.edititem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
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
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

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
            // todo: make scrollable using lazy
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
            Divider(modifier = Modifier.padding(16.dp), thickness = 0.5.dp)
            DeadlinePicker(
                deadline = todoItem.deadline,
                onDeadlineChange = { editTodoItemViewModel.updateDeadline(it) }
            )
            Divider(thickness = 0.5.dp)
            DeleteButton(onDelete = onDelete)
        }
    }

    PriorityBottomSheet(
        sheetState = bottomSheetState,
        onPriorityChange = {
            editTodoItemViewModel.updatePriority(it)
        }
    )
}
