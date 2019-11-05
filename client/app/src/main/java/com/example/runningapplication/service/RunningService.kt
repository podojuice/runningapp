package com.example.runningapplication.service

import com.example.runningapplication.data.model.Running
import com.example.runningapplication.data.model.User

import retrofit2.http.*
import retrofit2.Call

interface RunningService {

    @POST("/start.run")
    fun startRunning(
        @Body parameters: HashMap<String, Int>
    ):Call<Running>

    @PUT("/end.run")
    fun endRunning(
        @Body parameters: HashMap<String, Any>
    ):Call<Running>

    @GET("/findRunning.run")
    fun findRunning(
        @Query("uid") uid: Int
    ):Call<User>

    @GET("/distance.run")
    fun distance(
        @Query("uid") uid: Int
    ):Call<Double>
}