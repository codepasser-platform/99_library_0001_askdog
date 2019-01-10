package com.askdog.android.view.dialog;


import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.askdog.android.R;

public class AlertDialogOk {
	private Context mContext;

	public AlertDialogOk(Context context) {
		mContext = context;
	}

	public void showAlertDialog(String message) {
		LinearLayout layout = new LinearLayout(mContext);
		TextView tv = new TextView(mContext);
		tv.setText(message);
		int top = (int) mContext.getResources().getDimension(R.dimen.common_margin15);
		tv.setPadding(0, top, 0, 0);
		tv.setTextSize(18f);
		LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layout.addView(tv, pm);

		layout.setGravity(Gravity.CENTER);
		new AlertDialog.Builder(mContext)
				.setView(layout)
				.setPositiveButton(
						mContext.getResources().getString(
								R.string.alert_dialog_ok), null).show();
	}
}
