package com.zzq.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    /**
     * 退出
     */

    private val result = MutableLiveData<String>()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val computation = doComputation()
            result.value = computation
        }
    }

    private suspend fun doComputation():String {
        return ""
    }


    private val data = liveData<Int> {
//        emit()
    }

}