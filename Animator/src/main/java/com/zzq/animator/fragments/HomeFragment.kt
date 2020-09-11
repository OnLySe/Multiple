package com.zzq.animator.fragments

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zzq.animator.R
import com.zzq.animator.databinding.FragmentHomeBinding
import com.zzq.animator.vm.ViewPropertyViewModel
import com.zzq.util.DensityUtil

/**
 * ObjectAnimator演示
 */
class HomeFragment : Fragment() {

    private lateinit var viewModel: ViewPropertyViewModel
    private lateinit var ivLauncher1: ImageView
    private lateinit var ivLauncher2: ImageView

    private lateinit var dataBinding: FragmentHomeBinding
    private var translationX: Float = 0F
    private val duration = 2000L

    private val handler = Handler()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false
        )
        viewModel = ViewModelProvider(this).get(ViewPropertyViewModel::class.java)
        ivLauncher1 = dataBinding.ivLauncher1
        ivLauncher2 = dataBinding.ivLauncher2

        translationX = DensityUtil.dp2px(requireActivity(), 200F).toFloat()
        handler.postDelayed({
            test1()
            test2()
            test3()
        }, 100)

        return dataBinding.root
    }

    private fun test3() {
        //动画加速进行
        startAnimator(dataBinding.tvText3, AccelerateInterpolator())
        //快速完成动画，超出再回到结束样式
        startAnimator(dataBinding.tvText4, OvershootInterpolator())
        //先加速再减速
        startAnimator(dataBinding.tvText5, AccelerateDecelerateInterpolator())
        //先退后再加速前进
        startAnimator(dataBinding.tvText6, AnticipateInterpolator())
        //先退后再加速前进，超出终点后再回终点
        startAnimator(dataBinding.tvText7, AnticipateOvershootInterpolator())
        //最后阶段弹球效果
        startAnimator(dataBinding.tvText8, BounceInterpolator())
        //周期运动
        startAnimator(dataBinding.tvText9, CycleInterpolator(1F))
        //减速
        startAnimator(dataBinding.tvText10, DecelerateInterpolator())
        //匀速
        startAnimator(dataBinding.tvText11, LinearInterpolator())
    }

    private fun startAnimator(target: TextView, interpolator: TimeInterpolator) {
        ObjectAnimator.ofFloat(target, "translationX", translationX)
                .setDuration(duration).also { it.interpolator = interpolator }.start()
    }

    private fun test2() {
        //最为普通的用法
        ObjectAnimator.ofFloat(ivLauncher2, "translationX", translationX).setDuration(duration).start()
    }

    //ViewPropertyAnimator
    private fun test1() {
        ivLauncher1.animate().also { it.duration = duration }.apply { translationXBy(translationX) }
    }
}