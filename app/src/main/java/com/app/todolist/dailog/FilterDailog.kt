package com.app.todolist.dailog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
import com.app.todolist.adapters.CategoryAdapter
import com.app.todolist.adapters.CategoryAdapter2
import com.app.todolist.adapters.DateFilterAdapter
import com.app.todolist.adapters.PriorityAdapter
import com.app.todolist.base.DialogBaseFragment
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.ItemFilterDailogBinding
import com.app.todolist.model.CategoryList
import com.app.todolist.model.PriorityModel
import com.app.todolist.network.APIInterfaceTodoList
import org.koin.android.ext.android.bind

class FilterDailog(var baseActivity: KotlinBaseActivity, val itemClick: (String, String) -> Unit) :
    DialogBaseFragment(), View.OnClickListener {
    lateinit var binding: ItemFilterDailogBinding
    var selectedPriority = ""
    var selectedCategory = ""
    var priorityAdapter: PriorityAdapter? = null
    private lateinit var mAPIInterfaceTodoList: APIInterfaceTodoList
    val categorylist: ArrayList<CategoryList>? = ArrayList<CategoryList>()
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
        completeCategoryList()
        setclick()


    }


    private fun completeCategoryList() {
        mAPIInterfaceTodoList =
            ViewModelProvider(baseActivity).get(APIInterfaceTodoList::class.java)
        mAPIInterfaceTodoList.readAllCategoryData.observe(baseActivity, Observer { catList ->
            Log.e("cdeeeeeee22222", catList.toString())

            categorylist?.clear()
            categorylist?.addAll(catList)
            setCategoryAdapter()
        })
    }

    private fun setclick() {
        binding.cancelBtn.setOnClickListener(this)
        binding.btnconatiner.setOnClickListener(this)

    }


    private fun setPriorityAdapter() {
        val list = ArrayList<PriorityModel>()
        list.add(
            PriorityModel(
                baseActivity.getString(R.string.high_priority),
                R.drawable.red_flag,
            )
        )
        list.add(
            PriorityModel(
                baseActivity.getString(R.string.medium_priority),
                R.drawable.flag_yellow
            )
        )
        list.add(PriorityModel(baseActivity.getString(R.string.low_priority), R.drawable.flag_blue))
        list.add(PriorityModel(baseActivity.getString(R.string.no_priority), R.drawable.flag_black))
        priorityAdapter = PriorityAdapter(baseActivity) {

            selectedPriority = list[it].name


        }
        priorityAdapter?.addNewList(list)
        binding.rvPriorityfilter.adapter = priorityAdapter
    }

    private fun setCategoryAdapter() {


        val priorityAdapter = CategoryAdapter2(baseActivity) {

            selectedCategory = categorylist!![it].category_name


        }
        priorityAdapter.addNewList(categorylist)
        binding.rvCateogryfilter.adapter = priorityAdapter
    }

    private fun setDateFilterAdapter() {
        val list = ArrayList<PriorityModel>()
        list.add(PriorityModel(baseActivity.getString(R.string.today), R.drawable.today))
        list.add(PriorityModel(baseActivity.getString(R.string.yesterday), R.drawable.yesterday))
        list.add(PriorityModel(baseActivity.getString(R.string.last_week), R.drawable.month))
        val priorityAdapter = DateFilterAdapter(baseActivity) {

        }
        priorityAdapter.addNewList(list)
        binding.rvDateFilter.adapter = priorityAdapter
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.cancel_btn -> {
                dismiss()
            }
            R.id.btnconatiner -> {
                itemClick(selectedCategory, selectedPriority)
                dismiss()
            }
        }


    }


}