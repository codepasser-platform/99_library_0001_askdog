//
//  HYChannelInfoDataModel.swift
//  AskDog
//
//  Created by Symond on 16/8/11.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYChannelInfoOwnerDataModel: HYBaseDataModel{
    var id:String?
    var name:String?
    var avatar:String?
    var gender:String?
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
        CommonTools.stringCheckNullObject(dic["avatar"], callBack: {
            (str:String) -> Void in
            self.avatar = str;
        });
        CommonTools.stringCheckNullObject(dic["gender"], callBack: {
            (str:String) -> Void in
            self.gender = str;
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

class HYChannelInfoDataModel: HYBaseDataModel {
    
    var id:String?
    var name:String?
    var Description:String?
    var subscriber_count:Int?
    var thumbnail:String?
    var owner:HYChannelInfoOwnerDataModel?
    var tags:[String]?
    var subscribable:Bool?
    var subscribed:Bool?
    var mine:Bool?
    var deletable:Bool?
    var creation_time:TimeInterval?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["name"], callBack: {
            (str:String) -> Void in
            self.name = str;
        });
        CommonTools.stringCheckNullObject(dic["description"], callBack: {
            (str:String) -> Void in
            self.Description = str;
        })
        CommonTools.intCheckNullObject(dic["subscriber_count"], callBack: {
            (n:Int) -> Void in
            self.subscriber_count = n;
        });
        CommonTools.stringCheckNullObject(dic["thumbnail"], callBack: {
            (str:String) -> Void in
            self.thumbnail = str;
        })
        if let ownerObj:Any = dic["owner"]{
            
            let ownerDic:NSDictionary = ownerObj as! NSDictionary;
            self.owner = HYChannelInfoOwnerDataModel(jsonDic: ownerDic);
        }
        if let tagsObj:Any = dic["tags"]{
            self.tags = tagsObj as? [String];
        }
        CommonTools.boolCheckNullObject(dic["subscribable"], callBack: {
            (be:Bool) -> Void in
            self.subscribable = be;
        });
        CommonTools.boolCheckNullObject(dic["subscribed"], callBack: {
            (be:Bool) -> Void in
            self.subscribed = be;
        });
        CommonTools.boolCheckNullObject(dic["mine"], callBack: {
            (be:Bool) -> Void in
            self.mine = be;
        });
        CommonTools.boolCheckNullObject(dic["deletable"], callBack: {
            (be:Bool) -> Void in
            self.deletable = be;
        });
        CommonTools.nsTimeIntervalCheckNullObject(dic["creation_time"], callBack: {
            (n:TimeInterval) -> Void in
            self.creation_time = n;
        });
    }

}
