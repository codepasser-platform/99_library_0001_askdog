/*
    ShengDao Android Client, URLConstants
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.askdog.android.network.service;

/**
 * [A brief description]
 *
 * @author wyong
 * @version 1.0
 * @date 2015-12-02
 **/
public class OkRequestConstants {

    /**
     * 基础请求码
     */
    private static int BASE_REQUEST_CODE = 0x12345;
    /**
     * 域名
     */
//    public static final String DOMAIN = "http://rokng.tunnel.qydev.com/";
//    public static final String DOMAIN = "http://askdog.tunnel.qydev.com/";
//    public static final String DOMAIN = "http://192.168.1.122:8280/";
    public static final String DOMAIN = "http://192.168.1.123:60/";

    /**
     * 用户注册
     */
    public static final int CODE_REGISTER = BASE_REQUEST_CODE + 2;
    public static final String API_REGISTER = "api/users";


    /**
     * 用户手机注册
     */
    public static final int CODE_PHONE_REGISTER = BASE_REQUEST_CODE + 100;
    public static final String API_PHONE_REGISTER = "api/users/phone";


    /**
     * 发送验证码
     */
    public static final int CODE_SEND_MESSAGE = BASE_REQUEST_CODE + 101;
    public static final String API_PHONE_SEND_MESSAGE = "api/users/phone/code";


    /**
     * 发送验证码
     */
    public static final int CODE_CHECK_PHONE_NUMBER = BASE_REQUEST_CODE + 102;
    public static final String API_CHECK_PHONE_NUMBER = "api/password/recover/phone";

    /**
     * 验证验证码
     */
    public static final int CODE_CHECK_MESSAGE_NUMBER = BASE_REQUEST_CODE + 103;
    public static final String API_CHECK_MESSAGE_NUMBER = "api/password/code/validation";

    /**
     * 更新手机用户密码
     */
    public static final int CODE_UPDATE_PHONE_USER_PASSWORD = BASE_REQUEST_CODE + 104;
    public static final String API_UPDATE_PHONE_USER_PASSWORD = "api/password/phone";

    /**
     * 登录前混合各种分类的列表展示
     */
    public static final String PARA_UNLOGIN_SEARCH = "unLoginHomeSearch";
    public static final String PARA_LOGIN_SEARCH = "loginHomeSearch";
    public static final String PARA_SEARCH_HOTE = "experience_hot";
    public static final String PARA_SEARCH_RECOMMEND = "experience_recommend";
    public static final int CODE_SEARCH_CODE = BASE_REQUEST_CODE + 3;
    public static final int CODE_SEARCH_HOT = BASE_REQUEST_CODE + 15;
    public static final int CODE_SEARCH_RECOMMEND = BASE_REQUEST_CODE + 16;
    public static final String API_SEARCH = "api/search";

    /**
     * 用户认证 post
     */
    public static final int CODE_LOGIN = BASE_REQUEST_CODE + 4;
    public static final String API_CONFIRM = "login?ajax=true";

    public static final int CODE_API_EXPERIENCES = BASE_REQUEST_CODE + 5;
    public static final String API_EXPERIENCES= "api/experiences";

    /**
     * 获取用户创建的频道列表API
     */
    public static final int CODE_USER_CHANNELS_OWNED = BASE_REQUEST_CODE + 6;
    public static final String API_USER_CHANNELS_OWNED= "api/channels/owned";

    /**
     * 获取用户创建的频道列表API
     */
    public static final int CODE_USER_CHANNELS_SUBSCRIBED = BASE_REQUEST_CODE + 7;
    public static final String API_USER_CHANNELS_SUBSCRIBED = "api/channels/subscribed";

    /**
     * 全文搜索API
     */
    public static final int CODE_SEARCH_SUGGEST = BASE_REQUEST_CODE + 8;
    public static final int CODE_SEARCH_ALL_RESULT = CODE_SEARCH_SUGGEST + 1000;


    /**
     * type=experience_related&from=0&size=20&experienceId={experienceId}
     * 分享详细页相关分享API
     */
    public static final int CODE_SHARE_RELATED = BASE_REQUEST_CODE + 9;

