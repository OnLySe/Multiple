package com.zzq.coroutine

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zzq.util.LogUtil.eLog
import kotlinx.coroutines.*
import retrofit2.await

class MainActivity : AppCompatActivity() {

    private lateinit var wanApi :WanApi
    private lateinit var tvTitle:TextView
    private lateinit var viewModel:CoroutineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(CoroutineViewModel::class.java)
        wanApi = CoroutineApp.getWanApi()
        tvTitle = findViewById(R.id.tv_title)

        val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            eLog("出现了个异常： ${Thread.currentThread().name} ${throwable.message}")
        }
        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            eLog("start ${Thread.currentThread().name}")
            val result = async(Dispatchers.IO) {
                eLog("inside ${Thread.currentThread().name}")
                wanApi.getArticles().await()
            }
            val articles = result.await()
            tvTitle.text = articles.toString()
            eLog("end ${Thread.currentThread().name} $articles")
            eLog("end nothing! ${Thread.currentThread().name}")
        }
        eLog("outside ${Thread.currentThread().name}")
    }
}
