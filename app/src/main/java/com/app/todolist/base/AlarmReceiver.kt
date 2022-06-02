package com.app.todolist.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.todolist.R
import com.app.todolist.services.AlarmService
import com.app.todolist.services.RescheduleAlarmsService
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    var alarm: Alarm? = null
    override fun onReceive(context: Context?, intent: Intent?) {


        if (Intent.ACTION_BOOT_COMPLETED == intent!!.action) {
            Log.e("ppipopopopoo", "dsdfefsdfsfsdfdsfsdf")
            val toastText = String.format("Alarm Reboot")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            startRescheduleAlarmsService(context!!)
        } else {
            val bundle = intent!!.getBundleExtra(context!!.getString(R.string.bundle_alarm_obj))

            Log.e("ppipopopopoo", "345646574256421")
            if (bundle != null) alarm =
                bundle.getSerializable(context!!.getString(R.string.arg_alarm_obj)) as Alarm?
//            val toastText = String.format("Alarm Received")
//            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            if (alarm != null) {
                startAlarmService(context, alarm!!)
                if (!alarm?.isRecurring!!) {
                    startAlarmService(context, alarm!!)
                } else {

                }
            }
        }

    }


    private fun startAlarmService(context: Context, alarm1: Alarm) {
        val intentService = Intent(context, AlarmService::class.java)
        val bundle = Bundle()
        bundle.putSerializable(context.getString(R.string.arg_alarm_obj), alarm1)
        intentService.putExtra(context.getString(R.string.bundle_alarm_obj), bundle)
        intentService.putExtra("karan", "kochar")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

    private fun startRescheduleAlarmsService(context: Context) {

        val intentService = Intent(context, RescheduleAlarmsService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }

    }
}