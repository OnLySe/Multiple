package com.zzq.coroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import com.zzq.coroutine.net.NetManager
import com.zzq.coroutine.net.response.GankGirlPic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureViewModel : ViewModel() {


    init {
        NetManager().gankApi.getGirlPictures().enqueue(object :Callback<GankGirlPic> {
            override fun onResponse(call: Call<GankGirlPic>, response: Response<GankGirlPic>) {
                Log.e("tetetetete", "PictureViewModel OnResponse")
            }

            override fun onFailure(call: Call<GankGirlPic>, t: Throwable) {
                Log.e("tetetetete", "PictureViewModel onFailure")
            }
        })
    }

}