package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.db.TodoDatabase
import com.example.todoapp.data.db.TodoListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext app: Context
    ): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "TodoDatabase.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesDatabaseDao(db: TodoDatabase): TodoListDao = db.getTodoListDao()

}