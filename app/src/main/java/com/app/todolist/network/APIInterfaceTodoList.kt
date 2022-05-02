package com.app.todolist.network

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.todolist.data.TodoListDatabase
import com.app.todolist.model.TodoList
import com.app.todolist.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class APIInterfaceTodoList(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<TodoList>>
    private val repository: UserRepository

    init {
        val todoDao = TodoListDatabase.getDatabase(application).TodoListDao()
        repository= UserRepository(todoDao)
        readAllData = repository.readAllData
    }

    fun addList(todoList: TodoList) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("rerererererererer",todoList.toString())
            repository.addList(todoList)
        }
    }

    fun updateList(todoList: TodoList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateList(todoList)
        }
    }
    fun deleteList(todoList: TodoList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteList(todoList)
        }
    }
    fun deleteAllLists() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllList()
        }
    }


}