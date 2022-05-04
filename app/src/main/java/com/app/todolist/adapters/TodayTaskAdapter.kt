package com.app.todolist.adapters


import android.graphics.Paint
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider

import com.app.todolist.R
import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.dailog.BottomDailog
import com.app.todolist.listner.ItemChecked
import com.app.todolist.model.TodoList
import com.app.todolist.network.APIInterfaceTodoList
import kotlinx.android.synthetic.main.item_today_list2.view.*


class TodayTaskAdapter(
    val baseActivity: KotlinBaseActivity,
    var itemchecked: ItemChecked,
    val itemClick: (Int) -> Unit
) :
    BaseAdapter<TodoList>(R.layout.item_today_list2) {
    var isChecked = false
    private lateinit var mAPIInterfaceTodoList: APIInterfaceTodoList

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        holder.itemView.apply {

            mAPIInterfaceTodoList =
                ViewModelProvider(baseActivity).get(APIInterfaceTodoList::class.java)

            rvTodayList.setText(list[position].todo_titile)
            tvdateshow.setText(list[position].date)
            if (list[position].todo_checked.equals(1)) {
                checkbox.setImageResource(R.drawable.radiobuttonon)
//                rvTodayList.setPaintFlags(rvTodayList.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                maincontainer.setBackgroundColor(baseActivity.resources.getColor(com.github.dhaval2404.colorpicker.R.color.teal_50))
            } else {
                checkbox.setImageResource(R.drawable.radiobuttongrey)
//                rvTodayList.setPaintFlags(rvTodayList.getPaintFlags() or Paint.CURSOR_AFTER)
                maincontainer.setBackgroundColor(baseActivity.resources.getColor(com.github.dhaval2404.colorpicker.R.color.white))
            }

            checkbox.setOnClickListener {

                if (isChecked) {
                    checkbox.setImageResource(R.drawable.radiobuttonon)
//                    rvTodayList.setPaintFlags(rvTodayList.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                    maincontainer.setBackgroundColor(baseActivity.resources.getColor(com.github.dhaval2404.colorpicker.R.color.teal_50))
                    itemchecked.onItemViewClicked(position)
                    isChecked = false

                } else {
                    checkbox.setImageResource(R.drawable.radiobuttongrey)
//                    rvTodayList.setPaintFlags(rvTodayList.getPaintFlags() or (Paint.STRIKE_THRU_TEXT_FLAG))
                    maincontainer.setBackgroundColor(baseActivity.resources.getColor(com.github.dhaval2404.colorpicker.R.color.white))
                    itemchecked.onItemViewClicked(position)
                    isChecked = true
                }
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



            threedot.setOnClickListener {
                val popupMenu: PopupMenu = PopupMenu(baseActivity, threedot)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_edit -> {
                            val modalBottomSheet = BottomDailog(baseActivity, list[position]) {

                            }
                            modalBottomSheet.show(
                                baseActivity.supportFragmentManager,
                                BottomDailog.TAG
                            )
                        }
                        R.id.action_delete -> {
                            itemClick(position)
                        }

                    }
                    true
                })
                popupMenu.show()
            }


        }


    }


}