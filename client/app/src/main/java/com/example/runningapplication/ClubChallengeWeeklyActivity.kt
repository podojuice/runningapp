package com.example.runningapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.runningapplication.data.model.Challenge
import com.example.runningapplication.service.UserService
import kotlinx.android.synthetic.main.activity_club_challenge_thismonth50k.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClubChallengeWeeklyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_challenge_weekly)

        var intent = getIntent()
        var challenge = Challenge()
        challenge.gid = intent.getIntExtra("gid", 0)
        challenge.content = intent.getStringExtra("gid")
        challenge.distance = intent.getDoubleExtra("distance", 0.0)
        challenge.name = intent.getStringExtra("name")
        challenge.period = intent.getStringExtra("period")
        challenge.runnerSum = intent.getIntExtra("runnerSum", 0)
        challenge.active = intent.getBooleanExtra("active", false)

        cname.text = challenge.name
        distance.text = challenge.distance.toString() + "km"
        period.text = challenge.period
        participants.text = challenge.runnerSum.toString() + "명의 러너"

        if(challenge.active) {
            join_btn.isEnabled = false
            join_btn.text = "챌린지 참여중"
        }
        var retrofit = Retrofit.Builder()
            .baseUrl("http://70.12.247.54:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(UserService::class.java)
        var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)

        join_btn.setOnClickListener {
            var params = HashMap<String,Any>()
            params.put("uid", settings.getInt("uid", 0))
            params.put("gid", challenge.gid!!)

            server.joinTeam(params).enqueue(object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.code()==200){
                        if(response.body()!!){
                            Toast.makeText(applicationContext, "참가 성공", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ClubChallengeWeeklyActivity, ClubActivity::class.java)
                            startActivity(intent)
                        }
                    }else{
                        Toast.makeText(applicationContext, "참가 실패", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("hi","hi")
                    Toast.makeText(applicationContext, "참가 실패", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}
