package com.askdog.android.model;

/**
 * Created by Administrator on 2016/8/17.
 */
public class UpDownBean {
    public int up_vote_count;     // 赞数
    public int down_vote_count;  // 踩数
    public String vote_direction;   // UP表示赞了此问题，DOWN表示踩了此问题，null或无此字段表示未赞也未踩
}
