package com.zzq.jetpack.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.gyf.immersionbar.components.SimpleImmersionProxy
import com.zzq.jetpack.R

/**
 * 需要注意：
 * 1. 必须是在导航图内的Fragment才能继承
 * 2. 每个子类都要有Toolbar，且Toolbar的默认id是toolbar（R.id.toolbar),可通过setToolbarId返回自定义的Toolbar Id
 *
 * TODO 当前不支持DataBinding!
 */
open abstract class BaseFragment : Fragment(), SimpleImmersionOwner {

    protected abstract fun getLayoutId(): Int
    protected lateinit var defaultToolbar: Toolbar

    protected open fun initView(view: View) {}

    @IdRes
    @Nullable
    protected fun setToolbarId(): Int? = R.id.toolbar

    /**
     * ImmersionBar代理类
     */
    private val mSimpleImmersionProxy = SimpleImmersionProxy(this)

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mSimpleImmersionProxy.isUserVisibleHint = isVisibleToUser
    }

    override fun onDestroy() {
        super.onDestroy()
        mSimpleImmersionProxy.onDestroy()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mSimpleImmersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mSimpleImmersionProxy.onConfigurationChanged(newConfig)
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    override fun immersionBarEnabled(): Boolean {
        return true
    }

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
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState)
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