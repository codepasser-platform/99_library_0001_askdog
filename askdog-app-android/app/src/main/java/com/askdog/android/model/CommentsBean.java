package com.askdog.android.model;

import java.util.ArrayList;

/**
 * Created by wyong on 2016/8/25.
 */
public class CommentsBean {
    public int size;
    public int page;
    public int total;
    public boolean last;
    public ArrayList<CommentsResult> result;
    public static class CommentsResult{
        public String id;
        public String content;
        public Commenter commenter;
        public CommentsBean comments;
        public static class Commenter {
            public String id;
            public String name;
            public String avatar;
        }
        public String reply_name;                    // 被评论的经验或者评论的所有者的名称
        public long  creation_time;                  // 评论创建时间
        public String owner_id;                      // 评论目标ID
        public String reply_comment_id;              // 回复的评论的ID,只有二级评论存在该字段
        public boolean mine;                         // 是否我创建的评论
        public boolean deletable;                    // 此评论是否可删除
    }
}
