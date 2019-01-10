//
//  HYExperienceListDataModel.swift
//  AskDog
//
//  Created by Symond on 16/8/15.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYExperienceListCategoryDataModel: HYBaseDataModel {
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

class HYExperienceListChannelDataModel: HYBaseDataModel {
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

class HYExperienceListAuthorDataModel: HYBaseDataModel {
    var avatar_url:String?
    var name:String?
    var id:String?
    var vip:Bool?
    
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
    }
}


class HYExperienceListDataModel: HYBaseDataModel {
    
    var _from:String?
    var content_type:String?
    var subject:String?
    var content_pic_url:String?
    var author:HYExperienceListAuthorDataModel?
    var channel:HYExperienceListChannelDataModel?
    var category:HYExperienceListCategoryDataModel?
    var content_text:String?
    var creation_date:TimeInterval?
    var id:String?
    var view_count:Int?
    
    override func dataProcess(_ dic: NSDictionary!) {
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
        CommonTools.stringCheckNullObject(dic["content_text"], callBack: {
            (str:String) -> Void in
            self.content_text = str;
        });
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        if let authorObj = dic["author"]{
            let d:NSDictionary = authorObj as! NSDictionary;
            let da:HYExperienceListAuthorDataModel = HYExperienceListAuthorDataModel(jsonDic: d);
            self.author = da;
        }
        if let channelObj = dic["channel"]{
            let d:NSDictionary = channelObj as! NSDictionary;
            let da:HYExperienceListChannelDataModel = HYExperienceListChannelDataModel(jsonDic: d);
            self.channel = da;
        }
        if let categoryObj = dic["category"]{
            let d:NSDictionary = categoryObj as! NSDictionary;
            let da:HYExperienceListCategoryDataModel = HYExperienceListCategoryDataModel(jsonDic: d);
            self.category = da;
        }
        CommonTools.nsTimeIntervalCheckNullObject(dic["creation_date"], callBack: {
            (tm:TimeInterval) -> Void in
            self.creation_date = tm;
        });
        CommonTools.intCheckNullObject(dic["view_count"], callBack: {
            (n:Int) -> Void in
            self.view_count = n;
        });
    }
    

}
