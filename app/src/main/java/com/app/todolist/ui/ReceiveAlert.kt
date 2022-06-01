package com.app.todolist.ui

 import android.content.Intent
 import android.os.Bundle
 import androidx.databinding.DataBindingUtil
 import com.app.todolist.R
 import com.app.todolist.base.Alarm
 import com.app.todolist.base.KotlinBaseActivity
 import com.app.todolist.databinding.ReceiveAlertBinding
 import com.app.todolist.services.AlarmService


class ReceiveAlert :  KotlinBaseActivity(R.id.container) {
    var alarm: Alarm? = null
    lateinit var binding: ReceiveAlertBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.receive_alert)
        if (intent.getStringExtra("dhrin")!=null)
        {
            if (bundle != null) alarm =
                bundle.getSerializable(getString(R.string.arg_alarm_obj)) as Alarm?
            dismissAlarm()

        }



    }

    private fun dismissAlarm() {
        if (alarm != null) {
            alarm?.isStarted = false
            alarm?.cancelAlarm(baseContext)
//            alarmsListViewModel.update(alarm)
        }
        val intentService = Intent(applicationContext, AlarmService::class.java)
        applicationContext.stopService(intentService)
        finish()
    }
}