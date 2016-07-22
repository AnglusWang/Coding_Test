package com.angluswang.festival_sms.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angluswang.festival_sms.R;
import com.angluswang.festival_sms.bean.SendedMsg;
import com.angluswang.festival_sms.db.SmsProvider;
import com.angluswang.festival_sms.view.FlowLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Jeson on 2016/7/20.
 * 发送记录 Fragment
 */

public class SmsHistoryFragment extends ListFragment {

    private static final int LOADER_ID = 1;

    private LayoutInflater mInflater;
    private CursorAdapter mCursorAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInflater = LayoutInflater.from(getActivity());

        initLoader();
        setupListAdapter();
    }

    private void setupListAdapter() {
        mCursorAdapter = new CursorAdapter(getActivity(), null, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View view = mInflater.inflate(R.layout.item_sended_msg, parent, false);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

                TextView msg = (TextView) view.findViewById(R.id.id_tv_msg);
                FlowLayout fl = (FlowLayout) view.findViewById(R.id.id_fl_contacts);
                TextView fes = (TextView) view.findViewById(R.id.id_tv_festival);
                TextView date = (TextView) view.findViewById(R.id.id_tv_date);

                msg.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUME_MSG)));
                fes.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUME_FESTIVAL_NAME)));
                long dateVal = cursor.getLong(cursor.getColumnIndex(SendedMsg.COLUME_DATE));
                date.setText(ParseDate(dateVal));

                String names = cursor.getString(cursor.getColumnIndex(SendedMsg.COLUME_NAMES));
                if (TextUtils.isEmpty(names)) {
                    return;
                }
                fl.removeAllViews();
                for (String name : names.split(":")) {
                    addTag(name, fl); //
                }
            }
        };
        setListAdapter(mCursorAdapter);
    }

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd, HH-mm-ss");
    private String ParseDate(long dateVal) {
        return df.format(dateVal);
    }

    private void addTag(String name, FlowLayout fl) {
        TextView view = (TextView) mInflater.inflate(R.layout.tag, fl, false);
        view.setText(name);
        fl.addView(view);
    }

    private void initLoader() {
        getLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader loader = new CursorLoader(
                        getActivity(), SmsProvider.URI_SMS_ALL, null, null, null, null);
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if (loader.getId() == LOADER_ID) {
                    mCursorAdapter.swapCursor(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mCursorAdapter.swapCursor(null);
            }
        });
    }
}
