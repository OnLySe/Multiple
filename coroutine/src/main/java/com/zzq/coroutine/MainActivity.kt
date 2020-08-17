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
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private lateinit var wanApi :WanApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wanApi = CoroutineApp.getWanApi()

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
    }
}
