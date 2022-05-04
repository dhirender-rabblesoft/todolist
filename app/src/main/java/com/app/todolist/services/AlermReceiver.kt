package com.app.todolist.services

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.todolist.R
import com.app.todolist.fragments.HomeFragment

class AlermReceiver:BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {

        val intent = Intent(context, HomeFragment::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
        val pendingIntent = PendingIntent.getActivity(context,0,intent,0)
        val builder = Notification.Builder(context,"foxandroid")
            .setSmallIcon(R.drawable.category)
            .setContentTitle("Todo List Alerem")
            .setContentText("android alem text")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(Notification.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)


        val notificationMangerCompact = context?.let { NotificationManagerCompat.from(it) }
        notificationMangerCompact?.notify(123,builder.build())



    }
}