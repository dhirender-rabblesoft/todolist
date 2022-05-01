package com.app.todolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.R
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.databinding.ActivityHomeBinding
import com.app.todolist.databinding.ActivityLoginBinding
import com.app.todolist.fragments.HomeFragment
import com.app.todolist.viewmodel.HomeViewModel
import com.app.todolist.viewmodel.LoginViewModel

class LoginActivity : KotlinBaseActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewmodel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewmodel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewmodel.setBinder(binding, this)
        setclick()
    }
    private fun setclick(){
        binding.loginbtn.setOnClickListener {
            openA(Home::class)
        }
        binding.forgotpassword.setOnClickListener {
            openA(ForgotPassword::class)

        }
        binding.llsignup.setOnClickListener {
            openA(Singup::class)

        }
    }
}