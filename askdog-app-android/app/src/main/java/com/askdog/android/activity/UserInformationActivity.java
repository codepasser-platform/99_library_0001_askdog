package com.askdog.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.network.service.BaseAction;

public class UserInformationActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
    }

    @Override
    public void onClick(View v) {

    }
}
