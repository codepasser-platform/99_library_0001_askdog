package com.askdog.android.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.askdog.android.R;
import com.askdog.android.utils.ConstantUtils;


public class PhotoChoiceDialog {

    private Context context;
    private String mCamera;
    private String mAlbum;
    private int type = 0;

    private CameraButtonListener mCameraButtonListener = null;
    private Dialog mCameraDialog;
    /**
     * 对话框按钮监听
     */
    private OnClickListener dialogOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_btn_camera:
                    mCameraButtonListener.onClickBtn(ConstantUtils.CAMERA);
                    break;
                case R.id.dialog_btn_album:
                    mCameraButtonListener.onClickBtn(ConstantUtils.ALBUM);
                    break;
                default:
                    break;
            }

            closeCameraDialog();
        }
    };

    public PhotoChoiceDialog(Context context, CameraButtonListener listener) {
        this.context = context;
        mCameraButtonListener = listener;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        Button timerBtn1 = null;
        Button timerBtn2 = null;
        if (mCameraDialog == null) {
            mCameraDialog = new Dialog(context, R.style.dialog_translucent);
            mCameraDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mCameraDialog.setContentView(R.layout.dialog_camera);
            Window window = mCameraDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);

            timerBtn1 = (Button) mCameraDialog
                    .findViewById(R.id.dialog_btn_camera);
            timerBtn1.setOnClickListener(dialogOnClickListener);

            timerBtn2 = (Button) mCameraDialog
                    .findViewById(R.id.dialog_btn_album);
            timerBtn2.setOnClickListener(dialogOnClickListener);

            Button cancelBtn = (Button) mCameraDialog
                    .findViewById(R.id.dialog_btn_cancel);
            cancelBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    closeCameraDialog();
                }
            });
        }
        mCamera = context.getString(R.string.dialog_string_camera);
        mAlbum = context.getString(R.string.dialog_string_album);
        timerBtn1.setText(mCamera);
        timerBtn2.setText(mAlbum);
    }

    /**
     * 显示对话框
     */
    public void show() {
        mCameraDialog.show();
    }

    /**
     * 关闭对话框
     */
    private void closeCameraDialog() {
        if (mCameraDialog != null && mCameraDialog.isShowing()) {
            mCameraDialog.dismiss();
        }
    }

    public interface CameraButtonListener {
        /**
         * 点击位置
         *
         * @param number 第几个
         */
        public void onClickBtn(int type);
    }
}
