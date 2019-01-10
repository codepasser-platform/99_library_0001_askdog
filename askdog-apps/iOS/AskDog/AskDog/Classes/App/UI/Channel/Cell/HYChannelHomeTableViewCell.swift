//
//  HYChannelHomeTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/11.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import SDWebImage

protocol HYChannelHomeTableViewCellDelegate  :class{
    func hyChannelHomeTableViewCellMenuBtnClicked(cell ce:HYChannelHomeTableViewCell,index row:Int) -> Void ;
    
}

class HYChannelHomeTableViewCell: UITableViewCell {
    
    
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var imgView: UIImageView!
    @IBOutlet weak var lblContent: UILabel!
    @IBOutlet weak var lblViewInfo: UILabel!
    @IBOutlet weak var btnMenu: UIButton!
    var cellIndex:Int!
    var dataModel:HYChannelExperienceListDataModel!
    
    weak var delegate:HYChannelHomeTableViewCellDelegate?
    
    
    func setExpDataModel(_ da:HYChannelExperienceListDataModel){
        dataModel = da;
        
        lblContent.text = "";
        lblViewInfo.text = "";
       // imgView.image = UIImage(named: "channelDefault");
        
        if let subject:String = dataModel.subject{
            lblContent.text = subject;
        }
        
//        var category_img_name:String = "";
//        if let category_code = dataModel.category?.code{
//            if let name:String = CATEGORY_IMG_DIC[category_code]{
//                category_img_name = name;
//            }else{
//                category_img_name = "channelDefault";
//            }
//            
//        }
        
        var category_img_name:String = "";
        if let category:String = dataModel?.category?.code{
            //把code转小写  abstract specific
            let strUrl:String = "http://pic.askdog.cn/resources/category/thumbnail/abstract/\(category.lowercased()).png@312w_176h_1e_1c.png";
            category_img_name = strUrl;
        }
        
        if let imgUrl:String = dataModel?.thumbnail{
            imgView.sd_setImage(with: URL(string: imgUrl), completed: {
                (img1, err1, cacheType1, url1) -> Void in
                
                if let e:Error = err1{
                    print("1 errcode = \(e.localizedDescription)");
                    //如果发现错误 加载默认图片
                    self.imgView.sd_setImage(with: URL(string: category_img_name), completed: {
                        (img2, err2, cacheType2, url2) -> Void in
                        
                        if let e2:Error = err2{
                            print("2 errcode = \(e2.localizedDescription)");
                            //如果发现错误 加载本地的默认图片
                            self.imgView.image = UIImage(named: "channelDefault");
                        }
                    });
                }
                
                //                if let u:NSURL = url{
                //                    print("---\(u.absoluteString)");
                //                }
                
            });
            
        }else{
            self.imgView.sd_setImage(with: URL(string: category_img_name), completed: {
                (img1, err1, cacheType1, url1) in
                if let e1:Error = err1{
                    print("2 errcode = \(e1.localizedDescription)");
                    //如果发现错误 加载本地的默认图片
                    self.imgView.image = UIImage(named: "channelDefault");
                }
            })
        }
        
        
//        imgView.image = UIImage(named: category_img_name);
//
//        if let url:String = dataModel.thumbnail{
//            imgView.sd_setImageWithURL(NSURL(string: url),placeholderImage: UIImage(named: category_img_name));
//        }
        
        var strTm:String = "";
        
        if let time:TimeInterval  = dataModel.creation_time{
            strTm = CommonTools.compareTime(time);
        }
        
        var nViewCount:Int = 0;
        if let cnt:Int = dataModel.view_count{
            nViewCount = cnt;
        }

        lblViewInfo.text = strTm + " 浏览数 " + String(nViewCount);
        
    }

    @IBAction func btnMenuClicked(_ sender: UIButton) {
        delegate?.hyChannelHomeTableViewCellMenuBtnClicked(cell: self, index: cellIndex);
    }
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        //加阴影
        bkView.layer.shadowColor = UIColor.black.cgColor;
        bkView.layer.shadowOffset = CGSize(width: 3, height: 3);
        bkView.layer.shadowOpacity = 0.4;
        bkView.layer.shadowRadius = 3;
        bkView.layer.cornerRadius = 3;
        bkView.clipsToBounds = true;
        
        self.layer.shadowColor = UIColor.black.cgColor;
        self.layer.shadowOffset = CGSize(width: 3, height: 2);
        self.layer.shadowOpacity = 0.3;
        self.layer.shadowRadius = 3;
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
