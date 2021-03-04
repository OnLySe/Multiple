package com.zzq.coroutine.net

import com.zzq.coroutine.net.response.Articles
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET

interface WanApi {
    @GET("wxarticle/chapters/json")
    fun getArticles1(): Call<Articles>

    @GET("wxarticle/chapters/json")
    suspend fun getArticles2(): Deferred<Articles>

    @GET("wxarticle/chapters/json")
    suspend fun getArticles21(): Articles

    @GET("qqqqqqqqwxarticle/chapters/json")
    suspend fun getArticles22(): Articles

    @GET("sssssssssssssssssssssssswxarticle/chapters/json")
    fun getArticles3(): Call<Articles>
}