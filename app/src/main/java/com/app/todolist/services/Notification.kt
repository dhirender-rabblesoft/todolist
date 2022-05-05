package com.app.todolist.services

import android.R
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.app.todolist.fragments.HomeFragment
import com.app.todolist.utils.Keys

class Notification: BroadcastReceiver() {
    private   val NOTIFICATION_ID = 4;
    override fun onReceive(context: Context?, intent: Intent?) {
        val notification = NotificationCompat.Builder(context!!, Keys.CHANNEL_ID)
            .setSmallIcon(R.mipmap.sym_def_app_icon) // notification icon
            .setContentTitle("title") // title for notification
            .setContentText("Message") // message for notification
            .setAutoCancel(true) // clear notification after click
            .build()

        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(NOTIFICATION_ID,notification)
    }
}