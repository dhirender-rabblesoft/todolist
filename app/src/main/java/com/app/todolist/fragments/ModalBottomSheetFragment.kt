package com.app.todolist.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
import com.app.todolist.base.KotlinBaseActivity
  import com.app.todolist.databinding.ModalBottomSheetContentBinding
import com.app.todolist.extensions.hideKeyboard
import com.app.todolist.extensions.toast
import com.app.todolist.viewmodel.AddTaskFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.bind


class ModalBottomSheetFragment(val baseActivity: KotlinBaseActivity) : BottomSheetDialogFragment()  {
    lateinit var binding: ModalBottomSheetContentBinding
    lateinit var viewmodel: AddTaskFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.modal_bottom_sheet_content, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(this).get(AddTaskFragmentViewModel::class.java)
        viewmodel.setBinder(binding, baseActivity)
        binding.newtask.setOnClickListener {
            if (binding.entertask.text.toString().trim().isNotEmpty()){
                val   newtask =   binding.entertask.text.toString().trim()
                Log.e("TheTheTheThe",  "New Task - "+newtask+ "Priority - "+  viewmodel.priorityinfo+ "category - "+ viewmodel.categoryInfo+ "datetime - "+ viewmodel.datetime)
                dismiss()
            } else {
                Toast.makeText(baseActivity,"Please Enter Todo" , Toast.LENGTH_LONG).show()

            }

        }
        binding.maincontainer.setOnClickListener {
            baseActivity.hideKeyboard()

        }
     }


    companion object {
        const val TAG = "ModalBottomSheet"
    }




}