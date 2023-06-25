package com.example.todoapp.ui.todolist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.databinding.TodoItemBinding
import com.example.todoapp.util.DateParser

class TodoItemViewHolder(
    private val binding: TodoItemBinding,
    private val callbacks: TodoItemChangeCallbacks
    ) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(todoItem: TodoItem){
        binding.isCompletedCheckBox.isChecked = todoItem.isCompleted
        binding.taskText.text = todoItem.text
        binding.taskDeadline.text = DateParser.parse(todoItem.deadline)
        setPriorityIcon(todoItem)

        binding.isCompletedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            callbacks.onTodoItemCheckedChanged(todoItem, isChecked)
        }

        binding.taskText.setOnClickListener {
            callbacks.onTodoItemClicked(todoItem)
        }
    }

    private fun setPriorityIcon(todoItem: TodoItem) {
        when (todoItem.priority) {
            Priority.LOW -> {
                binding.priorityIcon.visibility = View.VISIBLE
                binding.priorityIcon.setImageResource(R.drawable.low_priority_sign)
            }
            Priority.HIGH -> {
                binding.priorityIcon.visibility = View.VISIBLE
                binding.priorityIcon.setImageResource(R.drawable.high_priority_sign)
            }
            Priority.COMMON -> {
                binding.priorityIcon.visibility = View.GONE
            }
        }
    }
}
