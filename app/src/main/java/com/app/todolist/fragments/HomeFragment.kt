package com.app.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.base.KotlinBaseFragment
import com.app.todolist.databinding.FragmentHomeBinding
 import com.app.todolist.viewmodel.HomeFragmentViewModel
import com.app.todolist.network.APIInterfaceTodoList


class HomeFragment(val baseActivity: KotlinBaseActivity) : KotlinBaseFragment() {

    lateinit var binding: FragmentHomeBinding
     lateinit var viewmodel: HomeFragmentViewModel
    private lateinit var mAPIInterfaceTodoList: APIInterfaceTodoList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        viewmodel.setBinder(binding, baseActivity)

    }



}