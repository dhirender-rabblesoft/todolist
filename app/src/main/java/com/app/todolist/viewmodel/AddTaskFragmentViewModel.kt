package com.app.todolist.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

import com.app.todolist.R
import com.app.todolist.adapters.BallonCategoryAdapter
import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.FragmentBottomDailog2Binding

import com.app.todolist.extensions.visible
import com.app.todolist.model.CategoryList
import com.app.todolist.model.TodoList
import com.app.todolist.network.APIInterfaceTodoList
import com.app.todolist.services.AlarmBroadcastReceiver
import com.app.todolist.utils.TimePickerFragment
import com.app.todolist.utils.Utils
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
    var time = ""
    var newtask = ""
    var title = ""
    var list_of_category: ArrayList<CategoryList> = ArrayList<CategoryList>()
    var alarmManager: AlarmManager? = null

    lateinit var todolist: TodoList
    var rvBallonCategoryListing: RecyclerView? = null
    var count = 0

    fun setBinder(
        binder: FragmentBottomDailog2Binding,
        baseActivity: KotlinBaseActivity,
        list: TodoList,

        ) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        todolist = list

        setPriority()
        mAPIInterfaceTodoList =
            ViewModelProvider(baseActivity).get(APIInterfaceTodoList::class.java)
        alarmManager = baseActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        setCategoryAdapter()
