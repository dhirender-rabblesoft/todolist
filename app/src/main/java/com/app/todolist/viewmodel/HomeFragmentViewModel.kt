package com.app.todolist.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.adapters.CategoryAdapter
import com.app.todolist.adapters.CompleteTaskAdapter
import com.app.todolist.adapters.PriorityAdapter
import com.app.todolist.adapters.TodayTaskAdapter
import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.dailog.AddCategoryDailog
import com.app.todolist.dailog.BottomDailog
import com.app.todolist.dailog.FilterDailog
import com.app.todolist.data.TodoListDatabase
import com.app.todolist.databinding.FragmentHomeBinding
import com.app.todolist.extensions.gone
import com.app.todolist.extensions.visible
import com.app.todolist.listner.ItemChecked
import com.app.todolist.model.TodoJson2
import com.app.todolist.model.TodoList
import com.app.todolist.network.APIInterfaceTodoList
import com.app.todolist.repository.TodoListingRepository
import com.app.todolist.ui.Home


class HomeFragmentViewModel(application: Application) : AppViewModel(application), ItemChecked {
    private lateinit var binder: FragmentHomeBinding
    lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context
    private lateinit var mAPIInterfaceTodoList: APIInterfaceTodoList
    var todolist = ArrayList<TodoJson2.TodoJson2Item>()
    var completeList = ArrayList<TodoJson2.TodoJson2Item>()
    var todoListingRepository: TodoListingRepository = TodoListingRepository(application)
    var iscompleteflag = true

    var todaycategoryAdapter: TodayTaskAdapter? = null
    var list_of_todo: ArrayList<TodoList> = ArrayList<TodoList>()
    var position = 0

    fun setBinder(binder: FragmentHomeBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        settoolbar()
        setClicks()
        getalllisting()
        setCategoryAdapter()
        setPriorityAdapter()
//        gettodoapi()
        completeTaskListner()


    }

    private fun completeTaskListner() {
        binder.switchlist.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mAPIInterfaceTodoList =
                    ViewModelProvider(baseActivity).get(APIInterfaceTodoList::class.java)
                mAPIInterfaceTodoList.readAllData.observe(baseActivity, Observer { todolist ->

//            list_of_todo?.addAll(todolist)
                    list_of_todo?.clear()
                    val data = todolist.filter {
                        it.todo_checked == 1
                    }
                    list_of_todo?.addAll(data)
                    Log.e("itemsize0000", list_of_todo?.size!!.toString())

                    Log.e("00000ffffffffffff", list_of_todo.toString())
                    setTodayTaskAdapter()
                })
            }
            else {
                list_of_todo.clear()
                  getalllisting()
            }
        })
    }

    private fun getalllisting() {
        // TodoListViewModel
        mAPIInterfaceTodoList =
            ViewModelProvider(baseActivity).get(APIInterfaceTodoList::class.java)
        mAPIInterfaceTodoList.readAllData.observe(baseActivity, Observer { todolist ->

//            list_of_todo?.addAll(todolist)
            val data = todolist.filter {
                it.todo_checked == 0
            }
            list_of_todo.clear()
            list_of_todo.addAll(data)

             setTodayTaskAdapter()
        })
    }

//    private fun gettodoapi() {
//        todoListingRepository.todolisting(baseActivity) {
//            todolist.addAll(it)
////            todolist = it.
//            Log.e("000000000000000000000", todolist.toString())
//            setTodayTaskAdapter()
//
//        }
//    }

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
            Log.e("RRRRRRRRRRRRRRRRR", "ADdCATEGORY CLICK")
            val addCategoryDailog = AddCategoryDailog(baseActivity) {

            }
            addCategoryDailog.show(baseActivity.supportFragmentManager, AddCategoryDailog.TAG)

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

        todaycategoryAdapter = TodayTaskAdapter(baseActivity, this) {

            position = it


            val itemselect = list_of_todo?.get(it)
            deletetodo(itemselect!!)
        }
        todaycategoryAdapter?.addNewList(list_of_todo)
        binder.rvTodayList.adapter = todaycategoryAdapter
    }

    private fun deletetodo(item: TodoList) {
        val builder = AlertDialog.Builder(baseActivity)
        builder.setPositiveButton("Yes") { _, _ ->     // Make a "Yes" option and set action if the user selects "Yes"

            mAPIInterfaceTodoList.deleteList(item)    // Execute : delete user
            Toast.makeText(                                // Notification if a user is deleted successfully
                baseActivity,
                "Successfully removed ${item.todo_titile}",
                Toast.LENGTH_SHORT
            )
                .show()

        }
        builder.setNegativeButton("No") { _, _ -> }    // Make a "No" option and set action if the user selects "No"
        builder.setTitle("Delete ${item.todo_titile} ?")  // Set the title of the prompt with a sentence saying the first name of the user inside the app (using template string)
        builder.setMessage("Are you sure to remove ${item.todo_titile} ?")  // Set the message of the prompt with a sentence saying the first name of the user inside the app (using template string)
        builder.create()
            .show()  // Create a prompt with the configuration above to ask the user (the real app user which is human)
    }

    private fun setCompleteTaskAdapter() {
        val completeListAdapter = CompleteTaskAdapter(baseActivity) {

        }
//        completeListAdapter.addNewList(completeList)
        binder.rvCompleteTask.adapter = completeListAdapter
    }

    override fun onItemViewClicked(position: Int) {

        var postStatus=1
        if (list_of_todo[position].todo_checked.equals(1))
        {
            postStatus=0
        }

        var dbModel:TodoList?=null
        dbModel=list_of_todo[position]
        dbModel.todo_checked=postStatus
        mAPIInterfaceTodoList.updateList(dbModel)
        todolist.clear()
//        getalllisting()


    }
}