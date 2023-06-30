package com.example.todoapp.data.local.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.todoapp.data.local.entities.TodoItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todo_items")
    fun getTodoItemsFlow(): Flow<List<TodoItemEntity>>

    @Query("SELECT * FROM todo_items")
    suspend fun getTodoItems(): List<TodoItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItem(todoItem: TodoItemEntity)

    @Query("DELETE FROM todo_items WHERE id = :itemId")
    suspend fun deleteTodoItem(itemId: String)

    @Update
    suspend fun updateTodoItem(todoItem: TodoItemEntity)

    @Query("SELECT * FROM todo_items WHERE id = :itemId")
    suspend fun findById(itemId: String): TodoItemEntity?

    @Query("SELECT * FROM todo_items WHERE id IN (:itemsIds)")
    suspend fun getTodoItems(itemsIds: List<String>): List<TodoItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItems(todoItems: List<TodoItemEntity>)

    @Query("DELETE FROM todo_items WHERE id IN (:itemsIds)")
    suspend fun deleteTodoItems(itemsIds: List<String>)

    @Transaction
    suspend fun updateTodoItems(todoItems: List<TodoItemEntity>) {
        val existingTodoItems = getTodoItems(todoItems.map { it.id })

        val itemsToInsert = mutableListOf<TodoItemEntity>()
        val itemsToDelete = mutableListOf<TodoItemEntity>()

        todoItems.forEach { remoteItem ->
            Log.d(TAG, "todoItems: 1")
            itemsToInsert.add(remoteItem)
        }

        existingTodoItems.forEach { localItem ->
            Log.d(TAG, "existingTodoItems: 2")
            val item = todoItems.find { it.id == localItem.id }
            if (item == null){
                itemsToDelete.add(localItem)
            }
        }

        if (itemsToInsert.isNotEmpty()) {
            Log.d(TAG, "itemsToInsert: ${itemsToInsert.size}")
            insertTodoItems(itemsToInsert)
        }

        Log.d(TAG, "itemsToDelete: ${itemsToDelete.size}")
        if (itemsToDelete.isNotEmpty()) {
            Log.d(TAG, "itemsToDelete: ${itemsToDelete.size}")
            deleteTodoItems(itemsToDelete.map { it.id })
        }
    }

    companion object {
        private const val TAG = "TodoItemDao"
    }
}
