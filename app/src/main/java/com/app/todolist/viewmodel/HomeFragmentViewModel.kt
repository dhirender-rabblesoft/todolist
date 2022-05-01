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
import com.app.todolist.dailog.AddCategoryDailog
import com.app.todolist.dailog.BottomDailog
import com.app.todolist.dailog.FilterDailog
import com.app.todolist.databinding.FragmentHomeBinding
import com.app.todolist.extensions.gone
import com.app.todolist.extensions.showConfirmAlert
import com.app.todolist.extensions.toast
import com.app.todolist.extensions.visible
import com.app.todolist.fragments.ModalBottomSheetFragment
import com.app.todolist.model.TodoJson2
import com.app.todolist.model.TodoListJson
import com.app.todolist.repository.TodoListingRepository
import com.app.todolist.ui.Home

class HomeFragmentViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: FragmentHomeBinding
    lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context
    var todolist = ArrayList<TodoJson2.TodoJson2Item>()
    var completeList = ArrayList<TodoJson2.TodoJson2Item>()
    var todoListingRepository: TodoListingRepository = TodoListingRepository(application)
    var iscompleteflag = true

    fun setBinder(binder: FragmentHomeBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        settoolbar()
        setClicks()
        setCategoryAdapter()
        setPriorityAdapter()
        gettodoapi()


    }

    private fun gettodoapi() {
        todoListingRepository.todolisting(baseActivity) {
            todolist.addAll(it)
//            todolist = it.
            Log.e("000000000000000000000", todolist.toString())
            setTodayTaskAdapter()

        }
    }

    private fun settoolbar() {
        binder.toolbar.tvtitile.setText("Inbox")
        binder.toolbar.menu.setOnClickListener {
            (baseActivity as Home).binding.drawerLayout.openDrawer(GravityCompat.START)
        }

    }

    private fun setClicks() {
//        binder.addtask.setOnClickListener {
//            baseActivity.navigateToFragment(ModalBottomSheetFragment(baseActivity))
//         }

        binder.addtask.setOnClickListener {
//            val modalBottomSheet = ModalBottomSheetFragment(baseActivity)
//            modalBottomSheet.show(baseActivity.supportFragmentManager, ModalBottomSheetFragment.TAG)
            val modalBottomSheet = BottomDailog(baseActivity) {

            }
            modalBottomSheet.show(baseActivity.supportFragmentManager, BottomDailog.TAG)
        }

        binder.toolbar.ivfilter.setOnClickListener {
            val dailog = FilterDailog(baseActivity) {

            }

            dailog.show(baseActivity.supportFragmentManager, dailog.getTag())
        }

        binder.addCategory.setOnClickListener {
            Log.e("RRRRRRRRRRRRRRRRR","ADdCATEGORY CLICK")
            val addCategoryDailog = AddCategoryDailog(baseActivity){

            }
            addCategoryDailog.show(baseActivity.supportFragmentManager,AddCategoryDailog.TAG)

        }


      binder.tvcomplete.setOnClickListener {


          if (iscompleteflag) {
              iscompleteflag = false
              binder.completeConatiner.visible()

          } else {
              iscompleteflag = true
              binder.completeConatiner.gone()

          }



      }

    }

    private fun setCategoryAdapter() {
        val categoryAdapter = CategoryAdapter(baseActivity) {

        }
        binder.rvCategory.adapter = categoryAdapter
    }


    private fun setPriorityAdapter() {
        val priorityAdapter = PriorityAdapter(baseActivity) {

        }
        binder.rvPriority.adapter = priorityAdapter
    }

    private fun setTodayTaskAdapter() {
        Log.e("oooooooooooooooooo", todolist.toString())
        val todaycategoryAdapter = TodayTaskAdapter(baseActivity) {
            todolist.forEach {
                if (it.completed) {
                    completeList.addAll(todolist)
                }

            }


//            baseActivity.showConfirmAlert("Are you sure you want to delete the Todo Task"," ","Ok","Cancel",onCancel = {
//                Log.e("tytytytty","303000000030")
//
//            },onConfirmed = {
//                Log.e("tytytytty","1010101010101010")
////                deletuser(userlist[it].id.toString())
//            } )
             setCompleteTaskAdapter()
        }

        todaycategoryAdapter.addNewList(todolist)
        binder.rvTodayList.adapter = todaycategoryAdapter
    }

    private fun setCompleteTaskAdapter() {
        val completeListAdapter = CompleteTaskAdapter(baseActivity) {

        }
//        completeListAdapter.addNewList(completeList)
        binder.rvCompleteTask.adapter = completeListAdapter
    }
}