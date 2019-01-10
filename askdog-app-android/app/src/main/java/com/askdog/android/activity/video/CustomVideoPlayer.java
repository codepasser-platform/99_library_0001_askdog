package com.askdog.android.activity.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.askdog.android.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class CustomVideoPlayer extends JCVideoPlayerStandard {

    public CustomVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoPlayer(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.jc_layout_custom;
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    public void setUiWitStateAndScreen(int state) {
        super.setUiWitStateAndScreen(state);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }

}
