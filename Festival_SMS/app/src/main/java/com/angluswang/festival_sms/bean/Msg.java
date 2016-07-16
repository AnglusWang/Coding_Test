package com.angluswang.festival_sms.bean;

/**
 * Created by Jeson on 2016/7/16.
 * 短信的实体
 */

public class Msg {
    private int id;
    private int festivalId;
    private String content;

    public Msg(int id, int festivalId, String content) {
        this.id = id;
        this.festivalId = festivalId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(int festivalId) {
        this.festivalId = festivalId;
    }
}
