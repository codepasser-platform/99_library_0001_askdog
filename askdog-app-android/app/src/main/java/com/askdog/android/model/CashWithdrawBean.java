package com.askdog.android.model;

import java.util.ArrayList;

/**
 * Created by wyong on 2016/8/24.
 */
public class CashWithdrawBean {
    public ArrayList<WithdrawResult> result;
    public int size;
    public int page;
    public int total;
    public boolean last;

    public static class WithdrawResult {
        public String id;                                    // 记录ID, 可以忽略
        public String withdrawal_way;                        // 提现方式，"WXPAY"表示提现到微信零钱
        public long withdrawal_time;                         // 提现时间
        public int withdrawal_amount;                        // 提现金额
        public String withdrawal_status;                     // 提现状态
        public String withdrawal_to;                         // 提现账户的名字（或者昵称）
    }
}
