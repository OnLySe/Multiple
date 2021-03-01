package com.zzq.coroutine

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.zzq.common.utils.LogUtil.eLog
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var viewModel: CoroutineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(CoroutineViewModel::class.java)
        tvTitle = findViewById(R.id.tv_title)

        val targetSdkVersion = applicationInfo.targetSdkVersion
        eLog(targetSdkVersion.toString())
        firstHandCoroutineTest()
    }

    private fun firstHandCoroutineTest() {
        //        viewModel.getArticle()

        viewModel.getArticle2().observe(this, Observer {
            eLog(it.toString())
            tvTitle.text = it.toString()
        })
        viewModel.getArticle3().observe(this, Observer {
            eLog(it.toString())
            tvTitle.text = it.toString()
        })

        viewModel.getArticle4().observe(this, Observer {
            eLog(it.toString())
            tvTitle.text = it.toString()
        })

        //这行也是可以的，最为原始的方式
        //        viewModel.getArticle1()

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

        test3()
        test4()
    }

    /**
     * 在[test3]的基础上，测试切换协程执行函数，是否也能切换到指定协程运行。
     * 目的是在调用处，或者说挂起点就切换协程，不需要在函数内部切换协程
     * 结果是成功的。
     */
    private fun test4() {
        eLog("test4 ${Thread.currentThread().name}")

        GlobalScope.launch(Dispatchers.IO) {
            test41()
            delay(200)
            test41()
            withContext(Dispatchers.Main) {
                test41()
            }
            test41()

        }
    }

    private suspend fun test41() {
        eLog("test41 ${Thread.currentThread().name}")
        //以下这行在用IO执行会闪退，因为只有主线程能够更新UI
//        tvTitle.text = "test41"
    }

    /**
     * 测试协程切换是否有效
     */
    private fun test3() = runBlocking {
        eLog("test3")

        GlobalScope.launch(Dispatchers.IO) {
            eLog("test3 " + Thread.currentThread().name)
            delay(200)
            eLog("test3 " + Thread.currentThread().name)
            withContext(Dispatchers.Main) {
                eLog("test3 " + Thread.currentThread().name)
            }
            eLog("test3 " + Thread.currentThread().name)

        }
//        delay(1000)
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
