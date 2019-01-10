//
//  HYCashDetailTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/15.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYCashDetailTableViewCell: UITableViewCell {
    
    
    @IBOutlet weak var lblTitle: UILabel!
    @IBOutlet weak var lblTime: UILabel!
    @IBOutlet weak var lblPrice: UILabel!
    
    var dataModel:HYCashDetailDataModel!
    
    func setCashData(_ da:HYCashDetailDataModel) -> Void {
        self.dataModel = da;
        self.lblTitle.text = "";
        self.lblTime.text = "";
        self.lblPrice.text = "+0.00元";
        
        
        var strContent:String = "";
        
        if let payway:String = dataModel.withdrawal_way{
            if (PAY_WAY_WEIXIN == payway){
                if let weixinid:String = dataModel.withdrawal_to_alias{
                    strContent = "提现到微信号 \(weixinid)";
                }
            }
        }
        
        self.lblTitle.text = strContent;
        
        if let tm = dataModel.withdrawal_time{
            let d:Date = Date(timeIntervalSince1970: tm/1000);
            self.lblTime.text = CommonTools.nsDateToString(d, withFormat: "yyyy-MM-dd HH:mm");
        }
        
        if let n = dataModel.withdrawal_amount{
            let s:String = String(format: "%.2f",n);
            self.lblPrice.text = "+\(s)元";
        }
        
        
        
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        self.selectionStyle = UITableViewCellSelectionStyle.none
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
