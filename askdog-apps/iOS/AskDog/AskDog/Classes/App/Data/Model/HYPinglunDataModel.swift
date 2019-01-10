//
//  HYPinglunDataModel.swift
//  AskDog
//
//  Created by Symond on 16/8/4.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit


class HYCategoryDataModel: HYBaseDataModel {
    var id:String?
    var code:String?
    var name:String?
    var isSelected:Bool = false;
    
    override func dataProcess(_ dic: NSDictionary!){
        
        if let IdObj:Any = dic["id"]{
            self.id = IdObj as? String;
        }
        if let nameObj:Any = dic["name"]{
            self.name = nameObj as? String;
        }
        if let codeObj:Any = dic["code"]{
            self.code = codeObj as? String;
        }
        
    }
}

class HYTranscodeVideosDataModel: HYBaseDataModel{
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

class HYContentSnapshotsDataModel: HYBaseDataModel{
    var url:String?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["url"], callBack: {
            (str:String) -> Void in
            self.url = str;
        });
    }
}

class HYContentDataModel: HYBaseDataModel {
    var type:String?
    var content:String?
    var picture_link_ids:[String]?
    
    //视频
    var link_id:String?
    var snapshots:[HYContentSnapshotsDataModel]?
    var transcode_videos:[HYTranscodeVideosDataModel]?
    
    
    override func dataProcess(_ dic: NSDictionary!) {
        
        CommonTools.stringCheckNullObject(dic["type"], callBack: {
            (str:String) -> Void in
            self.type = str;
        });
        
        CommonTools.stringCheckNullObject(dic["content"], callBack: {
            (str:String) -> Void in
            self.content = str;
        });
        
        if let contentPicture_link_idsObj:Any = dic["picture_link_ids"]{
            self.picture_link_ids = contentPicture_link_idsObj as? [String];
        }
        
        CommonTools.stringCheckNullObject(dic["link_id"], callBack: {
            (str:String) -> Void in
            self.link_id = str;
        });
        
        if let snapshotsObj:Any = dic["snapshots"]{
            let arr:NSArray = snapshotsObj as! NSArray;
            var snapshotsArray:[HYContentSnapshotsDataModel] = [HYContentSnapshotsDataModel]();
            for item in arr{
                let snapshotsDic:NSDictionary = item as! NSDictionary;
                let sn:HYContentSnapshotsDataModel = HYContentSnapshotsDataModel(jsonDic: snapshotsDic);
                snapshotsArray.append(sn);
            }
            self.snapshots = snapshotsArray;
        }
        
        
        if let transcode_videosObj:Any = dic["transcode_videos"]{
            let transcode_videosArr:NSArray = transcode_videosObj as! NSArray;
            var transcodeVideoArray:[HYTranscodeVideosDataModel] = [HYTranscodeVideosDataModel]();
            for item in transcode_videosArr{
                let transcodeDic:NSDictionary = item as! NSDictionary;
                let transcodeModel:HYTranscodeVideosDataModel = HYTranscodeVideosDataModel(jsonDic: transcodeDic);
                transcodeVideoArray.append(transcodeModel);
            }
            
            self.transcode_videos = transcodeVideoArray;
        }
    }
}

class HYAuthorDataModel: HYBaseDataModel{
    var id:String?
    var name:String?
    var avatar:String?
    var vip:Bool?
    var gender:String?
    var signature:String?
    
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
        
        CommonTools.stringCheckNullObject(dic["signature"], callBack: {
            (str:String) -> Void in
            self.signature = str;
        });
        
        CommonTools.arrayCheckNullObject(dic["tags"], callBack: {
            (arr:NSArray) -> Void in
            if arr.contains("VIP"){
                self.vip = true;
            }else{
                self.vip = false;
            }
        });



    }
}

class HYChannelDataModel: HYBaseDataModel{
    var id:String?
    var name:String?
    var subscriber_count:Int?
    var tags:[String]?
    var subscribable:Bool?
    var subscribed:Bool?
    var mine:Bool?
    
    override func dataProcess(_ dic: NSDictionary!) {
        if let channelIdObj:Any = dic["id"]{
            self.id = channelIdObj as? String;
        }
        if let channelNameObj:Any = dic["name"]{
            self.name = channelNameObj as? String;
        }
        if let subscriber_countObj:Any = dic["subscriber_count"]{
            self.subscriber_count = subscriber_countObj as? Int;
        }
        if let tagsObj:Any = dic["tags"]{
            self.tags = tagsObj as? [String];
        }
        if let subscribableObj:Any = dic["subscribable"]{
            self.subscribable = subscribableObj as? Bool;
        }
        if let subscribedObj:Any = dic["subscribed"]{
            self.subscribed = subscribedObj as? Bool;
        }
        if let mineObj:Any = dic["mine"]{
            self.mine = mineObj as? Bool;
        }
    }
}

