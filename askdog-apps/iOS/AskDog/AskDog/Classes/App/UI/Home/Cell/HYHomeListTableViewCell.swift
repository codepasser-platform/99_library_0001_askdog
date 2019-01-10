//
//  HYHomeListTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/7/27.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import SDWebImage

class HYHomeListTableViewCell: UITableViewCell {
    
    @IBOutlet weak var bkView: UIView!
    
    var dataModel:HYHomeListDataModel?
    var channelModel:HYHomeChannelModel?
    var searchModel:HYSearchActionModel?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        //加阴影
        //        bkView.layer.shadowColor = UIColor.black.cgColor;
        //        bkView.layer.shadowOffset = CGSize(width: 3, height: 3);
        //        bkView.layer.shadowOpacity = 0.4;
        //        bkView.layer.shadowRadius = 3;
        //        bkView.layer.cornerRadius = 2;
        
    }
    
    @IBOutlet weak var lblSubject: UILabel!
    @IBOutlet weak var imgV: UIImageView!
    @IBOutlet weak var lblChannelName: UILabel!
    //    @IBOutlet weak var lblType: UILabel!
    @IBOutlet weak var lblViewInfo: UILabel!
    @IBOutlet weak var infoLabel: UILabel!
    @IBOutlet weak var playImg: UIImageView!
    @IBOutlet weak var priceLabel: UILabel!
    @IBOutlet weak var price_W: NSLayoutConstraint!
    @IBOutlet weak var timeLabel: UILabel!
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
    func setData(_ da:HYHomeListDataModel){
        self.dataModel = da;
        
        if let subject:String = dataModel?.subject{
            lblSubject.text = subject;
        }
        
        
        var category_img_name:String = "";
        if let category:String = dataModel?.category?.category_code{
            //把code转小写  abstract specific
            let strUrl:String = "http://pic.askdog.cn/resources/category/thumbnail/abstract/\(category.lowercased()).png@312w_176h_1e_1c.png";
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
        
        //        if let channelName:String = dataModel?.channel?.channel_name{
        //            lblChannelName.text = channelName;
        //        }
        //        if let from:String = dataModel?._from{
        //            self.lblType.text = "来自于 " + from;
        //        }
        //        var strTm:String = "";
        //        if let createTm:TimeInterval = dataModel?.creation_date{
        //            strTm = CommonTools.compareTime(createTm);
        //        }
        //        var nViewCount:Int = 0;
        //        if let cnt:Int = dataModel?.view_count{
        //            nViewCount = cnt;
        //        }
        //        lblViewInfo.text = strTm + " 浏览数 " + String(nViewCount);
        //       var chanName = ""
        //        if let channelName:String = dataModel?.channel?.channel_name{
        //            lblChannelName.text = channelName;
        //        }
        var chName:String = "";
        if let channelName:String = dataModel?.channel?.channel_name{
            chName = channelName;
        }
        var strTm:String = "";
        if let createTm:TimeInterval = dataModel?.creation_date{
            strTm = CommonTools.compareTime(createTm);
        }
        var nViewCount:Int = 0;
        if let cnt:Int = dataModel?.view_count{
            nViewCount = cnt;
        }
        infoLabel.text = chName + " · " + strTm + " · 浏览数 " + String(nViewCount);
        
        priceLabel.layer.cornerRadius = price_W.constant / 2 ;
        priceLabel.clipsToBounds = true;
        if let price = dataModel?.price {
            priceLabel.isHidden = false;
            priceLabel.text = String(price / 100);
        } else {
            priceLabel.isHidden = true;
        }
        
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
    
    
    func setChannelData(_ da:HYHomeChannelModel){
        self.channelModel = da;
        
        if let subject:String = channelModel?.subject{
            let title: NSString = subject as NSString
            let attr = HYMutableAttributedString()
            lblSubject.attributedText = attr.markString(title);
        }
        
        
        var category_img_name:String = "";
        if let category:String = channelModel?.thumbnail{
            //把code转小写  abstract specific
           let strUrl:String = "http://pic.askdog.cn/resources/category/thumbnail/abstract/\(category.lowercased()).png@119w_64h_1e_1c.png";
            category_img_name = strUrl;
        }
        
        if let imgUrl:String = channelModel?.thumbnail{
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
        
        //        if let channelName:String = channelModel?.author?.name{
        //            lblChannelName.text = channelName;
        //        }
        ////        if let from:String = dataModel?._from{
        ////            self.lblType.text = "来自于 " + from;
        ////        }
        //        var strTm:String = "";
        //        if let createTm:TimeInterval = dataModel?.creation_date{
        //            strTm = CommonTools.compareTime(createTm);
        //        }
        //        var nViewCount:Int = 0;
        //        if let cnt:Int = dataModel?.view_count{
        //            nViewCount = cnt;
        //        }
        //        lblViewInfo.text = strTm + " 浏览数 " + String(nViewCount);
        
        var chName:String = "";
        if let channelName:String = channelModel?.author?.name{
            chName = channelName;
        }
        var strTm:String = "";
        if let createTm:TimeInterval = channelModel?.creation_time{
            strTm = CommonTools.compareTime(createTm);
        }
        var nViewCount:Int = 0;
        if let cnt:Int = dataModel?.view_count{
            nViewCount = cnt;
        }
        infoLabel.text = chName + " · " + strTm + " · 浏览数 " + String(nViewCount);
        
        priceLabel.layer.cornerRadius = price_W.constant / 2 ;
        priceLabel.clipsToBounds = true;
        if let price = dataModel?.price {
            priceLabel.isHidden = false;
            priceLabel.text = String(price / 100);
        } else {
            priceLabel.isHidden = true;
        }
        
        if let doration = channelModel?.content?.video?.duration {
            let s = Int(doration) / 60;
            let m = Int(doration) % 60;
            if m < 10 {
                timeLabel.text = String(s) + " : 0" + String(m);
                
            } else {
                timeLabel.text = String(s) + " : " + String(m);
                
            }
            
        }
    }
    
    //搜索更多列表
    func setSearchData(_ da:HYSearchActionModel){
        self.searchModel = da;
        
        if let subject:String = searchModel?.subject{
            lblSubject.text = subject;
        }
        
        
        var category_img_name:String = "";
        if let category:String = searchModel?.category?.category_code{
            //把code转小写  abstract specific
            let strUrl:String = "http://pic.askdog.cn/resources/category/thumbnail/abstract/\(category.lowercased()).png@312w_176h_1e_1c.png";
            category_img_name = strUrl;
        }
        
        if let imgUrl:String = searchModel?.content_pic_url{
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
        
        var chName:String = "";
        if let channelName:String = searchModel?.channel?.channel_name{
            chName = channelName;
        }
        var strTm:String = "";
        if let createTm:TimeInterval = searchModel?.creation_date{
            strTm = CommonTools.compareTime(createTm);
        }
        var nViewCount:Int = 0;
        if let cnt:Int = searchModel?.view_count{
            nViewCount = cnt;
        }
        infoLabel.text = chName + " · " + strTm + " · 浏览数 " + String(nViewCount);
        
        priceLabel.layer.cornerRadius = price_W.constant / 2 ;
        priceLabel.clipsToBounds = true;
        if let price = searchModel?.price {
            priceLabel.isHidden = false;
            priceLabel.text = String(price / 100);
        } else {
            priceLabel.isHidden = true;
        }
        
        if let doration = searchModel?.video_duration {
            let s = Int(doration) / 60;
            let m = Int(doration) % 60;
            if m < 10 {
                timeLabel.text = String(s) + " : 0" + String(m);
                
            } else {
                timeLabel.text = String(s) + " : " + String(m);
                
            }
            
        }
    }
    
    //    func setDataDic(dic:[String:AnyObject]) -> Void {
    //        lblSubject.text = "";
    //        lblChannelName.text = "";
    //        lblType.text = "";
    //        lblViewInfo.text = "";
    //
    //
    //        if let subjectObj:AnyObject = dic["subject"]{
    //            let strSubject:String = subjectObj as! String;
    //            lblSubject.text = strSubject;
    //        }
    //
    //        var category_img_name:String = "";
    //        if let categoryObj:AnyObject = dic["category"]{
    //            let categoryDic:[String:AnyObject]  = categoryObj as! [String:AnyObject];
    //
    //            if let category_codeObj:AnyObject = categoryDic["category_code"]{
    //                let category_code = (category_codeObj as? String)!;
    //                category_img_name = CATEGORY_IMG_DIC[category_code]!;
    //            }
    //
    //        }
    //
    //        imgV.image = UIImage(named: category_img_name);
    //
    //        if let picImgObj:AnyObject = dic["content_pic_url"]{
    //            let picImgString:String = picImgObj as! String;
    //
    //
    //
    //            //根据不同类别贴不同的图片
    //           // imgV.sd_setImageWithURL(NSURL(string: picImgString));
    //            imgV.sd_setImageWithURL(NSURL(string: picImgString), placeholderImage: UIImage(named: category_img_name));
    //        }
    //
    ////        if let authorObj:AnyObject = dic["author"]{
    ////            let authorDic:[String:AnyObject]  = authorObj as! [String:AnyObject];
    ////
    ////            if let userHeaderImgObj:AnyObject = authorDic["avatar_url"]{
    ////                let userHeaderImgString:String = userHeaderImgObj as! String;
    ////                headerImgV.sd_setImageWithURL(NSURL(string: userHeaderImgString));
    ////            }
    ////
    ////            if let usernameObj:AnyObject = authorDic["name"]{
    ////                lblUserName.text = usernameObj as? String;
    ////            }
    ////
    ////            if let vipObj:AnyObject = authorDic["vip"]{
    ////                let beVip:Bool = vipObj as! Bool;
    ////                vipImgView.hidden = !beVip;
    ////            }
    ////
    ////        }
    //
    //        if let channelObj:AnyObject = dic["channel"]{
    //            let channelDic:[String:AnyObject]  = channelObj as! [String:AnyObject];
    //
    //            if let channelNameObj:AnyObject = channelDic["channel_name"]{
    //                lblChannelName.text = channelNameObj as? String;
    //            }
    //
    //        }
    //
    //
    //        CommonTools.stringCheckNullObject(dic["_from"], callBack: {
    //            (str:String) -> Void in
    //            let strType:String = str;
    //            self.lblType.text = "来自于 " + strType;
    //        })
    //
    //        var strTm:String = "";
    //        if let createTmObj:AnyObject = dic["creation_date"]{
    //            let createTm:NSTimeInterval = createTmObj as! NSTimeInterval;
    //            strTm = CommonTools.compareTime(createTm);
    //
    //        }
    //        
    //        var nViewCount:Int = 0;
    //        if let viewCountObj:AnyObject = dic["view_count"]{
    //            nViewCount = viewCountObj as! Int;
    //        }
    //        
    //        lblViewInfo.text = strTm + " 浏览数 " + String(nViewCount);
    //        
    //    }
    
}
