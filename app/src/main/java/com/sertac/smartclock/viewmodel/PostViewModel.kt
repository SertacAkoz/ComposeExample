package com.sertac.smartclock.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.sertac.smartclock.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository:RecordRepository
): ViewModel() {

    fun deneme(){
        println("PostViewModel Function is Working")
    }

}