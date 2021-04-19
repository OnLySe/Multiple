package com.zzq.jetpack.livedata.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zzq.jetpack.livedata.datasource.DataSource
import com.zzq.jetpack.livedata.datasource.DefaultDataSource

class LiveDataViewModel(private val dataSource: DataSource) : ViewModel() {

    val currentResult = dataSource.generateData()
}

object LiveDataViewModelFactory : ViewModelProvider.Factory {

    var dataSource: DataSource = DefaultDataSource()
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LiveDataViewModel(dataSource) as T
    }

}