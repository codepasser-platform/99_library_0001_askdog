//
//  HYContentListFenxiangTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/1.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import SDWebImage

class HYContentListFenxiangTableViewCell: UITableViewCell {

    @IBOutlet weak var lblViewInfo: UILabel!
    @IBOutlet weak var lblChannelName: UILabel!
    @IBOutlet weak var lblContentTitle: UILabel!
    @IBOutlet weak var imgV: UIImageView!
    @IBOutlet weak var bkView: UIView!
    var dataModel:HYExperienceListDataModel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
//        bkView.layer.shadowColor = UIColor.black.cgColor;
//        bkView.layer.shadowOffset = CGSize(width: 3, height: 3);
//        bkView.layer.shadowOpacity = 0.4;
//        bkView.layer.shadowRadius = 3;
//        bkView.layer.cornerRadius = 5;
        bkView.backgroundColor = UIColor.white;
        lblContentTitle.backgroundColor = UIColor.clear;
        lblChannelName.backgroundColor = UIColor.clear;
        lblViewInfo.backgroundColor = UIColor.clear;
    }
    
    func setData(_ da:HYExperienceListDataModel) -> Void {
        dataModel = da;
        
        var category_img_name:String = "";
        if let category:String = dataModel?.category?.category_code{
            //把code转小写  abstract specific
            let strUrl:String = "http://pic.askdog.cn/resources/category/thumbnail/abstract/\(category.lowercased()).png@119w_67h_1e_1c.png";
            category_img_name = strUrl;
        }
        
        if let imgUrl:String = dataModel?.content_pic_url{
            imgV.sd_setImage(with: URL(string: imgUrl), completed: {
                (img, err, tp, url) -> Void in
                
                if let e:Error = err{
                    print("1 errcode = \(e.localizedDescription)");
                    //如果发现错误 加载默认图片
                    self.imgV.sd_setImage(with: URL(string: category_img_name), completed: {
                        (img, err1, tp, url) -> Void in
                        
                        if let e1:Error = err1{
                            print("2 errcode = \(e1.localizedDescription)");
                            //如果发现错误 加载本地的默认图片
                            self.imgV.image = UIImage(named: "channelDefault");
                        }
                    });
                }
                
                //                if let u:NSURL = url{
                //                    print("---\(u.absoluteString)");
                //                }
                
            });
            
        }else{
            //如果发现错误 加载默认图片
            self.imgV.sd_setImage(with: URL(string: category_img_name), completed: {
                (img, err1, tp, url) -> Void in
                
                if let e1:Error = err1{
                    print("2 errcode = \(e1.localizedDescription)");
                    //如果发现错误 加载本地的默认图片
                    self.imgV.image = UIImage(named: "channelDefault");
                }
            });
        }
        
        
        if let title = dataModel.subject{
            lblContentTitle.text = title;
        }
        if let channelName = dataModel.channel?.channel_name{
            lblChannelName.text = channelName;
        }
        
        var strTm:String = "";
        if let tm = dataModel.creation_date{
            strTm = CommonTools.compareTime(tm);
        }
        
        var nViewCount:Int = 0;
        if let n = dataModel.view_count{
            nViewCount = n;
        }
        
        lblViewInfo.text = strTm + " 浏览数 " + String(nViewCount);
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
