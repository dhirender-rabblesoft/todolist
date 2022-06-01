package com.app.todolist.utils

import android.app.Dialog
import android.app.DialogFragment
import android.widget.TimePicker
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import java.util.*
/**
 * @author flokol120
 * TimePicker Fragment to get the time
 */
class TimePickerFragment (var context: Context, var iface : TimePickerInterface?, var  selectedTime: Calendar = Calendar.getInstance()) : TimePickerDialog.OnTimeSetListener {

    fun showPicker(){

        val timePickerDialog = TimePickerDialog(
            context,
            this,
            selectedTime.get(Calendar.HOUR_OF_DAY),
            selectedTime.get(Calendar.MINUTE),
            true
        )

        timePickerDialog.show()

    }


    override fun onTimeSet(p0: TimePicker?, hour: Int, min: Int) {
        p0?.setIs24HourView(true)
        selectedTime.set(Calendar.HOUR_OF_DAY,hour)
        selectedTime.set(Calendar.MINUTE,min)
        iface?.onTimeSelected(selectedTime)
    }

    interface TimePickerInterface {
        fun onTimeSelected(calendar: Calendar)
    }
}