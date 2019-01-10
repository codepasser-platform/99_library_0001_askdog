//
//  HYIncomeDataModel.swift
//  AskDog
//
//  Created by Symond on 16/8/15.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYIncomeDataModel: HYBaseDataModel {
    //单位分
    var total:Float?
    var balance:Float?
    var locked:Float?
    var withdrawal_total:Float?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.intCheckNullObject(dic["total"], callBack: {
            (n:Int) -> Void in
            self.total = Float(n/100);
            
        });
        CommonTools.intCheckNullObject(dic["balance"], callBack: {
            (n:Int) -> Void in
            self.balance = Float(n/100);
            
        });
        CommonTools.intCheckNullObject(dic["locked"], callBack: {
            (n:Int) -> Void in
            self.locked = Float(n/100);
            
        });
        CommonTools.intCheckNullObject(dic["withdrawal_total"], callBack: {
            (n:Int) -> Void in
            self.withdrawal_total = Float(n/100);
            
        });
    }
}

class HYIncomeDetailPayerDataModel: HYBaseDataModel{
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

class HYIncomeDetailDataModel: HYBaseDataModel {
    var income_time:TimeInterval?
    var price:Double?
    var experience_title:String?
    var payer:HYIncomeDetailPayerDataModel?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.nsTimeIntervalCheckNullObject(dic["income_time"], callBack: {
            (tm:TimeInterval) -> Void in
            self.income_time = tm;
        });
        CommonTools.stringCheckNullObject(dic["experience_title"], callBack: {
            (str:String) -> Void in
            self.experience_title = str;
        });
        CommonTools.floatCheckNullObject(dic["price"], callBack: {
            (f:Double) -> Void in
            self.price = f/100;
        });
        if let payerObj = dic["payer"]{
            let d:NSDictionary = payerObj as! NSDictionary;
            let data:HYIncomeDetailPayerDataModel = HYIncomeDetailPayerDataModel(jsonDic: d);
            self.payer = data;
        }
    }
}

class HYIncomeDetailResultDataModel: HYBaseDataModel {
    var total: Int?
    var last: Bool?
    var result: [HYIncomeDetailDataModel]?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.intCheckNullObject(dic["total"]) { (to: Int) in
            self.total = to
        }
        CommonTools.boolCheckNullObject(dic["last"]) { (la: Bool) in
            self.last = la
        }
        CommonTools.arrayCheckNullObject(dic["result"]) { (arr: NSArray) in
            var res: [HYIncomeDetailDataModel] = [HYIncomeDetailDataModel]()
            for item in arr {
                let d: NSDictionary = item as! NSDictionary
                let data: HYIncomeDetailDataModel = HYIncomeDetailDataModel(jsonDic: d)
                res.append(data)
            }
            self.result = res
        }
    }
}


class HYCashDetailDataModel: HYBaseDataModel{
    var id:String?
    var withdrawal_way:String?
    var withdrawal_time:TimeInterval?
    var withdrawal_amount:Double?
    var withdrawal_status:String?
    var withdrawal_to:String?
    var withdrawal_to_alias:String?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.stringCheckNullObject(dic["id"], callBack: {
            (str:String) -> Void in
            self.id = str;
        });
        CommonTools.stringCheckNullObject(dic["withdrawal_way"], callBack: {
            (str:String) -> Void in
            self.withdrawal_way = str;
        });
        CommonTools.stringCheckNullObject(dic["withdrawal_to_alias"], callBack: {
            (str:String) -> Void in
            self.withdrawal_to_alias = str;
        });
        CommonTools.stringCheckNullObject(dic["withdrawal_status"], callBack: {
            (str:String) -> Void in
            self.withdrawal_status = str;
        });
        CommonTools.stringCheckNullObject(dic["withdrawal_to"], callBack: {
            (str:String) -> Void in
            self.withdrawal_to = str;
        });
        CommonTools.nsTimeIntervalCheckNullObject(dic["withdrawal_time"], callBack: {
            (tm:TimeInterval) -> Void in
            self.withdrawal_time = tm;
        });
        CommonTools.floatCheckNullObject(dic["withdrawal_amount"], callBack: {
            (f:Double) -> Void in
            self.withdrawal_amount = f/100;
            
        });
    }
}

class HYCashResultDetailDataModel: HYBaseDataModel {
    var total: Int?
    var last: Bool?
    var result: [HYCashDetailDataModel]?
    
    override func dataProcess(_ dic: NSDictionary!) {
        CommonTools.intCheckNullObject(dic["total"]) { (to: Int) in
            self.total = to
        }
        CommonTools.boolCheckNullObject(dic["last"]) { (la: Bool) in
            self.last = la
        }
        CommonTools.arrayCheckNullObject(dic["result"]) { (arr: NSArray) in
            var res: [HYCashDetailDataModel] = [HYCashDetailDataModel]()
            for item in arr {
                let d: NSDictionary = item as! NSDictionary
                let data: HYCashDetailDataModel = HYCashDetailDataModel(jsonDic: d)
                res.append(data)
            }
            self.result = res
        }
    }
}
