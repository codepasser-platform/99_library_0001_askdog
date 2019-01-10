//
//  HYWeiXinDataModel.swift
//  AskDog
//
//  Created by Symond on 16/8/31.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYWeiXinAuthDataModel: HYBaseDataModel {
    var access_token:String?
    var expires_in:Int?
    var refresh_token:String?
    var openid:String?
    var scope:String?
    var unionid:String?
    
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["access_token"], callBack: {
            (str:String) -> Void in
            self.access_token = str;
        });
        CommonTools.stringCheckNullObject(dic["refresh_token"], callBack: {
            (str:String) -> Void in
            self.refresh_token = str;
        });
        CommonTools.stringCheckNullObject(dic["openid"], callBack: {
            (str:String) -> Void in
            self.openid = str;
        });
        CommonTools.stringCheckNullObject(dic["scope"], callBack: {
            (str:String) -> Void in
            self.scope = str;
        });
        CommonTools.stringCheckNullObject(dic["unionid"], callBack: {
            (str:String) -> Void in
            self.unionid = str;
        });
        CommonTools.intCheckNullObject(dic["expires_in"], callBack: {
            (n:Int) -> Void in
            self.expires_in = n;
        });
    }
    
}

class HYWeiXinUserInfoDataModel: HYBaseDataModel {
    var openid:String?
    var nickname:String?
    var sex:Int?
    var language:String?
    var city:String?
    var province:String?
    var country:String?
    var headimgurl:String?
    var privilege:[String]?
    var unionid:String?
    
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["openid"], callBack: {
            (str:String) -> Void in
            self.openid = str;
        });
        CommonTools.stringCheckNullObject(dic["nickname"], callBack: {
            (str:String) -> Void in
            self.nickname = str;
        });
        CommonTools.intCheckNullObject(dic["sex"], callBack: {
            (n:Int) -> Void in
            self.sex = n;
        });
        CommonTools.stringCheckNullObject(dic["language"], callBack: {
            (str:String) -> Void in
            self.language = str;
        });
        CommonTools.stringCheckNullObject(dic["city"], callBack: {
            (str:String) -> Void in
            self.city = str;
        });
        CommonTools.stringCheckNullObject(dic["province"], callBack: {
            (str:String) -> Void in
            self.province = str;
        });
        CommonTools.stringCheckNullObject(dic["country"], callBack: {
            (str:String) -> Void in
            self.country = str;
        });
        CommonTools.stringCheckNullObject(dic["headimgurl"], callBack: {
            (str:String) -> Void in
            self.headimgurl = str;
        });
        CommonTools.stringCheckNullObject(dic["unionid"], callBack: {
            (str:String) -> Void in
            self.unionid = str;
        });
        CommonTools.arrayCheckNullObject(dic["privilege"], callBack: {
            (arr:NSArray) -> Void in
            var temp:[String] = [];
            for item in arr{
                temp.append(item as! String);
            }
            self.privilege = temp;
        });
        
    }
    
}
