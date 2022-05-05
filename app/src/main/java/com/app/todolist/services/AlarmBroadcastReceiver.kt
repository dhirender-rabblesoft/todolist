package com.app.todolist.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.todolist.ui.AlarmActivity
import com.app.todolist.utils.Keys

class AlarmBroadcastReceiver:BroadcastReceiver() {
      var title:String? =""
      var desc:String? = ""
      var date:String? = ""
      var time:String? = ""
    override fun onReceive(context: Context?, intent: Intent) {


         title = intent.getStringExtra(Keys.TITLE)
         desc = intent.getStringExtra(Keys.DESC)
         date = intent.getStringExtra(Keys.DATE)
         time = intent.getStringExtra(Keys.TIME)


        val i = Intent(context, AlarmActivity::class.java)
        i.putExtra("TITLE", title)
        i.putExtra("DESC", desc)
        i.putExtra("DATE", date)
        i.putExtra("TIME", time)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context!!.startActivity(i)

    }
}