package com.askdog.android.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.view.dialog.CommonToast;
import butterknife.Bind;
import butterknife.ButterKnife;

public class CheckPhoneActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.bar_back_btn)
    ImageView mBackBtn;
    @Bind(R.id.tab_login_title)
    TextView titleTextView;
    @Bind(R.id.register_btn)
    Button mRegisterBtn;
    @Bind(R.id.btn_login_submit)
    Button submit;
    @Bind(R.id.tv_phone)
    TextView phoneTextView;
    @Bind(R.id.et_code)
    EditText codeEditText;

    private TimeCount time;

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            submit.setText("获取验证码");
            submit.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            submit.setClickable(false);//防止重复点击
            submit.setText("等待"+String.valueOf(millisUntilFinished / 1000)+"秒");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_phone);
        ButterKnife.bind(this);
        initView();
        // 中间按键图片触发
        time = new TimeCount(60000, 1000);
        request(OkRequestConstants.CODE_SEND_MESSAGE);
    }

    private void initView() {
        titleTextView.setText("注册");
        mBackBtn.setOnClickListener(this);
        submit.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
        Intent intent = getIntent();
        phoneTextView.setText(intent.getStringExtra("phone"));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bar_back_btn:
                finish();
                break;
            case R.id.register_btn:

                String smsCode = null;
                String phone = null;

                if (codeEditText.getText() != null) {
                    smsCode = codeEditText.getText().toString();
                }
                if (phoneTextView.getText() != null) {
                    phone = phoneTextView.getText().toString();
                }

                Intent mIntent = new Intent(CheckPhoneActivity.this,ResetPasswordActivity.class);
                mIntent.putExtra("isPhone",true);
                mIntent.putExtra("phone",phone);
                mIntent.putExtra("code",smsCode);
                startActivity(mIntent);

//                if (TextUtils.isEmpty(smsCode)) {
//                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
//                    break;
//                }
//                request(OkRequestConstants.CODE_CHECK_MESSAGE_NUMBER);
                break;
            case R.id.useEmailRegister:
                finish();
                break;
            case R.id.btn_login_submit:
                request(OkRequestConstants.CODE_SEND_MESSAGE);
                break;
            default:
                break;
        }
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (requestCode == OkRequestConstants.CODE_SEND_MESSAGE){
            return action.sendMessageUsePhone(phoneTextView.getText().toString());
        } else if (requestCode == OkRequestConstants.CODE_CHECK_MESSAGE_NUMBER) {
            return action.checkSmsCode(phoneTextView.getText().toString(),codeEditText.getText().toString());
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        if (requestCode == OkRequestConstants.CODE_SEND_MESSAGE) {
            sendMsgSuccess();
        } else if (requestCode == OkRequestConstants.CODE_CHECK_MESSAGE_NUMBER) {
            startResetPasswordActivity();
        }
    }

    public void startResetPasswordActivity() {
        Intent mIntent = new Intent(CheckPhoneActivity.this,ResetPasswordActivity.class);
        mIntent.putExtra("isPhone",true);
        mIntent.putExtra("phone",phoneTextView.getText().toString());
        mIntent.putExtra("code",codeEditText.getText().toString());
        startActivity(mIntent);
    }

    public void sendMsgSuccess() {
        CommonToast toast = new CommonToast(CheckPhoneActivity.this);
        toast.setMessage("验证码发送成功");
        toast.show();
        time.start();
    }
}
