package com.askdog.android.activity.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.model.FileUploadSuccessBean;
import com.askdog.android.model.TokenMessageBean;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.AlbumUtils;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.NLog;
import com.askdog.android.utils.NToast;
import com.askdog.android.view.dialog.ConfirmDialog;
import com.askdog.android.view.dialog.PhotoChoiceDialog;
import com.askdog.android.view.searchview.ImageAndTextEditor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareWordOneActivity extends BaseActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_THUMB_ACTIVITY_REQUEST_CODE = 200;
    private final String TAG = ShareWordOneActivity.class.getSimpleName();
    @Bind(R.id.navi_title)
    TextView title;
    @Bind(R.id.navi_right)
    TextView nextBtn;
    @Bind(R.id.share_word_title)
    EditText word_title;
    @Bind(R.id.share_word_content)
    ImageAndTextEditor word_content;
    private File photoFile;
    private PhotoChoiceDialog mPhotoChoiceDialog;
    private String photopath;
    private TokenMessageBean mTokeMessage;
    private FileUploadSuccessBean mUpLoadSuccess;
    private ConfirmDialog mDialog;
    private Map<String, FileUploadSuccessBean> mUrlMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_word);
        ButterKnife.bind(this);
        initView();
        mDialog = new ConfirmDialog(ShareWordOneActivity.this);
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
//        word_content.insertBitmap(photopath);
    }


    private void doProcessAlbumImage(Intent data) {
        photopath = AlbumUtils.getPath(getApplicationContext(), data.getData());
        if (mTokeMessage != null) {
            request(OkRequestConstants.CODE_UPLOAD_TO_OSS);
            NLog.d(TAG, mTokeMessage.host);
        }
//        word_content.insertBitmap(photopath);
    }

    private void initView() {
        title.setText(getResources().getString(R.string.activity_share_word_title));
        nextBtn.setText(getResources().getString(R.string.next_step_label));
        nextBtn.setTextColor(ContextCompat.getColor(this, R.color.black));
        nextBtn.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.navi_back, R.id.share_word_insert_pic, R.id.navi_right})
    @Override
    public void onClick(View v) {
        int vid = v.getId();
        switch (vid) {
            case R.id.navi_back:
                mDialog.showDialog();
                break;
            case R.id.share_word_insert_pic:
                loadImageFunc();
                request(OkRequestConstants.CODE_THUMBNAIL_ACCESS_TOKEN, true);
                break;
            case R.id.navi_right:
                if (TextUtils.isEmpty(word_content.getText())) {
                    NToast.shortToast(this,getResources().getString(R.string.please_input_title));
                    word_title.requestFocus();
                    break;
                }
                if (TextUtils.isEmpty(word_title.getText())) {
                    NToast.shortToast(this,getResources().getString(R.string.please_input_content));
                    word_title.requestFocus();
                    break;
                }
                if (mUrlMap == null) {
                    NToast.shortToast(this, "请插入图片");
                    break;
                }
                buildNextStep();
                break;
        }
    }

    private void buildNextStep(){
        Intent intent = new Intent();
        List<String> valueList = word_content.getContentList();
        StringBuilder sbValue = new StringBuilder();
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < valueList.size(); i++) {
            String key = valueList.get(i);
            if (!TextUtils.isEmpty(key)) {
                FileUploadSuccessBean value = mUrlMap.get(key);
                if (value != null) {
                    sbValue.append("![](" + value.url + ")");
                    ids.add(value.linkId);
                } else {
                    sbValue.append(key);
                }
            }
        }
        intent.putExtra("subject", word_title.getText().toString().trim());
        intent.putExtra("content", sbValue.toString());
        intent.putStringArrayListExtra("picture_link_ids", ids);
        intent.setClass(ShareWordOneActivity.this, ShareWordTwoActivity.class);
        startActivity(intent);
    }

    private void loadImageFunc() {
        if (mPhotoChoiceDialog == null) {
            mPhotoChoiceDialog = new PhotoChoiceDialog(ShareWordOneActivity.this,
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
        photoFile = AlbumUtils.getOutputMediaFile();
        // 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null，如果此处指定，则后来的data为null
        // set the image file name
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private void startAlbum() {
        /**
         * 调用相册
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");// 相片类型
        startActivityForResult(intent, CAPTURE_THUMB_ACTIVITY_REQUEST_CODE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (requestCode == OkRequestConstants.CODE_THUMBNAIL_ACCESS_TOKEN) {
                mTokeMessage = JsonMananger.jsonToBean(result, TokenMessageBean.class);
            } else if (requestCode == OkRequestConstants.CODE_UPLOAD_TO_OSS) {
                mUpLoadSuccess = JsonMananger.jsonToBean(result, FileUploadSuccessBean.class);
                if (mUrlMap == null) {
                    mUrlMap = new HashMap<>();
                }
                mUrlMap.put(photopath, mUpLoadSuccess);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        word_content.insertBitmap(photopath);
                        mTokeMessage = null;//清空上一次的message
                    }
                });
                NLog.d(TAG, mUpLoadSuccess.url);
            }
        } catch (Exception e) {
            NLog.d(TAG, e.toString());
        }
    }
}
