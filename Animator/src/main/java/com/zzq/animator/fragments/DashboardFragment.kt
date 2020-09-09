package com.zzq.animator.fragments

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zzq.animator.R
import com.zzq.animator.databinding.FragmentDashboardBinding
import com.zzq.animator.vm.ViewPropertyViewModel

/**
 * ValueAnimator演示
 */
class DashboardFragment : Fragment() {

    private lateinit var viewModel: ViewPropertyViewModel
    private lateinit var textDashboard1: TextView
    private lateinit var textDashboard2: TextView
    private lateinit var textDashboard3: TextView
    private lateinit var textDashboard4: TextView
    private lateinit var textDashboard5: TextView
    private lateinit var textDashboard6: TextView
    private lateinit var textDashboard7: TextView
    private lateinit var textDashboard8: TextView
    private lateinit var textDashboard9: TextView

    private lateinit var animator1: ValueAnimator
    private lateinit var animator2: ValueAnimator
    private lateinit var animator3: ValueAnimator
    private lateinit var animator4: ValueAnimator
    private lateinit var animator5: ValueAnimator
    private lateinit var animator6: ValueAnimator
    private lateinit var animator7: ValueAnimator
    private lateinit var animator8: ValueAnimator
    private lateinit var animator9: ValueAnimator

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel =
                ViewModelProvider(this).get(ViewPropertyViewModel::class.java)

        val dataBinding = DataBindingUtil.inflate<FragmentDashboardBinding>(inflater,
                R.layout.fragment_dashboard, container, false)
        initView(dataBinding)

        animator1 = createIntAnimator(textDashboard1, {
            repeatCount = 4
            repeatMode = ValueAnimator.RESTART
        }, { text = "progress:${it.animatedValue.toString()}" })
        animator2 = createIntAnimator(textDashboard2, {
            repeatCount = 4
            repeatMode = ValueAnimator.REVERSE
        }, { text = "progress:${it.animatedValue.toString()}" })
        animator3 = createIntAnimator(textDashboard3, action = { text = "progress:${it.animatedValue.toString()}" })


        animator4 = createFloatAnimator(textDashboard4, {
            repeatCount = 4
            repeatMode = ValueAnimator.RESTART
        }, action = { text = "progress:${it.animatedValue.toString()}" })
        animator5 = createFloatAnimator(textDashboard5, {
            repeatCount = 4
            repeatMode = ValueAnimator.REVERSE
        }, action = { text = "progress:${it.animatedValue.toString()}" })
        animator6 = createFloatAnimator(textDashboard6, action = { text = "progress:${it.animatedValue.toString()}" })


        animator7 = createArgbAnimator(textDashboard7, {
            val temp: Int = it.animatedValue as Int
            val value = "#${Integer.toHexString(temp)}"
            text = value
            setBackgroundColor(Color.parseColor(value))
        })
        animator8 = createIntAnimator(textDashboard8, action = {
            Log.e("tetetetete", "act ${it.animatedValue.toString()}")
            text = "progress:${it.animatedValue.toString()}"
        })

        //TODO 标记，可自由设置哪些显示，哪些不显示
        setInVisible(
                textDashboard1,
                textDashboard2,
                textDashboard3,
                textDashboard4,
                textDashboard5,
                textDashboard6,
//                textDashboard7,
//                textDashboard8,
                textDashboard9
        )
        return dataBinding.root
    }

    private fun createIntAnimator(textView: TextView,
                                  setting: ValueAnimator.() -> Unit = {},
                                  action: TextView.(ValueAnimator) -> Unit = {},
                                  duration: Long = 3000): ValueAnimator {
        return ValueAnimator.ofInt(1, 31)
                .setDuration(duration)
                .apply {
                    setting()
                    addUpdateListener {
                        textView.action(it)
                    }
                }
    }

    private fun createFloatAnimator(textView: TextView,
                                    setting: ValueAnimator.() -> Unit = {},
                                    action: TextView.(ValueAnimator) -> Unit,
                                    duration: Long = 6000): ValueAnimator {
        return ValueAnimator.ofFloat(1.58F, 1.60F)
                .setDuration(duration)
                .apply {
                    setting()
                    addUpdateListener { textView.action(it) }
                }
    }

    private fun createArgbAnimator(textView: TextView, action: TextView.(ValueAnimator) -> Unit,
                                   duration: Long = 6000): ValueAnimator {
        return ValueAnimator.ofArgb(0xDE594B, 0x359664)
                .setDuration(duration).also {
                    it.addUpdateListener {
                        textView.action(it)
                    }
                }
    }

    //TODO object Animator

    private fun initView(dataBinding: FragmentDashboardBinding) {
        textDashboard1 = dataBinding.textDashboard1
        textDashboard2 = dataBinding.textDashboard2
        textDashboard3 = dataBinding.textDashboard3
        textDashboard4 = dataBinding.textDashboard4
        textDashboard5 = dataBinding.textDashboard5
        textDashboard6 = dataBinding.textDashboard6
        textDashboard7 = dataBinding.textDashboard7
        textDashboard8 = dataBinding.textDashboard8
        textDashboard9 = dataBinding.textDashboard9

        textDashboard1.text = "textDashboard1"
        textDashboard2.text = "textDashboard2"
        textDashboard3.text = "textDashboard3"
        textDashboard4.text = "textDashboard4"
        textDashboard5.text = "textDashboard5"
        textDashboard6.text = "textDashboard6"
        textDashboard7.text = "textDashboard7"
        textDashboard8.text = "textDashboard8"
        textDashboard9.text = "textDashboard9"
    }

    /**
     * 使某些TextView不可见，避免对其他View的观察结果产生观察影响
     */
    private fun setInVisible(vararg textViews: TextView) {
        textViews.forEach { it.visibility = View.INVISIBLE }
    }

    override fun onResume() {
        super.onResume()
        animator1.start()
        animator2.start()
        animator3.start()
        animator4.start()
        animator5.start()
        animator6.start()
        animator7.start()

        if (this::animator8.isInitialized) {
            animator8.start()
        }
        if (this::animator9.isInitialized) {
            animator9.start()
        }
    }

    override fun onPause() {
        super.onPause()
        animator1.cancel()
        animator2.cancel()
        animator3.cancel()
        animator4.cancel()
        animator5.cancel()
        animator6.cancel()
        animator7.cancel()

        if (this::animator8.isInitialized) {
            animator8.cancel()
        }
        if (this::animator9.isInitialized) {
            animator9.cancel()
        }
    }
}