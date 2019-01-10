package com.askdog.android.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.CommonUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = "ResetPasswordActivity";

    @Bind(R.id.bar_back_btn)
    ImageView mBackBtn;

    @Bind(R.id.tab_login_title)
    TextView titleTextView;
    @Bind(R.id.setpassword_one_edit)
    EditText mPasswordOneEdit;
    @Bind(R.id.setpassword_two_edit)
    EditText mPasswordTwoEdit;

    @Bind(R.id.sent_password_btn)
    Button mSendBtn;
    @Bind(R.id.pwd_not_match)
    TextView mErrorMessage;

    @Bind(R.id.setpassword_one_clear_image)
    ImageView mClearPasswordOneBtn;

    @Bind(R.id.setpassword_one_show_image)
    ImageView mShowPasswordOneBtn;
    private boolean isShowPasswordOne = false;


    @Bind(R.id.setpassword_two_clear_image)
    ImageView mClearPasswordTwoBtn;

    @Bind(R.id.setpassword_two_show_image)
    ImageView mShowPasswordTwoBtn;

    @Bind(R.id.setpassword_email_edit)
    EditText emailEditText;

    private boolean isShowPasswordTwo = false;
    private boolean isPhone;

    private String phone;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        ButterKnife.bind(this);
        initView();
        // 中间按键图片触发
        Intent intent = getIntent();
        isPhone = intent.getBooleanExtra("isPhone",false);

        if (!isPhone) {
            emailEditText.setText(intent.getStringExtra("email"));
        } else {
            phone = intent.getStringExtra("phone");
            code = intent.getStringExtra("code");
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout_email);
        linearLayout.setVisibility(isPhone ? View.GONE : View.VISIBLE);
    }

    private void initView() {
        titleTextView.setText("密码重置");
        mPasswordOneEdit.addTextChangedListener(watcher1);
        mPasswordTwoEdit.addTextChangedListener(watcher2);

        mShowPasswordOneBtn.setOnClickListener(this);
        mClearPasswordOneBtn.setOnClickListener(this);
        mClearPasswordTwoBtn.setOnClickListener(this);
        mShowPasswordTwoBtn.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bar_back_btn:
                finish();
                break;
            case R.id.setpassword_one_clear_image:
                mPasswordOneEdit.setText("");
                break;
            case R.id.setpassword_two_clear_image:
                mPasswordTwoEdit.setText("");
                break;
            case R.id.setpassword_one_show_image:
                // mPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                if (isShowPasswordOne) {
                    isShowPasswordOne = false;
                    //textPassword
                    mPasswordOneEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //    mShowPasswordOneBtn.setImageResource(R.drawable.in_anshul_show_password);
                } else {
                    //    mShowPasswordOneBtn.setImageResource(R.drawable.in_anshul_hide_password);

                    isShowPasswordOne = true;
                    mPasswordOneEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;
            case R.id.setpassword_two_show_image:
                // mPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                if (isShowPasswordTwo) {
                    isShowPasswordTwo = false;
                    //textPassword
                    mPasswordTwoEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //     mClearPasswordTwoBtn.setImageResource(R.drawable.in_anshul_show_password);
                } else {
                    //    mClearPasswordTwoBtn.setImageResource(R.drawable.in_anshul_hide_password);
                    isShowPasswordTwo = true;
                    mPasswordTwoEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;
            case R.id.sent_password_btn:
                String pwd1 = mPasswordOneEdit.getText().toString();
                String pwd2 = mPasswordTwoEdit.getText().toString();
                String pwd3 = emailEditText.getText().toString();

                if(!isPhone) {
                    if (TextUtils.isEmpty(pwd3)) {
                        emailEditText.requestFocus();
                        Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (!CommonUtils.isEmail(pwd3)) {
                        emailEditText.requestFocus();
                        Toast.makeText(this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if (TextUtils.isEmpty(pwd1)) {
                    mPasswordOneEdit.requestFocus();
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(pwd2)) {
                    mPasswordTwoEdit.requestFocus();
                    Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (pwd1.compareTo(pwd2) != 0) {
                    mErrorMessage.setVisibility(View.VISIBLE);
                }
                else {
                    mErrorMessage.setVisibility(View.GONE);
                    //TODO 请求接口需要矫正
                    if (isPhone) {
                        request(OkRequestConstants.CODE_UPDATE_PHONE_USER_PASSWORD);
                    } else {
                        request(OkRequestConstants.CODE_FIND_PWD);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (requestCode == OkRequestConstants.CODE_FIND_PWD) {
            return action.getFindPwd(emailEditText.getText().toString());
        } else if (requestCode == OkRequestConstants.CODE_UPDATE_PHONE_USER_PASSWORD) {
            return action.updatePhoneUserPassword(phone,code,mPasswordTwoEdit.getText().toString());
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);

        Intent mIntent = new Intent(ResetPasswordActivity.this,LoginActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mIntent);
        finish();
    }

    protected TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!TextUtils.isEmpty(s)) {
                mShowPasswordOneBtn.setVisibility(View.VISIBLE);
                mClearPasswordOneBtn.setVisibility(View.VISIBLE);
            } else {
                mClearPasswordOneBtn.setVisibility(View.GONE);
                mShowPasswordOneBtn.setVisibility(View.GONE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    protected TextWatcher watcher2 = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!TextUtils.isEmpty(s)) {
                mClearPasswordTwoBtn.setVisibility(View.VISIBLE);
                mShowPasswordTwoBtn.setVisibility(View.VISIBLE);
            } else {
                mClearPasswordTwoBtn.setVisibility(View.GONE);
                mShowPasswordTwoBtn.setVisibility(View.GONE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
