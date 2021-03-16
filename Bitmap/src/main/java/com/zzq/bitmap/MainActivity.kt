package com.zzq.bitmap

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.zzq.common.utils.DensityUtil

class MainActivity : AppCompatActivity() {

    private lateinit var ivOriginal: ImageView
    private lateinit var ivModify: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivOriginal = findViewById(R.id.iv_original)
        ivModify = findViewById(R.id.iv_modify)

        DensityUtil.getAndroidScreenProperty(this)
        ivModify.drawable.toBitmap()
    }
}