//
//  HYServiceInfoDataModel.swift
//  AskDog
//
//  Created by Symond on 16/9/10.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYServiceInfoDataModel: HYBaseDataModel {
    var version:String?
    var status:String?
    var message:String?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["version"], callBack: {
            (str:String) -> Void in
            self.version = str;
        });
        CommonTools.stringCheckNullObject(dic["status"], callBack: {
            (str:String) -> Void in
            self.status = str;
        });
        CommonTools.stringCheckNullObject(dic["message"], callBack: {
            (str:String) -> Void in
            self.message = str;
        });
    }
}
