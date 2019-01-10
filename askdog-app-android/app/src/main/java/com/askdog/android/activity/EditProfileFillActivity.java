package com.askdog.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.NToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileFillActivity extends BaseActivity {
    @Bind(R.id.navi_title)
    TextView title;
    @Bind(R.id.edit_profile_fill_header)
    TextView fill_header;
    @Bind(R.id.edit_profile_fill_value)
    EditText value;
    private String header;
    private String editValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_fill);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        header = getIntent().getStringExtra("header");
        editValue = getIntent().getStringExtra("editValue");
        if(!TextUtils.isEmpty(header)){
            fill_header.setText(header);
        }
        if(!TextUtils.isEmpty(editValue)){
            value.setText(editValue);
        }else{
            value.setHint("请输入"+header);
        }
        if(header.equalsIgnoreCase("手机号码")){
            value.setInputType(InputType.TYPE_CLASS_PHONE);
            value.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11) {
            }});
        }
    }

    private void initView() {
        title.setText("编辑信息");
    }

    @OnClick({R.id.navi_back, R.id.edit_profile_fill_save_btn,R.id.edit_profile_fill_delete})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navi_back:
                finish();
                break;
            case R.id.edit_profile_fill_save_btn:
                String textValue = value.getText().toString().trim();
                if(!TextUtils.isEmpty(textValue)){
                    Intent intent = new Intent();
                    intent.putExtra(ConstantUtils.REQUEST_CODE_VALUE, textValue);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    value.requestFocus();
                    NToast.shortToast(this,"内容不能为空");
                }
                break;
            case R.id.edit_profile_fill_delete:
                value.setText("");
                break;
        }
    }


}
