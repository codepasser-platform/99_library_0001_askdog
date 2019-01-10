//
//  HYUserInfoListTopTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/9.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYUserInfoListTopTableViewCell: UITableViewCell {

    @IBOutlet weak var headerImgWidth: NSLayoutConstraint!
    @IBOutlet weak var headerImgV: UIImageView!
    @IBOutlet weak var bkView: UIView!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        
        
        
//        bkView.layer.cornerRadius = 5;
//        
//        bkView.layer.shadowColor = UIColor.black.cgColor;
//        bkView.layer.shadowOffset = CGSize(width: 3, height: 3);
//        bkView.layer.shadowOpacity = 0.4;
//        bkView.layer.shadowRadius = 3;

        headerImgV.layer.cornerRadius = headerImgWidth.constant/2.0;
        headerImgV.layer.masksToBounds = true;
        

        
        
    }
    
    func setData() -> Void {
        if let url:String = Global.sharedInstance.userInfo?.avatar{
            //headerImgV.sd_setImage(with: URL(string: url));
            headerImgV.sd_setImage(with: URL(string: url), placeholderImage: UIImage(named: "AvatarDefault"));
        }
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
