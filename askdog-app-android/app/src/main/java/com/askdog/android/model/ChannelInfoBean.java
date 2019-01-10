package com.askdog.android.model;

import java.util.ArrayList;

/**
 * Created by wyong on 2016/8/19.
 */
public class ChannelInfoBean {

    public String id;                     // 频道ID
    public String name;                   // 频道名称
    public String description;            // 频道描述
    public int subscriber_count;          // 频道订阅数
    public String thumbnail;              // 频道封面图片URL
    public ArrayList<String> tags;        // 频道标签数组
    public boolean subscribable;          // 是否可订阅此频道
    public boolean subscribed;            // 是否订阅了此频道
    public boolean mine;                  // 是否我创建的频道
    public boolean deletable;             // 此频道是否可删除
    public long creation_time;            // 频道创建时间
    public ChannelOwner owner;            // 频道所有者

    public static class ChannelOwner {
        public String id;// 频道所有者
        public String name;// 频道所有者
    }
}
