package com.app.todolist.services


import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import com.app.todolist.R
import com.app.todolist.application.TodoApplication.Companion.CHANNEL_ID
import com.app.todolist.base.Alarm
import com.app.todolist.ui.Home
import com.app.todolist.ui.ReceiveAlert
import com.app.todolist.utils.Keys
import java.io.IOException
import java.util.*


class AlarmService() : Service()
{


    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null
    var alarm: Alarm? = null
    var ringtone: Uri? = null
    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.isLooping = true
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        ringtone = RingtoneManager.getActualDefaultRingtoneUri(
            this.baseContext,
            RingtoneManager.TYPE_ALARM
        )
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
                if (intent.getStringExtra("karan")!=null)
                {

                    Log.e("karan", intent.getStringExtra("karan").toString())
                }

        var bundle = intent.getBundleExtra(getString(R.string.bundle_alarm_obj))
        Log.e("ewrewrwer", intent.getBundleExtra(getString(R.string.bundle_alarm_obj)).toString())
        if (bundle != null) alarm = bundle.getSerializable(getString(R.string.arg_alarm_obj)) as Alarm?

        val notificationIntent = Intent(this, ReceiveAlert::class.java)
       // bundle.putString("dhrin","kumar")
        notificationIntent.putExtra("dhrin", "kumar")
        notificationIntent.putExtra("data",bundle)
        //notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_USER_ACTION or Intent.FLAG_ACTIVITY_SINGLE_TOP);

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        Log.e("95ddddd95ddd",alarm?.title.toString())
        var alarmTitle = alarm?.title

//        if (alarm != null) {
//            alarmTitle = alarm!!.title
//            try {
//                mediaPlayer!!.setDataSource(this.baseContext, Uri.parse(alarm!!.tone))
//                mediaPlayer!!.prepareAsync()
//            } catch (ex: IOException) {
//                ex.printStackTrace()
//            }
//        } else {
//            try {
//                mediaPlayer!!.setDataSource(this.baseContext, ringtone!!)
//                mediaPlayer!!.prepareAsync()
//            } catch (ex: IOException) {
//                ex.printStackTrace()
//            }
//        }
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Todo List Reminder")
            .setContentText(alarmTitle)
            .setSmallIcon(R.drawable.barbell)
            .setSound(null)
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setFullScreenIntent(pendingIntent, true)
            .build()
        mediaPlayer!!.setOnPreparedListener { mediaPlayer -> mediaPlayer.start() }
        if (alarm!!.isVibrate) {
            val pattern = longArrayOf(0, 100, 1000)
            vibrator!!.vibrate(pattern, 0)
        }
        startForeground(1, notification)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.stop()
        vibrator!!.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}