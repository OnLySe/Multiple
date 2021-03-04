package com.zzq.coroutine.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zzq.common.utils.LogUtil.eLog
import com.zzq.coroutine.R
import com.zzq.coroutine.vm.ExerciseViewModel

class NetRequestActivity : AppCompatActivity() {
    private lateinit var tvInfo: TextView
    private lateinit var firstViewModel: ExerciseViewModel

    //    private lateinit var pictureViewModel: PictureViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_request)
        firstViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
//        pictureViewModel = ViewModelProvider(this).get(PictureViewModel::class.java)
        tvInfo = findViewById(R.id.tv_info)

        val targetSdkVersion = applicationInfo.targetSdkVersion
        eLog(targetSdkVersion.toString())
        firstHandCoroutineTest()
    }

    private fun firstHandCoroutineTest() {
        //        viewModel.getArticle()

        firstViewModel.getArticle2().observe(this, Observer {
            eLog(it.toString())
            tvInfo.text = it.toString()
        })
        firstViewModel.getArticle3().observe(this, Observer {
            eLog(it.toString())
            tvInfo.text = it.toString()
        })

        firstViewModel.getArticle4().observe(this, Observer {
            eLog(it.toString())
            tvInfo.text = it.toString()
        })
    }
}