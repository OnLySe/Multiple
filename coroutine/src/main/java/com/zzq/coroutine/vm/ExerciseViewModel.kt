package com.zzq.coroutine.vm

import androidx.lifecycle.*
import com.zzq.common.utils.LogUtil.eLog
import com.zzq.coroutine.net.NetManager
import com.zzq.coroutine.net.response.Articles
import kotlinx.coroutines.*

class ExerciseViewModel : ViewModel() {

    private val wanApi = NetManager.wanApi

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        eLog("出现了个异常： ${Thread.currentThread().name} $throwable")
    }

    fun getArticle() {

        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            val articles = async(Dispatchers.IO) { wanApi.getArticles22() }.await()
            eLog("end ${Thread.currentThread().name} $articles")
        }
    }

    fun getArticle1(): LiveData<Articles> {
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
        return getArticle1().switchMap { liveData { emit(it.data) } }
    }

    fun getArticle4(): LiveData<List<Articles.DataBean>> {
        return getArticle1().map { it.data }
    }

    suspend fun getArticle5(): List<Articles.DataBean> {
        val data = withContext(Dispatchers.IO + exceptionHandler) {
            async { wanApi.getArticles21() }
        }
        return data.await().data
    }
}