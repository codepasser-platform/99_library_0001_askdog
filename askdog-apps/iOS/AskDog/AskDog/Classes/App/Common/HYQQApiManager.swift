//
//  HYQQApiManager.swift
//  AskDog
//
//  Created by Hooying_zhaody on 16/9/2.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

typealias QQLoginBlock  = (_ beOK: Bool?, _ tencentOAuth: TencentOAuth?) ->Void
typealias QQShareBlock  = (_ beOK: Bool?, _ shareMessage: String?) ->Void
typealias QQUserInfoBlock = (_ beOk: Bool?, _ heardUrl: String, _ userName: String) -> Void

class HYQQApiManager: NSObject {
    var tencentOAuth: TencentOAuth!
    var tencentLoginBlock: QQLoginBlock?
    var tencentShareBlock: QQShareBlock?
    var tencentUserInfoBlock: QQUserInfoBlock?
    
    static let sharedInstance = HYQQApiManager()
    fileprivate override init() {
        tencentOAuth = TencentOAuth()
    }
    
    //MARK: - 登录
    func tencentLoginRequest(_ loginBlock: @escaping (Bool?, TencentOAuth?) -> Void) -> Void {
        self.tencentLoginBlock = loginBlock
        
        let permissions = ["kOPEN_PERMISSION_GET_USER_INFO", "kOPEN_PERMISSION_GET_SIMPLE_USER_INFO", "kOPEN_PERMISSION_GET_INFO", "kOPEN_PERMISSION_ADD_SHARE"]
        tencentOAuth .authorize(permissions, inSafari: false)
    }

    
    
    func tencentBack(_ beOK: Bool, cancelled: Bool) {
        
        if beOK == true {
            print("tencent登录成功后的回调")
            
            if let accessToken = tencentOAuth.accessToken  {
                if 0 != accessToken.characters.count {
                    // accessToken
                    UserDefaults.standard.set(tencentOAuth.accessToken, forKey: QQ_TOKEN)
                    tencentOAuth.getUserInfo()
                    print("tencent登录成功 获取accesstoken")
                    if let call = self.tencentLoginBlock {
                        call (true, tencentOAuth)
                        self.tencentLoginBlock = nil
                    }
                    
                } else {
                    print("tencent登录不成功 没有获取accesstoken")
                    if let call = self.tencentLoginBlock {
                        call (false, tencentOAuth)
                        self.tencentLoginBlock = nil
                    }
                }
            }

        } else {
            print("tencent登录失败后的回调")
            
            if cancelled {
                print("tencent用户取消登录")
                
            } else {
                print("tencent登录失败，TODO 消息提示")
                if let call = self.tencentLoginBlock {
                    call (false, tencentOAuth)
                    self.tencentLoginBlock = nil
                }
            }

        }
    }
    
    func tencentNotNetWorkBack() {
        
    }
    
    
    func tencentUpdate(_ tencentOAuth: TencentOAuth) {
        print("tencent过期重新登录成功后的回调")
        
        if let accessToken = tencentOAuth.accessToken  {
            if 0 != accessToken.characters.count {
                // accessToken
                UserDefaults.standard.set(tencentOAuth.accessToken, forKey: QQ_TOKEN)
                tencentOAuth.getUserInfo()
                print("tencent重新登录成功 获取accesstoken")
                if let call = self.tencentLoginBlock {
                    call (true, tencentOAuth)
                    self.tencentLoginBlock = nil
                }
            }
            print("tencent重新登录不成功 没有获取accesstoken")
            if let call = self.tencentLoginBlock {
                call (false, tencentOAuth)
                self.tencentLoginBlock = nil
            }
        }
    }
    
    func userInfoResponse(_ response: APIResponse) {
        let userInfo: NSDictionary = response.jsonResponse as NSDictionary
        print(userInfo)
        /*
         {
         city = "\U5927\U8fde";
         figureurl = "http://qzapp.qlogo.cn/qzapp/1105654292/D9F7D3DCB03C4B0DC47B4F2F2A53CBEC/30";
         "figureurl_1" = "http://qzapp.qlogo.cn/qzapp/1105654292/D9F7D3DCB03C4B0DC47B4F2F2A53CBEC/50";
         "figureurl_2" = "http://qzapp.qlogo.cn/qzapp/1105654292/D9F7D3DCB03C4B0DC47B4F2F2A53CBEC/100";
         "figureurl_qq_1" = "http://q.qlogo.cn/qqapp/1105654292/D9F7D3DCB03C4B0DC47B4F2F2A53CBEC/40";
         "figureurl_qq_2" = "http://q.qlogo.cn/qqapp/1105654292/D9F7D3DCB03C4B0DC47B4F2F2A53CBEC/100";
         gender = "\U7537";
         "is_lost" = 0;
         "is_yellow_vip" = 0;
         "is_yellow_year_vip" = 0;
         level = 0;
         msg = "";
         nickname = "\U8d75\U5c0f\U5b69";
         province = "\U8fbd\U5b81";
         ret = 0;
         vip = 0;
         "yellow_vip_level" = 0;
         }

         */
    }
    
    
    //MARK: - 分享
   
