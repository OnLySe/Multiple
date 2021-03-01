package com.zzq.net

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class RetrofitClient {

    private val DEFAULT_TIME_OUT = 5
    private val DEFAULT_READ_TIME_OUT = 10;
    open var baseUrl: String = ""
    protected val retrofit: Retrofit
    private val gson = Gson()

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
                .addConverterFactory(addConverterFactory())
                .build()
    }

    /**
     * 默认使用[GsonConverterFactory]，可以替换为Moshi，只是它们不能同时使用。
     */
    protected open fun addConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    open fun <T> fromJson(jsonStr: String, clazz: Class<T>): T {
        return gson.fromJson<T>(jsonStr, clazz)
    }

    open fun <T> toJson(src: T, clazz: Class<T>): String {
        return gson.toJson(src)
    }
}