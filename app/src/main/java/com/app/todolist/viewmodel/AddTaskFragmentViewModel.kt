package com.app.todolist.viewmodel

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.app.todolist.R
import com.app.todolist.adapters.BallonCategoryAdapter
import com.app.todolist.application.TodoApplication.Companion.CHANNEL_ID
import com.app.todolist.base.Alarm
import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.FragmentBottomDailog2Binding
import com.app.todolist.extensions.visible
import com.app.todolist.model.CategoryList
import com.app.todolist.model.TodoList
import com.app.todolist.network.APIInterfaceTodoList
import com.app.todolist.ui.Home
import com.app.todolist.utils.Keys
import com.app.todolist.utils.TimePickerFragment
import com.app.todolist.utils.Utils
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*


class AddTaskFragmentViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: FragmentBottomDailog2Binding
    lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context
    private lateinit var mAPIInterfaceTodoList: APIInterfaceTodoList
    private val NOTIFICATION_ID = 4;
    var mCalendar: Calendar? = null


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

    var hous = ""
    var min = ""
    var alarm: Alarm? = null

    lateinit var todolist: TodoList
    var rvBallonCategoryListing: RecyclerView? = null
    var count = 0

    @RequiresApi(Build.VERSION_CODES.O)
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
        createNotificationChannel()
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
        Toast.makeText(
            baseActivity,
            baseActivity.getString(R.string.successfully_added),
            Toast.LENGTH_LONG
        ).show()
    }

    fun validation(): Boolean {
        val entertask = binder.entertask.text.toString().trim()
        if (entertask.isEmpty()) {
            Toast.makeText(
                baseActivity,
                baseActivity.getString(R.string.enter_title),
                Toast.LENGTH_LONG
            ).show()
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
                autoCompleteTextView.setText(SimpleDateFormat(Utils.TIMEFORMAT3).format(calendar.time))

                //                binder.tvcalender.setText(date + " " + binder.tvcalender.text.toString())


                binder.showdate.visible()
                binder.showdate.setText(date + " " + binder.tvcalender.text.toString())
                time = binder.tvcalender.text.toString()


                datetime = date + " " + autoCompleteTextView.text.toString()
                Log.e("46545665", datetime.toString())


            }
        }).showPicker()
    }

//    public fun createAlram(){
//        try {
//            val items1: Array<String> = date.split("-").toTypedArray()
//            val dd = items1[0]
//            val month = items1[1]
//            val year = items1[2]
//            val itemTime: Array<String> = time.split(":").toTypedArray()
//            val hour = itemTime[0]
//            val min = itemTime[1]
//            val cur_cal: Calendar = GregorianCalendar()
//            cur_cal.timeInMillis = System.currentTimeMillis()
//            val cal: Calendar = GregorianCalendar()
//            cal[Calendar.HOUR_OF_DAY] = hour.toInt()
//            cal[Calendar.MINUTE] = min.toInt()
//            cal[Calendar.SECOND] = 0
//            cal[Calendar.MILLISECOND] = 0
//            cal[Calendar.DATE] = dd.toInt()
//            val alarmIntent = Intent(baseActivity, AlarmBroadcastReceiver::class.java)
//            alarmIntent.putExtra("TITLE",  binder.entertask.getText().toString())
//            alarmIntent.putExtra("DESC", "This is Desc")
//            alarmIntent.putExtra("DATE", date)
//            alarmIntent.putExtra("TIME", time)
////            val pendingIntent = PendingIntent.getBroadcast(
////                baseActivity,
////                com.codegama.todolistapplication.bottomSheetFragment.CreateTaskBottomSheetFragment.count,
////                alarmIntent,
////                PendingIntent.FLAG_UPDATE_CURRENT
////            )
//
//            val pendingIntent = PendingIntent.getBroadcast(baseActivity,count,alarmIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                alarmManager?.setAndAllowWhileIdle(
//                    AlarmManager.RTC_WAKEUP,
//                    cal.timeInMillis,
//                    pendingIntent
//                )
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    alarmManager?.setExact(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
//                } else {
//                    alarmManager?.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
//                }
//                count++
//                val intent = PendingIntent.getBroadcast(
//                    baseActivity,
//                    count,
//                    alarmIntent,
//                    0
//                )
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    alarmManager?.setAndAllowWhileIdle(
//                        AlarmManager.RTC_WAKEUP,
//                        cal.timeInMillis - 600000,
//                        intent
//                    )
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        alarmManager?.setExact(
//                            AlarmManager.RTC_WAKEUP,
//                            cal.timeInMillis - 600000,
//                            intent
//                        )
//                    } else {
//                        alarmManager?.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis - 600000, intent)
//                    }
//                }
//                count++
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
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


    @RequiresApi(Build.VERSION_CODES.M)
    fun scheduleNotification() {
        val intent = Intent(baseActivity.applicationContext, Home::class.java)
        val titile = binder.entertask.text.toString()
        val message = binder.entertask.text.toString()
        intent.putExtra(Keys.TITLE, titile)
        intent.putExtra(Keys.DESC, message)
        val pendingIntent = PendingIntent.getBroadcast(
            baseActivity.applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = baseActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    }

    private fun getTime(): Long {
        val items1: Array<String> = date.split("-").toTypedArray()
        val dd = items1[0]
        val month = items1[1]
        val year = items1[2]
        val itemTime: Array<String> = time.split(":").toTypedArray()
        val hour = itemTime[0]
        this.hous = hour
        val min = itemTime[1].split(" ")
        val min2 = min[0]
        this.min = min2
        Log.e(
            "hoursminsectnl---",
            hour.toString() + "-------min --- " + min.toString() + "min222---" + min2.toString()
        )
        val calendar = Calendar.getInstance()

        calendar.set(year.toInt(), month.toInt(), dd.toInt(), hour.toInt(), min2.toInt())
        return calendar.timeInMillis

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "TodoListReminderChannel"
        val description = "Channel of Decription"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description
        val notificationManager =
            baseActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    fun updateAlarm() {
        var alarmTitle: String = baseActivity.resources.getString(R.string.alarm_title)
        val alarmId = Random().nextInt(Integer.MAX_VALUE);

        val updatedAlarm = Alarm(
            alarmId,
            this.hous.toInt(),
            this.min.toInt(),
            binder.entertask.text.toString(),
            true,
            true
            )
//        createAlarmViewModel.update(updatedAlarm)
        updatedAlarm.schedule(getContext())
    }

    public fun scheduleAlarm() {
        var alarmTitle = baseActivity.resources.getString(R.string.alarm_title)
        val alarmId = Random().nextInt(Int.MAX_VALUE)
        val alarm = Alarm(
            alarmId,
            this.hous.toInt(),
            this.min.toInt(),
            binder.entertask.text.toString(),
            true,
            false,

        )

        alarm.schedule(getContext())
    }


}