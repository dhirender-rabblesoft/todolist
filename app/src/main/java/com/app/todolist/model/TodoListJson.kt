package com.app.todolist.model

data class TodoListJson(val data : ArrayList<TodoListJsonItem>)
{
    data class TodoListJsonItem(
        val completed: Boolean,
        val id: Int,
        val title: String,
        val userId: Int
    )
}