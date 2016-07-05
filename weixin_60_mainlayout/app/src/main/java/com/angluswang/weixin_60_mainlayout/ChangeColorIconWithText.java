package com.angluswang.weixin_60_mainlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Jeson on 2016/7/5.
 * 自定义View （Tab 栏的图标 随页面切换而变化）
 * <p>
 * 步骤：
 * 1.新建attr.xml文件，自己属性并声明
 * 2.在布局文件中使用自定义属性
 * 3.在构造函数中获取自定义属性
 * 4.onMeasure() 测量
 * 5.onDraw() 绘图
 */

public class ChangeColorIconWithText extends View {

    private int mColor = 0xFF45C01A;
    private Bitmap mIconBitmap;
    private String mText = "微信";
    private int mTextSize = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;

    private float mAlpha;

    private Rect mIconRect;
    private Rect mTextBound;
    private Paint mTextPaint;

    public ChangeColorIconWithText(Context context) {
        this(context, null);
    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 在构造方法中获取自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ChangeColorIconWithText);
        for (int i = 0, n = a.getIndexCount(); i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ChangeColorIconWithText_color:
                    mColor = a.getColor(attr, 0xFF45C01A);
                    break;
                case R.styleable.ChangeColorIconWithText_icon:
                    BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.ChangeColorIconWithText_text:
                    mText = a.getString(attr);
                    break;
                case R.styleable.ChangeColorIconWithText_text_size:
                    mTextSize = (int) a.getDimension(attr, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
        }

        a.recycle();

        //初始化文本绘制相关
        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0Xff555555);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
                - getPaddingRight(), getMeasuredHeight() - getPaddingTop()
                - getPaddingBottom() - mTextBound.height());
        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = getMeasuredHeight() / 2 - (mTextBound.height() + iconWidth)
                / 2;
        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

        int alpha = (int) Math.ceil(255 * mAlpha); // 向上取1

        // 内存去准备mBitmap , setAlpha , 纯色 ，xfermode ， 图标
        setupTargetBitmap(alpha);

        canvas.drawBitmap(mBitmap, 0, 0, null);

        super.onDraw(canvas);
    }

    /**
     * 在内存中绘制可变色的Bitmap
     * @param alpha
     */
    private void setupTargetBitmap(int alpha) {

        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setAlpha(alpha);
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mCanvas.drawRect(mIconRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }
}
