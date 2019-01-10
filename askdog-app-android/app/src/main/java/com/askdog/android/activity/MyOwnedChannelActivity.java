package com.askdog.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.adapter.MyOwnedChannelAdapter;
import com.askdog.android.interf.IOnClickListener;
import com.askdog.android.model.ChannelBean;
import com.askdog.android.model.ChannelDetailItemBean;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.NToast;
import com.askdog.android.view.dialog.CommonToast;
import com.askdog.android.view.dialog.DeleteConfirmDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOwnedChannelActivity extends BaseActivity implements IOnClickListener {
    private static final int CREATECHANNEL = 100;
    @Bind(R.id.my_own_channel)
    RecyclerView recylerView;
    @Bind(R.id.navi_title)
    TextView title;
    int page = 0;
    private MyOwnedChannelAdapter mChannelAdapter;
    private ChannelBean mChannelBean;
    private DeleteConfirmDialog mChoiceDialog;
    private int mPostion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_owned_channel);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.activity_my_own_channel_title));
        setRecycleView();
        request(OkRequestConstants.CODE_MY_OWNED_CHANNEL);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(this);
        if (requestCode == OkRequestConstants.CODE_DELETE_CHANNEL) {
            return action.deleteChannelExperience(mChannelAdapter.getDataList().get(mPostion).id);
        } else if (requestCode == OkRequestConstants.CODE_MY_OWNED_CHANNEL) {
            return action.getMyOwnedChannel(page, PAGESIZE);
        }else if (requestCode == OkRequestConstants.CODE_CHANNELS_LIST) {
            return action.getChannelExpericencesList(mChannelAdapter.getDataList().get(mPostion).id, 0, 1);
        }
        return super.doInBackground(requestCode);
    }

    private void setRecycleView() {
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        //设置加载，网络异常，数据到底文字

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATECHANNEL && resultCode == RESULT_OK) {
            CommonToast toast = new CommonToast(MyOwnedChannelActivity.this);
            toast.setMessage(getResources().getString(R.string.channel_release_success));
            toast.show();
            request(OkRequestConstants.CODE_MY_OWNED_CHANNEL);
            if (mChannelAdapter != null) {
                mChannelAdapter.getDataList().clear();
            }
        }
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {

            if (requestCode == OkRequestConstants.CODE_DELETE_CHANNEL) {
                CommonToast toast = new CommonToast(MyOwnedChannelActivity.this);
                toast.setMessage("频道删除成功");
                toast.show();
                mChannelAdapter.getDataList().remove(mPostion);
                mChannelAdapter.notifyDataSetChanged();
            } else if (requestCode == OkRequestConstants.CODE_MY_OWNED_CHANNEL) {
                mChannelBean = JsonMananger.jsonToBean(result, ChannelBean.class);
                if (mChannelBean != null)
                    executeViewAdapter(mChannelBean.result);
            }else if (requestCode == OkRequestConstants.CODE_CHANNELS_LIST) {
                ChannelDetailItemBean mChannelDetailItemBean = JsonMananger.jsonToBean(result, ChannelDetailItemBean.class);
                if(mChannelDetailItemBean.total > 0){
                    NToast.shortToast(MyOwnedChannelActivity.this,"请先删除频道内容");
                }else{
                    request(OkRequestConstants.CODE_DELETE_CHANNEL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeViewAdapter(ArrayList<ChannelBean.ChannelResult> results) {

        if (mChannelAdapter == null) {
            mChannelAdapter = new MyOwnedChannelAdapter(this, results, this);
            recylerView.setAdapter(mChannelAdapter);
        } else {
            mChannelAdapter.getDataList().addAll(results);
            mChannelAdapter.notifyDataSetChanged();
        }
    }


    private void showNetWorkErrorView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.view_customer_network_error, null);
    }

    @OnClick(R.id.navi_back)
    @Override
    public void onClick(View v) {
        finish();
    }


    @Override
    public void onItemViewClickListener(int pos) {
        try {
            if (pos == -1) {
                Intent intent = new Intent();
                intent.setClass(MyOwnedChannelActivity.this, CreateChannelActivity.class);
                startActivityForResult(intent, CREATECHANNEL);
            } else {
                List<ChannelBean.ChannelResult> results = mChannelAdapter.getDataList();
                ChannelBean.ChannelResult result = results.get(pos);
                Intent intent = new Intent();
                intent.setClass(MyOwnedChannelActivity.this, ChannelHomeActivity.class);
                intent.putExtra("channelId", result.id);
                startActivity(intent);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onItemDeleteClickListener(int pos) {
        mPostion = pos;
        comfirmDelete();
    }

    private void comfirmDelete() {
        if (mChoiceDialog == null) {
            mChoiceDialog = new DeleteConfirmDialog(MyOwnedChannelActivity.this,
                    new DeleteConfirmDialog.ButtonListener() {
                        @Override
                        public void onClickBtn() {
                            //确认频道下是否有经验
                            request(OkRequestConstants.CODE_CHANNELS_LIST);
//                            request(OkRequestConstants.CODE_DELETE_CHANNEL);
                        }
                    });
        }
        mChoiceDialog.show();
    }
}
