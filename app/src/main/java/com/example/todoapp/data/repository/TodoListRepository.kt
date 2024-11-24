package com.example.todoapp.data.repository

import com.example.todoapp.data.db.TodoListDao
import com.example.todoapp.data.entities.TodoItemEntity
import javax.inject.Inject

class TodoListRepository @Inject constructor(
    private val shoppingDao: TodoListDao
) {

    suspend fun insert(item: TodoItemEntity) = shoppingDao.insertTodoItem(item)

    fun getTodoList() = shoppingDao.getTodoList()

}