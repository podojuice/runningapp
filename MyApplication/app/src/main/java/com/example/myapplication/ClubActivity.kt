package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_club.*

class ClubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club)

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
        btn_activity.setOnClickListener{
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }
    }
}
