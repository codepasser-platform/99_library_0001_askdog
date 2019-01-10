package com.askdog.android.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.askdog.android.BaseApplication;
import com.askdog.android.BaseFragment;
import com.askdog.android.R;
import com.askdog.android.activity.AskDogActivity;
import com.askdog.android.activity.EditProfileActivity;
import com.askdog.android.activity.MyOwnedChannelActivity;
import com.askdog.android.activity.MySubscribedChannelActivity;
import com.askdog.android.activity.MyViewHistoryActivity;
import com.askdog.android.activity.cash.MyCashActivity;
import com.askdog.android.activity.setting.SecurityMenuActivity;
import com.askdog.android.activity.setting.SystemSettingActivity;
import com.askdog.android.adapter.MeRecycleViewAdapter;
import com.askdog.android.interf.OnTabSelectListener;
import com.askdog.android.model.MeBean;
import com.askdog.android.model.ProfileBean;
import com.askdog.android.model.ProfileEditBean;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.transformations.glide.CropCircleTransformation;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener, OnTabSelectListener, MeRecycleViewAdapter.OnMyRecyclerViewItemClickListener {


    private static final int EDITPROFILE = 99;
    @Bind(R.id.me_avatar)
    ImageView me_avatar;
    @Bind(R.id.me_name)
    TextView me_name;
    @Bind(R.id.me_editor)
    ImageView me_editor;
    @Bind(R.id.me_signature)
    TextView signature;
    @Bind(R.id.me_vip)
    ImageView vip;
    @Bind(R.id.me_recycle_view)
    RecyclerView me_recycle_view;
    @Bind(R.id.navi_title)
    TextView navi_title;
    @Bind(R.id.navi_back)
    ImageView navi_back;
    String[] me_titles;
    int[] me_icons = {R.drawable.ic_profile_my_channel, R.drawable.ic_profile_subscribe, R.drawable.ic_profile_browsing, R.drawable.ic_profile_income, R.drawable.ic_profile_safe, R.drawable.ic_profile_system};
    private View mCurrentView;
    private MeRecycleViewAdapter adapter;
    private List<MeBean> dataList;
    private boolean isLogin;
    private ProfileBean profileBean;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request(OkRequestConstants.CODE_ME_PROFILE_PERSONAL_INFO);
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mCurrentView == null) {
            mCurrentView = inflater.inflate(R.layout.fragment_me, container, false);
        }
        return mCurrentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AskDogActivity) getActivity()).showHeaderView(false);
    }

    @Override
    public void initView(View view) {
        profileBean = BaseApplication.getProfileBean();
        navi_back.setVisibility(View.GONE);
        navi_title.setText(getResources().getString(R.string.fragment_my_title));
        me_recycle_view.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        me_recycle_view.setLayoutManager(layoutManager);
        adapter = new MeRecycleViewAdapter(getActivity(), dataList, this);
        me_recycle_view.setAdapter(adapter);
        if (TextUtils.isEmpty(profileBean.avatar)) {
            me_avatar.setImageResource(R.drawable.avatar_default);
        } else {
            Glide.with(mContext)
                    .load(profileBean.avatar)
                    .placeholder(R.drawable.avatar_default)
                    .error(R.drawable.avatar_default)
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .into(me_avatar);
        }

        if (!TextUtils.isEmpty(profileBean.signature)) {
            signature.setText(profileBean.signature);
        }
        if (!TextUtils.isEmpty(profileBean.signature) && profileBean.vip) {
            vip.setVisibility(View.VISIBLE);
        }
        me_name.setText(profileBean.name);
        me_editor.setImageResource(R.drawable.ic_detail_edit);
    }

    @Override
    public void onItemClick(View view, int pos) {
        Intent i = new Intent();
        switch (pos) {
            case 0:
                i.setClass(getActivity(), MyOwnedChannelActivity.class);
                ((AskDogActivity) getActivity()).startActivity(i);
                break;
            case 1:
                i.setClass(getActivity(), MySubscribedChannelActivity.class);
                ((AskDogActivity) getActivity()).startActivity(i);
                break;
            case 2:
                i.setClass(getActivity(), MyViewHistoryActivity.class);
                ((AskDogActivity) getActivity()).startActivity(i);
                break;
            case 3:
                i.setClass(getActivity(), MyCashActivity.class);
                ((AskDogActivity) getActivity()).startActivity(i);
                break;
            case 4:
                i.setClass(getActivity(), SecurityMenuActivity.class);
                ((AskDogActivity) getActivity()).startActivity(i);
                break;
            case 5:
                i.setClass(getActivity(), SystemSettingActivity.class);
                ((AskDogActivity) getActivity()).startActivity(i);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDITPROFILE && resultCode == Activity.RESULT_OK) {
            String uName = data.getStringExtra("name");
            String uAvatar = data.getStringExtra("avatar");
            String uSignature = data.getStringExtra("signature");
            updateHeaderUi(uName, uAvatar, uSignature);
        }
    }

    @Override
    public void initData() {
        me_titles = getResources().getStringArray(R.array.me_title);
        dataList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            MeBean bean = new MeBean();
            bean.setMe_item_icon(me_icons[i]);
            bean.setMe_item_name(me_titles[i]);
            dataList.add(bean);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.me_editor})
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.me_editor:
                Intent intent = new Intent();
                intent.setClass(mContext, EditProfileActivity.class);
                ((AskDogActivity) getActivity()).startActivityForResult(intent, EDITPROFILE);
                break;
        }
    }

    private void updateHeaderUi(String uName, String uAvatar, String uSignature) {
        if (!TextUtils.isEmpty(uAvatar)) {
            Glide.with(mContext)
                    .load(uAvatar)
                    .placeholder(R.drawable.avatar_default)
                    .error(R.drawable.avatar_default)
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .into(me_avatar);

        }else {
            me_avatar.setImageResource(R.drawable.avatar_default);
        }

        if (!TextUtils.isEmpty(uSignature)) {
            signature.setText(uSignature);
        }

        if (!TextUtils.isEmpty(uName))
            me_name.setText(uName);
    }

    /**
     *
     */
    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (requestCode == OkRequestConstants.CODE_ME_PROFILE_PERSONAL_INFO) {
            return action.getMeProfilePersonalInfo();
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (requestCode == OkRequestConstants.CODE_ME_PROFILE_PERSONAL_INFO) {
                ProfileEditBean bean = JsonMananger.jsonToBean(result, ProfileEditBean.class);
                updateHeaderUi(bean.name,bean.avatar,bean.signature);
            }
        }catch (Exception e){

        }

    }
}
