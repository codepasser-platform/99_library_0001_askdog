//
//  HYHomeChannelListCellTableViewCell.swift
//  AskDog
//
//  Created by Hooying_zhaody on 16/9/22.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYHomeChannelListCellDelegate: class{
    func channeclsListDidSelectedTableViewCell(cell:HYHomeChannelListCellTableViewCell, model:HYChannelRecommendModel, index:IndexPath) -> Void;
    
}

class HYHomeChannelListCellTableViewCell: UITableViewCell {
    @IBOutlet weak var l_Image: UIImageView!
    @IBOutlet weak var label: UILabel!
    @IBOutlet weak var r_button: UIButton!
    
    var delegate: HYHomeChannelListCellDelegate! = nil
    var data: HYChannelRecommendModel!
    var index: IndexPath!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        r_button.layer.borderWidth = 1.0;
        r_button.layer.cornerRadius = 3.0;
        r_button.layer.borderColor = CommonTools.rgbColor(red: 219, green: 225, blue: 239).cgColor;
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func reloadCell(model:HYChannelRecommendModel, index:IndexPath) -> Void {
        self.data = model;
        self.index = index;
        if let title: String = model.subject {
            self.label.text = title;
        }

        if let channeImg: String = model.channel_pic_url {
            l_Image.sd_setImage(with: URL(string: channeImg));
        } else {
            l_Image.image = UIImage(named: "channelDefault");
        }

        if let isSubscribed: Bool = model.subscribed {
            //FIXME: 无法判断是否是用户自己频道
            r_button.isSelected = isSubscribed;
        }
    }
    
    
    @IBAction func buttonAction(_ sender: AnyObject) {
        self.delegate.channeclsListDidSelectedTableViewCell(cell: self, model: data, index: index);

    }
}
