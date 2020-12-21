package com.zzq.concurrent

import android.os.Bundle
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
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private val list = ArrayList<User>()
    private val map = HashMap<String, User>()
    private val random = Random(30)

    private val userAdapter: UserAdapter = UserAdapter()

    private val executorService: ScheduledExecutorService =  Executors.newScheduledThreadPool(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rvData = findViewById<RecyclerView>(R.id.rv_data)
        rvData.layoutManager = LinearLayoutManager(this)
        rvData.adapter = userAdapter


        executorService.scheduleAtFixedRate({
            if (map.size >= 50) {
                return@scheduleAtFixedRate
            }

            //创建User
            val user = User(ByteUtil.bytesToHexString(ByteUtil.ranDomBytes(5)), random.nextFloat())
            map.put(user.name, user)
            list.add(user)

        }, 500, 500, TimeUnit.MILLISECONDS)
        executorService.scheduleAtFixedRate({

            val index = random.nextInt(map.size)
            list[index].rate = 1024.1024f

            runOnUiThread { userAdapter.updateData(list) }
        }, 2000, 1000, TimeUnit.MILLISECONDS)
    }


    override fun onDestroy() {
        super.onDestroy()
        executorService.shutdown()
    }
}