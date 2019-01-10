package com.askdog.android.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.utils.ConstantUtils;

/**
 * Created by Administrator on 2016/8/21.
 */
public class ShareExpDialog {
    private Context context;
    private String mCamera;
    private String mAlbum;
    private int type = 0;

    private ShareExpListener mShareExpListener = null;
    private Dialog mShareDialog;

    public ShareExpDialog(Context context, ShareExpListener listener) {
        this.context = context;
        mShareExpListener = listener;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        ImageView videoBtn = null;
        ImageView wordBtn = null;
        if (mShareDialog == null) {
            mShareDialog = new Dialog(context, R.style.dialog_translucent);
            mShareDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mShareDialog.setContentView(R.layout.dialog_share_exp);
            Window window = mShareDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);

            videoBtn = (ImageView) mShareDialog
                    .findViewById(R.id.share_exp_video);
            videoBtn.setOnClickListener(dialogOnClickListener);

            wordBtn = (ImageView) mShareDialog
                    .findViewById(R.id.share_exp_word);
            wordBtn.setOnClickListener(dialogOnClickListener);

            TextView cancelBtn = (TextView) mShareDialog
                    .findViewById(R.id.share_exp_cancel);
            cancelBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    closeCameraDialog();
                }
            });
        }
        mCamera = context.getString(R.string.dialog_string_camera);
        mAlbum = context.getString(R.string.dialog_string_album);

    }

    /**
     * 显示对话框
     */
    public void show() {
        mShareDialog.show();
    }

    /**
     * 对话框按钮监听
     */
    private View.OnClickListener dialogOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.share_exp_video:
                    mShareExpListener.onClickBtn(ConstantUtils.VIDEO);
                    break;
                case R.id.share_exp_word:
                    mShareExpListener.onClickBtn(ConstantUtils.WORD);
                    break;
                default:
                    break;
            }

            closeCameraDialog();
        }
    };

    /**
     * 关闭对话框
     */
    private void closeCameraDialog() {
        if (mShareDialog != null && mShareDialog.isShowing()) {
            mShareDialog.dismiss();
        }
    }

    public interface ShareExpListener {
        /**
         * 点击位置
         *
         * @param type 第几个
         */
        public void onClickBtn(int type);
    }
}
