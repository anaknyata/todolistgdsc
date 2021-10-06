package com.dsc.todolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1, exportSchema = true)
abstract class TodoListDatabase: RoomDatabase(){

    abstract fun getTodoDao(): TodoDao

    companion object {
        val databaseName = "tododatabase"
        var todoListDatabase: TodoListDatabase? = null

        fun getInstance(context: Context): TodoListDatabase?{
            if (todoListDatabase == null){
                todoListDatabase = Room.databaseBuilder(context,
                    TodoListDatabase::class.java,
                    TodoListDatabase.databaseName)
                    .allowMainThreadQueries()
                    .build()
            }
            return todoListDatabase
        }
    }
}

