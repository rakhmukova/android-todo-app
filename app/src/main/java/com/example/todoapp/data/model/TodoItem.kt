package com.example.todoapp.data.model

import java.util.*

/**
 * Data class representing a single task or item in a todolist.
 */
data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String = "",
    val priority: Priority = Priority.COMMON,
    val deadline: Date? = null,
    val isCompleted: Boolean = false,
    val createdAt: Date = Date(),
    val modifiedAt: Date = Date(),
    val isDeleted: Boolean = false
)
