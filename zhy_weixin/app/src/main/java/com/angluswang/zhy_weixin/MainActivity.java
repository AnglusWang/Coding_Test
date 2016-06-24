package com.angluswang.zhy_weixin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mDatas;

    private TextView top2TvChat;
    private TextView top2TvFound;
    private TextView top2TvContact;

    private LinearLayout chatLinearLayout;
    private BadgeView mBadgeView;

    private ImageView top2ImgTabline;
    private int mScreenOneThird;

    private int mCurrentPageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabline();

        initView();
    }

    private void initTabline() {
        top2ImgTabline = (ImageView) findViewById(R.id.top2_img_tabline);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        mScreenOneThird = metrics.widthPixels / 3;

        ViewGroup.LayoutParams lp = top2ImgTabline.getLayoutParams();
        lp.width = mScreenOneThird;
        top2ImgTabline.setLayoutParams(lp);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        top2TvChat = (TextView) findViewById(R.id.top2_tv_chat);
        top2TvFound = (TextView) findViewById(R.id.top2_tv_found);
        top2TvContact = (TextView) findViewById(R.id.top2_tv_contact);
        chatLinearLayout = (LinearLayout) findViewById(R.id.id_ll_chat);

        mDatas = new ArrayList<>();
        ChatMainTabFragment tab1 = new ChatMainTabFragment();
        FoundTabFragment tab2 = new FoundTabFragment();
        ContactTabFragment tab3 = new ContactTabFragment();
        mDatas.add(tab1);
        mDatas.add(tab2);
        mDatas.add(tab3);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }
        };
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)
                        top2ImgTabline.getLayoutParams();

                // 初始时的 leftMargin 数值
                int oldScreen = mCurrentPageIndex * mScreenOneThird;
                // 往左移动时 leftMargin 的变化
                int toLeftOffset = (int) ((positionOffset - 1) * mScreenOneThird);
                // 往右移动时 leftMargin 的变
                int toRightOffset = (int) (positionOffset * mScreenOneThird);
                if (mCurrentPageIndex == 0 && position == 0) {
                    // 0 -> 1
                    lp.leftMargin = oldScreen + toRightOffset;
                } else if (mCurrentPageIndex == 1 && position == 0) {
                    // 1 -> 0
                    lp.leftMargin = oldScreen + toLeftOffset;
                } else if (mCurrentPageIndex == 1 && position == 1) {
                    // 1 -> 2
                    lp.leftMargin = oldScreen + toRightOffset;
                } else if (mCurrentPageIndex == 2 && position == 1) {
                    // 2 -> 1
                    lp.leftMargin = oldScreen + toLeftOffset;
                }

                top2ImgTabline.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        if (mBadgeView != null) {
                            chatLinearLayout.removeView(mBadgeView);
                        }
                        mBadgeView = new BadgeView(MainActivity.this);
                        mBadgeView.setBadgeCount(8);
                        chatLinearLayout.addView(mBadgeView);

                        top2TvChat.setTextColor(0xFF008000);
                        break;
                    case 1:
                        top2TvFound.setTextColor(0xFF008000);
                        break;
                    case 2:
                        top2TvContact.setTextColor(0xFF008000);
                        break;
                    default:
                        break;
                }

                mCurrentPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 用于窗口切换时，重置top2文本字体颜色为色
     */
    private void resetTextView() {
        top2TvChat.setTextColor(Color.BLACK);
        top2TvFound.setTextColor(Color.BLACK);
        top2TvContact.setTextColor(Color.BLACK);
    }
}
