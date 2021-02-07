package com.zzq.animator

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zzq.animator.activity.ViewPropertyActivity
import com.zzq.animator.databinding.ActivityMainBinding
import com.zzq.common.utils.showToast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
                this,
                R.layout.activity_main
        )

        binding.viewPropertyListener = View.OnClickListener {
            startActivity(Intent(this, ViewPropertyActivity::class.java))
        }
        binding.activityListener = View.OnClickListener {
            showToast(this@MainActivity, "activityListener")
        }
        binding.dialogListener = View.OnClickListener {
            showToast(this@MainActivity, "dialogListener")
        }
        binding.fragmentListener = View.OnClickListener {
            showToast(this@MainActivity, "fragmentListener")
        }
        binding.dialogFragmentListener = View.OnClickListener {
            showToast(this@MainActivity, "dialogFragmentListener")
        }
    }
}
