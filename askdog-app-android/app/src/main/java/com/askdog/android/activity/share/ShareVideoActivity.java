package com.askdog.android.activity.share;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.activity.ExperiencesDetailActivity;
import com.askdog.android.model.CategoriesInfoBean;
import com.askdog.android.model.ChannelBean;
import com.askdog.android.model.ExperiencesBean;
import com.askdog.android.model.FileUploadSuccessBean;
import com.askdog.android.model.TokenMessageBean;
import com.askdog.android.model.VideoPostBean;
import com.askdog.android.network.okhttp.request.OkHttpUploadProgressRequest;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.AlbumUtils;
import com.askdog.android.utils.BitmapUtils;
import com.askdog.android.utils.CommonUtils;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.NLog;
import com.askdog.android.utils.NToast;
import com.askdog.android.view.dialog.ConfirmDialog;
import com.askdog.android.view.dialog.VideoChoiceDialog;
import com.askdog.android.view.pickerview.OptionsPopupWindow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareVideoActivity extends BaseActivity implements View.OnClickListener, OkHttpUploadProgressRequest.ProgressListener, ConfirmDialog.OnConfirmListener {
    private static final int REQUEST_CAPTURE_VIDEO = 100;
    private static final int CAPTURE_THUMB_ACTIVITY_REQUEST_CODE = 200;
    @Bind(R.id.navi_title)
    protected TextView title;
    @Bind(R.id.share_video_free_or_not)
    protected CheckBox mShowBtn;

    @Bind(R.id.share_video_checkbox1)
    protected CheckBox mCheckBox1Btn;

    @Bind(R.id.share_video_checkbox10)
    protected CheckBox mCheckBox10Btn;
    @Bind(R.id.sharevideo_btn_show_layout)
    protected LinearLayout mBtnShowLayout;
    @Bind(R.id.videoshare_no_image_layout)
    protected LinearLayout mNoimageLayout;
    @Bind(R.id.videoshare_image_layout)
    protected RelativeLayout mImageLayout;
    @Bind(R.id.videoshare_show_image)
    protected ImageView mImageView;
    @Bind(R.id.share_video_progressbar)
    protected ProgressBar mProgressBar;
    @Bind(R.id.share_video_progressbar_text)
    protected TextView mProgressBarText;
    protected TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (!TextUtils.isEmpty(s)) {
                mCheckBox1Btn.setChecked(false);
                mCheckBox10Btn.setChecked(false);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    @Bind(R.id.share_video_subject)
    EditText mVideoSubject;
    @Bind(R.id.navi_right)
    TextView nextBtn;
    @Bind(R.id.share_video_channel_rl)
    RelativeLayout share_video_channel_rl;
    @Bind(R.id.share_video_channel_name)
    TextView channel_name;
    @Bind(R.id.share_video_category_name)
    TextView category_name;
    @Bind(R.id.share_video_price)
    EditText mVideoPrice;
    int number = 0;
    private String TAG = ShareVideoActivity.class.getSimpleName();
    private VideoChoiceDialog mVideoChoiceDialog;
    private String videoPath;
    private OptionsPopupWindow categoryOptions;
    private ArrayList<String> categoryOptionsItems = new ArrayList<>();
    private OptionsPopupWindow channelOptions;
    private ArrayList<String> channelOptionsItems = new ArrayList<>();
    private ChannelBean mChannelBean;
    private ArrayList<CategoriesInfoBean> mCategories;
    private String channelId;
    private String categoryId;
    private FileUploadSuccessBean mUpLoadSuccess;
    private ConfirmDialog mDialog;
    private TokenMessageBean mTokeMessage;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            int progress = msg.arg1;
            mProgressBarText.setText("上传" + progress + "%...");

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_video);
        ButterKnife.bind(this);
        initView();
        // 中间按键图片触发
        mDialog = new ConfirmDialog(this, this);
        request(OkRequestConstants.CODE_USER_CHANNELS_OWNED);
        request(OkRequestConstants.CODE_CATEGORIES_INFO);
    }

    private void initView() {
        Log.v(TAG, "initView  ");
        title.setText(getResources().getString(R.string.activity_share_word_title));
        nextBtn.setText(getResources().getString(R.string.channel_release_btn));
        nextBtn.setTextColor(ContextCompat.getColor(this, R.color.blue));
        nextBtn.setVisibility(View.VISIBLE);
        mVideoPrice.addTextChangedListener(watcher);
        mShowBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mBtnShowLayout.setVisibility(View.VISIBLE);
                } else {
                    mBtnShowLayout.setVisibility(View.GONE);
                    mCheckBox1Btn.setChecked(false);
                    mCheckBox10Btn.setChecked(false);
                    mVideoPrice.setText("");
                }
            }
        });

        mCheckBox1Btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mCheckBox10Btn.setChecked(false);
                }
            }
        });

        mCheckBox10Btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBox1Btn.setChecked(false);
                }
            }
        });
    }

    @OnClick({R.id.navi_back, R.id.videoshare_no_image_layout,
            R.id.videoshare_image_layout, R.id.share_video_category_rl,
            R.id.share_video_channel_rl, R.id.navi_right, R.id.videoshare_cancel_image})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.navi_back:
                CommonUtils.hideKeyboard(ShareVideoActivity.this);
                finish();
                break;
            case R.id.videoshare_image_layout:
            case R.id.videoshare_no_image_layout:
                loadImageFunc();

                break;
            case R.id.share_video_category_rl:

                showCategoryDialog();
                break;
            case R.id.share_video_channel_rl:
                showChannelDialog();
                break;
            case R.id.videoshare_cancel_image:
                mDialog.showDialog("确定删除视频嘛?");
                break;
            case R.id.navi_right:
                if (TextUtils.isEmpty(videoPath)) {
                    NToast.shortToast(this, "请选择视频");
                    break;
                }

                if (!channel_name.isSelected()) {
                    NToast.shortToast(this, "请选择频道");
                    break;
                }
                if (!category_name.isSelected()) {
                    NToast.shortToast(this, "请选择分类");
                    break;
                }
                if (TextUtils.isEmpty(mVideoSubject.getText().toString().trim())) {
                    NToast.shortToast(this, "请输入题目");
                    break;
                }

                if (mUpLoadSuccess == null) {
                    NToast.shortToast(this, "确认视频已经处理完毕");
                    break;
                }
                request(OkRequestConstants.CODE_CREATE_VIDEO);
                break;
            default:
                break;
        }
    }

    private void loadImageFunc() {
        if (mVideoChoiceDialog == null) {
            mVideoChoiceDialog = new VideoChoiceDialog(ShareVideoActivity.this,
                    new VideoChoiceDialog.VideoButtonListener() {
                        @Override
                        public void onClickBtn(int type) {
                            // TODO Auto-generated method stub
                            switch (type) {
                                case 0:
                                    captureVideo();
                                    break;
                                case 1:
                                    startAlbum();
                                    break;
                            }
                        }
                    });
        }
        mVideoChoiceDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "requestCode   " + requestCode + "  " + resultCode);
        switch (requestCode) {
            case REQUEST_CAPTURE_VIDEO:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData(); // 得到Uri
                    String fPath = uri2filePath(uri); // 转化为路径
                    doProcessCaptureImage(fPath);
                }
                break;
            case CAPTURE_THUMB_ACTIVITY_REQUEST_CODE:
                if (RESULT_OK == resultCode) {
                    doProcessAlbumImage(data);
                }
                break;
        }

        //
        if (!TextUtils.isEmpty(videoPath))
            request(OkRequestConstants.CODE_THUMBNAIL_ACCESS_TOKEN, true);
    }

    private String uri2filePath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, proj, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(index);
        return path;
    }

    public void captureVideo() {
        Log.v(TAG, "captureVideo ");
        Intent intent2 = new Intent();
        intent2.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        intent2.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); //设置为低质量
        startActivityForResult(intent2, REQUEST_CAPTURE_VIDEO);
    }

    private void startAlbum() {
        // TODO Auto-generated method stub
        /**
         * 调用相册
         */
        Log.v(TAG, "startAlbum ");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");// 选择视频
        startActivityForResult(intent, CAPTURE_THUMB_ACTIVITY_REQUEST_CODE);
    }

    private void doProcessCaptureImage(String path) {
        // request(OkRequestConstants.CODE_UPLOAD_TO_OSS);
        Log.v(TAG, "doProcessCaptureImage   path   " + path);

        videoPath = path;
        int width = mImageView.getWidth();
        int height = mImageView.getHeight();

        Bitmap map = BitmapUtils.getVideoThumbnail(videoPath, width, height,
                MediaStore.Images.Thumbnails.MINI_KIND);
        mImageView.setImageBitmap(map);
        mNoimageLayout.setVisibility(View.INVISIBLE);
        mImageLayout.setVisibility(View.VISIBLE);

    }

    private void doProcessAlbumImage(Intent data) {


        videoPath = AlbumUtils.getPath(getApplicationContext(), data.getData());

        Log.v(TAG, "doProcessAlbumImage   videoPath   " + videoPath);

        int width = mImageView.getWidth();
        int height = mImageView.getHeight();

        Bitmap map = BitmapUtils.getVideoThumbnail(videoPath, width, height,
                MediaStore.Images.Thumbnails.MINI_KIND);
        mImageView.setImageBitmap(map);
        mNoimageLayout.setVisibility(View.INVISIBLE);
        mImageLayout.setVisibility(View.VISIBLE);

        Log.v(TAG, "map   " + map.getWidth() + "  " + map.getHeight());

    }

    @Override
    public void onProgress(long totalBytes, long remainingBytes, boolean done) {

        int progress = (int) ((totalBytes - remainingBytes) * 100 / totalBytes);
        if (progress != number) {
            mProgressBar.setProgress(progress);
            number = progress;
            Message msg = new Message();
            msg.arg1 = progress;
            mHandler.sendMessageDelayed(msg, 0);
        }
        System.out.print(progress + "%");
        Log.v(TAG, "progress   " + progress + "%");
    }

    private String buildAndSendVideoRelease() {
        VideoPostBean VideoPostBean = new VideoPostBean();
        VideoPostBean.VContent content = new VideoPostBean.VContent();
        content.type = "VIDEO";
        content.video_link_id = mTokeMessage.link_id;
        VideoPostBean.category_id = categoryId;
        VideoPostBean.channel_id = channelId;
        String price;
        if (mShowBtn.isChecked()) {
            //注意：这块的逻辑在输入的时候就已经确定为点选互斥模式
            if (mCheckBox1Btn.isChecked()) {
                price = "1";
            } else if (mCheckBox10Btn.isChecked()) {
                price = "10";
            } else if (TextUtils.isEmpty(mVideoPrice.getText().toString())) {
                price = "0";
            } else {
                price = mVideoPrice.getText().toString();
            }

        } else {
            price = "0";
        }
        VideoPostBean.price = Integer.valueOf(price);
        VideoPostBean.subject = mVideoSubject.getText().toString().trim();
        VideoPostBean.content = content;
        try {
            return JsonMananger.beanToJson(VideoPostBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(this);
        if (requestCode == OkRequestConstants.CODE_THUMBNAIL_ACCESS_TOKEN) {
            mTokeMessage = null;
            String suffix = videoPath.substring(videoPath.lastIndexOf(".") + 1, videoPath.length());
            if (TextUtils.isEmpty(suffix)) {
                suffix = "mp4";
            }
            NLog.d(TAG, suffix);
            return action.getChannelUploadAccessToken(suffix, ConstantUtils.EXPERIENCE_VIDEO);

        } else if (requestCode == OkRequestConstants.CODE_UPLOAD_TO_OSS) {
            if (mTokeMessage != null) {
                Log.v(TAG, "doInBackground   " + mTokeMessage.key);

                return action.uploadFileProgressResponse(videoPath, mTokeMessage.host,
                        mTokeMessage.policy,
                        mTokeMessage.signature,
                        mTokeMessage.callback,
                        mTokeMessage.OSSAccessKeyId,
                        mTokeMessage.key, TAG, this);

            }
        } else if (OkRequestConstants.CODE_USER_CHANNELS_OWNED == requestCode) {
            return action.getMyOwnedChannel(0, 100);
        } else if (OkRequestConstants.CODE_CATEGORIES_INFO == requestCode) {
            return action.getCategoriesInfo();
        } else if (OkRequestConstants.CODE_CREATE_VIDEO == requestCode) {
            String json = buildAndSendVideoRelease();
            return action.postCreateWordExp(json);
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (OkRequestConstants.CODE_USER_CHANNELS_OWNED == requestCode) {
                mChannelBean = JsonMananger.jsonToBean(result, ChannelBean.class);
                buildChannelDialog(mChannelBean);
            } else if (OkRequestConstants.CODE_CATEGORIES_INFO == requestCode) {
                buildCategoryDialog(result);
            } else if (requestCode == OkRequestConstants.CODE_THUMBNAIL_ACCESS_TOKEN) {
                mTokeMessage = JsonMananger.jsonToBean(result, TokenMessageBean.class);
                if (mTokeMessage != null) {
                    //发送本地video到服务器
                    request(OkRequestConstants.CODE_UPLOAD_TO_OSS, true);
                    NLog.d(TAG, mTokeMessage.host);
                }
            } else if (requestCode == OkRequestConstants.CODE_UPLOAD_TO_OSS) {
                mUpLoadSuccess = JsonMananger.jsonToBean(result, FileUploadSuccessBean.class);
            } else if (OkRequestConstants.CODE_CREATE_VIDEO == requestCode) {
//                JsonObject obj = new JsonObject();
                ExperiencesBean bean = JsonMananger.jsonToBean(result, ExperiencesBean.class);
                if (bean != null) {
                    Intent intent = new Intent();
                    intent.setClass(ShareVideoActivity.this, ExperiencesDetailActivity.class);
                    intent.putExtra("authorId", bean.id);
                    intent.putExtra("from", "1");
                    startActivity(intent);
                }
                NLog.d(TAG, result);
            }
        } catch (Exception e) {
            NLog.d("Share", e.toString());
        }

    }

    private void showCategoryDialog() {
        //选项选择器
        categoryOptions.showAtLocation(share_video_channel_rl, Gravity.BOTTOM, 0, 0);
    }

    private void buildCategoryDialog(String result) {
        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<CategoriesInfoBean>>() {
        }.getType();
        mCategories = gson.fromJson(result, type);

        //选项1
        if (mCategories != null) {
            categoryOptions = new OptionsPopupWindow(this);
            int size = mCategories.size();
            for (int i = 0; i < size; i++) {
                CategoriesInfoBean value = mCategories.get(i);
                categoryOptionsItems.add(value.name);
            }
            categoryOptions.setPicker(categoryOptionsItems);
            categoryOptions.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    String tx = categoryOptionsItems.get(options1);
                    categoryId = mCategories.get(options1).id;
                    category_name.setText(tx);
                    category_name.setSelected(true);
                }
            });
        }
    }

    private void showChannelDialog() {
        channelOptions.showAtLocation(share_video_channel_rl, Gravity.BOTTOM, 0, 0);
    }


    private void buildChannelDialog(final ChannelBean mChannel) {
        //选项选择器
        channelOptions = new OptionsPopupWindow(this);

        //选项1
        ArrayList<ChannelBean.ChannelResult> results = mChannel.result;
        for (int i = 0; i < results.size(); i++) {
            ChannelBean.ChannelResult result = results.get(i);
            channelOptionsItems.add(result.name);
        }

        channelOptions.setPicker(channelOptionsItems);
        channelOptions.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = channelOptionsItems.get(options1);
                channelId = mChannelBean.result.get(options1).id;
                channel_name.setText(tx);
                channel_name.setSelected(true);
            }
        });
    }

    @Override
    public void OnConfirmClick() {
        if (action != null) {
            action.cancelRequestByTag();
            number = 0;
            mNoimageLayout.setVisibility(View.VISIBLE);
            mImageLayout.setVisibility(View.INVISIBLE);
        }
    }
}
