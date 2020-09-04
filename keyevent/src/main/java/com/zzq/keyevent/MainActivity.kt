package com.zzq.keyevent

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zzq.keyevent.databinding.ActivityMainBinding
import com.zzq.util.LogUtil.eLog
import com.zzq.util.ToastUtil.shortToast

class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eLog("onCreate ${applicationInfo.targetSdkVersion}")
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.title1 = "repeat show"
        dataBinding.title1Click = View.OnClickListener {
            createDialog1()
        }
    }

    private fun createDialog1() {
        AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("标题")
                .setMessage("消息内容是：不会输不会输")
//                .setOnDismissListener {
//                    eLog("setOnDismissListener ")
//                    shortToast(this, "setOnDismissListener")
//                }
//                .setOnCancelListener {
//                    eLog("setOnCancelListener ")
//                    shortToast(this, "setOnCancelListener")
//                }
                .setOnKeyListener { dialog, keyCode, event ->
                    eLog("setOnKeyListener $keyCode")
                    val returnValue = false
                    shortToast(this, "setOnKeyListener  $keyCode $returnValue")
                    return@setOnKeyListener returnValue
                }
                .setNegativeButton("取消吧") { dialog, _ ->
                    shortToast(this, "好吧")
                    dialog.dismiss()
                }
                .setPositiveButton("可以了") { dialog, _ ->
                    shortToast(this, "我信了")
                    dialog.dismiss()
                }
                .create().also { it.show() }
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
}
