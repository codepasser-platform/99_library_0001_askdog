package com.askdog.android.model;

import java.util.ArrayList;

/**
 * Created by wyong on 2016/8/8.
 */
public class ChannelBean {
    public int size;                                   // 翻页信息：每页显示条数
    public int page;                                   // 翻页信息：当前第几页
    public int total;                                  // 总的数据条数
    public boolean last;                               // 是否最后一页
    public ArrayList<ChannelResult> result;

    public static class ChannelResult {
        public String id;                              // 频道ID
        public String name;                            // 频道名称
        public int subscriber_count;                   // 频道订阅数
        public String thumbnail;                       // 频道封面图片URL
        public boolean subscribable;                   // 是否可订阅此频道
        public boolean subscribed;                     // 是否订阅了此频道
        public boolean mine;                           // 是否我创建的频道
    }
}
