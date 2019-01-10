//
//  HYShareView.swift
//  AskDog
//
//  Created by Symond on 16/8/31.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYShareView: UIView {

    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    
    var bkView:UIView!
    var blackView:UIView!  //半透明的view
    var bottomView:UIView!  //底部放按钮的view
    
    var callBack:((AnyObject) -> Void)!
    
    override init(frame: CGRect) {
        super.init(frame: frame);
        
        self.backgroundColor = UIColor.clear;
        self.translatesAutoresizingMaskIntoConstraints = false;
        
        //添加背景view
        bkView = UIView(frame: CGRect.zero);
        bkView.alpha = 0.0;
        bkView.backgroundColor = UIColor.clear;
        bkView.translatesAutoresizingMaskIntoConstraints = false;
        var views:[String:AnyObject] = [String:AnyObject]();
        self.addSubview(bkView);
        
        views["bkView"] = bkView;
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[bkView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[bkView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        //添加半透明的view
        var views1:[String:AnyObject] = [String:AnyObject]();
        blackView = UIView(frame: CGRect.zero);
        blackView.alpha = 0.5;
        blackView.backgroundColor = UIColor.black;
        blackView.translatesAutoresizingMaskIntoConstraints = false;
        bkView.addSubview(blackView);
        
        views1["blackView"] = blackView;
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[blackView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[blackView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        
        bottomView = UIView(frame: CGRect.zero);
        bottomView.alpha = 1.0;
        bottomView.backgroundColor = UIColor.white;
        bottomView.translatesAutoresizingMaskIntoConstraints = false;
        bkView.addSubview(bottomView);
        
        views1["bottomView"] = bottomView;
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[bottomView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[bottomView(200)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        
        
//        //高斯模糊
//        let blurEffect = UIBlurEffect(style: .Light);
//        let blurView = UIVisualEffectView(effect: blurEffect);
//        blurView.translatesAutoresizingMaskIntoConstraints = false;
//        bkView.addSubview(blurView);
//        
//        var viewsBkView:[String:AnyObject] = [String:AnyObject]();
//        viewsBkView["blurView"] = blurView;
//        bkView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:|-0-[blurView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: viewsBkView));
//        bkView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("V:|-0-[blurView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: viewsBkView));
        
        
        //加一个触摸关闭
        let tap:UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(tapToClose));
        self.addGestureRecognizer(tap);
        
        
        var bottomViews:[String:AnyObject] = [String:AnyObject]();
        //取消按钮
        let btnCancel:UIButton = UIButton(type: .custom);
        btnCancel.translatesAutoresizingMaskIntoConstraints = false;
        btnCancel.backgroundColor = UIColor.clear;
        btnCancel.addTarget(self, action: #selector(btnCancelClicked), for: .touchUpInside);
        btnCancel.setTitle("取消", for: UIControlState());
        btnCancel.titleLabel?.font = UIFont.systemFont(ofSize: 16);
        btnCancel.setTitleColor(UIColor.darkGray, for: UIControlState());
        bottomView.addSubview(btnCancel);
        
        //横线
        let lblLine:UILabel = UILabel(frame: CGRect.zero);
        lblLine.backgroundColor = CommonTools.rgbColor(red: 240, green: 242, blue: 247);
        lblLine.translatesAutoresizingMaskIntoConstraints = false;
        bottomView.addSubview(lblLine);
        
        bottomViews["btnCancel"] = btnCancel;
        bottomViews["lblLine"] = lblLine;
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[btnCancel]-0-|", options:  NSLayoutFormatOptions(), metrics: nil, views: bottomViews));
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[lblLine]-0-|", options:  NSLayoutFormatOptions(), metrics: nil, views: bottomViews));
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[lblLine(1)]-1-[btnCancel(40)]-5-|", options: NSLayoutFormatOptions(), metrics: nil, views: bottomViews));
        
        
        //先在中间加一个lbl做中间点
        let lblCenter:UILabel = UILabel(frame: CGRect.zero);
        lblCenter.backgroundColor = UIColor.clear;
        lblCenter.translatesAutoresizingMaskIntoConstraints = false;
        bottomView.addSubview(lblCenter);
        
        bottomViews["lblCenter"] = lblCenter;
       // bottomView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:[lblCenter(1)]", options:  NSLayoutFormatOptions(), metrics: nil, views: bottomViews));
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-50-[lblCenter(70)]", options: NSLayoutFormatOptions(), metrics: nil, views: bottomViews));
        bottomView.addConstraint(NSLayoutConstraint(item: lblCenter, attribute: .centerX, relatedBy: .equal, toItem: bottomView, attribute: .centerX, multiplier: 1, constant: 0));
        
        //中间左边的微信按钮
        let btnWeixin:UIButton = UIButton(type: .custom);
        btnWeixin.translatesAutoresizingMaskIntoConstraints = false;
        btnWeixin.backgroundColor = UIColor.clear;
        btnWeixin.addTarget(self, action: #selector(btnWeixinClicked), for: .touchUpInside);
        btnWeixin.setTitle("朋友圈", for: UIControlState());
        btnWeixin.setImage(UIImage(named: "weixinpengyouquan"), for: UIControlState());
        btnWeixin.titleLabel?.font = UIFont.systemFont(ofSize: 14);
        btnWeixin.setTitleColor(UIColor.darkGray, for: UIControlState());
        btnWeixin.titleEdgeInsets = UIEdgeInsetsMake(30, -170, 0, 0);
        btnWeixin.imageEdgeInsets = UIEdgeInsetsMake(0, 15, 30, 15);
        bottomView.addSubview(btnWeixin);
        
        bottomViews["btnWeixin"] = btnWeixin;
        //bottomView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:[btnWeixin(70)]-10-[lblCenter(1)]", options:  NSLayoutFormatOptions(), metrics: nil, views: bottomViews));
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-50-[btnWeixin(70)]", options: NSLayoutFormatOptions(), metrics: nil, views: bottomViews));
        //bottomView.addConstraint(NSLayoutConstraint(item: btnWeixin, attribute: .CenterX, relatedBy: .Equal, toItem: bottomView, attribute: .CenterX, multiplier: 1, constant: 0));

        //左侧qq
        let btnQQ:UIButton = UIButton(type: .custom);
        btnQQ.translatesAutoresizingMaskIntoConstraints = false;
        btnQQ.backgroundColor = UIColor.clear;
        btnQQ.addTarget(self, action: #selector(btnQQClicked), for: .touchUpInside);
        btnQQ.setTitle("QQ", for: UIControlState());
        btnQQ.setImage(UIImage(named: "QQshare"), for: UIControlState());
        btnQQ.titleLabel?.font = UIFont.systemFont(ofSize: 14);
        btnQQ.setTitleColor(UIColor.darkGray, for: UIControlState());
        btnQQ.titleEdgeInsets = UIEdgeInsetsMake(30, -170, 0, 0);
        btnQQ.imageEdgeInsets = UIEdgeInsetsMake(0, 15, 30, 15);
        bottomView.addSubview(btnQQ);
        
        bottomViews["btnQQ"] = btnQQ;
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnQQ(70)]-10-[btnWeixin(70)]-5-[lblCenter(1)]", options:  NSLayoutFormatOptions(), metrics: nil, views: bottomViews));
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-50-[btnQQ(70)]", options: NSLayoutFormatOptions(), metrics: nil, views: bottomViews));
        
        //中间右边的微信好友
        let btnWeixinFriend:UIButton = UIButton(type: .custom);
        btnWeixinFriend.translatesAutoresizingMaskIntoConstraints = false;
        btnWeixinFriend.backgroundColor = UIColor.clear;
        btnWeixinFriend.addTarget(self, action: #selector(btnWeixinFriendClicked), for: .touchUpInside);
        btnWeixinFriend.setTitle("好友", for: UIControlState());
        btnWeixinFriend.setImage(UIImage(named: "weixinshare"), for: UIControlState());
        btnWeixinFriend.titleLabel?.font = UIFont.systemFont(ofSize: 14);
        btnWeixinFriend.setTitleColor(UIColor.darkGray, for: UIControlState());
        btnWeixinFriend.titleEdgeInsets = UIEdgeInsetsMake(30, -170, 0, 0);
        btnWeixinFriend.imageEdgeInsets = UIEdgeInsetsMake(0, 15, 30, 15);
        bottomView.addSubview(btnWeixinFriend);
        
        bottomViews["btnWeixinFriend"] = btnWeixinFriend;
        //bottomView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:[btnWeixin(70)]-10-[lblCenter(1)]", options:  NSLayoutFormatOptions(), metrics: nil, views: bottomViews));
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-50-[btnWeixinFriend(70)]", options: NSLayoutFormatOptions(), metrics: nil, views: bottomViews));

        //右侧微博
        let btnWeibo:UIButton = UIButton(type: .custom);
        btnWeibo.translatesAutoresizingMaskIntoConstraints = false;
        btnWeibo.backgroundColor = UIColor.clear;
        btnWeibo.addTarget(self, action: #selector(btnWeiboClicked), for: .touchUpInside);
        btnWeibo.setTitle("微博", for: UIControlState());
        btnWeibo.setImage(UIImage(named: "weiboshare"), for: UIControlState());
        btnWeibo.titleLabel?.font = UIFont.systemFont(ofSize: 14);
        btnWeibo.setTitleColor(UIColor.darkGray, for: UIControlState());
        btnWeibo.titleEdgeInsets = UIEdgeInsetsMake(30, -170, 0, 0);
        btnWeibo.imageEdgeInsets = UIEdgeInsetsMake(0, 15, 30, 15);
        bottomView.addSubview(btnWeibo);
        
        bottomViews["btnWeibo"] = btnWeibo;
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[lblCenter(1)]-5-[btnWeixinFriend(70)]-10-[btnWeibo(70)]", options:  NSLayoutFormatOptions(), metrics: nil, views: bottomViews));
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-50-[btnWeibo(70)]", options: NSLayoutFormatOptions(), metrics: nil, views: bottomViews));
        
        

        
        
    }
    
    func btnWeixinFriendClicked() -> Void {
        print("btnWeixinClicked in ");
        self.callBack(2 as AnyObject);
        self.disMiss();
    }
    
    func btnWeixinClicked() -> Void {
        print("btnWeixinClicked in ");
        
        self.callBack(1 as AnyObject);
        self.disMiss();
    }
    
    func btnWeiboClicked() -> Void {
        print("btnWeiboClicked in ");
        self.callBack(3 as AnyObject);
        self.disMiss();
    }
    
    func btnQQClicked() -> Void {
        print("btnQQClicked in ");
        self.callBack(0 as AnyObject);
        self.disMiss();
    }
    
    func btnCancelClicked() -> Void {
        print("btnCancelClicked in ");
        self.disMiss();
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func show(inView v:UIView,callBack call:@escaping ((AnyObject)->Void)){
        
        v.addSubview(self);
        
        callBack = call;
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["HYShareView"] = self;
        
        v.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[HYShareView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        v.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[HYShareView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        UIView.animate(withDuration: 0.2, animations: { () -> Void in
            self.bkView.alpha = 1.0;
            
        }, completion: { (finish) -> Void in
            
        }) 
        
    }
    
    func tapToClose(_ tap: UIGestureRecognizer) -> Void {
        print("tapToClose in");
        self.disMiss();
    }
    
    func disMiss() -> Void {
        
        UIView.animate(withDuration: 0.2, animations: { () -> Void in
            self.bkView.alpha = 0.0;
            
        }, completion: { (finish) -> Void in
            self.removeFromSuperview();
        }) 
        
    }
    
    deinit{
        print("HYShareView deinit in");
    }


}
