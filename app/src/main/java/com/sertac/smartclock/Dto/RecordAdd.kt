package com.sertac.smartclock.Dto

import java.time.LocalDateTime

data class RecordAdd(
    val recordName : String,
    val recordValue : String,
    val status : Int,
    val importanceLevel : Int,
    val createdOn : String,
    val modifiedOn : String
)
