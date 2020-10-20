package com.zzq.net

import com.zzq.net.url.Douban
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private val DEFAULT_TIME_OUT = 5
    private val DEFAULT_READ_TIME_OUT = 10;
    private val baseUrl:String = ""
    private lateinit var retrofit:Retrofit
    private lateinit var douban :Douban

    init {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .build()
                    chain.proceed(request)
                }
                .addInterceptor(LogPrintIntercepter())
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        douban = retrofit.create(Douban::class.java)
    }
}