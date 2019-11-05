package com.example.runningapplication.data.model

data class User(
    var uid: String? = null,
    var email: String? = null,
    var password: String? = null,
    var name: String? = null,
    var height: String? = null,
    var weight: String? = null,
    var gender: String? = null,
    var img: String? = null,
//    var userPlans : List<String> ? = null,
//    var groups : List<String> ? = null,
//    var runningData : List<String> ? = null
    var userPlans : List<UserPlan> ? = null,
    var groups : List<Challenge> ? = null,
    var runningData : List<Running> ? = null
)