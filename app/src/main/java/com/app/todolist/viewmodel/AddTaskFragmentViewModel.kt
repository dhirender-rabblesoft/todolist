package com.app.todolist.viewmodel

import android.app.Activity
import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.app.todolist.R
 import com.app.todolist.base.AppViewModel
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.ModalBottomSheetContentBinding
import com.app.todolist.extensions.hideKeyboard
import com.app.todolist.extensions.visible
import com.app.todolist.model.PriorityModel
import com.app.todolist.utils.TimePickerFragment
import com.app.todolist.utils.Utils
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import java.text.SimpleDateFormat
import java.util.*


class AddTaskFragmentViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ModalBottomSheetContentBinding
    lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context


    var priorityflag = true
    var priorityinfo = ""
    var categoryInfo = ""
    var date = ""
    var datetime = ""
    var newtask = ""

    fun setBinder(binder: ModalBottomSheetContentBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        setPriority()
//        setCategoryAdapter()
//        setPriorityAdapter()
        setCategory()
        setCalender()
        setclick()
    }

    private fun setclick() {

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
        binder.ivCalender.setOnClickListener {
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
                binder.tvcalender.visible()
                binder.tvcalender.setText(date + " " + binder.tvcalender.text.toString())
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
            .setBackgroundColorResource(com.github.dhaval2404.colorpicker.R.color.teal_200)
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
            .setBackgroundColorResource(com.github.dhaval2404.colorpicker.R.color.teal_200)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()



        binder.ivCategory.setOnClickListener {

            balloon.showAlignTop(binder.ivCategory)

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
            homeButton.setOnClickListener {
                Toast.makeText(baseActivity, homeButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(homeButton.text.toString())
                categoryInfo = homeButton.text.toString()
                balloon.dismiss()
            }
            peronalButton.setOnClickListener {
                Toast.makeText(
                    baseActivity,
                    peronalButton.text.toString(),
                    Toast.LENGTH_LONG
                ).show()
                setCategoryVisiable(peronalButton.text.toString())
                categoryInfo = peronalButton.text.toString()
                balloon.dismiss()
            }
            fitnessButton.setOnClickListener {
                Toast.makeText(baseActivity, fitnessButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(fitnessButton.text.toString())
                categoryInfo = fitnessButton.text.toString()
                balloon.dismiss()
            }


            learningButton.setOnClickListener {
                Toast.makeText(baseActivity, learningButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(learningButton.text.toString())
                categoryInfo = learningButton.text.toString()
                balloon.dismiss()
            }
            birthdayButton.setOnClickListener {
                Toast.makeText(baseActivity, birthdayButton.text.toString(), Toast.LENGTH_LONG)
                    .show()
                setCategoryVisiable(birthdayButton.text.toString())
                categoryInfo = birthdayButton.text.toString()

                balloon.dismiss()
            }
        }

    }


    private fun setpriorityVisiable(name: String, flag: Int) {
        binder.tvflag.visible()
        binder.tvflag.setText(name)
        binder.ivPriority.setImageResource(flag)



    }

    private fun setCategoryVisiable(name: String) {
        binder.tvCategory.visible()
        binder.tvCategory.setText(name)

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