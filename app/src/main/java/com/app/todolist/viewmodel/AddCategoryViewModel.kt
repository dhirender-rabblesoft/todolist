package com.app.todolist.viewmodel

import android.app.Application
import android.content.Context
import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.ActivityAddCategoryBinding
import com.app.todolist.extensions.hideKeyboard
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape

class AddCategoryViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityAddCategoryBinding
    lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context

    fun setBinder(binder: ActivityAddCategoryBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity

        setClicks()
    }

    private fun setClicks() {

        binder.colorbox.setOnClickListener {
            // Kotlin Code
            val mDefaultColor =	"#800000"
            ColorPickerDialog
                .Builder(baseActivity)        				// Pass Activity Instance
                .setTitle("Pick Theme")           	// Default "Choose Color"
                .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
                .setDefaultColor(mDefaultColor)     // Pass Default Color
                .setColorListener { color, colorHex ->
                    // Handle Color Selection
                    binder.colorbox.setBackgroundColor(color)
                }
                .show()

        }
        binder.maincontainer.setOnClickListener {
            baseActivity.hideKeyboard()
        }
        binder.closebtn.setOnClickListener {
            baseActivity.onBackPressed()
        }

    }


}