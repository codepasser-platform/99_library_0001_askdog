//
//  HYWBApiManager.swift
//  AskDog
//
//  Created by Hooying_zhaody on 16/9/2.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import SVProgressHUD


typealias WBSSOBlock   = (_ beOK: Bool?, _ response: WBBaseResponse?) -> Void
typealias WBShareBlock = (_ beOK: Bool?, _ response: WBBaseResponse?) -> Void

class HYWBApiManager: NSObject {

    var wbSSOBlock: WBSSOBlock?
    var wbShareBloc: WBShareBlock?
    
    static let sharedInstance = HYWBApiManager()
    fileprivate override init() { }
    
    //MARHK: - 登录
    func wbSSORequest(_ SSOBlock: @escaping (Bool?, WBBaseResponse?) -> Void) -> Void {
        self.wbSSOBlock = SSOBlock
        
        let request: WBAuthorizeRequest = WBAuthorizeRequest()
        request.redirectURI = WEIBO_RREDIRECTURL
        request.scope = "all"
        //参数
        request.userInfo = ["SSO_From": "SendMessageToWeiboViewController",
                            "Other_Info_1": 123,
                            "Other_Info_2": ["key1": "obj1"]]
        
        WeiboSDK.send(request)
    }
    
    
    
    //MARHK: - 分享
    //是否安装微博客户端
    func userInstallationSinaApp() -> Bool {
        return WeiboSDK.isWeiboAppInstalled() as Bool
    }
    
    //获取微博下载地址
    func getSinaDownUrl() -> URL {
        let urlStr = WeiboSDK.getWeiboAppInstallUrl()
        return  URL(string: urlStr!)!
    }
    
    func weShareRequset(_ userInfo:NSDictionary, objectID:String, title:String, description:String, imgUrl: String, webpageUrl:String,
                        shareBlock: @escaping (Bool?, WBBaseResponse?) ->Void) -> Void {
        self.wbShareBloc = shareBlock
        
        /** userInfo
         自定义信息字典，用于数据传输过程中存储相关的上下文环境数据
         
         第三方应用给微博客户端程序发送 request 时，可以在 userInfo 中存储请求相关的信息。
         
         @warning userInfo中的数据必须是实现了 `NSCoding` 协议的对象，必须保证能序列化和反序列化
         @warning 序列化后的数据不能大于10M
         */
        

        let webpage: WBWebpageObject = (WBWebpageObject.object() as? WBWebpageObject)!
        
        SVProgressHUD.show(withStatus: "加载中...");
        
        DispatchQueue.global().async {
            if let imgData = try? Data(contentsOf: URL(string: imgUrl)!) {
                if  let img = UIImage(data: imgData) {
                    webpage.thumbnailData = UIImage().compressImage(img, maxLength: 280)
                }
            }
            DispatchQueue.main.async(execute: {
                SVProgressHUD.dismiss()
                
                webpage.objectID = objectID
                webpage.title = title
                webpage.description = description
                webpage.webpageUrl = webpageUrl
//                webpage.scheme = "wb691533910"
                
                let messgae = WBMessageObject()
                messgae.mediaObject = webpage
                
                let wbToken = UserDefaults.standard.value(forKey: WB_TOKEN) as? String
                
                let authRequest = WBAuthorizeRequest();
                authRequest.redirectURI = WEIBO_RREDIRECTURL
                authRequest.scope = "all"
                
                let request:WBSendMessageToWeiboRequest = WBSendMessageToWeiboRequest.request(withMessage: messgae, authInfo: authRequest, access_token: wbToken) as! WBSendMessageToWeiboRequest
                request.userInfo = userInfo as Dictionary
                WeiboSDK .send(request);
            })
        }
        


    }
    
    
    
    func sinaBack(_ response: WBBaseResponse)  {
        
        if response.statusCode == WeiboSDKResponseStatusCode.success {
            // 成功
            self.successResponse(response)
        }
        
        if response.statusCode == WeiboSDKResponseStatusCode.userCancel {
            //用户取消发送
        }
        
        if response.statusCode == WeiboSDKResponseStatusCode.sentFail {
            //发送失败
        }
        
        if response.statusCode == WeiboSDKResponseStatusCode.authDeny {
            //授权失败
            self.failureResponse(response)
        }
    }
    
    
    func successResponse(_ response: WBBaseResponse) {
        
        var wbtoken: String = ""
        var wbuserID: String = ""
        var wbRefreshToken: String = ""
        var wbExpirationDate: Date = Date()
        
        if let response = response as? WBAuthorizeResponse {
            //认证结果
            if let accessToken: String = response.accessToken {
                wbtoken = accessToken
                if let userId: String = response.userID {
                    wbuserID = userId
                    wbRefreshToken = response.refreshToken
                    wbExpirationDate = response.expirationDate
                    
                    UserDefaults.standard.set(wbtoken, forKey: WB_TOKEN)
                    UserDefaults.standard.set(wbuserID, forKey: WB_USERID)
                    UserDefaults.standard.set(wbRefreshToken, forKey: WB_REFRESHTOKEN)
                    UserDefaults.standard.set(wbExpirationDate, forKey: WB_EXPITRATIONDATA)
                    
                    //获取新浪微博权限成功
                    if let call = self.wbSSOBlock {
                        call(true, response)
                        self.wbSSOBlock = nil
                    }
                    return
                }
            }
        }
            
        else if let messageResponse:  WBSendMessageToWeiboResponse = response as? WBSendMessageToWeiboResponse {
        
//            发送分享结果 TODO
            if let auth: WBAuthorizeResponse = messageResponse.authResponse {
                if let accessToken: String = auth.accessToken {
                    wbtoken = accessToken
                    if let userId: String = auth.userID {
                        wbuserID = userId
                        
                        //发微博成功
                        if let call = self.wbShareBloc {
                            call(true, response)
                            self.wbShareBloc = nil
                        }
                        return
                    }
                }
            }
        }
    }
    
    
    func failureResponse(_ response: WBBaseResponse) {
        if response is WBAuthorizeResponse {
            //认证失败
            if let call = self.wbSSOBlock {
                call(false, response)
                self.wbSSOBlock = nil
            }
        }
        if response is WBSendMessageToWeiboResponse {
            //分享失败
            if let call = self.wbShareBloc {
                call(false, response)
                self.wbShareBloc = nil
            }
        }
    }
}
