//
//  HYChannelRecommendDataModel.swift
//  AskDog
//
//  Created by Hooying_zhaody on 16/9/22.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYChannelRecommendModel: HYBaseDataModel {
    var subject: String?                    //频道名字
    var id: String?                         // 频道id
    var channel_pic_url: String?            //频道缩略图
    var subscribed: Bool?                 //是否订阅过（登录后可见）

    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["subject"], callBack: {
            (str:String) -> Void in
            self.subject = str;
        });
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["channel_pic_url"], callBack: {
            (str:String) -> Void in
            self.channel_pic_url = str;
        });
        CommonTools.boolCheckNullObject(dic["subscribed"], callBack: {
            (be:Bool) -> Void in
            self.subscribed = be;
        });
    }
}

class HYChannelRecommendDataModel: HYBaseDataModel {
    var total:Int?
    var last:Bool?
    var result:[HYChannelRecommendModel]?
    
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
            
            var res:[HYChannelRecommendModel] = [HYChannelRecommendModel]();
            for item in arr{
                let d:NSDictionary = item as! NSDictionary;
                let data:HYChannelRecommendModel = HYChannelRecommendModel(jsonDic: d);
                res.append(data);
            }
            self.result = res;
            
        });
    }

}
