package com.sertac.smartclock.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomSharedPreferences @Inject constructor(
    @ApplicationContext context : Context
) {

    companion object{
        private val TOKEN = "token"
        private var sharedPreferences:SharedPreferences? = null

        @Volatile private var instance:CustomSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(lock){
            instance ?: createCustomSharedPreferences(context).also {
                instance = it
            }
        }
        private fun createCustomSharedPreferences(context: Context):CustomSharedPreferences{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences(context)
        }
    }

    fun saveToken(tokenString:String){
        println("SP-saveToken-tokenString --> ${tokenString}")

        try {
            sharedPreferences?.edit(commit = true){
                println("SP-saveToken-tokenString --> ${tokenString}")
                putString(TOKEN,tokenString)
            }
        }catch (e:Exception){
            println("Exception-CustomSharedPreferences-saveToken --> ${e}")
        }

    }

    fun getToken() = sharedPreferences?.getString(TOKEN,"null")


}