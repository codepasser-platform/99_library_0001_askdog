package com.askdog.android.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/2.
 */
public class HomeBean {
    public int total;
    public boolean last;
    public ArrayList<Result> result;
    public class Result{
        public float video_duration;
        public int hot_score;
        public String subject;
        public String content_pic_url;
        public Author author;
        public String content_text;
        public Channel channel;
        public long creation_date;
        public String id;
        public Category category;
        public int view_count;
        public String _from;
        public String content_type;
        public class Author{
            public String avatar_url;
            public String name;
            public String id;
            public boolean vip;
        }
        public class Channel{
            public String channel_name;
            public String channel_thumbnail;
            public long channel_id;
        }
        public class Category{
            public String category_name;
            public String category_code;
        }
    }
}
