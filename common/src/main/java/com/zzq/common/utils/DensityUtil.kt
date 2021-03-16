package com.zzq.common.utils

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

object DensityUtil {

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun sp2px(context: Context, spValue: Float): Int {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                spValue,
                context.resources.displayMetrics
        ).toInt()
    }

    fun getAndroidScreenProperty(context: Context) {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        // 屏幕宽度（像素）
        val width = dm.widthPixels
        // 屏幕高度（像素）
        val height = dm.heightPixels
        // 屏幕密度（0.75 / 1.0 / 1.5）
        val density = dm.density
        // 屏幕密度dpi（120 / 160 / 240）
        val densityDpi = dm.densityDpi
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        // 屏幕宽度(dp)
        val screenWidth = (width / density).toInt()
        // 屏幕高度(dp)
        val screenHeight = (height / density).toInt()
        LogUtil.e("DensityUtil", "屏幕宽度（像素）：$width")
        LogUtil.e("DensityUtil", "屏幕高度（像素）：$height")
        LogUtil.e("DensityUtil", "屏幕密度（0.75 / 1.0 / 1.5）by1：$density")
        LogUtil.e("DensityUtil", "屏幕密度（0.75 / 1.0 / 1.5）by2：${context.resources.displayMetrics.density}")
        LogUtil.e("DensityUtil", "屏幕密度dpi（120 / 160 / 240）：$densityDpi")
        LogUtil.e("DensityUtil", "屏幕宽度（dp）：$screenWidth")
        LogUtil.e("DensityUtil", "屏幕高度（dp）：$screenHeight")
        /**
         * 三星S10打印：
         * 屏幕宽度（像素）：1440
         * 屏幕高度（像素）：2723
         * 屏幕密度（0.75 / 1.0 / 1.5）：3.5
         * 屏幕密度dpi（120 / 160 / 240）：560
         * 屏幕宽度（dp）：411
         * 屏幕高度（dp）：778
         */
    }

    /**
     * 获取屏幕宽度，单位px
     *
     * @param context 上下文
     * @return 屏幕宽度，单位px
     */
    fun getRealWidth(context: Context): Float {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        // 屏幕宽度（像素）
        val width = dm.widthPixels
        // 屏幕高度（像素）
        val height = dm.heightPixels
        // 屏幕密度（0.75 / 1.0 / 1.5）
        val density = dm.density
        // 屏幕密度dpi（120 / 160 / 240）
        val densityDpi = dm.densityDpi
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        // 屏幕宽度(dp)

        //华为 mate9和三星s8获取到的density值是有异议的
        LogUtil.e("DensityUtil", dm.toString())
        LogUtil.e("DensityUtil", "density: $density")
        LogUtil.e("DensityUtil", "width: $width")
        LogUtil.e("DensityUtil", "height: $height")
        LogUtil.e("DensityUtil", context.resources.displayMetrics.toString())
        /**
         * 同一部手机（三星S10）的打印：
         * DisplayMetrics{density=3.5, width=1440, height=2723, scaledDensity=3.5, xdpi=554.181, ydpi=551.542}
         * density: 3.5
         * width: 1440
         * height: 2723
         * DisplayMetrics{density=4.0, width=1440, height=2723, scaledDensity=4.4, xdpi=554.181, ydpi=551.542}
         */
        val screenWidth = (width / density).toInt()
        // 屏幕高度(dp)
        val screenHeight = (height / density).toInt()
        return width.toFloat()
    }

    /**
     * 获取屏幕高度，单位px
     *
     * @param context 上下文
     * @return 屏幕高度，单位px
     */
    fun getRealHeight(context: Context): Float {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        // 屏幕宽度（像素）
        val width = dm.widthPixels
        // 屏幕高度（像素）
        val height = dm.heightPixels
        // 屏幕密度（0.75 / 1.0 / 1.5）
        val density = dm.density
        // 屏幕密度dpi（120 / 160 / 240）
        val densityDpi = dm.densityDpi
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        // 屏幕宽度(dp)
        return height.toFloat()
    }

    /**
     * point.x和point.y拿到的好像是屏幕分辨率的宽高
     *
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getRealSize(point)
        LogUtil.e("DensityUtil", "屏幕宽度：pixel：" + point.x)
        LogUtil.e("DensityUtil", "屏幕宽度：pixel：" + point.y)
        /**
         * 三星S10打印：
         * 屏幕宽度：pixel：1440
         * 屏幕宽度：pixel：3040
         */
        return point.x
    }
}