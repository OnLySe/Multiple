package com.zzq.rxjava

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class SimpleActivity : AppCompatActivity() {

    private lateinit var tvReceiveInfo: TextView
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)
        tvReceiveInfo = findViewById(R.id.tv_receive_info)

        handler.postDelayed({ createObservable1() }, 100)

    }

    private fun createObservable1() {
        Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {
                Log.e("rxjava2", "Observable emit 1")
                emitter.onNext(1)
                Thread.sleep(1000)

                Log.e("rxjava2", "Observable emit 2")
                emitter.onNext(2)
                Thread.sleep(1500)

                Log.e("rxjava2", "Observable emit 3")
                emitter.onNext(3)

                emitter.onComplete()
            }

        }).subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                Log.e("rxjava2", "Observer onSubscribe $d")
            }

            override fun onNext(t: Int) {
                Log.e("rxjava2", "Observer ${Thread.currentThread().name} ,onNext $t")
//                tvReceiveInfo.text = (tvReceiveInfo.text.toString()?:"").plus(t.toString())
                tvReceiveInfo.text = t.toString()
            }

            override fun onError(e: Throwable) {
                Log.e("rxjava2", "Observer onError")
            }

            override fun onComplete() {
                Log.e("rxjava2", "Observer onComplete")
                tvReceiveInfo.text = "onComplete!!"
            }
        })
    }
}