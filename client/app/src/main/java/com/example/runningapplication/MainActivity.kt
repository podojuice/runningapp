package com.example.runningapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import com.example.runningapplication.data.model.User
import com.example.runningapplication.service.RunningService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    MainRecordFragment.OnFragmentInteractionListener,
    MainLevelFragment.OnFragmentInteractionListener{

    private var tabLayout: TabLayout?=null
    var viewPager: ViewPager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainMenu.setOnNavigationItemSelectedListener(this)
        mainMenu.selectedItemId = R.id.main


        viewPager=mainViewPager as ViewPager
        setupViewPager(viewPager!!)
        tabLayout = mainTabs as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.getTabAt(0)!!.setText("기록")
        tabLayout!!.getTabAt(1)!!.setText("러닝 레벨")
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MainRecordFragment(), "record")
        adapter.addFragment(MainLevelFragment(), "level")
        viewPager.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment,title: String){
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onNavigationItemSelected(p0: MenuItem) : Boolean {
        when(p0.itemId){
            R.id.feed -> {
                val feedIntent = Intent(this, FeedActivity::class.java)
                feedIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(feedIntent)
            }
            R.id.main-> {
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
