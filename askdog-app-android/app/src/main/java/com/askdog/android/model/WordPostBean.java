package com.askdog.android.model;

import java.util.ArrayList;

/**
 * Created by wyong on 2016/8/22.
 */
public class WordPostBean {
    public String subject;                           // 经验标题
    public WContent content;                          // 经验内容
    public int price;                                // 经验价格
    public String channel_id;                        // 经验所属频道ID
    public String category_id;                       // 经验所属分类ID

    public static class WContent {
        public String type;                          // 经验内容类别，TEXT代表图文经验
        public String content;                       // 经验内容正文，markdown格式
        public ArrayList<String> picture_link_ids;   // 经验内容正文中包含的图片对应得linkId
    }
}
