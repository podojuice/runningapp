package com.example.runningapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.runningapplication.data.model.User
import com.example.runningapplication.service.RunningService
import com.example.runningapplication.service.UserService
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_main_level.*
import kotlinx.android.synthetic.main.fragment_main_level.view.*
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
 * [MainLevelFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainLevelFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainLevelFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var myView :View = inflater.inflate(R.layout.fragment_main_level, container, false)
        var list : ArrayList<String> = ArrayList()

        if(!list.contains("ok"))myView.level_volt.alpha=0.5f
        if(!list.contains("ok"))myView.level_yellow.alpha=0.5f
        if(!list.contains("ok"))myView.level_orange.alpha=0.5f
        if(!list.contains("ok"))myView.level_black.alpha=0.5f
        if(!list.contains("ok"))myView.level_blue.alpha=0.5f
        if(!list.contains("ok"))myView.level_green.alpha=0.5f
        if(!list.contains("ok"))myView.level_purple.alpha=0.5f


        var settings: SharedPreferences = activity!!.getSharedPreferences("loginStatus", Context.MODE_PRIVATE)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://70.12.247.54:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(RunningService::class.java)
        server.distance(settings.getInt("uid", 0)).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if(response.code()==200){
                    var distance:Double = 0.0
                    distance = response.body()!!
                    leftdistance.text = distance.toString()
                    if(distance <= 50) {
//                        val drawable : Drawable? =
                        level_image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.level_yellow, null))
                        myView.level_yellow.alpha = 1.0f

                    } else if (distance <=250) {
                        level_image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.level_orange, null))
                        myView.level_orange.alpha = 1.0f

                    } else if (distance <=1000) {
                        level_image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.level_green, null))
                        myView.level_green.alpha = 1.0f

                    } else if (distance <=2500) {
                        level_image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.level_blue, null))
                        myView.level_blue.alpha = 1.0f

                    } else if (distance <=5000) {
                        level_image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.level_purple, null))
                        myView.level_purple.alpha = 1.0f

                    } else if (distance <=15000) {
                        level_image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.level_black, null))
                        myView.level_black.alpha = 1.0f

                    } else {
                        level_image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.level_volt, null))
                        myView.level_volt.alpha = 1.0f
                    }

                }else{
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                Log.d("hi","hi")
            }
        })

        // Inflate the layout for this fragment


        return myView
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
         * @return A new instance of fragment MainLevelFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainLevelFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
