package com.app.todolist.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.app.todolist.R
import com.app.todolist.base.KotlinBaseActivity
import java.text.SimpleDateFormat

class SplashScreen : KotlinBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        timeconvert()
//        getDate()
        Handler(Looper.getMainLooper()).postDelayed({

            openA(Home::class)
            finishAffinity()
        },3000)
    }


    private fun getDate() {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val dateString: String = simpleDateFormat.format(1654033200000L)
        Log.e("Days115151 -" ,  dateString.toString() )

    }

    private fun timeconvert(){
        var diff = 1654033200000
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        val elapsedDays = diff / daysInMilli
        diff %= daysInMilli

        val elapsedHours = diff / hoursInMilli
        diff %= hoursInMilli

        val elapsedMinutes = diff / minutesInMilli
        diff %= minutesInMilli

        val elapsedSeconds = diff / secondsInMilli

        Log.e("Days -" , elapsedDays.toString() + " hours - "+ elapsedHours.toString() + " minu "+elapsedMinutes.toString() )

    }
}