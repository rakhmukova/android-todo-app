package com.example.todoapp.ui.todolist.recyclerview

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.databinding.TodoItemBinding
import com.example.todoapp.ui.util.DateParser

class TodoItemViewHolder(
    private val binding: TodoItemBinding,
    private val callbacks: TodoItemChangeCallbacks
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(todoItem: TodoItem, position: Int) {
        setTaskDeadline(todoItem)
        setTaskDescription(todoItem)
        setPriorityIcon(todoItem)
        setCheckBox(todoItem, position)
    }

    private fun setTaskDeadline(todoItem: TodoItem) {
        binding.taskDeadline.text = DateParser.parse(todoItem.deadline)
    }

    private fun setCheckBox(todoItem: TodoItem, position: Int) {
        binding.isCompletedCheckBox.isChecked = todoItem.isCompleted
        binding.isCompletedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            callbacks.onTodoItemCheckedChanged(todoItem, isChecked, position)
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

    private fun setTaskDescription(todoItem: TodoItem) {
        binding.taskText.text = todoItem.text
        if (todoItem.isCompleted) {
            binding.taskText.paint.isStrikeThruText = true
            binding.taskText.setTextColor(ContextCompat.getColor(binding.root.context, R.color.label_tertiary))
        } else {
            binding.taskText.paint.isStrikeThruText = false
            binding.taskText.setTextColor(ContextCompat.getColor(binding.root.context, R.color.label_primary))
        }

        binding.taskText.setOnClickListener {
            callbacks.onTodoItemClicked(todoItem)
        }
    }
}
