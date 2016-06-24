package com.angluswang.guaguaka.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jeson on 2016/6/24.
 * 刮刮卡
 */

public class Guaguaka extends View {

    private Paint mOutPaint;
    private Path mPath;
    private Canvas mCanvas;
    private Bitmap mBitmap;

    private int mLastX;
    private int mLastY;

    public Guaguaka(Context context) {
        this(context, null);
    }

    public Guaguaka(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Guaguaka(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

        mOutPaint = new Paint();
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int heiht = getMeasuredHeight();
        //初始Bitmap
        mBitmap = Bitmap.createBitmap(width, heiht, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        setupOutpaint();


    }

    //设置绘制画笔的一些属性
    private void setupOutpaint() {
        mOutPaint.setColor(Color.RED);
        mOutPaint.setAntiAlias(true);
        mOutPaint.setDither(true);
        mOutPaint.setStrokeJoin(Paint.Join.ROUND);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setStrokeWidth(20);
    }

    //事件处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(mLastX - x);
                int dy = Math.abs(mLastY - y);
                if (dx > 3 || dy > 3) {
                    mPath.lineTo(x, y);
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    //绘制图形
    @Override
    protected void onDraw(Canvas canvas) {
        drawPath();
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    private void drawPath() {
        mCanvas.drawPath(mPath, mOutPaint);
    }
}
