//
//  AskDogDataRequest.swift
//  AskDog
//
//  Created by Symond on 16/7/27.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import Foundation
import Alamofire

//MARK:未登录首页API
open class AskDogHomeSearchDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/search";
    }
}

//MARK:热门分享API
open class AskDogHotShareDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/search";
    }
}

//MARK:经验详情页推荐经验
open class AskDogExperienceRelatedDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/search";
    }
}

//MARK:首页用户推荐API
open class AskDogUserRecommendDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/search";
    }
}

//http://192.168.1.123:8280/login?ajax=ture
//MARK:登录API
open class AskDogLoginDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "login?ajax=true";
    }
    
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
}

//MARK:登出API
open class AskDogLogoutDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "logout?ajax=true";
    }
    override func isOnlyCheckHttpStatusCode() -> Bool {
        return true;
    }

}

//MARK:热门分享API
open class AskDogRegisterDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users";
    }
    
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    
//    override func getHeaders() -> [String : String]? {
//        let headers = [
//            "Content-Type": "application/json;charset=UTF-8"
//        ];
//        
//        return headers;
//    }
    
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
    
}

//MARK:发送重置密码邮件API
open class AskDogSendFindPwdEmailDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/password/recover";
    }
    
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    
    override func isOnlyCheckHttpStatusCode() -> Bool {
        return true;
    }
}

//MARK:经验详细信息API
open class AskDogGetExperienceDetailDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/experiences";
    }
}

//MARK:获取推荐频道
open class AskDogGetCategoriesListDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/categories";
    }
}

//MARK:获取嵌套分类列表API
open class AskDogGetAllCategoryDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/categories/nested";
    }
    
}

//MARK:点赞踩
open class AskDogVoteDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/experiences";
    }
    
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
}

//MARK:取消赞踩
open class AskDogCancelVoteDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/experiences";
    }
    
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .delete;
    }
}

//MARK:修改用户频道类别API
open class AskDogModifyPersonalCategoriesListDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/me/profile/personal_category";
    }
    
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .put;
    }
    
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
}

//FIXME:接口待确认
//MARK:重置密码
open class AskDogResetPasswordDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/password/recover";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .put;
    }
}


//MARK:订阅频道
open class AskDogSubscriptionDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/channels";
    }
    
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    
    override func isOnlyCheckHttpStatusCode() -> Bool {
        return true;
    }
}

//MARK:取消订阅频道
open class AskDogCancelSubscriptionDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/channels";
    }
    
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .delete;
    }
    
    override func isOnlyCheckHttpStatusCode() -> Bool {
        return true;
    }
}


//MARK:取个人信息
open class AskDogGetUserInfoDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/me";
    }
}

//MARK:取个人创建的频道列表
open class AskDogGetUserOwnedChannelListDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/channels/owned";
    }
}

//MARK:获取我订阅的的频道列表
open class AskDogGetMySubscribedChannelListDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/me/channels/subscribed";
    }
}

//MARK:获取用户订阅频道更新经验的列表API
open class AskDogGetMySubscribedChannelsUnreadDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/channels/subscribed/unread";
    }
}

//MARK:创建频道API
open class AskDogCreateChannelListDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/channels";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
}

//MARK:删除频道API
open class AskDogDeleteChannelListDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/channels";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .delete;
    }
    override func isOnlyCheckHttpStatusCode() -> Bool {
        return true;
    }

}

//MARK:修改频道API
open class AskDogEditChannelInfoDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/channels";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .put;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
}

//MARK:获取文件上传授权TokenAPI
open class AskDogGetAccessTokenDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/storage/access_token";
    }
}

//MARK:上传图片API
open class AskDogUploadImageDataRequest : HYBaseRequest{
    
}

//MARK:获取历史记录API
open class AskDogGetViewHistoryDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/me/views";
    }
}

//MARK:修改密码API
open class AskDogModifyPasswordDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/password/change";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .put;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
    override func isOnlyCheckHttpStatusCode() -> Bool {
        return true;
    }
}

//MARK:频道详细信息API
open class AskDogGetChannelInfoDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/channels";
    }
}

//MARK:获取频道经验列表API
open class AskDogGetChannelExperinceListDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/experiences";
    }
}

//MARK:删除经验API
open class AskDogDeleteExperinceDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/experiences";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .delete;
    }
    override func isOnlyCheckHttpStatusCode() -> Bool {
        return true;
    }
}

