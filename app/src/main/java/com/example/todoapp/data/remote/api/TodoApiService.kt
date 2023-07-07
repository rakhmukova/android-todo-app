package com.example.todoapp.data.remote.api

import com.example.todoapp.data.remote.model.requests.TodoItemRequest
import com.example.todoapp.data.remote.model.requests.TodoListRequest
import com.example.todoapp.data.remote.model.responses.TodoResponse
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
    suspend fun getAllTodoItems(): Response<TodoResponse.List>

    @PATCH("list")
    suspend fun updateTodoItems(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoListRequest: TodoListRequest
    ): Response<TodoResponse.List>

    @GET("list/{todoItemId}")
    suspend fun getTodoItem(@Path("todoItemId") todoItemId: String): Response<TodoResponse.Item>

    @POST("list")
    suspend fun addTodoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoItemRequest: TodoItemRequest
    ): Response<TodoResponse.Item>

    @PUT("list/{todoItemId}")
    suspend fun updateTodoItem(
        @Path("todoItemId") todoItemId: String,
        @Body todoItemRequest: TodoItemRequest
    ): Response<TodoResponse.Item>

    @DELETE("list/{todoItemId}")
    suspend fun removeTodoItem(@Path("todoItemId") todoItemId: String): Response<TodoResponse.Item>
}
