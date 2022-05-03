package com.app.todolist.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "todo_category")
data class CategoryList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category_name: String,
    val category_icon: String,
) : Parcelable
