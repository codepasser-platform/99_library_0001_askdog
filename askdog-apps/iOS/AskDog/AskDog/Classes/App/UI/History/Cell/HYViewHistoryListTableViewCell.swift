//
//  HYViewHistoryListTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/11.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import SDWebImage

protocol HYViewHistoryListTableViewCellDelegate  :class{
    func hyViewHistoryListTableViewCellMenuBtnClicked(cell ce:HYViewHistoryListTableViewCell,index row:Int) -> Void ;
    
}

class HYViewHistoryListTableViewCell: UITableViewCell {

    @IBOutlet weak var timeLabel: UILabel!
    @IBOutlet weak var lblViewInfo: UILabel!
    @IBOutlet weak var lblInfo: UILabel!
    @IBOutlet weak var lblTitle: UILabel!
    @IBOutlet weak var imgView: UIImageView!
    @IBOutlet weak var bkView: UIView!
    var cellIndex:Int!
    
    weak var delegate:HYViewHistoryListTableViewCellDelegate?
    
    var dataModel:HYHistoryDataModel!
    
    func setChannelData(_ data:HYHistoryDataModel) -> Void {
        
        lblTitle.text = "";
        lblInfo.text = "";
        //imgView.image = UIImage(named: "channelDefault");
        dataModel = data;
        
        if let title:String = dataModel.subject{
            lblTitle.text = title;
        }
        
        var category_img_name:String = "";
        if let category:String = dataModel?.category?.code{
            //把code转小写  abstract specific
            let strUrl:String = "http://pic.askdog.cn/resources/category/thumbnail/abstract/\(category.lowercased()).png@119w_67h_1e_1c.png";
            category_img_name = strUrl;
        }
        
        if let imgUrl:String = dataModel?.thumbnail{
            imgView.sd_setImage(with: URL(string: imgUrl), completed: {
                (img, err, tp, url) -> Void in
                
                if let e:Error = err{
                    print("1 errcode = \(e.localizedDescription)");
                    //如果发现错误 加载默认图片
                    self.imgView.sd_setImage(with: URL(string: category_img_name), completed: {
                        (img, err1, tp, url) -> Void in
                        
                        if let e1:Error = err1{
                            print("2 errcode = \(e1.localizedDescription)");
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
            //如果发现错误 加载默认图片
            self.imgView.sd_setImage(with: URL(string: category_img_name), completed: {
                (img, err1, tp, url) -> Void in
                
                if let e1:Error = err1{
                    print("2 errcode = \(e1.localizedDescription)");
                    //如果发现错误 加载本地的默认图片
                    self.imgView.image = UIImage(named: "channelDefault");
                }
            });
        }
        
//        var category_img_name:String = "";
//        if let category_code = dataModel.category?.code{
//            if let name:String = CATEGORY_IMG_DIC[category_code]{
//                category_img_name = name;
//            }else{
//                category_img_name = "channelDefault";
//            }
//        }
 
        
        //imgView.image = UIImage(named: category_img_name);
        
        
//        if let url:String = dataModel.thumbnail{
//            imgView.sd_setImageWithURL(NSURL(string: url),placeholderImage: UIImage(named: category_img_name));
//        }
        var channelName:String = "";
//        var userName:String = "";
        
        if let channel:String = dataModel.channel?.name{
            channelName = channel;
        }
//        if let name:String =  dataModel.author?.name{
//            userName = name;
//        }
        
//        lblInfo.text = "\(userName) > \(channelName)";
        lblInfo.text = channelName;
        
//        var strTm:String = "";
//        if let time:TimeInterval = dataModel.creation_time{
//            strTm = CommonTools.compareTime(time);
//        }
//        
        var nViewCount:Int = 0;
        if let cnt:Int = dataModel.view_count{
            nViewCount = cnt;
        }
        
//        lblViewInfo.text = strTm + " - 浏览数 " + String(nViewCount);
        lblViewInfo.text = "浏览数 " + String(nViewCount);
        
        if let doration = dataModel?.video_duration {
            let s = Int(doration) / 60;
            let m = Int(doration) % 60;
            if m < 10 {
                timeLabel.text = String(s) + " : 0" + String(m);
                
            } else {
                timeLabel.text = String(s) + " : " + String(m);
                
            }
            
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
        delegate?.hyViewHistoryListTableViewCellMenuBtnClicked(cell: self, index: cellIndex);
    }
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    
}
