package com.app.todolist.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.FragmentAddCategoryDailogBinding
import com.app.todolist.model.CategoryList
import com.app.todolist.model.TodoList
import com.app.todolist.network.APIInterfaceTodoList
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec

class AddCategoryDailogViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: FragmentAddCategoryDailogBinding
    lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context
    var categoryInfo = ""
    var categoryicon = ""
    var category_icon_id = ""


    lateinit var categorylist: CategoryList

    private lateinit var mAPIInterfaceTodoList: APIInterfaceTodoList

    fun setBinder(binder: FragmentAddCategoryDailogBinding, baseActivity: KotlinBaseActivity, list: CategoryList) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        categorylist = list

        mAPIInterfaceTodoList =
            ViewModelProvider(baseActivity).get(APIInterfaceTodoList::class.java)
        setClicks()
        setCategory()
        if (categorylist.category_name.isNotEmpty()) {
            categoryInfo = categorylist.category_name
            categoryicon = categorylist.category_icon
            category_icon_id = categorylist.category_icon_id
        }
        setdata()
    }


    private fun setdata() {
        if (categorylist.category_name.isNotEmpty()) {
            binder.etTitle.setText(categorylist.category_name)

            //set category
            if (categorylist.category_name.equals(baseActivity.getString(R.string.inbox))) {
                binder.ivicon.setImageResource(R.drawable.ic_baseline_category_24)
            }
            if (categorylist.category_name.equals(baseActivity.getString(R.string.home))) {
                binder.ivicon.setImageResource(R.drawable.home)
            }
            if (categorylist.category_name.equals(baseActivity.getString(R.string.personal))) {
                binder.ivicon.setImageResource(R.drawable.person)
            }

            if (categorylist.category_name.equals(baseActivity.getString(R.string.learning))) {
                binder.ivicon.setImageResource(R.drawable.study)
            }

            if (categorylist.category_name.equals(baseActivity.getString(R.string.fitness))) {
                binder.ivicon.setImageResource(R.drawable.barbell)
            }
            if (categorylist.category_name.equals(baseActivity.getString(R.string.birthday))) {
                binder.ivicon.setImageResource(R.drawable.calendar)
            }


        }
    }

      fun updateData() {
        val id = categorylist.id
        val titile = binder.etTitle.text.toString().trim()
        val category_icon = categoryInfo
        val category_icon_id = categorylist.category_icon_id

          Log.e("orororororo",categoryInfo)

        val categoryList = CategoryList(id, titile,category_icon,category_icon_id)
        mAPIInterfaceTodoList.updateCategoryList(categoryList)
        Toast.makeText(baseActivity, "Updated Successfully !", Toast.LENGTH_SHORT).show()

    }
    private fun setClicks() {

        binder.colorbox.setOnClickListener {
            // Kotlin Code
            val mDefaultColor = "#800000"
            ColorPickerDialog
                .Builder(baseActivity)                        // Pass Activity Instance
                .setTitle("Pick Theme")            // Default "Choose Color"
                .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
                .setDefaultColor(mDefaultColor)     // Pass Default Color
                .setColorListener { color, colorHex ->
                    // Handle Color Selection
                    binder.colorbox.setBackgroundColor(color)
                }
                .show()
        }



    }


      fun addCategoryList() {

        Log.e("task:", binder.etTitle.text.toString().trim())
        Log.e("category :", categoryInfo)
        val categorylist =
            CategoryList(category_name = binder.etTitle.text.toString().trim(),category_icon = categoryInfo,category_icon_id= category_icon_id)

        mAPIInterfaceTodoList.addCategory(categorylist)
        Toast.makeText(baseActivity, "Successfully added!", Toast.LENGTH_LONG).show()
    }

      fun validation(): Boolean {
        val entertask = binder.etTitle.text.toString().trim()
        if (entertask.isEmpty()) {
            return false
        }
        if (categoryInfo.isEmpty()) {
            return false
        }

        return true
    }

    private fun setCategory() {

        val balloon = Balloon.Builder(baseActivity.applicationContext)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setLayout(R.layout.default_category_list)
            .setTextColorResource(R.color.white)
            .setTextSize(15f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setBackgroundColorResource(com.github.dhaval2404.colorpicker.R.color.white)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()

        binder.ivicon.setOnClickListener {

            balloon.showAlignTop(binder.ivicon)


            val inboxButton = balloon.getContentView().findViewById<TextView>(R.id.textinbox)
            val homeButton =
                balloon.getContentView().findViewById<TextView>(R.id.texthome)

            val peronalButton =
                balloon.getContentView().findViewById<TextView>(R.id.textpersonal)

            val fitnessButton =
                balloon.getContentView().findViewById<TextView>(R.id.textFitness)
            val learningButton =
                balloon.getContentView().findViewById<TextView>(R.id.textlearning)
            val birthdayButton =
                balloon.getContentView().findViewById<TextView>(R.id.textBirthday)


            inboxButton.setOnClickListener {
                Toast.makeText(baseActivity, inboxButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(inboxButton.text.toString(), R.drawable.ic_baseline_category_24)
                categoryInfo = inboxButton.text.toString()

                category_icon_id = "1"
                balloon.dismiss()
            }
            homeButton.setOnClickListener {
                Toast.makeText(baseActivity, homeButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(homeButton.text.toString(), R.drawable.home)
                categoryInfo = homeButton.text.toString()
                category_icon_id = "2"
                balloon.dismiss()
            }
            peronalButton.setOnClickListener {
                Toast.makeText(
                    baseActivity,
                    peronalButton.text.toString(),
                    Toast.LENGTH_LONG
                ).show()
                setCategoryVisiable(peronalButton.text.toString(), R.drawable.person)
                categoryInfo = peronalButton.text.toString()
                category_icon_id = "3"
                balloon.dismiss()
            }
            fitnessButton.setOnClickListener {
                Toast.makeText(baseActivity, fitnessButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(fitnessButton.text.toString(), R.drawable.barbell)
                categoryInfo = fitnessButton.text.toString()
                category_icon_id = "4"
                balloon.dismiss()
            }


            learningButton.setOnClickListener {
                Toast.makeText(baseActivity, learningButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(learningButton.text.toString(), R.drawable.study)
                categoryInfo = learningButton.text.toString()
                category_icon_id = "5"
                balloon.dismiss()
            }
            birthdayButton.setOnClickListener {
                Toast.makeText(baseActivity, birthdayButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(birthdayButton.text.toString(), R.drawable.calendar)
                categoryInfo = birthdayButton.text.toString()
                category_icon_id = "6"
                balloon.dismiss()
            }
        }

    }

    private fun setCategoryVisiable(name: String, cateogoryimg: Int) {
//        binder.tvCategory.visible()
//        binder.tvCategory.setText(name)
        binder.ivicon.setImageResource(cateogoryimg)

    }


}