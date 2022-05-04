package com.app.todolist.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


data class CategoryList2(
    val id: Int,
    val category_name: String,
    val category_icon: String,
    val category_icon_id: String,
    var isClick:Boolean = false
)