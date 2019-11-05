package com.example.runningapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.runningapplication.data.model.User
import com.example.runningapplication.service.UserService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = settings.edit()

        var retrofit = Retrofit.Builder()
            .baseUrl("http://70.12.247.54:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val loginIntent = Intent(this, RunningActivity::class.java)

        var server = retrofit.create(UserService::class.java)

        btn_submit.setOnClickListener {
            var parameters = HashMap<String,Any>()
            parameters.put("email",this.email.text.toString())
            parameters.put("password",this.pwd.text.toString())

            server.login(parameters).enqueue(object : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.code()==200){
                        Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        if(response.body()?.uid.equals("0")) {
                        } else {
                            var user:User? = response.body()

                            Log.d("user",user?.email.toString())
                            Log.d("user",user?.password.toString())
                            editor.putInt("uid", user?.uid!!.toInt())
                            editor.putBoolean("AutoLogin",true)
                            editor.putString("name",user?.name.toString())
                            editor.putString("email",user?.email.toString())
                            editor.putString("password",user?.password.toString())
                            editor.putString("gender",user?.gender.toString())
                            editor.putString("height",user?.height!!.toString())
                            editor.putString("weight",user?.weight!!.toString())
                            editor.putString("img",user?.img.toString())
                            editor.commit()
                            Toast.makeText(applicationContext, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                            startActivity(loginIntent)

                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                            finish()
                        }

                    }else{
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("hi","hi")
                    Toast.makeText(applicationContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            })
        }
        back.setOnClickListener {
            val landingIntent = Intent(this, LandingActivity::class.java)
            startActivity(landingIntent)
        }

    }
}
