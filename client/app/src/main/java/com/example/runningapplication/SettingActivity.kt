package com.example.runningapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_setting.*
import android.widget.NumberPicker
import android.widget.RadioButton
import androidx.core.view.get
import kotlinx.android.synthetic.main.genderdialog.*
import kotlinx.android.synthetic.main.genderdialog.view.*
import kotlinx.android.synthetic.main.heightdialog.*
import kotlinx.android.synthetic.main.weightdialog.*

class SettingActivity : AppCompatActivity()  , BottomNavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = settings.edit()

        settingMenu.setOnNavigationItemSelectedListener(this)
        settingMenu.selectedItemId = R.id.setting

        val displayRectangle = Rect()
        window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        var size=(displayRectangle.width()*0.3f).toInt()
        ProfileImage.layoutParams.height=size
        ProfileImage.layoutParams.width=size

        genderVal.text=settings.getString("gender","여성")
        email.text=settings.getString("email","dudaduada")
        name.text=settings.getString("name","옹붐바바")
        heightVal.text=settings.getString("height","181 cm")
        weightVal.text=settings.getString("weight","70 kg")
        ProfileImage.background = ShapeDrawable(OvalShape())
        ProfileImage.clipToOutline = true
        ProfileImage.requestLayout()

        ProfileImage.setOnClickListener {
            var tmp = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ProfileImage.setImageURI(tmp)
        }

        ProfileGender.setOnClickListener {
            var d=Dialog(this)
            var gd=layoutInflater.inflate(R.layout.genderdialog,null)
            val displayRectangle = Rect()

            window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
            gd.minimumWidth = (displayRectangle.width()*0.8f).toInt()
            d.setContentView(gd)
            var gg=d.genderGroup
            if (genderVal.text.toString().equals("남성"))gg.genderMale.isChecked=true
            else gg.genderFemale.isChecked=true

            d.genderConfirm.setOnClickListener {
                genderVal.text=d.findViewById<RadioButton>(gg.checkedRadioButtonId).text.toString()
                d.dismiss()
            }
            d.genderCancel.setOnClickListener {
                d.dismiss()
            }
            d.show()
        }

        ProfileHeight.setOnClickListener {
            var d=Dialog(this)
            var hd=layoutInflater.inflate(R.layout.heightdialog,null)
            val displayRectangle = Rect()

            window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
            hd.minimumWidth = (displayRectangle.width()*0.8f).toInt()
            d.setContentView(hd)
            var hp:NumberPicker=d.heightPicker
            hp.maxValue=270
            hp.minValue=100
            Log.d("hereHW",heightVal.text.toString())
            hp.value=heightVal.text.toString().substring(0,(heightVal.text.toString().length)-3).toInt()
            var heights= Array<String>(171,{""})
            for (i in 100..270){
                heights[i-100]=i.toString()+" cm"
            }
            hp.displayedValues=heights
            d.heightConfirm.setOnClickListener {
                heightVal.text=d.heightPicker.value.toString()+" cm"
                d.dismiss()
            }
            d.heightCancel.setOnClickListener {
                d.dismiss()
            }
            d.show()
        }

        ProfileWeight.setOnClickListener {
            var d=Dialog(this)
            var wd=layoutInflater.inflate(R.layout.weightdialog,null)
            val displayRectangle = Rect()

            window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
            wd.minimumWidth = (displayRectangle.width()*0.8f).toInt()
            d.setContentView(wd)
            var wp:NumberPicker=d.weightPicker
            wp.maxValue=200
            wp.minValue=20
            Log.d("hereW",weightVal.text.toString())
            wp.value=weightVal.text.toString().substring(0,(weightVal.text.toString().length)-3).toInt()
            var weights= Array<String>(181,{""})
            for (i in 20..200){
                weights[i-20]=i.toString()+" kg"
            }
            wp.displayedValues=weights
            d.weightConfirm.setOnClickListener {
                weightVal.text=d.weightPicker.value.toString()+" kg"
                d.dismiss()
            }
            d.weightCancel.setOnClickListener {
                d.dismiss()
            }
            d.show()
        }

        btn_logout.setOnClickListener{
            editor.remove("AutoLogin")
            editor.remove("email")
            editor.commit()

            val landingIntent = Intent(this,LandingActivity::class.java)
            startActivity(landingIntent)
        }
    }
    override fun onNavigationItemSelected(p0: MenuItem) : Boolean {
        when(p0.itemId){
            R.id.feed -> {
                val feedIntent = Intent(this, FeedActivity::class.java)
                feedIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(feedIntent)
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
            }

        }
        return true
    }
}
