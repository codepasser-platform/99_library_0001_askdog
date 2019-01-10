//
//  HYChannelListDataModel.swift
//  AskDog
//
//  Created by Symond on 16/8/10.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYChannelListDataModel: HYBaseDataModel {
    
    var id:String?
    var subscriber_count:Int?
    var name:String?
    var thumbnail:String?
    var subscribable:Bool?
    var subscribed:Bool?
    var deletable:Bool?
    var mine:Bool?
    var unread_exp_count:Int?

    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["name"], callBack: {
            (str:String) -> Void in
            self.name = str;
        });
        CommonTools.stringCheckNullObject(dic["thumbnail"], callBack: {
            (str:String) -> Void in
            self.thumbnail = str;
        });
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.intCheckNullObject(dic["subscriber_count"], callBack: {
            (n:Int) -> Void in
            self.subscriber_count = n;
        });
        CommonTools.boolCheckNullObject(dic["subscribable"], callBack: {
            (be:Bool) -> Void in
            self.subscribable = be;
        });
        CommonTools.boolCheckNullObject(dic["subscribed"], callBack: {
            (be:Bool) -> Void in
            self.subscribed = be;
        });
        CommonTools.boolCheckNullObject(dic["deletable"], callBack: {
            (be:Bool) -> Void in
            self.deletable = be;
        });
        CommonTools.boolCheckNullObject(dic["mine"], callBack: {
            (be:Bool) -> Void in
            self.mine = be;
        });
        CommonTools.intCheckNullObject(dic["unread_exp_count"], callBack: {
            (n:Int) -> Void in
            self.unread_exp_count = n;
        });
    }

}


class HYChannelListResultDataModel: HYBaseDataModel {
    var total: Int?
    var last: Bool?
    var result: [HYChannelListDataModel]?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.intCheckNullObject(dic["total"]) { (to: Int) in
            self.total = to
        }
        CommonTools.boolCheckNullObject(dic["last"]) { (la: Bool) in
            self.last = la
        }
        CommonTools.arrayCheckNullObject(dic["result"]) { (arr: NSArray) in
            var res: [HYChannelListDataModel] = [HYChannelListDataModel]()
            for item in arr {
                let d: NSDictionary = item as! NSDictionary
                let data: HYChannelListDataModel = HYChannelListDataModel(jsonDic: d)
                res.append(data)
            }
            self.result = res
        }
    }
}
