package com.zzq.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CoroutineViewModel : ViewModel() {

    init {

    }

    fun test() {
        viewModelScope.launch {  }
    }
}