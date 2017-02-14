package com.angluswang.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.angluswang.progressbar.R;

/**
 * Created by anglus on 2017/2/14.
 */

public class RoundProgressBar extends HorizontalProgressBar {

    private int mRadius = dp2px(30);// 默认半径
    private RectF mRectF;
    private int mMaxPaintWidth;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mReachHeight = (int) (mUnreachHeight * 2.5f);// 为了美观（测试用）
        TypedArray ta = getContext().obtainStyledAttributes(
                attrs, R.styleable.RoundProgressBar);
        mRadius = (int) ta.getDimension(R.styleable.RoundProgressBar_radius, mRadius);
        ta.recycle();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mRectF = new RectF(0, 0, mRadius * 2, mRadius * 2);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMaxPaintWidth = Math.max(mReachHeight, mUnreachHeight);
        // 默认四个 padding 一致
        int expect = mRadius * 2 + mMaxPaintWidth + getPaddingLeft() + getPaddingRight();

        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);

        int realWidth = Math.min(width, height);
        mRadius = (realWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth) / 2;
        setMeasuredDimension(realWidth, realWidth);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2,
                getPaddingTop() + mMaxPaintWidth / 2);
        mPaint.setStyle(Paint.Style.STROKE);
        // draw unreach bar
        mPaint.setColor(mColorUnreach);
        mPaint.setStrokeWidth(mUnreachHeight);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        // draw reach bar
        mPaint.setColor(mColorReach);
        mPaint.setStrokeWidth(mReachHeight);
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(mRectF, 0, sweepAngle, false, mPaint);
        // draw text
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight / 2, mPaint);

        canvas.restore();
    }
}
