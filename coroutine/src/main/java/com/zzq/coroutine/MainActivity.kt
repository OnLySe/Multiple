package com.zzq.coroutine

import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.zzq.util.LogUtil.eLog
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var viewModel: CoroutineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(CoroutineViewModel::class.java)
        tvTitle = findViewById(R.id.tv_title)
        val dialog = AlertDialog.Builder(this).setCancelable(false)

        val targetSdkVersion = applicationInfo.targetSdkVersion
        eLog(targetSdkVersion.toString())
//        viewModel.getArticle()

//        viewModel.getArticle2().observe(this, Observer {
//            eLog(it.toString())
//            tvTitle.text = it.toString()
//        })
//        viewModel.getArticle3().observe(this, Observer {
//            eLog(it.toString())
//            tvTitle.text = it.toString()
//        })

        viewModel.getArticle4().observe(this, Observer {
            eLog(it.toString())
            tvTitle.text = it.toString()
        })

//        lifecycleScope.launch {
//            //EmptyCoroutineContext默认不切换线程
//            eLog("getData ${Thread.currentThread().name}")
//            delay(2000)
//            val article5 = viewModel.getArticle5()
//            tvTitle.text = article5.toString()
//        }

//        testLiveData()
//        test1()
//        test2()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val onKeyDown = super.onKeyDown(keyCode, event)
        eLog("onKeyDown $onKeyDown $keyCode ${event!!.keyCode}")
        return onKeyDown
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        val onKeyUp = super.onKeyUp(keyCode, event)
        eLog("onKeyUp $onKeyUp $keyCode ${event!!.keyCode}")
        return onKeyUp
    }

    override fun onBackPressed() {
        eLog("onBackPressed")
        super.onBackPressed()
    }

    private fun test1() {

        lifecycleScope.launch(Dispatchers.Default) {

            val result1 = async {
                delay(50)
                eLog("test1 ${Thread.currentThread().name}")
                "test1"
            }
            eLog("test1 ${result1.await()}")

            delay(80)
            val result2 = async(Dispatchers.Main) {
                delay(50)
                eLog("test2 ${Thread.currentThread().name}")
                "test2"
            }
            eLog("test2 ${result2.await()}")

            delay(80)
            val result3 = async(Dispatchers.IO) {
                delay(50)
                eLog("test3 ${Thread.currentThread().name}")
                "test3"
            }
            eLog("test3 ${result3.await()}")

            delay(80)
            val result4 = async(Dispatchers.Default) {
                delay(50)
                eLog("test4 ${Thread.currentThread().name}")
                "test4"
            }
            eLog("test4 ${result4.await()}")
        }
    }

    private fun test2() {

        lifecycleScope.launch {
            eLog("test2 inside start ${Thread.currentThread().name}")
            delay(3000)
            eLog("-------------------------------------------------------------------")
            val result1 = withContext(Dispatchers.Default) {
                delay(50)
                eLog("test21 ${Thread.currentThread().name}")
                "test21"
            }
            eLog("test21 $result1")

            delay(80)
            val result2 = withContext(Dispatchers.Main) {
                delay(50)
                eLog("test22 ${Thread.currentThread().name}")
                "test22"
            }
            eLog("test22 $result2")

            delay(80)
            val result3 = withContext(Dispatchers.IO) {
                delay(50)
                eLog("test23 ${Thread.currentThread().name}")
                "test23"
            }
            eLog("test23 $result3")
        }
        eLog("outside ${Thread.currentThread().name}")
    }

    private fun testLiveData() {
        val firstLiveData = MutableLiveData<Int>()
        val secondLiveData = MutableLiveData<Float>()
        val mediatorLiveData = MediatorLiveData<String>()
        firstLiveData.observe(this, Observer { run { eLog("first lifeCycle onChanged $it") } })
        secondLiveData.observe(this, Observer { run { eLog("second lifeCycle onChanged $it") } })
        mediatorLiveData.observe(this, Observer { eLog("mediator onChanged $it") })

        mediatorLiveData.addSource(firstLiveData) {
            eLog("mediator addSource first $it")
            mediatorLiveData.value = "first onChange $it"
        }
        mediatorLiveData.addSource(secondLiveData) {
            eLog("mediator addSource second $it")
            mediatorLiveData.value = "second onChange $it"
        }

        lifecycleScope.launch(Dispatchers.IO) {
            delay(500L)
            firstLiveData.postValue(10)
            delay(30L)
            secondLiveData.postValue(3.33F)
        }
    }
}
