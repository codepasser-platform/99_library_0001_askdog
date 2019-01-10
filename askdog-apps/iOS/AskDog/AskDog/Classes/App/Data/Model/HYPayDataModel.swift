//
//  HYPayDataModel.swift
//  AskDog
//
//  Created by Symond on 16/9/6.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYPayRequestDataModel: HYBaseDataModel {
    var appid:String?
    var partnerid:String?
    var prepayid:String?
    var package:String?
    var noncestr:String?
    var timestamp:String?
    var sign:String?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["appid"], callBack: {
            (str:String) -> Void in
            self.appid = str;
        });
        CommonTools.stringCheckNullObject(dic["partnerid"], callBack: {
            (str:String) -> Void in
            self.partnerid = str;
        });
        CommonTools.stringCheckNullObject(dic["prepayid"], callBack: {
            (str:String) -> Void in
            self.prepayid = str;
        });
        CommonTools.stringCheckNullObject(dic["package"], callBack: {
            (str:String) -> Void in
            self.package = str;
        });
        CommonTools.stringCheckNullObject(dic["noncestr"], callBack: {
            (str:String) -> Void in
            self.noncestr = str;
        });
        CommonTools.stringCheckNullObject(dic["timestamp"], callBack: {
            (str:String) -> Void in
            self.timestamp = str;
        });
        CommonTools.stringCheckNullObject(dic["sign"], callBack: {
            (str:String) -> Void in
            self.sign = str;
        });
    }
    
}

class HYPayBeforeDataModel: HYBaseDataModel {
    var pay_way:String?
    var pay_status:String?
    var experience_id:String?
    var order_id:String?
    var pay_request:HYPayRequestDataModel?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["pay_way"], callBack: {
            (str:String) -> Void in
            self.pay_way = str;
        });
        CommonTools.stringCheckNullObject(dic["pay_status"], callBack: {
            (str:String) -> Void in
            self.pay_status = str;
        });
        CommonTools.stringCheckNullObject(dic["experience_id"], callBack: {
            (str:String) -> Void in
            self.experience_id = str;
        });
        CommonTools.stringCheckNullObject(dic["order_id"], callBack: {
            (str:String) -> Void in
            self.order_id = str;
        });
        CommonTools.dicCheckNullObject(dic["pay_request"], callBack: {
            (d:NSDictionary) -> Void in
            self.pay_request = HYPayRequestDataModel(jsonDic: d);
        });
    }

}
