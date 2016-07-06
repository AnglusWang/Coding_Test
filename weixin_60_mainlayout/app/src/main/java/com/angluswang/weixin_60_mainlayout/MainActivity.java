package com.angluswang.weixin_60_mainlayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity
        implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager mViewpager;
    private List<Fragment> mTabs = new ArrayList<>();
    private String[] mTitles = new String[]
            {"First Fragment !", "Second Fragment !", "Third Fragment !",
                    "Fourth Fragment !"};
    private FragmentPagerAdapter mAdapter;

    private List<ChangeColorIconWithText> mTabIndicator = new ArrayList<>();
    private ChangeColorIconWithText indicatorOne;
    private ChangeColorIconWithText indicatorTwo;
    private ChangeColorIconWithText indicatorThree;
    private ChangeColorIconWithText indicatorFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setOverflowButtonAlways();
        getActionBar().setDisplayShowHomeEnabled(false); // 隐藏微信左边的图标

        initView();
        initData();
        mViewpager.setAdapter(mAdapter);
        mViewpager.setOnPageChangeListener(this);

//        // 改变 ActionBar 标题的位置
//        getActionBar().setCustomView(R.layout.自定义控件);
//        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }

    private void initData() {

        //初始化数据
        for (String title : mTitles) {
            TabFragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.TITLE, title);
            tabFragment.setArguments(bundle);
            mTabs.add(tabFragment);
        }

        //初始化设配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };
    }

    private void initView() {
        mViewpager = (ViewPager) findViewById(R.id.id_viewpager);

        indicatorOne = (ChangeColorIconWithText) findViewById(R.id.id_indicator_one);
        mTabIndicator.add(indicatorOne);
        indicatorTwo = (ChangeColorIconWithText) findViewById(R.id.id_indicator_two);
        mTabIndicator.add(indicatorTwo);
        indicatorThree = (ChangeColorIconWithText) findViewById(R.id.id_indicator_three);
        mTabIndicator.add(indicatorThree);
        indicatorFour = (ChangeColorIconWithText) findViewById(R.id.id_indicator_four);
        mTabIndicator.add(indicatorFour);

        indicatorOne.setOnClickListener(this);
        indicatorTwo.setOnClickListener(this);
        indicatorThree.setOnClickListener(this);
        indicatorFour.setOnClickListener(this);

        indicatorOne.setIconAlpha(1.0f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 设置 把actionbar的（默认...）显示出来
     */
    private void setOverflowButtonAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKey = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(config, false); // 换成true可看到效果 ， 4.1 默认为false
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置menu显示icon
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onClick(View v) {
        clickTabs(v);
    }

    /**
     *  底部 Tab 点击事件处理
     */
    private void clickTabs(View v) {
        // 重置其他TabIndicator的颜色
        resetTabOther();
        switch (v.getId()) {
            case R.id.id_indicator_one:
                mTabIndicator.get(0).setIconAlpha(1.0f);
                mViewpager.setCurrentItem(0, false);
                break;
            case R.id.id_indicator_two:
                mTabIndicator.get(1).setIconAlpha(1.0f);
                mViewpager.setCurrentItem(1, false);
                break;
            case R.id.id_indicator_three:
                mTabIndicator.get(2).setIconAlpha(1.0f);
                mViewpager.setCurrentItem(2, false);
                break;
            case R.id.id_indicator_four:
                mTabIndicator.get(3).setIconAlpha(1.0f);
                mViewpager.setCurrentItem(3, false);
                break;
            default:
                break;
        }
    }

    /**
     * 重置其他TabIndicator的颜色
     */
    private void resetTabOther() {
        for (int i = 0, n = mTabIndicator.size(); i < n; i++) {
            mTabIndicator.get(i).setIconAlpha(0.0f);
        }
    }

    @Override
    public void onPageScrolled(int position,
                               float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ChangeColorIconWithText left = mTabIndicator.get(position);
            ChangeColorIconWithText right = mTabIndicator.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
