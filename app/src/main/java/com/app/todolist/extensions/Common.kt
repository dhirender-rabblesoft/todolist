package com.app.todolist.extensions

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import android.content.ClipboardManager
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.*
import android.text.format.DateFormat
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
 import androidx.recyclerview.widget.RecyclerView
import com.app.todolist.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

import com.google.android.material.snackbar.Snackbar

import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


import java.math.RoundingMode
import java.net.URLDecoder
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern



/*fun Activity.startHomeActivity(action: Intent.() -> Unit = {}) {
    val intent = Intent(this, HomeActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    intent.action()
    startActivity(intent)
}*/

/*fun Fragment.startHomeActivity(action: Intent.() -> Unit = {}) {
    activity?.startHomeActivity(action)
}*/
///////////////////////////////////////// CONTEXT ////////////////////////////////////

/**
 * Util Function for startActivity
 *    open<BooksDetailActivity> {
 *      putExtra("IntentKey","DATA")
 *      putExtra("IntenKey@2", "DATA@2")
 *    }
 *  or
 * open<BooksDetailActivity>()
 * */
inline fun <reified T> Activity.openActivity(extras: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.extras()
    startActivity(intent)
}
inline fun <reified T> Activity.openActivityWithFinish(extras: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.extras()
    startActivity(intent)
    finish()
}
fun AppCompatImageView.loadSvg(url: String?) {
    val IMAGE_URL = "https://d1rl21n2nalbcq.cloudfront.net/assets/$url"
    GlideToVectorYou.justLoadImage(this.context as Activity?, Uri.parse(IMAGE_URL), this)
}
fun AppCompatImageView.loadPng(url: String?) {
    val IMAGE_URL = "https://d1rl21n2nalbcq.cloudfront.net/assets/$url"
    Picasso.get().load(IMAGE_URL).into(this)
}
fun AppCompatImageView.loadPngwithoucdn(url: String?) {
    Picasso.get().load(url).into(this)
}
fun CircleImageView.loadPngwithoucdn(url: String?) {
    Picasso.get().load(url).into(this)
}

fun Activity.displayMetrics(): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics
}
fun TextView.isEllipsized() = layout.text.toString() != text.toString()

inline fun <reified T> Fragment.openActivity(extras: Intent.() -> Unit = {}) {
    val intent = Intent(this.requireActivity(), T::class.java)
    intent.extras()
    startActivity(intent)
}
fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: (T) -> Unit)
{
    observe(owner, object: Observer<T>
    {
        override fun onChanged(value: T)
        {
            var initialized=false
            if (!initialized)
            {
                initialized=true
                observer(value)
            }
            removeObserver(this)

        }

    })
}

fun String.currencyFormat(): String {
    return if(!this.contains("৳"))
        "\u09F3 $this"
    else
        this
}

inline fun <reified T> Activity.openActivityForResult(
    requestCode: Int,
    extras: Intent.() -> Unit = {}
) {
    val intent = Intent(this, T::class.java)
    intent.extras()
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
    startActivityForResult(intent, requestCode, options.toBundle())
}

inline fun <reified T> Fragment.openActivityForResult(
    requestCode: Int,
    extras: Intent.() -> Unit = {}
) {
    val intent = Intent(this.requireActivity()!!, T::class.java)
    intent.extras()
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this.requireActivity()!!)
    startActivityForResult(intent, requestCode, options.toBundle())
}


@Throws(IllegalAccessException::class, InstantiationException::class)
inline fun <reified T> newFragmentInstance(extras: Bundle.() -> Unit = {}): T? {

    return (T::class.java.newInstance() as Fragment).apply {
        arguments = Bundle().apply { extras() }
    } as T

}

@Throws(IllegalAccessException::class, InstantiationException::class)
inline fun <reified T> AppCompatActivity.showDialogFragment(extras: Bundle.() -> Unit = {}): T? {

    val instance = newFragmentInstance<T>(extras)
    (instance as DialogFragment).show(
        supportFragmentManager,
        T::class.java.simpleName
    )
    return instance
}

