package com.askdog.android.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.utils.NToast;
import com.askdog.android.view.dialog.CommonToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecurityMenuActivity extends BaseActivity {
    private String TAG = "ForGetPasswordActivity";

    @Bind(R.id.tab_login_title)
    TextView titleTextView;
    @Bind(R.id.security_weixin_text)
    TextView mWeixinTextView;
    @Bind(R.id.security_weixin_img)
    ImageView mWeinxinImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        titleTextView.setText("安全中心");
    }



    @OnClick({R.id.bar_back_btn, R.id.security_weixin_layout, R.id.security_rest_password_layout})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent mIntent;
        switch (id) {
            case R.id.bar_back_btn:
                finish();
                break;
            case R.id.security_weixin_layout:
                NToast.shortToast(this,"功能开发中，请等待...");
                break;
            case R.id.security_rest_password_layout:
                mIntent = new Intent(SecurityMenuActivity.this, ResetPasswordActivity.class);
                startActivityForResult(mIntent,100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            CommonToast toast = new CommonToast(SecurityMenuActivity.this);
            toast.setMessage("密码修改成功");
            toast.show();
        }
    }
}
