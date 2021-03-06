package com.utils.angluswang.roundimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Jeson on 2016/6/13.
 * 自定义圆形控件
 */

public class RoundImageView extends ImageView {

    private int mBorderThickness = 0;
    private Context mContext;

    private int defaultColor = 0xFFFFFFFF;
    private int mBorderOutsideColor = 0; //外圆边框颜色
    private int mBorderInsideColor = 0; //内圆边框颜色

    private int defaultWidth = 0;   //RoundImageView控件的默认宽度
    private int defaultHeight = 0;  //RoundImageView控件的默认长度

    public RoundImageView(Context context) {
        super(context);
        mContext = context;
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        //设置RoundImageView的属性值，比如：颜色，宽度等
        setRoundImageViewAttributes(attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setRoundImageViewAttributes(attrs);
    }

    /**
     * 从attrs.xml 文件中获取属性值，并给RoundImageView设置
     */
    public void setRoundImageViewAttributes(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs,
                R.styleable.round_image_view);
        mBorderThickness = a.getDimensionPixelSize(
                R.styleable.round_image_view_border_width, 0);
        mBorderOutsideColor = a.getColor(
                R.styleable.round_image_view_border_outcolor, defaultColor);
        mBorderInsideColor = a.getColor(
                R.styleable.round_image_view_border_incolor, defaultColor);
        a.recycle();
    }

    /**
     * 这是一个继承的父类的onDraw方法，在这里画出想要的图形或者形状即可
     *
     * @param canvas 绘图关键类（画布）
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        this.measure(0, 0);
        if (drawable.getClass() == NinePatchDrawable.class) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        if (defaultWidth == 0) {
            defaultWidth = getWidth();
        }

        if (defaultHeight == 0) {
            defaultHeight = getHeight();
        }
        int radius = 0;
        // 如果内圆与外圆设置的颜色值不为空且不是默认颜色，就定义画两个圆框，分别为内圆与外圆边框
        if (mBorderInsideColor != defaultColor
                && mBorderOutsideColor != defaultColor) {
            radius = (defaultWidth < defaultHeight ? defaultWidth : defaultHeight) / 2
                    - 2 * mBorderThickness;
            //画内圆
            drawCircleBorder(canvas, radius + mBorderThickness / 2,
                    mBorderInsideColor);
            //画外圆
            drawCircleBorder(canvas, radius + mBorderThickness + mBorderThickness / 2,
                    mBorderOutsideColor);
        } else if (mBorderInsideColor != defaultColor
                && mBorderOutsideColor == defaultColor) {
            // 如果内圆边框不为空且颜色不是默认值，就画一个内圆的边框
            radius = (defaultWidth < defaultHeight ? defaultWidth : defaultHeight) / 2
                    - mBorderThickness;
            drawCircleBorder(canvas, radius + mBorderThickness / 2,
                    mBorderInsideColor);
        } else if (mBorderInsideColor == defaultColor
                && mBorderOutsideColor != defaultColor) {
            // 如果外圆边框不为空且颜色不是默认值，就画一个外圆的边框
            radius = (defaultWidth < defaultHeight ? defaultWidth : defaultHeight) / 2
                    - mBorderThickness;
            drawCircleBorder(canvas, radius + mBorderThickness / 2,
                    mBorderOutsideColor);
        } else {
            // 没有边框的情况
            radius = (defaultWidth < defaultHeight ? defaultWidth : defaultHeight) / 2;
        }
        Bitmap roundBitmap = getCroppedRoundBitmap(bitmap, radius);
        canvas.drawBitmap(roundBitmap, defaultWidth / 2 - radius, defaultHeight / 2 - radius, null);
    }

    public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
        Bitmap scaledSrcBmp;
        int diameter = radius * 2;

        //为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        int squareWidth = 0, squareHeight = 0;
        int x = 0, y = 0;
        Bitmap squareBitmap;
        if (bmpHeight > bmpWidth) {
            squareWidth = squareHeight = bmpWidth;
            x = 0;
            y = (bmpHeight - bmpWidth) / 2;
            // 截取正方形图片
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight);
        } else if (bmpHeight < bmpWidth) {
            squareWidth = squareHeight = bmpHeight;
            x = (bmpWidth - bmpHeight) / 2;
            y = 0;
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight);
        } else {
            squareBitmap = bmp;
        }

        if (squareBitmap.getWidth() != diameter || squareBitmap.getHeight() != diameter) {
            scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);
        } else {
            scaledSrcBmp = squareBitmap;
        }

        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
                scaledSrcBmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
                scaledSrcBmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(scaledSrcBmp.getWidth() / 2, scaledSrcBmp.getHeight() / 2,
                scaledSrcBmp.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);

        bmp = null;
        squareBitmap = null;
        scaledSrcBmp = null;
        return output;
    }

    /**
     * 画边缘的圆，即内圆或者外圆
     */
    private void drawCircleBorder(Canvas canvas, int radius, int color) {
        Paint paint = new Paint();
        // 去锯齿
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);

        //设置paint的style 为STROCK ： 空心
        paint.setStyle(Paint.Style.STROKE);
        //设置paint的外框宽度
        paint.setStrokeWidth(mBorderThickness);
        canvas.drawCircle(defaultWidth/2, defaultHeight/2, radius, paint);
    }

}
