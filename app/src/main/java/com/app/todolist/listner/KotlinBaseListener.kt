package com.app.todolist.listner
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlin.reflect.KClass


interface KotlinBaseListener {


    fun openA(kClass: KClass<out AppCompatActivity>, extras: Bundle? = Bundle())
    fun navigateToFragment(java: Fragment, extras: Bundle? = Bundle(),addToBackstack:Boolean=true)
    fun addFragment(fragment: Fragment, extras: Bundle? = Bundle(), tag: String = "",addToBackstack:Boolean=true)
    fun addChildFragment(childFragmentManager: FragmentManager, container: Int, kClass: Fragment,extras: Bundle? = Bundle(),addToBackstack:Boolean=true)
    fun replaceChildFragment(childFragmentManager: FragmentManager, container: Int, kClass:Fragment,extras: Bundle? = Bundle(),addToBackstack:Boolean=true)
    fun openAForResult(kClass: KClass<out AppCompatActivity>, bundle: Bundle, code: Int)
    fun showProgress()
    fun hideProgress()
    fun showAlert(message: String)
    fun getFragment(kClass: Fragment): Fragment?
    fun navigateToFragment(fragment:Fragment, extras: Bundle?, userTag: String,addToBackstack:Boolean=true)
}