package com.zzq.coroutine.net

import com.squareup.moshi.Moshi
import com.zzq.coroutine.net.gank.GankApi
import com.zzq.net.RetrofitClient

object NetManager : RetrofitClient() {

    val gankApi: GankApi = addRetrofit("https://gank.io/api/v2/").create(GankApi::class.java)
    val wanApi: WanApi = addRetrofit("https://wanandroid.com/").create(WanApi::class.java)
    private val moshi = Moshi.Builder().build()

    override fun <T> fromJson(jsonStr: String, clazz: Class<T>): T {
        return moshi.adapter<T>(clazz).fromJson(jsonStr)!!
    }

    override fun <T> toJson(src: T, clazz: Class<T>): String {
        return moshi.adapter<T>(clazz).toJson(src)
    }

}