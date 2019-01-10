package com.askdog.android.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.askdog.android.BaseFragment;
import com.askdog.android.R;
import com.askdog.android.activity.ChannelHomeActivity;
import com.askdog.android.activity.ExperiencesDetailActivity;
import com.askdog.android.activity.PersonalChannelActivity;
import com.askdog.android.adapter.ChannelViewAdapter;
import com.askdog.android.interf.IOnClickListener;
import com.askdog.android.model.ChannelBean;
import com.askdog.android.model.UserBean;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.JsonMananger;
import com.hellosliu.easyrecyclerview.LoadMoreRecylerView;
import com.hellosliu.easyrecyclerview.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link .} interface
 * to handle interaction events.
 * Use the {@link ChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChannelFragment extends BaseFragment implements IOnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String TAG = ChannelFragment.class.getSimpleName();
    @Bind(R.id.channel_recycleview)
    LoadMoreRecylerView recylerView;
    ArrayList<ChannelBean.ChannelResult> items;

    /**
     * mParamType
     * 0 自己
     * 1 订阅
     */
    private int mParamType;


    /**
     * 其他人id，自己为空
     */
    private String mParamUserId;
    private View mView;
    private int PAGESIZE = 5;
    private ChannelViewAdapter mChannelAdapter;
    private int page = 0;
    private int total = 0;
    private ChannelBean mChannelBean;

    public ChannelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChannelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChannelFragment newInstance(int param1, String param2) {
        ChannelFragment fragment = new ChannelFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamType = getArguments().getInt(ARG_PARAM1);
            mParamUserId = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_channel, container, false);

            if (mParamType == 0) {
                request(OkRequestConstants.CODE_USER_CHANNELS_OWNED);
            } else {
                request(OkRequestConstants.CODE_USER_CHANNELS_SUBSCRIBED);
            }
        }

        ButterKnife.bind(this, mView);
        setRecycleView();

        return mView;
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setRecycleView() {

        recylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
//                recylerView.setDataEnd();
            }
        });
//        recylerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                ArrayList<ChannelBean.ChannelResult> results = mChannelBean.result;
//                ChannelBean.ChannelResult result = results.get(position);
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), ExperiencesDetailActivity.class);
//                intent.putExtra("authorId", result.id);
//                ((PersonalChannelActivity) getActivity()).startActivity(intent);
//            }
//        }));
    }

    private void getMoreData() {
        if (mChannelAdapter.getItemCount() >= mChannelBean.total) {
            recylerView.setDataEnd();
        } else {
            page++;
            if (mParamType == 0) {
                request(OkRequestConstants.CODE_USER_CHANNELS_OWNED);
            } else {
                request(OkRequestConstants.CODE_USER_CHANNELS_SUBSCRIBED);
            }
        }
    }

    private void setSampleLoadText() {
        //设置加载，网络异常，数据到底文字
        recylerView.setSampleLoadText("数据加载中...", "网络异常", "到底了...");
    }

//    private void setCustomerLoadFoot() {
//        TextView loadingView = new TextView(getActivity());
//        loadingView.setGravity(Gravity.CENTER);
//        loadingView.setText("Customer Loading...");
//
//        TextView networkErrorView = new TextView(getActivity());
//        networkErrorView.setGravity(Gravity.CENTER);
//        networkErrorView.setText("Customer NetWork Error");
//
//        TextView dataEndView = new TextView(getActivity());
//        dataEndView.setGravity(Gravity.CENTER);
//        dataEndView.setText("Customer Data End");
//
//        //自定义加载，网络异常，数据到底 显示view
//        recylerView.setCustomerLoadFooter(loadingView, networkErrorView, dataEndView);
//    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(getActivity());

        if (requestCode == OkRequestConstants.CODE_USER_CHANNELS_OWNED) {
            return action.getUserChannelOwned(mParamUserId, PAGESIZE, page);
        } else if (requestCode == OkRequestConstants.CODE_USER_CHANNELS_SUBSCRIBED) {
            return action.getUserChannelSubscribed(mParamUserId, PAGESIZE, page);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {

            mChannelBean = JsonMananger.jsonToBean(result, ChannelBean.class);
            if (requestCode == OkRequestConstants.CODE_USER_CHANNELS_OWNED) {
                ((PersonalChannelActivity)getActivity()).updateSelfChannel(mChannelBean.total);
            } else if (requestCode == OkRequestConstants.CODE_USER_CHANNELS_SUBSCRIBED) {
                ((PersonalChannelActivity)getActivity()).updateOrderChannel(mChannelBean.total);
            }

            if (mChannelBean != null)
                executeViewAdapter(mChannelBean.result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //    private void showEmptyView(){
//        View view = layoutInflater.inflate(R.layout.view_customer_empty, null);
//        recylerView.showEmptyView(view);
//    }
//
    private void showNetWorkErrorView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_customer_network_error, null);
        recylerView.showEmptyView(view);
    }

    private void executeViewAdapter(ArrayList<ChannelBean.ChannelResult> results) {

        if (mChannelAdapter == null) {
            mChannelAdapter = new ChannelViewAdapter(getActivity(), results, this);
            recylerView.setAdapter(mChannelAdapter);
        } else {
            mChannelAdapter.getDataList().addAll(results);
            mChannelAdapter.notifyDataSetChanged();
        }
        recylerView.onRefreshComplete();
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        showNetWorkErrorView();
    }

    @Override
    public void onItemViewClickListener(int pos) {
        try {
            List<ChannelBean.ChannelResult> results = mChannelAdapter.getDataList();
            ChannelBean.ChannelResult result = results.get(pos);
            Intent intent = new Intent();
            intent.setClass(getActivity(), ChannelHomeActivity.class);
            intent.putExtra("channelId", result.id);
            ((PersonalChannelActivity) getActivity()).startActivity(intent);

        } catch (Exception e) {

        }

    }

    @Override
    public void onItemDeleteClickListener(int pos) {

    }
}
