package com.app.todolist.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.view.GravityCompat
import com.app.todolist.adapters.CategoryAdapter
import com.app.todolist.adapters.CompleteTaskAdapter
import com.app.todolist.adapters.PriorityAdapter
import com.app.todolist.adapters.TodayTaskAdapter
import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.dailog.FilterDailog
import com.app.todolist.databinding.FragmentHomeBinding
 import com.app.todolist.extensions.showConfirmAlert
import com.app.todolist.extensions.visible
import com.app.todolist.fragments.ModalBottomSheetFragment
import com.app.todolist.ui.Home

class HomeFragmentViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: FragmentHomeBinding
    lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context

    fun setBinder(binder: FragmentHomeBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        settoolbar()
        setClicks()
        setCategoryAdapter()
        setPriorityAdapter()
        setTodayTaskAdapter()

    }

    private fun settoolbar() {
        binder.toolbar.tvtitile.setText("Today Task")
        binder.toolbar.menu.setOnClickListener {
            (baseActivity as Home).binding.drawerLayout.openDrawer(GravityCompat.START)
        }

    }

    private fun setClicks() {
//        binder.addtask.setOnClickListener {
//            baseActivity.navigateToFragment(ModalBottomSheetFragment(baseActivity))
//         }

        binder.addtask.setOnClickListener {
            val modalBottomSheet = ModalBottomSheetFragment(baseActivity)
            modalBottomSheet.show(baseActivity.supportFragmentManager, ModalBottomSheetFragment.TAG)
        }

        binder.toolbar.ivfilter.setOnClickListener {
            val dailog = FilterDailog(baseActivity){

            }

            dailog.show(baseActivity.supportFragmentManager, dailog.getTag())
        }




    }

    private fun setCategoryAdapter() {
        val categoryAdapter = CategoryAdapter(baseActivity){

        }
        binder.rvCategory.adapter = categoryAdapter
    }


    private fun setPriorityAdapter() {
        val priorityAdapter = PriorityAdapter(baseActivity){

        }
        binder.rvPriority.adapter = priorityAdapter
    }

    private fun setTodayTaskAdapter() {
        val categoryAdapter = TodayTaskAdapter(baseActivity){
            baseActivity.showConfirmAlert("Are you sure you want to delete the Todo Task"," ","Ok","Cancel",onCancel = {
                Log.e("tytytytty","303000000030")

            },onConfirmed = {
                Log.e("tytytytty","1010101010101010")
//                deletuser(userlist[it].id.toString())
            } )
            binder.completeConatiner.visible()
            setCompleteTaskAdapter()
        }
        binder.rvTodayList.adapter = categoryAdapter
    }
    private fun setCompleteTaskAdapter() {
        val categoryAdapter = CompleteTaskAdapter(baseActivity){

        }
        binder.rvCompleteTask.adapter = categoryAdapter
    }
}