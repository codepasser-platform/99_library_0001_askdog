package com.askdog.android.activity;


import com.askdog.android.R;
import com.askdog.android.fragment.HomeFragment;
import com.askdog.android.fragment.HotFragment;
import com.askdog.android.fragment.MeFragment;
import com.askdog.android.fragment.RecommnedFragment;

/**
 *
 */


public enum MainTab {
    HOME(0, R.string.main_tab_name_home, R.drawable.tab_icon_home,
            HomeFragment.class),

    HOT(1, R.string.main_tab_name_hot, R.drawable.tab_icon_hot,
            HotFragment.class),

    SHARE(2, R.string.main_tab_name_add, R.drawable.icon_pull_up,null),

    RECOMMEND(3, R.string.main_tab_name_recommend, R.drawable.tab_icon_recommend,
            RecommnedFragment.class),

    ME(4, R.string.main_tab_name_me, R.drawable.tab_icon_me,
            MeFragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
