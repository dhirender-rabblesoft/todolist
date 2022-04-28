package com.app.todolist.base

import android.app.Application
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel

open class AppViewModel : AndroidViewModel {


    internal lateinit var toast: Toast

    constructor(application: Application) : super(application) {
        initToast()
    }

    fun initToast() {
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_LONG)

    }


    fun getContext(): Context {
        return (getApplication() as Application).applicationContext
    }

    fun showToast(text: String?) {
        initToast()
        toast.setText(text)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
    }

    fun showToast(resId: Int) {
        showToast(getContext().getString(resId))
    }

    fun getLabelText(resId: Int): String {
        return getContext().getString(resId)
    }


//    fun isLocationPermissionAllowed(context: Context): Boolean {
//        val list = listOf<String>(
//                Manifest.permission.INTERNET,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//        )
//        val managePermissions = ManagePermissionsImp((context as MainActivity), list, 0)
//        if (managePermissions.isPermissionsGranted() == 0) {
//            return true
//        }
//        return false
//    }

}
