package com.example.todoapp.data.remote.api

import com.example.todoapp.data.remote.model.TodoItemRequest
import com.example.todoapp.data.remote.model.TodoItemResponse
import com.example.todoapp.data.remote.model.TodoListRequest
import com.example.todoapp.data.remote.model.TodoListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApiService {

    @GET("list")
    suspend fun getAllTodoItems(): Response<TodoListResponse>

    @PATCH("list")
    suspend fun updateTodoItems(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoListRequest: TodoListRequest
    ): Response<TodoListResponse>

    @GET("list/{todoItemId}")
    suspend fun getTodoItem(@Path("todoItemId") todoItemId: String):
            Response<TodoItemResponse>

    @POST("list")
    suspend fun addTodoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoItemRequest: TodoItemRequest
    ): Response<TodoItemResponse>

    @PUT("list/{todoItemId}")
    suspend fun updateTodoItem(
        @Path("todoItemId") todoItemId: String,
        @Body todoItemRequest: TodoItemRequest
    ): Response<TodoItemResponse>

    @DELETE("list/{todoItemId}")
    suspend fun removeTodoItem(@Path("todoItemId") todoItemId: String)
}
