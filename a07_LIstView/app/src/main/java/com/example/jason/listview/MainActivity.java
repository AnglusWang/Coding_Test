package com.example.jason.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{

//    private String[] fruits = {"apple","banana","orange","watermelon","pear",
//            "grape","pineapple","strawberry","cherry","mango","other1","other2",
//            "other3","other4","other5"};

    private List<Fruit> fruitList = new ArrayList<>();

    private int[] price = {998, 462, 530, 342, 564, 344, 654, 413, 344, 644, 894, 234};

//    private Button back;
//    private TextView title;
//    private Button edit;

    @Override
    public void onClick(View v) {

        //旧方法
//        switch (v.getId()) {
//            case R.id.title_back:
//                Toast.makeText(MainActivity.this, "点了也没用", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.title_name:
//                Toast.makeText(MainActivity.this, "标题也能点？", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.title_eidt:
//                Toast.makeText(MainActivity.this, "还没定义呢", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        back =(Button) findViewById(R.id.title_back);
//        title = (TextView) findViewById(R.id.title_name);
//        edit = (Button) findViewById(R.id.title_eidt);
//
//        back.setOnClickListener(this);
//        title.setOnClickListener(this);
//        edit.setOnClickListener(this);

        /* 使用系统的适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (MainActivity.this,android.R.layout.simple_list_item_1,fruits);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        */

        //使用自定义适配器
        initFruits();   //初始化数据
        FruitAdapter adapter =
                new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                String toast = "This is a "+fruit.getName();

                switch(fruit.getImageId()) {
                    case R.drawable.apple:
                        Intent intent = new Intent(MainActivity.this, appleActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initFruits() {
        Fruit apple = new Fruit("Apple",R.drawable.apple,price[0]);
        fruitList.add(apple);
        Fruit banana = new Fruit("Banana",R.drawable.banana,price[1]);
        fruitList.add(banana);
        Fruit orange = new Fruit("Orange",R.drawable.orange,price[2]);
        fruitList.add(orange);
        Fruit watermelon = new Fruit("Watermelon",R.drawable.watermelon,price[3]);
        fruitList.add(watermelon);
        Fruit pear = new Fruit("Pear",R.drawable.pear,price[4]);
        fruitList.add(pear);
        Fruit grape = new Fruit("Grape",R.drawable.grape,price[5]);
        fruitList.add(grape);
        Fruit pineapple = new Fruit("Pineapple",R.drawable.pineapple,price[6]);
        fruitList.add(pineapple);
        Fruit strawberry = new Fruit("Strawberry",R.drawable.strawberry,price[7]);
        fruitList.add(strawberry);
    }
}
