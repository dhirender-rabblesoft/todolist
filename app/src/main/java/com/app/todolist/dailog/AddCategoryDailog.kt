package com.app.todolist.dailog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
import com.app.todolist.base.DialogBaseFragment
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.FragmentAddCategoryDailogBinding
import com.app.todolist.viewmodel.AddCategoryDailogViewModel

class AddCategoryDailog(var baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit) :
    DialogBaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentAddCategoryDailogBinding
    lateinit var viewModel: AddCategoryDailogViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_category_dailog,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddCategoryDailogViewModel::class.java)
        viewModel.setBinder(binding,baseActivity)


        binding.conatinermain.setOnClickListener {
            dismiss()
        }
    }





    override fun onClick(p0: View?) {

    }
    companion object{
        val  TAG = "ADD_CATEGORY_DAILOG"
    }

}