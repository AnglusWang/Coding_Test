package com.example.jason.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jason on 4/4/2016.
 * 自定义一个适配器
 */
public class FruitAdapter extends ArrayAdapter<Fruit> {
    private int resourceId;

    public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = getItem(position);    //获取当前项的Fruit实例

        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            viewHolder.fruitName = (TextView) view.findViewById(R.id.fruit_name);
            viewHolder.fruitPrice = (TextView) view.findViewById(R.id.fruit_price);
            view.setTag(viewHolder);    //将viewHolder存储在view中
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();   //重新获取ViewHolder
        }
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
        viewHolder.fruitPrice.setText("$ "+fruit.getPrice()+".00元/斤");

          //优化前
//        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
//        TextView fruitName = (TextView) view.findViewById(R.id.fruit_name);
//
//        fruitImage.setImageResource(fruit.getImageId());
//        fruitName.setText(fruit.getName());

        return view;
    }
    class ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
        TextView fruitPrice;
    }
}
