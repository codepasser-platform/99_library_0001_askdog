//
//  HYSearchActionModel.swift
//  AskDog
//
//  Created by Hooying_zhaody on 16/8/29.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit


class HYSearchCategoryModel: HYBaseDataModel {
    var category_code: String?     //用户头像
    var category_name: String?           //用户名字
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["category_code"], callBack: {
            (str:String) -> Void in
            self.category_code = str;
        });
        CommonTools.stringCheckNullObject(dic["category_name"], callBack: {
            (str:String) -> Void in
            self.category_name = str;
        });
    }
}



class HYSearchAuthorModel: HYBaseDataModel {
    var avatar_url: String?     //用户头像
    var name: String?           //用户名字
    var id: String?             //用户ID
    var tags: NSArray?           //用户标签
    
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
        CommonTools.arrayCheckNullObject(dic[""]) { (arr:NSArray) -> Void in
            self.tags = arr
        }
    }
}


class HYSearchChannelModel: HYBaseDataModel {
    var channel_name: String?                   // 频道名字
    var channel_thumbnail: String?              // 频道缩略图url
    var channel_id: String?                     // 频道id
    
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



class HYSearchActionModel: HYBaseDataModel {
    var hot_score: Int?                             // 分享分数
    var subject: String?                            // 分享标题
    var content_pic_url: String?                    // 分享内容缩略图url
    var video_duration: Double?                     // 如果为视频的话显示
    var author: HYSearchAuthorModel?                // 用户信息
    var content_text: String?                       // 分享文字内容
    var channel: HYSearchChannelModel?              //频道信息
    var creation_date: TimeInterval?                         // 创建时间
    var id: String?                                    // 分享id
    var vip: Bool?                                  // 是否是VIP
    var view_count: Int?                            // 浏览数
    var content_type: String?                        // 内容类型：图文或者视频
    var category:HYSearchCategoryModel?
    var price: Int?
    
    override func dataProcess(_ dic: NSDictionary!) {
        
        CommonTools.intCheckNullObject(dic["hot_score"], callBack: {
            (value:Int) -> Void in
            self.hot_score = value;
        });
        CommonTools.stringCheckNullObject(dic["subject"], callBack: {
            (str:String) -> Void in
            self.subject = str;
        });
        CommonTools.stringCheckNullObject(dic["content_pic_url"], callBack: {
            (str:String) -> Void in
            self.content_pic_url = str;
        });
        CommonTools.floatCheckNullObject(dic["video_duration"], callBack: {
            (value: Double) -> Void in
            self.video_duration = value;
        });
        
        if let contentObj:Any = dic["author"]{
            let contentObjDic:NSDictionary = contentObj as! NSDictionary;
            self.author = HYSearchAuthorModel(jsonDic: contentObjDic);
        }
        if let contentObj:Any = dic["category"]{
            let contentObjDic:NSDictionary = contentObj as! NSDictionary;
            self.category = HYSearchCategoryModel(jsonDic: contentObjDic);
        }
        if let contentObj:Any = dic["channel"]{
            let contentObjDic:NSDictionary = contentObj as! NSDictionary;
            self.channel = HYSearchChannelModel(jsonDic: contentObjDic);
        }

        CommonTools.stringCheckNullObject(dic["content_text"], callBack: {
            (str:String) -> Void in
            self.content_text = str;
        });

        CommonTools.nsTimeIntervalCheckNullObject(dic["creation_date"], callBack: {
            (n:TimeInterval) -> Void in
            self.creation_date = n;
        });
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.boolCheckNullObject(dic["vip"], callBack: {
            (value:Bool) -> Void in
            self.vip = value;
        });
        CommonTools.intCheckNullObject(dic["view_count"], callBack: {
            (vlue:Int) -> Void in
            self.view_count = vlue;
        });
        CommonTools.stringCheckNullObject(dic["content_type"], callBack: {
            (str:String) -> Void in
            self.content_text = str;
        });
        CommonTools.intCheckNullObject(dic["price"], callBack: {
            (n:Int) -> Void in
            self.price = n;
        });

    }
    
}

class HYSearchActionResultModel: HYBaseDataModel {
    var total: Int?
    var last: Bool?
    var result: [HYSearchActionModel]?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.intCheckNullObject(dic["total"]) { (to: Int) in
            self.total = to
        }
        CommonTools.boolCheckNullObject(dic["last"]) { (la: Bool) in
            self.last = la
        }
        CommonTools.arrayCheckNullObject(dic["result"]) { (arr: NSArray) in
            var res: [HYSearchActionModel] = [HYSearchActionModel]()
            for item in arr {
                let d: NSDictionary = item as! NSDictionary
                let data: HYSearchActionModel = HYSearchActionModel(jsonDic: d)
                res.append(data)
            }
            self.result = res
        }
    }
}
