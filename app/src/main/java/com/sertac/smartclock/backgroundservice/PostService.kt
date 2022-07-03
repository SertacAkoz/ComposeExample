package com.sertac.smartclock.backgroundservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.hilt.navigation.compose.hiltViewModel
import com.sertac.smartclock.Dto.RecordAdd
import com.sertac.smartclock.model.CustomResponse
import com.sertac.smartclock.repository.RecordRepository
import com.sertac.smartclock.util.Constants
import com.sertac.smartclock.util.Resource
import com.sertac.smartclock.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class PostService: Service() {

    @Inject
    lateinit var repository:RecordRepository

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()

        Thread(Runnable {
            kotlin.run {
                while (true){

                    try {
                        Thread.sleep(60000)
                        println("Service ---> Working.........")
                        GlobalScope.launch {
                            val randomRecordValue = (80..140).random() // generated random from 0 to 10 included
                            val randomImmortanceLevel = (1..3).random() // generated random from 0 to 10 included
                            val date = LocalDateTime.now()
                            customData = RecordAdd(
                                "Nabız Verisi",
                                randomRecordValue.toString(),
                                1,
                                randomImmortanceLevel,
                                date.toString(),
                                date.toString()
                            )
                            var myResult = repository.addRecord(Constants.Token, customData!!)


                            setResult(myResult)
                        }
                    }catch (e: Exception){
                        println("Exception-PostService --> ${e}")
                    }
                }
            }
        }).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    companion object{
        var customResult: Resource<CustomResponse>? = null

        var customData : RecordAdd? = null

        fun getResult(): Resource<CustomResponse>? {
            return customResult
        }

        private fun setResult(value:Resource<CustomResponse>){
            println(value.message)
            customResult = value
        }
    }
}