package com.askdog.android.activity.cash;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.adapter.CashIncomeDetailAdapter;
import com.askdog.android.interf.IOnClickListener;
import com.askdog.android.model.CashIncomeBean;
import com.askdog.android.model.RevenueBean;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.DateUtils;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.view.widget.EmptyLayout;
import com.hellosliu.easyrecyclerview.LoadMoreRecylerView;
import com.hellosliu.easyrecyclerview.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CashIncomeDetailActivity extends BaseActivity implements IOnClickListener {
    @Bind(R.id.navi_title)
    TextView title;
    @Bind(R.id.cash_income_emptyLayout)
    EmptyLayout emptyLayout;
    @Bind(R.id.cash_income_total)
    TextView incomeTotal;
    @Bind(R.id.cash_income_recycle_view)
    LoadMoreRecylerView recyclerView;
    CashIncomeDetailAdapter mAdapter;
    ArrayList<CashIncomeBean.IncomeResult> items = new ArrayList<>();
    private int page = 0;
    private CashIncomeBean mCashIncomeBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_income_detail);
        ButterKnife.bind(this);
        initView();
        request(OkRequestConstants.CODE_ME_REVENUE);
        request(OkRequestConstants.CODE_ME_INCOME);
    }


    private void initView() {
        title.setText("收入明细");
        setRecycleView();

    }

    private void updateUI(String s) {
        incomeTotal.setText("¥" + s);
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

            }

            @Override
            public void onReload() {
//                getMoreData();
                recyclerView.setDataEnd();
            }
        });
    }

    private void getMoreData() {
        if (mAdapter.getItemCount() >= mCashIncomeBean.total || mCashIncomeBean.last) {
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
        View view = getLayoutInflater().inflate(R.layout.activity_cash_income_detail_empty, null);
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


    /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(this);
        if (requestCode == OkRequestConstants.CODE_ME_INCOME) {
//            Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
//            ca.add(Calendar.YEAR, -1); //月份减1
//            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String from = DateUtils.beforeOneYearTime();
            String to = DateUtils.currentDateTime();
            return action.getMeIncomeList(from, to, page, PAGESIZE);
        } else if (requestCode == OkRequestConstants.CODE_ME_REVENUE) {
            return action.getMeRevenue();
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (requestCode == OkRequestConstants.CODE_ME_INCOME) {
                mCashIncomeBean = JsonMananger.jsonToBean(result, CashIncomeBean.class);
                if (mCashIncomeBean.result.size() == 0 && mAdapter == null) {
                    showEmpty();
                } else {
                    emptyLayout.hide();
                    if (mAdapter == null) {
                        mAdapter = new CashIncomeDetailAdapter(CashIncomeDetailActivity.this, this, mCashIncomeBean.result);
                        recyclerView.setAdapter(mAdapter);
                    } else {
                        mAdapter.addItemsData(mCashIncomeBean.result);
                    }
                }
            } else if (requestCode == OkRequestConstants.CODE_ME_REVENUE) {
                final RevenueBean bean = JsonMananger.jsonToBean(result, RevenueBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(String.valueOf(bean.total));
                    }
                });
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        super.onFailure(requestCode, state, result);
    }
}
