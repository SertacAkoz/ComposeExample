package com.sertac.smartclock.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sertac.smartclock.model.RecordListItem
import com.sertac.smartclock.repository.RecordRepository
import com.sertac.smartclock.util.Constants
import com.sertac.smartclock.util.CustomSharedPreferences
import com.sertac.smartclock.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: RecordRepository,
//    private val customSharedPreferences: CustomSharedPreferences = CustomSharedPreferences()
//    private val customSharedPreferences: CustomSharedPreferences
    private val sharedPreferences: SharedPreferences
):ViewModel() {
    var recordList = mutableStateOf<List<RecordListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

//    val TOKEN = sharedPreferences.getString("TOKEN", "null")

    val TOKEN = Constants.Token



    init {
        println("SP-ListViewModel-Init-GetToken --> ${TOKEN}")
        getRecordList()
    }

    fun getRecordList(){
        viewModelScope.launch {
            isLoading.value = true

            val result = repository.getRecordList(token = TOKEN!!)

            when(result){
                is Resource.Success -> {
                    val recordItems = result.data!!.mapIndexed { index, recordListItem ->
                        println("ListViewModel-recordListItem --> ${recordListItem}")
                        RecordListItem(recordListItem.id, recordListItem.recordName,recordListItem.recordDescription, recordListItem.recordValue)
                    }

                    errorMessage.value = ""
                    isLoading.value = false
                    recordList.value += recordItems
                }
                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}