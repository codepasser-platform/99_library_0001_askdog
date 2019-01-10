//
//  HYChannelHomeSecondTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/11.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYChannelHomeSecondTableViewCell: UITableViewCell {

    @IBOutlet weak var imgV: UIImageView!
    @IBOutlet weak var lblContent: UILabel!
    @IBOutlet weak var bkView: UIView!
    
    
    var masklayer:CAShapeLayer!;
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        masklayer = CAShapeLayer();
        lblContent.text = "";
    }
    
    override func layoutSubviews() {
        super.layoutSubviews();
        
        
        masklayer.removeFromSuperlayer();
        let re:CGRect = CGRect(x: 0, y: 0, width: self.bounds.size.width-8, height: self.bounds.size.height-4);
        let corner:UIRectCorner = [UIRectCorner.bottomLeft,UIRectCorner.bottomRight];
        let size:CGSize = CGSize(width: 5, height: 5);
        let bezierPath:UIBezierPath = UIBezierPath(roundedRect: re, byRoundingCorners: corner, cornerRadii: size);
        masklayer.frame = re;
        masklayer.path = bezierPath.cgPath;
        bkView.layer.mask = masklayer;
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
