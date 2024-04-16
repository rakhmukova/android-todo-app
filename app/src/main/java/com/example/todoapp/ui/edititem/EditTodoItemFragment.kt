package com.example.todoapp.ui.edititem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.di.component.EditTodoItemFragmentComponent
import com.example.todoapp.ui.LambdaFactory
import com.example.todoapp.ui.edititem.components.EditItemScreenContent
import com.example.todoapp.ui.main.MainActivity
import com.example.todoapp.ui.util.theme.AppTheme
import javax.inject.Inject

/**
 * A fragment for editing a todoItem.
 */
class EditTodoItemFragment : Fragment() {

    private lateinit var component: EditTodoItemFragmentComponent
    private lateinit var todoItemId: String

    @Inject
    lateinit var factory: EditTodoItemViewModel.Factory

    private val editTodoItemViewModel: EditTodoItemViewModel by viewModels {
        LambdaFactory(this) { _ ->
            factory.create(todoItemId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            todoItemId = it.getString(ARG_TODO_ITEM_ID, "")
        }

        component = (activity as MainActivity).activityComponent.editTodoItemFragmentComponent
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    EditItemScreenContent(
                        editTodoItemViewModel = editTodoItemViewModel,
                        onClose = {
                            navigateToItemsFragment()
                        },
                        onSave = {
                            editTodoItemViewModel.saveTodoItem()
                            navigateToItemsFragment()
                        },
                        onDelete = {
                            editTodoItemViewModel.removeTodoItem()
                            navigateToItemsFragment()
                        }
                    )
                }
            }
        }
    }

    private fun navigateToItemsFragment() {
        findNavController().popBackStack(R.id.TodoItemsFragment, false)
    }

    companion object {
        const val ARG_TODO_ITEM_ID = "todoItemId"
    }
}