    /**
     * api/experiences/{experienceId}/vote?direction={vote_direction}
     * 赞踩经验API
     */
    public static final int CODE_VOTE_UP = BASE_REQUEST_CODE + 10;
    public static final int CODE_VOTE_DOWN = BASE_REQUEST_CODE + 12;
    public static final String API_VOTE = "api/experiences";
    /**
     * 取消赞或踩
     * api/experiences/{experienceId}/vote
     */
    public static final int CODE_VOTE_DOWN_UP_CANCEL  = BASE_REQUEST_CODE + 13;

    /**
     * 获取我创建的频道列表API
     */
    public static final int CODE_MY_OWNED_CHANNEL = BASE_REQUEST_CODE + 17;
    public static final String API_MY_OWNED_CHANNEL = "api/users/me/channels/owned";

    /**
     * 获取我订阅的频道列表API
     */
    public static final int CODE_MY_SUBSCRIBED_CHANNEL = BASE_REQUEST_CODE + 18;
    public static final String API_MY_SUBSCRIBED_CHANNEL = "api/users/me/channels/subscribed";

    /**
     * 重置密码API
     * api/password/recover?mail=admin@bianchunguang.com
     */
    public static final int CODE_FIND_PWD = BASE_REQUEST_CODE + 19;
    public static final String API_FIND_PWD = "api/password/recover";

    /**
     * 历史记录
     * /api/users/me/views?page=0&size=1
     */
    public static final int CODE_VIEW_HISTORY = BASE_REQUEST_CODE + 20;
    public static final String API_VIEW_HISTORY = "api/users/me/views";

    /**
     * 订阅频道API
     * api/channels/{channel_id}/subscription
     */
    public static final int CODE_CHANNELS_SUBSCRIPTION = BASE_REQUEST_CODE + 21;
    public static final int CODE_CHANNELS_SUBSCRIPTION_CANCEL = BASE_REQUEST_CODE + 22;
    public static final String API_CHANNELS_SUBSCRIPTION = "api/channels";
    /**
     * 频道详细信息API
     * api/channels/{channelId}
     */
    public static final int CODE_CHANNELS_DETAIL = BASE_REQUEST_CODE + 23;
    /**
     * 获取频道经验列表API
     * api/experiences?channelId=30962247440242694&page=1&size=1
     */
    public static final int CODE_CHANNELS_LIST = BASE_REQUEST_CODE + 24;

    /**
     * 获取文件上传授权TokenAPI
     * api/storage/access_token?extention=mov&type=EXPERIENCE_VIDEO
     * extention 欲上传文件的扩展名
     * type 上传文件的逻辑类别，可选值为：USER_AVATAR,
     *                                    EXPERIENCE_PICTURE,
     *                                    CHANNEL_THUMBNAIL,
     *                                    EXPERIENCE_VIDEO
     */
    public static final int CODE_AVATAR_ACCESS_TOKEN = BASE_REQUEST_CODE + 25;
    public static final int CODE_PICTURE_ACCESS_TOKEN = BASE_REQUEST_CODE + 26;
    public static final int CODE_THUMBNAIL_ACCESS_TOKEN = BASE_REQUEST_CODE + 27;
    public static final int CODE_VIDEO_ACCESS_TOKEN = BASE_REQUEST_CODE + 28;
    public static final String API_UPLOAD_ACCESS_TOKEN = "api/storage/access_token";
    /**
     * 文件存储于阿里OSS
     */
    public static final int CODE_UPLOAD_TO_OSS = BASE_REQUEST_CODE + 29;
    public static final String API_OSS = "http://vod-bucket-in.oss-cn-hangzhou.aliyuncs.com";

    /**
     * 创建频道API
     */
    public static final int CODE_BUILD_CHANNEL = BASE_REQUEST_CODE + 30;

    /**
     * 获取频道分类信息
     */

    public static final int CODE_CATEGORIES_INFO = BASE_REQUEST_CODE + 31;
    public static final String API_CATEGORIES_INFO = "api/categories";

    /**
     * 创建图文经验API
     */
    public static final int CODE_CREATE_WORD = BASE_REQUEST_CODE + 32;

