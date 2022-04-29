package com.app.todolist.viewmodel

import android.app.Application
import android.content.Context
import android.os.Bundle
import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.ActivityHomeBinding
import com.app.todolist.databinding.ActivityLoginBinding

class LoginViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityLoginBinding
    private lateinit var mContext: Context
    lateinit var baseActivity: KotlinBaseActivity
    var bundle = Bundle()

    fun setBinder(binder: ActivityLoginBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        this.binder.viewModel = this
//
//        //defalut icon set
//        binder.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


    }

}