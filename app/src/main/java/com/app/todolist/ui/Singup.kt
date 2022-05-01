package com.app.todolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.todolist.R
import com.app.todolist.databinding.ActivitySingupBinding

class Singup : AppCompatActivity() {
    lateinit var binding: ActivitySingupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_singup)
        setclick()
    }

    private fun setclick() {
        binding.loginbtn.setOnClickListener {
            onBackPressed()
        }
        binding.commonToolbar.icback.setOnClickListener {
            onBackPressed()
        }
    }
}