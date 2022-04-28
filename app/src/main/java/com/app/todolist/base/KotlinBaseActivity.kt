package com.app.todolist.base
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.*
import android.telephony.TelephonyManager
import android.text.Spannable
import android.text.style.ImageSpan
import android.text.style.URLSpan
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
 import com.app.todolist.R
import com.app.todolist.extensions.getDecorView
import com.app.todolist.extensions.showConfirmAlert
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.google.gson.Gson
import com.app.todolist.listner.KotlinBaseListener
import com.app.todolist.utils.NetworkCheck
import com.app.todolist.utils.SharedPreferenceManager
import com.app.todolist.navigator.Navigator

import org.json.JSONObject
import org.koin.android.ext.android.inject
import retrofit2.Response
import java.io.File
import java.util.*
import kotlin.reflect.KClass
open class KotlinBaseActivity(@IdRes private val container: Int = 0) : AppCompatActivity(),
    KotlinBaseListener
{
    var bundle=Bundle()
    val networkcheck: NetworkCheck by inject()

    val preferencemanger: SharedPreferenceManager by inject()
    private var progressDialog: Dialog? = null
    var imageUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"

    val gson= Gson()
    override fun showProgress() {
        hideProgress()
        //  isDialogShow = true
        progress.show()
    }
    override fun hideProgress()
    {
//        if (this.isDialogShow!!){
//            progress?.dismiss()
//            isDialogShow = false
//        }
    }

    lateinit var progress: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initBackStackListener()
        initializeProgressDialog()
    }
    fun goToUrl(url: String)
    {
        if (url.startsWith("https")||url.startsWith("http"))
        {
            val uriUrl = Uri.parse(url)
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            startActivity(launchBrowser)

        }
        else
        {
            showtoast(getString(R.string.invalidurl))
        }
    }
    fun updatealert()
    {
        showConfirmAlert("App update available","Update","","",onConfirmed = {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())
                )
            );
        })
    }
    fun setFullscreen(activity: Activity) {
        if (Build.VERSION.SDK_INT > 10) {
            var flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN
            val isImmersiveAvailable = Build.VERSION.SDK_INT >= 19
            if (isImmersiveAvailable) {
                flags =
                    flags or (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
            activity.window.decorView.systemUiVisibility = flags
        } else {
            activity.window
                .setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
        }
    }

    fun exitFullscreen(activity: Activity) {
        if (Build.VERSION.SDK_INT > 10) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        } else {
            activity.window
                .setFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
                )
        }
    }


    fun ShowHideFullscreen(isFullscreen: Boolean, cntx: Context) {
        var newUiOptions = 0
        if (isFullscreen) {
            // BEGIN_INCLUDE (get_current_ui_flags)
            // The UI options currently enabled are represented by a bitfield.
            // getSystemUiVisibility() gives us that bitfield.
            val uiOptions = (cntx as Activity).window.decorView.systemUiVisibility
            newUiOptions = uiOptions
            // END_INCLUDE (get_current_ui_flags)
            // BEGIN_INCLUDE (toggle_ui_flags)
            val isImmersiveModeEnabled =
                uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == uiOptions
            if (isImmersiveModeEnabled) {
                Log.d("isImmersiveModeEnabled", "Turning immersive mode mode off. ")
            } else {
                Log.d("isImmersiveModeEnabled", "Turning immersive mode mode on.")
            }

            // Navigation bar hiding:  Backwards compatible to ICS.
            if (Build.VERSION.SDK_INT >= 14) {
                newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }

            // Status bar hiding: Backwards compatible to Jellybean
            if (Build.VERSION.SDK_INT >= 16) {
                newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
            }

            // Immersive mode: Backward compatible to KitKat.
            // Note that this flag doesn't do anything by itself, it only augments the behavior
            // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
            // all three flags are being toggled together.
            // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
            // Sticky immersive mode differs in that it makes the navigation and status bars
            // semi-transparent, and the UI flag does not get cleared when the user interacts with
            // the screen.
            if (Build.VERSION.SDK_INT >= 18) {
                newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            }
            (cntx as Activity).window.decorView.systemUiVisibility = newUiOptions
        } else {
            (cntx as Activity).window.clearFlags(newUiOptions)
        }
    }
    fun isTablet():Boolean
    {
        var istab=false
        val manager = applicationContext.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        if (Objects.requireNonNull(manager).phoneType === TelephonyManager.PHONE_TYPE_NONE) {
            istab=false
        } else {
            istab=true
        }
        return istab
    }
    fun isTablet(context: Context): Boolean {
        return ((context.resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

    fun showSystemBar(isDisplayed: Boolean) {
        val uiOptions: Int
        if (isDisplayed) {
            // show status bar
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                getWindow().getDecorView()
//                    .setSystemUiVisibility(uiOptions)
                getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black))

            }
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                getWindow().getDecorView()
                    .setSystemUiVisibility(uiOptions)
            } else {
                getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
            getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    fun nointernershowToast()
    {
        customSnackBar(getString(R.string.nointernet),true)
//        val myToast = Toast.makeText(this,getString(R.string.nointernet), Toast.LENGTH_SHORT)
//        myToast.setGravity(Gravity.CENTER,0,0)
//        myToast.show()

    }
//    fun guestuseralert()
//    {
//        showConfirmAlert(getString(R.string.pleaselogin),"Ok","Cancel",onCancel = {
//
//        },onConfirmed = {
//            openA(Login::class)
//            finishAffinity()
//        })
//    }




    private fun statusMessage(url: String, directory: File, status: Int): String? {
        var msg = ""
        msg = when (status) {
            DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
            DownloadManager.STATUS_PAUSED -> "Paused"
            DownloadManager.STATUS_PENDING -> "Pending"
            DownloadManager.STATUS_RUNNING -> "Downloading..."
            DownloadManager.STATUS_SUCCESSFUL -> "PDF downloaded successfully in $directory" + File.separator + url.substring(
                url.lastIndexOf("/") + 1
            )
            else -> "There's nothing to download"
        }
        return msg
    }

    private fun initializeProgressDialog() {
        progressDialog = Dialog(this, R.style.transparent_dialog_borderless)
        val view = View.inflate(this, R.layout.progress_dailog, null)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setContentView(view)
        Objects.requireNonNull(progressDialog?.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // gif1 = view.findViewById<GifView>(R.id.gif1);
        progressDialog!!.setCancelable(false)

    }

    fun showtoast(string: String,isError: Boolean=true)
    {
        customSnackBar(string,isError)
//        val myToast = Toast.makeText(this,string, Toast.LENGTH_SHORT)
//        try {
//            if(!myToast.view?.isShown()!!)
//            {
//                myToast.show()
//            }
//
//        }catch (e:Exception)
//        {
//            myToast.setGravity(Gravity.CENTER,0,0)
//            myToast.show()
//        }
    }



    private fun initBackStackListener() {

        with(supportFragmentManager) {
            addOnBackStackChangedListener {
                val fragment = findFragmentById(container)
                navigator.lastFragmentChanged(fragment = fragment as KotlinBaseFragment)
            }
        }
    }
    //Function to parse image tags and enable click events
    fun ImageClick(html: Spannable) {
        for (span in html.getSpans(0, html.length, ImageSpan::class.java)) {
            val flags = html.getSpanFlags(span)
            val start = html.getSpanStart(span)
            val end = html.getSpanEnd(span)
            html.setSpan(object : URLSpan(span.source) {
                override fun onClick(v: View) {
                }
            }, start, end, flags)
        }
    }


    override fun getFragment(kClass: Fragment): Fragment? {
        val fragment = supportFragmentManager.findFragmentByTag(kClass.javaClass.simpleName)
        return if (fragment != null) fragment as Fragment else null
    }

    fun startProgressDialog() {
        if (progressDialog != null  && !progressDialog!!.isShowing) {
            try {
                //   gif1?.gifResource = R.drawable.loader
                progressDialog!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    open fun parseError(response: Response<*>)
    {

        val jsonObj = JSONObject(response.errorBody()?.string().toString())
        customSnackBar(jsonObj.getString("message"),true)
        //showtoast(jsonObj.getString("message"))

    }
    open fun parseError2(response: Response<*>):String
    {

        val jsonObj = JSONObject(response.errorBody()?.string().toString())
        // customSnackBar(jsonObj.getString("message"),true)
        //showtoast(jsonObj.getString("message"))
        return  jsonObj.getString("message")

    }



    open  fun customSnackBar(message: String?, isError: Boolean)
    {
        // create an instance of the snackbar
        // create an instance of the snackbar
        val snackbar = Snackbar.make(this.getDecorView().rootView, "", Snackbar.LENGTH_LONG)

        // inflate the custom_snackbar_view created previously

        // inflate the custom_snackbar_view created previously
        val customSnackView: View = layoutInflater.inflate(R.layout.custom_snakbar2, null)
        val view = snackbar.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        // set the background of the default snackbar as transparent

        // set the background of the default snackbar as transparent
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)

        // now change the layout of the snackbar

        // now change the layout of the snackbar
        val snackbarLayout = snackbar.view as SnackbarLayout

        // set padding of the all corners as 0

        // set padding of the all corners as 0
        snackbarLayout.setPadding(0, 0, 0, 0)

        // register the button from the custom_snackbar_view layout file

        // register the button from the custom_snackbar_view layout file
        val bGotoWebsite: AppCompatImageView = customSnackView.findViewById(R.id.ivcancel)
        val rlmain: RelativeLayout = customSnackView.findViewById(R.id.rlmain)
        val textView1: TextView = customSnackView.findViewById(R.id.textView1)
        textView1.text=message
        if (!isError)
        {
            rlmain.setBackgroundColor(Color.GREEN)
        }
        // now handle the same button with onClickListener

        // now handle the same button with onClickListener
        bGotoWebsite.setOnClickListener(object : View.OnClickListener
        {
            override  fun onClick(v: View?) {

                snackbar.dismiss()
            }
        })

        // add the custom snack bar layout to snackbar layout

        // add the custom snack bar layout to snackbar layout
        snackbarLayout.addView(customSnackView, 0)

        snackbar.show()
    }


    fun stopProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            try {
                progressDialog!!.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    //       fun sessionexpire()
//    {
//        showConfirmAlert(getString(R.string.sessionexpire),"Ok","",onCancel = {
//
//        },onConfirmed = {
//            Home.userModel=null
//            Home.TOKEN=null
//            preferencemanger.saveString(Keys.USERID,"")
//            preferencemanger.saveString(Keys.GUESTUSER,"")
//            preferencemanger.saveString(Keys.TOKEN,"")
//            openA(Login::class)
//            finishAffinity()
//        })
//    }
    override fun openAForResult(kClass: KClass<out AppCompatActivity>, bundle: Bundle, code: Int) {
        navigator.openAForResult(kClass, bundle, code)
    }


    override fun navigateToFragment(java: Fragment, extras: Bundle?,addToBackstack:Boolean) {
        navigateToFragment(java, extras, transitionView = null,addToBackstack = addToBackstack)
    }

    override fun navigateToFragment(java: Fragment, extras: Bundle?, userTag: String,addToBackstack:Boolean) {
        navigateToFragment(java, extras, transitionView = null, userTag = userTag,addToBackstack = addToBackstack)
    }

    override fun addFragment(fragment: Fragment, extras: Bundle?, tag: String,addToBackstack:Boolean) {
        navigator.addFragment(fragment, tag = tag, bundle = extras,addToBackstack = addToBackstack)
    }



    private val manager: FragmentManager by lazy {
        supportFragmentManager
    }



    private val navigator: Navigator by lazy { Navigator(this, container) }


    fun navigateToFragment(
        clazz:  Fragment,
        bundle: Bundle? = null,
        transitionView: View? = null,
        userTag: String = "",
        addToBackstack:Boolean
    ) {
        navigator.replaceFragment(clazz, bundle, transitionView, userTag,addToBackstack)
    }




    override fun openA(kClass: KClass<out AppCompatActivity>, extras: Bundle?) {
        navigator.openA(kClass, extras)
    }

    override fun onBackPressed()
    {
        if (manager.backStackEntryCount > 0)
        {
            var last = manager.fragments.last()
            if(last.javaClass.canonicalName=="com.bumptech.glide.manager.SupportRequestManagerFragment")
            {
                if(manager.fragments.size>1)
                {
                    last=manager.fragments[manager.fragments.size-2]
                    checkChildFragment(last)
                }else
                {
                    finish()
                }
            }else {
                checkChildFragment(last)
            }


        } else {
            super.onBackPressed()
        }

    }

    private fun checkChildFragment(last: Fragment) {
        if (last.childFragmentManager.backStackEntryCount > 1) {
            handleBackPress(last)
        } else {
            if(manager.backStackEntryCount==1)
                finish()
            else super.onBackPressed()
        }
    }

    private fun handleBackPress(parentFragment: Fragment) {


        var childFragmentManager = parentFragment!!.childFragmentManager
        val childCount = childFragmentManager.backStackEntryCount

        if (childCount == 0) {
            // it has no child Fragment
            // can not handle the onBackPressed task by itself
            super.onBackPressed()

        } else {
            // get the child Fragment
            if(childFragmentManager!=null) {
                /* val childFragment = childFragmentManager!!.fragments[0] as OnBackPressListener

                 // propagate onBackPressed method call to the child Fragment
                 if (!childFragment.onBackPressed()) {*/
                // child Fragment was unable to handle the task
                // It could happen when the child Fragment is last last leaf of a chain
                // removing the child Fragment from stack
                childFragmentManager.popBackStackImmediate()

                // }
            }

            // either this Fragment or its child handled the task
            // either way we are successful and done here

        }
    }

    open fun containsOnlyFragment(): Boolean {
        return true
    }

    fun currentVisibleFragmentTag(): String? {
        return navigator.getCurrentFragmentTag()
    }

    override fun addChildFragment(childFragmentManager: FragmentManager, container: Int, kClass: Fragment,extras: Bundle? ,addToBackstack:Boolean) {
        navigator.addChildFragment(childFragmentManager, container, kClass,extras,addToBackstack)
    }
    override fun replaceChildFragment(childFragmentManager: FragmentManager, container: Int, kClass: Fragment,extras: Bundle?,addToBackstack:Boolean ) {
        navigator.replaceChildFragment(childFragmentManager, container, kClass,extras,addToBackstack)
    }

    fun bringtoFront(kClass: KotlinBaseFragment) {
        navigator.bringFragmentToFrontIfPresentOrCreate(kClass)
    }

    fun getLastAddedFragment(): KotlinBaseFragment? {
        return navigator.getLastAddedFragment()
    }
    override fun showAlert(message: String) {

    }

}
