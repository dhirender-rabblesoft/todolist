package com.app.todolist.dailog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
import com.app.todolist.base.DialogBaseFragment
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.FragmentBottomDailog2Binding
import com.app.todolist.databinding.FragmentBottomDailogBinding
import com.app.todolist.extensions.hideKeyboard
import com.app.todolist.extensions.isNotNull
import com.app.todolist.model.TodoList
import com.app.todolist.viewmodel.AddTaskFragmentViewModel
import kotlinx.android.synthetic.main.fragment_bottom_dailog2.*


class BottomDailog (var baseActivity: KotlinBaseActivity, val list: TodoList = TodoList(0,"","","",false), val itemClick: (Int) -> Unit) :
    DialogBaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentBottomDailog2Binding
    lateinit var viewmodel: AddTaskFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_dailog2, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(AddTaskFragmentViewModel::class.java)
        viewmodel.setBinder(binding, baseActivity)

        isCancelable = true

        Log.e("cheekdfkjdfjdf",list.toString())
        setclick()
        setdata()
//        binding.newtask.setOnClickListener {
//            if (binding.entertask.text.toString().trim().isNotEmpty()){
//                val   newtask =   binding.entertask.text.toString().trim()
//                Log.e("TheTheTheThe",  "New Task - "+newtask+ "Priority - "+  viewmodel.priorityinfo+ "category - "+ viewmodel.categoryInfo+ "datetime - "+ viewmodel.datetime)
//                dismiss()
//            } else {
//                Toast.makeText(baseActivity,"Please Enter Todo" , Toast.LENGTH_LONG).show()
//
//            }
//
//        }
        binding.maincontainer.setOnClickListener {
            baseActivity.hideKeyboard()

        }
    }


    private fun setdata(){
        if (list.todo_titile.isNotEmpty() && list.todo_titile.isNotNull()){

        }
    }


    private fun setclick(){
        conatinermain.setOnClickListener(this)
    }



    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.conatinermain ->{
                dismiss()
            }
        }

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
