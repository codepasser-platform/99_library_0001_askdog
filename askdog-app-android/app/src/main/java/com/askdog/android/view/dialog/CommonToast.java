package com.askdog.android.view.dialog;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.askdog.android.BaseApplication;
import com.askdog.android.R;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CommonToast {
    public static final long DURATION_LONG = 5000L;
    public static final long DURATION_MEDIUM = 3500L;
    public static final long DURATION_SHORT = 2500L;
    Activity activity;
    private long _duration = 3500l;
    private ToastView _toastVw;

    public CommonToast(Activity activity) {
        this.activity = activity;
        init(activity);
    }

    public CommonToast(Activity activity, String message, int icon,
                       String action, int actionIcon, long l) {
        _duration = l;
        init(activity);
        setMessage(message);
        setMessageIc(icon);
        setAction(action);
        setActionIc(actionIcon);
    }

    public static float dpToPixel(float dp) {
        return dp * (getDisplayMetrics().densityDpi / 160F);
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) BaseApplication.context().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(
                displaymetrics);
        return displaymetrics;
    }

    private void init(Activity activity) {
        _toastVw = new ToastView(activity);
        setLayoutGravity(81);
    }

    public long getDuration() {
        return _duration;
    }

    public void setDuration(long l) {
        _duration = l;
    }

    public void setAction(String s) {
        _toastVw.actionTv.setVisibility(View.VISIBLE);
        _toastVw.actionTv.setText(s);
    }

    public void setActionIc(int i) {
        _toastVw.actionIv.setImageResource(i);
    }

    public void setLayoutGravity(int i) {
        if (i != 0) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2,
                    -2);
            params.gravity = Gravity.CENTER;
            ;
            _toastVw.setLayoutParams(params);
        }
    }

    public void setMessage(String s) {
        _toastVw.messageTv.setText(s);
    }

    public void setMessageIc(int i) {
        _toastVw.messageIc.setImageResource(i);
    }

    public void show() {
        final ViewGroup content = (ViewGroup) ((Activity) _toastVw.getContext())
                .findViewById(android.R.id.content);
        if (content != null) {
            ObjectAnimator.ofFloat(_toastVw, "alpha", 0.0F).setDuration(0L)
                    .start();
            content.addView(_toastVw);
            ObjectAnimator.ofFloat(_toastVw, "alpha", 0.0F, 1.0F)
                    .setDuration(167L).start();
            _toastVw.postDelayed(new Runnable() {

                @Override
                public void run() {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(_toastVw,
                            "alpha", 1.0F, 0.0F);
                    animator.setDuration(100L);
                    animator.addListener(new AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            content.removeView(_toastVw);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }
                    });
                    animator.start();
                }
            }, _duration);
        } else {

        }
    }

    private class ToastView extends FrameLayout {

        public ImageView actionIv;
        public TextView actionTv;
        public ImageView messageIc;
        public TextView messageTv;

        public ToastView(Context context) {
            this(context, null);
        }

        public ToastView(Context context, AttributeSet attributeset) {
            this(context, attributeset, 0);
        }

        public ToastView(Context context, AttributeSet attributeset, int i) {
            super(context, attributeset, i);
            init();
        }

        private void init() {
            LayoutInflater.from(getContext()).inflate(
                    R.layout.view_base_toast, this, true);
            messageTv = (TextView) findViewById(R.id.title_tv);
            messageIc = (ImageView) findViewById(R.id.icon_iv);
            actionTv = (TextView) findViewById(R.id.action_tv);
            actionIv = (ImageView) findViewById(R.id.icon_iv);
        }
    }
}
