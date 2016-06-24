package com.angluswang.zhy_weixin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mViewPager.setAdapter(mAdapter);
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

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

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
