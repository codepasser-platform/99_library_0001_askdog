package com.askdog.android.activity;

import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.adapter.HistoryAdapter;
import com.askdog.android.interf.IOnClickListener;
import com.askdog.android.model.HistoryBean;
import com.askdog.android.model.HomeBean;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.CommonUtils;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.NLog;
import com.askdog.android.utils.NToast;
import com.hellosliu.easyrecyclerview.LoadMoreRecylerView;
import com.hellosliu.easyrecyclerview.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchExperiencesActivity extends BaseActivity implements IOnClickListener, com.askdog.android.view.searchview.SearchView.SearchViewListener {
    private final int SIZE = 10;
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Bind(R.id.serarch_experience_recyclerview)
    LoadMoreRecylerView recyclerView;

    @Bind(R.id.experience_search_layout)
    com.askdog.android.view.searchview.SearchView searchView;
    @Bind(R.id.serarch_result_header)
    TextView searchResult;
    @Bind(R.id.search_result_cardview)
    CardView cardView;

    private String searchKey = "";
    private String lastKey = "";
    private HomeBean mBean;

    private HistoryAdapter mChannelAdapter;
    private String headerNotice = "%s个\"<font color=\"#3688e5\">%s</font>\"搜索结果";

    private String starMark = "<mark>";
    private String endMark = "</mark>";
    private String highlight = "<font color=\"#3688e5\">%s</font>";
    /**
     * 任务线程，间隔一定的时间查询是否需要更新UI
     */
    private Runnable timeTaskThread = new Runnable() {
        public void run() {
            launchTaskSchedule();
        }
    };

    private int total = 0;
    private int page = 0;
    private MatrixCursor matrixCursor;
    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

    private void launchTaskSchedule() {
        if (!TextUtils.isEmpty(searchKey) && !searchKey.equalsIgnoreCase(lastKey)) {
            lastKey = searchKey;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    request(OkRequestConstants.CODE_SEARCH_SUGGEST);
                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview_experiences);
        ButterKnife.bind(this);
        initView();
        setRecycleView();

        executor.scheduleAtFixedRate(timeTaskThread, 0, 600, TimeUnit.MICROSECONDS);

    }

    @Override
    protected void onPause() {
        super.onPause();
        CommonUtils.hideKeyboard(SearchExperiencesActivity.this);
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(ArrayList<String> autoCompleteData) {

        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
            searchView.setAutoCompleteAdapter(autoCompleteAdapter);
        } else {
            autoCompleteAdapter.clear();
            autoCompleteAdapter.addAll(autoCompleteData);
        }
        autoCompleteAdapter.notifyDataSetChanged();
    }

    private void initView() {
        //设置监听
        searchView.setSearchViewListener(this);
    }

    private void setRecycleView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置加载，网络异常，数据到底文字
        setSampleLoadText();
        //自定义加载，网络异常，数据到底 显示view
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMoreData();
//                recylerView.setDataEnd();
            }

            @Override
            public void onReload() {
//                getMoreData();
                recyclerView.setDataEnd();
            }
        });
    }

    private void setSampleLoadText() {
        //设置加载，网络异常，数据到底文字
        recyclerView.setSampleLoadText("数据加载中...", "网络异常", "到底了...");
    }

    private void getMoreData() {
        if (mChannelAdapter.getItemCount() >= mBean.total) {
            recyclerView.setDataEnd();
        } else {
            page++;
            request(OkRequestConstants.CODE_SEARCH_ALL_RESULT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void enterExperiencesDetail(int pos) {
        HomeBean.Result r = mBean.result.get(pos);
        Intent intent = new Intent();
        intent.setClass(SearchExperiencesActivity.this, ExperiencesDetailActivity.class);

        intent.putExtra("authorId", r.id);
        startActivity(intent);
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(mContext);
        if (requestCode == OkRequestConstants.CODE_SEARCH_SUGGEST) {
            return action.getSearchResultList("experience_search", searchKey.toString(), 0, 5);
        } else if (requestCode == OkRequestConstants.CODE_SEARCH_ALL_RESULT) {
            return action.getSearchResultList("experience_search", searchKey.toString(), page * SIZE, SIZE);
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            mBean = JsonMananger.jsonToBean(result, HomeBean.class);
            if (requestCode == OkRequestConstants.CODE_SEARCH_SUGGEST) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buildSearchSuggestDataList(mBean);
                    }
                });
            } else if (requestCode == OkRequestConstants.CODE_SEARCH_ALL_RESULT) {
                //TODO build new adapter
                buildSearchResultDataList(mBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildSearchResultDataList(HomeBean bean) {
        cardView.setVisibility(View.VISIBLE);
        total = bean.total;
        searchResult.setText(Html.fromHtml(String.format(headerNotice, bean.total, searchKey)));
        ArrayList<HomeBean.Result> HResults = bean.result;
        ArrayList<HistoryBean.HistoryResult> results = new ArrayList<>();
        for (int i = 0; i < HResults.size(); i++) {
            HomeBean.Result hResult = HResults.get(i);
            HistoryBean.HistoryResult result = new HistoryBean.HistoryResult();
            result.id = hResult.id;
            HistoryBean.HistoryResult.H_Author author = new HistoryBean.HistoryResult.H_Author();
            author.name = hResult.author.name;
            author.id = hResult.author.id;
            result.author = author;
            HistoryBean.HistoryResult.H_Channel channel = new HistoryBean.HistoryResult.H_Channel();
            channel.name = hResult.channel.channel_name;
            result.channel = channel;
            result.thumbnail = hResult.content_pic_url;
            result.creation_time = hResult.creation_date;
            result.subject = hResult.subject;
            result.view_count = hResult.view_count;
            results.add(result);
        }


        if (mChannelAdapter == null || page == 0) {
            mChannelAdapter = new HistoryAdapter(SearchExperiencesActivity.this, results, this);
            recyclerView.setAdapter(mChannelAdapter);
        } else {
//            mChannelAdapter.getDataList().clear();
            mChannelAdapter.getDataList().addAll(results);
            mChannelAdapter.notifyDataSetChanged();
        }
        recyclerView.onRefreshComplete();
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        super.onFailure(requestCode, state, result);
        recyclerView.onRefreshComplete();
    }

    private void buildSearchSuggestDataList(HomeBean bean) {
        ArrayList<String> dataList = new ArrayList<>();
        ArrayList<HomeBean.Result> results = bean.result;
        int size = results.size() > 5 ? 5 : results.size();
        for (int i = 0; i < size; i++) {
            HomeBean.Result result = results.get(i);
            dataList.add(result.subject.replace(starMark, "").replace(endMark, ""));
        }
        if (dataList.size() == 5) {
            dataList.add("查看全部搜索结果");
        }

        getAutoCompleteData(dataList);
    }

    @Override
    public void onItemViewClickListener(int pos) {
        Intent intent = new Intent();
        intent.setClass(SearchExperiencesActivity.this, ExperiencesDetailActivity.class);
        HistoryBean.HistoryResult result = mChannelAdapter.getDataList().get(pos);
        intent.putExtra("authorId", result.id);
        startActivity(intent);
    }

    @Override
    public void onItemDeleteClickListener(int pos) {

    }


    @Override
    public void onRefreshAutoComplete(String text) {
        searchKey = text;
    }

    @Override
    public void onSearch(String text) {
        page = 0;
        request(OkRequestConstants.CODE_SEARCH_ALL_RESULT);
    }


}
