package com.zzq.coroutine.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzq.coroutine.net.NetManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class PictureViewModel : ViewModel() {

    init {
        viewModelScope.launch {
            obtainFirstPictureList()
        }
    }

    private suspend fun obtainFirstPictureList() {

        viewModelScope.launch {
            delay(500)
            val body1 = NetManager.gankApi.getGirlPictures().awaitResponse()
            Log.e("tetetetete", "PictureViewModel OnResponse $body1")
        }

    }

}