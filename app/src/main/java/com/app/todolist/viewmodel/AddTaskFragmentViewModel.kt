package com.app.todolist.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.app.todolist.R
import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.FragmentBottomDailog2Binding

import com.app.todolist.extensions.visible
import com.app.todolist.model.TodoList
import com.app.todolist.network.APIInterfaceTodoList
import com.app.todolist.utils.TimePickerFragment
import com.app.todolist.utils.Utils
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import kotlinx.android.synthetic.main.item_today_list2.view.*
import java.text.SimpleDateFormat
import java.util.*


class AddTaskFragmentViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: FragmentBottomDailog2Binding
    lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context
    private lateinit var mAPIInterfaceTodoList: APIInterfaceTodoList


    var priorityflag = true
    var priorityinfo = ""
    var categoryInfo = ""
    var date = ""
    var datetime = ""
    var newtask = ""
    lateinit var todolist:TodoList

    fun setBinder(binder: FragmentBottomDailog2Binding, baseActivity: KotlinBaseActivity,list:TodoList) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        todolist = list
        setPriority()
        mAPIInterfaceTodoList =
            ViewModelProvider(baseActivity).get(APIInterfaceTodoList::class.java)
//        setCategoryAdapter()
//        setPriorityAdapter()

        setCategory()
        setCalender()
        setclick()
        setdata()
    }
    private fun setdata(){
        if (todolist.todo_titile.isNotEmpty()){
            binder.entertask.setText(todolist.todo_titile)
            binder.showdate.visible()
            binder.showdate.setText(todolist.date)

            //set todo list
            if (todolist.todo_priority.equals(baseActivity.getString(R.string.high_priority))) {
               binder.ivPriority.setImageResource(R.drawable.red_flag)
            }
            if (todolist.todo_priority.equals(baseActivity.getString(R.string.medium_priority))) {
                binder.ivPriority.setImageResource(R.drawable.flag_yellow)
            }
            if (todolist.todo_priority.equals(baseActivity.getString(R.string.low_priority))) {
                binder.ivPriority.setImageResource(R.drawable.flag_blue)
            }
            if (todolist.todo_priority.equals(baseActivity.getString(R.string.no_priority))) {
                binder.ivPriority.setImageResource(R.drawable.flag_black)
            }

            //set category
            if (todolist.todo_category.equals(baseActivity.getString(R.string.inbox))) {
                binder.ivCategory.setImageResource(R.drawable.ic_baseline_category_24)
            }
            if (todolist.todo_category.equals(baseActivity.getString(R.string.home))) {
                binder.ivCategory.setImageResource(R.drawable.home)
            }
            if (todolist.todo_category.equals(baseActivity.getString(R.string.personal))) {
                binder.ivCategory.setImageResource(R.drawable.person)
            }

            if (todolist.todo_category.equals(baseActivity.getString(R.string.learning))) {
                binder.ivCategory.setImageResource(R.drawable.study)
            }

            if (todolist.todo_category.equals(baseActivity.getString(R.string.fitness))) {
                binder.ivCategory.setImageResource(R.drawable.barbell)
            }
            if (todolist.todo_category.equals(baseActivity.getString(R.string.birthday))) {
                binder.ivCategory.setImageResource(R.drawable.calendar)
            }



        }
    }

    private fun updateData(){
        val id = todolist.id
        val titile = binder.entertask.text.toString().trim()
        val priority =priorityinfo
        val category = categoryInfo
        val dateFormat = datetime
        val check = todolist.todo_checked
        val todolist = TodoList(id,titile,category,priority,dateFormat,"",0)
        mAPIInterfaceTodoList.updateList(todolist)
        Toast.makeText(baseActivity, "Updated Successfully !", Toast.LENGTH_SHORT).show()

    }


    private fun addtodoList() {

        Log.e("task:", binder.entertask.text.toString().trim())
        Log.e("category :", categoryInfo)
        Log.e("priority :", priorityinfo)
        Log.e("datetime :", datetime)
        val todolist =
            TodoList(0, binder.entertask.text.toString().trim(), categoryInfo, priorityinfo, datetime,"", 0)
        mAPIInterfaceTodoList.addList(todolist)
        Toast.makeText(baseActivity, "Successfully added!", Toast.LENGTH_LONG).show()
    }

    private fun validation(): Boolean {
        val entertask = binder.entertask.text.toString().trim()
        if (entertask.isEmpty()) {
            return false
        }
        if (priorityinfo.isEmpty()) {
            return false
        }
        if (categoryInfo.isEmpty()) {
            return false
        } else
            if (datetime.isEmpty()) {
                return false
            }
        return true
    }

    private fun setclick() {
        binder.newtask22.setOnClickListener {
            if (validation()) {
                 if(todolist.todo_titile.isNotEmpty()){
                    updateData()
                }else{
                     addtodoList()
                 }

            } else {
                    Toast.makeText(baseActivity, "Something Wrong", Toast.LENGTH_LONG).show()


            }
        }

        binder.maincontainer.setOnClickListener {

//            val input =
//                baseActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//            input.hideSoftInputFromWindow(baseActivity.currentFocus?.windowToken, 0)
        }
    }

