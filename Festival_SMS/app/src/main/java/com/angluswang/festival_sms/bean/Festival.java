package com.angluswang.festival_sms.bean;

import java.util.Date;

/**
 * Created by Jeson on 2016/7/15.
 * 节日信息实体类
 */

public class Festival {
    private int id;
    private String name;
    private String desc;
    private Date mDate;

    public Festival(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
