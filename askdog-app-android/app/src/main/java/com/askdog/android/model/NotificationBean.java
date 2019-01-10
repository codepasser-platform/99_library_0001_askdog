package com.askdog.android.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/3.
 */
public class NotificationBean {
    public ArrayList<NofifyResult> result;
    public static class NofifyResult{
        public GroupDate group_date;                // 分组日期
        public static class GroupDate {
            public int y;
            public int m;
            public int d;
        }
        public ArrayList<GroupData> group_data;                  // 分组数据
        public static class GroupData {
            public String id;       // 通知ID
            public String notification_type;           // 通知类别 EVENT: 事件通知
            public String recipient;                     // 接收者ID
            public Content content;                     // 通知内容。不同notification_type的内容格式可能不同，EVENT类别下content实为event对象
            public static class Content {
                public User user;// 触发此事件的用户

                public static class User {
                    public String id;          // 用户ID
                    public String name;                  // 用户姓名
                }

                public String type;  // 事件类别CODE, 事件CODE说明见上方API说明
                public CTarget target;// 事件目标对象， 对象的具体类别根据事件类别CODE确定

                public static class CTarget {
                    public String id;                           // 目标ID
                    public String description;                 // 目标描述
                    public boolean deleted;                   // 是否已删除
                    public COwner owner;

                    public static class COwner {
                        public String id;                         // 目标ID
                        public String description;               // 目标描述
                        public boolean deleted;                 // 是否已删除
                    }
                }
            }
            public boolean read;
            public long create_date;
        }
    }
    public int size;                                  // 翻页信息：每页显示条数
    public int page;                                  // 翻页信息：当前第几页
    public int total;                                // 总的数据条数
    public boolean last;                             // 是否最后一页
}
