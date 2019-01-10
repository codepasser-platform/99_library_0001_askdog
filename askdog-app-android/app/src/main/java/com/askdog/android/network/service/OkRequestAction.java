/*
    ShengDao Android Client, DemoAction
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.askdog.android.network.service;


import android.content.Context;
import android.util.Pair;
import com.askdog.android.model.ProfileEditBean;
import com.askdog.android.network.okhttp.request.OkHttpRequest;
import com.askdog.android.network.okhttp.request.OkHttpUploadProgressRequest;
import com.askdog.android.utils.JsonMananger;
import com.google.gson.JsonObject;
import com.squareup.okhttp.Response;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * [本地数据接口测试类]
 *
 * @author wyong
 * @version 1.0
 * @date 2015-12-02
 **/
public class OkRequestAction extends BaseAction {
    private OkHttpRequest mOkHttpRequest;

    /**
     * 构造方法
     *
     * @param mContext
     */
    public OkRequestAction(Context mContext) {
        super(mContext);
    }

    public void cancelRequestByTag() {
        if (mOkHttpRequest != null)//没有网络请求的页面为空
            mOkHttpRequest.cancel();
    }

    /**
     * [用户注册]
     *
     * @param name
     * @param password
     * @param mail
     * @throws Exception
     */
    public Response register(String name, String password, String mail) throws Exception {
        String url = getURL(OkRequestConstants.API_REGISTER);

        JsonObject object = new JsonObject();
        object.addProperty("name", name);
        object.addProperty("mail", mail);
        object.addProperty("password", password);
        String json = object.toString();


        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");

        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(json).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * [用户手机注册]
     *
     * @param phone
     * @throws Exception
     */
    public Response registerUsePhone(String name, String password, String phone, String code) throws Exception {
        String url = getURL(OkRequestConstants.API_PHONE_REGISTER);

        JsonObject object = new JsonObject();
        object.addProperty("name", name);
        object.addProperty("password", password);
        object.addProperty("phone", phone);
        object.addProperty("code", code);
        String json = object.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");

        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(json).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * [用户手机发送验证码]
     *
     * @param phone
     * @throws Exception
     */
    public Response sendMessageUsePhone(String phone) throws Exception {
        String url = getURL(OkRequestConstants.API_PHONE_SEND_MESSAGE);

        JsonObject object = new JsonObject();
        object.addProperty("phone", phone);
        String json = object.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");

        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(json).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * [验证手机号合法性]
     *
     * @param phone
     * @throws Exception
     */
    public Response checkPhone(String phone) throws Exception {
        String url = getURL(OkRequestConstants.API_CHECK_PHONE_NUMBER);

        Map<String, String> param = new HashMap<>();
        param.put("phone", phone);

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/x-www-form-urlencoded");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).content_type("application/json;charset=UTF-8").params(param).headers(headers).url(url).post(mContext);
        return mOkHttpRequest.invoke();

    }

    /**
     * [验证手机验证码]
     *
     * @param phone
     * @throws Exception
     */
    public Response checkSmsCode(String phone,String code) throws Exception {
        String url = getURL(OkRequestConstants.API_CHECK_MESSAGE_NUMBER);

        Map<String, String> param = new HashMap<>();
        param.put("phone", phone);
        param.put("code", code);
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/x-www-form-urlencoded");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).content_type("application/json;charset=UTF-8").params(param).headers(headers).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * [更新手机用户密码]
     *
     * @param phone
     * @throws Exception
     */
    public Response updatePhoneUserPassword(String phone,String code, String password) throws Exception {
        String url = getURL(OkRequestConstants.API_UPDATE_PHONE_USER_PASSWORD);

        JsonObject object = new JsonObject();
        object.addProperty("phone", phone);
        object.addProperty("code", code);
        object.addProperty("password", password);
        String json = object.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");

        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(json).url(url).put(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * [登录前混合各种分类的列表展示]
     * type 查询类型
     * from 起始页码
     * size 每页显示数据条数
     */
    public Response getCategoryList(String type, int from, int size) throws Exception {
//        String[] para = {"type=" + type, "from=" + from, "size=" + size};
        Map<String, String> param = new HashMap<>();
        param.put("type", type);
        param.put("from", String.valueOf(from));
        param.put("size", String.valueOf(size));
        String url = getURL(OkRequestConstants.API_SEARCH);
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 获取经验详细信息
     *
     * @param id
     * @throws Exception
     * @mOkHttpRequest =
     */
    public Response getExperiencesInfo(String id) throws Exception {
        String url = getURL(OkRequestConstants.API_EXPERIENCES) + File.separator + id;
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @throws Exception
     * @mOkHttpRequest =
     */
    public Response login(String username, String password) throws Exception {
        String url = getURL(OkRequestConstants.API_CONFIRM);

        Map<String, String> param = new HashMap<>();
        param.put("username", username);
        param.put("password", password);

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/x-www-form-urlencoded");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).content_type("application/json;charset=UTF-8").params(param).headers(headers).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * userId=30962247440242404&size=2&page=0
     *
     * @param userId
     * @param size
     * @param page
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response getUserChannelOwned(String userId, int size, int page) throws IOException {
        String url = getURL(OkRequestConstants.API_USER_CHANNELS_OWNED);
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("page", String.valueOf(page));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * userId=30962247440242404&size=2&page=0
     *
     * @param userId
     * @param size
     * @param page
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response getUserChannelSubscribed(String userId, int size, int page) throws IOException {
        String url = getURL(OkRequestConstants.API_USER_CHANNELS_SUBSCRIBED);
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("page", String.valueOf(page));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * @param size
     * @param page
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response getUserViewHistory(int page, int size) throws IOException {
        String url = getURL(OkRequestConstants.API_VIEW_HISTORY);
        Map<String, String> param = new HashMap<>();
        param.put("page", String.valueOf(page));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    //type=experience_search&key=视频&from=0&size=20
    public Response getSearchResultList(String type, String key, int from, int size) throws IOException {
        String url = getURL(OkRequestConstants.API_SEARCH);
        Map<String, String> param = new HashMap<>();
        param.put("type", type);
        param.put("key", key);
        param.put("from", String.valueOf(from));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * @param type
     * @param experienceId
     * @param from
     * @param size
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response getSearchShareRelated(String type, String experienceId, int from, int size) throws IOException {
        String url = getURL(OkRequestConstants.API_SEARCH);
        Map<String, String> param = new HashMap<>();
        param.put("type", type);
        param.put("experienceId", experienceId);
        param.put("from", String.valueOf(from));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 赞踩经验API
     *
     * @param experienceId
     * @param direction
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response postVoteExperice(String experienceId, String direction) throws IOException {
        String url = getURL(OkRequestConstants.API_VOTE) + "/" + experienceId + "/vote";
        Map<String, String> param = new HashMap<>();
        param.put("direction", direction);
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").params(param).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response deleteVoteExperice(String experienceId) throws IOException {
        String url = getURL(OkRequestConstants.API_VOTE) + "/" + experienceId + "/vote";
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).url(url).delete(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 获取我创建的频道列表API
     * api/users/me/channels/owned?size=2&page=0
     *
     * @param page
     * @param size
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response getMyOwnedChannel(int page, int size) throws IOException {
        String url = getURL(OkRequestConstants.API_MY_OWNED_CHANNEL);
        Map<String, String> param = new HashMap<>();
        param.put("page", String.valueOf(page));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 获取我订阅的频道列表API
     * api/users/me/channels/owned?size=2&page=0
     *
     * @param page
     * @param size
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response getMySubscribedChannel(int page, int size) throws IOException {
        String url = getURL(OkRequestConstants.API_MY_SUBSCRIBED_CHANNEL);
        Map<String, String> param = new HashMap<>();
        param.put("page", String.valueOf(page));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response getFindPwd(String mail) throws IOException {
        String url = getURL(OkRequestConstants.API_FIND_PWD);
        Map<String, String> param = new HashMap<>();
        param.put("mail", mail);
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 订阅频道API
     *
     * @param channel_id
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response postChannelSubscription(String channel_id) throws IOException {
        String url = getURL(OkRequestConstants.API_CHANNELS_SUBSCRIPTION) + "/" + channel_id + "/subscription";

//        mOkHttpRequest =  new OkHttpRequest.Builder().tag(this).url(url).get(mContext);

        JsonObject object = new JsonObject();
        String json = object.toString();


        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(json).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 订阅频道API
     *
     * @param channel_id
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response deleteChannelSubscription(String channel_id) throws IOException {
        String url = getURL(OkRequestConstants.API_CHANNELS_SUBSCRIPTION) + "/" + channel_id + "/subscription";
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).url(url).delete(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 频道详细信息API
     *
     * @param channel_id
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response getChannelDetailInfo(String channel_id) throws IOException {
        String url = getURL(OkRequestConstants.API_CHANNELS_SUBSCRIPTION) + "/" + channel_id;
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response getChannelExpericencesList(String channelId, int page, int size) throws IOException {
        String url = getURL(OkRequestConstants.API_EXPERIENCES);
        Map<String, String> param = new HashMap<>();
        param.put("channelId", channelId);
        param.put("page", String.valueOf(page));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response getChannelUploadAccessToken(String extention, String type) throws IOException {
        String url = getURL(OkRequestConstants.API_UPLOAD_ACCESS_TOKEN);
        Map<String, String> param = new HashMap<>();
        param.put("extention", extention);
        param.put("type", type);
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response uploadFile2OSS(String filePath, String host, String policy
            , String signature
            , String callback
            , String OSSAccessKeyId
            , String key) throws IOException {
        String url = host;
        Pair<String, File> pair = new Pair<>("file", new File(filePath));
        Pair<String, File>[] pairs = new Pair[1];
        pairs[0] = pair;
        Map<String, String> param = new HashMap<>();
        param.put("policy", policy);
        param.put("signature", signature);
        param.put("callback", callback);
        param.put("OSSAccessKeyId", OSSAccessKeyId);
        param.put("key", key);

        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).files(pairs).params(param).url(url).upload();
        return mOkHttpRequest.invoke();
    }

    public Response uploadFileProgress(String filePath, String host, String policy
            , String signature
            , String callback
            , String OSSAccessKeyId
            , String key, String tag, OkHttpUploadProgressRequest.ProgressListener listener) throws IOException {
        String url = host;

        File file = new File(filePath);
        Map<String, String> param = new HashMap<>();
        param.put("policy", policy);
        param.put("signature", signature);
        param.put("callback", callback);
        param.put("OSSAccessKeyId", OSSAccessKeyId);
        param.put("key", key);

        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).file(file).params(param).tag(tag).url(url).listener(listener).uploadProgress();
        return mOkHttpRequest.invoke();
    }

    public Response uploadFileProgressResponse(String filePath, String host, String policy
            , String signature
            , String callback
            , String OSSAccessKeyId
            , String key, String tag, OkHttpUploadProgressRequest.ProgressListener listener) throws IOException {
        String url = host;

        File file = new File(filePath);
        Map<String, String> param = new HashMap<>();
        param.put("policy", policy);
        param.put("signature", signature);
        param.put("callback", callback);
        param.put("OSSAccessKeyId", OSSAccessKeyId);
        param.put("key", key);

        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).file(file).params(param).tag(tag).url(url).listener(listener).uploadProgressRequest();
        return mOkHttpRequest.invoke();
    }

    public Response postBuildChannel(String name, String description, String cover_image_link_id) throws IOException {
        String url = getURL(OkRequestConstants.API_CHANNELS_SUBSCRIPTION);
//        Map<String, String> param = new HashMap<>();
//        param.put("name", name);
//        param.put("description", description);
//        param.put("cover_image_link_id", cover_image_link_id);
//        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).post(mContext);

        JsonObject object = new JsonObject();
        object.addProperty("name", name);
        object.addProperty("description", description);
        object.addProperty("cover_image_link_id", cover_image_link_id);
        String json = object.toString();


        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(json).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response getCategoriesInfo() throws IOException {
        String url = getURL(OkRequestConstants.API_CATEGORIES_INFO);

        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 创建图文经验API
     *
     * @param jsonbody
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response postCreateWordExp(String jsonbody) throws IOException {
        String url = getURL(OkRequestConstants.API_EXPERIENCES);

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(jsonbody).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }


    public Response getCommentsFirstLevelList(String experienceId, int page, int size) throws IOException {
        String url = getURL(OkRequestConstants.API_COMMENTS_FIRSTLEVEL);
        Map<String, String> param = new HashMap<>();
        param.put("experienceId", experienceId);
        param.put("page", String.valueOf(page));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response getCommentsSecondLevelList(String commentId, int page, int size) throws IOException {
        String url = getURL(OkRequestConstants.API_COMMENTS_SECONDLEVEL);
        Map<String, String> param = new HashMap<>();
        param.put("commentId", commentId);
        param.put("page", String.valueOf(page));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response postCommendAnswer(String content, String reply_comment_id, String experienceId) throws IOException {
        String url = getURL(OkRequestConstants.API_COMMENTS_ANSWER) + "?experienceId=" + experienceId;
        JsonObject object = new JsonObject();
        object.addProperty("content", content);
        object.addProperty("reply_comment_id", reply_comment_id);
        String jsonbody = object.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(jsonbody).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response putResetPwd(String origin_password, String new_password) throws IOException {
        String url = getURL(OkRequestConstants.API_RESET_PWD);
        JsonObject object = new JsonObject();
        object.addProperty("origin_password", origin_password);
        object.addProperty("new_password", new_password);
        String jsonbody = object.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(jsonbody).url(url).put(mContext);
        return mOkHttpRequest.invoke();
    }


    public Response postUserWithdraw(String withdrawal_way, String withdrawal_amount) throws IOException {
        String url = getURL(OkRequestConstants.API_USER_WITHDRAW);
        JsonObject object = new JsonObject();
        object.addProperty("withdrawal_way", withdrawal_way);
        object.addProperty("withdrawal_amount", withdrawal_amount);
        String jsonbody = object.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(jsonbody).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 获取账户基本信息
     *
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response getMeRevenue() throws IOException {
        String url = getURL(OkRequestConstants.API_ME_REVENUE);
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 获取账户基本信息
     *
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response getMeBindAccount() throws IOException {
        String url = getURL(OkRequestConstants.API_ME_BIND_ACCOUNTS);
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 账户收入明细
     *
     * @param from
     * @param to
     * @param page
     * @param size
     * @throws IOException
     * @mOkHttpRequest =
     */
    public Response getMeIncomeList(String from, String to, int page, int size) throws IOException {
        String url = getURL(OkRequestConstants.API_ME_INCOME);
        Map<String, String> param = new HashMap<>();
        param.put("from", from);
        param.put("to", to);
        param.put("page", String.valueOf(page));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }


    /**
     * 账户支出明细
     *
     * @param from
     * @param to
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public Response getMeWithdrawList(String from, String to, int page, int size) throws IOException {
        String url = getURL(OkRequestConstants.API_USER_WITHDRAW_DETAIL);
        Map<String, String> param = new HashMap<>();
        param.put("from", from);
        param.put("to", to);
        param.put("page", String.valueOf(page));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response deleteMyExperience(String experienceId) throws IOException {
        String url = getURL(OkRequestConstants.API_EXPERIENCES) + "/" + experienceId;
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).url(url).delete(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response deleteChannelExperience(String experienceId) throws IOException {
        String url = getURL(OkRequestConstants.API_CHANNELS_SUBSCRIPTION) + "/" + experienceId;
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).url(url).delete(mContext);
        return mOkHttpRequest.invoke();
    }

    /**
     * 获取用户通知列表API
     *
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public Response getUserNotification(int page, int size) throws IOException {
        String url = getURL(OkRequestConstants.API_USER_NOTIFICATIONS);
        Map<String, String> param = new HashMap<>();
        param.put("page", String.valueOf(page));
        param.put("size", String.valueOf(size));
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).params(param).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response getMeProfilePersonalInfo() throws IOException {
        String url = getURL(OkRequestConstants.API_ME_PROFILE_PERSONAL_INFO);
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response putMeProfilePersonalInfo(ProfileEditBean bean) throws Exception {
        String url = getURL(OkRequestConstants.API_ME_PROFILE_PERSONAL_INFO);
        String jsonbody = JsonMananger.beanToJson(bean);
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(jsonbody).url(url).put(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response putMeProfileAvatar(String  linkId) throws Exception {
        String url = getURL(OkRequestConstants.API_ME_PROFILE_AVATAR) + "?linkId=" + linkId;
        JsonObject object = new JsonObject();

        String jsonbody = object.toString();
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(jsonbody).url(url).put(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response getOrdersExperienceStatus(String experienceId) throws IOException {
        String url = getURL(OkRequestConstants.API_ORDERS_EXPERIENCE) + experienceId;
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).url(url).get(mContext);
        return mOkHttpRequest.invoke();
    }

    public Response postOrdersExperiencePay(String experienceId,String title, String product_description) throws IOException {
        String url = String.format(getURL(OkRequestConstants.API_ORDERS_EXPERIENCE_PAY),experienceId);
        JsonObject object = new JsonObject();
        object.addProperty("pay_way", "WXPAY");
        object.addProperty("pay_way_detail", "APP");
        object.addProperty("title", title);
        object.addProperty("product_description", product_description);
        String jsonbody = object.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");
        mOkHttpRequest = new OkHttpRequest.Builder().tag(this).headers(headers).content_type("application/json;charset=UTF-8").content(jsonbody).url(url).post(mContext);
        return mOkHttpRequest.invoke();
    }
}
