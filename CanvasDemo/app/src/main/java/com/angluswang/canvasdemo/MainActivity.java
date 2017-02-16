package com.angluswang.canvasdemo;

import android.app.Activity;
import android.os.Bundle;

import com.anlguswang.view.PieData;
import com.anlguswang.view.PieView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<PieData> datas = new ArrayList();
        PieData pieData = new PieData("name1", 15.0f);
        datas.add(pieData);
        pieData = new PieData("name2", 25.0f);
        datas.add(pieData);
        pieData = new PieData("name3", 40.0f);
        datas.add(pieData);
        pieData = new PieData("name4", 40.0f);
        datas.add(pieData);
        pieData = new PieData("name5", 40.0f);
        datas.add(pieData);
        pieData = new PieData("name6", 40.0f);
        datas.add(pieData);

        PieView pv = (PieView) findViewById(R.id.id_pieView);
        pv.setData(datas);
    }
}
