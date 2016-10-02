package com.anglus.recyclertest;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class RecyclerTest extends Activity {

    private RecyclerView mRcList;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Spinner mSpinner;

    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        mRcList = findViewById(R.id.ic_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRcList.setLayoutManager(mLayoutManager);
        mRcList.setHasFixedSize(true);

        // 设置显示动画
        mRcList.setItemAnimator(new DefaultItemAnimator());

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setOnItemClickListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view,
                                       int position,
                                       long id) {
                if (position == 0) {
                    mRcList.setLayoutManager(
                            // 设置为线性布局
                            new LinearLayoutManager(RecyclerTest.this));
                } else if (position == 1) {
                    mRcList.setLayoutManager(
                            // 设置为表格布局
                            new GridLayoutManager(RecyclerTest.this, 3));
                } else if (position == 2) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 增加测试数据
        mData.add("Recycler");
        mData.add("Recycler");
        mData.add("Recycler");
        mAdapter = new RecyclerAdapter(mData);
        mRcList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickLister() {
            @Override
            public void onItemClick(final View view, int position) {
                // 设置点击动画
                view.animate()
                        .translationZ(15f).setDuration(200)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                view.animate().translationZ(1f).setDuration(500).start();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).start();
            }
        });
    }

    public void addRecycler(View view) {
        mData.add("Recycler");
        int position = mData.size();
        if (position > 0) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void delRecycler(View view) {
        int position = mData.size();
        if (position > 0) {
            mData.remove(position - 1);
            mAdapter.notifyDataSetChanged();
        }
    }
}
