package com.askdog.android.model;

import java.util.ArrayList;

/**
 * Created by wyong on 2016/8/24.
 */
public class CashIncomeBean {

    public ArrayList<IncomeResult> result;
    public int size;
    public int page;
    public int total;
    public boolean last;

    public static class IncomeResult {
        public long income_time;                            // 入账时间
        public String price;                                  // 入账金额
        public String experience_title;                       // 经验的标题
    }
}
