package com.askdog.android.view.widget.holder;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.activity.video.CustomVideoPlayer;
import com.askdog.android.interf.ICallback;
import com.askdog.android.model.ExperiencesBean;
import com.askdog.android.model.UpDownBean;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.DateUtils;
import com.askdog.android.utils.NToast;
import com.askdog.android.utils.transformations.glide.CropCircleTransformation;
import com.askdog.android.utils.transformations.glide.CropTransformation;
import com.bumptech.glide.Glide;
import com.unnamed.b.atv.model.TreeNode;
import com.wx.goodview.GoodView;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Created by Administrator on 2016/8/5.
 */
public class DetailHeaderHolder extends TreeNode.BaseNodeViewHolder<DetailHeaderHolder.DetailHeaderItem> implements View.OnClickListener {
    private final ICallback callback;
    private final String BASE_MD_URL = ConstantUtils.WEBVIEW_TEXT_BASE_URL;
    private GoodView goodView;
    private TextView name;
    private TextView time;
    private TextView reviewtimes;
    private TextView channel_name;
    private TextView upno;
    private TextView downno;
    private WebView markdownView;
    private ImageView avatarView;
    private ImageView up;
    private ImageView down;
    private ImageView share;
    private DetailHeaderItem mItemValue;
    private TextView subscribe_no;
    private TextView subscribe_text;
    private CustomVideoPlayer jcVideoPlayerStandard;

    public DetailHeaderHolder(Context context, ICallback callback) {
        super(context);
        this.callback = callback;
    }

    @Override
    public View createNodeView(TreeNode node, DetailHeaderItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view;

        view = inflater.inflate(R.layout.layout_node_header, null, false);

        markdownView = (WebView) view.findViewById(R.id.experice_markdown);
        jcVideoPlayerStandard = (CustomVideoPlayer)view.findViewById(R.id.custom_videoplayer_standard);
        avatarView = (ImageView) view.findViewById(R.id.detail_avatar);
        goodView = new GoodView(context);
        name = (TextView) view.findViewById(R.id.detail_name);
        time = (TextView) view.findViewById(R.id.detail_sharetime);
        reviewtimes = (TextView) view.findViewById(R.id.detail_reviewtimes);

        channel_name = (TextView) view.findViewById(R.id.detail_channel_name);
        upno = (TextView) view.findViewById(R.id.detail_upno);
        downno = (TextView) view.findViewById(R.id.detail_downno);

        subscribe_no = (TextView) view.findViewById(R.id.detail_subscribe_no);
        subscribe_text = (TextView) view.findViewById(R.id.detail_subscribe_text);
        up = (ImageView) view.findViewById(R.id.detail_thumbsup);
        down = (ImageView) view.findViewById(R.id.detail_thumbsdown);
        share = (ImageView) view.findViewById(R.id.detail_share);
        RelativeLayout header_user = (RelativeLayout) view.findViewById(R.id.detail_header_user);
        RelativeLayout header_channel = (RelativeLayout) view.findViewById(R.id.detail_header_channel);
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        share.setOnClickListener(this);
        subscribe_text.setOnClickListener(this);
        header_user.setOnClickListener(this);
        header_channel.setOnClickListener(this);
        setDefaultValue(value);
        return view;
    }

    //    private boolean isvote = false;
    public void updateOrderStatus(boolean flg) {
        if (flg) {
            subscribe_text.setText(context.getResources().getString(R.string.detail_subscribe_off));
            subscribe_text.setSelected(true);
            subscribe_no.setText(String.valueOf(mItemValue.channel_subscriber_count + 1));
        } else {
            subscribe_text.setText(context.getResources().getString(R.string.detail_subscribe_on));
            subscribe_text.setSelected(false);
            subscribe_no.setText(String.valueOf(mItemValue.channel_subscriber_count));
        }
    }

    public void updateUpDown(UpDownBean bean) {

        if (TextUtils.isEmpty(bean.vote_direction)) {
            up.setImageResource(R.drawable.detail_praise_normal);
            down.setImageResource(R.drawable.detail_tread_normal);
            down.setSelected(false);
            up.setSelected(false);
        } else {
            if (bean.vote_direction.equalsIgnoreCase("UP")) {
                up.setImageResource(R.drawable.detail_praise_pressed);
                down.setImageResource(R.drawable.detail_tread_normal);
                up.setSelected(true);
                down.setSelected(false);
            }
            if (bean.vote_direction.equalsIgnoreCase("DOWN")) {
                up.setImageResource(R.drawable.detail_praise_normal);
                down.setImageResource(R.drawable.detail_tread_pressed);
                down.setSelected(true);
                up.setSelected(false);
            }
        }

        upno.setText("赞" + String.valueOf(bean.up_vote_count));
        downno.setText("踩" + String.valueOf(bean.down_vote_count));


    }

