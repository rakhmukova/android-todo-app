package com.example.todoapp.ui.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.databinding.TodoItemBinding

class TodoItemsAdapter(
    private val callbacks: TodoItemChangeCallbacks
) : ListAdapter<TodoItem, TodoItemViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TodoItemBinding.inflate(inflater, parent, false)
        return TodoItemViewHolder(binding, callbacks)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todoItem = getItem(position)
        holder.onBind(todoItem, position)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }
}
