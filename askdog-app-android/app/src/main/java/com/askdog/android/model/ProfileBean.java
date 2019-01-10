package com.askdog.android.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/28.
 */
public class ProfileBean {
    public String id;
    public String name;
    public String mail;
    public String type;
    public int notice_count;
    public ArrayList<String> category_codes;        // 如果用户没有选择频道类别，则不返回该字段
    public String avatar;
    public boolean vip;
    public String signature;
}
