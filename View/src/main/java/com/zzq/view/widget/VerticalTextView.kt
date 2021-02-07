package com.zzq.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.zzq.common.utils.DensityUtil.dp2px
import com.zzq.view.ViewApp.Companion.instance

/**
 * 测试、调试，逐步完善竖直TextView的过程，里面有多个text显示，最终竖直的那个Text才是最终需要。
 *
 * 思路是把水平排列的文本旋转90度后显示
 * 1. 旋转画布90度
 * 2. 绘制文本，并且坐标与原先的坐标需要经过转换，因为没有转换走了很多弯路
 *
 * 需要对坐标系有足够的了解，以及坐标系方向的转换等
 */
class VerticalTextView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val mWidth = dp2px(instance, 300f)
    private val mHeight = dp2px(instance, 400f)
    private val imPaint = Paint()
    private val linePaint = Paint()
    private val mTextPaint = Paint()
    private val rectPaint = Paint()
    private val pointPaint = Paint()
    private val backgroundPaint = Paint()

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
        mTextPaint.textSize = 80F

        rectPaint.style = Paint.Style.STROKE
        rectPaint.strokeWidth = dp2px(instance, 2F).toFloat()
        rectPaint.color = Color.parseColor("#263238")

        pointPaint.style = Paint.Style.FILL
        pointPaint.strokeWidth = dp2px(instance, 10F).toFloat()
        pointPaint.color = Color.YELLOW

        backgroundPaint.color = Color.parseColor("#AFB1B3")
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
        val width = mWidth.toFloat()
        val height = mHeight.toFloat()
        canvas.drawRect(0F, 0F, width, height, backgroundPaint)

        val startX = width / 4
        val startY = height / 3

        //画两条线标记位置
        linePaint.strokeWidth = 4F
        //横线
        linePaint.color = Color.RED
        canvas.drawLine(0F, startY, width, startY, linePaint)
        //竖线
        linePaint.color = Color.BLUE
        canvas.drawLine(startX, 0F, startX, height, linePaint)

        //通过这个点来明确指出绘制文本的起始位置！也就是上面所绘制的两条线的交点
        canvas.drawPoint(startX, startY, pointPaint)

        val text = "测试：my text"
        canvas.drawText(text, startX, startY, mTextPaint)

        val fontMetrics = mTextPaint.getFontMetrics()
        val textHeight = fontMetrics.bottom - fontMetrics.top

        val rect = Rect()
        mTextPaint.getTextBounds(text, 0, text.length, rect)

        rect.left += startX.toInt()
        rect.right += startX.toInt()
        rect.top += startY.toInt()
        rect.bottom += startY.toInt()
        val w: Int = rect.width()
        val h: Int = rect.height()
        //通过该行打印，得出FontMetrics和getTextBounds()获取到的文本宽度是有差异的，而获得的文本高度却能基本一致。
        Log.e("tetetetete", "$textHeight, ${mTextPaint.measureText(text)}, ${rect}, $w, $h")
        canvas.drawRect(rect, rectPaint)

        //点1
        canvas.drawCircle(80F, 200F, 20F, pointPaint)
        canvas.save()
        //点2，注意点1和点2的位置，那么在旋转后绘制坐标应该做相应的转变
        canvas.drawCircle(80F, 200F, 20F, pointPaint)

        //实现方式1
        /*canvas.rotate(90F)
        canvas.drawText(text, startY, -startX, mTextPaint)*/

        //实现方式2
        canvas.rotate(90F, startX, startY)
        canvas.drawText(text, startX, startY, mTextPaint)
        canvas.restore()
    }
}