//
//  HYSearchHeardTableViewCell.swift
//  AskDog
//
//  Created by Hooying_zhaody on 16/8/30.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYSearchHeardTableViewCell: UITableViewCell {

    let Color_bule = UIColor(red: 62/255, green: 190/255, blue: 253/255, alpha: 1)
    let Color_galy = UIColor(red: 135/255, green: 146/255, blue: 164/255, alpha: 1)
    
    @IBOutlet weak var bgViwe: UIView!
    @IBOutlet weak var titleLabel: UILabel!
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        self.backgroundColor = UIColor.clear
        self.contentView.backgroundColor = UIColor.clear
//        //加阴影
//        bgViwe.layer.shadowColor = UIColor.black.cgColor;
//        bgViwe.layer.shadowOffset = CGSize(width: 3, height: 3);
//        bgViwe.layer.shadowOpacity = 0.4;
//        bgViwe.layer.shadowRadius = 3;
//        bgViwe.layer.cornerRadius = 3;
    }

    
    func cellRelodData(_ dataCount: String, searchVale: String) {

        let str = "\(dataCount)个\"\(searchVale)\"搜索结果"
        let attributeString = NSMutableAttributedString(string:str).grayString
        
        //设置字体颜色
        let dataL:Int = dataCount.characters.count
        let valeL:Int = searchVale.characters.count
        attributeString.addAttribute(NSForegroundColorAttributeName, value: Color_bule, range: NSMakeRange(dataL + 1, valeL + 2))
        titleLabel.attributedText = attributeString
}
    
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
