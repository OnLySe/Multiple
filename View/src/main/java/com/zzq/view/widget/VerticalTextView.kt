package com.zzq.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zzq.common.utils.DensityUtil.dp2px
import com.zzq.view.ViewApp.Companion.instance

class VerticalTextView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val mWidth = dp2px(instance, 300f)
    private val mHeight = dp2px(instance, 400f)
    private val imPaint = Paint()
    private val linePaint = Paint()
    private val mTextPaint = Paint()

    init {
        initView()
    }
    private fun initView() {
        imPaint.color = Color.parseColor("#968883")

        linePaint.color = Color.RED
        linePaint.strokeWidth = dp2px(instance, 2F).toFloat()

//        mTextPaint.color = Color.parseColor("#499C54")
        mTextPaint.style = Paint.Style.FILL
        mTextPaint.strokeWidth = 12F
        mTextPaint.textSize = 100F
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
                Math.max(suggestedMinimumWidth,
                        resolveSize(mWidth, widthMeasureSpec)),
                Math.max(suggestedMinimumHeight,
                        resolveSize(mHeight, heightMeasureSpec))
        )
    }

    override fun onDraw(canvas: Canvas) {
//        val width = mWidth.toFloat()
//        val height = mHeight.toFloat()
//        canvas.drawRect(0f, 0f, width, height, imPaint)
//        canvas.drawLine(0F, height / 2, width, height / 2, linePaint)
//        canvas.drawLine(width / 2, 0F, width / 2, height, linePaint)
//
////        // 将坐标系原点移动到画布正中心
////        canvas.translate((mWidth / 2).toFloat(), (mHeight / 2).toFloat())
//        val fontMetrics: Paint.FontMetricsInt = mTextPaint.fontMetricsInt
//        val text = "hello world"
//        val measureLength = mTextPaint.measureText(text)
//        canvas.drawText(text, width / 2, height / 2, mTextPaint)



        val text = "测试：my text"
        canvas.drawText(text, 200F, 400F, mTextPaint)

        //画两条线标记位置
        linePaint.strokeWidth = 4F
        linePaint.color = Color.RED
        canvas.drawLine(0F, 400F, 2000F, 400F, linePaint)
        linePaint.color = Color.BLUE
        canvas.drawLine(200F, 0F, 200F, 2000F, linePaint)

        canvas.save()
        canvas.rotate(90F)
        canvas.drawText(text, 200F, 400F, mTextPaint)
        canvas.restore()
    }
}