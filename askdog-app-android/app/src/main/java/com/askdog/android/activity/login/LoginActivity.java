package com.askdog.android.activity.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.askdog.android.BaseActivity;
import com.askdog.android.BaseApplication;
import com.askdog.android.R;
import com.askdog.android.model.ProfileBean;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.platformlogin.LoginApi;
import com.askdog.android.platformlogin.OnLoginListener;
import com.askdog.android.platformlogin.UserInfo;
import com.askdog.android.utils.CommonUtils;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.NLog;
import com.askdog.android.utils.NToast;
import com.askdog.android.utils.PreferencesUtils;
import com.askdog.android.view.dialog.CommonToast;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final int FORGEG = 200;
    private final int REGISTER = 100;
    @Bind(R.id.navi_back)
    ImageView mBackBtn;
    @Bind(R.id.navi_title)
    TextView title;
    @Bind(R.id.login_regist_btn)
    Button mRegistBtn;
    @Bind(R.id.login_forget_password)
    TextView mForgetTextView;
    @Bind(R.id.login_name_edt)
    EditText mNameEditText;
    @Bind(R.id.login_password_edt)
    EditText mPasswordEditText;
    @Bind(R.id.login_login_btn)
    Button mLoginBtn;
    @Bind(R.id.login_error_format)
    TextView error_format;
    @Bind(R.id.login_error_pwd)
    TextView error_pwd;
    @Bind(R.id.password_clear_image)
    ImageView mClearPasswordBtn;
    @Bind(R.id.password_show_image)
    ImageView mShowPasswordBtn;
    protected TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            if (!TextUtils.isEmpty(s)) {
                mClearPasswordBtn.setVisibility(View.VISIBLE);
                mShowPasswordBtn.setVisibility(View.VISIBLE);
            } else {
                mClearPasswordBtn.setVisibility(View.GONE);
                mShowPasswordBtn.setVisibility(View.GONE);
            }


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    private String TAG = "LoginActivity";
    private boolean isShowPassword = false;


    private void initView() {
        title.setText(getResources().getString(R.string.login_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        mPasswordEditText.addTextChangedListener(watcher);
        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    if (!TextUtils.isEmpty(v.getText().toString())) {

                    }
                }
                return false;
            }
        });
        initView();
    }

    @OnClick({R.id.navi_back, R.id.login_regist_btn, R.id.login_forget_password, R.id.password_clear_image, R.id.password_show_image, R.id.login_login_btn})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent mIntent;
        switch (id) {
            case R.id.navi_back:
                finish();
                break;
            case R.id.login_regist_btn:
                mIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(mIntent, REGISTER);
                break;
            case R.id.login_forget_password:
                mIntent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivityForResult(mIntent, FORGEG);
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

                    //    mShowPasswordBtn.setImageResource(R.drawable.in_anshul_show_password);
                } else {
                    //    mShowPasswordBtn.setImageResource(R.drawable.in_anshul_hide_password);
                    isShowPassword = true;
                    mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;
            case R.id.login_login_btn:
                String name = null;
                String passwrod = null;
                if (mNameEditText.getText() != null) {
                    name = mNameEditText.getText().toString();
                }
                if (mPasswordEditText.getText() != null) {
                    passwrod = mPasswordEditText.getText().toString();
                }

                if (TextUtils.isEmpty(name)) {
                    mNameEditText.requestFocus();
                    error_format.setVisibility(View.VISIBLE);
                    break;
                }

//                boolean isPhone = CommonUtils.isPhone(name);
//                boolean isEmail = CommonUtils.isEmail(name);

//                if (!isPhone || !isEmail ) {
//                    mNameEditText.setText("");
//                    mNameEditText.requestFocus();
//                    error_format.setVisibility(View.VISIBLE);
//                    break;
//                }

                if (TextUtils.isEmpty(passwrod)) {
                    error_pwd.setVisibility(View.VISIBLE);
                    error_format.setVisibility(View.GONE);
                    mPasswordEditText.requestFocus();
                    break;
                }

                NLog.d(TAG, "登录 start");
                request(OkRequestConstants.CODE_LOGIN);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REGISTER || requestCode == FORGEG) && resultCode == RESULT_OK) {
            CommonToast toast = new CommonToast(LoginActivity.this);
            toast.setAction("激活链接已发送至");
            toast.setMessage("您的邮箱");
            toast.show();
        }
    }

    public void auzTabClicked(View v) {
        int id = v.getId();
        String text = null;
        switch (id) {
            case R.id.login_qq:
                login(QQ.NAME);
                break;
            case R.id.login_wx:
                login(Wechat.NAME);
                break;
            case R.id.login_wb:
                login(SinaWeibo.NAME);
                break;
        }
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(mContext);
        return action.login(mNameEditText.getText().toString(), mPasswordEditText.getText().toString());
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (!TextUtils.isEmpty(result)) {
                ProfileBean profileBean = JsonMananger.jsonToBean(result, ProfileBean.class);
                BaseApplication.setProfileBean(profileBean);
            }
            CommonUtils.hideKeyboard(LoginActivity.this);
            PreferencesUtils.putBoolean(this, ConstantUtils.IS_LOGIN, true);
            setResult(RESULT_OK);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*
* 演示执行第三方登录/注册的方法
* <p>
* 这不是一个完整的示例代码，需要根据您项目的业务需求，改写登录/注册回调函数
*
* @param platformName 执行登录/注册的平台名称，如：SinaWeibo.NAME
*/
    private void login(String platformName) {
        LoginApi api = new LoginApi();
        //设置登陆的平台后执行登陆的方法
        api.setPlatform(platformName);
        api.setOnLoginListener(new OnLoginListener() {
            public boolean onLogin(String platform, HashMap<String, Object> res) {
                // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                // 此处全部给回需要注册
                Toast.makeText(LoginActivity.this, "onLogin", Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onRegister(UserInfo info) {
                // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                return true;
            }
        });
        api.login(this);
    }

}
