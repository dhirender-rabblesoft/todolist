package com.app.todolist.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
 import com.app.todolist.model.TodoList

@Database(entities = [TodoList::class], version = 1, exportSchema = true )
abstract class TodoListDatabase :
    RoomDatabase() {// <- Add 'abstract' keyword and extends RoomDatabase

    abstract fun TodoListDao(): TodoListDao

    companion object {
        @Volatile
        private var INSTANCE: TodoListDatabase? = null

        fun getDatabase(context: Context): TodoListDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoListDatabase::class.java,
                    "todo_list"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}