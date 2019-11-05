package com.example.runningapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_running.*
import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.runningapplication.data.model.Running
import com.example.runningapplication.data.model.User
import com.example.runningapplication.service.RunningService
import com.example.runningapplication.service.UserService
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.SphericalUtil.computeDistanceBetween
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream

class RunningActivity : AppCompatActivity() ,
    BottomNavigationView.OnNavigationItemSelectedListener,
    OnMapReadyCallback, GoogleMap.SnapshotReadyCallback {

    private var polyLineOptions = PolylineOptions().width(0f).color(Color.RED)
    private lateinit var mMap : GoogleMap
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallBack
    private var flag : Int = 1
    private var dir : Double = 0.0
    private var stoptime:Long = 0
    private lateinit var running1 : Running
    private lateinit var server : RunningService

    // 위치 정보를 얻기 위한 초기화
    private fun locationinit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        locationCallback = MyLocationCallBack()
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
    }

    // 위치 정보를 찾고 나서 인스턴스화하는 클래스
    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            location?.run {
                val latLng = LatLng(latitude, longitude)

                mMap.clear()
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                mMap.addMarker(MarkerOptions().position(latLng).title("Current Location"))
                polyLineOptions.add(latLng)
                mMap.addPolyline(polyLineOptions)

                if(flag == 1) { // 시작 전, 정지 버튼 클릭시

                } else if(flag == 2) {  // 시작 버튼 클릭시
                    if(polyLineOptions.points.size > 1) {
                        dir += computeDistanceBetween(polyLineOptions.points[polyLineOptions.points.size - 2], polyLineOptions.points[polyLineOptions.points.size - 1])

                    }
                    totalDir.setText(String.format("%.2f", dir))
                    var time : CharSequence = chronometer2.text
                    var totalTime : Int = ((time[0] - '0') * 10 + (time[1] - '0')) * 60 + ((time[3] - '0') * 10 + (time[4] - '0'))
                    totalCal.setText(String.format("%.1fkcal", totalTime * 0.55))
                    totalVec.setText(String.format("%.2fm/s", dir / totalTime))
                    // Toast.makeText(applicationContext, polyLineOptions.points[polyLineOptions.points.size-1].toString(), Toast.LENGTH_LONG).show()
                } else {  // 일시정지 버튼 클릭시
                    if(polyLineOptions.points.size > 0) {
                    }
                }

            }
        }
    }

    override fun onSnapshotReady(p0: Bitmap) {
        var tb = Bitmap.createBitmap(p0, 0, 0, p0.width, p0.height)
        val baos = ByteArrayOutputStream()

        tb.compress(
            Bitmap.CompressFormat.PNG,
            100,
            baos
        ) //bm is the bitmap object

        val b: ByteArray = baos.toByteArray()


        var ret = Base64.encodeToString(b, Base64.DEFAULT)

        // server 통신
        var parameters = HashMap<String,Any>()
        parameters.put("userId",running1!!.userId!!)
        parameters.put("rid",running1!!.rid!!)
        parameters.put("starttime", running1!!.starttime!!)
        parameters.put("distance", totalDir.text.toString().toDouble())
        parameters.put("image", ret)

        server.endRunning(parameters).enqueue(object : Callback<Running> {
            override fun onResponse(call: Call<Running>, response: Response<Running>) {
                running1 = response.body()!!
                Toast.makeText(applicationContext, "기록이 저장되었습니다.", Toast.LENGTH_SHORT)
                    .show()

            }

            override fun onFailure(call: Call<Running>, t: Throwable) {
                Log.d("test123", t.localizedMessage)
            }
        })





        // ret를 img로 보내서 DB 저장

        // 반대로 비트맵 만들기
//        val bImage: ByteArray = Base64.decode(ret, 0)
//        val bais = ByteArrayInputStream(bImage)
//        val bm: Bitmap? = BitmapFactory.decodeStream(bais)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_running)


        running1 = Running()


        runningMenu.setOnNavigationItemSelectedListener(this)
        runningMenu.selectedItemId = R.id.running

        var retrofit = Retrofit.Builder()
            .baseUrl("http://70.12.247.54:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        server = retrofit.create(RunningService::class.java)
        var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)

        // 버튼 스톱워치
        start_btn.setOnClickListener {
            polyLineOptions = PolylineOptions().width(5f).color(Color.RED)
            chronometer2.base = SystemClock.elapsedRealtime() + stoptime
            chronometer2.start()

            flag = 2
            Toast.makeText(this.applicationContext, "시작", Toast.LENGTH_SHORT).show()
            pause_btn.setBackgroundResource(R.color.Yellow)
            reset_btn.setBackgroundColor(resources.getColor(R.color.Red))
            pause_btn.setTextColor(resources.getColor(R.color.White))
            reset_btn.setTextColor(resources.getColor(R.color.White))
            start_btn.visibility = View.GONE
            pause_btn.visibility = View.VISIBLE
            reset_btn.isEnabled = true

            // server 통신

            var parameters = HashMap<String,Int>()
            parameters.put("userId",settings.getInt("uid", 0))

            server.startRunning(parameters).enqueue(object : Callback<Running> {
                override fun onResponse(call: Call<Running>, response: Response<Running>) {
                    running1 = response.body()!!
                    Toast.makeText(applicationContext, "기록 측정이 시작되었습니다.", Toast.LENGTH_SHORT)
                        .show()

                }

                override fun onFailure(call: Call<Running>, t: Throwable) {
                    Log.d("test123", t.localizedMessage)
                }
            })

        }

        // 일시정지 후 재시작 버튼
        restart_btn.setOnClickListener {
            chronometer2.base = SystemClock.elapsedRealtime() + stoptime
            chronometer2.start()
            flag = 2
            Toast.makeText(this.applicationContext, "재시작", Toast.LENGTH_SHORT).show()

            restart_btn.visibility = View.GONE
            pause_btn.setBackgroundResource(R.color.Yellow)
            reset_btn.setBackgroundColor(resources.getColor(R.color.Red))
            pause_btn.setTextColor(resources.getColor(R.color.White))
            reset_btn.setTextColor(resources.getColor(R.color.White))
            pause_btn.visibility = View.VISIBLE
        }

        pause_btn.setOnClickListener {
            stoptime = chronometer2.base - SystemClock.elapsedRealtime()
            chronometer2.stop()

            flag = 3
            Toast.makeText(this.applicationContext, "일시정지", Toast.LENGTH_SHORT).show()
            reset_btn.setBackgroundResource(R.color.Red)
            reset_btn.setTextColor(resources.getColor(R.color.White))
            restart_btn.setBackgroundResource(R.color.Blue)
            restart_btn.setTextColor(resources.getColor(R.color.White))
            pause_btn.visibility = View.GONE
            restart_btn.visibility = View.VISIBLE
        }

        // 저장을 할건지 말건지
        reset_btn.setOnClickListener {
            chronometer2.base = SystemClock.elapsedRealtime()
            stoptime = 0
            totalDir.setText("0.00")
            totalCal.setText("0kcal")
            totalVec.setText("0m/s")
            chronometer2.stop()

            flag = 1
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.custum_dialog_view, null)

            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    mMap.snapshot(this)

                    /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */

                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    /* 취소일 때 아무 액션이 없으므로 빈칸 */
                }
                .show()
            // mMap.addPolyline(polyLineOptions)





            polyLineOptions = PolylineOptions().width(0f)

            start_btn.visibility = View.VISIBLE
            pause_btn.visibility = View.GONE
            restart_btn.visibility = View.GONE
            reset_btn.setBackgroundResource(R.color.White)
            reset_btn.setTextColor(resources.getColor(R.color.Black))
            reset_btn.isEnabled = false

        }
        // 버튼 스톱워치

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        locationinit()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "GPS가 켜져 있는지 확인해 주십시오.", Toast.LENGTH_SHORT).show()
        permissionCheck(cancel = {showPermissionInfoDialog()}, ok = {addLocationListener()})
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
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

    // 위치 요청 메소드
    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        val SEOUL = LatLng(37.56, 126.97)
        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL).title("서울").snippet("한국의 수도").visible(false)

        mMap.addMarker(markerOptions.visible(false))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f))
        //googleMap.snapshot(this)
    }

    private val gps_request_code = 1000
    private fun permissionCheck(cancel:() -> Unit, ok:() -> Unit) {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                cancel()
            }
            else
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), gps_request_code)
        }
        else
            ok()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            gps_request_code -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    addLocationListener()
                else {
                    toast("권한 거부 됨")
                    finish()
                }
            }
        }
    }

    private fun showPermissionInfoDialog() {
        alert("지도 정보를 얻으려면 위치 권한이 필요합니다", "") {
            yesButton {
                ActivityCompat.requestPermissions(this@RunningActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), gps_request_code)
            }
            noButton {
                toast("권한 거부 됨")
                finish()
            }
        }.show()
    }
}
