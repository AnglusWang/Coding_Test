package com.angluswang.guaguaka.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

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

    //----------
//    private Bitmap bitmap;

    private String mText;
    private int mTextSize;
    private Paint mBackPaint;
    // 用于记录刮奖信息文本的宽与高
    private Rect mTextBound;

    // 判断擦除的区域是否达到阈值
    // volatile 解决多线程对同一变量的可能潜在问题
    private volatile boolean isComplete = false;

    public interface onGuaguakaCompleteListener {
        void complete();
    }

    private onGuaguakaCompleteListener mListener;

    public void setonGuaguakaCompleteListener(
            onGuaguakaCompleteListener listener) {
        mListener = listener;
    }

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

//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t2);

        mText = "谢谢惠顾";
        mTextSize = 40;
        mTextBound = new Rect();
        mBackPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int heiht = getMeasuredHeight();
        //初始Bitmap
        mBitmap = Bitmap.createBitmap(width, heiht, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        setupOutpaint();    //设置绘制画笔
        setupBackpaint();   //设置获奖信息的画笔

        //设置画板颜色的三种方法
//        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
//        mCanvas.drawColor(Color.GRAY);
        mCanvas.drawColor(0xffc0c0c0);
    }

    /**
     * 设置获奖信息的画笔的一些属性
     */
    private void setupBackpaint() {
        mBackPaint.setColor(Color.BLACK);
        mBackPaint.setAntiAlias(true);
        mBackPaint.setDither(true);
        mBackPaint.setStyle(Paint.Style.FILL);

        mBackPaint.setTextSize(mTextSize);
        // 获得当前画笔绘制文本的宽和高
        mBackPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    /**
     * 设置绘制画笔的一些属性
     */
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
                new Thread(mRunnable).start();
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 开启一个线程，用于计算刮开区域的面积（避免在主线程中出现阻塞或卡顿现象）
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int w = getWidth();
            int h = getHeight();

            float wipeArea = 0;
            float totalArea = w * h;

            Bitmap bitmap = mBitmap;
            int[] mPixels = new int[w * h];

            //获取Bitmap上的所有像素信息
            bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int index = i + j * w;
                    if (mPixels[index] == 0) {
                        wipeArea++;
                    }
                }
            }

            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea * 100 / totalArea);
                Log.e(TAG, percent + "...");
                if (percent > 60) {
                    //清除掉图层区域
                    isComplete = true;
                    postInvalidate();

                }
            }
        }
    };

    //绘制图形
    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawText(mText,
                getWidth() / 2 - mTextBound.width() / 2,
                getHeight() / 2 + mTextBound.height() / 2,
                mBackPaint);   //绘制获奖信息

        if (isComplete) {
            if (mListener != null) {
                mListener.complete();
            }
        }

        if (!isComplete) {
            drawPath();
            canvas.drawBitmap(mBitmap, 0, 0, null); //绘制图层
        }
    }

    private void drawPath() {
        // PorterDuffXfermode 设置的是两个图层交集区域的显示方式
        // dst 是先化的图形， src 是后画的图形
        mOutPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawPath(mPath, mOutPaint);
    }
}
