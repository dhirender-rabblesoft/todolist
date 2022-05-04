package com.app.todolist.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
import com.app.todolist.adapters.CategoryListingAdapter
import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.dailog.AddCategoryDailog
import com.app.todolist.databinding.ActivityCategoryListingBinding
import com.app.todolist.extensions.gone
import com.app.todolist.model.CategoryList
import com.app.todolist.network.APIInterfaceTodoList


class CategoryListingViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityCategoryListingBinding
    lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context
    private lateinit var mAPIInterfaceTodoList: APIInterfaceTodoList
    var categorylist: ArrayList<CategoryList>? = ArrayList<CategoryList>()

    fun setBinder(binder: ActivityCategoryListingBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        settoolbar()
        setclick()
        completeCategoryList()

    }


    private fun completeCategoryList() {
        mAPIInterfaceTodoList =
            ViewModelProvider(baseActivity).get(APIInterfaceTodoList::class.java)
        mAPIInterfaceTodoList.readAllCategoryData.observe(baseActivity, Observer { catList ->
            categorylist?.clear()
            categorylist?.addAll(catList)
            setCateogorylistAdapter()
        })
    }


    private fun setclick() {
        binder.addcategory.setOnClickListener {

            val addCategoryDailog = AddCategoryDailog(baseActivity) {

            }
            addCategoryDailog.show(baseActivity.supportFragmentManager, AddCategoryDailog.TAG)
        }

        binder.toolbar.ivfilter.gone()
    }

    private fun setCateogorylistAdapter() {
        val categoryListAdapter = CategoryListingAdapter(baseActivity) {

        }
        categoryListAdapter.addNewList(categorylist)
        binder.rvCategoryListing.adapter = categoryListAdapter

    }

    private fun settoolbar() {
        binder.toolbar.tvtitile.setText("Category Listing")
        binder.toolbar.menu.setImageResource(R.drawable.ic_baseline_arrow_back_24)
        binder.toolbar.menu.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

}