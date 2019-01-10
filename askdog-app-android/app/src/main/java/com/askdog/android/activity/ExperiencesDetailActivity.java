package com.askdog.android.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.activity.login.LoginActivity;
import com.askdog.android.activity.share.ShareMessageActivity;
import com.askdog.android.activity.webview.WebViewActivity;
import com.askdog.android.interf.ICallback;
import com.askdog.android.model.CommentsBean;
import com.askdog.android.model.ExperiencesBean;
import com.askdog.android.model.OrderPayStatusBean;
import com.askdog.android.model.OrdersPayBean;
import com.askdog.android.model.ShareRelatedBean;
import com.askdog.android.model.UpDownBean;
import com.askdog.android.model.UserBean;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.NToast;
import com.askdog.android.utils.PreferencesUtils;
import com.askdog.android.view.dialog.CommonToast;
import com.askdog.android.view.dialog.InputDialog;
import com.askdog.android.view.widget.holder.AboutShareHolder;
import com.askdog.android.view.widget.holder.AboutShareItemHolder;
import com.askdog.android.view.widget.holder.DetailHeaderHolder;
import com.askdog.android.view.widget.holder.DetailRecommendHolder;
import com.askdog.android.view.widget.holder.DetailRecommendItemHolder;
import com.askdog.android.view.widget.holder.LoadMoreHolder;
import com.askdog.android.wxapi.WXOrdersPayActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class ExperiencesDetailActivity extends BaseActivity implements ICallback, View.OnClickListener, TreeNode.TreeNodeClickListener {

    @Bind(R.id.container)
    RelativeLayout containerView;
    @Bind(R.id.detail_navi_title)
    TextView navi_title;
    private ExperiencesBean mExperiencesBean;
    private AndroidTreeView tView;
    private TreeNode myProfile;
    private TreeNode shareRoot;
    private TreeNode recommendRoot;
    //    private String expericeId;
    private String authorId;
    private ShareRelatedBean mSharebean;
    private DetailHeaderHolder mDetailHeaderHolder;
    private String from;
    private Map<TreeNode, List<TreeNode>> treeNodeListMap = new HashMap<>();
    private TreeNode mCurrentNode;
    private String commentId;//
    private TreeNode loadMore = null;
    private int page = 0;
    private TreeNode mAnswerNode;//评论的父类节点
    private String mAnswerId;    //评论Id
    private String mAnswerContent;
    private String reply_comment_id;
    private DetailRecommendHolder mDetailRecommendHolder;

    JCVideoPlayer.JCAutoFullscreenListener sensorEventListener;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mutilfunc);
        ButterKnife.bind(this);
//        cancelRequest();
        initData();
        initView();
        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }
        request(OkRequestConstants.CODE_API_EXPERIENCES);
