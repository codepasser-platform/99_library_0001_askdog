//
//  HYDingyuePindaoCollectionViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/5.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYDingyuePindaoCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var selectedImgV: UIImageView!
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var imgView: UIImageView!
    @IBOutlet weak var lblTitle: UILabel!
    var data:HYCategoryDataModel!
    
//    let CHANNEL_IMG_DIC:[String:String] = ["LAW":"law_sub",
//                                           "SCIENCE":"science_sub",
//                                            "EDUCATION":"Education_sub",
//                                            "ART":"art_sub",
//                                            "ENTERTAINMENT":"Entertainment_sub",
//                                            "CULTURE":"Culture_sub",
//                                            "LIFE":"life_sub",
//                                            "ECONOMIC":"economy_sub",
//                                            "HEALTH":"Health_sub",
//                                            "IT":"IT_sub"];
    
    
    func  setDataModel(_ da:HYCategoryDataModel) -> Void {
        data = da;
        
        //http://pic.askdog.cn/resources/category/thumbnail/specific/{code}.png
        //http://pic.askdog.cn/resources/category/thumbnail/specific/life.png@240w_135h_1e_1c.png
        
        if let name:String = data.name{
            lblTitle.text = name;
        }
        
        if let channel_code = data.code{
            //把code转小写  abstract specific
            let strUrl:String = "http://pic.askdog.cn/resources/category/thumbnail/specific/\(channel_code.lowercased()).png@240w_135h_1e_1c.png";
            print("url = \(strUrl)");
            imgView.sd_setImage(with: URL(string:strUrl) , placeholderImage: UIImage(named: "channelDefault"));
        }
        
        selectedImgV.isHidden = !data.isSelected;
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        bkView.layer.cornerRadius = 5;
        bkView.layer.borderColor = UIColor.lightGray.cgColor;
        bkView.layer.borderWidth = 1;
        bkView.clipsToBounds = true;
        
    }

}
