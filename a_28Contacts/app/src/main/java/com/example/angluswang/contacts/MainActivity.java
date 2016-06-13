package com.example.angluswang.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

    private ListView lvContacts;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetNumber.getNumber(this);

        lvContacts = (ListView) findViewById(R.id.lv_contacts);
        myAdapter = new MyAdapter(GetNumber.sList, this);
        lvContacts.setAdapter(myAdapter);
    }
}
