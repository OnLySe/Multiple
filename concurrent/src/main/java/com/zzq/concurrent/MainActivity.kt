package com.zzq.concurrent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzq.common.utils.ByteUtil
import com.zzq.concurrent.adapter.UserAdapter
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val size = 100
    private val list = ArrayList<User>(size)
    private val random = Random()

    private val userAdapter: UserAdapter = UserAdapter()

    private val executorService: ScheduledExecutorService = Executors.newScheduledThreadPool(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rvData = findViewById<RecyclerView>(R.id.rv_data)
        rvData.layoutManager = LinearLayoutManager(this)
        rvData.adapter = userAdapter

        executorService.scheduleAtFixedRate({
            if (list.size == size-1) {
                //通过抛出异常，使任务终止！
                Log.e("tetetetete", "add or modify, 不需要重复添加了，该任务可以停止了")
                throw IllegalStateException("不需要重复添加了，该任务可以停止了")
            }
            var randomInt = random.nextInt(size)
            //第一次执行任务会导致越界访问，但不会导致程序闪退
//            if (randomInt <= list.size) {

            if (randomInt > list.size) {
                //创建User
                val user = User(ByteUtil.bytesToHexString(ByteUtil.ranDomBytes(5)), random.nextFloat())
                list.add(user)
            } else {
                if (randomInt != 0) {
                    randomInt -= 1
                }
                val value = list.get(randomInt)!!
                value.rate = if (value.rate < 1024.1024) {
                    1024.1024f
                } else {
                    value.rate * 2
                }
            }
            Log.e("tetetetete", "${Thread.currentThread().name} add or modify, list size ${list.size}, random num is $randomInt")
        }, 1000, 300, TimeUnit.MILLISECONDS)

        executorService.scheduleAtFixedRate({
            Log.e("tetetetete", "${Thread.currentThread().name} read, list size ${list.size}")
            runOnUiThread {
                userAdapter.updateData(list)
            }
        }, 2000, 1500, TimeUnit.MILLISECONDS)
    }


    override fun onDestroy() {
        super.onDestroy()
        executorService.shutdown()
    }
}