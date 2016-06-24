package com.angluswang.zhy_weixin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mViewPager.setAdapter(mAdapter);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
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
    }
}
