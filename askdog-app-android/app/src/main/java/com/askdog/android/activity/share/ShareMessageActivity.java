package com.askdog.android.activity.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.utils.ConstantUtils;
import com.mob.tools.utils.UIHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareMessageActivity extends BaseActivity implements View.OnClickListener,
        PlatformActionListener, Callback {

    private String share_title = "";
    private String share_text = "";
    private String share_image = "";
    private String share_url = "";

    /**
     * 将action转换为String
     */
    public static String actionToString(int action) {
        switch (action) {
            case Platform.ACTION_AUTHORIZING:
                return "ACTION_AUTHORIZING";
            case Platform.ACTION_GETTING_FRIEND_LIST:
                return "ACTION_GETTING_FRIEND_LIST";
            case Platform.ACTION_FOLLOWING_USER:
                return "ACTION_FOLLOWING_USER";
            case Platform.ACTION_SENDING_DIRECT_MESSAGE:
                return "ACTION_SENDING_DIRECT_MESSAGE";
            case Platform.ACTION_TIMELINE:
                return "ACTION_TIMELINE";
            case Platform.ACTION_USER_INFOR:
                return "ACTION_USER_INFOR";
            case Platform.ACTION_SHARE:
                return "ACTION_SHARE";
            default: {
                return "UNKNOWN";
            }
        }
    }

    /**
     * 从服务器取图片
     *
     * @param url
     * @return
     */
    public static Drawable getHttpDrawable(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BitmapDrawable(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_message);
        ButterKnife.bind(this);
        ShareSDK.initSDK(this);
        initData();

    }

    private void initData() {
        share_title = getIntent().getStringExtra(ConstantUtils.TITLE);
        share_text = getIntent().getStringExtra(ConstantUtils.TEXT);
        share_image = getIntent().getStringExtra(ConstantUtils.IMAGE);
        share_url = getIntent().getStringExtra(ConstantUtils.URL);
//        share_title = "度娘好";
//        share_text = "度娘在哪里，请点击这里好么？";
//        share_image = "http://www.cctime.com/upLoadFile/2012/1/18/2012118101653355.jpg";
//        share_url = "http://image.baidu.com/i?ct=503316480&z=0&tn=baiduimagedetail&ipn=d&cl=2&cm=1&sc=0&lm=-1&fr=ala2&pn=0&rn=1&di=0&ln=24&word=%B6%C8%C4%EF&objurl=http%3A%2F%2Fimage1%2Enbd%2Ecom%2Ecn%2Fuploads%2Farticles%2Fthumbnails%2F4454%2FQQ%5F%5F%5F%5F%5F%5F20120110102334%2Ex%5Flarge%2Ejpg#pn1&-1&di0&objURLhttp%3A%2F%2Fwww.cctime.com%2FupLoadFile%2F2012%2F1%2F18%2F2012118101653355.jpg&fromURLippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bvvpt4j_z%26e3Bv54AzdH3Fip4sAzdH3Fda8d-8-8bAzdH3Fda8d88b8a8mc0dbb8_z%26e3Bip4&W400&H600&T0&S84&TPjpg";

    }

    /**
     * 定义一个函数将dp转换为像素
     *
     * @param dp
     * @return
     */
    public int Dp2Px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale / 2);
    }

    /**
     * 分享到朋友圈
     */
    private void share_CircleFriend() {
        ShareSDK.initSDK(this);
        Platform circle = ShareSDK.getPlatform(WechatMoments.NAME);
//        if (!circle.isValid()) {
//            InfoMessage.showMessage(this, "分享失败，请先安装微信");
//            return;
//        }


        cn.sharesdk.wechat.moments.WechatMoments.ShareParams sp = new cn.sharesdk.wechat.moments.WechatMoments.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
        sp.setTitle(share_title);
        sp.setText(share_text);
        sp.setImageData(null);
        sp.setImageUrl(share_image);
        sp.setImagePath("");
        sp.setUrl(share_url);

        circle.setPlatformActionListener(this); // 设置分享事件回调
        // 执行图文分享
        circle.share(sp);
    }

    /**
     * 分享到微信好友
     */
    private void share_WxFriend() {
        Platform circle = ShareSDK.getPlatform(Wechat.NAME);
//        if (!circle.isValid()) {
//            InfoMessage.showMessage(this, "分享失败，请先安装微信");
//            return;
//        }

        ShareSDK.initSDK(this);

        ShareParams sp = new ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
        sp.setTitle(share_title);
        sp.setText(share_text);
        sp.setImageData(null);
        sp.setImageUrl(share_image);
        sp.setImagePath("");
        sp.setUrl(share_url);

        circle.setPlatformActionListener(this); // 设置分享事件回调
        // 执行图文分享
        circle.share(sp);
    }

    /**
     * 分享到QQ空间
     */
    private void share_Qzone() {

        ShareSDK.initSDK(this);

        cn.sharesdk.tencent.qzone.QZone.ShareParams sp = new cn.sharesdk.tencent.qzone.QZone.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
        sp.setTitle(share_title);
        sp.setText(share_text);
        sp.setTitleUrl(share_url);
        sp.setImageUrl(share_image);
        sp.setImagePath("");
        sp.setSite("273业管");
        sp.setSite("273业管");

        Platform qzone = ShareSDK.getPlatform(this, QZone.NAME);
        qzone.setPlatformActionListener(this); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp);
    }

    /**
     * 分享到新浪微博
     */
    private void share_SinaWeibo() {

        ShareSDK.initSDK(this);

        cn.sharesdk.sina.weibo.SinaWeibo.ShareParams sp = new cn.sharesdk.sina.weibo.SinaWeibo.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
        sp.setText(share_text + share_url);
        sp.setImageUrl(share_image);
        sp.setImagePath("");

        Platform weibo = ShareSDK.getPlatform(this, SinaWeibo.NAME);
        weibo.setPlatformActionListener(this); // 设置分享事件回调
        // 执行图文分享
        weibo.share(sp);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        // 取消分享菜单的统计
//        ShareSDK.logDemoEvent(2, null);
        // 释放资源空间
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }

    public void onComplete(Platform plat, int action,
                           HashMap<String, Object> res) {

        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    public void onCancel(Platform palt, int action) {
        Message msg = new Message();
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = palt;
        UIHandler.sendMessage(msg, this);
    }

    public void onError(Platform palt, int action, Throwable t) {
        t.printStackTrace();
        Message msg = new Message();
        msg.arg1 = 2;
        msg.arg2 = action;
        UIHandler.sendMessage(msg, this);
    }

    public boolean handleMessage(Message msg) {
        String text = actionToString(msg.arg2);

        switch (msg.arg1) {
            case 1:
                // 成功
                text = "分享成功";
                InfoMessage.showMessage(this, text);
                ShareMessageActivity.this.finish();

                break;
            case 2:
                // 失败
                text = "分享失败";
                break;
            case 3:
                // 取消
                text = "分享已取消";
                break;
        }
        InfoMessage.showMessage(this, text);
        return false;
    }

    @OnClick({R.id.share_message_cancel_ll
            ,R.id.linearLayout_ciclefriend
            ,R.id.linearLayout_qzone
            ,R.id.linearLayout_weixin
            ,R.id.LinearLayout_sinaweibo
    })
    @Override
    public void onClick(View v) {
        // 清空之前可能存在的分项数据

        switch (v.getId()) {
            // 取消
            case R.id.share_message_cancel_ll:
                ShareMessageActivity.this.finish();
                break;
            // 分享到微信朋友圈
            case R.id.linearLayout_ciclefriend:
                share_CircleFriend();
                break;
            // 分享到QQ空间
            case R.id.linearLayout_qzone:
                share_Qzone();
                break;
            // 分享到微信好友
            case R.id.linearLayout_weixin:
                share_WxFriend();
                break;
            // 分享到新浪微博
            case R.id.LinearLayout_sinaweibo:
                share_SinaWeibo();
                break;

            default:
                break;
        }
    }
}
