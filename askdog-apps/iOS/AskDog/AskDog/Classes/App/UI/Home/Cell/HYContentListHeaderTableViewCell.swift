//
//  HYContentListHeaderTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/1.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYContentListHeaderTableViewCellDelegate  :class{
    func hyContentListHeaderTableViewCellBtnClicked(cell ce:HYContentListHeaderTableViewCell,sectionIndex section:Int) -> Void ;

}


class HYContentListHeaderTableViewCell: UITableViewCell {

    @IBOutlet weak var btn: UIButton!
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var lblTitle: UILabel!
    
    @IBOutlet weak var lineView: UILabel!
    @IBOutlet weak var bottom: NSLayoutConstraint!

    
    var sectionIndex:Int = -1;
    
    
    weak var delegate:HYContentListHeaderTableViewCellDelegate?
    
    
    func setCellBtnImage(_ img:UIImage) -> Void {
        btn.setImage(img, for: UIControlState());
    }
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        lineView.backgroundColor = UIColor.white;

    }
    
    func setTheSectionIndex(_ index:Int) -> Void {
        sectionIndex = index;
        if(1 == index){
            lineView.isHidden = true;
//            bkView.layer.shadowColor = UIColor.black.cgColor;
//            bkView.layer.shadowOffset = CGSize(width: 3, height: 3);
//            bkView.layer.shadowOpacity = 0.4;
//            bkView.layer.shadowRadius = 3;
//            bkView.layer.cornerRadius = 5;
            bottom.constant = -5;
        }else{
            lineView.isHidden = true;
//            bkView.layer.shadowColor = UIColor.blackColor().CGColor;
//            bkView.layer.shadowOffset = CGSizeMake(3, 3);
//            bkView.layer.shadowOpacity = 0.4;
//            bkView.layer.shadowRadius = 3;
            bkView.layer.cornerRadius = 5;
            bottom.constant = -7;
        }
    }

    @IBAction func btnClicked(_ sender: UIButton) {
        //print("tableview cell header btn clicked");

        delegate?.hyContentListHeaderTableViewCellBtnClicked(cell: self,sectionIndex: sectionIndex);
        
    }
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
}
