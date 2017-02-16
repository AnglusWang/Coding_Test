package com.angluswang.canvasdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by anglus on 2017/2/15.
 */

public class AnglusView extends View {

    // 1.创建一个画笔
    private Paint mPaint = new Paint();

    // 2.初始化画笔
    private void initPaint() {
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
    }


    public AnglusView(Context context) {
        this(context, null);
    }

    public AnglusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnglusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawPoint(400, 200, mPaint);     //在坐标(400,200)位置绘制一个点
//        canvas.drawPoints(new float[]{          //绘制一组点，坐标位置由float数组指定
//                500, 500,
//                500, 600,
//                500, 700
//        }, mPaint);
//
//        canvas.drawLine(300, 300 + 30, 400, 600 + 30, mPaint);    // 在坐标(300,300)(500,600)之间绘制一条直线
//        canvas.drawLines(new float[]{               // 绘制一组线 每四数字(两个点的坐标)确定一条线
//                100, 200 + 30, 200, 200 + 30,
//                100, 300 + 30, 200, 300 + 30
//        }, mPaint);

//        canvas.drawRect(600, 100, 900, 300, mPaint);
//
//        RectF rectF = new RectF(800, 400, 200, 300);
//        canvas.drawRoundRect(rectF, 30, 30, mPaint);

//        RectF rectF = new RectF(100, 600, 300, 1080);
//        canvas.drawRoundRect(rectF, 30, 30, mPaint);

////         矩形
//        RectF rectF = new RectF(100, 100, 800, 400);
//
//        // 绘制背景矩形
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(rectF, mPaint);
//
//        // 绘制圆角矩形
//        mPaint.setColor(Color.BLUE);
//        canvas.drawRoundRect(rectF, 700, 400, mPaint);

//        RectF rectF = new RectF(100,100,800,400);
//        canvas.drawOval(rectF,mPaint);

//        canvas.drawCircle(500,500,400,mPaint);  // 绘制一个圆心坐标在(500,500)，半径为400 的圆。

        RectF rectF = new RectF(100, 100, 800, 400);

// 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF, mPaint);

// 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF, 0, 90, false, mPaint);

//-------------------------------------

        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF2 = new RectF(100, 600, 800, 900);
// 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF2, mPaint);

// 绘制圆弧
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF2, 0, 90, true, mPaint);
    }
}
