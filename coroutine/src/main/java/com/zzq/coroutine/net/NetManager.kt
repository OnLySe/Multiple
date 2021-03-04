package com.zzq.coroutine.net

import com.squareup.moshi.Moshi
import com.zzq.coroutine.net.gank.GankApi
import com.zzq.net.RetrofitClient

object NetManager : RetrofitClient() {

//    override var baseUrl = "https://gank.io/api/v2/"
    override var baseUrl = "https://wanandroid.com/"
    private val moshi = Moshi.Builder().build()
    val gankApi: GankApi
    val wanApi: WanApi

    init {
        initRetrofit()
        gankApi = retrofit.create(GankApi::class.java)
        wanApi = retrofit.create(WanApi::class.java)
    }

//    override fun addConverterFactory(): Converter.Factory {
//        return MoshiConverterFactory.create()
//    }

    override fun <T> fromJson(jsonStr: String, clazz: Class<T>): T {
        return moshi.adapter<T>(clazz).fromJson(jsonStr)!!
    }

    override fun <T> toJson(src: T, clazz: Class<T>): String {
        return moshi.adapter<T>(clazz).toJson(src)
    }

}