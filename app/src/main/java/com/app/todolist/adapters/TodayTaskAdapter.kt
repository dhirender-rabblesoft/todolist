package com.app.todolist.adapters


import android.graphics.Paint
import com.app.todolist.R
import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.dailog.BottomDailog
import com.app.todolist.listner.ItemChecked
import com.app.todolist.model.TodoList
import kotlinx.android.synthetic.main.item_today_list2.view.*


class TodayTaskAdapter(val baseActivity: KotlinBaseActivity,  var itemchecked: ItemChecked,val itemClick: (Int) -> Unit ) :
    BaseAdapter<TodoList>(R.layout.item_today_list2) {
    var isChecked = false

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        holder.itemView.apply {

            rvTodayList.setText(list[position].todo_titile)
            tvdateshow.setText(list[position].date)

            checkbox.setOnClickListener {
                if (isChecked){
                    checkbox.setImageResource(R.drawable.radiobuttonon)
                    rvTodayList.setPaintFlags(rvTodayList.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                    itemchecked.onItemViewClicked(position)
                    isChecked = false

                }
                else{
                    checkbox.setImageResource(R.drawable.radiobuttongrey)
                    isChecked = true
                }

            }
            if (list[position].todo_checked.equals(1)){
                checkbox.setImageResource(R.drawable.radiobuttonon)
                rvTodayList.setPaintFlags(rvTodayList.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                maincontainer.setBackgroundColor(baseActivity.resources.getColor(com.github.dhaval2404.colorpicker.R.color.teal_50))
            }


            //set todo list
            if (list[position].todo_priority.equals(baseActivity.getString(R.string.high_priority))) {
                holder.itemView.ivPriority.setImageResource(R.drawable.red_flag)
            }
            if (list[position].todo_priority.equals(baseActivity.getString(R.string.medium_priority))) {
                holder.itemView.ivPriority.setImageResource(R.drawable.flag_yellow)
            }
            if (list[position].todo_priority.equals(baseActivity.getString(R.string.low_priority))) {
                holder.itemView.ivPriority.setImageResource(R.drawable.flag_blue)
            }
            if (list[position].todo_priority.equals(baseActivity.getString(R.string.no_priority))) {
                holder.itemView.ivPriority.setImageResource(R.drawable.flag_black)
            }

            //set category
            if (list[position].todo_category.equals(baseActivity.getString(R.string.inbox))) {
                holder.itemView.ivCategory.setImageResource(R.drawable.ic_baseline_category_24)
            }
            if (list[position].todo_category.equals(baseActivity.getString(R.string.home))) {
                holder.itemView.ivCategory.setImageResource(R.drawable.home)
            }
            if (list[position].todo_category.equals(baseActivity.getString(R.string.personal))) {
                holder.itemView.ivCategory.setImageResource(R.drawable.person)
            }

            if (list[position].todo_category.equals(baseActivity.getString(R.string.learning))) {
                holder.itemView.ivCategory.setImageResource(R.drawable.study)
            }

            if (list[position].todo_category.equals(baseActivity.getString(R.string.fitness))) {
                holder.itemView.ivCategory.setImageResource(R.drawable.barbell)
            }
            if (list[position].todo_category.equals(baseActivity.getString(R.string.birthday))) {
                holder.itemView.ivCategory.setImageResource(R.drawable.calendar)
            }






//            if (list[position].todo_checked == 1) {
//                checkbox.isChecked = true
//
//            }

            ivedit.setOnClickListener {
//                val modalBottomSheet = ModalBottomSheetFragment(baseActivity)
//                modalBottomSheet.show(baseActivity.supportFragmentManager, ModalBottomSheetFragment.TAG)
                val modalBottomSheet = BottomDailog(baseActivity, list[position]) {

                }
                modalBottomSheet.show(baseActivity.supportFragmentManager, BottomDailog.TAG)
            }
            ivdelete.setOnClickListener {
                 itemClick(position)
            }

        }


    }


}