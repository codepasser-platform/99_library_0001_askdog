package com.askdog.android.model;

import java.util.ArrayList;

/**
 * Created by wyong on 2016/8/10.
 */
public class ExperiencesBean {
    public String id;
    public String subject;
    public String state;
    public int price;
    public long creation_time;
    public String thumbnail;
    public int view_count;
    public int up_vote_count;
    public int down_vote_count;
    public boolean up_voted;
    public boolean down_voted;
    public boolean mine;
    public int comment_count;
    public ExperiencesContent content;
    public ExperiencesAuthor author;
    public ExperiencesChannel channel;
    public ExperiencesCategory category;
    public ArrayList<ExperiencesRecommend> comments;
    public boolean deletable;
    public static class ExperiencesContent {
        public String type;
        public String content;
        public ArrayList<String> picture_link_ids;
        //以下为video字段
        public String link_id;
        public ArrayList<CSnapshots> snapshots;
        public static class CSnapshots{
            public String url;
        }
        public ArrayList<TranscodeVideos> transcode_videos;
        public static class TranscodeVideos{
            public int width;                                   // 视频宽度 （单位 px）
            public int height;                                 // 视频高度 （单位 px）
            public float duration;                              // 视频时长 （单位 s）
            public String format;                               // 视频格式
            public long file_size;                              // 视频文件大小 （单位 bit）
            public float bit_rate;                              // 视频码率 （单位 kbps）
            public String url;                                  // 视频URL地址
            public String resolution;                           // 视频清晰度标识，UHD(超清), FHD(全高清), HD(高清), SD(标清), LD(流畅)
        }
    }

    public static class ExperiencesAuthor {
        public String id;
        public String name;
        public String avatar;
        public String gender;
        public String signature;
    }

    public static class ExperiencesChannel {
        public String id;
        public String name;
        public int subscriber_count;
        public boolean subscribable;
        public boolean subscribed;
        public boolean mine;
    }

    public static class ExperiencesCategory {
        public String id;
        public String code;
        public String name;
    }

    public static class ExperiencesRecommend {
        public String id;
        public String content;
        public ExperiencesCommenter commenter;
        public String creation_time;
        public boolean mine;
        public boolean deleted;
        public ArrayList<ExperiencesRecommend> comments;
        public static class ExperiencesCommenter {
            public String id;
            public String name;
            public String avatar;
            public String gender;
            public String signature;
        }

    }
}
