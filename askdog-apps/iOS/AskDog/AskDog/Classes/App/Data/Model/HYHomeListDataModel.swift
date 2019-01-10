//
//  HYHomeListDataModel.swift
//  AskDog
//
//  Created by Symond on 16/9/1.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYHomeListCategoryDataModel: HYBaseDataModel{
    var category_name:String?
    var category_code:String?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["category_name"], callBack: {
            (str:String) -> Void in
            self.category_name = str;
        });
        CommonTools.stringCheckNullObject(dic["category_code"], callBack: {
            (str:String) -> Void in
            self.category_code = str;
        });
    }

}

class HYHomeListChannelDataModel: HYBaseDataModel{
    var channel_name:String?
    var channel_thumbnail:String?
    var channel_id:String?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["channel_name"], callBack: {
            (str:String) -> Void in
            self.channel_name = str;
        });
        CommonTools.stringCheckNullObject(dic["channel_thumbnail"], callBack: {
            (str:String) -> Void in
            self.channel_thumbnail = str;
        });
        CommonTools.stringCheckNullObject(dic["channel_id"], callBack: {
            (str:String) -> Void in
            self.channel_id = str;
        });
    }
}

class HYHomeListAuthorDataModel: HYBaseDataModel{
    var avatar_url:String?
    var name:String?
    var id:String?
    var vip:Bool?
    var tags:[String]?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["avatar_url"], callBack: {
            (str:String) -> Void in
            self.avatar_url = str;
        });
        CommonTools.stringCheckNullObject(dic["name"], callBack: {
            (str:String) -> Void in
            self.name = str;
        });
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.boolCheckNullObject(dic["vip"], callBack: {
            (be:Bool) -> Void in
            self.vip = be;
        });
        CommonTools.arrayCheckNullObject(dic["tags"], callBack: {
            (arr:NSArray) -> Void in
            
            var newArr:[String] = [String]();
            
            for item in arr{
                newArr.append(item as! String);
            }
            self.tags = newArr;
        });
    }
}

class HYHomeListDataModel: HYBaseDataModel{
    var video_duration:Double?
    var _from:String?
    var content_type:String?
    var subject:String?
    var content_pic_url:String?
    var author:HYHomeListAuthorDataModel?
    var channel:HYHomeListChannelDataModel?
    var creation_date:TimeInterval?
    var id:String?
    var category:HYHomeListCategoryDataModel?
    var view_count:Int?
    var isHot:Bool?
    var isNewest:Bool?
    var isRecommended:Bool?
    var price: Int?
    
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.floatCheckNullObject(dic["video_duration"], callBack: {
            (f:Double) -> Void in
            self.video_duration = f;
        });
        CommonTools.stringCheckNullObject(dic["_from"], callBack: {
            (str:String) -> Void in
            self._from = str;
        });
        CommonTools.stringCheckNullObject(dic["content_type"], callBack: {
            (str:String) -> Void in
            self.content_type = str;
        });
        CommonTools.stringCheckNullObject(dic["subject"], callBack: {
            (str:String) -> Void in
            self.subject = str;
        });
        CommonTools.stringCheckNullObject(dic["content_pic_url"], callBack: {
            (str:String) -> Void in
            self.content_pic_url = str;
        });
        CommonTools.dicCheckNullObject(dic["author"], callBack: {
            (d:NSDictionary) -> Void in
            self.author = HYHomeListAuthorDataModel(jsonDic: d);
        });
        CommonTools.dicCheckNullObject(dic["channel"], callBack: {
            (d:NSDictionary) -> Void in
            self.channel = HYHomeListChannelDataModel(jsonDic: d);
        });
        CommonTools.nsTimeIntervalCheckNullObject(dic["creation_date"], callBack: {
            (tm:TimeInterval) -> Void in
            self.creation_date = tm;
        });
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.dicCheckNullObject(dic["category"], callBack: {
            (d:NSDictionary) -> Void in
            self.category = HYHomeListCategoryDataModel(jsonDic: d);
        });
        CommonTools.intCheckNullObject(dic["view_count"], callBack: {
            (n:Int) -> Void in
            self.view_count = n;
        });
        CommonTools.boolCheckNullObject(dic["isHot"], callBack: {
            (be:Bool) -> Void in
            self.isHot = be;
        });
        CommonTools.boolCheckNullObject(dic["isNewest"], callBack: {
            (be:Bool) -> Void in
            self.isNewest = be;
        });
        CommonTools.boolCheckNullObject(dic["isRecommended"], callBack: {
            (be:Bool) -> Void in
            self.isRecommended = be;
        });
        CommonTools.intCheckNullObject(dic["price"], callBack: {
            (n:Int) -> Void in
            self.price = n;
        });
    }
    
}

class HYHomeListResultDataModel: HYBaseDataModel {
    
    var total:Int?
    var last:Bool?
    var result:[HYHomeListDataModel]?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.intCheckNullObject(dic["total"], callBack: {
            (n:Int) -> Void in
            self.total = n;
        });
        CommonTools.boolCheckNullObject(dic["last"], callBack: {
            (be:Bool) -> Void in
            self.last = be;
        });
        CommonTools.arrayCheckNullObject(dic["result"], callBack: {
            (arr:NSArray) -> Void in
            
            var res:[HYHomeListDataModel] = [HYHomeListDataModel]();
            for item in arr{
                let d:NSDictionary = item as! NSDictionary;
                let data:HYHomeListDataModel = HYHomeListDataModel(jsonDic: d);
                res.append(data);
            }
            self.result = res;
            
        });
    }

}
