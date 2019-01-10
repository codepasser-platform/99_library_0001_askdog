//
//  HYIncomeListTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/15.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYIncomeListTableViewCell: UITableViewCell {
    
    var dataModel:HYIncomeDetailDataModel!

    @IBOutlet weak var lblIncome: UILabel!
    @IBOutlet weak var lblTime: UILabel!
    @IBOutlet weak var lblTitle: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        self.lblTime.text = "";
        self.lblIncome.text = "+￥0.00";
        self.lblTitle.text = "";
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func setIncomeData(_ da:HYIncomeDetailDataModel) -> Void {
        self.dataModel = da;
        if let title = dataModel.experience_title{
            self.lblTitle.text = title;
        }
        
        if let n = dataModel.price{

            let s:String = String(format: "%.2f",n);
            self.lblIncome.text = "+\(s)元";
        }
        

        
        if let tm = dataModel.income_time{
            let d:Date = Date(timeIntervalSince1970: tm/1000);
            self.lblTime.text = CommonTools.nsDateToString(d, withFormat: "yyyy-MM-dd HH:mm");
        }
    }
    
}
