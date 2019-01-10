package com.askdog.android.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.utils.CommonUtils;

/**
 * Created by Administrator on 2016/8/21.
 */
public class ConfirmDialog {
    public static DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };
    private Activity activity;
    private AlertDialog dialog;
    private OnConfirmListener listener;

    public ConfirmDialog(Activity activity) {
        this.activity = activity;
    }

    public ConfirmDialog(Activity activity, OnConfirmListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public void showDialog() {
        dialog = new AlertDialog.Builder(activity).create();
        //点击外部区域不能取消dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setOnKeyListener(keylistener);
        dialog.show();

        Window window = dialog.getWindow();
        window.setContentView(R.layout.my_dialog);
        TextView tv_title = (TextView) window.findViewById(R.id.dialog_title);
//        tv_title.setText(title);
        TextView tv_confirm = (TextView) window.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) window.findViewById(R.id.tv_cancel);
        tv_confirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                CommonUtils.hideKeyboard(activity);
                activity.finish();
            }
        });

        tv_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    public void showDialog(String message) {
        dialog = new AlertDialog.Builder(activity).create();
        //点击外部区域不能取消dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setOnKeyListener(keylistener);
        dialog.show();

        Window window = dialog.getWindow();
        window.setContentView(R.layout.my_dialog);
        TextView tv_titleTop = (TextView) window.findViewById(R.id.dialog_first_line);
        tv_titleTop.setVisibility(View.GONE);
        TextView tv_title = (TextView) window.findViewById(R.id.dialog_title);
        tv_title.setText(message);
        TextView tv_confirm = (TextView) window.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) window.findViewById(R.id.tv_cancel);

        tv_confirm.setTag(dialog);
        tv_confirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                listener.OnConfirmClick();
            }
        });

        tv_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    public interface OnConfirmListener {
        public void OnConfirmClick();
    }
}
