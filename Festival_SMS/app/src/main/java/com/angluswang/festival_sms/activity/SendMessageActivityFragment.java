package com.angluswang.festival_sms.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angluswang.festival_sms.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SendMessageActivityFragment extends Fragment {

    public SendMessageActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_send_message, container, false);
    }
}
