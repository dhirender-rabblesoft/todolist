package com.app.todolist.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.app.todolist.listner.KotlinBaseListener
import com.app.todolist.utils.SharedPreferenceManager

 import org.koin.android.ext.android.inject

abstract class KotlinBaseFragment(@LayoutRes val view: Int = 0) : Fragment() {

    protected lateinit var baselistener: KotlinBaseListener
    var bundle=Bundle()

    var rationale = "Please provide permission location permission"
    val preferencemanger: SharedPreferenceManager by inject()



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is KotlinBaseListener) {
            baselistener = context
        } else {
            throw IllegalStateException("You Must have to extends your activity with KotlinBaseActivity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }






    private fun showAlert(s: String) {

        baselistener?.showAlert(s)
    }

    @DrawableRes
    open fun setBreadCrumbsImage(): Int? {
        return null
    }

    open fun setBreadCrumbsTitle(): String {
        return ""
    }

    //    fun nointernershowToast()
//    {
//        val myToast = Toast.makeText(activity,getString(R.string.internetconnection), Toast.LENGTH_SHORT)
//        myToast.setGravity(Gravity.CENTER,0,0)
//        myToast.show()
//
//
//    }
    fun showtoast(string: String)
    {
        val myToast = Toast.makeText(activity,string, Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.CENTER,0,0)
        myToast.show()

    }
    fun showProgress() {
        baselistener.showProgress()
    }

    fun hideProgress() {
        baselistener.hideProgress()
    }

    fun onBackPressed() {
        activity?.onBackPressed()
    }

    fun showErrorMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


}