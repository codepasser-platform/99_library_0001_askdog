package com.askdog.android.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/14.
 */
public class HistoryBean {
    public int size;                                 // 翻页信息：每页显示条数
    public int page;                                 // 翻页信息：当前第几页
    public int total;                                // 总的数据条数
    public boolean last;                            // 是否最后一页

    public ArrayList<HistoryResult> result;         // 浏览历史记录数组

    public static class HistoryResult {
        public String id;                                // 经验ID
        public String subject;                        // 经验标题
        public H_Content content;

        public static class H_Content {
            public String type;                      // 经验内容类别，TEXT表示图片经验  VIDEO为视频经验
            public String content;                   // 经验内容正文，截取前33个字符

            public ContentVideo video;

            public static class ContentVideo {
                public int width;                    // 视频宽度 （单位 px）
                public int height;                   // 视频高度 （单位 px）
                public float duration;               // 视频时长 （单位 s）
                public String format;                 // 视频格式
                public long file_size;               // 视频文件大小 （单位 bit）
                public float bit_rate;              // 视频码率 （单位 kbps）
                public String url;                    // 视频URL地址
                public String definition;            // 视频清晰度标识，UHD(超清), FHD(全高清), HD(高清), SD(标清), LD(流畅)
            }
        }

        public H_Author author;

        public static class H_Author {
            public String id;                           // 经验作者ID
            public String name;                        // 经验作者名称
        }

        public H_Channel channel;

        public static class H_Channel {
            public String id;                             // 经验所属频道
            public String name;                           // 经验所属频道名称
        }

        public H_Category category;

        public static class H_Category {
            public String code;                           // 类别代码
            public String name;                           // 类别名称
        }

        public String state;                               // 经验状态，OPEN代表正常
        public long creation_time;                       // 经验创建时间
        public String thumbnail;                            // 经验列表缩略图
        public int view_count;                              // 经验浏览数
        public boolean deleted;                             // 经验是否已删除
    }
}
