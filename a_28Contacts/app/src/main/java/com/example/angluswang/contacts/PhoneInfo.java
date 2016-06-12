package com.example.angluswang.contacts;

/**
 * Created by Jeson on 2016/6/12.
 * 用于将联系人信息封装起来
 */

public class PhoneInfo {
    private String name;
    private String number;

    public PhoneInfo(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
