package com.zzq.jetpack.livedata.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.zzq.common.utils.LogUtil
import kotlinx.coroutines.delay
import kotlin.random.Random

class DefaultDataSource : DataSource {
    private val random = Random(10)

    override fun generateData(): LiveData<String> = liveData {
        while (true) {
            //TODO 为什么当LifecycleOwner销毁后，这里就不再发射数据？
            val value = "结果是：${random.nextInt(10000)} in ${System.currentTimeMillis()}"
            LogUtil.e("tetetetete","LiveData DefaultDataSource $value")
            emit(value)
            delay(2000)
        }
    }
}

interface DataSource {

    fun generateData(): LiveData<String>

}