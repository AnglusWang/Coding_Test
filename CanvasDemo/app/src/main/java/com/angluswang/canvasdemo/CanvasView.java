package com.angluswang.canvasdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by anglus on 2017/2/16.
 */

public class CanvasView extends View {

    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint();

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);

        RectF rect = new RectF(-400, -400, 400, 400);   // 矩形区域
        mPaint.setStyle(Paint.Style.STROKE);

        for (int i = 0; i <= 20; i++) {
            canvas.scale(0.9f, 0.9f);
            canvas.drawRect(rect, mPaint);
        }
        canvas.restore();

        canvas.save();
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);

        canvas.drawCircle(0,0,530,mPaint);          // 绘制两个圆形
        canvas.drawCircle(0,0,510,mPaint);

        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
            canvas.drawLine(0,510,0,530,mPaint);
            canvas.rotate(10);
        }
        canvas.restore();
    }
}
