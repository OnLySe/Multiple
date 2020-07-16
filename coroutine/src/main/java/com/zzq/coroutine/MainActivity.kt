package com.zzq.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private lateinit var wanApi : Wan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRetrofit()

        GlobalScope.launch(Dispatchers.Main) {
            Log.e("tetetetete", "globalScope start")
            withContext(Dispatchers.IO) {
                val data = wanApi.getArticles().await()
                Log.e("testUrl", "await: " + data.toString())
                Log.e("tetetetete", "request finish")
            }
            Log.e("tetetetete", "globalScope next")

        }
        Log.e("tetetetete", "globalScope outside")

        /*wanApi.getArticles().enqueue(object :Callback<Articles> {
            override fun onFailure(call: Call<Articles>, t: Throwable) {
                Log.e("testUrl", "onFailure")
            }

            override fun onResponse(call: Call<Articles>, response: retrofit2.Response<Articles>) {
                Log.e("testUrl", "onResponse: " + response.body()!!)
            }
        })*/
    }

    private fun initRetrofit() {
        val retrofit = Retrofit.Builder()
                .client(OkHttpClient.Builder().addInterceptor(LogPrintInterceptor()).build())
                .baseUrl("https://wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        wanApi = retrofit.create(Wan::class.java)
    }

    private interface Wan {

        @GET("wxarticle/chapters/json")
        fun getArticles():Call<Articles>
    }

    private class LogPrintInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)
            val responseBody = response.body
            val source = responseBody!!.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.

            val buffer = source.buffer()
            val charset = Charset.forName("UTF-8")

            val bodyString = buffer.clone().readString(charset)
            Log.e("testUrl", request.url.toUrl().toString())
            Log.e("testUrl", bodyString)
            return response
        }

    }
}
