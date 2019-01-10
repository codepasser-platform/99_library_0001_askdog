package com.askdog.android.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = "ResetPasswordActivity";

    @Bind(R.id.navi_title)
    TextView titleTextView;
    @Bind(R.id.reset_pwd_old)
    EditText pwd_old;
    @Bind(R.id.reset_pwd_new)
    EditText pwd_new;
    @Bind(R.id.reset_pwd_confirm)
    EditText pwd_confirm;

    @Bind(R.id.reset_pwd_differ)
    TextView differ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_reset_passwrod);
        ButterKnife.bind(this);
        initView();
        // 中间按键图片触发

    }

    private void initView() {
        titleTextView.setText("密码重置");
    }

    @OnClick({R.id.navi_back, R.id.setting_reset_btn})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent mIntent;
        switch (id) {
            case R.id.navi_back:
                finish();
                break;
            case R.id.setting_reset_btn:
                String old = pwd_old.getText().toString().trim();
                String newpwd = pwd_new.getText().toString().trim();
                String confirm = pwd_confirm.getText().toString().trim();
                if (TextUtils.isEmpty(old)) {
                    pwd_old.requestFocus();
                    break;
                }
                if (TextUtils.isEmpty(newpwd)) {
                    pwd_new.requestFocus();
                    break;
                }
                if (TextUtils.isEmpty(confirm)) {
                    pwd_confirm.requestFocus();
                    break;
                }
                if (!newpwd.equals(confirm)) {
                    pwd_new.requestFocus();
                    pwd_new.setText("");
                    pwd_confirm.setText("");
                    differ.setVisibility(View.VISIBLE);
                }
                request(OkRequestConstants.CODE_RESET_PWD);
                break;
            default:
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(mContext);
        if(requestCode ==OkRequestConstants.CODE_RESET_PWD){
            return action.putResetPwd(pwd_old.getText().toString(), pwd_new.getText().toString());
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        setResult(RESULT_OK);
        finish();
    }
}