@Throws(IllegalAccessException::class, InstantiationException::class)
inline fun <reified T> Fragment.showDialogFragment(
    fromActivity: Boolean = true,
    extras: Bundle.() -> Unit = {}
): T? {
    val instance = newFragmentInstance<T>(extras)
    (instance as DialogFragment).show(
        if (fromActivity) {
            activity?.supportFragmentManager!!
        } else {
            childFragmentManager
        },
        T::class.java.simpleName
    )
    return instance
}

/*fun Fragment.vibratePhone() {
    activity?.vibratePhone()
}*/
/*

fun Activity.vibratePhone() {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(200)
    }
}
*/

fun Bundle.putStrings(vararg values: Pair<String, String>) {
    values.forEach { value ->
        this.putString(value.first, value.second)
    }
}


fun Context.getColorCompat(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Activity.getColorCompat(@ColorRes color: Int): Int {
    return baseContext.getColorCompat(color)
}

fun Fragment.getColorCompat(@ColorRes color: Int): Int {
    return activity!!.getColorCompat(color)
}

fun Context.toast(msg: String) {
    val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    val view = toast.view
    toast.setGravity(Gravity.BOTTOM, 0, 120)
    view!!.background.setColorFilter(ContextCompat.getColor(this, android.R.color.white))
    val text = view!!.findViewById<TextView>(android.R.id.message)
    executeSafe {
        text.typeface = getMediumTypeFace(this)
    }
    text.textSize=12f
    text.setTextColor(ContextCompat.getColor(this, R.color.black))
    toast.show()
}

/*
fun showInfoDialogFragment(
    fragmentManager: FragmentManager?,
    message: String?
) {
    val comingSoonDialogFragment: ComingSoonDialogFragment =
        ComingSoonDialogFragment.Companion.newInstance()
    comingSoonDialogFragment.isCancelable = true
    val args = Bundle()
    args.putString(ComingSoonDialogFragment.EXTRA_TEXT_TO_SHOW, message?:"Coming soon")
    comingSoonDialogFragment.arguments = args
    comingSoonDialogFragment.show(fragmentManager!!, ComingSoonDialogFragment.TAG)
    val handler = Handler()
    handler.postDelayed(comingSoonDialogFragment::dismissAllowingStateLoss, DISMISS_DIALOG_SECONDS)
}*/

fun getMediumTypeFace(context: Context): Typeface? {
    return Typeface.createFromAsset(context.assets, "fonts/poppins_medium.ttf")
}

fun Drawable.setColorFilter(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
    } else {
        setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}

fun ImageView.setColorFilter(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
    } else {
        setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}


fun Fragment.toast(msg: String) {
    activity?.toast(msg)
}

fun Context.parseResColor(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard(view ?: View(activity))
}

fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.openKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}


fun Fragment.openKeyboard() {
    activity?.openKeyboard()
}

fun Activity.openKeyboard() {
    applicationContext?.openKeyboard()
}

fun Context.showAlert(
    message: String?,
    cancelable: Boolean = true,
    showPositiveButton: Boolean = true,
    work: () -> Unit = { }
) {

    if (message.isNullOrEmpty()) return

    val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert)
    } else {
        AlertDialog.Builder(this)
    }

    builder.setMessage(message)
        .setCancelable(cancelable)

    if (showPositiveButton) {
        builder.setPositiveButton("Ok") { dialog, id ->
            work.invoke()
            dialog.dismiss()
        }
    }

    val alert = builder.create()
    alert.getButton(Dialog.BUTTON_NEGATIVE)?.isAllCaps = false
    alert.getButton(Dialog.BUTTON_POSITIVE)?.isAllCaps = false
    alert.show()
}

