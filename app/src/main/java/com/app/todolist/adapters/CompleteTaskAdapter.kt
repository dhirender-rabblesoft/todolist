package com.app.todolist.adapters

import com.app.todolist.R
import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity

class CompleteTaskAdapter(val baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit)  : BaseAdapter<String>(R.layout.item_complete_list) {

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        itemClick(position)

    }

    override fun getItemCount(): Int {
        return 50

    }
}