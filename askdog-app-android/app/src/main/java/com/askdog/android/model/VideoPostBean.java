package com.askdog.android.model;

/**
 * Created by wyong on 2016/8/26.
 */
public class VideoPostBean {
    public String subject;                           // 经验标题
    public VContent content;                          // 经验内容
    public int price;                                // 经验价格
    public String channel_id;                        // 经验所属频道ID
    public String category_id;                       // 经验所属分类ID

    public static class VContent {
        public String type;                          // 经验内容类别，TEXT代表图文经验 VIDEO
        public String video_link_id;              // 经验内容正文中包含的图片对应得linkId
    }
}
