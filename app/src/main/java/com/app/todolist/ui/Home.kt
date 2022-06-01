package com.app.todolist.ui

 import android.app.AlarmManager
 import android.app.PendingIntent
 import android.content.Context
 import android.content.Intent
 import android.os.Bundle
 import android.util.Log
 import android.widget.Toast
 import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
 import com.app.todolist.base.AlarmReceiver
 import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.ActivityHomeBinding
 import com.app.todolist.extensions.isNotNull
 import com.app.todolist.fragments.HomeFragment
 import com.app.todolist.utils.Keys
 import com.app.todolist.viewmodel.HomeViewModel

class Home :  KotlinBaseActivity(R.id.container) {
    lateinit var binding: ActivityHomeBinding
    lateinit var viewmodel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)
        navigateToFragment(HomeFragment(this),bundle,true)
        viewmodel.setBinder(binding, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)



        if (intent.isNotNull()){
           val r =  intent?.getStringExtra(Keys.from)
            Log.e("werwrwerewrwer",r.toString())
        }

    }

}