package com.app.todolist.ui

 import android.os.Bundle
 import android.widget.Toast
 import androidx.databinding.DataBindingUtil
import com.app.todolist.R
import com.app.todolist.base.KotlinBaseActivity
  import com.app.todolist.databinding.ActivityOtpverifyBinding
 import com.app.todolist.extensions.toast

class OTPVerify : KotlinBaseActivity() {
    lateinit var binding: ActivityOtpverifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otpverify)
        setClick()
    }
    private fun setClick(){
        binding.loginbtn.setOnClickListener {
            openA(LoginActivity::class)
        }
        binding.tvreset.setOnClickListener{
         Toast.makeText(this,"OTP RESEND",Toast.LENGTH_LONG).show()
        }

        binding.commonToolbar.icback.setOnClickListener {
            onBackPressed()
        }
    }
}