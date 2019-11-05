package com.example.runningapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64

import android.util.Log
import android.view.MenuItem
import android.view.View

import android.widget.Toast
import com.example.runningapplication.data.model.FriendsRecord
import com.example.runningapplication.service.UserService
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.android.synthetic.main.activity_feed.*

import kotlinx.android.synthetic.main.frienddialog.view.*
import kotlinx.android.synthetic.main.item_feed.view.*

import kotlinx.android.synthetic.main.item_friend.view.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayInputStream
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.HashMap


class FeedActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://52.79.200.149:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(UserService::class.java)
        Log.d("asdfasdfsadfgg111", settings.getInt("uid", 0).toString())
        var inflater = layoutInflater
        server.findMyFriends(settings.getInt("uid", 0)).enqueue(object : Callback<List<FriendsRecord>> {
            override fun onResponse(call: Call<List<FriendsRecord>>, response: Response<List<FriendsRecord>>) {
                if(response.code()==200){
                    var records: List<FriendsRecord>? = response.body()
                    for(record in records!!.iterator()) {
                        Log.d("saasgasfsa", record.userName.toString())
                        Log.d("saasgasfsa", record.userEmail.toString())
                        Log.d("saasgasfsa", record.running!!.endtime.toString())
                        Log.d("saasgasfsa", record.running!!.image.toString())
                        Log.d("saasgasfsa", record.running!!.starttime.toString())
                        var ed=LocalDateTime.parse(record.running!!.endtime.toString())
                        var sd=LocalDateTime.parse(record.running!!.starttime.toString())

                        var hour = ChronoUnit.HOURS.between(sd,ed)
                        var minutes = ChronoUnit.MINUTES.between(sd,ed)
                        var seconds = ChronoUnit.SECONDS.between(sd,ed)

                        var feeditem=inflater.inflate(R.layout.item_feed,null)
                        feeditem.username.text=record.userName.toString()
                        feeditem.feedDay.text=LocalDateTime.parse(record.running!!.endtime.toString()).toLocalDate().toString()
                        feeditem.feedImage.setImageBitmap(
                            BitmapFactory.decodeStream(
                                ByteArrayInputStream(
                                    Base64.decode(record.running!!.image.toString(), 0))))
                        feeditem.feedDistance.text=record.running!!.distance.toString()
                        feeditem.feedTime.text = (if(hour<10) "0"+hour.toString() else hour.toString())+":"+ (if(minutes<10) "0"+minutes.toString() else minutes.toString()) + ":" +(if(seconds<10) "0"+seconds.toString() else seconds.toString())
                        feedList.addView(feeditem)
                    }
                }else{
                }
            }

            override fun onFailure(call: Call<List<FriendsRecord>>, t: Throwable) {
                Log.d("hi","hi")
                Toast.makeText(applicationContext, "로그인 실패", Toast.LENGTH_LONG).show()
            }
        })


        //

        feedMenu.setOnNavigationItemSelectedListener(this)
        feedMenu.selectedItemId = R.id.feed

        search_friend.setOnClickListener {
            var d= Dialog(this)
            var gd=layoutInflater.inflate(R.layout.frienddialog,null)
            val displayRectangle = Rect()
            gd.friendSearch.setOnClickListener {

                var pre = gd.friendList.layoutParams.height
                gd.friendList.removeAllViewsInLayout()
                var friendItem : View

                server.findFriends(email = gd.friendname.text.toString()).enqueue(object : Callback<List<String>> {
                    override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                        if(response.code()==200){
                            for (s in response.body()!!.iterator()){
                                friendItem = layoutInflater.inflate(R.layout.item_friend,null)
                                friendItem.friendEmail.text=s
                                friendItem.addFriend.setOnClickListener {
                                    var parameters : HashMap<String,Any> = HashMap()
                                    parameters.put("user",settings.getString("email","hi").toString())
                                    parameters.put("email",s)
                                    server.addFriend(parameters).enqueue(object : Callback<Boolean>{
                                        override fun onResponse(
                                            call: Call<Boolean>,
                                            response: Response<Boolean>
                                        ) {
                                            d.dismiss()
                                        }

                                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                        }
                                    })
                                }
                                gd.friendList.addView(friendItem)
                            }
                        }else{

                        }
                    }

                    override fun onFailure(call: Call<List<String>>, t: Throwable) {
                        Log.d("hi","hi")
                    }
                })
                if(gd.friendList.layoutParams.height<pre){
                    gd.layoutParams.height-=(pre-gd.friendList.layoutParams.height)
                }

            }

            window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
            d.setContentView(gd)
            gd.friendCancel.setOnClickListener {
                d.dismiss()
            }
            d.show()
        }
    }
    override fun onNavigationItemSelected(p0: MenuItem) : Boolean {
        when(p0.itemId){
            R.id.feed -> {
            }
            R.id.main-> {
                val activityIntent = Intent(this, MainActivity::class.java)
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(activityIntent)
            }
            R.id.running-> {
                val runningIntent = Intent(this, RunningActivity::class.java)
                runningIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(runningIntent)
            }
            R.id.club-> {
                val clubIntent = Intent(this, ClubActivity::class.java)
                clubIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(clubIntent)
            }
            R.id.setting-> {
                val settingIntent = Intent(this, SettingActivity::class.java)
                settingIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(settingIntent)
            }

        }
        return true
    }
}
