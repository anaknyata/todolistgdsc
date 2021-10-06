package com.dsc.todolist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todo")

class Todo (

        @ColumnInfo(name = "title")
        var title:String?,
        @ColumnInfo(name = "desc")
        var desc:String?,
        @ColumnInfo(name = "date")
        var date:String?,
        @ColumnInfo(name = "ischecked")
        var ischecked:Boolean = false,
        @PrimaryKey(autoGenerate = true)
        var tId: Int? = null)