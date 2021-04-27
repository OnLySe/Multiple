package com.zzq.jetpack.lifecycle

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

class ActivityLifecycleModel(activity: AppCompatActivity) :
    LifecycleModel(activity, "ActivityLifecycleModel") {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun whenOnResume() {
        Log.d(specialName, "whenOnResume create $owner")
    }

}