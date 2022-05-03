package com.app.todolist.adapters

import android.util.Log
import com.app.todolist.R
import com.app.todolist.base.BaseAdapter
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.model.CategoryList
import com.app.todolist.model.PriorityModel
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_categorylisting.view.*
import kotlinx.android.synthetic.main.item_today_list2.view.*

class CategoryListingAdapter(val baseActivity: KotlinBaseActivity, val itemClick : (Int) -> Unit) : BaseAdapter<CategoryList>(R.layout.item_categorylisting) {


    override fun onBindViewHolder(holder: IViewHolder, position: Int) {

        holder.itemView.apply {

            Log.e("categorycheck",list[position].category_icon)

            textlearning.setText(list[position].category_name)

            //set category
            if (list[position].category_icon.equals(baseActivity.getString(R.string.inbox))) {
                holder.itemView.ivCategoryImg.setImageResource(R.drawable.ic_baseline_category_24)
            }
            if (list[position].category_icon.equals(baseActivity.getString(R.string.home))) {
                holder.itemView.ivCategoryImg.setImageResource(R.drawable.home)
            }
            if (list[position].category_icon.equals(baseActivity.getString(R.string.personal))) {
                holder.itemView.ivCategoryImg.setImageResource(R.drawable.person)
            }

            if (list[position].category_icon.equals(baseActivity.getString(R.string.learning))) {
                holder.itemView.ivCategoryImg.setImageResource(R.drawable.study)
            }

            if (list[position].category_icon.equals(baseActivity.getString(R.string.fitness))) {
                holder.itemView.ivCategoryImg.setImageResource(R.drawable.barbell)
            }
            if (list[position].category_icon.equals(baseActivity.getString(R.string.birthday))) {
                holder.itemView.ivCategoryImg.setImageResource(R.drawable.calendar)
            }
        }

    }


}