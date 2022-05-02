package com.app.todolist.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.Bundle
 import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.ActivityHomeBinding
import com.app.todolist.ui.AddCategory
import com.app.todolist.ui.CategoryListing
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape


class HomeViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityHomeBinding
    private lateinit var mContext: Context
    lateinit var baseActivity: KotlinBaseActivity

    var bundle = Bundle()

    fun setBinder(binder: ActivityHomeBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        this.binder.viewModel = this
//
//        //defalut icon set
//        binder.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        setclicks()

    }

    private fun setclicks(){
        binder.showDrawer.closebtn.setOnClickListener {
            binder.drawerLayout.closeDrawers()
        }

        binder.showDrawer.categorycoantiner.setOnClickListener {

            baseActivity.openA(AddCategory::class)

        }
        binder.showDrawer.logoutconatiner.setOnClickListener {
            baseActivity.openA(CategoryListing::class)

        }
    }


}