package com.angluswang.arcmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView listview;
    private ArcMenu arcMenu;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        initEvent();

        listview.setAdapter(new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, mDatas));
    }

    private void initEvent() {

        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                System.out.println("滑动了。。。");
                if (arcMenu.isOpen()) {
                    arcMenu.ToggleMenu(500);
                }
            }
        });

        arcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener()
        {
            @Override
            public void onClick(View view, int pos)
            {
                Toast.makeText(MainActivity.this, pos+":"+view.getTag(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'Z'; i++) {
            mDatas.add((char) i + "");
        }
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.id_listview);
        arcMenu = (ArcMenu) findViewById(R.id.id_menu_right_bottom);
    }
}
