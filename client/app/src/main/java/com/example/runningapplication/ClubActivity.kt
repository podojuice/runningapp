package com.example.runningapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_club.*
import kotlinx.android.synthetic.main.activity_main.*

class ClubActivity : AppCompatActivity() ,
    BottomNavigationView.OnNavigationItemSelectedListener,
    ClubLeaderBoardFragment.OnFragmentInteractionListener,
    ClubChallengeFragment.OnFragmentInteractionListener,
    ClubEventFragment.OnFragmentInteractionListener {

    private var tabLayout: TabLayout?=null
    var viewPager: ViewPager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club)
        clubMenu.setOnNavigationItemSelectedListener(this)
        clubMenu.selectedItemId = R.id.club

        viewPager= clubViewPager as ViewPager
        setupViewPager(viewPager!!)
        tabLayout = clubTabs as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.getTabAt(0)!!.setText("리더보드")
        tabLayout!!.getTabAt(1)!!.setText("챌린지")
        tabLayout!!.getTabAt(2)!!.setText("이벤트")

        
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ClubLeaderBoardFragment(), "leaderboard")
        adapter.addFragment(ClubChallengeFragment(), "challenge")
        adapter.addFragment(ClubEventFragment(), "event")
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

        fun addFragment(fragment: Fragment, title: String){
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
