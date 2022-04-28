package com.app.todolist.adapters

import com.app.todolist.R
import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.model.PriorityModel
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_category.view.imgFlag
import kotlinx.android.synthetic.main.item_category.view.tvname
import kotlinx.android.synthetic.main.item_priority.view.*

class DateFilterAdapter(val baseActivity: KotlinBaseActivity, val itemClick:(Int)->Unit) : BaseAdapter<PriorityModel>(R.layout.item_priority) {

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {

        holder.itemView.apply {
            imgFlag.setImageResource(list[position].priorityImg)
            tvname.setText(list[position].name)

            if (list[position].isClick){
                priorityContianer.setBackgroundColor(baseActivity.resources.getColor(com.github.dhaval2404.colorpicker.R.color.teal_50))
            }else{
                priorityContianer.setBackgroundColor(baseActivity.resources.getColor(com.github.dhaval2404.colorpicker.R.color.white))
            }

            priorityContianer.setOnClickListener {
                for (i in list.indices){
                    if (i== position){
                        list[position].isClick = true
                    } else {
                        list[i].isClick = false
                    }
                }
                notifyDataSetChanged()
            }

        }

    }


}