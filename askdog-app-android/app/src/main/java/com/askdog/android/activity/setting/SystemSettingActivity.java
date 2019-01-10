package com.askdog.android.activity.setting;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.DataCleanManager;
import com.askdog.android.utils.PreferencesUtils;
import com.askdog.android.utils.common.ActivityPageManager;
import com.askdog.android.view.dialog.CommonToast;
import com.askdog.android.view.dialog.ConfirmDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SystemSettingActivity extends BaseActivity implements ConfirmDialog.OnConfirmListener {
    @Bind(R.id.navi_title)
    TextView navi_title;
    @Bind(R.id.setting_wifi_check)
    CheckBox checkBox;
    @Bind(R.id.setting_cache_count)
    TextView count;
    private ConfirmDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        navi_title.setText("系统设置");
    }

    @OnClick({R.id.navi_back, R.id.setting_wifi_check, R.id.setting_exit_btn, R.id.setting_about_askdog, R.id.setting_clear_cache})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navi_back:
                finish();
                break;
            case R.id.setting_wifi_check:
//                checkBox.setChecked(checkBox.isChecked() ? false : true);
                PreferencesUtils.putBoolean(this, ConstantUtils.WIFIPERMIT, checkBox.isChecked());
                break;
            case R.id.setting_about_askdog:
                Intent i = new Intent();
                i.setClass(SystemSettingActivity.this,AboutAskdogActivity.class);
                startActivity(i);
                break;
            case R.id.setting_clear_cache:
                DataCleanManager.cleanInternalCache(this);

                CommonToast toast = new CommonToast(SystemSettingActivity.this);
                toast.setMessage("清理成功");
                toast.show();
                count.setText("0");
                break;
            case R.id.setting_exit_btn:
                if (mDialog == null) {
                    mDialog = new ConfirmDialog(this, this);
                }
                mDialog.showDialog("是否确定退出");
                break;
        }
    }

    @Override
    public void OnConfirmClick() {
        ActivityPageManager.getInstance().exit(this, true);
    }
}
