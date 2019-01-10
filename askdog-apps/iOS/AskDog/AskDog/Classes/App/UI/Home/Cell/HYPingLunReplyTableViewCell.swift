//
//  HYPingLunReplyTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/1.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYPingLunReplyTableViewCellDelegate  :class{
    func hyPingLunReplyTableViewCellOpenBtnClicked(cell ce:HYPingLunReplyTableViewCell) -> Void ;
    func hyPingLunReplyTableViewCellRrplyBtnClicked(cell ce:HYPingLunReplyTableViewCell) -> Void ;
    
}


class HYPingLunReplyTableViewCell: UITableViewCell {

    @IBOutlet weak var vipImageV: UIImageView!
    @IBOutlet weak var btnOpen: UIButton!
    @IBOutlet weak var lblReplyCountInfo: UILabel!
    @IBOutlet weak var headerImgV: UIImageView!
    @IBOutlet weak var lblName: UILabel!
    @IBOutlet weak var lblContent: UILabel!
    @IBOutlet weak var lblCreateTime: UILabel!
    @IBOutlet weak var headerImgWidth: NSLayoutConstraint!

    var dataDic:NSDictionary = NSDictionary();
    var dataModel:HYPinglunDataModel!
    
    var indexPath:IndexPath!
    weak var delegate:HYPingLunReplyTableViewCellDelegate?
    
    @IBAction func btnOpenClicked(_ sender: UIButton) {
        print("dataModel.ifOpen = \(dataModel.ifOpen)  title = \(btnOpen.titleLabel!.text))");
//        dataModel.ifOpen = !dataModel.ifOpen;
//        print("dataModel.ifOpen chage to  = \(dataModel.ifOpen)");
//        if(true == dataModel.ifOpen){
//            btnOpen.setTitle("收起", forState: .Normal);
//        }else{
//            btnOpen.setTitle("展开", forState: .Normal);
//        }
        delegate?.hyPingLunReplyTableViewCellOpenBtnClicked(cell: self);
    }
    
    
    func setCommentsDataModel(_ da:HYPinglunDataModel) -> Void {
        dataModel = da;
        
        var replyName:String = "";
        
        if let reply:String = dataModel.reply_name{
            replyName = reply;
        }
        
        if(true == dataModel.ifOpen){
            btnOpen.setTitle("收起", for: UIControlState());
        }else{
            btnOpen.setTitle("展开", for: UIControlState());
        }
        
        if let content:String = dataModel.content{
            //lblContent.text = content;
            
            //用富文本的方式
            let str:String = "@\(replyName)\(content)";
            let attrString:NSMutableAttributedString = NSMutableAttributedString(string: str);
            
            let range = str.range(of: "@\(replyName)");
            
            let loc:Int = str.characters.distance(from: str.startIndex, to: (range?.lowerBound)!);
            
            let distance:Int = str.characters.distance(from: (range?.lowerBound)!, to: (range?.upperBound)!);
            
           // let distance:Int = (<#T##String.CharacterView corresponding to your index##String.CharacterView#>.distance(from: (range?.lowerBound)!, to: (range?.upperBound)!));
            
            // print("\(range?.startIndex)  \(range?.endIndex)  \(distance)  \(loc)");
            
            let r:NSRange = NSMakeRange(loc, distance);
            
            attrString.addAttributes([NSForegroundColorAttributeName:CommonTools.rgbColor(red: 0, green: 170, blue: 239)], range: r);
            
            lblContent.attributedText = attrString;
            
            
        }
        
        if let commenter:HYCommenterDataModel = dataModel.commenter{
            if let name:String = commenter.name{
                //let n:String = "\(name)==[\(indexPath.section)]";
                lblName.text = name;
            }
        }
        
        if let createTm:TimeInterval = dataModel.creation_time{
            lblCreateTime.text = CommonTools.compareTime(createTm);
        }
        
        if let comments:[HYPinglunDataModel] = dataModel.comments?.result{
            let cnt:Int = comments.count;
            if(cnt <= 2){
                lblReplyCountInfo.text = "";
                if(dataModel.comments?.last == true){
                    btnOpen.isHidden = true;
                }else{
                    btnOpen.isHidden = false;
                }
                
            }else{
                lblReplyCountInfo.text = "\(cnt)条回复";
                btnOpen.isHidden = false;
                print("btnopen hidden = false ifopen = \(dataModel.ifOpen)");
            }
        }else{
            btnOpen.isHidden = true;
            lblReplyCountInfo.text = "";
        }
        
        if let commenter:HYCommenterDataModel = dataModel.commenter{
            if let headerUrl:String = commenter.avatar{
                headerImgV.sd_setImage(with: URL(string: headerUrl),placeholderImage: UIImage(named: "AvatarDefault"));
            }
            if let be:Bool = commenter.isVip{
                self.vipImageV.isHidden = !be;
            }
        }
        
        
    }
    
    @IBAction func btnReplyClicked(_ sender: UIButton) {
        delegate?.hyPingLunReplyTableViewCellRrplyBtnClicked(cell: self);
    }
    
    func heightOfMySelf() -> CGFloat {
        return 0;
    }
    
//    func setData(dic:NSDictionary) -> Void {
//        dataDic = dic;
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
//            lblCreateTime.text = CommonTools.compareTime(createTm);
//            
//        }
//        
//        
// 
//    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        lblName.text = "";
        lblContent.text = "";
        lblCreateTime.text = "";
        
        headerImgV.layer.cornerRadius = headerImgWidth.constant/2.0;
        headerImgV.layer.masksToBounds = true;
        
        lblName.backgroundColor = UIColor.clear;
        lblContent.backgroundColor = UIColor.clear;
        lblCreateTime.backgroundColor = UIColor.clear;
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    
}
