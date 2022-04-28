package com.app.todolist.adapters

import com.app.todolist.R
import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.model.PriorityModel
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(val baseActivity: KotlinBaseActivity, val itemClick : (Int) -> Unit) : BaseAdapter<PriorityModel>(R.layout.item_category) {
    var isclick = false

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {

        holder.itemView.apply {
            tvname.setText(list[position].name)
            imgFlag.setImageResource(list[position].priorityImg)
            if (list[position].isClick){
                categorycontainer.setBackgroundColor(baseActivity.resources.getColor(com.github.dhaval2404.colorpicker.R.color.teal_50))
            } else {
                categorycontainer.setBackgroundColor(baseActivity.resources.getColor(com.github.dhaval2404.colorpicker.R.color.white))
            }


            categorycontainer.setOnClickListener {
                for (i in list.indices){
                    if (i == position){
                        list[position].isClick = true
                    }else {
                        list[i].isClick = false
                    }
                }
                notifyDataSetChanged()
            }

        }

    }


}