package com.zzq.coroutine.net.gank

import com.zzq.coroutine.net.response.GankPictureList
import retrofit2.Call
import retrofit2.http.GET

interface GankApi {

    @GET("data/category/Girl/type/Girl/page/1/count/10")
    fun getGirlPictures():Call<GankPictureList>
}