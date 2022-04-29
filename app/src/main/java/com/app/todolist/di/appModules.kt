package com.app.todolist.di

import com.app.todolist.utils.NetworkCheck
import com.app.todolist.utils.SharedPreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module



private val dataModule = module {


    single {
        NetworkCheck(androidContext().applicationContext)
    }
    single {
        SharedPreferenceManager(androidContext().applicationContext)
    }

}
val appModules = listOf(dataModule)
