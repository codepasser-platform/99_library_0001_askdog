package com.askdog.android.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ShareRelatedBean {

    public int total;
    public boolean last;
    public ArrayList<ShareResult> result;

    public static class ShareResult {
        public int hot_score;                           // 分享分数
        public String subject;                           // 分享标题
        public String content_pic_url;                  // 分享内容缩略图url
        public ShareAuthor author;

        public class ShareAuthor {
            public String avatar_url;                     // 用户头像
            public String name;                            // 用户名字
            public String id;                              // 用户ID
        }

        public String content_text;                       // 分享文字内容

        public ShareChannel channel;

        public class ShareChannel {
            public String channel_name;                     // 频道名字
            public String channel_thumbnail;               // 频道缩略图url
            public String channel_id;                       // 频道id
        }

        public long creation_date;                        // 创建时间
        public String id;                                     // 分享id
        public boolean vip;                                  // 是否是VIP
        public ShareCategory category;

        public class ShareCategory {
            public String category_name;                    // 所属类型名字
            public String category_code;                    // 所属类型Code
        }

        public int view_count;                              // 浏览数
        public String from;                                  // 所属分类
    }

}
