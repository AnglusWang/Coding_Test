package com.utils.angluswang.roundimageview;

import android.content.Context;
import android.content.res.TypedArray;
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
     * 从attr.xml 文件中获取属性值，并给RoundImageView设置
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
}