//    private fun setCategoryAdapter() {
//        val categoryAdapter = CategoryAdapter()
//        binder.rvCategory.adapter = categoryAdapter
//    }

    private fun setCalender() {
        binder.calenderconatiner.setOnClickListener {
            Utils.shoedatepicker(baseActivity, binder.tvcalender, onConfirmed = {
                date = binder.tvcalender.text.toString()
                showtimepicker(binder.tvcalender)
            })
        }
    }


    private fun showtimepicker(autoCompleteTextView: AutoCompleteTextView) {
        TimePickerFragment(baseActivity, object : TimePickerFragment.TimePickerInterface {
            override fun onTimeSelected(calendar: Calendar) {
                autoCompleteTextView.setText(SimpleDateFormat(Utils.TIMEFORMAT).format(calendar.time))
                //                binder.tvcalender.setText(date + " " + binder.tvcalender.text.toString())

                binder.showdate.visible()
                binder.showdate.setText(date + " " + binder.tvcalender.text.toString())
                datetime = date + " " + binder.tvcalender.text.toString()

            }
        }).showPicker()
    }
//    private fun setPriorityAdapter() {
//        val priorityList = ArrayList<PriorityModel>()
//        priorityList.add(PriorityModel("High Priority", R.drawable.red_flag))
//        priorityList.add(PriorityModel("Medium Priority", R.drawable.flag_yellow))
//        priorityList.add(PriorityModel("Low Priority", R.drawable.flag_blue))
//        priorityList.add(PriorityModel("No Priority", R.drawable.flag_black))
//        val priorityAdapter = PriorityAdapter()
//        priorityAdapter.addNewList(priorityList)
//        binder.rvPriority.adapter = priorityAdapter
//
//
//    }

    private fun setPriority() {
        val balloon = Balloon.Builder(baseActivity.applicationContext)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setLayout(R.layout.item_ballon_priority)
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

        binder.ivPriority.setOnClickListener {
            balloon.showAlignTop(binder.ivPriority)

            val highPriorityButton =
                balloon.getContentView().findViewById<TextView>(R.id.tvhighname)
            val mediumPriorityButton =
                balloon.getContentView().findViewById<TextView>(R.id.tvmediumname)
            val lowPriorityButton =
                balloon.getContentView().findViewById<TextView>(R.id.tvlowPriority)
            val noPriorityButton =
                balloon.getContentView().findViewById<TextView>(R.id.tvNoPriority)


            highPriorityButton.setOnClickListener {
                Toast.makeText(baseActivity, highPriorityButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setpriorityVisiable(highPriorityButton.text.toString(), R.drawable.red_flag)
                priorityinfo = highPriorityButton.text.toString()
                balloon.dismiss()
            }
            mediumPriorityButton.setOnClickListener {
                Toast.makeText(
                    baseActivity,
                    mediumPriorityButton.text.toString(),
                    Toast.LENGTH_LONG
                ).show()
                setpriorityVisiable(mediumPriorityButton.text.toString(), R.drawable.flag_yellow)
                priorityinfo = mediumPriorityButton.text.toString()
                balloon.dismiss()
            }
            lowPriorityButton.setOnClickListener {
                Toast.makeText(baseActivity, lowPriorityButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setpriorityVisiable(lowPriorityButton.text.toString(), R.drawable.flag_blue)
                priorityinfo = lowPriorityButton.text.toString()
                balloon.dismiss()
            }
            noPriorityButton.setOnClickListener {
                Toast.makeText(baseActivity, noPriorityButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setpriorityVisiable(noPriorityButton.text.toString(), R.drawable.flag_black)
                priorityinfo = noPriorityButton.text.toString()
                balloon.dismiss()
            }
        }
    }

    private fun setCategory() {

        val balloon = Balloon.Builder(baseActivity.applicationContext)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setLayout(R.layout.item_ballon_category)
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

        binder.ivCategory.setOnClickListener {

            balloon.showAlignTop(binder.ivCategory)

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
                categoryInfo = homeButton.text.toString()
                balloon.dismiss()
            }


            homeButton.setOnClickListener {
                Toast.makeText(baseActivity, homeButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(homeButton.text.toString(), R.drawable.home)
                categoryInfo = homeButton.text.toString()
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
                balloon.dismiss()
            }
            fitnessButton.setOnClickListener {
                Toast.makeText(baseActivity, fitnessButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(fitnessButton.text.toString(), R.drawable.barbell)
                categoryInfo = fitnessButton.text.toString()
                balloon.dismiss()
            }


            learningButton.setOnClickListener {
                Toast.makeText(baseActivity, learningButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(learningButton.text.toString(), R.drawable.study)
                categoryInfo = learningButton.text.toString()
                balloon.dismiss()
            }
            birthdayButton.setOnClickListener {
                Toast.makeText(baseActivity, birthdayButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(birthdayButton.text.toString(), R.drawable.calendar)
                categoryInfo = birthdayButton.text.toString()

                balloon.dismiss()
            }
        }

    }


    private fun setpriorityVisiable(name: String, flag: Int) {

        binder.ivPriority.setImageResource(flag)

    }

    private fun setCategoryVisiable(name: String, cateogoryimg: Int) {

        binder.ivCategory.setImageResource(cateogoryimg)

    }
//    private fun showtimepicker(autoCompleteTextView: AutoCompleteTextView) {
//        TimePickerFragment(baseActivity, object : TimePickerFragment.TimePickerInterface {
//            override fun onTimeSelected(calendar: Calendar) {
//                autoCompleteTextView.setText(SimpleDateFormat(Utils.TIMEFORMAT).format(calendar.time))
//
//                // TODO: for setting end time by default 2 hours on the behalf selected date and start time
//                if (binder.tvtime.text.toString().isNotNull()) {
//                    val mDate = binder.tvdate.text.toString().trim()
//                    val mTime = binder.tvtime.text.toString().trim()
//                    Log.e("ddfsddsfsfsdf", mDate + " ||| " + mTime)
//                    val combintdatetime = mDate + " " + mTime
//
//                    binder.tvTask.setText(combintdatetime)
//                }
//            }
//        }).showPicker()
//    }


}