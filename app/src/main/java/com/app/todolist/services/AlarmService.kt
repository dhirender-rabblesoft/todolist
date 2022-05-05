package com.app.todolist.services

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.app.todolist.fragments.HomeFragment
import com.app.todolist.ui.Home
import com.app.todolist.utils.Keys


class AlarmService() : Service(){
    private   val NOTIFICATION_ID = 3;

    public constructor(name:String) : this() {

    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        onHandleIntent(intent)
        return super.onStartCommand(intent, flags, startId)
    }


     fun onHandleIntent(intent: Intent?) {
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Keys.CHANNEL_ID,
                Keys.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "YOUR_NOTIFICATION_CHANNEL_DESCRIPTION"
            mNotificationManager.createNotificationChannel(channel)
        }
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, Keys.CHANNEL_ID)
            .setSmallIcon(R.mipmap.sym_def_app_icon) // notification icon
            .setContentTitle("title") // title for notification
            .setContentText("Message") // message for notification
            .setAutoCancel(true) // clear notification after click
        val i = Intent(applicationContext, HomeFragment::class.java)
        val pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(pi)
        mNotificationManager.notify(0, mBuilder.build())
    }



    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


}