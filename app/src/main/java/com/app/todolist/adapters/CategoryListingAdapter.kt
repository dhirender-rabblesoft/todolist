package com.app.todolist.adapters

import com.app.todolist.R
import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.model.PriorityModel
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryListingAdapter(val baseActivity: KotlinBaseActivity, val itemClick : (Int) -> Unit) : BaseAdapter<String>(R.layout.item_categorylisting) {


    override fun onBindViewHolder(holder: IViewHolder, position: Int) {

        holder.itemView.apply {


        }

    }

    override fun getItemCount(): Int {
        return 15
    }

}