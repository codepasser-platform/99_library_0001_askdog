package com.askdog.android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.model.FileUploadSuccessBean;
import com.askdog.android.model.TokenMessageBean;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.AlbumUtils;
import com.askdog.android.utils.CommonUtils;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.MyProgressDialog;
import com.askdog.android.utils.NLog;
import com.askdog.android.utils.NToast;
import com.askdog.android.utils.transformations.glide.CropSquareTransformation;
import com.askdog.android.view.dialog.PhotoChoiceDialog;
import com.askdog.android.view.dialog.CommonToast;
import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateChannelActivity extends BaseActivity {
    private final String TAG = CreateChannelActivity.class.getSimpleName();
    @Bind(R.id.navi_title)
    TextView title;
    @Bind(R.id.navi_right)
    TextView confirmBtn;
    @Bind(R.id.channel_upload_thumbnail)
    ImageView thumbnail;
    @Bind(R.id.create_channel_title)
    EditText channel_title;
    @Bind(R.id.create_channel_content)
    EditText channel_content;
    private PhotoChoiceDialog mPhotoChoiceDialog;


    private File photoFile;
    private TokenMessageBean mTokeMessage;
    private FileUploadSuccessBean mUpLoadSuccess;
    private String photopath;

    private boolean releaseSuccess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_channel);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText(getResources().getString(R.string.activity_create_channel_title));
        confirmBtn.setText(getResources().getString(R.string.alert_dialog_ok));
        confirmBtn.setTextColor(ContextCompat.getColor(this, R.color.blue));
        confirmBtn.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.navi_back, R.id.navi_right, R.id.channel_upload_cardview})
    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId) {
            case R.id.navi_back:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.navi_right:
                String e_title = channel_title.getText().toString().trim();
                if (TextUtils.isEmpty(e_title)) {
                    channel_title.requestFocus();
                    NToast.shortToast(this, "请输入题目");
                    break;
                }
                String e_content = channel_content.getText().toString().trim();
                if (TextUtils.isEmpty(e_content)) {
                    channel_content.requestFocus();
                    NToast.shortToast(this, "请输入内容");
                    break;
                }
                uploadChannelInfo();
                CommonUtils.hideKeyboard(CreateChannelActivity.this);
                break;
            case R.id.channel_upload_cardview:
                loadImageFunc();
                request(OkRequestConstants.CODE_THUMBNAIL_ACCESS_TOKEN);
                break;
        }
    }

    private void uploadChannelInfo() {
        //TODO
        request(OkRequestConstants.CODE_BUILD_CHANNEL);
    }

    private void loadImageFunc() {
        if (mPhotoChoiceDialog == null) {
            mPhotoChoiceDialog = new PhotoChoiceDialog(CreateChannelActivity.this,
                    new PhotoChoiceDialog.CameraButtonListener() {
                        @Override
                        public void onClickBtn(int type) {
                            // TODO Auto-generated method stub

                            switch (type) {
                                case ConstantUtils.CAMERA:
                                    startCamera();
                                    break;
                                case ConstantUtils.ALBUM:
                                    startAlbum();
                                    break;
                            }
                        }
                    });
        }
        mPhotoChoiceDialog.show();
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // create a file to save the image
        photoFile = getOutputMediaFile();
        // 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null，如果此处指定，则后来的data为null
        // set the image file name
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private void startAlbum() {
        // TODO Auto-generated method stub
        /**
         * 调用相册
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");// 相片类型
        startActivityForResult(intent, CAPTURE_THUMB_ACTIVITY_REQUEST_CODE);
    }



    /**
     * Create a File for saving an image
     */
    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = null;
        try {
            // This location works best if you want the created images to beshared
            // between applications and persist after your app has beenuninstalled.
            mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "AskDog");

        } catch (Exception e) {
            e.printStackTrace();

        }

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if (RESULT_OK == resultCode) {
                    doProcessCaptureImage();
                }
                break;
            case CAPTURE_THUMB_ACTIVITY_REQUEST_CODE:
                if (RESULT_OK == resultCode) {
                    doProcessAlbumImage(data);
                }
                break;
        }
    }

    private void doProcessCaptureImage() {
        photopath = photoFile.getPath();
        if (mTokeMessage != null) {
            request(OkRequestConstants.CODE_UPLOAD_TO_OSS);
            NLog.d(TAG, mTokeMessage.host);
        }
        Glide.with(mContext)
                .load(photopath)
                .placeholder(R.drawable.channel_default_diagram)
                .error(R.drawable.channel_default_diagram)
                .bitmapTransform(new CropSquareTransformation(mContext))
                .centerCrop()
                .into(thumbnail);
    }

    private void doProcessAlbumImage(Intent data) {
        photopath = AlbumUtils.getPath(getApplicationContext(), data.getData());
        if (mTokeMessage != null) {
            request(OkRequestConstants.CODE_UPLOAD_TO_OSS);
            NLog.d(TAG, mTokeMessage.host);
        }
        Glide.with(mContext)
                .load(photopath)
                .placeholder(R.drawable.channel_default_diagram)
                .error(R.drawable.channel_default_diagram)
                .bitmapTransform(new CropSquareTransformation(mContext))
                .centerCrop()
                .into(thumbnail);
    }

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_THUMB_ACTIVITY_REQUEST_CODE = 200;

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(this);
        if (requestCode == OkRequestConstants.CODE_THUMBNAIL_ACCESS_TOKEN) {
            return action.getChannelUploadAccessToken("jpg", ConstantUtils.CHANNEL_THUMBNAIL);
        } else if (requestCode == OkRequestConstants.CODE_UPLOAD_TO_OSS) {
            if (mTokeMessage != null) {
                return action.uploadFile2OSS(photopath, mTokeMessage.host,
                        mTokeMessage.policy,
                        mTokeMessage.signature,
                        mTokeMessage.callback,
                        mTokeMessage.OSSAccessKeyId,
                        mTokeMessage.key);
            }
        } else if (requestCode == OkRequestConstants.CODE_BUILD_CHANNEL) {
            String name = channel_title.getText().toString().trim();
            String description = channel_content.getText().toString().trim();
            if(photopath == null){
                return action.postBuildChannel(name, description, "");
            }
            if (mUpLoadSuccess != null)
                return action.postBuildChannel(name, description, mUpLoadSuccess.linkId);

        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode,result);
        try {
            if (requestCode == OkRequestConstants.CODE_THUMBNAIL_ACCESS_TOKEN) {
                mTokeMessage = JsonMananger.jsonToBean(result, TokenMessageBean.class);

            } else if (requestCode == OkRequestConstants.CODE_UPLOAD_TO_OSS) {
                mUpLoadSuccess = JsonMananger.jsonToBean(result, FileUploadSuccessBean.class);
            } else if (requestCode == OkRequestConstants.CODE_BUILD_CHANNEL) {

                releaseSuccess = true;
                setResult(RESULT_OK);
                finish();
            }
            NLog.d(TAG, mUpLoadSuccess.url);
        } catch (Exception e) {
            NLog.d(TAG, e.toString());
        }

    }
}
