package com.example.todoapp.data

import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class TodoItemsRepository {
    private val todoItems: MutableList<TodoItem> = mutableListOf()
    private val todoItemsFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(todoItems)

    init {
        todoItems.addAll(getInitialTodoItems())
    }

    fun getAll(): Flow<List<TodoItem>> = todoItemsFlow

    fun updateOrAdd(todoItem: TodoItem) {
        val index = todoItems.indexOfFirst { it.id == todoItem.id }
        if (index != -1) {
            todoItems[index] = todoItem
        } else {
            todoItems.add(todoItem)
        }
        updateTodoItemsFlow()
    }

    fun add(todoItem: TodoItem) {
        todoItems.add(todoItem)
        updateTodoItemsFlow()
    }

    fun update(todoItem: TodoItem) {
        val index = todoItems.indexOfFirst { it.id == todoItem.id }
        if (index != -1) {
            todoItems[index] = todoItem
            updateTodoItemsFlow()
        }
    }

    fun remove(todoItem: TodoItem) {
        todoItems.remove(todoItem)
        updateTodoItemsFlow()
    }

    fun findById(id: String): TodoItem? = todoItems.find { it.id == id }

    private fun updateTodoItemsFlow() {
        todoItemsFlow.value = todoItems
    }

    private fun getInitialTodoItems(): List<TodoItem> = listOf(
        TodoItem(id = "1", text = "Покормить черепаху", priority = Priority.HIGH, deadline = Date()),
        TodoItem(id = "2", text = "Выгулять кота", deadline = Date()),
        TodoItem(id = "3", text = "Записать видео с домашкой", priority = Priority.LOW, isCompleted = true),
        TodoItem(id = "4", text = "Не представляю, что можно придумать, чтобы это занимало больше трех строк. Может быть...",
            priority = Priority.LOW, isCompleted = true, modifiedAt = Date()),
        TodoItem(id = "5", text = "Я пришёл к тебе с приветом,\n" +
                "Рассказать, что солнце встало,\n" +
                "Что оно горячим светом\n" +
                "По листам затрепетало;\n" +
                "\n" +
                "Рассказать, что лес проснулся,\n" +
                "Весь проснулся, веткой каждой,\n" +
                "Каждой птицей встрепенулся\n" +
                "И весенней полон жаждой;\n" +
                "\n" +
                "Рассказать, что с той же страстью,\n" +
                "Как вчера, пришёл я снова,\n" +
                "Что душа всё так же счастью\n" +
                "И тебе служить готова;\n" +
                "\n" +
                "Рассказать, что отовсюду\n" +
                "На меня весельем веет,\n" +
                "Что не знаю сам, что́ буду\n" +
                "Петь — но только песня зреет.",
            Priority.LOW, deadline =  Date())
    )
}
