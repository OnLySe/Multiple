package com.zzq.itemdecoration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val adapter = MainAdapter()
    private lateinit var rvMain: RecyclerView
    val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.rv_main)
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = adapter
        val verticalProgressDecoration = VerticalProgressDecoration(this)
        verticalProgressDecoration.bottomMargin = DensityUtil.dp2px(this, 10F)
        rvMain.addItemDecoration(verticalProgressDecoration)
        adapter.addNewData(createData())

    }

    private fun createData(): ArrayList<String> {
        val list = ArrayList<String>()
        repeat(30) {
            list.add("data: ${random.nextInt(50) + 10}")
        }
        return list
    }

    fun addData(view: View) {
        adapter.addData("data: ${random.nextInt(50) + 10}")
        rvMain.smoothScrollToPosition(adapter.itemCount)
    }
}
