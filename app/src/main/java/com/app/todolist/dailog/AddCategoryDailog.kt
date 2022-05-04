package com.app.todolist.dailog

import android.os.Bundle
 import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
import com.app.todolist.base.DialogBaseFragment
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.FragmentAddCategoryDailogBinding
import com.app.todolist.extensions.hideKeyboard
import com.app.todolist.model.CategoryList
import com.app.todolist.viewmodel.AddCategoryDailogViewModel

class AddCategoryDailog(var baseActivity: KotlinBaseActivity,val list :CategoryList =  CategoryList(0, "", "","") , val itemClick: (Int) -> Unit) :
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
        viewModel.setBinder(binding,baseActivity,list)


       setClick()
    }

    private fun setClick(){
        binding.addCategoryButton.setOnClickListener {
            if (viewModel.validation()) {
                if (list.category_name.isNotEmpty()) {
                   viewModel.updateData()
                    dismiss()
                } else {
                    viewModel.addCategoryList()
                    dismiss()
                }

            }
        }
        binding.conatinermain.setOnClickListener {
            baseActivity.hideKeyboard()
            dismiss()
        }
    }





    override fun onClick(p0: View?) {

    }
    companion object{
        val  TAG = "ADD_CATEGORY_DAILOG"
    }

}