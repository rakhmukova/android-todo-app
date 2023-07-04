package com.example.todoapp.data.remote.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class ApiTodoItem(
    @SerializedName("id")
    val id: String = "",

    @SerializedName("text")
    val text: String,

    @SerializedName("importance")
    val priority: String = "basic",

    @SerializedName("deadline")
    val deadline: Long? = null,

    @SerializedName("done")
    val isCompleted: Boolean = false,

    @SerializedName("created_at")
    val createdAt: Long,

    @SerializedName("changed_at")
    val modifiedAt: Long,

    @SerializedName("color")
    val color: String? = null,

    @SerializedName("last_updated_by")
    val lastUpdatedBy: String
)
