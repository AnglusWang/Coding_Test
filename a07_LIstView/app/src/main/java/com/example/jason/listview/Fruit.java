package com.example.jason.listview;

/**
 * Created by Jason on 4/4/2016.
 * Fruit 实体类，作为ListView适配器的适配类型
 */
public class Fruit {

    private String name;

    private int imageId;

    private int price;

    public Fruit(String name, int imageId, int price) {
        this.name = name;
        this.imageId = imageId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public int getPrice() {
        return price;
    }
}
