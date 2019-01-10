package com.askdog.android.view.dialog;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.askdog.android.R;

/**
 * Created by Administrator on 2016/8/20.
 */
public class AlertDialogDark {

    private Context mContext;

    public AlertDialogDark(Context context) {
        mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showAlertDialog(String message) {
        LinearLayout layout = new LinearLayout(mContext);
        TextView tv = new TextView(mContext);
        tv.setText(message);
        int top = (int) mContext.getResources().getDimension(R.dimen.common_margin15);
        tv.setPadding(0, top, 0, 0);
        tv.setTextSize(18f);
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        layout.addView(tv, pm);

        layout.setGravity(Gravity.CENTER);
        layout.setBackground(mContext.getResources().getDrawable(R.drawable.popup_dialog_bg));
        new AlertDialog.Builder(mContext,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
                .setView(layout)
                .setPositiveButton(
                        mContext.getResources().getString(
                                R.string.alert_dialog_ok), null).show();
    }
}
