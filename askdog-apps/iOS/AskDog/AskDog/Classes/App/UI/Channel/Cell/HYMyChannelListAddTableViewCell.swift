//
//  HYMyChannelListAddTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/10.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit



class HYMyChannelListAddTableViewCell: UITableViewCell {

    @IBOutlet weak var bkView: UIView!
    

    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        //加阴影
//        bkView.layer.shadowColor = UIColor.black.cgColor;
//        bkView.layer.shadowOffset = CGSize(width: 3, height: 3);
//        bkView.layer.shadowOpacity = 0.4;
//        bkView.layer.shadowRadius = 3;
//        bkView.layer.cornerRadius = 3;
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
