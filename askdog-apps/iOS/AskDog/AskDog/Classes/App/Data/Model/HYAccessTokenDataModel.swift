//
//  HYAccessTokenDataModel.swift
//  AskDog
//
//  Created by Symond on 16/8/10.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYAccessTokenDataModel: HYBaseDataModel {
    
    var type:String?
    var policy:String?
    var signature:String?
    var host:String?
    var expire:Int?
    var callback:String?
    var OSSAccessKeyId:String?
    var key:String?
    var secret_key:String?
    var link_id:String?
    var bucket:String?
    var endpoint:String?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["type"], callBack: {
            (str:String) -> Void in
            self.type = str;
        });
        CommonTools.stringCheckNullObject(dic["policy"], callBack: {
            (str:String) -> Void in
            self.policy = str;
        });
        CommonTools.stringCheckNullObject(dic["signature"], callBack: {
            (str:String) -> Void in
            self.signature = str;
        });
        CommonTools.stringCheckNullObject(dic["host"], callBack: {
            (str:String) -> Void in
            self.host = str;
        });
        CommonTools.intCheckNullObject(dic["expire"], callBack: {
            (n:Int) -> Void in
            self.expire = n;
        });
        CommonTools.stringCheckNullObject(dic["callback"], callBack: {
            (str:String) -> Void in
            self.callback = str;
        });
        CommonTools.stringCheckNullObject(dic["OSSAccessKeyId"], callBack: {
            (str:String) -> Void in
            self.OSSAccessKeyId = str;
        });
        CommonTools.stringCheckNullObject(dic["key"], callBack: {
            (str:String) -> Void in
            self.key = str;
        });
        CommonTools.stringCheckNullObject(dic["secret_key"], callBack: {
            (str:String) -> Void in
            self.secret_key = str;
        });
        CommonTools.stringCheckNullObject(dic["link_id"], callBack: {
            (str:String) -> Void in
            self.link_id = str;
        });
        CommonTools.stringCheckNullObject(dic["bucket"], callBack: {
            (str:String) -> Void in
            self.bucket = str;
        });
        CommonTools.stringCheckNullObject(dic["endpoint"], callBack: {
            (str:String) -> Void in
            self.endpoint = str;
        });
    }

}
