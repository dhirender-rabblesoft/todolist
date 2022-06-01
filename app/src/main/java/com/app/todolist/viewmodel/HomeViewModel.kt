package com.app.todolist.viewmodel

import android.R
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.adapters.SideCateogryAdapter
import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.ActivityHomeBinding
import com.app.todolist.fragments.HomeFragment
import com.app.todolist.model.CategoryList
import com.app.todolist.network.APIInterfaceTodoList
import com.app.todolist.ui.AddCategory
import com.app.todolist.ui.CategoryListing
import com.app.todolist.utils.Keys


class HomeViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityHomeBinding
    private lateinit var mContext: Context
    lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mAPIInterfaceTodoList: APIInterfaceTodoList
    var bundle = Bundle()


    val categorylist :ArrayList<CategoryList>?= ArrayList<CategoryList>()
    fun setBinder(binder: ActivityHomeBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        this.binder.viewModel = this



//
//        //defalut icon set
//        binder.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        setclicks()
        completeCategoryList()

    }


    private fun completeCategoryList() {
        mAPIInterfaceTodoList =
            ViewModelProvider(baseActivity).get(APIInterfaceTodoList::class.java)
        mAPIInterfaceTodoList.readAllCategoryData.observe(baseActivity, Observer { catList ->
            Log.e("cdeeeeeee22222",catList.toString())

            categorylist?.clear()
            categorylist?.addAll(catList)
            setsidemenuadapter()
        })
    }

    private fun setsidemenuadapter(){
        Log.e("cdeeeeeee",categorylist.toString())
        val sideadapter = SideCateogryAdapter(baseActivity){
            Log.e("position151515", categorylist!![it].category_name)
            baseActivity.addFragmentToActivity(HomeFragment(baseActivity,categorylist[it].category_name))
            binder.drawerLayout.closeDrawers()

        }
        sideadapter.addNewList(categorylist)
        binder.showDrawer.rvsidemenu.adapter = sideadapter
    }

    private fun setclicks(){
        binder.showDrawer.closebtn.setOnClickListener {
            binder.drawerLayout.closeDrawers()
        }

        binder.showDrawer.categorycoantiner.setOnClickListener {

            baseActivity.openA(AddCategory::class)

        }

        binder.showDrawer.textinbox.setOnClickListener {
            baseActivity.addFragmentToActivity(HomeFragment(baseActivity,"Home"))

//             baseActivity.navigateToFragment(HomeFragment(baseActivity),bundle,true)
            binder.drawerLayout.closeDrawers()
        }
        binder.showDrawer.texthome.setOnClickListener {

        }
        binder.showDrawer.textpersonal.setOnClickListener {

        }
        binder.showDrawer.textlearning.setOnClickListener {

        }
        binder.showDrawer.textFitness.setOnClickListener {

        }
        binder.showDrawer.textBirthday.setOnClickListener {

        }

        binder.showDrawer.logoutconatiner.setOnClickListener {
            baseActivity.openA(CategoryListing::class)

        }
    }


}