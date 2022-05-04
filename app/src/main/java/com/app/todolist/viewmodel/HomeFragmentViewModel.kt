package com.app.todolist.viewmodel

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.todolist.R
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
import com.app.todolist.model.CategoryList
import com.app.todolist.model.TodoJson2
import com.app.todolist.model.TodoList
import com.app.todolist.network.APIInterfaceTodoList
import com.app.todolist.repository.TodoListingRepository
import com.app.todolist.ui.Home
import com.app.todolist.utils.Keys


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
    var temp_todoList: ArrayList<TodoList> = ArrayList<TodoList>()
    var position = 0
    var modalBottomSheet: BottomDailog? = null
    var bundle = Bundle()
    var categoryTilte = ""
    val categorylist: ArrayList<CategoryList> = ArrayList<CategoryList>()


    fun setBinder(binder: FragmentHomeBinding, baseActivity: KotlinBaseActivity, catTitle: String) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity

        bundle = (mContext as Activity).intent.extras!!

        categoryTilte = catTitle
        completeCategoryList()

        settoolbar()
        setClicks()
        getalllisting()
//        setCategoryAdapter()
        setPriorityAdapter()

//        gettodoapi()


        binder.refreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                binder.rvTodayList.visible()
                binder.nodata.gone()
                temp_todoList.clear()
                binder.toolbar.tvtitile.setText(baseActivity.getString(R.string.inbox))
                setTodayTaskAdapter(list_of_todo)
                binder.refreshLayout.isRefreshing = false
            }

        })



        list_of_todo.forEach {
            if (it.todo_category.equals(categoryTilte)) {
                binder.toolbar.tvtitile.setText(categoryTilte)
                temp_todoList.clear()
                temp_todoList.add(it)
                setTodayTaskAdapter(temp_todoList)

            }
        }

    }


    private fun completeCategoryList() {
        mAPIInterfaceTodoList =
            ViewModelProvider(baseActivity).get(APIInterfaceTodoList::class.java)
        mAPIInterfaceTodoList.readAllCategoryData.observe(baseActivity, Observer { catList ->
            if (catList.size.equals(0)) {
                adddefaultcategory()
            }
            categorylist.addAll(catList)

        })
    }


    private fun adddefaultcategory() {
        mAPIInterfaceTodoList.addCategory(
            CategoryList(
                category_name = "Inbox",
                category_icon = "",
                category_icon_id = "1"
            )
        )
        mAPIInterfaceTodoList.addCategory(
            CategoryList(
                category_name = "Home",
                category_icon = "",
                category_icon_id = "2"
            )
        )
        mAPIInterfaceTodoList.addCategory(
            CategoryList(
                category_name = "Personal",
                category_icon = "",
                category_icon_id = "3"
            )
        )
        mAPIInterfaceTodoList.addCategory(
            CategoryList(
                category_name = "Learning",
                category_icon = "",
                category_icon_id = "4"
            )
        )
        mAPIInterfaceTodoList.addCategory(
            CategoryList(
                category_name = "Fitness",
                category_icon = "",
                category_icon_id = "5"
            )
        )
        mAPIInterfaceTodoList.addCategory(
            CategoryList(
                category_name = "Birthday",
                category_icon = "",
                category_icon_id = "6"
            )
        )
    }


    private fun getalllisting() {
        // TodoListViewModel
        mAPIInterfaceTodoList =
            ViewModelProvider(baseActivity).get(APIInterfaceTodoList::class.java)
        mAPIInterfaceTodoList.readAllData.observe(baseActivity, Observer { todolist ->

            list_of_todo.clear()
            list_of_todo.addAll(todolist)
            setTodayTaskAdapter(list_of_todo)
        })
    }



    private fun settoolbar() {
        binder.toolbar.tvtitile.setText("Inbox")
        binder.toolbar.menu.setOnClickListener {
            (baseActivity as Home).binding.drawerLayout.openDrawer(GravityCompat.START)
        }

    }

    private fun setClicks() {

        binder.addtask.setOnClickListener {
            modalBottomSheet = BottomDailog(baseActivity) {

                modalBottomSheet?.dismiss()

            }
            modalBottomSheet?.show(baseActivity.supportFragmentManager, BottomDailog.TAG)
        }

        binder.toolbar.ivfilter.setOnClickListener {
            val dailog = FilterDailog(baseActivity) { category, priority ->
                temp_todoList.clear()
                list_of_todo.forEach {
                    if (it.todo_category.equals(category) || it.todo_priority.equals(priority)) {
                        temp_todoList.add(it)
                    }
                }
                if (temp_todoList.isEmpty()) {
                    binder.rvTodayList.gone()
                    binder.nodata.visible()
                } else {
                    binder.rvTodayList.visible()
                    binder.nodata.gone()
                    setTodayTaskAdapter(temp_todoList)
                }


            }
            dailog.show(baseActivity.supportFragmentManager, dailog.getTag())
        }

        binder.addCategory.setOnClickListener {
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

    private fun setTodayTaskAdapter(todayList: ArrayList<TodoList>) {

        todaycategoryAdapter = TodayTaskAdapter(baseActivity, this) {
            position = it
            val itemselect = todayList?.get(it)
            deletetodo(itemselect!!)
        }
        todaycategoryAdapter?.addNewList(todayList)
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
            ).show()

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
        binder.rvCompleteTask.adapter = completeListAdapter
    }

    override fun onItemViewClicked(position: Int) {

        var postStatus = 1
        if (list_of_todo[position].todo_checked.equals(1)) {
            postStatus = 0
        }

        var dbModel: TodoList? = null
        dbModel = list_of_todo[position]
        dbModel.todo_checked = postStatus
        mAPIInterfaceTodoList.updateList(dbModel)
        todolist.clear()
    }
}