class HYCommenterDataModel: HYBaseDataModel{
    var id:String?
    var name:String?
    var avatar:String?
    var gender:String?
    var isVip:Bool?
    
    override func dataProcess(_ dic: NSDictionary!) {
        if let subCocommenterIdObj:Any = dic["id"]{
            self.id = subCocommenterIdObj as? String;
        }
        if let subCocommenterNameObj:Any = dic["name"]{
            self.name = subCocommenterNameObj as? String;
        }
        
        if let avatarObj:Any = dic["avatar"]{
            self.avatar = avatarObj as? String;
        }
        
        if let genderObj:Any = dic["gender"]{
            self.gender = genderObj as? String;
        }
        
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

class HYPinglunResultDataModel: HYBaseDataModel {
    var result:[HYPinglunDataModel]?
    var size:Int?
    var page:Int?
    var total:Int?
    var last:Bool?
    
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
        
        if let commentsArrayObj = dic["result"]{
            let arr:NSArray = commentsArrayObj as! NSArray;
            
            var subComments:[HYPinglunDataModel] = [HYPinglunDataModel]();
            
            for item in arr{
                let tempDic:NSDictionary = item as! NSDictionary;
                let d:HYPinglunDataModel = HYPinglunDataModel(jsonDic: tempDic);
                subComments.append(d);
            }
            self.result = subComments;
            
            
        }
        
    }
}

class HYPinglunDataModel: HYBaseDataModel{
    var id:String?
    var content:String?
    var commenter:HYCommenterDataModel?
    var creation_time:TimeInterval?
    var owner_id:Int?
    var mine:Bool?
    var deletable:Bool?
    var comments:HYPinglunResultDataModel?
    
    var reply_name:String?
    var reply_comment_id:String?
    
    var size:Int?
    var page:Int?
    var total:Int?
    var last:Bool?
    //是否展开
    var ifOpen:Bool! = false;
    //是否缓存了二级评论列表数据
    var ifCacheCommentsData:Bool! = false;
    
