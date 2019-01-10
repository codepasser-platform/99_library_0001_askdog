
package com.askdog.android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.adapter.ChannelViewAdapter;
import com.askdog.android.interf.IOnClickListener;
import com.askdog.android.model.ChannelBean;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.JsonMananger;
import com.hellosliu.easyrecyclerview.LoadMoreRecylerView;
import com.hellosliu.easyrecyclerview.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MySubscribedChannelActivity extends BaseActivity implements IOnClickListener {
    @Bind(R.id.my_subscribed_channel)
    LoadMoreRecylerView recylerView;
    @Bind(R.id.navi_title)
    TextView title;
    int page = 0;
    private ChannelViewAdapter mChannelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscribed_channel);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.activity_subscribed_channel_title));
        setRecycleView();
        request(OkRequestConstants.CODE_MY_SUBSCRIBED_CHANNEL);
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(this);
        return action.getMySubscribedChannel(page, PAGESIZE);
    }

    private void setRecycleView() {
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        //设置加载，网络异常，数据到底文字
        setSampleLoadText();
        //自定义加载，网络异常，数据到底 显示view
        recylerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
//                getMoreData();
                recylerView.setDataEnd();
            }

            @Override
            public void onReload() {
//                getMoreData();
                recylerView.setDataEnd();
            }
        });
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        ChannelBean bean = null;
        try {
            bean = JsonMananger.jsonToBean(result, ChannelBean.class);
            if (bean != null)
                executeViewAdapter(bean.result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeViewAdapter(ArrayList<ChannelBean.ChannelResult> results) {

        if (mChannelAdapter == null) {
            mChannelAdapter = new ChannelViewAdapter(this, results, this);
            recylerView.setAdapter(mChannelAdapter);
        } else {
            mChannelAdapter.getDataList().addAll(results);
            mChannelAdapter.notifyDataSetChanged();
        }
        if (mChannelAdapter.getItemCount() == 0) {
            showNetWorkErrorView();
//            return;
        }
        recylerView.onRefreshComplete();
    }

    private void setSampleLoadText() {
        //设置加载，网络异常，数据到底文字
        recylerView.setSampleLoadText("数据加载中...", "网络异常", "到底了...");
    }

    private void showNetWorkErrorView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.view_customer_network_error, null);
        recylerView.showEmptyView(view);
    }

    @OnClick(R.id.navi_back)
    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemViewClickListener(int pos) {
        try {
            List<ChannelBean.ChannelResult> results = mChannelAdapter.getDataList();
            ChannelBean.ChannelResult result = results.get(pos);
            Intent intent = new Intent();
            intent.setClass(MySubscribedChannelActivity.this, ChannelHomeActivity.class);
            intent.putExtra("channelId", result.id);
            startActivity(intent);

        } catch (Exception e) {

        }
    }

    @Override
    public void onItemDeleteClickListener(int pos) {

    }
}

