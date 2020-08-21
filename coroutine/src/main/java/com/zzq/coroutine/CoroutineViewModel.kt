package com.zzq.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzq.util.LogUtil.eLog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CoroutineViewModel : ViewModel() {

    private val wanApi = CoroutineApp.getWanApi()
    fun getArticle() {
        val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            eLog("出现了个异常： ${Thread.currentThread().name} ${throwable.message}")
        }
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

//    override fun onCleared() {
//        super.onCleared()
//        viewModelScope.cancel()
//    }
}