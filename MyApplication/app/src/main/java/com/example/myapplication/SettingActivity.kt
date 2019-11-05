package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_club.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        btn_activity.setOnClickListener {
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }
        btn_activity.setOnClickListener {
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }
        btn_activity.setOnClickListener{
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }
        btn_club.setOnClickListener{
            val clubIntent = Intent(this, ClubActivity::class.java)
            startActivity(clubIntent)
        }
    }
}
