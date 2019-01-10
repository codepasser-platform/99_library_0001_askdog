package com.askdog.android.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.askdog.android.R;
import com.askdog.android.utils.ConstantUtils;

/**
 * Created by Administrator on 2016/9/1.
 */
public class DeleteConfirmDialog {
    private Context context;
    private String mOK;
    private int type = 0;

    private ButtonListener mButtonListener = null;
    private Dialog mDeleteDialog;
    /**
     * 对话框按钮监听
     */
    private View.OnClickListener dialogOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete_dialog_btn_ok:
                    mButtonListener.onClickBtn();
                    break;
                default:
                    break;
            }

            closeCameraDialog();
        }
    };

    public DeleteConfirmDialog(Context context, ButtonListener listener) {
        this.context = context;
        mButtonListener = listener;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        Button mConfirm = null;
        if (mDeleteDialog == null) {
            mDeleteDialog = new Dialog(context, R.style.dialog_translucent);
            mDeleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDeleteDialog.setContentView(R.layout.dialog_delete_comfirm);
            Window window = mDeleteDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);

            mConfirm = (Button) mDeleteDialog
                    .findViewById(R.id.delete_dialog_btn_ok);
            mConfirm.setOnClickListener(dialogOnClickListener);


            Button cancelBtn = (Button) mDeleteDialog
                    .findViewById(R.id.delete_dialog_btn_cancel);
            cancelBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    closeCameraDialog();
                }
            });
        }
        mOK = context.getString(R.string.alert_delete_channel);
        mConfirm.setText(mOK);
    }

    /**
     * 显示对话框
     */
    public void show() {
        mDeleteDialog.show();
    }

    /**
     * 关闭对话框
     */
    private void closeCameraDialog() {
        if (mDeleteDialog != null && mDeleteDialog.isShowing()) {
            mDeleteDialog.dismiss();
        }
    }

    public interface ButtonListener {
        /**
         * 点击位置
         */
        public void onClickBtn();
    }
}
