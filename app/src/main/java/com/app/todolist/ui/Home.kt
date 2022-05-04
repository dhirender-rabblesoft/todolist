package com.app.todolist.ui

 import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.ActivityHomeBinding
 import com.app.todolist.fragments.HomeFragment
 import com.app.todolist.viewmodel.HomeViewModel

class Home :  KotlinBaseActivity(R.id.container) {
    lateinit var binding: ActivityHomeBinding
    lateinit var viewmodel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)
        navigateToFragment(HomeFragment(this),bundle,true)
        viewmodel.setBinder(binding, this)
    }
}