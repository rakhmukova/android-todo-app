package com.example.todoapp.data.remote.api

import com.example.todoapp.data.remote.model.requests.TodoItemRequestBody
import com.example.todoapp.data.remote.model.requests.TodoListRequestBody
import com.example.todoapp.data.remote.model.responses.TodoItemResponseBody
import com.example.todoapp.data.remote.model.responses.TodoListResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * API service interface for performing CRUD operations on items.
 */
interface TodoApiService {

    @GET("list")
    suspend fun getAllTodoItems(): Response<TodoListResponseBody>

    @PATCH("list")
    suspend fun updateTodoItems(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoListRequestBody: TodoListRequestBody
    ): Response<TodoListResponseBody>

    @GET("list/{todoItemId}")
    suspend fun getTodoItem(@Path("todoItemId") todoItemId: String): Response<TodoItemResponseBody>

    @POST("list")
    suspend fun addTodoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoItemRequestBody: TodoItemRequestBody
    ): Response<TodoItemResponseBody>

    @PUT("list/{todoItemId}")
    suspend fun updateTodoItem(
        @Path("todoItemId") todoItemId: String,
        @Body todoItemRequestBody: TodoItemRequestBody
    ): Response<TodoItemResponseBody>

    @DELETE("list/{todoItemId}")
    suspend fun removeTodoItem(@Path("todoItemId") todoItemId: String): Response<TodoItemResponseBody>
}
