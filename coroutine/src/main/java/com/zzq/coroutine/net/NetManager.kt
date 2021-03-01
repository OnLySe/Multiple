package com.zzq.coroutine.net

import com.squareup.moshi.Moshi
import com.zzq.coroutine.net.gank.GankApi
import com.zzq.net.RetrofitClient
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory

class NetManager : RetrofitClient() {

    override var baseUrl = "https://gank.io/api/v2/"
    private val moshi = Moshi.Builder().build()
    val gankApi: GankApi

    override fun addConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create()
    }

    override fun <T> fromJson(jsonStr: String, clazz: Class<T>): T {
        return moshi.adapter<T>(clazz).fromJson(jsonStr)!!
    }

    override fun <T> toJson(src: T, clazz: Class<T>): String {
        return moshi.adapter<T>(clazz).toJson(src)
    }

    init {

        gankApi = retrofit.create(GankApi::class.java)
    }
}