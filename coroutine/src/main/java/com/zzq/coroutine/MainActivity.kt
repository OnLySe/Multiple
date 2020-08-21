package com.zzq.coroutine

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.zzq.util.LogUtil.eLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var tvTitle:TextView
    private lateinit var viewModel:CoroutineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(CoroutineViewModel::class.java)
        tvTitle = findViewById(R.id.tv_title)

//        viewModel.getArticle()

//        viewModel.getArticle2().observe(this, Observer {
//            eLog(it.toString())
//            tvTitle.text = it.toString()
//        })

        viewModel.getArticle3().observe(this, Observer {
            eLog(it.toString())
            tvTitle.text = it.toString()
        })

        testLiveData()
//        test2()
    }

    private val mediatorLiveData = MediatorLiveData<String>()
    private val firstLiveData = MutableLiveData<Int>()
    private val secondLiveData = MutableLiveData<Float>()
    private fun testLiveData() {

        firstLiveData.observe(this, Observer { run { eLog("first $it") } })
        secondLiveData.observe(this, Observer { run { eLog("second $it") } })
        mediatorLiveData.addSource(firstLiveData) { eLog("mediator first $it") }
        mediatorLiveData.addSource(secondLiveData) { eLog("mediator second $it") }
        mediatorLiveData.observe(this, Observer { eLog("mediator data change $it") })

        lifecycleScope.launch {
            eLog("稍等，即将发出新变动")
            delay(2000)
//            firstLiveData.value = 10
//            secondLiveData.value = 3.33F

            mediatorLiveData.value = "67895AC"
        }
    }


    /*private fun test2() {
        val mediator = MediatorLiveData<List<String>>();
        val strLive = MutableLiveData<List<String>>();
        mediator.addSource(strLive, Observer {
            iLog("onchange"+it.size);
            mediator.value = it.filter {
                it.length>4
            }
        })

        mediator.observe(this, Observer {
            iLog("size==="+it.size)
        })

        val arrayList = ArrayList<String>()
        arrayList.add("8848 8848 88488")
        strLive.value = arrayList
    }*/
}