fun Context.showConfirmAlert(
    title:String="",
    message: String?, positiveText: String?
    , negativeText: String?
    , onConfirmed: () -> Unit = {}
    , onCancel: () -> Unit = { }

) {

    if (message.isNullOrEmpty()) return

    val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        AlertDialog.Builder(this, R.style.customAlertheme)
    } else {
        AlertDialog.Builder(this)
    }


    builder.setTitle(title).setMessage(message)
        .setCancelable(false)
        .setPositiveButton(positiveText) { dialog, _ ->
            onConfirmed.invoke()
            dialog.dismiss()
        }
        .setNegativeButton(negativeText) { dialog, _ ->
            onCancel.invoke()
            dialog.dismiss()
        }
        .setNeutralButton(""){dialog, _ ->

        }

    val alert = builder.create()
    alert.show()
}
fun Context.showConfirmAlertwithnewtural(
    title:String="",
    message: String?, positiveText: String?
    , negativeText: String?
    , onConfirmed: () -> Unit = {}
    , onCancel: () -> Unit = { }
    , newtural: () -> Unit = { }

) {

    if (message.isNullOrEmpty()) return

    val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        AlertDialog.Builder(this, R.style.customAlertheme)
    } else {
        AlertDialog.Builder(this)
    }


    builder.setTitle(title).setMessage(message)
        .setCancelable(false)
        .setPositiveButton(positiveText) { dialog, _ ->
            onConfirmed.invoke()
            dialog.dismiss()
        }
        .setNegativeButton(negativeText) { dialog, _ ->
            onCancel.invoke()
            dialog.dismiss()
        }
        .setNeutralButton(title){dialog, _ ->
            dialog.dismiss()
        }

    val alert = builder.create()

    alert.show()
}


///////////////////////////////////////// VIEW ////////////////////////////////////

fun Activity.getDecorView(): View {
    return window.decorView
}

fun Fragment.getDecorView(): View {
    return activity?.window?.decorView!!
}

inline fun EditText.observeTextChange(crossinline body: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            body(p0.toString())
        }
    })
}

inline fun TextView.observeTextChange(crossinline body: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            body(p0.toString())
        }
    })
}

fun View.animateX(value: Float) {
    with(ObjectAnimator.ofFloat(this, View.TRANSLATION_X, value)) {
        duration = 3500
        repeatMode = ValueAnimator.REVERSE
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
        start()
    }
}

fun View.animateY(value: Float) {
    with(ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, value)) {
        duration = 3500
        repeatMode = ValueAnimator.REVERSE
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
        start()
    }
}

infix fun ViewGroup.inflate(@LayoutRes view: Int): View {
    return LayoutInflater.from(context).inflate(view, this, false)
}

fun Int.inflate(viewGroup: ViewGroup): View {
    return LayoutInflater.from(viewGroup.context).inflate(this, viewGroup, false)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}
//fun Toast.showCustomToast(message: String, activity: Activity)
//{
//    val layout = activity.layoutInflater.inflate (
//        R.layout.custom_toast_layout,
//        activity.findViewById(R.id.toast_container)
//    )
//
//    // set the text of the TextView of the message
//    val textView = layout.findViewById<TextView>(R.id.toast_text)
//    textView.text = message
//
//    // use the application extension function
//    this.apply {
//        setGravity(Gravity.BOTTOM, 0, 40)
//        duration = Toast.LENGTH_SHORT
//        view = layout
//        show()
//    }
//}

fun View.toggleVisibility() {
    when (this.visibility) {
        View.VISIBLE -> this.gone()
        View.INVISIBLE -> this.visible()
        View.GONE -> this.visible()
    }
}

fun Activity.snack(arrayList: String) {
    getDecorView().snack(arrayList)
}

fun Fragment.snack(arrayList: String) {
    getDecorView().snack(arrayList)
}

inline fun View.snack(
    arrayList: String,
    length: Int = Snackbar.LENGTH_LONG,
    f: Snackbar.() -> Unit = {}
) {
    val snack = Snackbar.make(this, arrayList, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}


///////////////////////////////////////// COMMON ////////////////////////////////////

inline fun <T> T.executeSafe(body: () -> Unit) {
    try {
        body.invoke()
    } catch (e: Exception) {

    }
}

fun <T> T.isNull(): Boolean {
    return this == null
}

fun <T> T.isNotNull(): Boolean {
    return this != null
}

inline infix operator fun Int.times(action: (Int) -> Unit) {
    var i = 0
    while (i < this) {
        action(i)
        i++
    }
}

fun String.remove(vararg value: String): String {
    var removeString = this
    value.forEach {
        removeString = removeString.replace(it, "")
    }
    return removeString
}

//*
//  android:textColorHighlight="#f00" // background color when pressed
//    android:textColorLink="#0f0" link backgroudnd color**/
fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod =
        LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}
