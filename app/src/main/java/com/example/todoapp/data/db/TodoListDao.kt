package com.example.todoapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.data.entities.TodoItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodoItem(item: TodoItemEntity): Long

    @Query("SELECT * FROM todo_items")
    fun getTodoList(): Flow<List<TodoItemEntity>>

}