//MARK:修改用户信息API
open class AskDogModifyUserInfoDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/me/profile/personal_info";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .put;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
    override func isOnlyCheckHttpStatusCode() -> Bool {
        return true;
    }
}

//MARK:获取用户详细信息
open class AskDogGetUserDetailInfoDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/me/profile/personal_info";
    }
}

//MARK:创建经验评论API
open class AskDogCreateCommentDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/comments";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
}

//MARK:获取账户基本信息
open class AskDogGetUserAccountInfoDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/me/revenue";
    }
}

//MARK:账户收入明细
open class AskDogGetUserAccountIncomeDetailDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/me/income";
    }
}

//MARK:账户提现明细
open class AskDogGetUserAccountCashDetailDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/me/withdraw";
    }
}

//MARK:修改用户头像
open class AskDogModifyUserHeaderImgDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/me/profile/avatar";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .put;
    }
//    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
//        return .JSON;
//    }
    override func isOnlyCheckHttpStatusCode() -> Bool {
        return true;
    }
}

//MARK:举报经验API
open class AskDogReportExperienceDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/experiences";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }

}

//MARK:获取用户创建的频道列表API
open class AskDogGetUserCreatedChannelListDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/channels/owned";
    }
}

//MARK:创建图文经验API
open class AskDogCreateTextExperienceDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/experiences";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
}

//MARK:取一级经验评论
open class AskDogGetFirstLevelCommentsDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/comments/firstlevel";
    }
}

//MARK:取二级经验评论
open class AskDogGetSecondLevelCommentsDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/comments/secondlevel";
    }
}

//MARK:获取用户订阅的频道列表API
open class AskDogGetUserSubscribedChannelListDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/channels/subscribed";
    }
}

//MARK:获取用户通知列表API
open class AskDogGetUserNotifyListDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/notifications";
    }
}

//MARK:获取指定用户的信息
open class AskDogGetUserInfoByUserIdDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users";
    }
}

//MARK:全文搜索API
open class AskDogGetHomeSearchActionRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/search";
    }
}

//MARK:获取微信认证TOKEN 
open class AskDogGetWeixinTokenByCodeDataRequest : HYBaseRequest{
    override func getRequestUrl() -> Alamofire.URLConvertible {
        return "https://api.weixin.qq.com/sns/oauth2/access_token";
    }
}

//MARK:获取微信用户信息
open class AskDogGetWeixinUserInfoDataRequest : HYBaseRequest{
    override func getRequestUrl() -> Alamofire.URLConvertible {
        return "https://api.weixin.qq.com/sns/userinfo";
    }
}

//MARK:提现API
open class AskDogCashDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/withdraw";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
    override func isOnlyCheckHttpStatusCode() -> Bool {
        return true;
    }
}

//MARK:微信预支付
open class AskDogPayBeforeRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/orders/experience";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }

}

//MARK:获取视频支付状态
open class AskDogGetExperiencePayStatusRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/orders/experience";
    }
    
}

//MARK:发送短信验证码
open class AskDogSendSMSDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/phone/code";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
    
}

//MARK:通过手机注册账号
open class AskDogRegisterWithPhoneNumberDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/phone";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
    
}

//MARK:验证手机合法性
open class AskDogCheckPhoneNumberDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/password/recover/phone";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
    
}

//MARK:发送找回密码的验证码
open class AskDogSendRestPhonePwdCheckCodeDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/password/recover/code";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
    
}

//MARK:校验找回密码的手机和验证码的正确性
open class AskDogCheckResetPwdPhoneCheckCodeDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/password/code/validation";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .post;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
    
}

//MARK:更改手机注册账号的密码
open class AskDogChangePhonePwdDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/password/phone";
    }
    override func getRequestMethod() -> Alamofire.HTTPMethod {
        return .put;
    }
    override func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return JSONEncoding.default;
    }
    
}

//MARK:获取服务器的一些状态
open class AskDogGetServiceInfoDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/app/status";
    }
    
}

//MARK:获取视频可添加的背景音乐列表
open class AskDogGetMusicListDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/video/musics";
    }
//    override func getRequestMethod() -> Alamofire.HTTPMethod {
//        return .post;
//    }
    
}

//MARK:获取用户状态
open class AskDogGetUserStatusDataRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/users/me/status";
    }
    
    override func isOnlyCheckHttpStatusCode() -> Bool {
        return true;
    }
    
}


//MARK:获取通知下拉框预览列表API
open class AskDogGetPreviewRequest : HYBaseRequest{
    override func getMethodUrl() -> String! {
        return "api/notifications/preview";
    }
    
}











