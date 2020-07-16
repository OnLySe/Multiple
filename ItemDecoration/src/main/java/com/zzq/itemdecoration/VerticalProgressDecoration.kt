package com.zzq.itemdecoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class VerticalProgressDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    private val circlePaint = Paint()
    private val linePaint = Paint()
    var radius = DensityUtil.dp2px(context, 9F)
    var strokeWidth = DensityUtil.dp2px(context, 1F)
    var leftMargin = DensityUtil.dp2px(context, 50F)
    var rightMargin = DensityUtil.dp2px(context, 20F)
    var topMargin = 0
    var bottomMargin = 0

    //当前进度位置
    private var curPosition = 3

    init {
        circlePaint.flags = Paint.ANTI_ALIAS_FLAG
        circlePaint.color = ContextCompat.getColor(context, R.color.colorPrimary)
        circlePaint.style = Paint.Style.FILL
        circlePaint.strokeWidth = strokeWidth.toFloat()

        linePaint.flags = Paint.ANTI_ALIAS_FLAG
        linePaint.color = ContextCompat.getColor(context, R.color.colorPrimary)
        linePaint.strokeWidth = strokeWidth.toFloat()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = leftMargin
        outRect.right = rightMargin
        outRect.top = topMargin
        outRect.bottom = bottomMargin
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val layoutManager = parent.layoutManager ?: return
        repeat(parent.childCount) {
            val childView = parent.getChildAt(it)
            val leftDecorationWidth = layoutManager.getLeftDecorationWidth(childView)
            val rightDecorationWidth = layoutManager.getRightDecorationWidth(childView)
            val topDecorationHeight = layoutManager.getTopDecorationHeight(childView)
            val bottomDecorationHeight = layoutManager.getBottomDecorationHeight(childView)
            //获取当前item是列表第几个 childView
            val childLayoutPosition = parent.getChildLayoutPosition(childView)
            val startX = leftDecorationWidth / 2F
            //圆顶部部分竖线，起点Y
            val topStartX = childView.top - bottomDecorationHeight - topDecorationHeight
            //圆顶部部分竖线，终点Y
            val topEndX = childView.top + childView.height / 2 - radius
            //圆底部部分竖线，起点Y
            val bottomStartX = topEndX + radius * 2
            //圆底部部分竖线，终点Y
            val bottomEndX = childView.bottom

            //圆心Y
            val circleCenterY = childView.top + childView.height / 2F

            if (childLayoutPosition > curPosition) {
                circlePaint.color = ContextCompat.getColor(context, R.color.colorAccent)
                linePaint.color = ContextCompat.getColor(context, R.color.colorAccent)
                circlePaint.style = Paint.Style.STROKE
            } else if (childLayoutPosition < curPosition) {
                circlePaint.color = ContextCompat.getColor(context, R.color.colorPrimary)
                linePaint.color = ContextCompat.getColor(context, R.color.colorPrimary)
                circlePaint.style = Paint.Style.FILL
            } else {
                circlePaint.style = Paint.Style.STROKE
                //小圆
                c.drawCircle(startX, circleCenterY, radius / 3F, circlePaint)
            }
            c.drawCircle(startX, circleCenterY, radius.toFloat(), circlePaint)

            //绘制竖线
            if (childLayoutPosition == 0) {
                //第一个，只有上半部分
                c.drawLine(startX, bottomStartX.toFloat(), startX, bottomEndX.toFloat(), linePaint)
            } else if (curPosition == parent.adapter?.itemCount?.minus(1) ?: 0) {
                //最后一个
                c.drawLine(startX, topStartX.toFloat(), startX, topEndX.toFloat(), linePaint)
            } else {
                c.drawLine(startX, bottomStartX.toFloat(), startX, bottomEndX.toFloat(), linePaint)
                c.drawLine(startX, topStartX.toFloat(), startX, topEndX.toFloat(), linePaint)
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

    }
}