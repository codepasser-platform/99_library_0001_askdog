package com.askdog.android.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.askdog.android.BaseActivity;
import com.askdog.android.BaseApplication;
import com.askdog.android.BaseFragment;
import com.askdog.android.R;
import com.askdog.android.activity.cash.MyCashActivity;
import com.askdog.android.activity.login.LoginActivity;
import com.askdog.android.model.HomeBean;
import com.askdog.android.model.NotificationBean;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.CommonUtils;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.NLog;
import com.askdog.android.utils.common.ActivityPageManager;
import com.askdog.android.view.dialog.CommendPopupWindowUtil;
import com.askdog.android.view.dialog.PopupWindowUtil;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.PreferencesUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener, View.OnTouchListener, View.OnClickListener {
    private static final int LOGIN = 100;
    @Bind(android.R.id.tabhost)
    protected FragmentTabHost mTabHost;
    @Bind(R.id.quick_option_iv)
    protected View mAddBt;
    @Bind(R.id.unreal_search_header)
    RelativeLayout header;

    @Bind(R.id.main_login)
    TextView login;
    @Bind(R.id.main_remind_message)
    ImageView main_remind;
    @Bind(R.id.main_title_notify_no)
    TextView notify_no;

    private boolean crash = false;
    private String TAG = "MainActivity";
    private int width = 0;
    private boolean isLogin;
    private boolean isStart = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        // 中间按键图片触发
        intiDate();
        width = CommonUtils.getScreenWidth(this);
    }

    private void intiDate() {
        PreferencesUtils.putBoolean(this, ConstantUtils.IS_LOGIN, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isStart = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferencesUtils.putBoolean(this, ConstantUtils.IS_LOGIN, false);
    }

    private void initView() {
        mAddBt.setOnClickListener(this);
        initTabs();
    }

    @Override
    public void onBackPressed() {
        BaseFragment fm = (BaseFragment) getCurrentFragment();
        fm.onBackPressed();
        long curTime = SystemClock.uptimeMillis();
        if ((curTime - mBackPressedTime) < (3 * 1000)) {
            finish();
            System.exit(0);
        } else {
            mBackPressedTime = curTime;
            Toast.makeText(this, R.string.tip_double_click_exit, Toast.LENGTH_LONG).show();
        }
    }

    private void initTabs() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }

        MainTab[] tabs = MainTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = View.inflate(this, R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            ImageView icon = (ImageView) indicator.findViewById(R.id.iv_icon);

            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            icon.setImageDrawable(drawable);

            if (i == 2) {
                indicator.setVisibility(View.INVISIBLE);
            }
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {

                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            mTabHost.addTab(tab, mainTab.getClz(), null);
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
        mTabHost.setCurrentTab(0);

        mTabHost.setOnTabChangedListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        boolean consumed = false;
        // use getTabHost().getCurrentTabView to decide if the current tab is
        // touched again
        int id = v.getId();
        isLogin = PreferencesUtils.getBoolean(this, ConstantUtils.IS_LOGIN, false);
        if (event.getRawX() > width / 2 && !isLogin && !isStart) {
            isStart = true;//防止频繁启动
            startLogin();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {
            // use getTabHost().getCurrentView() to get a handle to the view
            // which is displayed in the tab - and to get this views context
            Fragment currentFragment = getCurrentFragment();

            consumed = true;

        }
        return consumed;
    }


    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

    @Override
    public void onTabChanged(String tabId) {
        final int size = mTabHost.getTabWidget().getTabCount();
        boolean canLogin = false;
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
        supportInvalidateOptionsMenu();
    }

    @OnClick({R.id.main_unreal_search, R.id.main_login, R.id.main_remind_message})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent();
        switch (id) {
            case R.id.quick_option_iv:
                isLogin = PreferencesUtils.getBoolean(this, ConstantUtils.IS_LOGIN, false);
                if (!isLogin) {
                    startLogin();
                } else {
                    showQuickOption();
                }
//                CommendPopupWindowUtil.showPopuWindowUtil(MainActivity.this);
                break;
            case R.id.main_login:
                startLogin();
                break;
            case R.id.main_unreal_search:
                intent.setClass(MainActivity.this, SearchExperiencesActivity.class);
                startActivity(intent);
                break;
            case R.id.main_remind_message:

                intent.setClass(MainActivity.this, NotificationMessageActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void startLogin(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, LOGIN);
    }
    public void showHeaderView(boolean show) {
        if (show) {
            header.setVisibility(View.VISIBLE);
        } else {
            header.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN && resultCode == RESULT_OK) {
            request(OkRequestConstants.CODE_USER_NOTIFICATIONS);
            main_remind.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
            mTabHost.setCurrentTab(0);
        }
    }


    private void showQuickOption() {
        PopupWindowUtil.showPopuWindowUtil(this);

    }

    private long mBackPressedTime;


    /**
     *
     */
    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (OkRequestConstants.CODE_USER_NOTIFICATIONS == requestCode) {
            return action.getUserNotification(0, 1);
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (OkRequestConstants.CODE_USER_NOTIFICATIONS == requestCode) {
                updateHeaderUi();
                NotificationBean mNotificationBean = JsonMananger.jsonToBean(result, NotificationBean.class);
                notify_no.setText(String.valueOf(mNotificationBean.total));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateHeaderUi() {
        notify_no.setVisibility(View.VISIBLE);

    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        super.onFailure(requestCode, state, result);
    }
}