    private void setDefaultValue(DetailHeaderItem value) {
        mItemValue = value;
        name.setText(value.name);
        time.setText(DateUtils.getTimeState(String.valueOf(value.time), ""));
        reviewtimes.setText("浏览数 " + value.reviewtimes);
        channel_name.setText(value.channel_name);
        if (value.isUp) {
            up.setSelected(true);
            up.setImageResource(R.drawable.detail_praise_pressed);
        }
        if (value.isDown) {
            down.setSelected(true);
            down.setImageResource(R.drawable.detail_tread_pressed);
        }
        upno.setText("赞" + String.valueOf(value.upno));
        downno.setText("踩" + String.valueOf(value.downno));
        subscribe_no.setText(String.valueOf(value.channel_subscriber_count));
        if (value.channel_subscribed) {
            subscribe_text.setText(context.getResources().getString(R.string.detail_subscribe_off));
            subscribe_text.setSelected(true);
        } else {
            subscribe_text.setText(context.getResources().getString(R.string.detail_subscribe_on));
            subscribe_text.setSelected(false);
        }
        if (value.type.equalsIgnoreCase("TEXT")) {
            String url = BASE_MD_URL + value.id;
            jcVideoPlayerStandard.setVisibility(View.GONE);
            markdownView.setVisibility(View.VISIBLE);
            DisplayMetrics metrics = new DisplayMetrics();
            WebSettings settings = markdownView.getSettings();

            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            markdownView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // TODO Auto-generated method stub
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
            });

            settings.setSupportZoom(true);
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setBuiltInZoomControls(true);//support zoom
            settings.setUseWideViewPort(true);// 这个很关键
            settings.setLoadWithOverviewMode(true);
            settings.setDefaultTextEncodingName("utf-8");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } else {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            }
            markdownView.loadUrl(url);
        } else {
            jcVideoPlayerStandard.setVisibility(View.VISIBLE);
            markdownView.setVisibility(View.GONE);

            setUpJcVideoPlayer(value.transcode_videos.get(0),value.thumbnail,value.name);
        }
        Glide.with(context)
                .load(value.avatarUrl)
                .error(R.drawable.avatar_default)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(avatarView);
    }

    public void setUpJcVideoPlayer(ExperiencesBean.ExperiencesContent.TranscodeVideos value,String thumbnail,String content)
    {
        jcVideoPlayerStandard.setUp(value.url, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, content);
        Glide.with(context)
                .load(thumbnail)
                .error(R.drawable.ic_empty_small)
                .bitmapTransform(new CropTransformation(context))
                .centerCrop()
                .into(jcVideoPlayerStandard.thumbImageView);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.detail_thumbsdown:
                if (down.isSelected()) {
                    callback.HeaderBtnClickListener(ConstantUtils.UP_DOWN_CANCEL);
                } else {
                    callback.HeaderBtnClickListener(ConstantUtils.DOWN);
                }
                break;
            case R.id.detail_thumbsup:
                if (up.isSelected()) {
                    callback.HeaderBtnClickListener(ConstantUtils.UP_DOWN_CANCEL);
                } else {
                    callback.HeaderBtnClickListener(ConstantUtils.UP);
                }

                break;
            case R.id.detail_share:
                callback.OnItemClickListerner(ConstantUtils.SHARE);
                break;
            case R.id.detail_header_user:
                callback.OnItemClickListerner(ConstantUtils.DETAIL_HEADER_USER_ITEM);
                break;
            case R.id.detail_header_channel:
                callback.OnItemClickListerner(ConstantUtils.DETAIL_HEADER_CHANNEL_ITEM);
                break;
            case R.id.detail_subscribe_text:
                if (subscribe_text.isSelected()) {
                    callback.HeaderBtnClickListener(ConstantUtils.ORDER_CANCEL);
                } else {
                    callback.HeaderBtnClickListener(ConstantUtils.ORDER);
                }
                break;
        }
    }

    @Override
    public void toggle(boolean active) {
    }

    public static class DetailHeaderItem {
        public String avatarUrl;
        public String name;
        public String time;
        public long reviewtimes;
        public String channel_name;
        public int upno;
        public int downno;
        public boolean isUp;
        public boolean isDown;
        public String userId;
        public String type;
        public String content;
        public String id;
        public String videoUrl;
        public String thumbnail;
        public String channel_id;
        public boolean channel_mine;
        public boolean channel_subscribable;
        public boolean channel_subscribed;
        public int channel_subscriber_count;

        public ArrayList<ExperiencesBean.ExperiencesContent.TranscodeVideos> transcode_videos;
        public static class TranscodeVideos{
            public int width;                                   // 视频宽度 （单位 px）
            public int height;                                 // 视频高度 （单位 px）
            public float duration;                              // 视频时长 （单位 s）
            public String format;                               // 视频格式
            public long file_size;                              // 视频文件大小 （单位 bit）
            public float bit_rate;                              // 视频码率 （单位 kbps）
            public String url;                                  // 视频URL地址
            public String resolution;                           // 视频清晰度标识，UHD(超清), FHD(全高清), HD(高清), SD(标清), LD(流畅)
        }

        public DetailHeaderItem() {
        }
    }
}
