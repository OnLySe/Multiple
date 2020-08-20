package com.zzq.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var viewModel:CoroutineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(CoroutineViewModel::class.java)
        wanApi = CoroutineApp.getWanApi()

        /*GlobalScope.launch(Dispatchers.IO) {
            Log.e("tetetetete", "start ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {
                Log.e("tetetetete", "inside ${Thread.currentThread().name}")
                val data = wanApi.getArticles().await()
                Log.e("tetetetete", "await: " + data.toString())
            }
            Log.e("tetetetete", "end ${Thread.currentThread().name}")

        }
        Log.e("tetetetete", "outside ${Thread.currentThread().name}")*/

        GlobalScope.launch(Dispatchers.Main) {
            Log.e("tetetetete", "start ${Thread.currentThread().name}")
            val result = async(Dispatchers.IO) {
                Log.e("tetetetete", "inside ${Thread.currentThread().name}")
                wanApi.getArticles().await()
            }
            val result2 = async(Dispatchers.IO) {
                Log.e("tetetetete", "inside ${Thread.currentThread().name}")
                wanApi.getArticles().await()
            }
            Log.e("tetetetete", "end ${Thread.currentThread().name} ${result.await()}")
            Log.e("tetetetete", "end2 ${Thread.currentThread().name} ${result2.await()}")
            Log.e("tetetetete","end nothing! ${Thread.currentThread().name}")
            delay(2000)
            Log.e("tetetetete","end2 nothing! ${Thread.currentThread().name}")

        }
        Log.e("tetetetete", "outside ${Thread.currentThread().name}")
    }
}
