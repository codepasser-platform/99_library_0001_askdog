//
//  HYUserCenterHomeTopTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/8.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYUserCenterHomeTopTableViewCellDelegate  :class{
    
    func hyUserCenterHomeTopTableViewCellEditBtnClicked(cell ce:HYUserCenterHomeTopTableViewCell,sectionIndex section:Int) -> Void ;
    
}

class HYUserCenterHomeTopTableViewCell: UITableViewCell {

    @IBOutlet weak var vipImgV: UIImageView!
    @IBOutlet weak var lblSign: UILabel!
    @IBOutlet weak var lblUserName: UILabel!
    @IBOutlet weak var headerImgV: UIImageView!
    @IBOutlet weak var bkView: UIView!    
    @IBOutlet weak var headerBkViewWidth: NSLayoutConstraint!
    weak var delegate:HYUserCenterHomeTopTableViewCellDelegate?
    
    @IBAction func btnEditClicked(_ sender: UIButton) {
        delegate?.hyUserCenterHomeTopTableViewCellEditBtnClicked(cell: self, sectionIndex: 0);
    }
    
    func setupData() -> Void {
        if let name:String = Global.sharedInstance.userInfo?.name {
            lblUserName.text = name;
        }
        if let url:String = Global.sharedInstance.userInfo?.avatar{
            headerImgV.sd_setImage(with: URL(string: url),placeholderImage: UIImage(named: "AvatarDefault"));
        }
        if let sign:String = Global.sharedInstance.userInfo?.signature{
            lblSign.text = sign;
        }else{
            lblSign.text = "";
        }
        if let beV:Bool = Global.sharedInstance.userInfo?.isVip{
            self.vipImgV.isHidden = !beV;
        }
        
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
//        bkView.layer.cornerRadius = 5;
//        bkView.clipsToBounds = true;
        
        headerImgV.layer.cornerRadius = (headerBkViewWidth.constant - 10)/2.0;
        headerImgV.layer.masksToBounds = true;
        
        self.vipImgV.isHidden = true;

        lblUserName.text = "";
        lblSign.text = "";
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
