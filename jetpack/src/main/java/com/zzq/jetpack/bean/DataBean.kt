package com.zzq.jetpack.bean

import android.view.View
import com.zzq.jetpack.Config

data class ClickFunction( val title: String, val action: (View) -> Unit,val type: Int = Config.TYPE_FIRST)