    /**
     * 获取评论
     * http://askdog.tunnel.qydev.com/api/comments/firstlevel?experienceId=281895886326013&page=0&size=20
     */
    public static final int CODE_COMMENTS_FIRSTLEVEL = BASE_REQUEST_CODE + 33;
    public static final String API_COMMENTS_FIRSTLEVEL = "api/comments/firstlevel";
    /**
     * 获取评论
     * /api/comments/secondlevel?commentId={commentId}&page=0&size=20
     */
    public static final int CODE_COMMENTS_SECONDLEVEL = BASE_REQUEST_CODE + 34;
    public static final String API_COMMENTS_SECONDLEVEL = "api/comments/secondlevel";

    /**
     * 创建图文经验API
     */
    public static final int CODE_CREATE_VIDEO = BASE_REQUEST_CODE + 35;
    /**
     * http://ask.dog:8080/api/comments?experienceId={experience_id}
     */
    public static final int CODE_COMMENTS_ANSWER = BASE_REQUEST_CODE + 36;
    public static final String API_COMMENTS_ANSWER = "api/comments";

    /**
     * 修改密码API
     * api/password/change
     */
    public static final int CODE_RESET_PWD = BASE_REQUEST_CODE + 37;
    public static final String API_RESET_PWD = "api/password/change";

    /**
     * api/users/withdraw
     * 提现API
     */
    public static final int CODE_USER_WITHDRAW = BASE_REQUEST_CODE + 38;
    public static final String API_USER_WITHDRAW = "api/users/withdraw";

    /**
     *
     * GET: http://ask.dog:8080/api/users/me/withdraw?from={from}&to={to}&page={page}&size={size} 参数说明:
     */
    public static final int CODE_USER_WITHDRAW_DETAIL = BASE_REQUEST_CODE + 39;
    public static final String API_USER_WITHDRAW_DETAIL = "api/users/me/withdraw";
    /**
     * GET: http://ask.dog:8080/api/users/me/revenue
     * 获取账户基本信息
     */
    public static final int CODE_ME_REVENUE = BASE_REQUEST_CODE + 40;
    public static final String API_ME_REVENUE= "api/users/me/revenue";

    /**

     * 账户收入明细
     * GET: http://ask.dog:8080/api/users/me/income?from={from}&to={to}&page={page}&size={size} 参数说明:
     * from: 查询开始日期，格式为：yyyy-MM-dd
     * to: 查询结束日期，格式为：yyyy-MM-dd
     * page: 页码
     * size: 每页大小
     */
    public static final int CODE_ME_INCOME = BASE_REQUEST_CODE + 41;
    public static final String API_ME_INCOME= "api/users/me/income";

    /**
     * GET: http://ask.dog:8080/api/users/me/revenue
     * 获取账户是否绑定微信信息
     */
    public static final int CODE_ME_BIND_ACCOUNTS = BASE_REQUEST_CODE + 42;
    public static final String API_ME_BIND_ACCOUNTS= "api/users/me/bind_accounts";


    public static final int CODE_DELETE_EXPERIENCE = BASE_REQUEST_CODE + 43;
    public static final int CODE_DELETE_CHANNEL = BASE_REQUEST_CODE + 44;


    /**
     * 获取用户通知列表API
     * /api/notifications/?page=0&size=15
     */
    public static final int CODE_USER_NOTIFICATIONS = BASE_REQUEST_CODE + 45;
    public static final String API_USER_NOTIFICATIONS= "api/notifications/";

    /**
     * 获取用户个人信息列表API
     * api/users/me/profile/personal_info
     */
    public static final int CODE_ME_PROFILE_PERSONAL_INFO = BASE_REQUEST_CODE + 46;
    public static final int CODE_ME_PUT_PROFILE_PERSONAL_INFO = BASE_REQUEST_CODE + 47;
    public static final String API_ME_PROFILE_PERSONAL_INFO= "api/users/me/profile/personal_info";

    /**
     * 修改个人信息头像
     * ?linkId="11111111111111"
     */
    public static final int CODE_ME_PROFILE_AVATAR = BASE_REQUEST_CODE + 48;
    public static final String API_ME_PROFILE_AVATAR= "api/users/me/profile/avatar";

    public static final int CODE_ORDERS_EXPERIENCE = BASE_REQUEST_CODE + 49;
    public static final String API_ORDERS_EXPERIENCE= "api/orders/experience/";

    public static final int CODE_ORDERS_EXPERIENCE_PAY = BASE_REQUEST_CODE + 50;
    public static final String API_ORDERS_EXPERIENCE_PAY= "api/orders/experience/%s/pay";
}
