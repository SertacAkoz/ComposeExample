package com.sertac.smartclock.Dto

data class UserRegister(
    val tckno : String,
    val email : String,
    val name : String,
    val age : Int,
    val gender : Int,
    val password : String,
)
