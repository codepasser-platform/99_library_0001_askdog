//
//  HYPayStatusDataModel.swift
//  AskDog
//
//  Created by Symond on 16/9/6.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYPayStatusDataModel: HYBaseDataModel {
    var pay_status:String?
    var experience_id:String?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["pay_status"], callBack: {
            (str:String) -> Void in
            self.pay_status = str;
        });
        CommonTools.stringCheckNullObject(dic["experience_id"], callBack: {
            (str:String) -> Void in
            self.experience_id = str;
        });
    }
    
}
