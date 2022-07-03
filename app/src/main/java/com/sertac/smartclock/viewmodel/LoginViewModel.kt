package com.sertac.smartclock.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sertac.smartclock.Dto.UserLogin
import com.sertac.smartclock.model.CustomResponse
import com.sertac.smartclock.model.LoginResponse
import com.sertac.smartclock.repository.AuthRepository
import com.sertac.smartclock.util.Constants
import com.sertac.smartclock.util.CustomSharedPreferences
import com.sertac.smartclock.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
//    private val customSharedPreferences: CustomSharedPreferences = CustomSharedPreferences()
//    private val customSharedPreferences: CustomSharedPreferences
    private val sharedPreferences: SharedPreferences
):ViewModel(){
    var customResponse = mutableStateOf(LoginResponse("null",false,"null"))
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun login(userLogin: UserLogin){

        viewModelScope.launch {
            isLoading.value = true

            val result = repository.login(userLogin)

            when(result){
                is Resource.Success -> {


                    customResponse.value = LoginResponse(result.data!!.accessToken,result.data.isSuccess, result.data.messsage)
                    println("Deneme-SaveToken --> ${customResponse.value.accessToken}")
                    sharedPreferences.edit().putString("TOKEN", customResponse.value.accessToken)
                    Constants.Token = customResponse.value.accessToken

//                    customSharedPreferences.saveToken(customResponse.value.accessToken)

                    isLoading.value = false
                    errorMessage.value = ""
                }
                is Resource.Error -> {
//                    customResponse.value = CustomResponse(false, result.message!!)
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}