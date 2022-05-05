package com.app.todolist.dailog

 import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
import com.app.todolist.base.DialogBaseFragment
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.FragmentBottomDailog2Binding
import com.app.todolist.extensions.hideKeyboard
import com.app.todolist.extensions.isNotNull
import com.app.todolist.model.TodoList
import com.app.todolist.viewmodel.AddTaskFragmentViewModel

class BottomDailog (var baseActivity: KotlinBaseActivity, val list: TodoList = TodoList(0,"","","","","",0), val itemClick: (Int) -> Unit) :
    DialogBaseFragment(){

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
        viewmodel.setBinder(binding, baseActivity,list)
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

    private fun setclick() {
         binding.newtask22.setOnClickListener {
            if (viewmodel.validation()) {
                if (list.todo_titile.isNotEmpty()) {
                    binding.newtask22.setText("Update")
                   viewmodel.updateData()
                    dismiss()
                } else {
                    binding.newtask22.setText("Add Task")
                    viewmodel.addtodoList()
                    viewmodel.createAlram()
                    dismiss()
                }
            }
        }

        binding.conatinermain.setOnClickListener {
            dismiss()
//            val input =
//                baseActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//            input.hideSoftInputFromWindow(baseActivity.currentFocus?.windowToken, 0)
        }
    }



    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
