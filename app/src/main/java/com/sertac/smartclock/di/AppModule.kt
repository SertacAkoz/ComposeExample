package com.sertac.smartclock.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.sertac.smartclock.repository.AuthRepository
import com.sertac.smartclock.repository.RecordRepository
import com.sertac.smartclock.service.AuthApi
import com.sertac.smartclock.service.RecordApi
import com.sertac.smartclock.util.Constants
import com.sertac.smartclock.util.CustomSharedPreferences
import com.sertac.smartclock.viewmodel.PostViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        api: AuthApi
    ) = AuthRepository(api)

    @Singleton
    @Provides
    fun providePostViewModel(
        repository: RecordRepository
    ):ViewModel = PostViewModel(repository)

    @Singleton
    @Provides
    fun provideRecordRepository(
        api: RecordApi
    ) = RecordRepository(api)

//    @Singleton
//    @Provides
//    fun provideCustomSharedPreferences(@ApplicationContext context: Context): CustomSharedPreferences{
//        return CustomSharedPreferences(context)
//    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("preferences_name", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideAuthApi():AuthApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRecordApi():RecordApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(RecordApi::class.java)
    }
}