package com.angluswang.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.angluswang.progressbar.R;

/**
 * Created by anglus on 2017/2/13.
 */

public class HorizontalProgressBar extends ProgressBar {

    public static final int DEFAULT_TEXT_SIZE = 10;// sp
    public static final int DEFAULT_TEXT_COLOR = 0xFFF300D1;
    public static final int DEFAULT_COLOR_UNREACH = 0xFFD3D6DA;
    public static final int DEFAULT_UNREACH_HEIGHT = 2;// dp
    public static final int DEFAULT_COLOR_REACH = DEFAULT_TEXT_COLOR;
    public static final int DEFAULT_REACH_HEIGHT = 2;// dp
    public static final int DEFAULT_TEXT_OFFSET = 10;// dp

    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mColorUnreach = DEFAULT_COLOR_UNREACH;
    private int mUnreachHeight = dp2px(DEFAULT_UNREACH_HEIGHT);
    private int mColorReach = DEFAULT_COLOR_REACH;
    private int mReachHeight = dp2px(DEFAULT_REACH_HEIGHT);
    private int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    private Paint mPaint = new Paint();
    private int mRealWidth;

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyleAttrs(attrs);
        mPaint.setTextSize(mTextSize);
    }

    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private void obtainStyleAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.HorizontalProgressBar);
        mTextSize = (int) ta.getDimension(
                R.styleable.HorizontalProgressBar_progress_text_size, mTextSize);
        mTextColor = ta.getColor(
                R.styleable.HorizontalProgressBar_progress_text_color, mTextColor);
        mTextOffset = (int) ta.getDimension(
                R.styleable.HorizontalProgressBar_progress_text_offset, mTextOffset);
        mColorReach = ta.getColor(
                R.styleable.HorizontalProgressBar_progress_reach_color, mColorReach);
        mReachHeight = (int) ta.getDimension(
                R.styleable.HorizontalProgressBar_progress_reach_height, mReachHeight);
        mColorUnreach = ta.getColor(
                R.styleable.HorizontalProgressBar_progress_unreach_color, mColorUnreach);
        mUnreachHeight = (int) ta.getDimension(
                R.styleable.HorizontalProgressBar_progress_unreach_height, mUnreachHeight);
        ta.recycle();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthVal = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(widthVal, height);

        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {// 精确值
            result = size;
        } else {
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            result = getPaddingTop() + getPaddingBottom()
                    + Math.max(Math.max(mReachHeight, mUnreachHeight), Math.abs(mTextSize));

            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(size, result);
            }
        }

        return result;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        boolean noNeedUnreach = false;

        String text = getProgress() + "%";
        int textWidth = (int) mPaint.measureText(text);

        float radio = getProgress() * 1.0f / getMax();
        float progressX = radio * mRealWidth;
        if (progressX + textWidth > mRealWidth) {
            progressX = mRealWidth - textWidth;
            noNeedUnreach = true;
        }
        float endX = progressX - mTextOffset / 2;
        if (endX > 0) {
            mPaint.setColor(mTextColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }

        // draw text
        mPaint.setColor(mTextColor);
        int y = (int) (-(mPaint.descent() + mPaint.ascent()) / 2);
        canvas.drawText(text, progressX, y, mPaint);

        // draw Unreach bar
        if (!noNeedUnreach) {
            float start = progressX + textWidth + mTextOffset / 2;
            mPaint.setColor(mColorUnreach);
            mPaint.setStrokeWidth(mUnreachHeight);
            canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
        }

        canvas.restore();
    }

    private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    private int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());
    }
}
