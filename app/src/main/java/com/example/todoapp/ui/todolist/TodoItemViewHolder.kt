package com.example.todoapp.ui.todolist

import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.databinding.TodoItemBinding

class TodoItemViewHolder(
    private val binding: TodoItemBinding,
    private val callbacks: TodoItemChangeCallbacks
    ) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(todoItem: TodoItem){
        binding.isDoneRadioButton.isChecked = todoItem.isDone
        binding.taskText.text = todoItem.description

        binding.isDoneRadioButton.setOnCheckedChangeListener { _, isChecked ->
            callbacks.onTodoItemCheckedChanged(todoItem, isChecked)
        }

        binding.taskText.setOnClickListener {
            callbacks.onTodoItemClicked(todoItem)
        }
    }
}
