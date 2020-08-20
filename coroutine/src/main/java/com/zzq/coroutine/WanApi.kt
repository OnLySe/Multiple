package com.zzq.coroutine

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET

interface WanApi {
    @GET("wxarticle/chapters/json")
    fun getArticles(): Call<Articles>

    @GET("wxarticle/chapters/json")
    suspend fun getArticles2(): Deferred<Articles>
}