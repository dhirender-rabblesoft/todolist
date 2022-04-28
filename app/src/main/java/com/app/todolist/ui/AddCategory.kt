package com.app.todolist.ui

 import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.ActivityAddCategoryBinding
import com.app.todolist.viewmodel.AddCategoryViewModel

class AddCategory : KotlinBaseActivity() {
    lateinit var binding: ActivityAddCategoryBinding
    lateinit var viewModel: AddCategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_category)
        viewModel = ViewModelProvider(this).get(AddCategoryViewModel::class.java)
        viewModel.setBinder(binding,this)

    }
}