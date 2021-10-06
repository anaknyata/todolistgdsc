package com.dsc.todolist

import androidx.room.*

@Dao
interface TodoDao{

    @Query("SELECT * FROM todo ORDER BY tId ASC")
    fun getTodoList(): MutableList<Todo>

    @Query("SELECT * FROM todo WHERE tId =:id")
    fun getTodoItem(id: Int): Todo

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

}