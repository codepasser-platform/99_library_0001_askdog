//
//  HYUserCenterHomePageListTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/8.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit


class HYUserCenterHomePageListTableViewCell: UITableViewCell {

    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var lblTitle: UILabel!
    @IBOutlet weak var iconImgView: UIImageView!
    @IBOutlet weak var redPoint: UIImageView!
    
    var cornerType:CornerMaskType = CornerMaskType.cornerMaskType_None;
    
//    var masklayer:CAShapeLayer!;
    
    override func awakeFromNib() {
        super.awakeFromNib()
        //self.accessoryType = .DisclosureIndicator;
        //self.accessoryView?.backgroundColor = UIColor.redColor();
        // Initialization code
        
        //加阴影
        //bkView.layer.shadowColor = UIColor.blackColor().CGColor;
        //bkView.layer.shadowOffset = CGSizeMake(3, 0);
        //bkView.layer.shadowOpacity = 0.4;
        //bkView.layer.shadowRadius = 3;
        //bkView.layer.cornerRadius = 5;
        
//        masklayer = CAShapeLayer();
        
        
    }
    
    override func layoutSubviews() {
        super.layoutSubviews();
        
//        masklayer.removeFromSuperlayer();
//        
//        let re:CGRect = CGRect(x: 0, y: 0, width: self.bounds.size.width-8, height: self.bounds.size.height);
//        
//        var corner:UIRectCorner = [UIRectCorner.topLeft,UIRectCorner.topRight];
//        var size:CGSize = CGSize(width: 0, height: 0);
//        
//        if(cornerType == CornerMaskType.cornerMaskType_Top){
//            corner = [UIRectCorner.topLeft,UIRectCorner.topRight];
//            size = CGSize(width: 5, height: 5);
//        }else if(cornerType == CornerMaskType.cornerMaskType_Bottom){
//            corner = [UIRectCorner.bottomLeft,UIRectCorner.bottomRight];
//            size = CGSize(width: 5, height: 5);
//        }else{
//            corner = UIRectCorner.allCorners;
//        }
//        
//        let bezierPath:UIBezierPath = UIBezierPath(roundedRect: re, byRoundingCorners: corner, cornerRadii: size);
//        masklayer.frame = re;
//        masklayer.path = bezierPath.cgPath;
//        bkView.layer.mask = masklayer;
        
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func setCellInfo(_ dic:NSDictionary) -> Void {
        let icon:String = dic["icon"] as! String;
        iconImgView.image = UIImage(named: icon);
        let title:String = dic["title"] as! String;
        lblTitle.text = title;
    }
    
}
