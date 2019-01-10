package com.askdog.android.activity.cash;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.adapter.CashIncomeDetailAdapter;
import com.askdog.android.adapter.CashWithdrawDetailAdapter;
import com.askdog.android.interf.IOnClickListener;
import com.askdog.android.model.CashIncomeBean;
import com.askdog.android.model.CashWithdrawBean;
import com.askdog.android.model.RevenueBean;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.DateUtils;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.view.widget.EmptyLayout;
import com.hellosliu.easyrecyclerview.LoadMoreRecylerView;
import com.hellosliu.easyrecyclerview.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CashWithdrawRecordActivity extends BaseActivity implements IOnClickListener {


    @Bind(R.id.navi_title)
    TextView title;
    @Bind(R.id.cash_withdraw_emptyLayout)
    EmptyLayout emptyLayout;
    @Bind(R.id.cash_withdraw_cased)
    TextView cashed_total;
    @Bind(R.id.cash_withdraw_recycle_view)
    LoadMoreRecylerView recyclerView;
    CashWithdrawDetailAdapter mAdapter;
    ArrayList<CashWithdrawBean.WithdrawResult> items = new ArrayList<>();
    private int page = 0;
    private CashWithdrawBean mCashWithdrawBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_withdraw_record);
        ButterKnife.bind(this);
        initView();
        request(OkRequestConstants.CODE_ME_REVENUE);
        request(OkRequestConstants.CODE_USER_WITHDRAW_DETAIL);
    }


    private void initView() {
        title.setText("提现记录");
        setRecycleView();
//        showEmpty();
    }

    private void setRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置加载，网络异常，数据到底文字
        setSampleLoadText();
        //自定义加载，网络异常，数据到底 显示view
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMoreData();
//                recyclerView.setDataEnd();
            }

            @Override
            public void onReload() {
//                getMoreData();
                recyclerView.setDataEnd();
            }
        });
    }

    private void getMoreData() {
        if (mAdapter.getItemCount() >= mCashWithdrawBean.total || mCashWithdrawBean.last) {
            recyclerView.setDataEnd();
        } else {
            page++;
            request(OkRequestConstants.CODE_ME_INCOME);
        }
    }

    private void setSampleLoadText() {
        //设置加载，网络异常，数据到底文字
        recyclerView.setSampleLoadText("数据加载中...", "网络异常", "到底了...");
    }

    private void showEmpty() {
        View view = getLayoutInflater().inflate(R.layout.activity_cash_withdraw_record_empty, null);
        emptyLayout.setEmptyView((ViewGroup) view);
        emptyLayout.showEmpty();
    }

    @OnClick({R.id.navi_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navi_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemViewClickListener(int pos) {

    }

    @Override
    public void onItemDeleteClickListener(int pos) {

    }

    /**
     * 以下网络部分
     */
    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (requestCode == OkRequestConstants.CODE_ME_REVENUE) {
            return action.getMeRevenue();
        } else if (requestCode == OkRequestConstants.CODE_USER_WITHDRAW_DETAIL) {
            String from = DateUtils.beforeOneYearTime();
            String to = DateUtils.currentDateTime();
            return action.getMeWithdrawList(from, to, page, PAGESIZE);
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (requestCode == OkRequestConstants.CODE_ME_REVENUE) {
                final RevenueBean bean = JsonMananger.jsonToBean(result, RevenueBean.class);
                long balance = bean.balance;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cashed_total.setText("¥" +String.valueOf(bean.total - bean.balance));
                    }
                });
            } else if (requestCode == OkRequestConstants.CODE_USER_WITHDRAW_DETAIL) {
                mCashWithdrawBean = JsonMananger.jsonToBean(result, CashWithdrawBean.class);
                if (mCashWithdrawBean.result.size() == 0 && mAdapter == null) {
                    showEmpty();
                } else {
                    emptyLayout.hide();
                    if (mAdapter == null) {
                        mAdapter = new CashWithdrawDetailAdapter(CashWithdrawRecordActivity.this, this, mCashWithdrawBean.result);
                        recyclerView.setAdapter(mAdapter);
                    } else {
                        mAdapter.addItemsData(mCashWithdrawBean.result);
                    }
                }
                recyclerView.onRefreshComplete();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        super.onFailure(requestCode, state, result);
        if (recyclerView != null) {
            recyclerView.onRefreshComplete();
        }
    }
}
