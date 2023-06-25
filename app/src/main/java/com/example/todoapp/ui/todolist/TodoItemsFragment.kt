package com.example.todoapp.ui.todolist

import com.example.todoapp.viewmodel.todolist.TodoListViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.TodoApp
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.databinding.FragmentTodoItemsBinding
import com.example.todoapp.ui.additem.AddTodoItemFragment
import com.example.todoapp.ui.todolist.recyclerview.TodoItemChangeCallbacks
import com.example.todoapp.ui.todolist.recyclerview.TodoItemsAdapter
import com.example.todoapp.ui.todolist.recyclerview.TodoItemsOffsetItemDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class TodoItemsFragment : Fragment(), TodoItemChangeCallbacks {

    private lateinit var todoAdapter: TodoItemsAdapter

    private val todoListViewModel: TodoListViewModel by lazy {
        (requireActivity().application as TodoApp).todoListViewModel
    }

    private var _binding: FragmentTodoItemsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        setupCreateTaskButton()
        setupVisibilityToggleButton()
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todoListViewModel.completedTasksCount.collectLatest {
                    binding.completedTitle.text = String.format(
                        getString(R.string.completed_tasks),
                        it
                    )
                }
            }
        }

        binding.visibilityToggleButton.isChecked = !todoListViewModel.showOnlyCompletedTasks.value
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoItemsAdapter(this)
        binding.todoItems.adapter = todoAdapter
        binding.todoItems.layoutManager = LinearLayoutManager(requireContext())
        binding.todoItems.addItemDecoration(TodoItemsOffsetItemDecoration(bottomOffset = 16f.toInt()))

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todoListViewModel.filteredTodoItems.collectLatest { filteredList ->
                    todoAdapter.submitList(filteredList)
                }
            }
        }
    }

    private fun setupCreateTaskButton() {
        binding.createTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_TodoItemsFragment_to_AddItemFragment)
        }
    }

    private fun setupVisibilityToggleButton() {
        binding.visibilityToggleButton.setOnCheckedChangeListener { _, isChecked ->
            todoListViewModel.setShowOnlyCompletedTasks(!isChecked)
        }
    }

    override fun onTodoItemClicked(todoItem: TodoItem) {
        val args = bundleOf(AddTodoItemFragment.ARG_TODO_ITEM_ID to todoItem.id)
        findNavController().navigate(R.id.action_TodoItemsFragment_to_AddItemFragment, args)
    }

    override fun onTodoItemCheckedChanged(todoItem: TodoItem, isChecked: Boolean, position: Int) {
        todoListViewModel.updateChecked(todoItem, isChecked)
        // TODO: fix when visibility on / off
        // todoAdapter.notifyItemChanged(position)
    }
}