    override func dataProcess(_ dic: NSDictionary!) {
        if let commentsIdObj:Any = dic["id"]{
            self.id = commentsIdObj as? String;
        }
        if let commentsContentObj:Any = dic["content"]{
            self.content = commentsContentObj as? String;
        }
        
        if let commenterObj:Any = dic["commenter"]{
            let commenterDic:NSDictionary = commenterObj as! NSDictionary;
            let commenterDataModel:HYCommenterDataModel = HYCommenterDataModel(jsonDic: commenterDic);
            self.commenter = commenterDataModel;
        }
        
        CommonTools.dicCheckNullObject(dic["comments"], callBack: {
            (d:NSDictionary) -> Void in
            
            let tempD:HYPinglunResultDataModel = HYPinglunResultDataModel(jsonDic: d);
            self.comments = tempD;
            
        });
        
//        if let commentsCommentsObj:AnyObject = dic["comments"]{
//            
//            let resultDic:NSDictionary = commentsCommentsObj as! NSDictionary;
//            
//            if let commentsArrayObj = resultDic["result"]{
//                let arr:NSArray = commentsArrayObj as! NSArray;
//                
//                var subComments:[HYPinglunDataModel] = [HYPinglunDataModel]();
//                
//                for item in arr{
//                    let tempDic:NSDictionary = item as! NSDictionary;
//                    let d:HYPinglunDataModel = HYPinglunDataModel(jsonDic: tempDic);
//                    subComments.append(d);
//                }
//                self.comments = subComments;
//                
//                
//            }
//            
//            
////            var subComments:[HYPinglunDataModel] = [HYPinglunDataModel]();
////            
////            let arr:NSArray = commentsCommentsObj as! NSArray;
////            for item in arr{
////                let tempDic:NSDictionary = item as! NSDictionary;
////                let d:HYPinglunDataModel = HYPinglunDataModel(jsonDic: tempDic);
////                
////                subComments.append(d);
////            }
////            self.comments = subComments;
//        }
        
        
        if let deletedObj:Any = dic["deletable"]{
            self.deletable = deletedObj as? Bool;
        }
        if let mineObj:Any = dic["mine"]{
            self.mine = mineObj as? Bool;
        }
        if let ownerIdObj:Any = dic["owner_id"]{
            self.owner_id = ownerIdObj as? Int;
        }
        if let creation_timeObj:Any = dic["creation_time"]{
            self.creation_time = creation_timeObj as? TimeInterval;
        }
        
        CommonTools.stringCheckNullObject(dic["reply_name"], callBack: {
            (str:String) -> Void in
            self.reply_name = str;
        });
        CommonTools.stringCheckNullObject(dic["reply_comment_id"], callBack: {
            (str:String) -> Void in
            self.reply_comment_id = str;
        });
        
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

    }
    
}

//class HYSubCommentsDataModel: HYBaseDataModel{
//    var id:String?
//    var content:String?
//    var commenter:HYCommenterDataModel?
//    var creation_time:NSTimeInterval?
//    var mine:Bool?
//    var deleted:Bool?
//    var reply_comment_id:Int?
//    
//    override func dataProcess(dic: NSDictionary!) {
//        if let subCoIdObj:AnyObject = dic["id"]{
//            self.id = subCoIdObj as? String;
//        }
//        if let subCoCotObj:AnyObject = dic["content"]{
//            self.content = subCoCotObj as? String;
//        }
//        if let subCoCommenterObj:AnyObject = dic["commenter"]{
//            
//            let subCoCommenterDic:NSDictionary = subCoCommenterObj as! NSDictionary;
//            let subCommenterDataModel:HYCommenterDataModel = HYCommenterDataModel(jsonDic: subCoCommenterDic);
//            self.commenter = subCommenterDataModel;
//        }
//        if let subCreation_timeObj:AnyObject = dic["creation_time"]{
//            self.creation_time = subCreation_timeObj as? NSTimeInterval;
//        }
//        if let subMineObj:AnyObject = dic["mine"]{
//            self.mine = subMineObj as? Bool;
//        }
//        if let subDeletedObj:AnyObject = dic["deleted"]{
//            self.deleted = subDeletedObj as? Bool;
//        }
//        if let reply_comment_idObj:AnyObject = dic["reply_comment_id"]{
//            self.reply_comment_id = reply_comment_idObj as? Int;
//        }
//    }
//}
//
//class HYPinglunDataModel: HYBaseDataModel {
//    
//    var id:String?
//    var subject:String?
//    var content:HYContentDataModel?
//    var author:HYAuthorDataModel?
//    var channel:HYChannelDataModel?
//    var category:HYCategoryDataModel?
//    var state:String?
//    var price:Int?
//    var creation_time:NSTimeInterval?
//    var thumbnail:String?
//    var view_count:Int?
//    var up_vote_count:Int?
//    var down_vote_count:Int?
//    var up_voted:Bool?
//    var down_voted:Bool?
//    var mine:Bool?
//    var comment_count:Int?
//    var comments:[HYCommentsDataModel]?
//    var deleted:Bool?
//    //扩展
//
//    override func dataProcess(dic: NSDictionary!) {
//
//        CommonTools.stringCheckNullObject(dic["id"], callBack: {
//            (str:String) -> Void in
//            self.id = str;
//        });
//        
//        CommonTools.stringCheckNullObject(dic["subject"], callBack: {
//            (str:String) -> Void in
//            self.subject = str;
//        });
//        
//        if let contentObj:AnyObject = dic["content"]{
//            let contentDic:NSDictionary = contentObj as! NSDictionary;
//            self.content = HYContentDataModel(jsonDic: contentDic);
//        }
//        
//        if let authorObj:AnyObject = dic["author"]{
//            let authorDic:NSDictionary = authorObj as! NSDictionary;
//            self.author = HYAuthorDataModel(jsonDic: authorDic);
//        }
//        
//
//        if let channelObj:AnyObject = dic["channel"]{
//            let channelDic:NSDictionary = channelObj as! NSDictionary;
//            self.channel = HYChannelDataModel(jsonDic: channelDic);
//        }
//        
//        if let categoryObj:AnyObject = dic["category"]{
//            let categoryDic:NSDictionary = categoryObj as! NSDictionary;
//            self.category = HYCategoryDataModel(jsonDic: categoryDic);
//        }
//        
//        CommonTools.stringCheckNullObject(dic["state"], callBack: {
//            (str:String) -> Void in
//            self.state = str;
//        });
//
//        CommonTools.intCheckNullObject(dic["price"], callBack: {
//            (n:Int) -> Void in
//            self.price = n;
//        });
//        
//        CommonTools.nsTimeIntervalCheckNullObject(dic["creation_time"], callBack: {
//            (n:NSTimeInterval) -> Void in
//            self.creation_time = n;
//        });
//        
//        CommonTools.stringCheckNullObject(dic["thumbnail"], callBack: {
//            (str:String) -> Void in
//            self.thumbnail = str;
//        });
//        
//        CommonTools.intCheckNullObject(dic["view_count"], callBack: {
//            (n:Int) -> Void in
//            self.view_count = n;
//        });
//        
//        CommonTools.intCheckNullObject(dic["up_vote_count"], callBack: {
//            (n:Int) -> Void in
//            self.up_vote_count = n;
//        });
//        
//        CommonTools.intCheckNullObject(dic["down_vote_count"], callBack: {
//            (n:Int) -> Void in
//            self.down_vote_count = n;
//        });
//        
//        CommonTools.boolCheckNullObject(dic["mine"], callBack: {
//            (be:Bool) -> Void in
//            self.mine = be;
//        });
//        
//        CommonTools.intCheckNullObject(dic["comment_count"], callBack: {
//            (n:Int) -> Void in
//            self.comment_count = n;
//        });
//        
////        if let commentsObj:AnyObject = dic["comments"]{
////            let commentsArr:NSArray = commentsObj as! NSArray;
////            var commentsDataModelArray:[HYCommentsDataModel] = [HYCommentsDataModel]();
////            for commentItem in commentsArr{
////                let commentsDic:NSDictionary = commentItem as! NSDictionary;
////                let commentsDataModel:HYCommentsDataModel = HYCommentsDataModel(jsonDic: commentsDic);
////                commentsDataModelArray.append(commentsDataModel);
////            }
////            
////            self.comments = commentsDataModelArray;
////        }
//        
//        CommonTools.boolCheckNullObject(dic["deleted"], callBack: {
//            (be:Bool) -> Void in
//            self.deleted = be;
//        });
//
//        
//    }
//    
//
//}

class HYExperienceDetailDataModel: HYBaseDataModel {
    
