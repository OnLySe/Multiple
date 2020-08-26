package com.zzq.coroutine

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.zzq.util.LogUtil.eLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var viewModel: CoroutineViewModel
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
//        viewModel.getArticle3().observe(this, Observer {
//            eLog(it.toString())
//            tvTitle.text = it.toString()
//        })

        /*viewModel.getArticle4().observe(this, Observer {
            eLog(it.toString())
            tvTitle.text = it.toString()
        })*/

        lifecycleScope.launch {
            //EmptyCoroutineContext默认不切换线程
            eLog("getData ${Thread.currentThread().name}")
            delay(2000)
            val article5 = viewModel.getArticle5()
            tvTitle.text = article5.toString()
        }

        testLiveData()
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
