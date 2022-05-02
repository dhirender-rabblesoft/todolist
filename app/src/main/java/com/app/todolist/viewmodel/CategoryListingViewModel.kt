package com.app.todolist.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.app.todolist.R
import com.app.todolist.adapters.CategoryListingAdapter
import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.dailog.AddCategoryDailog
import com.app.todolist.databinding.ActivityCategoryListingBinding
import com.app.todolist.extensions.gone


class CategoryListingViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityCategoryListingBinding
    lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context

    fun setBinder(binder: ActivityCategoryListingBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        settoolbar()
        setCateogorylistAdapter()
        setclick()
    }

    private fun setclick(){
        binder.addcategory.setOnClickListener {
            Toast.makeText(baseActivity,"Toast now",Toast.LENGTH_LONG).show()
            Log.e("RRRRRRRRRRRRRRRRR","ADdCATEGORY CLICK")
            val addCategoryDailog = AddCategoryDailog(baseActivity){

            }
            addCategoryDailog.show(baseActivity.supportFragmentManager, AddCategoryDailog.TAG)
        }
        binder.toolbar.ivfilter.gone()
    }
    private fun setCateogorylistAdapter() {
        val categoryListAdapter = CategoryListingAdapter(baseActivity) {

        }
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