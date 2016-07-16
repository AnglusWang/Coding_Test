package com.angluswang.festival_sms.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeson on 2016/7/15.
 */

public class FestivalLab {

    public static FestivalLab mInstance;

    private List<Festival> mFestivals = new ArrayList<>();
    private List<Msg> mMsgs = new ArrayList<>();

    private FestivalLab() {
        mFestivals.add(new Festival(1, "国庆节"));
        mFestivals.add(new Festival(2, "中秋节"));
        mFestivals.add(new Festival(3, "元旦节"));
        mFestivals.add(new Festival(4, "春节"));
        mFestivals.add(new Festival(5, "端午节"));
        mFestivals.add(new Festival(6, "七夕节"));
        mFestivals.add(new Festival(7, "圣诞节"));
        mFestivals.add(new Festival(8, "儿童节"));

        mMsgs.add(new Msg(1, 1, "云淡风也轻，秋叶飘满天，金秋收获季，共庆国庆节；" +
                "祝您国庆佳节天天好心情，事事都如意！"));
        mMsgs.add(new Msg(2, 2, "过几天就是中秋了，不知道现在发祝福短信给你是否有点早，" +
                "不过我想通了，提前的祝福和迟到的祝福都没有关系，因为我对你祝福的心是永远都不会改变的。中秋快乐！"));
        mMsgs.add(new Msg(3, 3, "新年新气象，百事可乐，万事七喜，心情雪碧，学习芬达，工作红牛，" +
                "生活茹梦，爱情鲜橙多，天天娃哈哈，月月乐百事"));
        mMsgs.add(new Msg(4, 4, "心连心，接受春的赏赐。愿你快快乐乐地迎新年。我们不常拥有新年，" +
                "却常拥有新的一天。愿你每一天，都充满幸福和喜悦。"));
        mMsgs.add(new Msg(5, 5, "端起轻松的酒杯，与美丽举杯；端起如意的酒杯，与成功交杯；端起惬意的酒杯，" +
                "与健康碰杯；端起幸福的酒杯，与快乐干杯。端午节到了，愿你端起人生美满的酒杯，快乐相随。"));
        mMsgs.add(new Msg(6, 6, "老天给我最大的恩赐就是让我拥有了你，拥有了你的爱！在这七夕之夜，" +
                "我祝福我们永远在一起，永不分离！！！"));
        mMsgs.add(new Msg(7, 7, "深祝福，丝丝情谊，串串思念，化作一份礼物，留在你的心田，祝你圣诞快乐，新年幸福！"));
        mMsgs.add(new Msg(8, 8, "今日大寒，满天雪花飞舞，临近年关仅六天，老叟童依皆大欢；瑞雪兆丰年，龙年伊始话慨感；" +
                "悦心不减当年；炮声处处映门庭；欢畅笑语迎新年！"));

    }

    public List<Msg> getMsgsByFestivalId(int fesId) {
        List<Msg> msgs = new ArrayList<>();
        for (Msg msg : mMsgs) {
            if (msg.getFestivalId() == fesId) {
                msgs.add(msg);
            }
        }
        return msgs;
    }

    public Msg getMsgByMsgId(int id) {
        Msg msg = null;
        for (Msg m : mMsgs) {
            if (m.getId() == id) {
                msg = m;
            }
        }
        return msg;
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