fun TextView.makeMulticolourString(vararg links: Pair<String, View.OnClickListener> , colour:Int) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val foregroundColorSpan = object : ForegroundColorSpan(colour) {

        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(
            foregroundColorSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}

fun spToPx(sp: Int): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp.toFloat(),
        Resources.getSystem().displayMetrics
    )
}

fun View.setTopMargin(top: Int) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.topMargin = dpToPx(top)
        requestLayout()
    }
    /*(view.layoutParams as RelativeLayout.LayoutParams).setMargins(
        position.left.convertDpToPx(context),
        position.top.convertDpToPx(context),
        position.right.convertDpToPx(context),
        position.bottom.convertDpToPx(context)
    )*/

}
fun View.setMargins(marginLeft: Int, marginTop: Int, marginRight: Int, marginBottom: Int) {
    val params: LinearLayout.LayoutParams =
        LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    params.setMargins(marginLeft, marginTop, marginRight, marginBottom)
    this.layoutParams = params
}

fun View.setLeftMargin(left: Int) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.leftMargin = dpToPx(top)
        requestLayout()
    }
}

fun View.setRightMargin(right: Int) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.rightMargin = dpToPx(top)
        requestLayout()
    }
}

fun View.setBottomMargin(bottom: Int) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.bottomMargin = dpToPx(top)
        requestLayout()
    }
}

fun View.getMarginLayoutParams(): ViewGroup.MarginLayoutParams? {
    return if (layoutParams is ViewGroup.MarginLayoutParams) {
        layoutParams as ViewGroup.MarginLayoutParams
    } else {
        null
    }

}
/*

fun Activity.playSound(@RawRes sound: Int = R.raw.bell) {
    MediaPlayer.create(this, sound).start()
}
*/

fun String.underline(): SpannableString {
    val content = SpannableString(this)
    content.setSpan(UnderlineSpan(), 0, this.length, 0)
    return content
}

fun TextView.underline() {
    text = text.toString().underline()
}
/*

fun Context.openTab(url: String) {
    val builder = CustomTabsIntent.Builder()
// modify toolbar color
    builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
// add share button to overflow men
    builder.addDefaultShareMenuItem()
// add menu item to oveflow
    //builder.addMenuItem("MENU_ITEM_NAME", pendingIntent)
// show website title
    builder.setShowTitle(true)
// modify back button icon
    //builder.setCloseButtonIcon(bitmap)
// menu item icon
    //builder.setActionButton(bitmap, "Android", pendingIntent, true)
// animation for enter and exit of tab            builder.setStartAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)
    builder.setExitAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)
    val customTabsIntent = builder.build()
    val packageName = CustomTabHelper().getPackageNameToUse(this, url)

    if (packageName == null) {
        // if chrome not available open in web view
        openActivity<WebViewActivity> {
            putExtra(WebViewActivity.EXTRA_URL, url)
        }
    } else {
        customTabsIntent.intent.setPackage(packageName)
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }
}*/

fun String.copyToClipboard(context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText(this, this))
}

fun String.maintainTwoLength(): String {
    executeSafe {
        if (this.length < 2) {
            return "0$this"
        }
    }
    return this
}

/*fun AutocompletePrediction.getAddress(): Address {
    val geocoder = Geocoder(App.getApp())
    var result: List<Address>
    result = geocoder.getFromLocationName(getFullText(null).toString(), 1)
    if (result.isNullOrEmpty()) {
        result = geocoder.getFromLocationName(getPrimaryText(null).toString(), 1)
    }

    if (result.isNullOrEmpty()) {
        return Address(Locale.getDefault())
    }

    return result[0]
}

fun LatLng.getAddress(): Address {
    val geocoder = Geocoder(App.getApp())
    val result: List<Address>
    result = geocoder.getFromLocation(latitude, longitude, 1)

    if (result.isNullOrEmpty()) {
        return Address(Locale.getDefault())
    }
    return result[0]
}*/

inline fun <reified T : Activity> RecyclerView.ViewHolder.getActivity(): T? {
    val contextWrapperBaseContext = ((itemView.context as ContextWrapper).baseContext)
    val fieldOuterContext = contextWrapperBaseContext.javaClass.getDeclaredField("mOuterContext")
    fieldOuterContext.isAccessible = true
    val activity = fieldOuterContext.get(contextWrapperBaseContext) as? T
    fieldOuterContext.isAccessible = false
    return activity
}

