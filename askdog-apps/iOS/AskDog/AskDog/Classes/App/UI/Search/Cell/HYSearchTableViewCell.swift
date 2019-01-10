//
//  HYSearchTableViewCell.swift
//  AskDog
//
//  Created by Hooying_zhaody on 16/8/29.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import SDWebImage

class HYSearchTableViewCell: UITableViewCell {

    @IBOutlet weak var bgView: UIView!
    @IBOutlet weak var leftImage: UIImageView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var classesLabel: UILabel!
    @IBOutlet weak var numberLabel: UILabel!
    var indexPath: IndexPath!
  
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        self.backgroundColor = UIColor.clear
        self.contentView.backgroundColor = UIColor.clear
        //加阴影
        bgView.layer.shadowColor = UIColor.black.cgColor;
        bgView.layer.shadowOffset = CGSize(width: 3, height: 3);
        bgView.layer.shadowOpacity = 0.4;
        bgView.layer.shadowRadius = 3;
        bgView.layer.cornerRadius = 3;
    }
    
    func cellRelodData(_ model: HYSearchActionModel, index:IndexPath) {
        indexPath = index
        
        // mark高亮
        let title: NSString = model.subject! as NSString
        let attr = HYMutableAttributedString()
        titleLabel.attributedText = attr.markString(title)
        
//        var category_img_name:String = ""
//        if let category_code = model.category?.category_code{
//            if let name:String = CATEGORY_IMG_DIC[category_code]{
//                category_img_name = name;
//            }else{
//                category_img_name = "channelDefault";
//            }
//        }
//        leftImage.image = UIImage(named: category_img_name)
//        
//        if let url:String = model.content_pic_url{
//            leftImage.sd_setImageWithURL(NSURL(string: url),placeholderImage: UIImage(named: ""));
//        }
        
        var category_img_name:String = "";
        if let category:String = model.category?.category_code{
            //把code转小写  abstract specific
            let strUrl:String = "http://pic.askdog.cn/resources/category/thumbnail/abstract/\(category.lowercased()).png@119w_64h_1e_1c.png";
            category_img_name = strUrl;
        }
        
        if let imgUrl:String = model.content_pic_url{

            leftImage.sd_setImage(with: URL(string: imgUrl), completed: {
                (img, err, tp, url) -> Void in
                
                if let e:Error = err{
                    print("1 errcode = \(e.localizedDescription)");
                    //如果发现错误 加载默认图片
                    self.leftImage.sd_setImage(with: URL(string: category_img_name), completed: {
                        (img, err1, tp, url) -> Void in
                        
                        if let e1:Error = err1{
                            print("2 errcode = \(e1.localizedDescription)");
                            //如果发现错误 加载本地的默认图片
                            self.leftImage.image = UIImage(named: "channelDefault");
                        }
                    });
                }
            });
            
        }else{
            //如果发现错误 加载默认图片
            self.leftImage.sd_setImage(with: URL(string: category_img_name), completed: {
                (img, err1, tp, url) -> Void in
                
                if let e1:Error = err1{
                    print("2 errcode = \(e1.localizedDescription)");
                    //如果发现错误 加载本地的默认图片
                    self.leftImage.image = UIImage(named: "channelDefault");
                }
            });
        }
        
        
        var channelName:String = ""
        var userName:String = ""
        
        if let channel:String = model.channel?.channel_name{
            channelName = channel;
        }
        if let name:String =  model.author?.name{
            userName = name;
        }
        classesLabel.text = "\(userName) > \(channelName)";
        
        
        var strTm:String = ""
        if let time:TimeInterval = model.creation_date {
            strTm = CommonTools.compareTime(time);
        }
        
        
        var nViewCount:Int = 0;
        if let cnt:Int = model.view_count{
            nViewCount = cnt;
        }
        numberLabel.text = strTm + " - 浏览数 " + String(nViewCount);

    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
