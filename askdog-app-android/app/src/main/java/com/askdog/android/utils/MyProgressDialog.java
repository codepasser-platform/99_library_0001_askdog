/**
 * @File:AlertDialog.java
 * @Note:
 * @Author:liu.bo.neu@neusoft.com
 * @DateTime:2014-6-6
 * @History:
 */
package com.askdog.android.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;

import com.askdog.android.R;

public class MyProgressDialog {
    private ProgressDialog mProgressDialog;

    public MyProgressDialog(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getResources().getString(R.string.dialog_loading));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        mProgressDialog.setCancelable(true);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public MyProgressDialog(Context context, String message) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public boolean isShowing() {
        if (mProgressDialog == null)
            return false;
        return mProgressDialog.isShowing();
    }

    public void show(boolean show) {
        if (show) {
            if (mProgressDialog != null) {
                mProgressDialog.show();
            }

        } else {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
//                mProgressDialog = null;
            }
        }

    }

    public void setOnCancelListener(OnCancelListener listener) {
        mProgressDialog.setOnCancelListener(listener);
    }
}
