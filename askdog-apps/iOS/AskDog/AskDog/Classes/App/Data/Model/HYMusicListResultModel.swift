//
//  HYMusicListResultModel.swift
//  AskDog
//
//  Created by Symond on 16/9/22.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYMusicListDataModel: HYBaseDataModel {
    var id:String?
    var name:String?
    var duration:Double?
    var url:String?
    var add_time:TimeInterval?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["name"], callBack: {
            (str:String) -> Void in
            self.name = str;
        });
        CommonTools.floatCheckNullObject(dic["duration"], callBack: {
            (f:Double) -> Void in
            self.duration = f;
        });
        CommonTools.stringCheckNullObject(dic["url"], callBack: {
            (str:String) -> Void in
            self.url = str;
        });
        CommonTools.nsTimeIntervalCheckNullObject(dic["add_time"], callBack: {
            (tm:TimeInterval) -> Void in
            self.add_time = tm;
        });
    }
}



class HYMusicListResultDataModel: HYBaseDataModel {
    var size:Int?
    var page:Int?
    var total:Int?
    var last:Bool?
    var result:[HYMusicListDataModel]?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.intCheckNullObject(dic["size"], callBack: {
            (n:Int) -> Void in
            self.size = n;
        });
        CommonTools.intCheckNullObject(dic["page"], callBack: {
            (n:Int) -> Void in
            self.page = n;
        });
        CommonTools.intCheckNullObject(dic["total"], callBack: {
            (n:Int) -> Void in
            self.total = n;
        });
        CommonTools.boolCheckNullObject(dic["last"], callBack: {
            (be:Bool) -> Void in
            self.last = be;
        });
//        CommonTools.arrayCheckNullObject(dic["result"], callBack: {
//            (arr:NSArray) -> Void in
//            self.result = arr as! [HYMusicListDataModel];
//        });
        
        CommonTools.arrayFanxingCheckNullObject(dic["result"], callBack: {
            (obj:[HYMusicListDataModel]) -> Void in
            self.result = obj;
        });
    }
}
