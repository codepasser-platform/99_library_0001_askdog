package com.askdog.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SubscribeChannelActivity extends BaseActivity {

    @Bind(R.id.loading)
    ImageView loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_channel);
        ButterKnife.bind(this);
        showLoading();
    }


    private void showLoading(){
        String url = "file:///android_asset/loading_white.gif";
        Glide.with(this)
                .load("http://img0.imgtn.bdimg.com/it/u=3229897698,2043497950&fm=21&gp=0.jpg")
                .into(loading);
    }

    long lasttime = 0;
    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if (time - lasttime < 2000*1000){
            super.onBackPressed();
        }
        lasttime = time;
        loading.setVisibility(View.GONE);
    }
}
