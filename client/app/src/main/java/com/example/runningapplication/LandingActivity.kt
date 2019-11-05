package com.example.runningapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)

        if(settings.getBoolean("AutoLogin",false)){
            val rIntent = Intent(this, RunningActivity::class.java)
            startActivity(rIntent)
            finish()
        }else{
            setContentView(R.layout.activity_landing)
            btn_signup.setOnClickListener {
                val signUpIntent = Intent(this, SignUpActivity::class.java)
                startActivity(signUpIntent)
            }

            btn_login.setOnClickListener{
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            }
        }


    }

}
