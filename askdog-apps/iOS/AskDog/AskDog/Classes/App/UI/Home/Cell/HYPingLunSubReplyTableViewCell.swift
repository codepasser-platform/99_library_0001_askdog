//
//  HYPingLunSubReplyTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/1.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYPingLunSubReplyTableViewCellDelegate :class {
    func hyPingLunSubReplyTableViewCellReplyBtnClicked(cell ce:HYPingLunSubReplyTableViewCell) -> Void ;
    
}

class HYPingLunSubReplyTableViewCell: UITableViewCell {

    @IBOutlet weak var headerImgWidth: NSLayoutConstraint!
    @IBOutlet weak var vipImgV: UIImageView!
    @IBOutlet weak var lblCreatTime: UILabel!
    @IBOutlet weak var lblContent: UILabel!
    @IBOutlet weak var lblName: UILabel!
    @IBOutlet weak var headerImgV: UIImageView!

    var dataDic:NSDictionary = NSDictionary();
    var dataModel:HYPinglunDataModel!
    var indexPath:IndexPath!
    weak var delegate:HYPingLunSubReplyTableViewCellDelegate?
    
    func setSubDataModel(_ da:HYPinglunDataModel) -> Void {
        dataModel = da;
        
        var replyName:String = "";
        
        if let reply:String = dataModel.reply_name{
            replyName = reply;
        }
        
        if let content:String = dataModel.content{
            //lblContent.text = content;
            
            //用富文本的方式
            let str:String = "@\(replyName)\(content)";
            let attrString:NSMutableAttributedString = NSMutableAttributedString(string: str);
            
            let range = str.range(of: "@\(replyName)");
            
            let loc:Int = str.characters.distance(from: str.startIndex, to: (range?.lowerBound)!);
            
            let distance:Int = str.characters.distance(from: (range?.lowerBound)!, to: (range?.upperBound)!);
            
            //let distance:Int = (<#T##String.CharacterView corresponding to your index##String.CharacterView#>.distance(from: (range?.lowerBound)!, to: (range?.upperBound)!));
            
           // print("\(range?.startIndex)  \(range?.endIndex)  \(distance)  \(loc)");
            
            let r:NSRange = NSMakeRange(loc, distance);
            
            attrString.addAttributes([NSForegroundColorAttributeName:CommonTools.rgbColor(red: 0, green: 170, blue: 239)], range: r);
            
            lblContent.attributedText = attrString;
        }
        

        
        if let commenter:HYCommenterDataModel = dataModel.commenter{
            if let name:String = commenter.name{
                lblName.text = name;
            }
            
            if let headerUrl:String = commenter.avatar{
                headerImgV.sd_setImage(with: URL(string: headerUrl),placeholderImage: UIImage(named: "AvatarDefault"));
            }
            
            if let be:Bool = commenter.isVip{
                self.vipImgV.isHidden = !be;
            }
            
        }
        

        
        if let createTm:TimeInterval = dataModel.creation_time{
            lblCreatTime.text = CommonTools.compareTime(createTm);
        }
        

    }
    
//    func setData(dic:NSDictionary) -> Void {
//        dataDic = dic;
//        
//        if let contentObj:AnyObject = dic["content"]{
//            let strContent:String = contentObj as! String;
//            lblContent.text = strContent;
//        }
//        
//        if let commenterObj:AnyObject = dic["commenter"]{
//            let commenterDic:[String:AnyObject]  = commenterObj as! [String:AnyObject];
//            
//            if let usernameObj:AnyObject = commenterDic["name"]{
//                lblName.text = usernameObj as? String;
//            }
//            
//        }
//        
//        if let createTmObj:AnyObject = dic["creation_time"]{
//            let createTm:NSTimeInterval = createTmObj as! NSTimeInterval;
//            lblCreatTime.text = CommonTools.compareTime(createTm);
//            
//        }
//        
//    }
    
    @IBAction func btnReplyClicked(_ sender: UIButton) {
        delegate?.hyPingLunSubReplyTableViewCellReplyBtnClicked(cell: self);
    }
    
    func heightOfMySelf() -> CGFloat {
        return 0;
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        lblName.backgroundColor = UIColor.clear;
        lblContent.backgroundColor = UIColor.clear;
        lblCreatTime.backgroundColor = UIColor.clear;
        
        lblContent.text = "";
        lblName.text = "";
        lblCreatTime.text = "";
        
        headerImgV.layer.cornerRadius = headerImgWidth.constant/2.0;
        headerImgV.layer.masksToBounds = true;
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
