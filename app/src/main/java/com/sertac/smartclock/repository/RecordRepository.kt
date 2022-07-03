package com.sertac.smartclock.repository

import com.sertac.smartclock.Dto.RecordAdd
import com.sertac.smartclock.model.CustomResponse
import com.sertac.smartclock.model.RecordListItem
import com.sertac.smartclock.service.RecordApi
import com.sertac.smartclock.util.CustomSharedPreferences
import com.sertac.smartclock.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class RecordRepository @Inject constructor(
    private val api: RecordApi,
) {
    suspend fun getRecordList(token : String):Resource<List<RecordListItem>>{

        println("Deneme-GetToken --> ${token}")
        val response = try {
            api.getRecordList("Bearer " + token)
        }catch (e:Exception){
            println("Exception-RecordRepository-getRecordList --> ${e}")
            return Resource.Error("Error -> ${e}")
        }

        return Resource.Success(response)
    }

    suspend fun addRecord(token : String, recordAdd: RecordAdd): Resource<CustomResponse>{
        val response = try {
            api.addRecord("Bearer " + token, recordAdd)
        }catch (e:Exception){
            println("Exception-RecordRepository-addRecord --> ${e}")
            return Resource.Error("Error -> ${e}")
        }
        print("addRecord Success")
        return Resource.Success(response)
    }
}