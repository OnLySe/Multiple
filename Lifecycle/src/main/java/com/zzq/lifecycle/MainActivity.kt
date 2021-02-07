package com.zzq.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zzq.lifecycle.vm.HomeViewModel
import com.zzq.lifecycle.vm.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeViewModelFactory = HomeViewModelFactory("800")
        val viewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

        Log.e("tetetetete", "${viewModel.getValue()} ")
    }
}