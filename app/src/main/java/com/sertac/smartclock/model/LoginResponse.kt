package com.sertac.smartclock.model

data class LoginResponse(
    val accessToken : String,
    val isSuccess : Boolean,
    val messsage : String
)
