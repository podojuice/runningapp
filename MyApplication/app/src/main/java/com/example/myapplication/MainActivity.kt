package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_club.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_running.setOnClickListener {
            val runningIntent = Intent(this, RunningActivity::class.java)
            startActivity(runningIntent)
        }
        btn_setting.setOnClickListener {
            val settingIntent = Intent(this, SettingActivity::class.java)
            startActivity(settingIntent)
        }
        btn_feed.setOnClickListener {
            val feedIntent = Intent(this, FeedActivity::class.java)
            startActivity(feedIntent)
        }
        btn_club.setOnClickListener{
            val clubIntent = Intent(this, ClubActivity::class.java)
            startActivity(clubIntent)
        }
    }
}