    var id:String?
    var subject:String?
    var content:HYContentDataModel?
    var author:HYAuthorDataModel?
    var channel:HYChannelDataModel?
    var category:HYCategoryDataModel?
    var state:String?
    var price:Int?
    var creation_time:TimeInterval?
    var thumbnail:String?
    var view_count:Int?
    var up_vote_count:Int?
    var down_vote_count:Int?
    var up_voted:Bool?
    var down_voted:Bool?
    var mine:Bool?
    var comment_count:Int?
    //var comments:[HYCommentsDataModel]?
    var deleted:Bool?
    //是否已经支付
    var isPayed:Bool?
    //扩展
    
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
            let contentDic:NSDictionary = contentObj as! NSDictionary;
            self.content = HYContentDataModel(jsonDic: contentDic);
        }
        
        if let authorObj:Any = dic["author"]{
            let authorDic:NSDictionary = authorObj as! NSDictionary;
            self.author = HYAuthorDataModel(jsonDic: authorDic);
        }
        
        
        if let channelObj:Any = dic["channel"]{
            let channelDic:NSDictionary = channelObj as! NSDictionary;
            self.channel = HYChannelDataModel(jsonDic: channelDic);
        }
        
        if let categoryObj:Any = dic["category"]{
            let categoryDic:NSDictionary = categoryObj as! NSDictionary;
            self.category = HYCategoryDataModel(jsonDic: categoryDic);
        }
        
        CommonTools.stringCheckNullObject(dic["state"], callBack: {
            (str:String) -> Void in
            self.state = str;
        });
        
        self.isPayed = true;
        CommonTools.intCheckNullObject(dic["price"], callBack: {
            (n:Int) -> Void in
            self.price = n;
            if(0 == n){
                self.isPayed = true;
            }else{
                self.isPayed = false;
            }
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
        
        CommonTools.intCheckNullObject(dic["up_vote_count"], callBack: {
            (n:Int) -> Void in
            self.up_vote_count = n;
        });
        
        CommonTools.intCheckNullObject(dic["down_vote_count"], callBack: {
            (n:Int) -> Void in
            self.down_vote_count = n;
        });
        
        CommonTools.boolCheckNullObject(dic["mine"], callBack: {
            (be:Bool) -> Void in
            self.mine = be;
        });
        
        CommonTools.intCheckNullObject(dic["comment_count"], callBack: {
            (n:Int) -> Void in
            self.comment_count = n;
        });
        
        CommonTools.boolCheckNullObject(dic["deleted"], callBack: {
            (be:Bool) -> Void in
            self.deleted = be;
        });
        
        CommonTools.boolCheckNullObject(dic["up_voted"], callBack: {
            (be:Bool) -> Void in
            self.up_voted = be;
        });
        CommonTools.boolCheckNullObject(dic["down_voted"], callBack: {
            (be:Bool) -> Void in
            self.down_voted = be;
        });
        
        
    }
    
    
}
