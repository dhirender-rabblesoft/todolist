package com.app.todolist.adapters

import com.app.todolist.R
import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.model.CategoryList
import com.app.todolist.model.PriorityModel
import kotlinx.android.synthetic.main.ballon_category_list.view.*
import kotlinx.android.synthetic.main.item_category.view.*

class BallonCategoryAdapter(val baseActivity: KotlinBaseActivity, val itemClick : (Int) -> Unit) : BaseAdapter<CategoryList>(R.layout.ballon_category_list) {


    override fun onBindViewHolder(holder: IViewHolder, position: Int) {

        holder.itemView.apply {
            textinbox.setText(list[position].category_name)
            clmainconainter.setOnClickListener {
                itemClick(position)
            }


            //set categoryicon
            when(list[position].category_icon_id){
                "1" -> ivinbox.setImageResource(R.drawable.ic_baseline_category_24)
                "2" -> ivinbox.setImageResource(R.drawable.home)
                "3" ->  ivinbox.setImageResource(R.drawable.person)
                "4" ->  ivinbox.setImageResource(R.drawable.study)
                "5" ->  ivinbox.setImageResource(R.drawable.barbell)
                "6" ->  ivinbox.setImageResource(R.drawable.calendar)
            }

        }

    }


}