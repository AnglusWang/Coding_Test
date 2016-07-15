package com.angluswang.festival_sms.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeson on 2016/7/15.
 */

public class FestivalLab {

    public static FestivalLab mInstance;

    private List<Festival> mFestivals = new ArrayList<>();

    private FestivalLab() {
        mFestivals.add(new Festival(1, "国庆节"));
        mFestivals.add(new Festival(2, "中秋节"));
        mFestivals.add(new Festival(3, "元旦节"));
        mFestivals.add(new Festival(4, "春节"));
        mFestivals.add(new Festival(5, "端午节"));
        mFestivals.add(new Festival(6, "七夕节"));
        mFestivals.add(new Festival(7, "圣诞节"));
        mFestivals.add(new Festival(8, "儿童节"));
    }

    public List<Festival> getFestivals() {
        return new ArrayList<>(mFestivals);
    }

    public Festival getFestivalById(int fesId) {
        for (Festival festival : mFestivals) {
            if (festival.getId() == fesId) {
                return festival;
            }
        }
        return null;
    }

    public static FestivalLab getInstance() {
        if (mInstance == null) {  //多线程的时候，为了提升效率，没必要每次都同步；
            synchronized (FestivalLab.class) {  //让线程互斥的进入；注意if语句；
                if (mInstance == null) {
                    mInstance = new FestivalLab();
                }
            }
        }
        return mInstance;
    }
}
