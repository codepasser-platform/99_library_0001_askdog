package com.askdog.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.BaseApplication;
import com.askdog.android.R;
import com.askdog.android.interf.ICallback;
import com.askdog.android.model.HomeBean;
import com.askdog.android.model.NotificationBean;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.view.widget.holder.AboutShareHolder;
import com.askdog.android.view.widget.holder.LoadMoreHolder;
import com.askdog.android.view.widget.holder.NotificationItemHolder;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationMessageActivity extends BaseActivity {
    @Bind(R.id.notification_container)
    RelativeLayout containerView;
    @Bind(R.id.navi_title)
    TextView title;
    private AndroidTreeView tView;
    private int page = 0;
    private TreeNode root;
    private int showCount = 0;//记录当前view中加载了多少条记录
    private TreeNode loadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_message);
        ButterKnife.bind(this);
        initView();
        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }
        request(OkRequestConstants.CODE_USER_NOTIFICATIONS);
    }

    private void initView() {
        title.setText("通知");
        root = TreeNode.root();

        tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setUseAutoToggle(false);
        containerView.addView(tView.getView());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }

    @OnClick(R.id.navi_back)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navi_back:
                finish();
                break;
        }
    }

    /**
     *
     */
    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (OkRequestConstants.CODE_USER_NOTIFICATIONS == requestCode) {
            return action.getUserNotification(page, 28);
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (OkRequestConstants.CODE_USER_NOTIFICATIONS == requestCode) {
                NotificationBean mNotificationBean = JsonMananger.jsonToBean(result, NotificationBean.class);
                buildNotification2View(mNotificationBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildNotification2View(NotificationBean mNotificationBean) {
        ArrayList<NotificationBean.NofifyResult> results = mNotificationBean.result;
        int size = results.size();
        if (loadMore != null) {
            tView.removeNode(loadMore);
        }
        for (int i = 0; i < size; i++) {
            TreeNode notificationRoot = new TreeNode(results.get(i)).setViewHolder(new NotificationItemHolder(this));
            showCount += results.get(i).group_data.size();
            tView.addNode(root, notificationRoot);
        }

        if (mNotificationBean.total == showCount) {
            loadMore = new TreeNode(new LoadMoreHolder.LoadMoreItem(true)).setViewHolder(new LoadMoreHolder(this, loadmore));
        } else {
            loadMore = new TreeNode(new LoadMoreHolder.LoadMoreItem(false)).setViewHolder(new LoadMoreHolder(this, loadmore));
        }
        tView.addNode(root, loadMore);
    }

    ILoadMoreClick loadmore = new ILoadMoreClick();

    public class ILoadMoreClick implements ICallback {

        @Override
        public void HeaderBtnClickListener(int type) {

        }

        @Override
        public void OnRealeaseClick(TreeNode node, String content, Object holeder) {

        }

        @Override
        public void OnItemClick(Object holder, int type) {

        }

        @Override
        public void OnItemClickListerner(int type) {

        }

        @Override
        public void expandSecondList(TreeNode node, boolean isExpand, String id) {

        }

        @Override
        public void onLoadMoreClick() {
            page++;
            request(OkRequestConstants.CODE_USER_NOTIFICATIONS);
        }

        @Override
        public void onAnswerCommend(TreeNode node, String id, String reply_comment_id) {

        }
    }
}
