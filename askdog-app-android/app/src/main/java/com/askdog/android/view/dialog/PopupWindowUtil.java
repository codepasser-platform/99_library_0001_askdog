package com.askdog.android.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.askdog.android.R;
import com.askdog.android.activity.share.ShareVideoActivity;
import com.askdog.android.activity.share.ShareWordOneActivity;

/**
 * Created by lisong on 2016/8/9.
 */
public class PopupWindowUtil {

    public static void showPopuWindowUtil(final Context context) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.popupwindow_layout, null);
        // 设置按钮的点击事件

        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popupWindow.setAnimationStyle(R.style.dialog_translucent);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        //  popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
        //            R.drawable.selectmenu_bg_downward));

        View view = new View(context);
        view.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
        view.setBackgroundColor(Color.BLUE);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
        LinearLayout button = (LinearLayout) contentView.findViewById(R.id.upload_main_back_layout);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ImageView video = (ImageView) contentView.findViewById(R.id.popup_video_btn);
        video.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(context, ShareVideoActivity.class);
                context.startActivity(i);
                popupWindow.dismiss();

            }
        });
        ImageView word = (ImageView) contentView.findViewById(R.id.popup_audio_btn);
        word.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(context, ShareWordOneActivity.class);
                context.startActivity(i);
                popupWindow.dismiss();
            }
        });
    }
}
