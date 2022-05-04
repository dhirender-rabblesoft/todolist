package com.app.todolist.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.widget.AutoCompleteTextView
import java.lang.Exception
import java.util.concurrent.TimeUnit
import android.content.DialogInterface

import android.R
import android.app.TimePickerDialog
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.extensions.capitalizesLetters


object Utils
{
    const val SUPERADMIN="Super Admin";
    const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
    const val DATETIMEFORMAT="yyyy-MM-dd HH:mm:ss"
    const val dateformatwithZ="yyyy-MM-dd'T'HH:mm:ssZ";
    const val dateformatwithz="yyyy-MM-dd'T'hh:mm:ssZ";

    const val DATEFORMAT4="dd MMM"
    const val TIMEFORMAT="hh:mm aa"
    const val TIMEFORMAT2="hh:mm:ss"
    const val DATEFORMAT="dd MMM, yyyy"
    const val DATEFORMATRIMEFORMAT2="dd MMM, yyyy hh:mm aa"

    const val DATEFORMAT2="yyyy-MM-dd"
    const val DATEFORMAT3="dd-MM-yyyy"
    const val IELSTS_PREPRATIONID=7
    const val Writing="writing"
    const val Intro="intro"
    const val CueCard="cue_card"
    const val Discussion="discussion"
    const val Listenings="listening"
    const val Speaking="speaking"
    const val APPLICATIONJSON="application/json"
    const val VIDEORESOUCE="video/mp4"
    const val AUDIORESOUCE="audio/mpeg"
    const val APPLICATIONPDF="application/pdf"
    const val PDFRESOUCE="application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    var AUTHTOKEN=""
    const val OPAYN_LAT="30.8935428"
    const val OPAYN_LNG="75.8289174"
    //30.893460358472865, 75.82913737232002
    var CHECKUSER=""
    var MCQQUESTION=1
    var TRUEFALSEQUESTION=2
    var FIILLUPQUESTION=3
    var TYPE = "type"
    var tokens = ""
    var RAZORPAY_ID="rzp_test_T8kQTg09rXvlEi"
    fun setDialogAttributes(dialog: Dialog, height: Int) {
        val window = dialog.window ?: return
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, height)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    fun getAndroidID(context: Context):String {
        val android_id: String = Settings.System.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        println(android_id)
        return  android_id
    }

    fun hideKeyBoard(c: Context, v: View) {
        val imm = c
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)


    }
    fun String.toSpanned(): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            return Html.fromHtml(this)
        }
    }



    fun shoedatepicker(baseActivity: KotlinBaseActivity, lblDate:AutoCompleteTextView, onConfirmed: () -> Unit = {} ,onCancel: () -> Unit = {})
    {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(baseActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in textbox
            var month1=monthOfYear
            ++month1
            lblDate.setText("" + dayOfMonth.toString() +"-" + month1+ "-" + year.toString())
            onConfirmed.invoke()

        }, year, month, day)
        dpd.setButton(DialogInterface.BUTTON_NEGATIVE, baseActivity.getString(R.string.cancel).capitalizesLetters(),
            DialogInterface.OnClickListener { dialog, which ->
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    // Do Stuff
                    onCancel.invoke()
                }
            })

        dpd.show()
    }
    fun getTime(textView: AutoCompleteTextView, context: Context,onConfirmed: () -> Unit = {}){

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            textView.setText(  SimpleDateFormat("HH:mm").format(cal.time))
        }

        textView.setOnClickListener {
            // onConfirmed.invoke()
            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
    }
    //time conversion
    fun timerConversion(value: Long): String? {
        val audioTime: String
        val dur = value.toInt()
        val hrs = dur / 3600000
        val mns = dur / 60000 % 60000
        val scs = dur % 60000 / 1000
        audioTime = if (hrs > 0) {
            String.format("%02d:%02d:%02d", hrs, mns, scs)
        } else {
            String.format("%02d:%02d", mns, scs)
        }
        return audioTime
    }
    fun datedifference(date:String,sdate:String):String
    {

        val date1: Date
        val date2: Date
        var differdates=""

        val dates = SimpleDateFormat(DATETIMEFORMAT)


        try {
            //Setting dates
            date1 = dates.parse(date)
            date2 = dates.parse(sdate)

            //Comparing dates

            //Comparing dates
            val difference = Math.abs(date1.time - date2.time)
            val differenceDates = difference / (24 * 60 * 60 * 1000)



            //Convert long to String
            differdates = java.lang.Long.toString(differenceDates)

        }catch (e:Exception)
        {

        }

        return  differdates
    }
    fun getMultiPart(key: String?, file: String?): MultipartBody.Part?
    {
        return MultipartBody.Part.createFormData(key!!, file!!)
    }
    fun getMultiPart(key: String?, file: File): MultipartBody.Part?
    {
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(key!!, file.name, requestFile)
    }
    fun servertime( datew:String):String
    {
        val dateStr = datew
        val df = SimpleDateFormat(DATETIMEFORMAT, Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(dateStr)
        df.timeZone = TimeZone.getDefault()
        val formattedDate = df.format(date)
        return  formattedDate

    }

    @JvmStatic
    fun minuteago(args: String):String {
        var data=""
        try {
            val format = SimpleDateFormat(DATETIMEFORMAT)
            val past = format.parse(args)
            val now = Date()
            val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
            val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
            val hours: Long = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
            val days: Long = TimeUnit.MILLISECONDS.toDays(now.time - past.time)
            //
//          System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " milliseconds ago");
//          System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
//          System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
//          System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");
            if (seconds < 60) {
                data="$seconds seconds ago"
                println("$seconds seconds ago")
            } else if (minutes < 60) {
                data="$minutes minutes ago"
                println("$minutes minutes ago")
            } else if (hours < 24) {
                data="$hours hours ago"
                println("$hours hours ago")
            } else {
                data="$days days ago"
                println("$days days ago")
            }
        } catch (j: Exception) {
            j.printStackTrace()
        }
        return  data
    }
    fun getcurrentdate():String
    {
        val c = Calendar.getInstance()
        val sdf = SimpleDateFormat(DATETIMEFORMAT)
        val strDate = sdf.format(c.time)
        Log.e("checkNowdateTime",strDate)
        return strDate
    }
    fun shareintent(context: Context)
    {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        context.startActivity(Intent.createChooser(shareIntent,"Share"))
    }

    fun formateDateFromstring(inputFormat: String, outputFormat: String, inputDate: String): String {
        var parsed: Date? = null
        var outputDate = ""
        var df_input = SimpleDateFormat(inputFormat, Locale.getDefault())
        var df_output = SimpleDateFormat(outputFormat, Locale.getDefault())
        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)
        } catch (e: ParseException) {
            Log.e("datexptions",e.message.toString())
        }
        return outputDate
    }
}