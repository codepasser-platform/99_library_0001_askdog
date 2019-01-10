//
//  HYMyChannelListTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/10.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYMyChannelListTableViewCellDelegate  :class{
    func hyMyChannelListTableViewCellMenuBtnClicked(cell ce:HYMyChannelListTableViewCell,index row:Int) -> Void ;
    
}

class HYMyChannelListTableViewCell: UITableViewCell {

    @IBOutlet weak var lblDingyueNumber: UILabel!
    @IBOutlet weak var lblChannelTitle: UILabel!
    @IBOutlet weak var iconView: UIImageView!
    @IBOutlet weak var bkView: UIView!
    var index:Int!
    var dataModel:HYChannelListDataModel!
    
    weak var delegate:HYMyChannelListTableViewCellDelegate?
    
    func setChannelData(_ data:HYChannelListDataModel) -> Void {
        dataModel = data;
        
        lblChannelTitle.text = "";
        lblDingyueNumber.text = "";
        iconView.image = UIImage(named: "channelDefault");
        dataModel = data;
        
        if let name:String = dataModel.name{
            lblChannelTitle.text = name;
        }
        if let number:Int = dataModel.subscriber_count{
            lblDingyueNumber.text = String(number);
        }
        if let url:String = dataModel.thumbnail{
            iconView.sd_setImage(with: URL(string: url),placeholderImage: UIImage(named: "channelDefault"));
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        //加阴影
//        bkView.layer.shadowColor = UIColor.black.cgColor;
//        bkView.layer.shadowOffset = CGSize(width: 3, height: 3);
//        bkView.layer.shadowOpacity = 0.4;
//        bkView.layer.shadowRadius = 3;
//        bkView.layer.cornerRadius = 3;
    }

    @IBAction func btnMenuClicked(_ sender: UIButton) {
        delegate?.hyMyChannelListTableViewCellMenuBtnClicked(cell: self, index: index);
    }
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
