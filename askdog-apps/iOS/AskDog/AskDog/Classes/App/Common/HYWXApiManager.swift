//
//  HYWXApiManager.swift
//  AskDog
//
//  Created by Symond on 16/8/31.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

//protocol HYWXApiManagerDelegate:class {
//    func hyWXApiManagerOnReceiveAuthResp(resp: SendAuthResp!) -> Void;
//}

typealias authCallBack = (_ beOK:Bool?,_ resp:SendAuthResp?,_ needInstallWX:Bool?)->Void;
typealias sendMsgToWXCallBack = (_ beOK:Bool?,_ resp:SendMessageToWXResp?,_ needInstallWX:Bool?)->Void;
typealias makeWeixiOpenURLCallBack = (_ beOK:Bool?,_ resp:OpenWebviewResp?,_ needInstallWX:Bool?)->Void;
typealias weixinPayCallBack = (_ beOK:Bool?,_ resp:PayResp?,_ needInstallWX:Bool?)->Void;

class HYWXApiManager: NSObject {
    
    
    //weak var delegate:HYWXApiManagerDelegate?
    var authCallBackFun:authCallBack?
    var sendMsgToWXCallBackFun:sendMsgToWXCallBack?
    var makeWXOpenUrlCallBackFun:makeWeixiOpenURLCallBack?
    var weixinPayCallBackFun:weixinPayCallBack?
    
    static let sharedInstance = HYWXApiManager();
    fileprivate override init(){};
    
    func checkWeixinStatus() -> Bool {
        if(true == WXApi.isWXAppInstalled()){
            //微信安装了
            if(true == WXApi.isWXAppSupport()){
                return true;
            }
        }
        return false;
    }
    
    func getWeixinAppstoreUrl() -> URL {
        let str:String = WXApi.getWXAppInstallUrl();
        return URL(string:str)!;
    }
    
    /**
     微信认证登陆
     
     - parameter callBack: 回调
     */
    func sendAuthRequest(_ callBack: @escaping (Bool?,SendAuthResp?,Bool?) -> Void) -> Void {
        
        if(false == self.checkWeixinStatus()){

            callBack(false,nil,true);
        }
        
        
        self.authCallBackFun = callBack;
        
        let req:SendAuthReq = SendAuthReq();
        req.scope = "snsapi_userinfo";
        req.state = "auth";
       // req.openID = "";
        WXApi.send(req);
    }

//    /**
//     分享链接到微信朋友圈
//     
//     - parameter url:      要分享的链接
//     - parameter ti:       分享的标题
//     - parameter des:      分享的描述
//     - parameter img:      分享的封面图
//     - parameter callBack: 回调
//     */
//    func shareToPengyouquan(linkUrl url:String, title ti:String,Description des:String,thumbImage img:UIImage,callBack:(Bool?,SendMessageToWXResp?,Bool?) -> Void) -> Void {
//        
//        if(false == self.checkWeixinStatus()){
//            
//            callBack(false,nil,true);
//        }
//        
//        self.sendMsgToWXCallBackFun = callBack;
//        let obj:WXWebpageObject = WXWebpageObject();
//        obj.webpageUrl = url;
//        
//        let msg:WXMediaMessage = WXMediaMessage();
//        msg.title = ti;
//        msg.description = des;
//        msg.mediaObject = obj;
//        msg.setThumbImage(img);
//        
//        let req:SendMessageToWXReq = SendMessageToWXReq();
//        req.bText = false;
//        req.message = msg;
//        req.scene = Int32(WXSceneTimeline.rawValue);
//        
//        WXApi.sendReq(req);
//        
//    }
    
