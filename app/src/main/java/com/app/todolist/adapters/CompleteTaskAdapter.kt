package com.app.todolist.adapters

 import com.app.todolist.R
 import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.model.TodoJson2
 import kotlinx.android.synthetic.main.item_complete_list.view.*

class CompleteTaskAdapter(val baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit)  : BaseAdapter<TodoJson2.TodoJson2Item>(R.layout.item_complete_list) {

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        holder.itemView.apply {
//            rvTodayList.setText(list[position].title)
        }
    }

    override fun getItemCount(): Int {
        return 10
    }


}