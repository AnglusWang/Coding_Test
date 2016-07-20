package com.angluswang.festival_sms.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Jeson on 2016/7/20.
 * 发送记录 Fragment
 */

public class SmsHistoryFragment extends Fragment {

    private LayoutInflater mInflater;
    private CursorAdapter mCursorAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInflater = LayoutInflater.from(getContext());

        initLoader();
        setupListAdapter();
    }

    private void setupListAdapter() {
    }

    private void initLoader() {
        
    }
}
