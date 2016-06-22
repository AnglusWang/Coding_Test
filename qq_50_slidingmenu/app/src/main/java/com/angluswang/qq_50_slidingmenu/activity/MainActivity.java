package com.angluswang.qq_50_slidingmenu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.angluswang.qq_50_slidingmenu.R;
import com.angluswang.qq_50_slidingmenu.view.SlidingMenu;

public class MainActivity extends Activity {

    private SlidingMenu mSlidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mSlidingMenu = (SlidingMenu) findViewById(R.id.id_menu);
    }

    public void toggleMenu (View view) {
        mSlidingMenu.toggle();
    }
}
