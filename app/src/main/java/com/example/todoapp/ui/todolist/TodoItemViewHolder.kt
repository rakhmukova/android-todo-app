package com.example.todoapp.ui.todolist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.databinding.TodoItemBinding
import com.example.todoapp.util.DateParser

class TodoItemViewHolder(
    private val binding: TodoItemBinding,
    private val callbacks: TodoItemChangeCallbacks
    ) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(todoItem: TodoItem){
        binding.isDoneRadioButton.isChecked = todoItem.isCompleted
        binding.taskText.text = todoItem.text
        binding.taskDeadline.text = DateParser.parse(todoItem.deadline)

        binding.isDoneRadioButton.setOnCheckedChangeListener { _, isChecked ->
            callbacks.onTodoItemCheckedChanged(todoItem, isChecked)
        }

        binding.taskText.setOnClickListener {
            callbacks.onTodoItemClicked(todoItem)
        }
    }
}
