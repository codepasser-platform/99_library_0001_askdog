//
//  HYCategoryTreeDataModel.swift
//  AskDog
//
//  Created by Symond on 16/9/9.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYCategoryTreeDataModel: HYBaseDataModel {
    var id:String?
    var code:String?
    var name:String?
    var children:[HYCategoryTreeDataModel]?
    
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
        CommonTools.arrayCheckNullObject(dic["children"], callBack: {
            (arr:NSArray) -> Void in
            
            var ay:[HYCategoryTreeDataModel] = [HYCategoryTreeDataModel]();
            for item in arr{
                let d:NSDictionary = item as! NSDictionary;
                let da:HYCategoryTreeDataModel = HYCategoryTreeDataModel(jsonDic: d);
                ay.append(da);
            }
            self.children = ay;
            
        });
        
//        CommonTools.arrayFanxingCheckNullObject(dic["children"], callBack: {
//            (arr:[HYCategoryTreeDataModel]) -> Void in
//            self.children = arr;
//        });
    }
}
