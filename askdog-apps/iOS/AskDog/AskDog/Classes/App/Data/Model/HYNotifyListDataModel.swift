//
//  HYNotifyListDataModel.swift
//  AskDog
//
//  Created by Symond on 16/8/30.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYNotifyDateDataModel: HYBaseDataModel {
    
    var y:Int?
    var m:Int?
    var d:Int?
    
    
    override func dataProcess(_ dic: NSDictionary!) {
        
        CommonTools.intCheckNullObject(dic["y"], callBack: {
            (n:Int) -> Void in
            self.y = n;
        });
        CommonTools.intCheckNullObject(dic["m"], callBack: {
            (n:Int) -> Void in
            self.m = n;
        });
        CommonTools.intCheckNullObject(dic["d"], callBack: {
            (n:Int) -> Void in
            self.d = n;
        });
    }
    
}

class HYNotifyGroupDataContentTargetOwnerDataModel: HYBaseDataModel {
    
    var id:String?
    var Description:String?
    var deleted:Bool?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["description"], callBack: {
            (str:String) -> Void in
            self.Description = str;
        });
        CommonTools.boolCheckNullObject(dic["deleted"], callBack: {
            (be:Bool) -> Void in
            self.deleted = be;
        });
    }
    
}

class HYNotifyGroupDataContentTargetDataModel: HYBaseDataModel {
    
    var id:String?
    var Description:String?
    var deleted:Bool?
    var owner:HYNotifyGroupDataContentTargetOwnerDataModel?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["description"], callBack: {
            (str:String) -> Void in
            self.Description = str;
        });
        CommonTools.boolCheckNullObject(dic["deleted"], callBack: {
            (be:Bool) -> Void in
            self.deleted = be;
        });
        CommonTools.dicCheckNullObject(dic["owner"], callBack: {
            (d:NSDictionary) -> Void in
            let ow:HYNotifyGroupDataContentTargetOwnerDataModel = HYNotifyGroupDataContentTargetOwnerDataModel(jsonDic: d);
            self.owner = ow;
        });
    }
    
}

class HYNotifyGroupDataContentUserDataModel: HYBaseDataModel {
    
    var id:String?
    var name:String?

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

class HYNotifyGroupDataContentDataModel: HYBaseDataModel {
    
    var user:HYNotifyGroupDataContentUserDataModel?
    var type:String?
    var target:HYNotifyGroupDataContentTargetDataModel?

    
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.dicCheckNullObject(dic["user"], callBack: {
            (d:NSDictionary) -> Void in
            let ur:HYNotifyGroupDataContentUserDataModel = HYNotifyGroupDataContentUserDataModel(jsonDic: d);
            self.user = ur;
        });
        CommonTools.stringCheckNullObject(dic["type"], callBack: {
            (str:String) -> Void in
            self.type = str;
        });
        CommonTools.dicCheckNullObject(dic["target"], callBack: {
            (d:NSDictionary) -> Void in
            let tg:HYNotifyGroupDataContentTargetDataModel = HYNotifyGroupDataContentTargetDataModel(jsonDic: d);
            self.target = tg;
        });
    }
    
}

class HYNotifyGroupDataDataModel: HYBaseDataModel {
    
    var id:String?
    var notification_type:String?
    var recipient:Int?
    var content:HYNotifyGroupDataContentDataModel?
    var read:Bool?
    var create_date:Int?
    
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["notification_type"], callBack: {
            (str:String) -> Void in
            self.notification_type = str;
        });
        CommonTools.intCheckNullObject(dic["recipient"], callBack: {
            (n:Int) -> Void in
            self.recipient = n;
        });
        CommonTools.dicCheckNullObject(dic["content"], callBack: {
            (d:NSDictionary) -> Void in
            let ct:HYNotifyGroupDataContentDataModel = HYNotifyGroupDataContentDataModel(jsonDic: d);
            self.content = ct;
        });
        CommonTools.boolCheckNullObject(dic["read"], callBack: {
            (be:Bool) -> Void in
            self.read = be;
        });
        CommonTools.intCheckNullObject(dic["create_date"], callBack: {
            (n:Int) -> Void in
            self.create_date = n;
        });
    }
    
}



class HYNotifyListDataModel: HYBaseDataModel {
    
    var group_date:HYNotifyDateDataModel?
    var group_data:[HYNotifyGroupDataDataModel]?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.dicCheckNullObject(dic["group_date"], callBack: {
            (d:NSDictionary) -> Void in
            
            let gd:HYNotifyDateDataModel = HYNotifyDateDataModel(jsonDic: d);
            self.group_date = gd;
        });
        
        CommonTools.arrayCheckNullObject(dic["group_data"], callBack: {
            (arr:NSArray) -> Void in
            var newArr:[HYNotifyGroupDataDataModel] = [];
            for item in arr{
                let tempD:NSDictionary = item as! NSDictionary;
                let data:HYNotifyGroupDataDataModel = HYNotifyGroupDataDataModel(jsonDic: tempD);
                newArr.append(data);
            }
            self.group_data = newArr;
            
        });
        
        
    }

}

class HYNotifyResultListDataModel: HYBaseDataModel {
    
    var result:[HYNotifyListDataModel]?
    var size:Int?
    var page:Int?
    var total:Int?
    var last:Bool?
    
    override func dataProcess(_ dic: NSDictionary!) {

        CommonTools.arrayCheckNullObject(dic["result"], callBack: {
            (arr:NSArray)  -> Void in
            
            var newArr:[HYNotifyListDataModel] = [];
            
            for item in arr{
                let tempD:NSDictionary = item as! NSDictionary;
                let data:HYNotifyListDataModel = HYNotifyListDataModel(jsonDic: tempD);
                newArr.append(data);
            }
            self.result = newArr;
            
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
