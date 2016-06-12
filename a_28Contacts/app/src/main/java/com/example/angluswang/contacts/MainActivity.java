package com.example.angluswang.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    private ListView lvContacts;
    private Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetNumber.getNumber(this);
    }
}
