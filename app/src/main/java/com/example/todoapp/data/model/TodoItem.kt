package com.example.todoapp.data.model

import java.util.*

data class TodoItem(
    val id: String,
    val text: String,
    val priority: Priority,
    val deadline: Date?,
    val isCompleted: Boolean,
    val createdAt: Date,
    val modifiedAt: Date?
)
