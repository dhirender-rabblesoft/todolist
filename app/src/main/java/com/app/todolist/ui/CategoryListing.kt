package com.app.todolist.ui

 import android.os.Bundle
 import androidx.databinding.DataBindingUtil
 import androidx.lifecycle.ViewModelProvider
 import com.app.todolist.R
import com.app.todolist.base.KotlinBaseActivity
 import com.app.todolist.databinding.ActivityCategoryListingBinding
 import com.app.todolist.viewmodel.CategoryListingViewModel

class CategoryListing : KotlinBaseActivity() {
    lateinit var binding: ActivityCategoryListingBinding
    lateinit var viewmodel: CategoryListingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_listing)
        viewmodel = ViewModelProvider(this).get(CategoryListingViewModel::class.java)
        viewmodel.setBinder(binding,this )

    }


}