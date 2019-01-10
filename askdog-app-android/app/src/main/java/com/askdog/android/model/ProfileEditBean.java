package com.askdog.android.model;

/**
 * Created by wyong on 2016/9/5.
 */
public class ProfileEditBean {
    public String avatar;                //头像
    public String name;                   //用户名
    public String phone;                  // 手机号码
    public String gender;                 // 性别
    public Address address;               // 地址信息
    public String occupation;             // 职业
    public String major;                 // 专业
    public String school;                // 学校
    public String signature;             // 签名

    public static class Address {
        public String province;           // 省份
        public String city;               // 城市
    }
}
