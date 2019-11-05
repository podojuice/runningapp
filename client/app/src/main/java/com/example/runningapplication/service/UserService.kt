package com.example.runningapplication.service

import com.example.runningapplication.data.model.Challenge
import com.example.runningapplication.data.model.User
import com.example.runningapplication.data.model.FriendsRecord
import com.example.runningapplication.data.model.LeaderUser
import retrofit2.http.*
import retrofit2.Call

interface UserService {

//    @FormUrlEncoded
    @POST("/signup.run")
    fun signUp(
        @Body parameters: HashMap<String,Any>
    ):Call<Boolean>

    @POST("/login.run")
    fun login(
        @Body parameters: HashMap<String,Any>
    ):Call<User>

    @POST("/overlap.run")
    fun emailCheck(
        @Body parameters: HashMap<String,Any>
    ):Call<Boolean>


    @GET("/findAllTeam.run")
    fun findTeam(
        @Query("uid") uid:Int
    ):Call<List<Challenge>>

    @GET("/findFriends.run")
    fun findFriends(
        @Query("email") email: String
    ):Call<List<String>>

    @POST("/addFriend.run")
    fun addFriend(
        @Body parammeters: HashMap<String, Any>
    ):Call<Boolean>

    @GET("/selectMyFriends.run")
    fun selectMyFriends(
        @Query("uid") uid: Int
    ):Call<List<LeaderUser>>

    @GET("/findMyFriends.run")
    fun findMyFriends(
        @Query("uid") uid: Int
    ):Call<List<FriendsRecord>>
    
    @POST("/joinTeam.run")
    fun joinTeam(
        @Body param: HashMap<String, Any>
    ):Call<Boolean>

}