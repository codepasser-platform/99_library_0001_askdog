package com.askdog.android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.model.FileUploadSuccessBean;
import com.askdog.android.model.ProfileEditBean;
import com.askdog.android.model.TokenMessageBean;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.AlbumUtils;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.NLog;
import com.askdog.android.utils.NToast;
import com.askdog.android.utils.transformations.glide.CropCircleTransformation;
import com.askdog.android.view.dialog.PhotoChoiceDialog;
import com.askdog.android.view.pickerview.OptionsPopupWindow;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileActivity extends BaseActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_THUMB_ACTIVITY_REQUEST_CODE = 200;

    private static final int REQUEST_CODE_NAME = 301;
    private static final int REQUEST_CODE_PHONE = 302;
    private static final int REQUEST_CODE_OCCUPATION = 303;
    private static final int REQUEST_CODE_SCHOOL = 304;
    private static final int REQUEST_CODE_MAJOR = 305;
    private static final int REQUEST_CODE_PROVINCE = 306;
    private static final int REQUEST_CODE_CITY = 307;
    private static final int REQUEST_CODE_SIGNATURE = 308;

    final ArrayList<String> genderOptionsItems = new ArrayList<>();
    private final String TAG = EditProfileActivity.class.getSimpleName();
    @Bind(R.id.edit_profile_gender_rl)
    RelativeLayout gender_rl;
    @Bind(R.id.navi_title)
    TextView title;
    @Bind(R.id.navi_right)
    TextView navi_right;
    @Bind(R.id.edit_profile_avatar)
    ImageView profile_avatar;
    @Bind(R.id.edit_profile_name)
    TextView name;
    @Bind(R.id.edit_profile_gender)
    TextView gender;
    @Bind(R.id.edit_profile_phone)
    TextView phone;
    @Bind(R.id.edit_profile_occupation)
    TextView occupation;
    @Bind(R.id.edit_profile_school)
    TextView school;
    @Bind(R.id.edit_profile_major)
    TextView major;
    @Bind(R.id.edit_profile_province)
    TextView province;
    @Bind(R.id.edit_profile_city)
    TextView city;
    @Bind(R.id.edit_profile_signature)
    TextView signature;
    private PhotoChoiceDialog mPhotoChoiceDialog;
    private File photoFile;
    private String photopath;
    private TokenMessageBean mTokeMessage;
    private FileUploadSuccessBean mUpLoadSuccess;
    private OptionsPopupWindow genderOptions;

    private ProfileEditBean mProfileEditBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        initView();
        request(OkRequestConstants.CODE_ME_PROFILE_PERSONAL_INFO);
    }

    private void initView() {
        title.setText("个人信息");
        navi_right.setText("保存");
        navi_right.setTextColor(getResources().getColor(R.color.blue));
    }

    private void updateUiView() {
        if (mProfileEditBean == null) return;
        if (!TextUtils.isEmpty(mProfileEditBean.avatar)) {
            updatePorfileAvatar(mProfileEditBean.avatar);
        }
        if (!TextUtils.isEmpty(mProfileEditBean.name)) {
            name.setText(mProfileEditBean.name);
        }

        if (!TextUtils.isEmpty(mProfileEditBean.gender)) {
            if (mProfileEditBean.gender.equals("MALE")) {
                gender.setText("男");
            } else if (mProfileEditBean.gender.equals("FEMALE")) {
                gender.setText("女");
            } else {
                gender.setText("未选择");
            }
        }

        if (!TextUtils.isEmpty(mProfileEditBean.phone)) {
            phone.setText(mProfileEditBean.phone);
        }
        if (!TextUtils.isEmpty(mProfileEditBean.occupation)) {
            occupation.setText(mProfileEditBean.occupation);
        }
        if (!TextUtils.isEmpty(mProfileEditBean.school)) {
            school.setText(mProfileEditBean.school);
        }
        if (!TextUtils.isEmpty(mProfileEditBean.major)) {
            major.setText(mProfileEditBean.major);
        }
        if(mProfileEditBean.address == null){
            ProfileEditBean.Address address = new ProfileEditBean.Address();
            mProfileEditBean.address = address;
        }
        if (!TextUtils.isEmpty(mProfileEditBean.address.province)) {
            province.setText(mProfileEditBean.address.province);
        }
        if (!TextUtils.isEmpty(mProfileEditBean.address.city)) {
            city.setText(mProfileEditBean.address.city);
        }
        if (!TextUtils.isEmpty(mProfileEditBean.signature)) {
            signature.setText(mProfileEditBean.signature);
        }
    }

    @OnClick({R.id.edit_profile_avatar_rl
            , R.id.edit_profile_name_rl
            , R.id.edit_profile_gender_rl
            , R.id.edit_profile_phone_rl
            , R.id.edit_profile_occupation_rl
            , R.id.edit_profile_school_rl
            , R.id.edit_profile_major_rl
            , R.id.edit_profile_province_rl
            , R.id.edit_profile_city_rl
            , R.id.edit_profile_signature_rl
            ,R.id.navi_right
            ,R.id.navi_back
    })
    @Override
    public void onClick(View v) {
        String header = null;
        String editValue = null;
        int resultCode = -1;
        switch (v.getId()) {
            case R.id.edit_profile_avatar_rl:
                loadImageFunc();
                request(OkRequestConstants.CODE_THUMBNAIL_ACCESS_TOKEN);
                break;
            case R.id.edit_profile_name_rl:
                header = "用户名";
                editValue = mProfileEditBean.name;
                resultCode = REQUEST_CODE_NAME;
                break;
            case R.id.edit_profile_gender_rl:
                buildGenderDialog();
                break;
            case R.id.edit_profile_phone_rl:
                header = "手机号码";
                editValue = mProfileEditBean.phone;
                resultCode = REQUEST_CODE_PHONE;
                break;
            case R.id.edit_profile_occupation_rl:
                header = "职业";
                editValue = mProfileEditBean.occupation;
                resultCode = REQUEST_CODE_OCCUPATION;
                break;
            case R.id.edit_profile_school_rl:
                header = "学校";
                editValue = mProfileEditBean.school;
                resultCode = REQUEST_CODE_SCHOOL;
                break;
            case R.id.edit_profile_major_rl:
                header = "专业";
                editValue = mProfileEditBean.major;
                resultCode = REQUEST_CODE_MAJOR;
                break;
            case R.id.edit_profile_province_rl:
                header = "省份";
                editValue = mProfileEditBean.address.province;
                resultCode = REQUEST_CODE_PROVINCE;
                break;
            case R.id.edit_profile_city_rl:
                header = "城市";
                editValue = mProfileEditBean.address.city;
                resultCode = REQUEST_CODE_CITY;
                break;
            case R.id.edit_profile_signature_rl:
                header = "个人签名";
                editValue = mProfileEditBean.signature;
                resultCode = REQUEST_CODE_SIGNATURE;
                break;
            case  R.id.navi_right:
                request(OkRequestConstants.CODE_ME_PUT_PROFILE_PERSONAL_INFO);
                break;
            case R.id.navi_back:
                finish();
                break;
            default:break;
        }

        if (!TextUtils.isEmpty(header)) {
            Intent intent = new Intent();
            intent.setClass(EditProfileActivity.this, EditProfileFillActivity.class);
            intent.putExtra("header", header);
            intent.putExtra("editValue", editValue);
            startActivityForResult(intent, resultCode);
        }
    }

    private void buildGenderDialog() {
        if (genderOptions == null) {
            genderOptions = new OptionsPopupWindow(this);

            genderOptionsItems.add("男");
            genderOptionsItems.add("女");
            genderOptions.setPicker(genderOptionsItems);
            genderOptions.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    String value = genderOptionsItems.get(options1);
                    gender.setText(value);
                    if(options1 == 0){
                        mProfileEditBean.gender = "FEMALE";
                    }else{
                        mProfileEditBean.gender = "MALE";
                    }
                }
            });
        }
        genderOptions.showAtLocation(gender_rl, Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK != resultCode) return;
        String result = "";
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                doProcessCaptureImage();
                break;
            case CAPTURE_THUMB_ACTIVITY_REQUEST_CODE:
                doProcessAlbumImage(data);
                break;
            case REQUEST_CODE_NAME:
                result = data.getStringExtra(ConstantUtils.REQUEST_CODE_VALUE);
                mProfileEditBean.name = result;
                name.setText(result);
                break;
            case REQUEST_CODE_PHONE:
                result = data.getStringExtra(ConstantUtils.REQUEST_CODE_VALUE);
                mProfileEditBean.phone = result;
                phone.setText(result);
                break;
            case REQUEST_CODE_OCCUPATION:
                result = data.getStringExtra(ConstantUtils.REQUEST_CODE_VALUE);
                mProfileEditBean.occupation = result;
                occupation.setText(result);
                break;
            case REQUEST_CODE_SCHOOL:
                result = data.getStringExtra(ConstantUtils.REQUEST_CODE_VALUE);
                mProfileEditBean.school = result;
                school.setText(result);
                break;
            case REQUEST_CODE_MAJOR:
                result = data.getStringExtra(ConstantUtils.REQUEST_CODE_VALUE);
                mProfileEditBean.major = result;
                major.setText(result);
                break;
            case REQUEST_CODE_PROVINCE:
                result = data.getStringExtra(ConstantUtils.REQUEST_CODE_VALUE);
                mProfileEditBean.address.province  = result;
                province.setText(result);
                break;
            case REQUEST_CODE_CITY:
                result = data.getStringExtra(ConstantUtils.REQUEST_CODE_VALUE);
                mProfileEditBean.address.city = result;;
                city.setText(result);
                break;
            case REQUEST_CODE_SIGNATURE:
                result = data.getStringExtra(ConstantUtils.REQUEST_CODE_VALUE);
                mProfileEditBean.signature = result;
                signature.setText(result);
                break;
        }
    }

    private void doProcessCaptureImage() {
        photopath = photoFile.getPath();
        if (mTokeMessage != null) {
            request(OkRequestConstants.CODE_UPLOAD_TO_OSS);
            NLog.d(TAG, mTokeMessage.host);
        }
        updatePorfileAvatar(photopath);
    }

    private void doProcessAlbumImage(Intent data) {
        photopath = AlbumUtils.getPath(getApplicationContext(), data.getData());
        if (mTokeMessage != null) {
            request(OkRequestConstants.CODE_UPLOAD_TO_OSS);
            NLog.d(TAG, mTokeMessage.host);
        }
        updatePorfileAvatar(photopath);
    }

    private void loadImageFunc() {
        if (mPhotoChoiceDialog == null) {
            mPhotoChoiceDialog = new PhotoChoiceDialog(EditProfileActivity.this,
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

    private void updatePorfileAvatar(String path) {
        Glide.with(mContext)
                .load(path)
                .placeholder(R.drawable.avatar_default)
                .error(R.drawable.avatar_default)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(profile_avatar);
    }

    /**
     * 一下为网络部分
     */
    @Override
    public Object doInBackground(int requestCode) throws Exception {
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
        } else if (requestCode == OkRequestConstants.CODE_ME_PROFILE_PERSONAL_INFO) {
            return action.getMeProfilePersonalInfo();
        } else if(requestCode == OkRequestConstants.CODE_ME_PUT_PROFILE_PERSONAL_INFO){
            return action.putMeProfilePersonalInfo(mProfileEditBean);
        } else if(requestCode == OkRequestConstants.CODE_ME_PROFILE_AVATAR){
            return action.putMeProfileAvatar(mUpLoadSuccess.linkId);
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
                request(OkRequestConstants.CODE_ME_PROFILE_AVATAR);
            } else if (requestCode == OkRequestConstants.CODE_ME_PROFILE_PERSONAL_INFO) {
                mProfileEditBean = JsonMananger.jsonToBean(result, ProfileEditBean.class);
                updateUiView();
            } else if(requestCode == OkRequestConstants.CODE_ME_PUT_PROFILE_PERSONAL_INFO){
                setResult(RESULT_OK);
                finish();
            } else if(requestCode == OkRequestConstants.CODE_ME_PROFILE_AVATAR){
NLog.d("123",result);
            }
        } catch (Exception e) {
            NLog.d(TAG, e.toString());
        }
    }
}
