package com.app.todolist.dailog

 import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.app.todolist.R
 import com.app.todolist.adapters.CategoryAdapter
 import com.app.todolist.adapters.DateFilterAdapter
 import com.app.todolist.adapters.PriorityAdapter
 import com.app.todolist.base.DialogBaseFragment
import com.app.todolist.base.KotlinBaseActivity
 import com.app.todolist.databinding.ItemFilterDailogBinding
 import com.app.todolist.model.PriorityModel
 import org.koin.android.ext.android.bind

class FilterDailog(var baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit) :
    DialogBaseFragment(), View.OnClickListener {
    lateinit var binding: ItemFilterDailogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_filter_dailog,
            container,
            false
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPriorityAdapter()
        setCategoryAdapter()
        setDateFilterAdapter()
        setclick()


    }
    private fun setclick(){
        binding.cancelBtn.setOnClickListener(this)
        binding.btnconatiner.setOnClickListener(this)
    }


    private fun setPriorityAdapter(){
        val list = ArrayList<PriorityModel>()
        list.add(PriorityModel(baseActivity.getString(R.string.high_priority),R.drawable.red_flag,))
        list.add(PriorityModel(baseActivity.getString(R.string.medium_priority),R.drawable.flag_yellow) )
        list.add(PriorityModel(baseActivity.getString(R.string.low_priority),R.drawable.flag_blue))
        list.add( PriorityModel(baseActivity.getString(R.string.no_priority),R.drawable.flag_black))
        val priorityAdapter = PriorityAdapter(baseActivity){

        }
        priorityAdapter.addNewList(list)
        binding.rvPriorityfilter.adapter =  priorityAdapter
    }

    private fun setCategoryAdapter(){
        val categoryList = ArrayList<PriorityModel>()
        categoryList.add(PriorityModel(baseActivity.getString(R.string.home),R.drawable.category))
        categoryList.add(PriorityModel(baseActivity.getString(R.string.personal),R.drawable.category))
        categoryList.add(PriorityModel(baseActivity.getString(R.string.learning),R.drawable.category))
        categoryList.add(PriorityModel(baseActivity.getString(R.string.fitness),R.drawable.category))
        categoryList.add(PriorityModel(baseActivity.getString(R.string.birthday),R.drawable.category))

        val priorityAdapter = CategoryAdapter(baseActivity){

        }
        priorityAdapter.addNewList(categoryList)
        binding.rvCateogryfilter.adapter =  priorityAdapter
    }

    private fun setDateFilterAdapter(){
        val list = ArrayList<PriorityModel>()
        list.add(PriorityModel(baseActivity.getString(R.string.today),R.drawable.today))
        list.add(PriorityModel(baseActivity.getString(R.string.yesterday),R.drawable.yesterday) )
        list.add(PriorityModel(baseActivity.getString(R.string.last_week),R.drawable.month))
         val priorityAdapter = DateFilterAdapter(baseActivity){

         }
        priorityAdapter.addNewList(list)
        binding.rvDateFilter.adapter =  priorityAdapter
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.cancel_btn ->{
                dismiss()
            }
            R.id.btnconatiner ->{
                dismiss()
            }
        }


    }


}