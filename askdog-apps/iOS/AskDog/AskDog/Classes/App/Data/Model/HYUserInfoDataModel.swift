//
//  HYUserInfoDataModel.swift
//  AskDog
//
//  Created by Symond on 16/8/9.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYUserInfoAddressDataModel: HYBaseDataModel{
    var city:String?
    var province:String?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["city"], callBack: {
            (str:String) -> Void in
            self.city = str;
        });
        CommonTools.stringCheckNullObject(dic["province"], callBack: {
            (str:String) -> Void in
            self.province = str;
        });
    }

}

class HYUserInfoDataModel: HYBaseDataModel {
    
    var id:String?
    var name:String?
    var mail:String?
    var type:String?
    var notice_count:Int?
    var category_codes:[String]?
    var avatar:String?
    
    //FIXME:这里需要根据实际调
    var gender:String?
    var phone:String?
    var occupation:String?
    var school:String?
    var major:String?
    var address:HYUserInfoAddressDataModel?
    var signature:String?
    
    var isVip:Bool?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["name"], callBack: {
            (str:String) -> Void in
            self.name = str;
        });
        CommonTools.stringCheckNullObject(dic["type"], callBack: {
            (str:String) -> Void in
            self.type = str;
        });
        CommonTools.stringCheckNullObject(dic["mail"], callBack: {
            (str:String) -> Void in
            self.mail = str;
        });
        CommonTools.intCheckNullObject(dic["notice_count"], callBack: {
            (n:Int) -> Void in
            self.notice_count = n;
        });
        CommonTools.stringCheckNullObject(dic["avatar"], callBack: {
            (str:String) -> Void in
            self.avatar = str;
        });
        
        
        
        
        CommonTools.stringCheckNullObject(dic["gender"], callBack: {
            (str:String) -> Void in
            self.gender = str;
        });
        CommonTools.stringCheckNullObject(dic["phone"], callBack: {
            (str:String) -> Void in
            self.phone = str;
        });
        CommonTools.stringCheckNullObject(dic["occupation"], callBack: {
            (str:String) -> Void in
            self.occupation = str;
        });
        CommonTools.stringCheckNullObject(dic["school"], callBack: {
            (str:String) -> Void in
            self.school = str;
        });
        CommonTools.stringCheckNullObject(dic["major"], callBack: {
            (str:String) -> Void in
            self.major = str;
        });
        CommonTools.stringCheckNullObject(dic["signature"], callBack: {
            (str:String) -> Void in
            self.signature = str;
        });
        CommonTools.dicCheckNullObject(dic["address"], callBack: {
            (dic:NSDictionary) -> Void in
            self.address = HYUserInfoAddressDataModel(jsonDic: dic);
        });
        
        CommonTools.arrayCheckNullObject(dic["tags"], callBack: {
            (arr:NSArray) -> Void in
            if arr.contains("VIP"){
                self.isVip = true;
            }else{
                self.isVip = false;
            }
        });
    }

}
