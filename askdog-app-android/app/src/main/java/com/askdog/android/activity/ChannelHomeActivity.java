package com.askdog.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.activity.login.LoginActivity;
import com.askdog.android.adapter.ChannelHomePageAdapter;
import com.askdog.android.model.ChannelInfoBean;
import com.askdog.android.model.ChannelDetailItemBean;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.NToast;
import com.askdog.android.utils.PreferencesUtils;
import com.askdog.android.view.dialog.CommonToast;
import com.askdog.android.view.dialog.DeleteConfirmDialog;
import com.hellosliu.easyrecyclerview.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChannelHomeActivity extends BaseActivity implements ChannelHomePageAdapter.OnChannelHomeViewClickListener {

    private static final int SUBSCRIPTION = 100;
    @Bind(R.id.channel_home_rv)
    RecyclerView mRecyclerView;

    @Bind(R.id.navi_title)
    TextView title;
    private String channelId;
    private ChannelHomePageAdapter mAdapter;
    private ChannelInfoBean mChannelInfoBean;
    private ChannelDetailItemBean mChannelDetailItemBean;
    private int page = 0;
    private int subscribeType;
    private DeleteConfirmDialog mChoiceDialog;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_home);
        ButterKnife.bind(this);
        initData();
        initView();
        request(OkRequestConstants.CODE_CHANNELS_DETAIL);
        request(OkRequestConstants.CODE_CHANNELS_LIST);
    }

    public void initView() {
        title.setText(getResources().getString(R.string.activity_channel_home_title));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getMoreData();
//            }
//
//            @Override
//            public void onReload() {
////                getMoreData();
//                mRecyclerView.setDataEnd();
//            }
//        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                    if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                        getMoreData();
                    }
                }
            }
        });
        // Init Data
        List<ChannelDetailItemBean.ChannelExpResult> list = new ArrayList<>();
        mAdapter = new ChannelHomePageAdapter(this, list);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getMoreData() {
        if (mAdapter.getItemCount() >= mChannelDetailItemBean.total) {
//            mRecyclerView.setDataEnd();
        } else {
            page++;
            request(OkRequestConstants.CODE_CHANNELS_LIST);
        }
    }

    public void initData() {
        channelId = getIntent().getStringExtra("channelId");
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (requestCode == OkRequestConstants.CODE_CHANNELS_DETAIL) {
                mChannelInfoBean = JsonMananger.jsonToBean(result, ChannelInfoBean.class);
                mAdapter.setHeaderData(mChannelInfoBean);
            } else if (requestCode == OkRequestConstants.CODE_CHANNELS_LIST) {
                mChannelDetailItemBean = JsonMananger.jsonToBean(result, ChannelDetailItemBean.class);
                mAdapter.getmData().addAll(mChannelDetailItemBean.result);
            } else if (requestCode == OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION) {
                mChannelInfoBean.subscribed = true;
                mChannelInfoBean.subscriber_count = mChannelInfoBean.subscriber_count + 1;
                mAdapter.setHeaderData(mChannelInfoBean);
            } else if (requestCode == OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION_CANCEL) {
                mChannelInfoBean.subscribed = false;
                mChannelInfoBean.subscriber_count = mChannelInfoBean.subscriber_count - 1;
                mAdapter.setHeaderData(mChannelInfoBean);
            }  else if(requestCode == OkRequestConstants.CODE_DELETE_EXPERIENCE){
                mAdapter.getmData().remove(mPosition);
                CommonToast toast = new CommonToast(ChannelHomeActivity.this);
                toast.setMessage("经验删除成功");
                toast.show();
            }
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.getmData().clear();
        mAdapter = null;
    }

    private void buildItemList2Adapter(String request) {

    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(mContext);
        if (requestCode == OkRequestConstants.CODE_CHANNELS_DETAIL) {
            return action.getChannelDetailInfo(channelId);
        } else if (requestCode == OkRequestConstants.CODE_CHANNELS_LIST) {
            return action.getChannelExpericencesList(channelId, page, PAGESIZE);
        } else if (requestCode == OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION) {
            return action.postChannelSubscription(channelId);//TODO
        } else if (requestCode == OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION_CANCEL) {
            return action.deleteChannelSubscription(channelId);//TODO
        } else if(requestCode == OkRequestConstants.CODE_DELETE_EXPERIENCE){
            return action.deleteMyExperience(mAdapter.getmData().get(mPosition).id);//TODO
        }
        return super.doInBackground(requestCode);
    }

    @OnClick(R.id.navi_back)
    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId) {
            case R.id.navi_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(int position, ChannelDetailItemBean.ChannelExpResult data) {
        Intent intent = new Intent();
        intent.setClass(ChannelHomeActivity.this, ExperiencesDetailActivity.class);
        intent.putExtra("authorId", data.id);
        startActivity(intent);
    }

    @Override
    public void onSubscribeViewClick(int type) {
        boolean isLogin = PreferencesUtils.getBoolean(this, ConstantUtils.IS_LOGIN);
        if(!isLogin){
            subscribeType = type;
            Intent i = new Intent();
            i.setClass(ChannelHomeActivity.this, LoginActivity.class);
            startActivityForResult(i,SUBSCRIPTION);
            return;
        }
        if (type == 0) {
            request(OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION);
        } else if (type == 1) {
            request(OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION_CANCEL);
        }
    }

    @Override
    public void onDeleteChannel(int pos) {
        mPosition = pos;
        comfirmDelete();
    }
    private void comfirmDelete() {
        if (mChoiceDialog == null) {
            mChoiceDialog = new DeleteConfirmDialog(ChannelHomeActivity.this,
                    new DeleteConfirmDialog.ButtonListener() {
                        @Override
                        public void onClickBtn() {
                            // TODO Auto-generated method stub
                            request(OkRequestConstants.CODE_DELETE_EXPERIENCE);
                        }
                    });
        }
        mChoiceDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SUBSCRIPTION && requestCode == RESULT_OK){
            if (subscribeType == 0) {
                request(OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION);
            } else if (subscribeType == 1) {
                request(OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION_CANCEL);
            }
        }
    }
}
