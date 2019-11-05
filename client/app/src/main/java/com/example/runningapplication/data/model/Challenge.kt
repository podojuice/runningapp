package com.example.runningapplication.data.model

data class Challenge(
    var gid: Int? = null,
    var name: String? = null,
    var content: String? = null,
    var distance: Double? = null,
    var period: String? = null,
    var runnerSum: Int? = null,
    var active: Boolean = false
)