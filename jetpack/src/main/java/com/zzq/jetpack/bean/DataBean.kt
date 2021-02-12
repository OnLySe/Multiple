package com.zzq.jetpack.bean

import android.view.View
import com.zzq.jetpack.app.Config

data class ClickFunction( val title: String, val action: (View) -> Unit,val type: Int = Config.TYPE_FIRST)