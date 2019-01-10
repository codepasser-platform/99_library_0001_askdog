/*
    ShengDao Android Client, SplashActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */
package com.askdog.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.askdog.android.R;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.PreferencesUtils;

/**
 * [预加载页面]
 *
 * @author wyong
 * @version 1.0
 **/
public class SplashActivity extends Activity {

    private final String tag = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) == Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) {
            super.onCreate(savedInstanceState);
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        final View startView = View.inflate(this, R.layout.activity_splash, null);

        setContentView(startView);

        final boolean isFirstRun = PreferencesUtils.getBoolean(SplashActivity.this, ConstantUtils.IS_FIRST_RUN, true);
        // 渐变
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(3000);
        startView.setAnimation(aa);
        aa.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (isFirstRun) {
                    redirectto(GuideActivity.class);
                } else {
                    redirectto(AskDogActivity.class);
                }
            }
        });
    }

    private void redirectto(Class<?> classactivity) {
        Intent intent = new Intent(this, classactivity);
        startActivity(intent);
        finish();
    }
}
