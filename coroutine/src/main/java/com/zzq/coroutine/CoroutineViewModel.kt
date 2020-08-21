package com.zzq.coroutine

import androidx.lifecycle.*
import com.zzq.util.LogUtil.eLog
import kotlinx.coroutines.*

class CoroutineViewModel : ViewModel() {

    private val wanApi = CoroutineApp.getWanApi()
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        eLog("出现了个异常： ${Thread.currentThread().name} ${throwable.message}")
    }

    fun getArticle() {

        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            eLog("start ${Thread.currentThread().name}")
            val result = async(Dispatchers.IO) {
                eLog("inside ${Thread.currentThread().name}")
                wanApi.getArticles21()
            }
            val articles = result.await()
            eLog("end ${Thread.currentThread().name} $articles")
            eLog("end nothing! ${Thread.currentThread().name}")
        }
        eLog("outside ${Thread.currentThread().name}")
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


//    override fun onCleared() {
//        super.onCleared()
//        viewModelScope.cancel()
//    }
}