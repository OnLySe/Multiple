package com.zzq.jetpack.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzq.common.utils.LogUtil.eLog
import com.zzq.common.utils.showToast
import com.zzq.jetpack.Config
import com.zzq.jetpack.MyLifeCycleService
import com.zzq.jetpack.R
import com.zzq.jetpack.bean.ClickFunction
import com.zzq.jetpack.list.FunctionsAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var rvItems: RecyclerView
    private lateinit var serviceIntent : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        rvItems = findViewById(R.id.rv_items)
        rvItems.layoutManager = LinearLayoutManager(this)
        val list = createData()
        rvItems.adapter = FunctionsAdapter(list)
    }

    private fun createData(): List<ClickFunction> {
        val list = ArrayList<ClickFunction>()
        list.add(ClickFunction("Activity与Lifecycle", {startActivity(Intent(this,TestLifecycleActivity::class.java))}))

        list.add(ClickFunction("Service与Lifecycle", { showToast("展示Service与Lifecycle关系") }))
        serviceIntent = Intent(this, MyLifeCycleService::class.java)
        list.add(ClickFunction("startService", { startService(serviceIntent) }, Config.TYPE_SECOND))
        list.add(ClickFunction("stopService", { stopService(serviceIntent) }, Config.TYPE_SECOND))


        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        eLog("lifecycle MainActivity onDestroy")
    }
}