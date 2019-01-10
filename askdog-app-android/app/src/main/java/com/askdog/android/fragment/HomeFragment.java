package com.askdog.android.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.askdog.android.BaseFragment;
import com.askdog.android.R;
import com.askdog.android.activity.ExperiencesDetailActivity;
import com.askdog.android.activity.AskDogActivity;
import com.askdog.android.model.HomeBean;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.PreferencesUtils;
import com.askdog.android.view.widget.PullLoadMoreRecyclerView;
import com.askdog.android.view.widget.RecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements RecyclerViewAdapter.OnRecyclerViewItemClickListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private final int PAGESIZE = 6;//每页显示条数
    @Bind(R.id.pullLoadMoreRecyclerView)
    public PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;


    int total = 0;            //条目的总数
    private View mCurrentView;//避免View重复加载
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private int page = 0;    //翻页页码

    private int loginStatus = 0;
    private boolean isLogin = false;
    private int mParamType;
    private static final String ARG_TYPE = "ARG_TYPE";
    private int refreshFlg = 1;//1 未登录 ，2 登录需要刷新，3 登录不需要刷新


    public HomeFragment() {
        // Required empty public constructor
    }

    public static ChannelFragment newInstance(int param1, String param2) {
        ChannelFragment fragment = new ChannelFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamType = getArguments().getInt(ARG_TYPE);
        }
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mCurrentView == null)
            mCurrentView = inflater.inflate(R.layout.fragment_home, container, false);
        return mCurrentView;
    }


    private void unLogin2LoginChange() {
        if (refreshFlg != 3) {
            isLogin = PreferencesUtils.getBoolean(getActivity(), ConstantUtils.IS_LOGIN, false);
            if (isLogin) {
                refreshFlg = 2;
            }
            if (refreshFlg == 2) {
                page = 0;
                refreshFlg = 3;
                if (mRecyclerViewAdapter != null)
                    mRecyclerViewAdapter.getDataList().clear();
                request(OkRequestConstants.CODE_SEARCH_CODE);
            }
        }
    }

    @Override
    public void initView(View view) {

        if (mRecyclerViewAdapter == null) {
            request(OkRequestConstants.CODE_SEARCH_CODE);
            mPullLoadMoreRecyclerView.setRefreshing(true);
            mPullLoadMoreRecyclerView.setLinearLayout();
            mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AskDogActivity) getActivity()).showHeaderView(true);
        unLogin2LoginChange();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (OkRequestConstants.CODE_SEARCH_CODE == requestCode) {

            int from = page * PAGESIZE;

            if (isLogin) {
                return action.getCategoryList(OkRequestConstants.PARA_LOGIN_SEARCH, from, PAGESIZE);
            } else {
                return action.getCategoryList(OkRequestConstants.PARA_UNLOGIN_SEARCH, from, PAGESIZE);
            }
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        HomeBean bean = null;
        try {
            if (OkRequestConstants.CODE_SEARCH_CODE == requestCode) {
                bean = JsonMananger.jsonToBean(result, HomeBean.class);
                if (bean != null && bean.result.size() != 0) {
                    total = bean.total;
                    executeViewAdapter(bean.result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        super.onFailure(requestCode, state, result);
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }


    @Override
    public void onBackPressed() {
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }

    private void executeViewAdapter(ArrayList<HomeBean.Result> results) {
        if (mRecyclerViewAdapter == null) {
            mRecyclerViewAdapter = new RecyclerViewAdapter(getActivity(), results, this);
            mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        } else {
            mRecyclerViewAdapter.getDataList().addAll(results);
            mRecyclerViewAdapter.notifyDataSetChanged();
            if (mRecyclerViewAdapter.getItemCount() >= total)
                mPullLoadMoreRecyclerView.setHasMore(false);//没有更多的时候调用
            else {
                mPullLoadMoreRecyclerView.setHasMore(true);//没有更多的时候调用
            }
        }
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void onItemClick(String result, int pos) {

        Intent intent = new Intent();
        intent.setClass(getActivity(), ExperiencesDetailActivity.class);
        HomeBean.Result resultbean = mRecyclerViewAdapter.getDataList().get(pos);
        intent.putExtra("authorId", resultbean.id);
        intent.putExtra("expericeId", resultbean.id);
        ((AskDogActivity) getActivity()).startActivity(intent);
    }



    //主要更新代码
    class PullLoadMoreListener implements PullLoadMoreRecyclerView.PullLoadMoreListener {
        @Override
        public void onRefresh() {
            page = 0;
            mRecyclerViewAdapter.getDataList().clear();
            request(OkRequestConstants.CODE_SEARCH_CODE);
        }

        @Override
        public void onLoadMore() {
            page = page + 1;
            request(OkRequestConstants.CODE_SEARCH_CODE);
        }
    }
}
