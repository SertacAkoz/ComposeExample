package com.sertac.smartclock.service

import com.sertac.smartclock.Dto.UserLogin
import com.sertac.smartclock.Dto.UserRegister
import com.sertac.smartclock.model.CustomResponse
import com.sertac.smartclock.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("/User/Login")
    suspend fun login(@Body userLogin: UserLogin) : LoginResponse

    @POST("/User/CreateUser")
    suspend fun register(@Body userRegister: UserRegister) : CustomResponse

}