    //检测是否已安装QQ
    func userInstalledQQApp() -> Bool {
        return QQApiInterface.isQQInstalled() as Bool
    }
    
    //获取QQ下载地址
    func getQQDowmUrl() -> URL {
        let urlStr = QQApiInterface.getQQInstallUrl()
        return URL(string: urlStr!)! as URL
    }
    
    func tencentShareRequest(_ isQZone: Bool, url: String, imageUrl:String, title: String, description: String, shareBlock: @escaping (Bool?, String?) -> Void) -> Void {
        self.tencentShareBlock = shareBlock
        
        /**
         获取一个autorelease的<code>QQApiNewsObject</code>
         @param url 视频内容的目标URL
         @param title 分享内容的标题
         @param description 分享内容的描述
         @param previewURL 分享内容的预览图像URL
         @note 如果url为空，调用<code>QQApi#sendMessage:</code>时将返回FALSE
         */
        
        let newsObj = QQApiNewsObject.object(with: URL(string: url),
                                                    title: title,
                                                    description: description,
                                                    previewImageURL: URL(string: imageUrl))
        let req = SendMessageToQQReq(content: newsObj as! QQApiNewsObject)
        var sendCode: QQApiSendResultCode?
        if isQZone == true {
            sendCode = QQApiInterface.sendReq(toQZone: req)
        } else {
            sendCode = QQApiInterface.send(req)

        }
        self.handleSendResul(sendCode!)
        
        //分享回调
        HYQQShareDelegate.sharedManger().onReq{ (req) in
           // let obj = req as QQBaseReq
            /*]  EGETMESSAGEFROMQQREQTYPE = 0,   ///< 手Q -> 第三方应用，请求第三方应用向手Q发送消息
             ESENDMESSAGETOQQREQTYPE = 1,    ///< 第三方应用 -> 手Q，第三方应用向手Q分享消息
             ESHOWMESSAGEFROMQQREQTYPE = 2   ///< 手Q -> 第三方应用，请求第三方应用展现消息中的数据
             */
        }
        
        HYQQShareDelegate.sharedManger().onResp{ (resp) in
            if resp?.result == "0"  {
                 print("分享成功")
            } else {
                print("请求处理结果: \(resp?.result)  具体错误描述信息: \(resp?.errorDescription)")
            }
        }
    }
    
    
    func handleSendResul(_ sendResult: QQApiSendResultCode) {
        
        if let call = self.tencentShareBlock {
            if sendResult == EQQAPISENDSUCESS {
                call (true, "分享成功")
                self.tencentShareBlock = nil
            } else {
                call (false, "分享失败")
                self.tencentShareBlock = nil
            }
        }
        
        switch sendResult {
            
        case EQQAPIAPPNOTREGISTED:
            print("App未注册")
            break
        case EQQAPIMESSAGECONTENTNULL, EQQAPIMESSAGETYPEINVALID, EQQAPIMESSAGECONTENTINVALID:
            print("发送参数错误")
            break
        case EQQAPIQQNOTINSTALLED:
            print("未安装手Q")
            
            break
        case EQQAPIQQNOTSUPPORTAPI:
            print("API接口不支持")
            break
        case EQQAPISENDFAILD:
            print("发送失败")
            break
        case EQQAPIQZONENOTSUPPORTTEXT:
            print("空间分享不支持QQApiTextObject，请使用QQApiImageArrayForQZoneObject分享")
            break
        case EQQAPIQZONENOTSUPPORTIMAGE:
            print("空间分享不支持QQApiImageObject，请使用QQApiImageArrayForQZoneObject分享")
            break
        case EQQAPIVERSIONNEEDUPDATE:
            print("当前QQ版本太低，需要更新")
            break
        default:
            break;
        }

    }
}
