//
//  HYChannelHomeTopTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/11.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYChannelHomeTopTableViewCellDelegate  :class{
    func hyChannelHomeTopTableViewCellSubscriptionBtnClicked(cell ce:HYChannelHomeTopTableViewCell,index row:Int) -> Void ;
    func hyChannelHomeTopTableViewCellChannelImgClicked(cell ce:HYChannelHomeTopTableViewCell,index row:Int) -> Void ;
}



class HYChannelHomeTopTableViewCell: UITableViewCell {
    
    
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var channelImageView: UIImageView!
    @IBOutlet weak var lblChannelTitle: UILabel!
    @IBOutlet weak var lblUserName: UILabel!
    @IBOutlet weak var lblCreateTm: UILabel!
    @IBOutlet weak var dingYueBkView: UIView!
    @IBOutlet weak var btnDingyue: UIButton!
    @IBOutlet weak var lblDingyueNumber: UILabel!
    
    @IBOutlet weak var imgVip: UIImageView!
    @IBOutlet weak var lblNameWidth: NSLayoutConstraint!
    var masklayer:CAShapeLayer!;
    var scribableState:SubScribableState! = SubScribableState.subScribable_Disable;
    
    var channelInfoDataModel:HYChannelInfoDataModel!
    
    weak var delegate:HYChannelHomeTopTableViewCellDelegate?
    
    
    
    @IBAction func channelImgClicked(_ sender: UIButton) {
        delegate?.hyChannelHomeTopTableViewCellChannelImgClicked(cell: self, index: 0);
    }
    func setChannelInfoData(_ data:HYChannelInfoDataModel){
        channelInfoDataModel = data;
        
        lblChannelTitle.text = "";
        lblUserName.text = "";
        channelImageView.image = UIImage(named: "channelDefault");
        setSubScribableState(.subScribable_Disable);
        lblCreateTm.text = "";
        
        
        
        if let title = channelInfoDataModel.name{
            lblChannelTitle.text = title;
        }
        if let user = channelInfoDataModel.owner?.name{
            lblUserName.text = user;
            //根据文字长度 算lbl尺寸
            
            let rect:CGRect = CommonTools.getStringRect(lblUserName.text, font: lblUserName.font, size: CGSize(width: 200, height: 21));
            self.lblNameWidth.constant = rect.size.width;
        }
        
        if let beVip:Bool = channelInfoDataModel.owner!.isVip{
            self.imgVip.isHidden = !beVip;
        }
        
        if let url = channelInfoDataModel.thumbnail{
            channelImageView.sd_setImage(with: URL(string: url), placeholderImage: UIImage(named: "channelDefault"));
        }
        
        //设置订阅
        
        if let count:Int = channelInfoDataModel.subscriber_count{
            lblDingyueNumber.text = String(count);
        }
        
        if let mineObj:Bool = channelInfoDataModel.mine{
            if(true == mineObj){
                setSubScribableState(.subScribable_Disable);
            }else{
                if let subscribable:Bool = channelInfoDataModel.subscribable{
                    if(true == subscribable){
                        setSubScribableState(.subScribable_Can);
                    }else{
                        setSubScribableState(.subScribable_Already);
                    }
                }
            }
        }else{
            if let subscribable:Bool = channelInfoDataModel.subscribable{
                if(true == subscribable){
                    setSubScribableState(.subScribable_Can);
                }else{
                    setSubScribableState(.subScribable_Already);
                }
            }
        }
        //创建日期
        if let time:TimeInterval = channelInfoDataModel.creation_time{
            let d:Date = Date(timeIntervalSince1970: time/1000);
            let strTm:String = CommonTools.nsDateToString(d, withFormat: "yyyy/MM/dd");
            lblCreateTm.text = "创建于\(strTm)";
        }
        
        
    }
    
    func setSubScribableStateAndCount(_ state:SubScribableState,beAdd:Bool) -> Void {
        setSubScribableState(state);
        if(true == beAdd){
            if let n = channelInfoDataModel.subscriber_count{
                let cnt = n + 1;
                channelInfoDataModel.subscriber_count = cnt;
                lblDingyueNumber.text = String(cnt);
            }
        }else{
            if let n = channelInfoDataModel.subscriber_count{
                var cnt = n - 1;
                cnt = cnt < 0 ? 0: cnt;
                channelInfoDataModel.subscriber_count = cnt;
                lblDingyueNumber.text = String(cnt);
            }
        }
    }
    
    //1 已订阅 2未订阅 3禁止订阅
    func setSubScribableState(_ state:SubScribableState) -> Void {
        self.scribableState = state;
        
        switch state {
        case .subScribable_Already:
            btnDingyue.setTitle("取消订阅", for: UIControlState());
            dingYueBkView.layer.borderColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164).cgColor;
            //lblDingyueSepatorLine.backgroundColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164);
            btnDingyue.backgroundColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164);
            dingYueBkView.layer.borderColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164).cgColor;
            lblDingyueNumber.textColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164);
            btnDingyue.isEnabled = true;
        case .subScribable_Can:
            btnDingyue.setTitle("订阅频道", for: UIControlState());
            dingYueBkView.layer.borderColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231).cgColor;
            //lblDingyueSepatorLine.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231);
            btnDingyue.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231);
            lblDingyueNumber.textColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231);
            dingYueBkView.layer.borderColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231).cgColor;
            btnDingyue.isEnabled = true;
        case .subScribable_Disable:
            btnDingyue.setTitle("订阅频道", for: UIControlState());
            dingYueBkView.layer.borderColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5).cgColor;
            //lblDingyueSepatorLine.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5);
            btnDingyue.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5);
            lblDingyueNumber.textColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5);
            dingYueBkView.layer.borderColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5).cgColor;
            btnDingyue.isEnabled = false;
        }
    }
    
    
    override func layoutSubviews() {
        super.layoutSubviews();
        
        
        masklayer.removeFromSuperlayer();
        let re:CGRect = CGRect(x: 0, y: 0, width: self.bounds.size.width-8, height: self.bounds.size.height);
        let corner:UIRectCorner = [UIRectCorner.topLeft,UIRectCorner.topRight];
        let size:CGSize = CGSize(width: 5, height: 5);
        let bezierPath:UIBezierPath = UIBezierPath(roundedRect: re, byRoundingCorners: corner, cornerRadii: size);
        masklayer.frame = re;
        masklayer.path = bezierPath.cgPath;
        bkView.layer.mask = masklayer;
        
        dingYueBkView.layer.cornerRadius = 3;
        dingYueBkView.layer.borderWidth = 1;
        dingYueBkView.clipsToBounds = true;
        dingYueBkView.layer.borderColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231).cgColor;
        
    }

    @IBAction func btnDingyueClicked(_ sender: UIButton) {
        
        delegate?.hyChannelHomeTopTableViewCellSubscriptionBtnClicked(cell: self, index: 0);
        
        
    }
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        masklayer = CAShapeLayer();
        self.imgVip.isHidden = true;
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
