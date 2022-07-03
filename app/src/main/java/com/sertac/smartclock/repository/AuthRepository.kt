package com.sertac.smartclock.repository

import com.sertac.smartclock.Dto.UserLogin
import com.sertac.smartclock.Dto.UserRegister
import com.sertac.smartclock.model.CustomResponse
import com.sertac.smartclock.model.LoginResponse
import com.sertac.smartclock.service.AuthApi
import com.sertac.smartclock.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AuthRepository @Inject constructor(
    private val api:AuthApi
){
    suspend fun login(userLogin:UserLogin): Resource<LoginResponse>{
        val response = try {
            api.login(userLogin)
        }catch (e:Exception){
            println("Exception-AuthRepository --> ${e}")

            return Resource.Error("Error --> ${e}")
        }

        return Resource.Success(response)
    }

    suspend fun register(userRegister: UserRegister): Resource<CustomResponse>{
        val response = try {
            api.register(userRegister)
        }catch (e:Exception){
            println("Exception-AuthRepository --> ${e}")

            return Resource.Error("Error --> ${e}")
        }

        return Resource.Success(response)
    }
}