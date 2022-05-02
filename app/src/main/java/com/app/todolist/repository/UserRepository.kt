package com.app.todolist.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.app.todolist.data.TodoListDao
import com.app.todolist.model.TodoList

class UserRepository(private val todoListDao: TodoListDao) {


    val readAllData: LiveData<List<TodoList>> = todoListDao.readAllData()

    suspend fun addList(todoList: TodoList) {
        todoListDao.addList(todoList)
    }

    suspend fun updateList(todoList: TodoList) {
        todoListDao.updateList(todoList)
    }

    suspend fun deleteList(todoList: TodoList) {
        todoListDao.deleteList(todoList)
    }

    suspend fun deleteAllList() {
        todoListDao.deleteAllList()
    }
}