//
//  HYInputPingLunTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/2.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYInputPingLunTableViewCellDelegate :class {
    func hyInputPingLunTableViewCellReplyBtnClicked(cell ce:HYInputPingLunTableViewCell) -> Void ;
    
}

class HYInputPingLunTableViewCell: UITableViewCell,UITextViewDelegate, HYLimitTextDelegate {
    
    weak var delegate:HYInputPingLunTableViewCellDelegate?

    @IBOutlet weak var vipImageV: UIImageView!
    @IBOutlet weak var headerWidth: NSLayoutConstraint!
    @IBOutlet weak var headerImgV: UIImageView!
    @IBOutlet weak var btnPingLun: UIButton!
    @IBOutlet weak var textViewContent: PlaceholderTextView!
    let limit = HYLimitText()
    var indexPath:IndexPath!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        textViewContent.placeholder = "请输入评论，最多输入100字！"
        self.textViewContent.text = "";
        textViewContent.layer.borderWidth = 2;
        textViewContent.layer.borderColor = CommonTools.rgbColor(red: 0, green: 170, blue: 239).cgColor;
        
        //TODO 最多输入字数
        limit.addNotTextView(textViewContent)
        limit.delegate = self
        limit.maxLength = 100
        
        headerImgV.layer.cornerRadius = headerWidth.constant/2.0;
        headerImgV.layer.masksToBounds = true;
    }
    @IBAction func btnCancelClicked(_ sender: UIButton) {
        self.textViewContent.text = "";
    }

    @IBAction func btnPinglunClicked(_ sender: UIButton) {
        delegate?.hyInputPingLunTableViewCellReplyBtnClicked(cell: self);
    }
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func updateData() -> Void {
        
        if (false == Global.sharedInstance.isLogined){
            self.vipImageV.isHidden = true;
        }else{
            if let url = Global.sharedInstance.userInfo?.avatar{
                headerImgV.sd_setImage(with: URL(string: url),placeholderImage: UIImage(named: "AvatarDefault"));
            }
        }

    }
    
    //MARK:UITextViewDelegate
    func textViewDidChange(_ textView: UITextView) {
        if(textView.text == ""){
            self.btnPingLun.isEnabled = false;
        }else{
            self.btnPingLun.isEnabled = true;
        }
    }
    
}
