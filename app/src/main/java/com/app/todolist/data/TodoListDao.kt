package com.app.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.todolist.model.CategoryList
import com.app.todolist.model.TodoList


@Dao
interface TodoListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // <- Annotate the 'addUser' function below. Set the onConflict strategy to IGNORE so if exactly the same user exists, it will just ignore it.
    suspend fun addList(todolist: TodoList)


    @Update
    suspend fun updateList(todolist: TodoList)



    @Delete
    suspend fun deleteList(todolist: TodoList)


    @Query("DELETE FROM todo_list")
    suspend fun deleteAllList()

    @Query("SELECT * from todo_list ORDER BY id ASC") // <- Add a query to fetch all users (in user_table) in ascending order by their IDs.
    fun readAllData(): LiveData<List<TodoList>> // <- This means function return type is List. Specifically, a List of Users.


    //Category Database
    @Insert(onConflict = OnConflictStrategy.IGNORE) // <- Annotate the 'addUser' function below. Set the onConflict strategy to IGNORE so if exactly the same user exists, it will just ignore it.
    suspend fun addCategory(category: CategoryList)

    @Update
    suspend fun updateCategoryList(category: CategoryList)

    @Delete
    suspend fun deleteCategoryList(category: CategoryList)

    @Query("SELECT * from todo_category ORDER BY id ASC") // <- Add a query to fetch all users (in user_table) in ascending order by their IDs.
    fun readAllCategoryData(): LiveData<List<CategoryList>> // <- This means function return type is List. Specifically, a List of Users.


}