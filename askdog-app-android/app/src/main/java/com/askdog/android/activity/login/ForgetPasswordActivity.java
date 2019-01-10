package com.askdog.android.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.CommonUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity {
    private String TAG = "ForGetPasswordActivity";
    private String name = null;
    @Bind(R.id.bar_back_btn)
    ImageView mBackBtn;

    @Bind(R.id.tab_login_title)
    TextView titleTextView;

    @Bind(R.id.forget_email_err_text)
    TextView emailErrTextView;

    @Bind(R.id.forget_send_btn)
    Button mSendBtn;

    @Bind(R.id.forget_name_edt)
    EditText mForgetNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        initView();
        // 中间按键图片触发

    }

    private void initView() {
        titleTextView.setText("密码重置");
    }

    @OnClick({R.id.bar_back_btn, R.id.forget_send_btn})
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.bar_back_btn:
                finish();
                break;

            case R.id.forget_send_btn:
                Intent mIntent;
                if (mForgetNameEditText.getText() != null) {
                    name = mForgetNameEditText.getText().toString();
                }

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }

                if (CommonUtils.isEmail(name)) {
                    mIntent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                    mIntent.putExtra("isPhone",false);
                    mIntent.putExtra("email",name);

                    startActivity(mIntent);
                    break;
                } else if (CommonUtils.isPhone(name)) {
                    request(OkRequestConstants.CODE_CHECK_PHONE_NUMBER);
                    break;
                } else {
                    emailErrTextView.setVisibility(View.VISIBLE);
                    break;
                }
            default:
                break;
        }
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (requestCode == OkRequestConstants.CODE_CHECK_PHONE_NUMBER)
            return action.checkPhone(mForgetNameEditText.getText().toString());
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        if (requestCode == OkRequestConstants.CODE_CHECK_PHONE_NUMBER) {
            Intent mIntent = new Intent(ForgetPasswordActivity.this, CheckPhoneActivity.class);
            mIntent.putExtra("phone",name);
            startActivity(mIntent);
        }
    }
}
