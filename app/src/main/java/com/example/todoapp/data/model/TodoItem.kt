package com.example.todoapp.data.model

import java.util.*

data class TodoItem(
    val id: String,
    val description: String,
    val priority: Priority,
    val deadline: Date?,
    val isDone: Boolean,
    val createdAt: Date,
    val modifiedAt: Date?)
