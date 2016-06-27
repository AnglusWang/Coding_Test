package com.angluswang.arcmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Jeson on 2016/6/26.
 * 自定义控件
 */

public class ArcMenu extends ViewGroup implements View.OnClickListener {

    private static final int POS_LEFT_TOP = 0;
    private static final int POS_LEFT_BOTTOM = 1;
    private static final int POS_RIGHT_TOP = 2;
    private static final int POS_RIGHT_BOTTOM = 3;

    private Position mPosition = Position.RIGHT_BOTTOM;
    private int mRadius;

    private Status mMenuStatus = Status.CLOSE;
    private OnMenuItemClickListener mOnMenuItemClickListener;

    // 菜单的主按钮
    private View mCButton;

    // 菜单的状态
    private enum Status {
        CLOSE, OPEN
    }

    // 菜单的位置，枚举类
    private enum Position {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    // 点击子菜单项的回调接口
    public interface OnMenuItemClickListener {
        void onClick(View view, int position);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                100, getResources().getDisplayMetrics());
        //获取自定义属性的值
        TypedArray ta = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.ArcMenu, defStyleAttr, 0);

        int pos = ta.getInt(R.styleable.ArcMenu_position, POS_RIGHT_BOTTOM);
        switch (pos) {
            case POS_LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
            default:
                break;
        }
        mRadius = (int) ta.getDimension(R.styleable.ArcMenu_radius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
                        getResources().getDisplayMetrics()));

        System.out.println("mPosition: " + mPosition + "; mRadius: " + mRadius);

        ta.recycle();
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount(); //获取child的数目
        for (int i = 0; i < count; i++) {
            //测量child
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 布局
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (changed) {
            layoutCButton();

            //定位item
            int count = getChildCount();
            for (int i = 0; i < count - 1; i++) {
                View child = getChildAt(i + 1); //获取子item

                child.setVisibility(GONE); // 默认不展开子菜单

                //默认在左上时的item的(x, y)坐标
                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
                int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();

                // 根据主按钮的位置来修改item的左右位置
                // 在左下和右下时，修正ct
                if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
                    ct = getMeasuredHeight() - cHeight - ct;
                }
                // 在右上和右下时，修正cl
                if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {
                    cl = getMeasuredWidth() - cWidth - cl;
                }

                child.layout(cl, ct, cl + cWidth, ct + cHeight);
            }
        }
    }

    /**
     * 定位主菜单按钮
     */
    private void layoutCButton() {
        mCButton = getChildAt(0);
        mCButton.setOnClickListener(this);

        int l = 0;  //左边的距离
        int t = 0;  //顶部的距离

        int width = mCButton.getMeasuredWidth();    //主按钮的宽度与高度
        int height = mCButton.getMeasuredHeight();

        switch (mPosition) {
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
            default:
                break;
        }

        mCButton.layout(l, t, l + width, t + height);
    }

    @Override
    public void onClick(View v) {

        //为主按钮添加旋转动画
        rotateCButton(v, 0f, 360f, 300);

        //展开子菜单
        ToggleMenu(300);
    }

    /**
     * 展开子菜单
     *
     * @param duration
     */
    private void ToggleMenu(int duration) {

        //为MenuItem 添加平移动画和旋转动画
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {

            final View child = getChildAt(i + 1);
            child.setVisibility(VISIBLE);

            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            // 左加右减， 上加下减
            int xflag = 1;
            int yflag = 1;

            if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
                xflag = -1;
            }

            if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) {
                yflag = -1;
            }

            //添加平移动画
            AnimationSet animSet = new AnimationSet(true);
            TranslateAnimation tranAnim = null;

            // to open
            if (mMenuStatus == Status.CLOSE) {
                tranAnim = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
                // 设置子item 是否可以获得焦点及可否触摸
                child.setClickable(true);
                child.setFocusable(true);
            } else {
                tranAnim = new TranslateAnimation(0, xflag * cl, 0, yflag * ct);
                child.setClickable(false);
                child.setFocusable(false);
            }
            tranAnim.setDuration(duration);
            tranAnim.setFillAfter(true);

            // 设置动画监听，当主按钮状态为 CLOSE时 设置子item的可见属性为GONE
            tranAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    if (mMenuStatus == Status.CLOSE) {
                        child.setVisibility(GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            //添加旋转动画
            RotateAnimation rotAnim = new RotateAnimation(0, 720,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotAnim.setDuration(duration);
            rotAnim.setFillAfter(true);

            //添加动画到动画集 并开启动画（注： 先添加旋转动画 原因：setFillAfter都为true）
            animSet.addAnimation(rotAnim);
            animSet.addAnimation(tranAnim);

            child.startAnimation(animSet);
        }

        //切换菜单状态
        changeStatus();

    }

    /**
     * 改变子菜单的展开状态
     */
    private void changeStatus() {
        mMenuStatus = (mMenuStatus == Status.CLOSE) ? Status.OPEN : Status.CLOSE;
    }

    /**
     * 主按钮旋转动画
     */
    private void rotateCButton(View v, float start, float end, int duration) {
        RotateAnimation anim = new RotateAnimation(start, end,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        v.startAnimation(anim);
    }
}
