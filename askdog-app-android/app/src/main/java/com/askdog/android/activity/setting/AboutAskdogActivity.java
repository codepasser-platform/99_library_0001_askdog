package com.askdog.android.activity.setting;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutAskdogActivity extends BaseActivity {

    @Bind(R.id.navi_title)
    TextView title;

    @Bind(R.id.about_askdog_version)
    TextView version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_askdog);
        ButterKnife.bind(this);
        title.setText("关于");
        version.setText(getVersion());
    }

    @OnClick(R.id.navi_back)
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.navi_back:
               finish();
               break;

       }
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return this.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.version_name) + 2.0;
        }
    }
}
