package com.sertac.smartclock.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sertac.smartclock.Dto.UserRegister
import com.sertac.smartclock.model.CustomResponse
import com.sertac.smartclock.model.LoginResponse
import com.sertac.smartclock.repository.AuthRepository
import com.sertac.smartclock.util.Constants
import com.sertac.smartclock.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    var customResponse = mutableStateOf(CustomResponse(false,"null"))
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun register(userRegister: UserRegister){
        viewModelScope.launch {
            isLoading.value = true

            val result = repository.register(userRegister)

            when(result){
                is Resource.Success -> {
                    println("RegisterViewModel-register-Success")
                    customResponse.value = CustomResponse(result.data!!.isSuccess,result.data.messsage)

//                    customSharedPreferences.saveToken(customResponse.value.accessToken)

                    isLoading.value = false
                    errorMessage.value = ""
                }
                is Resource.Error -> {
//                    customResponse.value = CustomResponse(false, result.message!!)
                    println("Exception-RegisterViewModel-Error --> ${errorMessage.value}")
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}