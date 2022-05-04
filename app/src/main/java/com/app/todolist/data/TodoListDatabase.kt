package com.app.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.todolist.model.CategoryList
import com.app.todolist.model.TodoList

@Database(entities = [TodoList::class,CategoryList::class], version = 5  )
abstract class TodoListDatabase :
    RoomDatabase() {// <- Add 'abstract' keyword and extends RoomDatabase

    abstract fun TodoListDao(): TodoListDao
//    abstract fun categoryDao(): CategoryListDao?

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

        val migration_3_4 = object  :Migration(3,4){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS 'todo_category' (category_id INTEGER NOT NULL, " +
                            "category_name TEXT NOT NULL, " +
                            "category_icon TEXT NOT NULL, " +
                            "category_color TEXT NOT NULL, " +
                            "PRIMARY KEY (category_id))"
                )
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
//                    .addMigrations(migration_1_2, migration_2_3, migration_3_4)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}