package com.zzq.coroutine

import android.app.Application
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoroutineApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        wanApi = Retrofit.Builder()
                .client(OkHttpClient.Builder().addInterceptor(LogPrintInterceptor()).build())
                .baseUrl("https://wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WanApi::class.java)
    }

    companion object {
        private lateinit var instance: CoroutineApp
        private lateinit var wanApi: WanApi

        fun getInstance(): CoroutineApp {
            return instance
        }

        fun getWanApi(): WanApi {
            return wanApi
        }
    }
}