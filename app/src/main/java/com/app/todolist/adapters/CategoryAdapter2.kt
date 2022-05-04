package com.app.todolist.adapters

import com.app.todolist.R
import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.model.CategoryList
import com.app.todolist.model.PriorityModel
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_category.view.checkbox
import kotlinx.android.synthetic.main.item_category.view.imgFlag
import kotlinx.android.synthetic.main.item_category.view.tvname
import kotlinx.android.synthetic.main.item_categorylisting.view.*
import kotlinx.android.synthetic.main.item_priority.view.*
import kotlinx.android.synthetic.main.item_today_list2.view.*

class CategoryAdapter2(val baseActivity: KotlinBaseActivity, val itemClick : (Int) -> Unit) : BaseAdapter<CategoryList>(R.layout.item_category) {
    var isclick = false

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {

        holder.itemView.apply {
            tvname.setText(list[position].category_name)

            if (list[position].isClick.equals(1)) {
                checkbox.setImageResource(R.drawable.radiobuttonon)
                itemClick(position)

            } else {
                checkbox.setImageResource(R.drawable.radiobuttongrey)
            }



            //set category
            if (list[position].category_icon_id.equals("1")) {
                holder.itemView.imgFlag.setImageResource(R.drawable.ic_baseline_category_24)
            }
            if (list[position].category_icon_id.equals("2")) {
                holder.itemView.imgFlag.setImageResource(R.drawable.home)
            }
            if (list[position].category_icon_id.equals("3")) {
                holder.itemView.imgFlag.setImageResource(R.drawable.person)
            }

            if (list[position].category_icon_id.equals("4")) {
                holder.itemView.imgFlag.setImageResource(R.drawable.study)
            }

            if (list[position].category_icon_id.equals("5")) {
                holder.itemView.imgFlag.setImageResource(R.drawable.barbell)
            }
            if (list[position].category_icon_id.equals("6")) {
                holder.itemView.imgFlag.setImageResource(R.drawable.calendar)
            }

//
            categorycontainer.setOnClickListener {
                for (i in list.indices){
                    if (i == position){
                        list[position].isClick = 1
                        itemClick(position)
                    }else {
                        list[i].isClick = 0
                    }
                }
                notifyDataSetChanged()
            }

        }

    }


}