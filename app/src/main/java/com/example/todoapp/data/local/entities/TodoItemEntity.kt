package com.example.todoapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.model.Priority
import java.util.*

@Entity(tableName = "todo_items")
data class TodoItemEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "priority") val priority: Priority = Priority.COMMON,
    @ColumnInfo(name = "deadline") val deadline: Date? = null,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Date = Date(),
    @ColumnInfo(name = "modified_at") val modifiedAt: Date? = null,
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean = false
)
