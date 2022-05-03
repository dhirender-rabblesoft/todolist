package com.app.todolist.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "todo_list")
data class TodoList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val todo_titile: String,
    val todo_category: String,
    val todo_priority: String,
    val date:String,
    val time:String,
    var todo_checked: Int = 0,
) : Parcelable
