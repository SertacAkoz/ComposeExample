package com.sertac.smartclock.service

import com.sertac.smartclock.Dto.RecordAdd
import com.sertac.smartclock.model.CustomResponse
import com.sertac.smartclock.model.RecordListItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RecordApi {

    @GET("/Record/GetRecordList")
    suspend fun getRecordList(@Header("Authorization") authorization:String): List<RecordListItem>

    @POST("/Record/CreateNewRecord")
    suspend fun addRecord(@Header("Authorization") authorization:String,@Body recordAdd: RecordAdd): CustomResponse
}