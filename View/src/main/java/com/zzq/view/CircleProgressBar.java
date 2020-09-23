package com.zzq.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zzq.util.DensityUtil;

public class CircleProgressBar extends View {

    private final String TAG = "CircleProgressBar";
    /***整数级变化 */
    private int progress = 0;
    private ValueAnimator progressAnimator;

    private final int SIZE_DP = 80;
    //默认宽高80dp长度
    private int size;

    private Paint progressValuePaint;
    private Paint.FontMetrics progressFontMetrics;

    private Paint linePaint;
    //线粗，单位为dp
    private final float lineWidth = 0.33F;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        size = DensityUtil.INSTANCE.dp2px(context, SIZE_DP);
        initAnimator();

        progressValuePaint = new Paint();
        progressValuePaint.setAntiAlias(true);
        progressValuePaint.setColor(Color.BLACK);
        progressValuePaint.setTextSize(56);
        progressValuePaint.setTextAlign(Paint.Align.CENTER);
        //在paint设置完textSize后再获取
        progressFontMetrics = progressValuePaint.getFontMetrics();

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(DensityUtil.INSTANCE.dp2px(context, lineWidth));
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            stopProgressAnimator();
        } else {
            startProgressAnimator();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startProgressAnimator();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopProgressAnimator();
    }

    private void initAnimator() {
        progressAnimator = ValueAnimator.ofInt(0, 100).setDuration(10_000);
        progressAnimator.setRepeatCount(ValueAnimator.INFINITE);
        progressAnimator.setRepeatMode(ValueAnimator.RESTART);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    private void startProgressAnimator() {
        if (getVisibility() != VISIBLE) {
            return;
        }
        if (!progressAnimator.isRunning()) {
            progressAnimator.start();
        }
    }

    private void stopProgressAnimator() {
        if (progressAnimator.isRunning()) {
            progressAnimator.cancel();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //widthMeasureSpec和heightMeasureSpec是包含padding大小的
        setMeasuredDimension(
                Math.max(getSuggestedMinimumWidth(),
                        resolveSize(size, widthMeasureSpec)),
                Math.max(getSuggestedMinimumHeight(),
                        resolveSize(size, heightMeasureSpec))
        );
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //取小的那个
        size = Math.min(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw size: " + size + " " + getLeft() + " " + getTop() + " " + getRight() + " " + getBottom());

        //在xml中设置paddingStart或者paddingLeft，在这里获取到的paddingStart和paddingLeft都是一样的
        int paddingStart = getPaddingStart();
        int paddingLeft = getPaddingLeft();
        int paddingEnd = getPaddingEnd();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        Log.e(TAG, "onDraw padding " + paddingStart + " " + paddingTop + " " + paddingEnd + " " + paddingBottom);
        //处理padding时，左边加，右边减
        int centerX = size / 2 + paddingStart - paddingEnd;
        int centerY = size / 2 + paddingTop - paddingBottom;

        Log.e(TAG, "onDraw center " + centerX + " " + centerY);

        Paint.FontMetrics fontMetrics = progressValuePaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = centerY + distance;
        //备用，这种方式也可求得基线位置，从而使得文字居中！
        int baseline1 = (int) ((getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top + 0.5);

//      canvas.drawText(progress + "", centerX, centerY, progressValuePaint);
        canvas.drawText(progress + "", centerX, baseline, progressValuePaint);

        //在下方，如果设置getLeft()如果在设置marginStart的情况下会变化，但事实上，getLeft()是获取子View左上角距父View左侧的距离，在画线，不需要这个！
        //画直线，两个点确定一条直线
        //竖线
        canvas.drawLine(centerX, paddingTop, centerX, size - paddingBottom, linePaint);
        //横线
        canvas.drawLine(paddingStart, centerY, size - paddingEnd, centerY, linePaint);
    }
}
