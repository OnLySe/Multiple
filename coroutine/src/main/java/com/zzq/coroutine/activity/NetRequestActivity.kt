package com.zzq.coroutine.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.zzq.common.utils.LogUtil.eLog
import com.zzq.coroutine.R
import com.zzq.coroutine.vm.ExerciseViewModel
import com.zzq.coroutine.vm.GankViewModel

class NetRequestActivity : AppCompatActivity() {
    private lateinit var tvInfo: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var firstViewModel: ExerciseViewModel

    private lateinit var gankViewModel: GankViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_request)
        firstViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        gankViewModel = ViewModelProvider(this).get(GankViewModel::class.java)
        toolbar = findViewById(R.id.toolbar)
        tvInfo = findViewById(R.id.tv_info)

        val targetSdkVersion = applicationInfo.targetSdkVersion
        eLog(targetSdkVersion.toString())
        firstHandCoroutineTest()
    }

    private fun firstHandCoroutineTest() {

        firstViewModel.getArticle1().observe(this, {
            eLog(it.toString())
            tvInfo.text = it.toString()
        })
        /*firstViewModel.getArticle3().observe(this, {
            eLog(it.toString())
            tvInfo.text = it.toString()
        })

        firstViewModel.getArticle4().observe(this, {
            eLog(it.toString())
            tvInfo.text = it.toString()
        })*/
    }
}