package com.zzq.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoroutineViewModel : ViewModel() {

    fun getArticle() {
        viewModelScope.launch(Dispatchers.Main) {

        }
    }
}