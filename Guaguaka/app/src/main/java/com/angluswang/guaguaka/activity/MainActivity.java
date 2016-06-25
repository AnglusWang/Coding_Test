package com.angluswang.guaguaka.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.angluswang.guaguaka.R;
import com.angluswang.guaguaka.view.Guaguaka;

public class MainActivity extends Activity {

    private Guaguaka guaguaka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guaguaka = (Guaguaka) findViewById(R.id.id_guaguaka);
        guaguaka.setonGuaguakaCompleteListener(new Guaguaka.onGuaguakaCompleteListener() {
            @Override
            public void complete() {
                Toast.makeText(MainActivity.this, "刮的差不多了",
                        Toast.LENGTH_SHORT).show();
            }
        });
        guaguaka.setText("Android新技能Get");
    }
}