//        setPriorityAdapter()
        getallcategory()

        setCategory()
        setCalender()

        setdata()
        if (list.todo_titile.isNotEmpty()) {
            priorityinfo = todolist.todo_priority
            categoryInfo = todolist.todo_category
            datetime = todolist.date
            title = todolist.todo_titile
            binder.newtask22.setText("Update")
        }
    }



    private fun getallcategory() {
        mAPIInterfaceTodoList =
            ViewModelProvider(baseActivity).get(APIInterfaceTodoList::class.java)
        mAPIInterfaceTodoList.readAllCategoryData.observe(
            baseActivity,
            androidx.lifecycle.Observer { categoryList ->
                list_of_category.clear()
                list_of_category.addAll(categoryList)

            })

    }


    private fun setdata() {
        if (todolist.todo_titile.isNotEmpty()) {
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

    fun updateData() {

        val id = todolist.id
        val titile = binder.entertask.text.toString().trim()
        val priority = priorityinfo
        val category = categoryInfo
        val dateFormat = datetime
        val check = todolist.todo_checked
        binder.newtask22.setText("Update")
        val todolist = TodoList(id, titile, category, priority, dateFormat, "", 0)
        mAPIInterfaceTodoList.updateList(todolist)
        Toast.makeText(baseActivity, "Updated Successfully ", Toast.LENGTH_SHORT).show()

    }

    fun addtodoList() {
        Log.e("task:", binder.entertask.text.toString().trim())
        Log.e("category :", categoryInfo)
        Log.e("priority :", priorityinfo)
        Log.e("datetime :", datetime)
        val todolist =
            TodoList(
                0,
                binder.entertask.text.toString().trim(),
                categoryInfo,
                priorityinfo,
                datetime,
                "",
                0
            )
        mAPIInterfaceTodoList.addList(todolist)
        Toast.makeText(baseActivity, baseActivity.getString(R.string.successfully_added), Toast.LENGTH_LONG).show()
    }

    fun validation(): Boolean {
        val entertask = binder.entertask.text.toString().trim()
        if (entertask.isEmpty()) {
            Toast.makeText(baseActivity, baseActivity.getString(R.string.enter_title), Toast.LENGTH_LONG).show()
            return false
        }
        if (priorityinfo.isEmpty()) {
            Toast.makeText(baseActivity, "Enter Priority", Toast.LENGTH_LONG).show()
            return false
        }
        if (categoryInfo.isEmpty()) {
            Toast.makeText(baseActivity, "Enter Category", Toast.LENGTH_LONG).show()
            return false
        }

        if (datetime.isEmpty()) {
            Toast.makeText(baseActivity, "Enter Date", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

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
                time =  binder.tvcalender.text.toString()
                datetime = date + " " + binder.tvcalender.text.toString()

            }
        }).showPicker()
    }

    public fun createAlram(){
        try {
            val items1: Array<String> = date.split("-").toTypedArray()
            val dd = items1[0]
            val month = items1[1]
            val year = items1[2]
            val itemTime: Array<String> = time.split(":").toTypedArray()
            val hour = itemTime[0]
            val min = itemTime[1]
            val cur_cal: Calendar = GregorianCalendar()
            cur_cal.timeInMillis = System.currentTimeMillis()
            val cal: Calendar = GregorianCalendar()
            cal[Calendar.HOUR_OF_DAY] = hour.toInt()
            cal[Calendar.MINUTE] = min.toInt()
            cal[Calendar.SECOND] = 0
            cal[Calendar.MILLISECOND] = 0
            cal[Calendar.DATE] = dd.toInt()
            val alarmIntent = Intent(baseActivity, AlarmBroadcastReceiver::class.java)
            alarmIntent.putExtra("TITLE",  binder.entertask.getText().toString())
            alarmIntent.putExtra("DESC", "This is Desc")
            alarmIntent.putExtra("DATE", date)
            alarmIntent.putExtra("TIME", time)
//            val pendingIntent = PendingIntent.getBroadcast(
//                baseActivity,
//                com.codegama.todolistapplication.bottomSheetFragment.CreateTaskBottomSheetFragment.count,
//                alarmIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )

            val pendingIntent = PendingIntent.getBroadcast(baseActivity,count,alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager?.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    cal.timeInMillis,
                    pendingIntent
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager?.setExact(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
                } else {
                    alarmManager?.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
                }
                count++
                val intent = PendingIntent.getBroadcast(
                    baseActivity,
                    count,
                    alarmIntent,
                    0
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager?.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        cal.timeInMillis - 600000,
                        intent
                    )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        alarmManager?.setExact(
                            AlarmManager.RTC_WAKEUP,
                            cal.timeInMillis - 600000,
                            intent
                        )
                    } else {
                        alarmManager?.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis - 600000, intent)
                    }
                }
                count++
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

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

    private fun ballonCategoryAdapter(ballon: Balloon) {
        val ballonCategoryAdapter = BallonCategoryAdapter(baseActivity) {
            //set categoryicon
            when (list_of_category[it].category_icon_id) {
                "1" -> {
                    setCategoryVisiable(
                        list_of_category[it].category_name,
                        R.drawable.ic_baseline_category_24
                    )
                    categoryInfo = baseActivity.getString(R.string.inbox)
                    ballon.dismiss()
                }
                "2" -> {
                    setCategoryVisiable(list_of_category[it].category_name, R.drawable.home)
                    categoryInfo = baseActivity.getString(R.string.home)
                    ballon.dismiss()
                }
                "3" -> {
                    setCategoryVisiable(list_of_category[it].category_name, R.drawable.person)
                    categoryInfo = baseActivity.getString(R.string.personal)
                    ballon.dismiss()
                }
                "4" -> {
                    setCategoryVisiable(list_of_category[it].category_name, R.drawable.study)
                    categoryInfo = baseActivity.getString(R.string.learning)
                    ballon.dismiss()
                }
                "5" -> {
                    setCategoryVisiable(list_of_category[it].category_name, R.drawable.barbell)
                    categoryInfo = baseActivity.getString(R.string.fitness)
                    ballon.dismiss()
                }
                "6" -> {
                    setCategoryVisiable(list_of_category[it].category_name, R.drawable.calendar)
                    categoryInfo = baseActivity.getString(R.string.birthday)
                    ballon.dismiss()
                }
            }
        }
        ballonCategoryAdapter.addNewList(list_of_category)
        rvBallonCategoryListing?.adapter = ballonCategoryAdapter


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

            rvBallonCategoryListing =
                balloon.getContentView().findViewById<RecyclerView>(R.id.rvballoonCateogorylisting)

            ballonCategoryAdapter(balloon)


        }

    }


    private fun setpriorityVisiable(name: String, flag: Int) {

        binder.ivPriority.setImageResource(flag)

    }

    private fun setCategoryVisiable(name: String, cateogoryimg: Int) {

        binder.ivCategory.setImageResource(cateogoryimg)

    }






}