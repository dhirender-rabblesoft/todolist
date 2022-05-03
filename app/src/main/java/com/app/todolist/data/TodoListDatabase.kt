package com.app.todolist.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.todolist.model.TodoList

@Database(entities = [TodoList::class], version = 3  )
abstract class TodoListDatabase :
    RoomDatabase() {// <- Add 'abstract' keyword and extends RoomDatabase

    abstract fun TodoListDao(): TodoListDao

    companion object {
        val migration_1_2 = object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE todo_list ADD COLUMN date default '' ")
            }
        }

        val migration_2_3 = object :Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE todo_list ADD COLUMN time default '' ")
            }
        }
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
                    .addMigrations(migration_2_3)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}