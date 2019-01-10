//
//  HYChannelExperienceListDataModel.swift
//  AskDog
//
//  Created by Symond on 16/8/11.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYExpContentVideoDataModel: HYBaseDataModel{
    
    var width:Int?
    var height:Int?
    var duration:Double?
    var format:String?
    var file_size:Double?
    var bit_rate:Double?
    var url:String?
    var resolution:String?
    
    
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.intCheckNullObject(dic["width"], callBack: {
            (n:Int) -> Void in
            self.width = n;
        });
        CommonTools.intCheckNullObject(dic["height"], callBack: {
            (n:Int) -> Void in
            self.height = n;
        });
        CommonTools.floatCheckNullObject(dic["duration"], callBack: {
            (f:Double) -> Void in
            self.duration = f;
        });
        CommonTools.stringCheckNullObject(dic["format"], callBack: {
            (str:String) -> Void in
            self.format = str;
        });
        CommonTools.floatCheckNullObject(dic["file_size"], callBack: {
            (f:Double) -> Void in
            self.file_size = f;
        });
        CommonTools.floatCheckNullObject(dic["bit_rate"], callBack: {
            (f:Double) -> Void in
            self.bit_rate = f;
        });
        CommonTools.stringCheckNullObject(dic["url"], callBack: {
            (str:String) -> Void in
            self.url = str;
        });
        CommonTools.stringCheckNullObject(dic["resolution"], callBack: {
            (str:String) -> Void in
            self.resolution = str;
        });
        
    }
    
}

class HYExpContentDataModel: HYBaseDataModel{
    
    var type:String?
    var video:HYExpContentVideoDataModel?
    var content:String?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["type"], callBack: {
            (str:String) -> Void in
            self.type = str;
        });
        CommonTools.stringCheckNullObject(dic["content"], callBack: {
            (str:String) -> Void in
            self.content = str;
        });
        if let videoObj:Any = dic["video"]{
            
            let videoObjDic:NSDictionary = videoObj as! NSDictionary;
            self.video = HYExpContentVideoDataModel(jsonDic: videoObjDic);
        }
    }
    
}

class HYExpCategoryDataModel: HYBaseDataModel{
    var id:String?
    var name:String?
    var code:String?
    
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["name"], callBack: {
            (str:String) -> Void in
            self.name = str;
        });
        CommonTools.stringCheckNullObject(dic["code"], callBack: {
            (str:String) -> Void in
            self.code = str;
        });
    }
    
}

class HYChannelExperienceListDataModel: HYBaseDataModel {
    
    var id:String?
    var subject:String?
    var content:HYExpContentDataModel?
    var category:HYExpCategoryDataModel?
    var state:String?
    var creation_time:TimeInterval?
    var thumbnail:String?
    var view_count:Int?
    var deleted:Bool?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["subject"], callBack: {
            (str:String) -> Void in
            self.subject = str;
        });
        if let contentObj:Any = dic["content"]{
            
            let contentObjDic:NSDictionary = contentObj as! NSDictionary;
            self.content = HYExpContentDataModel(jsonDic: contentObjDic);
        }

        if let categoryObj:Any = dic["category"]{
            
            let categoryObjDic:NSDictionary = categoryObj as! NSDictionary;
            self.category = HYExpCategoryDataModel(jsonDic: categoryObjDic);
        }
        CommonTools.stringCheckNullObject(dic["state"], callBack: {
            (str:String) -> Void in
            self.state = str;
        });
        CommonTools.nsTimeIntervalCheckNullObject(dic["creation_time"], callBack: {
            (n:TimeInterval) -> Void in
            self.creation_time = n;
        });
        CommonTools.stringCheckNullObject(dic["thumbnail"], callBack: {
            (str:String) -> Void in
            self.thumbnail = str;
        });
        CommonTools.intCheckNullObject(dic["view_count"], callBack: {
            (n:Int) -> Void in
            self.view_count = n;
        });
        CommonTools.boolCheckNullObject(dic["deleted"], callBack: {
            (be:Bool) -> Void in
            self.deleted = be;
        });
        
    }
}


class HYChannelExperienceListResultDataModel: HYBaseDataModel {
    var total: Int?
    var last: Bool?
    var result: [HYChannelExperienceListDataModel]?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.intCheckNullObject(dic["total"]) { (to: Int) in
            self.total = to
        }
        CommonTools.boolCheckNullObject(dic["last"]) { (la: Bool) in
            self.last = la
        }
        CommonTools.arrayCheckNullObject(dic["result"]) { (arr: NSArray) in
            var res: [HYChannelExperienceListDataModel] = [HYChannelExperienceListDataModel]()
            for item in arr {
                let d: NSDictionary = item as! NSDictionary
                let data: HYChannelExperienceListDataModel = HYChannelExperienceListDataModel(jsonDic: d)
                res.append(data)
            }
            self.result = res
        }
        
//        CommonTools.arrayFanxingCheckNullObject(dic["children"], callBack: {
//            (arr:[HYChannelExperienceListDataModel]) -> Void in
//            self.result = arr;
//        });
    }
}
