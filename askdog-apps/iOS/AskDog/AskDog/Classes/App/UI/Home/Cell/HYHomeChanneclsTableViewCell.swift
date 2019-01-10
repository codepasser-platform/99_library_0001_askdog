//
//  HYHomeChanneclsTableViewCell.swift
//  AskDog
//
//  Created by Hooying_zhaody on 16/9/21.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit


protocol HYHomeChanneclsTableViewCellDelegate: class{
    func channeclsTableViewCellSeclectMore(cell:HYHomeChanneclsTableViewCell) -> Void;
    func channeclsTableViewCell(cell:HYHomeChanneclsTableViewCell, didSelectedAtInndex ins:Int) -> Void;

}

class HYHomeChanneclsTableViewCell: UITableViewCell {
    
    var bgScrollView:UIScrollView!;
    var mBtn:UIButton!;
    weak var delegate:HYHomeChanneclsTableViewCellDelegate?

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    override init(style: UITableViewCellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        
        self.setupView();
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    
    
    var dataModels:[HYChannelListDataModel]!
    
    func setChannelData(_ data:[HYChannelListDataModel]) -> Void {
        
        dataModels = data;
        var tagNum = 0;
        for model in dataModels {
            
            let headerV = HYChanneclsHeaderImage(frame: CGRect(x: tagNum*55, y: 2, width: 55, height: 55));
            self.bgScrollView.addSubview(headerV);
            headerV.btn.tag = tagNum;
            headerV.btn.addTarget(self, action: #selector(headerBtnAction), for: UIControlEvents.touchUpInside);
            
            if let channeImg: String = model.thumbnail {
                headerV.headerImage?.sd_setImage(with: URL(string: channeImg),placeholderImage:UIImage(named: "AvatarDefault"));
                
            }
            
            var category_img_name:String = "";
            if let category:String = model.thumbnail{
                //把code转小写  abstract specific
                let strUrl:String = "http://pic.askdog.cn/resources/category/thumbnail/abstract/\(category.lowercased()).png@119w_64h_1e_1c.png";
                category_img_name = strUrl;
            }
            
            if let imgUrl:String = model.thumbnail{
                headerV.headerImage.sd_setImage(with: URL(string: imgUrl), completed: {
                    (img, err, tp, url) -> Void in
                    
                    if let e:Error = err{
                        print("1 errcode = \(e.localizedDescription)");
                        //如果发现错误 加载默认图片
                        headerV.headerImage.sd_setImage(with: URL(string: category_img_name), completed: {
                            (img, err1, tp, url) -> Void in
                            
                            if let e1:Error = err1{
                                print("2 errcode = \(e1.localizedDescription)");
                                //如果发现错误 加载本地的默认图片
                                headerV.headerImage.image = UIImage(named: "channelDefault");
                            }
                        });
                    }
                    
                    //                if let u:NSURL = url{
                    //                    print("---\(u.absoluteString)");
                    //                }
                    
                });
                
            }else{
                //如果发现错误 加载默认图片
                headerV.headerImage.sd_setImage(with: URL(string: category_img_name), completed: {
                    (img, err1, tp, url) -> Void in
                    
                    if let e1:Error = err1{
                        print("2 errcode = \(e1.localizedDescription)");
                        //如果发现错误 加载本地的默认图片
                        headerV.headerImage.image = UIImage(named: "channelDefault");
                    }
                });
            }

            
            if let count = model.unread_exp_count{
                if count == 0 {
                    headerV.footerImage.image = UIImage(named: "")
                }
                else {
                    headerV.footerImage.image = UIImage(named: "reddot")
                }
            }
            tagNum += 1;
        }
        
        self.bgScrollView.contentSize = CGSize(width: data.count * 55, height: 60);
    }

    
    
    func setupView() -> Void {
        self.bgScrollView = UIScrollView(frame: CGRect.zero);
        self.bgScrollView.translatesAutoresizingMaskIntoConstraints = false;
        self.bgScrollView.backgroundColor = UIColor.clear;
        self.bgScrollView.isPagingEnabled = true;
        self.bgScrollView.bounces = false;
        self.bgScrollView.showsHorizontalScrollIndicator = false;
        self.contentView.addSubview(self.bgScrollView);
        
        
        self.mBtn = UIButton(type: UIButtonType.custom);
        self.mBtn.setBackgroundImage(UIImage(named: "arrow_L"), for: UIControlState.normal)
        self.mBtn.frame = CGRect.zero;
        self.mBtn.translatesAutoresizingMaskIntoConstraints = false;
        self.mBtn.addTarget(self, action: #selector(buttonAction), for: UIControlEvents.touchUpInside)
        self.contentView.addSubview(self.mBtn);

        var views:[String:AnyObject] = [String:AnyObject]();
        views["bgScrollView"] = bgScrollView;
         self.contentView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[bgScrollView]-40-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
         self.contentView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[bgScrollView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        views["mBtn"] = mBtn;
        self.contentView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[mBtn(24)]-10-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.contentView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-20-[mBtn]-20-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));

    }
    

    
    func buttonAction() -> Void {
        delegate?.channeclsTableViewCellSeclectMore(cell: self);

    }
    
    func headerBtnAction(btn: UIButton) -> Void {
        delegate?.channeclsTableViewCell(cell: self, didSelectedAtInndex: btn.tag)
    }
    
}
