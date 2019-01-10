//
//  HYHomeChannelModel.swift
//  AskDog
//
//  Created by Hooying_zhaody on 16/9/21.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYHomeChannelVideoDataModel: HYBaseDataModel{
    var width:Int?          // 视频宽度 （单位 px）
    var height:Int?         // 视频高度 （单位 px）
    var duration:Double?    // 视频时长 （单位 s）
    var format:String?      // 视频格式
    var file_size:Int?      // 视频文件大小 （单位 bit）
    var bit_rate:Double?    // 视频码率 （单位 kbps））
    var url:String?         // 视频URL地址
    var resolution:String?  // 视频清晰度标识，UHD(超清), FHD(全高清), HD(高清), SD(标清), LD(流畅)

    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.intCheckNullObject(dic["width"], callBack: {
            (int:Int) -> Void in
            self.width = int;
        });
        CommonTools.intCheckNullObject(dic["height"], callBack: {
            (int:Int) -> Void in
            self.height = int;
        });
        CommonTools.floatCheckNullObject(dic["duration"], callBack: {
            (int:Double) -> Void in
            self.duration = int;
        });
        CommonTools.stringCheckNullObject(dic["format"], callBack: {
            (str:String) -> Void in
            self.format = str;
        });
        CommonTools.intCheckNullObject(dic["file_size"], callBack: {
            (int:Int) -> Void in
            self.file_size = int;
        });
        CommonTools.floatCheckNullObject(dic["bit_rate"], callBack: {
            (int:Double) -> Void in
            self.bit_rate = int;
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

class HYHomeChannelContentDataModel: HYBaseDataModel{
    var type:String?   // 内容类别，VIDEO为视频经验
    var video:HYHomeChannelVideoDataModel? // 经验标题
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["type"], callBack: {
            (str:String) -> Void in
            self.type = str;
        });
        CommonTools.dicCheckNullObject(dic["video"], callBack: {
            (d:NSDictionary) -> Void in
            self.video = HYHomeChannelVideoDataModel(jsonDic: d);
        });
    }
    
}

class HYHomeChannelAuthorDataModel: HYBaseDataModel{
    var uuid:Int?       //经验作者ID
    var name:String?    //经验作者名字
    var tags:[String]?  //作者标签
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.intCheckNullObject(dic["uuid"], callBack: {
            (int:Int) -> Void in
            self.uuid = int;
        });
        CommonTools.stringCheckNullObject(dic["name"], callBack: {
            (str:String) -> Void in
            self.name = str;
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

class HYHomeChannelExperienceDataModel: HYBaseDataModel{
    var id:String?          //经验所属频道id
    var name:String?        //经验所属频道名字
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["name"], callBack: {
            (str:String) -> Void in
            self.name = str;
        });
    }
    
}

class HYHomeChannelParentDataModel: HYBaseDataModel{
    var id:String?
    var code:String?
    var name:String?
    var creation_date:Int?
//    var parent:{}       TODO
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["code"], callBack: {
            (str:String) -> Void in
            self.code = str;
        });
        CommonTools.stringCheckNullObject(dic["name"], callBack: {
            (str:String) -> Void in
            self.name = str;
        });
        CommonTools.intCheckNullObject(dic["creation_date"], callBack: {
            (int:Int) -> Void in
            self.creation_date = int;
        });
    }
    
}


class HYHomeChannelCategoryDataModel: HYBaseDataModel{
    var id:String?          //经验所属频道id
    var code:String?        //经验所属分类code
    var name:String?        //经验所属分类名字
    var creation_date:Int?
    var parent:HYHomeChannelParentDataModel?

    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["code"], callBack: {
            (str:String) -> Void in
            self.code = str;
        });
        CommonTools.stringCheckNullObject(dic["name"], callBack: {
            (str:String) -> Void in
            self.name = str;
        });
        CommonTools.intCheckNullObject(dic["creation_date"], callBack: {
            (int:Int) -> Void in
            self.creation_date = int;
        });
        CommonTools.dicCheckNullObject(dic["parent"], callBack: {
            (d:NSDictionary) -> Void in
            self.parent = HYHomeChannelParentDataModel(jsonDic: d);
        });
    }
    
}

class HYHomeChannelModel: HYBaseDataModel{
    var video_duration:Double?
    var id:String?   // 经验ID
    var subject:String? // 经验标题
    var content: HYHomeChannelContentDataModel?  // 经验内容
    var state:String? /// 经验状态，OPEN代表正常
    var creation_time:TimeInterval?  // 经验创建时间
    var thumbnail:String?   // 经验列表缩略图
    var view_count:Int?     // 经验浏览数
    var author:HYHomeChannelAuthorDataModel?
    var experience_detail_channel_ro:HYHomeChannelExperienceDataModel?
    var category_detail: HYHomeChannelCategoryDataModel?
    var price: Int?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["subject"], callBack: {
            (str:String) -> Void in
            self.subject = str;
        });
        CommonTools.dicCheckNullObject(dic["content"], callBack: {
            (d:NSDictionary) -> Void in
            self.content = HYHomeChannelContentDataModel(jsonDic: d);
        });
        CommonTools.stringCheckNullObject(dic["state"], callBack: {
            (str:String) -> Void in
            self.state = str;
        });
        CommonTools.nsTimeIntervalCheckNullObject(dic["creation_time"], callBack: {
            (tm:TimeInterval) -> Void in
            self.creation_time = tm;
        });
        CommonTools.stringCheckNullObject(dic["thumbnail"], callBack: {
            (str:String) -> Void in
            self.thumbnail = str;
        });
        CommonTools.intCheckNullObject(dic["view_count"], callBack: {
            (int:Int) -> Void in
            self.view_count = int;
        });
        CommonTools.dicCheckNullObject(dic["author"], callBack: {
            (d:NSDictionary) -> Void in
            self.author = HYHomeChannelAuthorDataModel(jsonDic: d);
        });
        CommonTools.dicCheckNullObject(dic["experience_detail_channel_ro"], callBack: {
            (d:NSDictionary) -> Void in
            self.experience_detail_channel_ro = HYHomeChannelExperienceDataModel(jsonDic: d);
        });
        CommonTools.dicCheckNullObject(dic["category_detail"], callBack: {
            (d:NSDictionary) -> Void in
            self.category_detail = HYHomeChannelCategoryDataModel(jsonDic: d);
        });
        CommonTools.intCheckNullObject(dic["price"], callBack: {
            (n:Int) -> Void in
            self.price = n;
        });
        CommonTools.floatCheckNullObject(dic["video_duration"], callBack: {
            (f:Double) -> Void in
            self.video_duration = f;
        });
    }
    
}


class HYHomeChanneltDataModel: HYBaseDataModel {
    var total:Int?
    var last:Bool?
    var result:[HYHomeChannelModel]?
    
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
            
            var res:[HYHomeChannelModel] = [HYHomeChannelModel]();
            for item in arr{
                let d:NSDictionary = item as! NSDictionary;
                let data:HYHomeChannelModel = HYHomeChannelModel(jsonDic: d);
                res.append(data);
            }
            self.result = res;
            
        });
    }
}

