package com.askdog.android.model;

import java.util.ArrayList;

/**
 * 获取频道经验列表
 * Created by wyong on 2016/8/19.
 */
public class ChannelDetailItemBean {
    public int size;                               // 翻页信息：每页显示条数
    public int page;                               // 翻页信息：当前第几页
    public int total;                              // 总的数据条数
    public boolean last;                           // 是否最后一页
    public ArrayList<ChannelExpResult> result;

    public static class ChannelExpResult {
        public String id;              // 经验ID
        public String subject;        // 经验标题
        public ChannelContent content;// 经验内容

        public static class ChannelContent {
            public String type;                    // 经验内容类别，TEXT表示图片经验 VIDEO为视频经验
            public String content;                 // 经验内容正文，截取前33个字符


            public ContentVideo video;

            public static class ContentVideo {
                public int width;                    // 视频宽度 （单位 px）
                public int height;                   // 视频高度 （单位 px）
                public float duration;               // 视频时长 （单位 s）
                public String format;                 // 视频格式
                public long file_size;               // 视频文件大小 （单位 bit）
                public float bit_rate;              // 视频码率 （单位 kbps）
                public String url;                    // 视频URL地址
                public String resolution;            // 视频清晰度标识，UHD(超清), FHD(全高清), HD(高清), SD(标清), LD(流畅)
            }
        }

        public ChannelCategory category;          // 经验所属分类

        public static class ChannelCategory {
            public String id;                      // 分类ID
            public String code;                    // 分类代码
            public String name;                    // 分类名称
        }

        public String state;                       // 经验状态，OPEN代表正常
        public String creation_time;              // 经验创建时间
        public String thumbnail;                   // 经验列表缩略图
        public int view_count;                    // 经验浏览数
        public boolean deleted;                   // 经验是否已删除
    }


}
