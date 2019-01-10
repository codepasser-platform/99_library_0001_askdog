package com.askdog.android.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.fragment.ChannelFragment;
import com.askdog.android.model.ChannelBean;
import com.askdog.android.model.UserBean;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.transformations.glide.CropCircleTransformation;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalChannelActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.personal_owned_channel)
    TextView self_channel;
    @Bind(R.id.personal_subscribed_channel)
    TextView order_channel;
    @Bind(R.id.navi_title)
    TextView navi_title;
    @Bind(R.id.personal_avatar)
    ImageView avatar;
    @Bind(R.id.personal_name)
    TextView name;
    @Bind(R.id.personal_signature)
    TextView signature;
    ChannelFragment presonalChannel;
    ChannelFragment orderChannel;

    private UserBean mUserBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_channel);
        ButterKnife.bind(this);
        initData();
        initView();
        setDefaultFragment();
        request(OkRequestConstants.CODE_USER_CHANNELS_SUBSCRIBED);
    }

    private void initData() {
        mUserBean = (UserBean) getIntent().getParcelableExtra("user");
    }

    private void initView() {
        navi_title.setText(mUserBean.name);
        self_channel.setText("自建频道");
        order_channel.setText(getResources().getString(R.string.activity_personal_channel_title));
        self_channel.setSelected(true);
        order_channel.setSelected(false);
        name.setText(mUserBean.name);
        if(TextUtils.isEmpty(mUserBean.signature)){
            signature.setText("让知识触手可及");
        }else{
            signature.setText(mUserBean.signature);
        }
        Glide.with(this)
                .load(mUserBean.avatar)
                .error(R.drawable.avatar_default)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(avatar);
    }

    public void updateSelfChannel(int num) {
        self_channel.setText("自建频道" + num);
    }

    public void updateOrderChannel(int num) {
        order_channel.setText("订阅的频道" + num);
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        presonalChannel = ChannelFragment.newInstance(0, mUserBean.id);
        transaction.replace(R.id.personal_channel_content, presonalChannel);
        transaction.commit();
    }

    @OnClick({R.id.personal_owned_channel, R.id.personal_subscribed_channel, R.id.navi_back})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.personal_owned_channel:
            case R.id.personal_subscribed_channel:
                showFragment(id);
                break;
            case R.id.navi_back:
                finish();
                break;
        }
    }

    private void showFragment(int id) {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();

        switch (id) {
            case R.id.personal_owned_channel:
                if (presonalChannel == null) {
                    presonalChannel = ChannelFragment.newInstance(0, mUserBean.id);
                }
                // 使用当前Fragment的布局替代id_content的控件
                self_channel.setSelected(true);
                order_channel.setSelected(false);
                transaction.replace(R.id.personal_channel_content, presonalChannel);
                break;
            case R.id.personal_subscribed_channel:
                if (orderChannel == null) {
                    orderChannel = ChannelFragment.newInstance(1, mUserBean.id);
                }
                self_channel.setSelected(false);
                order_channel.setSelected(true);
                transaction.replace(R.id.personal_channel_content, orderChannel);
                break;
        }
        // transaction.addToBackStack();
        // 事务提交
        transaction.commit();
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(getActivity());

        if (requestCode == OkRequestConstants.CODE_USER_CHANNELS_SUBSCRIBED) {
            return action.getUserChannelSubscribed(mUserBean.id, 1, 0);
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (requestCode == OkRequestConstants.CODE_USER_CHANNELS_SUBSCRIBED) {
                ChannelBean mChannelBean = JsonMananger.jsonToBean(result, ChannelBean.class);
                updateOrderChannel(mChannelBean.total);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
