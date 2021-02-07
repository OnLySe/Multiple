package com.zzq.jetpack.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gyf.immersionbar.ImmersionBar
import com.zzq.jetpack.R
import com.zzq.jetpack.navigation.HomeViewModel

/**
 * 需要注意：
 * 1. 必须是在导航图内的Fragment才能继承
 * 2. 每个子类都要有Toolbar，且Toolbar的默认id是toolbar（R.id.toolbar),可通过setToolbarId返回自定义的Toolbar Id
 */
open abstract class BaseFragment : SimpleImmersionFragment() {

    protected abstract fun getLayoutId(): Int
    protected lateinit var defaultToolbar: Toolbar

    protected open fun initView(view: View) {}

    /**
     * 不能在 initView 中使用
     */
    protected lateinit var homeViewModel: HomeViewModel

    @IdRes
    @Nullable
    protected fun setToolbarId(): Int? = R.id.toolbar

    /**
     * {@link HasDefaultViewModelProviderFactory#getDefaultViewModelProviderFactory() default factory}
     *  不应使onCreateView变成可重写，需要用final做限制，如有必要，可使用apply！
     */
    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false).apply {
            defaultToolbar = if (setToolbarId() == null) {
                findViewById(R.id.toolbar)
            } else {
                findViewById(setToolbarId()!!)
            }
            setToolbar(defaultToolbar)
            initView(this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //TODO 如此创建或有不妥之处！
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    /**
     * 默认使用这种方式来设置Toolbar，即返回上一层，支持设置Toolbar左边icon，不设置可不传
     */
    protected open fun setToolbarReturnFun(@DrawableRes iconId: Int? = null) {
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
            if (iconId != null) {
                it.setHomeAsUpIndicator(iconId)
            }
            defaultToolbar?.setNavigationOnClickListener { navigateUp() }
        }
    }

    protected open fun setToolbarReturnFun(function: () -> Unit, @DrawableRes iconId: Int? = null) {
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
            if (iconId != null) {
                it.setHomeAsUpIndicator(iconId)
            }
            defaultToolbar?.setNavigationOnClickListener { function() }
        }
    }

    private fun setToolbar(toolbar: Toolbar) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        ImmersionBar.setTitleBar(this, toolbar)
    }

    protected fun navigate(@IdRes resId: Int, args: Bundle? = null) {
        findNavController().navigate(resId, args)
    }

    protected fun navigateUp() {
        findNavController().navigateUp()
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).init();
    }
}