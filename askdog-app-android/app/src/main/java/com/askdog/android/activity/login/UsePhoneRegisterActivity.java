package com.askdog.android.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
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
import com.askdog.android.view.dialog.CommonToast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import butterknife.Bind;
import butterknife.ButterKnife;

public class UsePhoneRegisterActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.bar_back_btn)
    ImageView mBackBtn;
    @Bind(R.id.tab_login_title)
    TextView titleTextView;

    @Bind(R.id.register_phone_name)
    EditText mRegisterPhoneNameEditText;
    @Bind(R.id.register_phone_phone)
    EditText mRegisterPhonePhoneEditText;
    @Bind(R.id.register_phone_password)
    EditText mRegisterPhonePasswordEditText;
    @Bind(R.id.password_clear_image)
    ImageView mClearPasswordBtn;
    @Bind(R.id.password_show_image)
    ImageView mShowPasswordBtn;
    @Bind(R.id.register_btn)
    Button mRegistBtn;
    @Bind(R.id.useEmailRegister)
    TextView mUseEmailRegister;
    @Bind(R.id.btn_login_submit)
    Button submit;
    @Bind(R.id.tv_agreement)
    TextView agreementTextView;
    @Bind(R.id.register_phone_code)
    EditText mRegisterPhoneCodeEditText;

    private String TAG = "RegisterActivity";
    private boolean isShowPassword = false;

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
        setContentView(R.layout.activity_phone_register);
        ButterKnife.bind(this);
        initView();
        time = new TimeCount(60000, 1000);
    }

    private void initView() {
        titleTextView.setText("注册");
        mClearPasswordBtn.setOnClickListener(this);
        mShowPasswordBtn.setOnClickListener(this);
        mRegistBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        mUseEmailRegister.setOnClickListener(this);
        submit.setOnClickListener(this);
        mRegisterPhoneNameEditText.setText("wanghf");
        mRegisterPhonePhoneEditText.setText("13998409981");
        mRegisterPhonePasswordEditText.setText("123456");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent mIntent;
        switch (id) {
            case R.id.bar_back_btn:
                time.cancel();
                finish();
                break;
            case R.id.password_clear_image:
                mRegisterPhonePasswordEditText.setText("");
                break;
            case R.id.password_show_image:
                // mPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                if (isShowPassword) {
                    isShowPassword = false;
                    //textPassword
                    mRegisterPhonePasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //   mShowPasswordBtn.setImageResource(R.drawable.in_anshul_show_password);
                } else {
                    //   mShowPasswordBtn.setImageResource(R.drawable.in_anshul_hide_password);
                    isShowPassword = true;
                    mRegisterPhonePasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;

            case R.id.register_btn:
                String name = null;
                String password = null;
                String registerName = null;
                String smsCode = null;

                if (mRegisterPhoneNameEditText.getText() != null) {
                    registerName = mRegisterPhoneNameEditText.getText().toString();
                }
                if (mRegisterPhonePhoneEditText.getText() != null) {
                    name = mRegisterPhonePhoneEditText.getText().toString();
                }
                if (mRegisterPhonePasswordEditText.getText() != null) {
                    password = mRegisterPhonePasswordEditText.getText().toString();
                }
                if (mRegisterPhoneCodeEditText.getText() != null) {
                    smsCode = mRegisterPhoneCodeEditText.getText().toString();
                }
                if (TextUtils.isEmpty(registerName)) {
                    Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(smsCode)) {
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!isPhone(name)) {
                    Toast.makeText(this, "手机号码不正确", Toast.LENGTH_SHORT).show();
                    break;
                }
                request(OkRequestConstants.CODE_PHONE_REGISTER);
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

    public boolean isPhone(String phone) {

        String str = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(mContext);
        if (requestCode == OkRequestConstants.CODE_PHONE_REGISTER) {
            return action.registerUsePhone(mRegisterPhoneNameEditText.getText().toString().trim(), mRegisterPhonePasswordEditText.getText().toString(), mRegisterPhonePhoneEditText.getText().toString(),mRegisterPhoneCodeEditText.getText().toString());
        } else if (requestCode == OkRequestConstants.CODE_SEND_MESSAGE) {
            return action.sendMessageUsePhone(mRegisterPhonePhoneEditText.getText().toString());
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        if (requestCode == OkRequestConstants.CODE_PHONE_REGISTER) {
            Intent mIntent = new Intent(UsePhoneRegisterActivity.this,LoginActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mIntent);
            finish();
        } else if (requestCode == OkRequestConstants.CODE_SEND_MESSAGE)  {
            sendMsgSuccess();
        }
    }

    public void sendMsgSuccess() {
        CommonToast toast = new CommonToast(UsePhoneRegisterActivity.this);
        toast.setMessage("验证码发送成功");
        toast.show();
        time.start();
    }
}
