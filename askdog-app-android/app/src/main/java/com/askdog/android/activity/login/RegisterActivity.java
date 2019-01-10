package com.askdog.android.activity.login;

import android.content.Intent;
import android.os.Bundle;
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
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.NToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.bar_back_btn)
    ImageView mBackBtn;
    @Bind(R.id.tab_login_title)
    TextView titleTextView;
    @Bind(R.id.register_register_name_edt)
    EditText mRegisterNameEditText;
    @Bind(R.id.register_name)
    EditText mNameEditText;
    @Bind(R.id.register_password)
    EditText mPasswordEditText;
    @Bind(R.id.password_clear_image)
    ImageView mClearPasswordBtn;
    @Bind(R.id.password_show_image)
    ImageView mShowPasswordBtn;
    @Bind(R.id.register_btn)
    Button mRegistBtn;
    @Bind(R.id.usePhoneRegister)
    TextView mUsePhoneRegister;
    @Bind(R.id.tv_agreement)
    TextView agreementTextView;

    private String TAG = "RegisterActivity";
    private boolean isShowPassword = false;
    private final int REGISTER = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
        // 中间按键图片触发

    }

    private void initView() {
        titleTextView.setText("注册");
        mClearPasswordBtn.setOnClickListener(this);
        mShowPasswordBtn.setOnClickListener(this);
        mRegistBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        mUsePhoneRegister.setOnClickListener(this);
        mRegisterNameEditText.setText("wyong");
        mNameEditText.setText("sharkingyong@163.com");
        mPasswordEditText.setText("123456");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent mIntent;
        switch (id) {
            case R.id.bar_back_btn:
                finish();
                break;
            case R.id.password_clear_image:
                mPasswordEditText.setText("");
                break;
            case R.id.password_show_image:
                // mPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                if (isShowPassword) {
                    isShowPassword = false;
                    //textPassword
                    mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //   mShowPasswordBtn.setImageResource(R.drawable.in_anshul_show_password);
                } else {
                    //   mShowPasswordBtn.setImageResource(R.drawable.in_anshul_hide_password);
                    isShowPassword = true;
                    mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;

            case R.id.register_btn:
                String name = null;
                String passwrod = null;
                String registerName = null;

                if (mRegisterNameEditText.getText() != null) {
                    registerName = mRegisterNameEditText.getText().toString();
                }
                if (mNameEditText.getText() != null) {
                    name = mNameEditText.getText().toString();
                }
                if (mPasswordEditText.getText() != null) {
                    passwrod = mPasswordEditText.getText().toString();
                }
                if (TextUtils.isEmpty(registerName)) {
                    Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(passwrod)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!isEmail(name)) {
                    Toast.makeText(this, "格式不对", Toast.LENGTH_SHORT).show();
                    break;
                }
                request(OkRequestConstants.CODE_REGISTER);
                break;
            case R.id.usePhoneRegister:
                mIntent = new Intent(RegisterActivity.this, UsePhoneRegisterActivity.class);
                startActivityForResult(mIntent, REGISTER);
                break;
            default:
                break;
        }
    }

    public boolean isEmail(String email) {

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(mContext);
        return action.register(mRegisterNameEditText.getText().toString().trim(), mPasswordEditText.getText().toString(), mNameEditText.getText().toString());
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        setResult(RESULT_OK);
        finish();

    }
}
