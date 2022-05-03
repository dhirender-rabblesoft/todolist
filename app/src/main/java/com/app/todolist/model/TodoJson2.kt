package com.app.todolist.model

class TodoJson2 : ArrayList<TodoJson2.TodoJson2Item>(){
    data class TodoJson2Item(
        var completed: Boolean,
        val id: Int,
        val title: String,
        val userId: Int
    )
}