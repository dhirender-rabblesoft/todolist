package com.app.todolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.todolist.R
import com.app.todolist.base.KotlinBaseActivity

class SplashScreen : KotlinBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            openA(LoginActivity::class)
        },3000)
    }
}