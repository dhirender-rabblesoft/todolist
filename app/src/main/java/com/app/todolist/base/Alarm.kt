package com.app.todolist.base

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.todolist.R
import com.app.todolist.utils.Keys
import java.io.Serializable
import java.util.*


class Alarm(
    var alarmId :Int,
    var hour: Int,
    var minute: Int,
    var title: String,
    var isStarted: Boolean,
    val isRecurring: Boolean,
    var isVibrate:Boolean = true


) :
    Serializable {

    fun schedule(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val bundle = Bundle()
        bundle.putSerializable(context.getString(R.string.arg_alarm_obj), this)
        intent.putExtra(context.getString(R.string.bundle_alarm_obj), bundle)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId, intent, 0
        )
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0


        // if alarm time has already passed, increment day by 1
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH]
        }

        Log.e("chgeckmilisdfsdfs", calendar.timeInMillis.toString())
        if (!isRecurring) {
            var toastText: String? = null
            try {
//                toastText = java.lang.String.format(
//                    "One Time Alarm %s scheduled for %s at %02d:%02d",
//                    title, DayUtil.toDay(calendar[Calendar.DAY_OF_WEEK]),
//                    hour,
//                    minute
//                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(context, "One Time Alarm toastText", Toast.LENGTH_LONG).show()
            Log.e("checkmiliseconds", calendar.timeInMillis.toString())

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmPendingIntent
            )

        } else {
//            val toastText = String.format(
//                "Recurring Alarm %s scheduled for %s at %02d:%02d",
//                title,
//                hour,
//                minute
//            )
            Toast.makeText(context, "Recurring Alarm toastText", Toast.LENGTH_LONG).show()
            val RUN_DAILY = (24 * 60 * 60 * 1000).toLong()
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                RUN_DAILY,
                alarmPendingIntent
            )
        }

        isStarted = true
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId, intent, 0
        )
        alarmManager.cancel(alarmPendingIntent)
        isStarted = false
        val toastText = String.format(
            "Alarm cancelled for ",
            hour,
            minute
        )
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        Log.i("cancel", toastText)
    }

//    val recurringDaysText: String?
//        get() {
//            if (!isRecurring) {
//                return null
//            }
//            var days = ""
//            if (isMonday) {
//                days += "Mo "
//            }
//            if (isTuesday) {
//                days += "Tu "
//            }
//            if (isWednesday) {
//                days += "We "
//            }
//            if (isThursday) {
//                days += "Th "
//            }
//            if (isFriday) {
//                days += "Fr "
//            }
//            if (isSaturday) {
//                days += "Sa "
//            }
//            if (isSunday) {
//                days += "Su "
//            }
//            return days
//        }

}