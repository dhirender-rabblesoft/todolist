package com.app.todolist.adapters

import android.util.Log
import androidx.appcompat.widget.PopupMenu
import com.app.todolist.R
import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.dailog.AddCategoryDailog
import com.app.todolist.dailog.BottomDailog
import com.app.todolist.model.CategoryList

import kotlinx.android.synthetic.main.item_categorylisting.view.*

class CategoryListingAdapter(val baseActivity: KotlinBaseActivity, val itemClick : (Int) -> Unit) : BaseAdapter<CategoryList>(R.layout.item_categorylisting) {


    override fun onBindViewHolder(holder: IViewHolder, position: Int) {

        holder.itemView.apply {

            threedot.setOnClickListener {
                val popupMenu: PopupMenu = PopupMenu(baseActivity,threedot)
                popupMenu.menuInflater.inflate(R.menu.popup_menu2,popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.action_edit -> {
                            val modalBottomSheet = AddCategoryDailog(baseActivity, list[position]) {

                            }
                            modalBottomSheet.show(baseActivity.supportFragmentManager, BottomDailog.TAG)
                        }
                        R.id.action_delete -> {
                            itemClick(position)
                        }

                    }
                    true
                })
                popupMenu.show()
            }

            Log.e("categorycheck",list[position].category_icon)

            textlearning.setText(list[position].category_name)

            //set category
            if (list[position].category_icon_id.equals("1")) {
                holder.itemView.ivCategoryImg.setImageResource(R.drawable.ic_baseline_category_24)
            }
            if (list[position].category_icon_id.equals("2")) {
                holder.itemView.ivCategoryImg.setImageResource(R.drawable.home)
            }
            if (list[position].category_icon_id.equals("3")) {
                holder.itemView.ivCategoryImg.setImageResource(R.drawable.person)
            }

            if (list[position].category_icon_id.equals("4")) {
                holder.itemView.ivCategoryImg.setImageResource(R.drawable.study)
            }

            if (list[position].category_icon_id.equals("5")) {
                holder.itemView.ivCategoryImg.setImageResource(R.drawable.barbell)
            }
            if (list[position].category_icon_id.equals("6")) {
                holder.itemView.ivCategoryImg.setImageResource(R.drawable.calendar)
            }

        }

    }


}