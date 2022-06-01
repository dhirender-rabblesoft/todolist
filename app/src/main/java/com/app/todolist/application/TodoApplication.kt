package com.app.todolist.application

import android.app.Application
import android.os.Bundle
import com.app.todolist.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TodoApplication : Application()  {


    //     lateinit var firebaseAnalytics: FirebaseAnalytics
    val bundle = Bundle()


    override fun onCreate()
    {
        super.onCreate()
        myApp = this
        val modulelist= appModules
//        FirebaseApp.initializeApp(myApp!!)
//       firebaseAnalytics = FirebaseAnalytics.getInstance(myApp!!)

//        startKoin(this,modulelist)
        startKoin {
            androidContext(this@TodoApplication)
            modules(modulelist)
        }
    }
    companion object{
        var myApp: TodoApplication? = null
        const val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"
    }


}