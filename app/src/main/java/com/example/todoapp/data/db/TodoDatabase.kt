package com.example.todoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.entities.TodoItemEntity

@Database(
    entities = [TodoItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun getTodoListDao(): TodoListDao
}