package com.zzq.net

import android.util.Log
import androidx.collection.ArrayMap
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class RetrofitClient {

    protected val TAG = "RetrofitClient"
    private val DEFAULT_TIME_OUT = 5
    private val DEFAULT_READ_TIME_OUT = 10;
    private val gson = Gson()

    /**
     * 通过BaseUrl来确定一个[Retrofit]
     */
    private var retrofitMap: ArrayMap<String, Retrofit> = ArrayMap()

    private var converterFactoryMap: ArrayMap<String, Converter.Factory> = ArrayMap()

    /**
     * 添加Retrofit实例。通过BaseUrl来确定Retrofit唯一
     * 默认使用[GsonConverterFactory]，可以替换为Moshi，只是它们不能同时使用。
     */
    protected fun addRetrofit(baseUrl: String, converterFactory: Converter.Factory = GsonConverterFactory.create()): Retrofit {

        if (retrofitMap[baseUrl] != null) {
            Log.i(TAG, "已经添加过相关BaseUrl，不会重新创建")
            return retrofitMap[baseUrl]!!
        }
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .build()
                    chain.proceed(request)
                }
                .addInterceptor(LogPrintIntercepter())
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .build()
        retrofitMap.put(baseUrl, retrofit)
        return retrofit
    }

    open fun <T> fromJson(jsonStr: String, clazz: Class<T>): T {
        return gson.fromJson<T>(jsonStr, clazz)
    }

    open fun <T> toJson(src: T, clazz: Class<T>): String {
        return gson.toJson(src)
    }
}