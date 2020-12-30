package com.zzq.coroutine

import androidx.lifecycle.*
import com.zzq.common.utils.LogUtil.eLog
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoroutineViewModel : ViewModel() {

    private val wanApi = CoroutineApp.getWanApi()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        eLog("出现了个异常： ${Thread.currentThread().name} $throwable")
    }

    fun getArticle() {

        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            val result = async(Dispatchers.IO) { wanApi.getArticles22() }
            val articles = result.await()
            eLog("end ${Thread.currentThread().name} $articles")
        }
    }

    fun getArticle1() {
        wanApi.getArticles1().enqueue(object : Callback<Articles> {

            override fun onResponse(call: Call<Articles>, response: Response<Articles>) {
                eLog("getArticle1 onResponse")
            }


            override fun onFailure(call: Call<Articles>, t: Throwable) {
                eLog("getArticle1 onFailure")
            }
        })
    }

    fun getArticle2(): LiveData<Articles> {
        return liveData {
            eLog("liveData: constructor: " + Thread.currentThread().name)
            val data = withContext(Dispatchers.IO + exceptionHandler) {
                async { wanApi.getArticles21() }
            }
            emit(data.await())
        }
    }

    fun getArticle3(): LiveData<List<Articles.DataBean>> {
        //在build.gradle中增加配置：kotlinOptions {jvmTarget = "1.8"}
        return getArticle2().switchMap { liveData { emit(it.data) } }
    }

    fun getArticle4(): LiveData<List<Articles.DataBean>> {
        return getArticle2().map { it.data }
    }

    suspend fun getArticle5(): List<Articles.DataBean> {
        val data = withContext(Dispatchers.IO + exceptionHandler) {
            async { wanApi.getArticles21() }
        }
        return data.await().data
    }

    /*suspend fun getArticle6():List<Articles>{
        val continuation = object:Continuation<Articles>{
            override val context: CoroutineContext
                get() = TODO("Not yet implemented")

            override fun resumeWith(result: Result<Articles>) {

            }

        }

        val coroutineScope = CoroutineScope(Dispatchers.Main)
        val mainScope = MainScope()
    }*/
}