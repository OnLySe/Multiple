package com.zzq.lifecycle.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModel(private var value: String) : ViewModel() {

    fun getValue(): String {
        return value
    }
}

class HomeViewModelFactory(private var value:String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(value) as T
    }

}