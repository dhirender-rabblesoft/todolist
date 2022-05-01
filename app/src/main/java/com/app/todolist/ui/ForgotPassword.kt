package com.app.todolist.ui

 import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.todolist.R
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.ActivityForgotPasswordBinding


class ForgotPassword : KotlinBaseActivity() {
    lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        setClick()
     }
    private fun setClick(){
        binding.loginbtn.setOnClickListener {
            openA(OTPVerify::class )
        }

        binding.commonToolbar.icback.setOnClickListener {
            onBackPressed()
        }

    }
}