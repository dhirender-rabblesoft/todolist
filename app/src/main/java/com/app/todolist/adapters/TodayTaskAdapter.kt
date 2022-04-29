package com.app.todolist.adapters

import com.app.todolist.R
import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.dailog.BottomDailog
import com.app.todolist.model.TodoJson2
import com.app.todolist.model.TodoListJson
import kotlinx.android.synthetic.main.item_today_list2.view.*

class TodayTaskAdapter(val baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit)  : BaseAdapter<TodoJson2.TodoJson2Item>(R.layout.item_today_list2) {

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        holder.itemView.apply {


            rvTodayList.setText(list[position].title)
            if (list[position].completed){
                checkbox.isChecked = true
                itemClick(position)
            }

            ivedit.setOnClickListener {
//                val modalBottomSheet = ModalBottomSheetFragment(baseActivity)
//                modalBottomSheet.show(baseActivity.supportFragmentManager, ModalBottomSheetFragment.TAG)
                val modalBottomSheet = BottomDailog(baseActivity){

                }
                modalBottomSheet.show(baseActivity.supportFragmentManager, BottomDailog.TAG)
            }
            ivdelete.setOnClickListener {
                itemClick(position)
            }

        }



    }

}