package com.example.runningapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.runningapplication.data.model.Challenge
import com.example.runningapplication.service.UserService
import kotlinx.android.synthetic.main.fragment_club_challenge.view.*
import kotlinx.android.synthetic.main.custom_challenge.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ClubChallengeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ClubChallengeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClubChallengeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val listview: ListView
//        val adapter: ListViewAdapter

//        adapter = ListViewAdapter()

//        listview = getView()?.findViewById(R.id.LH) as ListView
//        listview.adapter = adapter

        // var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)
        // var editor: SharedPreferences.Editor = settings.edit()

//        var retrofit = Retrofit.Builder()
//            .baseUrl("http://70.12.247.54:8080")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        var server = retrofit.create(UserService::class.java)
//
//        server.findTeam().enqueue(object : Callback<List<Challenge>> {
//            override fun onResponse(call: Call<List<Challenge>>, response: Response<List<Challenge>>) {
//                if(response.code()==200){
//                    var challenge : List<Challenge>? = response.body()
//                    Log.d("msg", response.body().toString())
//                    var temp : Challenge = challenge!!.get(0)
//                    Log.d("msg", temp.toString())
//
//
//                    val inflater:LayoutInflater = LayoutInflater.from(context)
//                    var challengeItem:View=inflater.inflate(R.layout.row,null)
//                    challengeItem.name.text=temp.name
//                    challengeItem.content.text=temp.content
//
//                    view.CL.addView(challengeItem)
////                    view.LH.addView(challengeItem)
////                    LH.addView(challengeItem)
//
////                    adapter.addItem(temp.name!!, temp.content!!, temp.runnerSum!!)
//
////                    distanceText = findViewById(R.id.distance) as TextView
////                    periodText = findViewById(R.id.period) as TextView
////                    distanceText.text = challenge?.get(0)?.distance.toString() +"km"
////                    periodText.text = challenge?.get(1)?.period.toString()
//                } else{
//                }
//            }
//
//            override fun onFailure(call: Call<List<Challenge>>, t: Throwable) {
//                Log.d("hi","hi")
////                Toast.makeText(applicationContext, "로그인 실패", Toast.LENGTH_SHORT).show()
//            }
//        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_club_challenge, container, false)
        var retrofit = Retrofit.Builder()
            .baseUrl("http://52.79.200.149:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(UserService::class.java)

        view.MC.setVisibility(View.GONE)
        var settings: SharedPreferences = activity!!.getSharedPreferences("loginStatus", Context.MODE_PRIVATE)

        var challengeItem : View

//        Log.d("test",settings.getInt("uid",0).toString())
        server.findTeam(settings.getInt("uid", 0)).enqueue(object : Callback<List<Challenge>> {

            override fun onResponse(call: Call<List<Challenge>>, response: Response<List<Challenge>>) {
                if(response.code()==200){
                    var challenge : List<Challenge>? = response.body()
//                    Log.d("msg", response.body().toString())
                    var idx=0
                    var imgList = arrayOf(R.drawable.club_challenge15k,R.drawable.club_challenge50k,R.drawable.club_challenge100k,R.drawable.club_challenge_next50k)
                    // 여기서 반복문 시작
                    challenge?.forEach {
                        var temp  : Challenge = it

                        challengeItem=inflater.inflate(R.layout.custom_challenge,null)
                        if(idx == 0){
                            challengeItem.setOnClickListener{
                                val intent = Intent(activity, ClubChallengeWeeklyActivity::class.java)
                                intent.putExtra("gid", temp.gid)
                                intent.putExtra("content", temp.content)
                                intent.putExtra("distance", temp.distance)
                                intent.putExtra("name", temp.name)
                                intent.putExtra("period", temp.period)
                                intent.putExtra("runnerSum", temp.runnerSum)
                                intent.putExtra("active", temp.active)

                                startActivity(intent)
                            }
                        } else if(idx == 1){
                            challengeItem.setOnClickListener{
                                val intent = Intent(activity, ClubChallengeMonthly50Activity::class.java)
                                intent.putExtra("gid", temp.gid)
                                intent.putExtra("content", temp.content)
                                intent.putExtra("distance", temp.distance)
                                intent.putExtra("name", temp.name)
                                intent.putExtra("period", temp.period)
                                intent.putExtra("runnerSum", temp.runnerSum)
                                intent.putExtra("active", temp.active)

                                startActivityForResult(intent, 1)
                            }
                        } else if(idx == 2){
                            challengeItem.setOnClickListener{
                                val intent = Intent(activity, ClubChallengeMonthly100Activity::class.java)
                                intent.putExtra("gid", temp.gid)
                                intent.putExtra("content", temp.content)
                                intent.putExtra("distance", temp.distance)
                                intent.putExtra("name", temp.name)
                                intent.putExtra("period", temp.period)
                                intent.putExtra("runnerSum", temp.runnerSum)
                                intent.putExtra("active", temp.active)

                                startActivity(intent)
                            }
                        } else if(idx == 3){
                            challengeItem.setOnClickListener{
                                val intent = Intent(activity, ClubChallengeNextMonth50Activity::class.java)
                                intent.putExtra("gid", temp.gid)
                                intent.putExtra("content", temp.content)
                                intent.putExtra("distance", temp.distance)
                                intent.putExtra("name", temp.name)
                                intent.putExtra("period", temp.period)
                                intent.putExtra("runnerSum", temp.runnerSum)
                                intent.putExtra("active", temp.active)

                                startActivity(intent)
                            }
                        }

                        challengeItem.img.setImageDrawable(resources.getDrawable(imgList.get(idx++)))
                        challengeItem.title.text = temp.name
                        challengeItem.content.text = temp.content

                        if(temp.active){
                            view.MC.setVisibility(View.VISIBLE)
                            Log.d("visi",temp.active.toString())
                            view.MC.addView(challengeItem)
                        } else{
                            view.CL.addView(challengeItem)
                        }
//                        view.CL.addView(challengeItem)
                    }
//                    var temp : Challenge = challenge!!.get(0)
//
//                    Log.d("msg", temp.toString())
//
//                    challengeItem=inflater.inflate(R.layout.custom_challenge,null)
//                    challengeItem.title.text=temp.name
//                    challengeItem.content.text=temp.content
//
//                    view.CL.addView(challengeItem)

//                    view.LH.addView(challengeItem)
//                    LH.addView(challengeItem)

//                    adapter.addItem(temp.name!!, temp.content!!, temp.runnerSum!!)

//                    distanceText = findViewById(R.id.distance) as TextView
//                    periodText = findViewById(R.id.period) as TextView
//                    distanceText.text = challenge?.get(0)?.distance.toString() +"km"
//                    periodText.text = challenge?.get(1)?.period.toString()
                } else{
                }
            }

            override fun onFailure(call: Call<List<Challenge>>, t: Throwable) {
                Log.d("hi","hi")
//                Toast.makeText(applicationContext, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        })

//        var view =  inflater.inflate(R.layout.fragment_club_challenge, container, false)


//        view.first_challenge_layout.setOnClickListener {
//            val intent = Intent(activity, ClubChallengeMonthly50Activity::class.java)
//            startActivity(intent)
//        }

//        view.second_challenge_layout.setOnClickListener {
//            val intent = Intent(activity, ClubChallengeWeeklyActivity::class.java)
//            startActivity(intent)
//        }
//
//        view.third_challenge_layout.setOnClickListener {
//            val intent = Intent(activity, ClubChallengeMonthly100Activity::class.java)
//            startActivity(intent)
//        }
//
//        view.next_50_layout.setOnClickListener {
//            val intent = Intent(activity, ClubChallengeNextMonth50Activity::class.java)
//            startActivity(intent)
//        }
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClubChallengeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClubChallengeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
