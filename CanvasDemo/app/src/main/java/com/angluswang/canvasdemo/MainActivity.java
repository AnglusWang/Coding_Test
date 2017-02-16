package com.angluswang.canvasdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    CheckView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv = (CheckView) findViewById(R.id.id_checkView);
        Button check = (Button) findViewById(R.id.btn_check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.check();
            }
        });
        Button unCheck = (Button) findViewById(R.id.btn_unCheck);
        unCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.unCheck();
            }
        });

//        ArrayList<PieData> datas = new ArrayList();
//        PieData pieData = new PieData("name1", 15.0f);
//        datas.add(pieData);
//        pieData = new PieData("name2", 25.0f);
//        datas.add(pieData);
//        pieData = new PieData("name3", 40.0f);
//        datas.add(pieData);
//        pieData = new PieData("name4", 40.0f);
//        datas.add(pieData);
//        pieData = new PieData("name5", 40.0f);
//        datas.add(pieData);
//        pieData = new PieData("name6", 40.0f);
//        datas.add(pieData);
//
//        PieView pv = (PieView) findViewById(R.id.id_pieView);
//        pv.setData(datas);
    }
}
