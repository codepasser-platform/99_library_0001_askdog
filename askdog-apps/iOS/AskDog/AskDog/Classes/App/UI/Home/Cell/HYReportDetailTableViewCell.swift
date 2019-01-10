//
//  HYReportDetailTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/25.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYReportDetailTableViewCellDelegate  :class{
    func hyReportDetailTableViewCellReportDidEndEditing(cell ce:HYReportDetailTableViewCell,indexPath index:IndexPath,editString str:String) -> Void ;
    
}



class HYReportDetailTableViewCell: UITableViewCell,UITextViewDelegate {
    
    var dataDic:[String:AnyObject]!
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var textViewDetail: UITextView!
    
    var cellIndex:IndexPath!
    
    var cornerType:CornerMaskType = CornerMaskType.cornerMaskType_None;
    var masklayer:CAShapeLayer!;
    
    weak var delegate:HYReportDetailTableViewCellDelegate?
    
    func setData(_ dic:[String:AnyObject]){
        dataDic = dic;
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        masklayer = CAShapeLayer();
    }
    
    override func layoutSubviews() {
        super.layoutSubviews();
        
        
        masklayer.removeFromSuperlayer();
        
//        let re:CGRect = CGRect(x: 0, y: 0, width: self.bounds.size.width-8, height: self.bounds.size.height);
        
//        var corner:UIRectCorner = [UIRectCorner.topLeft,UIRectCorner.topRight];
//        var size:CGSize = CGSize(width: 0, height: 0);
        
//        if(cornerType == CornerMaskType.cornerMaskType_Top){
//            corner = [UIRectCorner.topLeft,UIRectCorner.topRight];
//            size = CGSize(width: 5, height: 5);
//        }else if(cornerType == CornerMaskType.cornerMaskType_Bottom){
//            corner = [UIRectCorner.bottomLeft,UIRectCorner.bottomRight];
//            size = CGSize(width: 5, height: 5);
        
//            self.layer.shadowColor = UIColor.black.cgColor;
//            self.layer.shadowOffset = CGSize(width: 3, height: 2);
//            self.layer.shadowOpacity = 0.3;
//            self.layer.shadowRadius = 3;
//            
//        }else if(cornerType == CornerMaskType.cornerMaskType_All){
//            corner = UIRectCorner.allCorners;
//            size = CGSize(width: 5, height: 5);
        
//            self.layer.shadowColor = UIColor.black.cgColor;
//            self.layer.shadowOffset = CGSize(width: 3, height: 2);
//            self.layer.shadowOpacity = 0.3;
//            self.layer.shadowRadius = 3;
            
//        }else{
//            corner = UIRectCorner.allCorners;
//        }
        
//        let bezierPath:UIBezierPath = UIBezierPath(roundedRect: re, byRoundingCorners: corner, cornerRadii: size);
//        masklayer.frame = re;
//        masklayer.path = bezierPath.cgPath;
//        
//        bkView.layer.mask = masklayer;
        
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    //MARK:UITextViewDelegate
    func textViewDidEndEditing(_ textView: UITextView) {
        delegate?.hyReportDetailTableViewCellReportDidEndEditing(cell: self, indexPath: cellIndex,editString: self.textViewDetail.text);
    }
    
}
