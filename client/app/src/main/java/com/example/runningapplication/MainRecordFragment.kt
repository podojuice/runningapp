package com.example.runningapplication

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.runningapplication.data.model.User
import com.example.runningapplication.service.RunningService
import kotlinx.android.synthetic.main.fragment_main_level.*
import kotlinx.android.synthetic.main.fragment_main_record.*
import kotlinx.android.synthetic.main.fragment_main_record.view.*
import kotlinx.android.synthetic.main.item_record.view.*
import kotlinx.android.synthetic.main.item_record.view.mapImage
import kotlinx.android.synthetic.main.record_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.jar.Attributes

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MainRecordFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainRecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainRecordFragment : Fragment() {
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
        // 서버 통신

        var settings: SharedPreferences = activity!!.getSharedPreferences("loginStatus", Context.MODE_PRIVATE)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://52.79.200.149:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(RunningService::class.java)

        var recordlist=inflater.inflate(R.layout.fragment_main_record, container, false)

        server.findRunning(settings.getInt("uid", 0)).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.code()==200){
                    var user: User? = response.body()
                    var sumkm = 0.0
                    var cnt = 0

                    for(running in user!!.runningData!!.iterator()) {
                        var ed=LocalDateTime.parse(running!!.endtime.toString())
                        var sd=LocalDateTime.parse(running!!.starttime.toString())
                        var hour = ChronoUnit.HOURS.between(sd,ed)
                        var minutes = ChronoUnit.MINUTES.between(sd,ed)
                        var seconds = ChronoUnit.SECONDS.between(sd,ed)

                        var recorditem=inflater.inflate(R.layout.item_record,null)
                        recorditem.day.text=LocalDateTime.parse(running!!.endtime.toString()).toLocalDate().toString()
                        recorditem.distance.text= "%.2f".format(running.distance)
                        recorditem.time.text=(if(hour<10) "0"+hour.toString() else hour.toString())+":"+ (if(minutes<10) "0"+minutes.toString() else minutes.toString()) + ":" +(if(seconds<10) "0"+seconds.toString() else seconds.toString())
                        sumkm += running.distance!!.toFloat()
                        cnt ++


                        var ttmp = running.image
                        val bImage: ByteArray = Base64.decode(ttmp, 0)
                        val bais = ByteArrayInputStream(bImage)
                        val bm: Bitmap? = BitmapFactory.decodeStream(bais)
                        recorditem.mapImage.setImageBitmap(bm)
                        recorditem.setOnClickListener {
                            var d= Dialog(requireContext())
                            var dd=layoutInflater.inflate(R.layout.record_detail,null)
                            dd.mapImage.setImageBitmap(bm)
                            val displayRectangle = Rect()
                            activity!!.window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
                            val iconsize = (displayRectangle.width()*0.05f).toInt()
                            val mapsize = (displayRectangle.width()*0.75f).toInt()
                            dd.mapImage.layoutParams.height=mapsize
                            dd.mapImage.layoutParams.width=mapsize
                            dd.closeMap.layoutParams.height=iconsize
                            dd.closeMap.layoutParams.width=iconsize
                            dd.closeMap.setOnClickListener {
                                d.dismiss()
                            }
                            d.setContentView(dd)
                            d.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                            d.show()
                        }

                        recordlist.recordList.addView(recorditem)
                    }

                    sumdistance.setText("%.2f".format(sumkm))
                    count.setText(cnt.toString())
                    if(cnt == 0) {
                        divide.setText("0.0")
                    } else {
                        divide.setText("%.2f".format(sumkm/cnt))
                    }
                }else{
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("hi","hi")
            }
        })

        // Inflate the layout for this fragment



        return recordlist
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
         * @return A new instance of fragment MainRecordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainRecordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