fun Long.getDateInformat(): String? {
    val sd = SimpleDateFormat("EEE,dd MMM-hh:mm")
    val dateformatdate = sd.format(this)
    return dateformatdate
}


fun getYearsTillEighteenYearOld(): ArrayList<String> {
    val years = ArrayList<String>()
    val thisYear = Calendar.getInstance().get(Calendar.YEAR)
    for (i in 1940..(thisYear - 18)) {
        years.add(i.toString())
    }
    return years
}

fun EditText.value(): String = this.text.toString()

fun TextView.value(): String = this.text.toString()


/*fun EditText.observeTextChangeAsObservable(): Observable<String> {
    return Observable.create<String> { observer ->
        observeTextChange { text ->
            observer.onNext(text)
        }
    }.subscribeOn(AndroidSchedulers.mainThread())
}*/

fun Spinner.onItemSelectListener(listener: (View?, Int) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            listener.invoke(view, position)
        }
    }
}

inline fun <T> T.notNull(block: T.() -> Unit) {
    this?.block()
}
fun EditText.isEmpty(): Boolean {
    return this.text.isEmpty()
}
fun EditText.isValidemail(): Boolean {
    return isEmailValid(this.text.toString())
}
fun isEmailValid(email: String): Boolean {
    return Pattern.compile(
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    ).matcher(email).matches()
}

fun getCustomDateTimeStamp(time: String?): Long {
    return try {
        val c = Calendar.getInstance(TimeZone.getDefault())
        when {
            time?.toLowerCase(Locale.getDefault())?.trim()?.indexOf("h") != -1 -> {
                val value: String? =
                    time?.toLowerCase(Locale.getDefault())?.trim()?.split("h")?.get(0).toString()
                value?.toInt()?.let { c.add(Calendar.HOUR, it) }
            }
            time.toLowerCase(Locale.getDefault()).trim().indexOf("m") != -1 -> {
                val value: String? = time.toLowerCase(Locale.getDefault()).trim().split("m")[0]
                value?.toInt()?.let { c.add(Calendar.MINUTE, it) }
            }
            time.toLowerCase(Locale.getDefault()).trim().indexOf("d") != -1 -> {
                val value: String? = time.toLowerCase(Locale.getDefault()).trim().split("d")[0]
                value?.toInt()?.let { c.add(Calendar.DAY_OF_MONTH, it) }
            }
        }
        c.add(Calendar.MINUTE, -10)
        return c.time.time
    } catch (e: Exception) {
        e.printStackTrace()
        0L
    }
}
fun decode(url: String): Spanned? {
    var prevURL = ""
    var decodeURL = url
    decodeURL = decodeURL.replace("%(?![0-9a-fA-F]{2})", "%25")
    decodeURL = decodeURL.replace("\\+", "%2B")
    try {
        while (prevURL != decodeURL) {
            prevURL = decodeURL
            decodeURL = URLDecoder.decode(decodeURL, "UTF-8")
        }
    } catch (e: Exception) {
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(decodeURL, Html.FROM_HTML_MODE_LEGACY)
    } else Html.fromHtml(decodeURL)
}

fun decodeString(url: String): String? {
    var prevURL = ""
    var decodeURL = url
    decodeURL = decodeURL.replace("%(?![0-9a-fA-F]{2})", "%25")
    decodeURL = decodeURL.replace("\\+", "%2B")
    try {
        while (prevURL != decodeURL) {
            prevURL = decodeURL
            decodeURL = URLDecoder.decode(decodeURL, "UTF-8")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return decodeURL
}

fun roundOffDecimal(number: Double?): Double? {
    val df = DecimalFormat("##.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(number).toDouble()
}
fun String.capitalizedFirstLetter(): String {
    return if (this.isEmpty())
        ""
    else this.substring(0, 1).uppercase(Locale.getDefault()) + this.substring(1)
}
fun String.capitalizesLetters(): String {
    return if (this.isEmpty())
        ""
    else this.split(' ').joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
}


//tv_video.text = list[position].title.split(' ').joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
fun getDate(timestamp: Long): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = timestamp * 1000L
    return DateFormat.format("dd-MMM-yy", calendar).toString()
}