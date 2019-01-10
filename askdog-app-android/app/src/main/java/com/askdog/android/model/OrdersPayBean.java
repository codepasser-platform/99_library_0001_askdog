package com.askdog.android.model;

/**
 * Created by Administrator on 2016/9/7.
 */
public class OrdersPayBean {
    public String pay_way;
    public String pay_status;
    public String experience_id;
    public String order_id;
    public PayRequest pay_request;

    public static class PayRequest {
        public String appid;
        public String partnerid;
        public String prepayid;
        public String _package;
        public String noncestr;
        public String timestamp;
        public String sign;
    }
}
