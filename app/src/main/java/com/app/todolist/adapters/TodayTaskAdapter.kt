package com.app.todolist.adapters

import com.app.todolist.R
import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.fragments.ModalBottomSheetFragment
import kotlinx.android.synthetic.main.item_today_list2.view.*

class TodayTaskAdapter(val baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit)  : BaseAdapter<String>(R.layout.item_today_list2) {

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        holder.itemView.apply {
            ivedit.setOnClickListener {
                val modalBottomSheet = ModalBottomSheetFragment(baseActivity)
                modalBottomSheet.show(baseActivity.supportFragmentManager, ModalBottomSheetFragment.TAG)
            }
            ivdelete.setOnClickListener {
                itemClick(position)
            }

        }



    }

    override fun getItemCount(): Int {
        return 50

    }
}