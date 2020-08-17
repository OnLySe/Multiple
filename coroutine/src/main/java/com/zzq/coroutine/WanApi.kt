package com.zzq.coroutine

import retrofit2.Call
import retrofit2.http.GET

interface WanApi {
    @GET("wxarticle/chapters/json")
    fun getArticles(): Call<Articles>
}