    /**
     分享链接到微信
     
     - parameter url:      要分享的链接
     - parameter ti:       分享的标题
     - parameter des:      分享的描述
     - parameter img:      分享的封面图
     - parameter scene:    场景 0好友1朋友圈 默认1
     - parameter callBack: 回调
     */
    func shareToWeixin(linkUrl url:String, title ti:String,Description des:String,thumbImage img:UIImage,shareScene scene:Int32 = 1,callBack:@escaping (Bool?,SendMessageToWXResp?,Bool?) -> Void) -> Void {
        
        if(false == self.checkWeixinStatus()){
            
            callBack(false,nil,true);
        }
        
        self.sendMsgToWXCallBackFun = callBack;
        let obj:WXWebpageObject = WXWebpageObject();
        obj.webpageUrl = url;
        
        let msg:WXMediaMessage = WXMediaMessage();
        msg.title = ti;
        msg.description = des;
        msg.mediaObject = obj;

        let image = UIImage(data:UIImage().compressImage(img, maxLength: 280)! )
        msg.setThumbImage(image);
        
        let req:SendMessageToWXReq = SendMessageToWXReq();
        req.bText = false;
        req.message = msg;
        //req.scene = Int32(WXSceneTimeline.rawValue);
        req.scene = scene;
        
        WXApi.send(req);
        
    }
    
    func receiveResp(_ resp: BaseResp!) -> Void{
        
        print("\(resp) result: type:\(resp.type) errcode:\(resp.errCode) errstr:\(resp.errStr)");
        
        
        
        if(resp.isKind(of: SendAuthResp.self)){
            let response:SendAuthResp = resp as! SendAuthResp;
            
            print("auth result:code:\(response.code) state:\(response.state)");
            
            if(0 == response.errCode){
                
                if let call = self.authCallBackFun{
                    call(true,response,false);
                    self.authCallBackFun = nil;
                }
            }else{
                if let call = self.authCallBackFun{
                    call(false,response,false);
                    self.authCallBackFun = nil;
                }
            }
        }else if(resp.isKind(of: SendMessageToWXResp.self)){
            
            let response:SendMessageToWXResp = resp as! SendMessageToWXResp;

            if let call = self.sendMsgToWXCallBackFun{
                call(true,response,false);
                self.sendMsgToWXCallBackFun = nil;
            }
            
        }else if(resp.isKind(of: OpenWebviewResp.self)){
            let response:OpenWebviewResp = resp as! OpenWebviewResp;
            
            if let call = self.makeWXOpenUrlCallBackFun{
                call(true,response,false);
                self.makeWXOpenUrlCallBackFun = nil;
            }
        }else if(resp.isKind(of: PayResp.self)){
            let response:PayResp = resp as! PayResp;
            
            print("return key = \(response.returnKey)");
            
            if let call = self.weixinPayCallBackFun{
                call(true,response,false);
                self.weixinPayCallBackFun = nil;
            }
            
        }
    }
    
    /**
     指定微信内置浏览器打开指定链接
     
     - parameter url: 要打开的链接
     */
    func openUrlInWeixinWebView(openUrl url:String,callBack: @escaping (Bool?,OpenWebviewResp?,Bool?) -> Void) -> Void {
        print("openUrlInWeixinWebView");
        
        
        //url长度不能长于1024
        assert(url.characters.count < 1024,"lenght can not more then 1024");
        
        if(false == self.checkWeixinStatus()){
            
            callBack(false,nil,true);
        }
        self.makeWXOpenUrlCallBackFun = callBack;
        
        let req:OpenWebviewReq = OpenWebviewReq();
        req.url = "http://www.baidu.com";

        
        
        WXApi.send(req);
    }
    
    /**
     使用微信支付
     
     - parameter da: 支付数据结构体
     */
    func useWeixinPay(payData da:HYPayBeforeDataModel,callBack: @escaping (Bool?,PayResp?,Bool?) -> Void){
        
        if(false == self.checkWeixinStatus()){
            
            callBack(false,nil,true);
        }
        
        self.weixinPayCallBackFun = callBack;
        
        
        let pay:PayReq = PayReq();
        if let partnerid = da.pay_request?.partnerid{
            pay.partnerId = partnerid;
            
            if let sign = da.pay_request?.sign{
                pay.sign = sign;
                if let pk = da.pay_request?.package{
                    pay.package = pk;
                    if let tm = da.pay_request?.timestamp{
                        pay.timeStamp = UInt32(tm)!;
                        if let non = da.pay_request?.noncestr{
                            pay.nonceStr = non;
                            if let preid = da.pay_request?.prepayid{
                                pay.prepayId = preid;
                                
                                WXApi.send(pay);
                            }
                        }
                    }
                }
            }
            
        }
    }
    

}
