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
import com.askdog.android.adapter.HistoryAdapter;
import com.askdog.android.interf.IOnClickListener;
import com.askdog.android.model.ChannelBean;
import com.askdog.android.model.HistoryBean;
import com.askdog.android.model.HomeBean;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.NToast;
import com.askdog.android.view.dialog.DeleteConfirmDialog;
import com.hellosliu.easyrecyclerview.LoadMoreRecylerView;
import com.hellosliu.easyrecyclerview.listener.OnRefreshListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyViewHistoryActivity extends BaseActivity implements IOnClickListener {
    @Bind(R.id.my_histroy_channel)
    LoadMoreRecylerView recylerView;
    @Bind(R.id.navi_title)
    TextView title;

    private HistoryAdapter mChannelAdapter;
    private int page = 0;
    private int total = 0;
    private int mPostion = 0;
    private DeleteConfirmDialog mChoiceDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_history);
        ButterKnife.bind(this);
        initView();
        request(OkRequestConstants.CODE_VIEW_HISTORY);
    }

    @OnClick(R.id.navi_back)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.navi_back:
                finish();
                break;
        }
    }

    private void initView() {
        title.setText(getResources().getString(R.string.activity_my_view_history_title));
        setRecycleView();
    }

    private void setRecycleView() {
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        //设置加载，网络异常，数据到底文字
        setSampleLoadText();
        //自定义加载，网络异常，数据到底 显示view
        recylerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMoreData();
//                recylerView.setDataEnd();
            }

            @Override
            public void onReload() {
//                getMoreData();
                recylerView.setDataEnd();
            }
        });
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (OkRequestConstants.CODE_VIEW_HISTORY == requestCode) {
//            OkRequestAction action = new OkRequestAction(this);
            return action.getUserViewHistory(page, PAGESIZE);
        } else if (requestCode == OkRequestConstants.CODE_DELETE_EXPERIENCE) {
            String mDeleteExperienceId = mChannelAdapter.getDataList().get(mPostion).id;
            return action.deleteMyExperience(mDeleteExperienceId);
        }
        return super.doInBackground(requestCode);
    }

    private void getMoreData() {
        if (mChannelAdapter.getItemCount() >= total) {
            recylerView.setDataEnd();
        } else {
            page++;
            request(OkRequestConstants.CODE_VIEW_HISTORY);
        }
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        HistoryBean bean = null;
        try {
            if (OkRequestConstants.CODE_VIEW_HISTORY == requestCode) {
                bean = JsonMananger.jsonToBean(result, HistoryBean.class);
                if (bean != null) {
                    total = bean.total;
                    executeViewAdapter(bean.result);
                }
            } else if (requestCode == OkRequestConstants.CODE_DELETE_EXPERIENCE) {
                mChannelAdapter.getDataList().remove(mPostion);
                mChannelAdapter.notifyDataSetChanged();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeViewAdapter(ArrayList<HistoryBean.HistoryResult> results) {

        if (mChannelAdapter == null) {
            mChannelAdapter = new HistoryAdapter(this, results, this);
//            mChannelAdapter.setDeleteEnable(true);
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

    @Override
    public void onItemViewClickListener(int pos) {
        Intent intent = new Intent();
        intent.setClass(MyViewHistoryActivity.this, ExperiencesDetailActivity.class);
        HistoryBean.HistoryResult result = mChannelAdapter.getDataList().get(pos);
        intent.putExtra("authorId", result.id);
        startActivity(intent);
    }

    @Override
    public void onItemDeleteClickListener(int pos) {
        mPostion = pos;
        comfirmDelete();

    }

    private void comfirmDelete() {
        if (mChoiceDialog == null) {
            mChoiceDialog = new DeleteConfirmDialog(MyViewHistoryActivity.this,
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
}
