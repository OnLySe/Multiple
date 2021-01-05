package com.zzq.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zzq.common.utils.DensityUtil;

public class CircleProgressView extends View {

    private final String TAG = "CircleProgressBar";
    /***整数级变化 */
    private int progress = 0;
    private ValueAnimator progressAnimator;

    private final int SIZE_DP = 80;
    //最大进度
    private int maxProgress = 100;
    //默认宽高80dp长度
    private int size;

    //预留空间给弧度，防止弧被切割一部分，3到5dp之间
    private int reservedWidth = DensityUtil.INSTANCE.dp2px(ViewApp.Companion.getInstance(), 2.8F);
    //线粗，单位为dp
    private final int lineWidth = DensityUtil.INSTANCE.dp2px(ViewApp.Companion.getInstance(), 1);

    private Paint progressValuePaint;
    private Paint circlePaint;
    private Paint linePaint;
    //圆弧-进度，默认颜色画笔
    private Paint ringDefaultPaint;
    //圆弧-进度，已经达到-进度，颜色画笔
    private Paint ringPassPaint;

    private Paint.FontMetrics progressFontMetrics;

    //环 线宽，默认5dp

    // 圆环背景颜色
    @ColorInt
    private int ringDefaultColor = Color.parseColor("#F5F3F3");
    //进度背景色
    @ColorInt
    private int ringProgressColor = Color.RED;
    @ColorInt
    private int progressBackground = Color.WHITE;

    private RectF rectF;
    private RectF passRectF;

    //环形 宽度
    private int ringWidth = DensityUtil.INSTANCE.dp2px(ViewApp.Companion.getInstance(), 5);

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        progress = typedArray.getInt(R.styleable.CircleProgressView_progress, 0);
        maxProgress = typedArray.getInt(R.styleable.CircleProgressView_maxProgress, 100);
        ringDefaultColor = typedArray.getColor(R.styleable.CircleProgressView_defaultProgressColor,
                Color.parseColor("#F5F3F3"));
        ringProgressColor = typedArray.getColor(R.styleable.CircleProgressView_passProgressColor, Color.RED);
        progressBackground = typedArray.getColor(R.styleable.CircleProgressView_textBackground, Color.WHITE);
        /**
         * recycle() :官方的解释是：回收TypedArray，以便后面重用。在调用这个函数后，你就不能再使用这个TypedArray。
         * 在TypedArray后调用recycle主要是为了缓存。当recycle被调用后，这就说明这个对象从现在可以被重用了。
         *TypedArray 内部持有部分数组，它们缓存在Resources类中的静态字段中，这样就不用每次使用前都需要分配内存。
         */
        typedArray.recycle();
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
        linePaint.setStrokeWidth(lineWidth);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(progressBackground);
        //填充
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setStrokeWidth(ringWidth);

        ringDefaultPaint = new Paint();
        ringDefaultPaint.setAntiAlias(true);
        ringDefaultPaint.setColor(ringDefaultColor);
        ringDefaultPaint.setStyle(Paint.Style.STROKE);
        ringDefaultPaint.setStrokeWidth(ringWidth);

        ringPassPaint = new Paint();
        ringPassPaint.setAntiAlias(true);
        ringPassPaint.setColor(ringProgressColor);
        ringPassPaint.setStyle(Paint.Style.STROKE);
        ringPassPaint.setStrokeWidth(ringWidth);
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
        progressAnimator = ValueAnimator.ofInt(0, maxProgress + 1).setDuration(10_000);
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
        rectF = new RectF(0, 0, size, size);

        float newSize = size - reservedWidth;
        rectF = new RectF(reservedWidth, reservedWidth, newSize, newSize);
        passRectF = new RectF(reservedWidth, reservedWidth, newSize, newSize);
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

        float distance = (progressFontMetrics.bottom - progressFontMetrics.top) / 2 - progressFontMetrics.bottom;
        float baseline = centerY + distance;
        //备用，这种方式也可求得基线位置，从而使得文字居中！
        int baseline1 = (int) ((getMeasuredHeight() - progressFontMetrics.bottom + progressFontMetrics.top) / 2 - progressFontMetrics.top + 0.5);

        float newSize = size - reservedWidth;
        //画圆
        canvas.drawCircle(centerX, centerY, newSize / 2, circlePaint);
        canvas.drawText(progress + "", centerX, baseline, progressValuePaint);

        //在下方，如果设置getLeft()如果在设置marginStart的情况下会变化，但事实上，getLeft()是获取子View左上角距父View左侧的距离，在画线，不需要这个！
        //画直线，两个点确定一条直线
        //竖线
//        canvas.drawLine(centerX, paddingTop, centerX, size - paddingBottom, linePaint);
        //横线
//        canvas.drawLine(paddingStart, centerY, size - paddingEnd, centerY, linePaint);

        canvas.drawArc(rectF, 0, 360, true, ringDefaultPaint);
        if (progress <= 0) {
            return;
        }
        canvas.drawArc(passRectF, -90, ((float) progress / maxProgress) * 360, false, ringPassPaint);
    }
}
