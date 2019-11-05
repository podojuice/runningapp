package com.example.runningapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import com.example.runningapplication.service.UserService
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://52.79.200.149:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(UserService::class.java)
        val landingIntent = Intent(this, LandingActivity::class.java)
        pwdChk.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(pwd.text.toString().equals(pwdChk.text.toString())){
                    setImage.setImageResource(R.drawable.correct)
                } else{
                    setImage.setImageResource(R.drawable.cancel)
                }
            }
        })




        emailChk.setOnClickListener{
            var parameters = HashMap<String,Any>()
            parameters.put("email",this.email.text.toString())
            server.emailCheck(parameters).enqueue(object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {

                    if(response.body()==true){
                        Toast.makeText(applicationContext, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext, "중복되는 이메일입니다.", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(applicationContext, "중복되는 이메일입니다.", Toast.LENGTH_SHORT).show()
                }

            })
        }



        btn_submit.setOnClickListener {
            Log.d("Here?","Here?")
            var parameters = HashMap<String,Any>()
            parameters.put("name", this.username.text.toString())
            parameters.put("height",this.height.text.toString()+" cm")
            parameters.put("weight", this.weight.text.toString()+" kg")
            parameters.put("email",this.email.text.toString())
            parameters.put("password",this.pwd.text.toString())
            parameters.put("gender",(findViewById<RadioButton>(this.gender.checkedRadioButtonId)).text.toString())
            Log.d("",parameters.toString())
            server.signUp(parameters).enqueue(object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.body()==true){
                        startActivity(landingIntent)
                    }else{
                        Toast.makeText(applicationContext, "가입 실패", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("hi","hi")
                    Toast.makeText(applicationContext, "가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }

            })
        }

        back.setOnClickListener {
            val landingIntent = Intent(this, LandingActivity::class.java)
            startActivity(landingIntent)
        }

    }
}
