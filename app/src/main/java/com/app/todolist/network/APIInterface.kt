package com.app.todolist.network

import com.app.todolist.model.TodoJson2
import com.app.todolist.model.TodoListJson
import retrofit2.Call
import retrofit2.http.GET
 import retrofit2.http.Headers

interface APIInterface {

@Headers("Accept: application/json")
@GET("todos")
fun todolisting(): Call<TodoJson2>
}