//        request(OkRequestConstants.CODE_SHARE_RELATED);
//        request(OkRequestConstants.CODE_COMMENTS_FIRSTLEVEL);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        sensorManager.unregisterListener(sensorEventListener);
    }

    @OnClick({R.id.detail_navi_back})
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.detail_navi_back:
                if (from != null) {
                    Intent i = new Intent();
                    i.setClass(ExperiencesDetailActivity.this, AskDogActivity.class);
                    startActivity(i);
                }
                JCVideoPlayer.releaseAllVideos();
                finish();
                break;
        }
    }

    private void initView() {
        navi_title.setText("详情");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }

    private void expandRecommend() {
        tView.expandNode(recommendRoot);
        for (TreeNode node : recommendRoot.getChildren()) {
            tView.expandNode(node);
        }
    }

    @Override
    public void HeaderBtnClickListener(int type) {
        boolean isLogin = PreferencesUtils.getBoolean(this, ConstantUtils.IS_LOGIN);
        if (!isLogin) {
            Intent intent = new Intent();
            intent.setClass(ExperiencesDetailActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            if (type == ConstantUtils.UP) {
                request(OkRequestConstants.CODE_VOTE_UP);
            } else if (type == ConstantUtils.DOWN) {
                request(OkRequestConstants.CODE_VOTE_DOWN);
            } else if (type == ConstantUtils.UP_DOWN_CANCEL) {
                request(OkRequestConstants.CODE_VOTE_DOWN_UP_CANCEL);
            } else if (type == ConstantUtils.ORDER) {
                request(OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION);
            } else if (type == ConstantUtils.ORDER_CANCEL) {
                request(OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION_CANCEL);
            }
        }
    }

    @Override
    public void OnRealeaseClick(TreeNode node, String content, Object holder) {
        boolean isLogin = PreferencesUtils.getBoolean(this,ConstantUtils.IS_LOGIN,false);
        if (!isLogin) {
            Intent intent = new Intent();
            intent.setClass(ExperiencesDetailActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            //1、构建评论note
            mAnswerContent = content;
            if (TextUtils.isEmpty(mAnswerContent)) {
                NToast.shortToast(this, "评论内容不能为空");
            } else {
                mAnswerNode = recommendRoot;
                mAnswerId = authorId;
                request(OkRequestConstants.CODE_COMMENTS_ANSWER);
            }
        }
    }

    @Override
    public void OnItemClick(Object holder, int type) {

    }

    @Override
    public void OnItemClickListerner(int type) {
        Intent mIntent = new Intent();
        if (type == ConstantUtils.DETAIL_HEADER_USER_ITEM) {
            UserBean user = new UserBean();
            user.id = mExperiencesBean.author.id;
            user.avatar = mExperiencesBean.author.avatar;
            user.name = mExperiencesBean.author.name;
            user.signature = mExperiencesBean.author.signature;


            mIntent.setClass(ExperiencesDetailActivity.this, PersonalChannelActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", user);

            mIntent.putExtras(bundle);
            startActivity(mIntent);
        } else if (type == ConstantUtils.DETAIL_HEADER_CHANNEL_ITEM) {
            mIntent.putExtra("channelId", mExperiencesBean.channel.id);
            mIntent.setClass(ExperiencesDetailActivity.this, ChannelHomeActivity.class);
            startActivity(mIntent);
        } else if (type == ConstantUtils.PALY_VIDEO) {
            //TODO
            /**
             * 1 是否收费
             * 2 收费 是否登录
             */

            try {
                if (mExperiencesBean.price == 0 || mExperiencesBean.mine) {
                    mIntent.setClass(ExperiencesDetailActivity.this, WebViewActivity.class);
                    mIntent.putExtra("url", mExperiencesBean.content.transcode_videos.get(0).url);
                    mIntent.putExtra("id", authorId);
                    startActivity(mIntent);
                } else {
                    NToast.shortToast(this, "收费视频");
                    request(OkRequestConstants.CODE_ORDERS_EXPERIENCE);
                }

            } catch (Exception e) {

            }
        } else if (type == ConstantUtils.SHARE) {
            //TODO
            mIntent.setClass(ExperiencesDetailActivity.this, ShareMessageActivity.class);
            mIntent.putExtra(ConstantUtils.TITLE, mExperiencesBean.subject);
            mIntent.putExtra(ConstantUtils.IMAGE, mExperiencesBean.thumbnail);
            if (mExperiencesBean.content.type.equalsIgnoreCase("VIDEO")) {
                mIntent.putExtra(ConstantUtils.TEXT, "视频来自ASKDOG经验分享社区");
                mIntent.putExtra(ConstantUtils.URL, ConstantUtils.WEBVIEW_VIDEO_BASE_URL + mExperiencesBean.id);
            } else {
                mIntent.putExtra(ConstantUtils.TEXT, "图文来自ASKDOG经验分享社区");
                mIntent.putExtra(ConstantUtils.URL, ConstantUtils.WEBVIEW_TEXT_BASE_URL + mExperiencesBean.id);
            }
            startActivity(mIntent);
        }
    }

    @Override
    public void expandSecondList(TreeNode nodeLevel1, boolean isExpand, String sId) {
        if (!isExpand) {//收起
            List<TreeNode> children = nodeLevel1.getChildren();
            int size = children.size();
            for (int i = size - 1; i > 1; i--) {
                tView.removeNode(children.get(i));
            }
        } else {
            //查看更多
            List<TreeNode> nodes = treeNodeListMap.get(nodeLevel1);
            if (nodes.size() > 2) {//说明已经请求过数据直接展开
                for (int i = 2; i < nodes.size(); i++) {
                    TreeNode nodeLevel2 = nodes.get(i);
                    tView.addNode(nodeLevel1, nodeLevel2);
                }
            } else {//发送数据请求
                mCurrentNode = nodeLevel1;
                commentId = sId;
                request(OkRequestConstants.CODE_COMMENTS_SECONDLEVEL);
            }
        }

    }

    @Override
    public void onLoadMoreClick() {
        page++;
        request(OkRequestConstants.CODE_SHARE_RELATED);
    }

    @Override
    public void onAnswerCommend(TreeNode node, String id, String reply_comment_id) {
        boolean isLogin = PreferencesUtils.getBoolean(this, ConstantUtils.IS_LOGIN);
        if (!isLogin) {
            Intent intent = new Intent();
            intent.setClass(ExperiencesDetailActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            mAnswerNode = node;
            mAnswerId = id;
            this.reply_comment_id = reply_comment_id;
            showInputDialog();
        }
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(mContext);
        if (requestCode == OkRequestConstants.CODE_API_EXPERIENCES) {
            return action.getExperiencesInfo(authorId);
        } else if (requestCode == OkRequestConstants.CODE_SHARE_RELATED) {
            return action.getSearchShareRelated("experience_related", authorId, page, PAGESIZE);
        } else if (requestCode == OkRequestConstants.CODE_VOTE_UP) {
            return action.postVoteExperice(authorId, "UP");
        } else if (requestCode == OkRequestConstants.CODE_VOTE_DOWN) {
            return action.postVoteExperice(authorId, "DOWN");
        } else if (requestCode == OkRequestConstants.CODE_VOTE_DOWN_UP_CANCEL) {
            return action.deleteVoteExperice(authorId);
        } else if (requestCode == OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION) {
            return action.postChannelSubscription(mExperiencesBean.channel.id);
        } else if (requestCode == OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION_CANCEL) {
            return action.deleteChannelSubscription(mExperiencesBean.channel.id);
        } else if (requestCode == OkRequestConstants.CODE_COMMENTS_FIRSTLEVEL) {
            return action.getCommentsFirstLevelList(authorId, 0, 20);
        } else if (requestCode == OkRequestConstants.CODE_COMMENTS_SECONDLEVEL) {
            return action.getCommentsSecondLevelList(commentId, 0, 20);
        } else if (requestCode == OkRequestConstants.CODE_COMMENTS_ANSWER) {
            return action.postCommendAnswer(mAnswerContent, reply_comment_id, mAnswerId);
        } else if(requestCode == OkRequestConstants.CODE_ORDERS_EXPERIENCE){
            return action.getOrdersExperienceStatus(authorId);
        }

        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (requestCode == OkRequestConstants.CODE_API_EXPERIENCES) {
                ExperiencesBean bean = JsonMananger.jsonToBean(result, ExperiencesBean.class);
                createRecommendView(bean);
                request(OkRequestConstants.CODE_SHARE_RELATED);
                request(OkRequestConstants.CODE_COMMENTS_FIRSTLEVEL);
            }else if (requestCode == OkRequestConstants.CODE_SHARE_RELATED) {
                ShareRelatedBean shareBean = JsonMananger.jsonToBean(result, ShareRelatedBean.class);
                createShareRelateView(shareBean);
            }else if (requestCode == OkRequestConstants.CODE_COMMENTS_FIRSTLEVEL) {
                CommentsBean commentsBean = JsonMananger.jsonToBean(result, CommentsBean.class);
                if (recommendRoot.size() > 0) {
                    recommendRoot = new TreeNode(new DetailRecommendHolder.DetailRecommendItem(true,
                            mExperiencesBean.author.avatar, mExperiencesBean.comment_count)).setViewHolder(mDetailRecommendHolder);
                }
                createRecommendListView(commentsBean);
            }else if (requestCode == OkRequestConstants.CODE_COMMENTS_SECONDLEVEL) {
                CommentsBean.CommentsResult[] second = JsonManangergetDtoArrFromJsonArrStr(result, CommentsBean.CommentsResult.class);
                createRecommendSecondListView(second);
            }else if (requestCode == OkRequestConstants.CODE_VOTE_UP || requestCode == OkRequestConstants.CODE_VOTE_DOWN
                    || requestCode == OkRequestConstants.CODE_VOTE_DOWN_UP_CANCEL) {
                UpDownBean mUpDownBean = JsonMananger.jsonToBean(result, UpDownBean.class);
                mDetailHeaderHolder.updateUpDown(mUpDownBean);
            } else if (requestCode == OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION) {
                mDetailHeaderHolder.updateOrderStatus(true);
            } else if (requestCode == OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION_CANCEL) {
                mDetailHeaderHolder.updateOrderStatus(false);
            } else if (requestCode == OkRequestConstants.CODE_COMMENTS_ANSWER) {
                CommonToast toast = new CommonToast(ExperiencesDetailActivity.this);
                toast.setMessage(getResources().getString(R.string.channel_release_success));
                toast.show();
                CommentsBean.CommentsResult commentsBean = JsonMananger.jsonToBean(result, CommentsBean.CommentsResult.class);

                if (TextUtils.isEmpty(reply_comment_id)) {
                    mAnswerId = null;
                    request(OkRequestConstants.CODE_COMMENTS_FIRSTLEVEL);
                } else {
                    mAnswerId = null;
                    reply_comment_id = null;
                    TreeNode ansNode = new TreeNode(buildNode(commentsBean, 2)).setViewHolder(new DetailRecommendItemHolder(this, this));
                    tView.addNode(mAnswerNode, ansNode);
                }
            } else if(requestCode == OkRequestConstants.CODE_ORDERS_EXPERIENCE){
                OrderPayStatusBean mOrderPayStatusBean = JsonMananger.jsonToBean(result, OrderPayStatusBean.class);
                processOrderPayStatus(mOrderPayStatusBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processOrderPayStatus(OrderPayStatusBean mOrderPayStatusBean) {
        if(!mOrderPayStatusBean.pay_status.equalsIgnoreCase("PAID") ){
//            request(OkRequestConstants.CODE_ORDERS_EXPERIENCE_PAY);
            Intent intent = new Intent();
            intent.setClass(ExperiencesDetailActivity.this, WXOrdersPayActivity.class);
            intent.putExtra("subject",mExperiencesBean.subject);
            intent.putExtra("experienceId",mExperiencesBean.id);
            intent.putExtra("amount",String.valueOf(mExperiencesBean.price));
            intent.putExtra("content",mExperiencesBean.content.content);
            startActivity(intent);
        }else{
           enterPlayVideo();
        }
    }

    private void enterPlayVideo() {
        NToast.shortToast(this,"enterPlayVideo...");
    }


    private CommentsBean.CommentsResult[] JsonManangergetDtoArrFromJsonArrStr(String jsonArrStr, Class<CommentsBean.CommentsResult> cls) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jsonArr = parser.parse(jsonArrStr).getAsJsonArray();
        CommentsBean.CommentsResult[] objArr = new CommentsBean.CommentsResult[jsonArr.size()];
        for (int i = 0; i < jsonArr.size(); i++) {
            objArr[i] = gson.fromJson(jsonArr.get(i), cls);
        }
        return objArr;
    }

    private void createShareRelateView(ShareRelatedBean bean) {

        if (bean != null) {
            mSharebean = bean;
            if (loadMore != null) {
                tView.removeNode(loadMore);
            }
            for (int i = 0; i < bean.result.size(); i++) {
                ShareRelatedBean.ShareResult result = bean.result.get(i);
                AboutShareItemHolder.ShareItem item = createShareItemData(result);
                TreeNode node = new TreeNode(item).setViewHolder(new AboutShareItemHolder(this));
                if (shareRoot == null) {
                    shareRoot = new TreeNode(new AboutShareHolder.DetailAboutShareItem()).setViewHolder(new AboutShareHolder(this));
                }
                tView.addNode(shareRoot, node);
            }
            int count = shareRoot.size();
            if (mSharebean.total == count) {
                loadMore = new TreeNode(new LoadMoreHolder.LoadMoreItem(true)).setViewHolder(new LoadMoreHolder(this, this));
            } else {
                loadMore = new TreeNode(new LoadMoreHolder.LoadMoreItem(false)).setViewHolder(new LoadMoreHolder(this, this));
            }
//            shareRoot.addChild(loadMore);
            tView.addNode(shareRoot, loadMore);
        }
    }

    private AboutShareItemHolder.ShareItem createShareItemData(ShareRelatedBean.ShareResult result) {
        AboutShareItemHolder.ShareItem item = new AboutShareItemHolder.ShareItem();
        item.share_author_name = result.author.name;
        item.share_channel_name = result.channel.channel_name;
        item.share_content_pic = result.content_pic_url;
        item.share_creation_date = result.creation_date;
        item.share_subject = result.subject;
        item.share_view_count = result.view_count;
        item.id = result.id;
        return item;
    }

    private void createRecommendView(final ExperiencesBean bean) {
        mExperiencesBean = bean;
        DetailHeaderHolder.DetailHeaderItem mDetailHeaderItem = buildDetailHeaderItem(bean);
        mDetailHeaderHolder = new DetailHeaderHolder(ExperiencesDetailActivity.this, this);
        myProfile = new TreeNode(mDetailHeaderItem).setViewHolder(mDetailHeaderHolder);
        mDetailRecommendHolder = new DetailRecommendHolder(this, this);
        recommendRoot = new TreeNode(new DetailRecommendHolder.DetailRecommendItem(true,
                bean.author.avatar, bean.comment_count)).setViewHolder(mDetailRecommendHolder);

        if (shareRoot == null) {
            shareRoot = new TreeNode(new AboutShareHolder.DetailAboutShareItem()).setViewHolder(new AboutShareHolder(this));
        }
//        share = new TreeNode(new AboutShareHolder.DetailAboutShareItem()).setViewHolder(new AboutShareHolder(this));
        final TreeNode root = TreeNode.root();
        root.addChildren(myProfile, shareRoot, recommendRoot);
        tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setUseAutoToggle(false);
//        shareRoot.setClickListener(this);
        tView.setDefaultNodeClickListener(ExperiencesDetailActivity.this);
        containerView.addView(tView.getView());

    }

    private DetailHeaderHolder.DetailHeaderItem buildDetailHeaderItem(ExperiencesBean bean) {
        DetailHeaderHolder.DetailHeaderItem mDetailHeaderItem = new DetailHeaderHolder.DetailHeaderItem();
        mDetailHeaderItem.type = bean.content.type;
        mDetailHeaderItem.content = bean.content.content;
        mDetailHeaderItem.avatarUrl = bean.author.avatar;
        mDetailHeaderItem.upno = bean.up_vote_count;
        mDetailHeaderItem.downno = bean.down_vote_count;
        mDetailHeaderItem.name = bean.author.name;
        mDetailHeaderItem.userId = bean.author.id;
        mDetailHeaderItem.time = String.valueOf(bean.creation_time);
        mDetailHeaderItem.reviewtimes = bean.view_count;
        mDetailHeaderItem.channel_name = bean.channel.name;
        mDetailHeaderItem.id = bean.id;
        mDetailHeaderItem.isUp = bean.up_voted;
        mDetailHeaderItem.isDown = bean.down_voted;
        if (mDetailHeaderItem.type.equalsIgnoreCase("VIDEO")) {
            mDetailHeaderItem.transcode_videos = bean.content.transcode_videos;
            mDetailHeaderItem.thumbnail = bean.thumbnail;
        }
        mDetailHeaderItem.channel_id = bean.channel.id;
        mDetailHeaderItem.channel_mine = bean.channel.mine;
        mDetailHeaderItem.channel_subscribable = bean.channel.subscribable;
        mDetailHeaderItem.channel_subscribed = bean.channel.subscribed;
        mDetailHeaderItem.channel_subscriber_count = bean.channel.subscriber_count;
        return mDetailHeaderItem;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    private void initData() {
        Intent intent = getIntent();
        authorId = intent.getExtras().getString("authorId");
        from = intent.getExtras().getString("from");//发布图文来源，返回到home页用
//        expericeId = intent.getExtras().getString("expericeId");
    }


    private void createRecommendListView(CommentsBean bean) {
        if (bean == null) return;
        int size = bean.result.size();
        mDetailRecommendHolder.updateCount(bean.total);//更新评论条目数
        for (int idx = 0; idx < size; idx++) {
            CommentsBean.CommentsResult _recommend = bean.result.get(idx);
            TreeNode LevelFirst = new TreeNode(buildNode(_recommend, 1)).setViewHolder(new DetailRecommendItemHolder(this, this));

            CommentsBean subComments = _recommend.comments;
            int innerSize = subComments.result.size();
            List<TreeNode> levelSecondList = new ArrayList<>();
            for (int in = 0; in < innerSize; in++) {
                CommentsBean.CommentsResult sub_recommend = subComments.result.get(in);
                TreeNode levelSecond = new TreeNode(buildNode(sub_recommend, 2)).setViewHolder(new DetailRecommendItemHolder(this, this));
                LevelFirst.addChildren(levelSecond);
                levelSecondList.add(levelSecond);
            }
            recommendRoot.addChildren(LevelFirst);
            treeNodeListMap.put(LevelFirst, levelSecondList);
        }
        expandRecommend();
    }

    private void createRecommendSecondListView(CommentsBean.CommentsResult[] bean) {
        if (bean == null) return;
        int size = bean.length;
        List<TreeNode> children = mCurrentNode.getChildren();

        for (int idx = children.size(); idx < size; idx++) {
            CommentsBean.CommentsResult _recommend = bean[idx];
            TreeNode second = new TreeNode(buildNode(_recommend, 2)).setViewHolder(new DetailRecommendItemHolder(this, this));
            tView.addNode(mCurrentNode, second);
            treeNodeListMap.get(mCurrentNode).add(second);
        }
    }

    private DetailRecommendItemHolder.DetailRecommendSubItem buildNode(CommentsBean.CommentsResult recommend, int level) {
        DetailRecommendItemHolder.DetailRecommendSubItem item = new DetailRecommendItemHolder.DetailRecommendSubItem();
        item.avatarUrl = recommend.commenter.avatar;
        item.mContent = recommend.content;
        item.uName = recommend.commenter.name;
        item.mTime = String.valueOf(recommend.creation_time);
        item.total = recommend.comments.total;
        item.reply_comment_id = recommend.reply_comment_id;
        item.level = level;
        item.owner_id = recommend.owner_id;
        item.id = recommend.id;
        return item;
    }


    @Override
    public void onClick(TreeNode node, Object value) {

        if (value instanceof AboutShareItemHolder.ShareItem) {
            AboutShareItemHolder.ShareItem shareItem = (AboutShareItemHolder.ShareItem) value;
            Intent i = new Intent();
            i.setClass(ExperiencesDetailActivity.this, ExperiencesDetailActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("authorId", shareItem.id);
            startActivity(i);
        } else if (value instanceof ExperiencesBean.ExperiencesRecommend) {
            ExperiencesBean.ExperiencesRecommend _recommend = (ExperiencesBean.ExperiencesRecommend) value;
            NToast.shortToast(this, _recommend.content);
        }
    }

    private void showInputDialog() {
        new InputDialog.Builder(this)
                .setTitle("评论回复")
                .setInputMaxWords(200)
                .setInputHint("请输入评论内容")
                .setPositiveButton("确定", new InputDialog.ButtonActionListener() {
                    @Override
                    public void onClick(CharSequence inputText) {
                        mAnswerContent = inputText.toString();
                        if (!TextUtils.isEmpty(mAnswerContent))
                            request(OkRequestConstants.CODE_COMMENTS_ANSWER);
                    }
                })
                .setNegativeButton("取消", new InputDialog.ButtonActionListener() {
                    @Override
                    public void onClick(CharSequence inputText) {
                        // TODO

                    }
                })
                .setOnCancelListener(new InputDialog.OnCancelListener() {
                    @Override
                    public void onCancel(CharSequence inputText) {
                        // TODO
                    }
                })
                .interceptButtonAction(new InputDialog.ButtonActionIntercepter() { // 拦截按钮行为
                    @Override
                    public boolean onInterceptButtonAction(int whichButton, CharSequence inputText) {
                        if ("/sdcard/my".equals(inputText) && whichButton == DialogInterface.BUTTON_POSITIVE) {
                            // TODO 此文件夹已存在，在此做相应的提示处理
                            // 以及return true拦截此按钮默认行为
                            return true;
                        }
                        return false;
                    }
                })
                .show();
    }


}
