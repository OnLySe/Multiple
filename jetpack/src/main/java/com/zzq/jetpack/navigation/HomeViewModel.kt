package com.zzq.jetpack.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class HomeViewModel : ViewModel() {

    /**
     * 退出
     */

    private val result = MutableLiveData<String>()

    private val data = liveData<Int> {
//        emit()
    }

}