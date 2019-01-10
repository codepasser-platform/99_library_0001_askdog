//
//  HYExperiencePopView.swift
//  AskDog
//
//  Created by Symond on 16/8/17.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYExperiencePopView: HYPopView {

    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    override init(frame: CGRect) {
        super.init(frame: frame);
        //加上两个按钮
        //先在中间加一个lbl做中间点
        let lblCenter:UILabel = UILabel(frame: CGRect.zero);
        lblCenter.backgroundColor = UIColor.clear;
        lblCenter.translatesAutoresizingMaskIntoConstraints = false;
        bkView.addSubview(lblCenter);
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["lblCenter"] = lblCenter;
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[lblCenter(1)]", options:  NSLayoutFormatOptions(), metrics: nil, views: views));
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[lblCenter(80)]-200-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        bkView.addConstraint(NSLayoutConstraint(item: lblCenter, attribute: .centerX, relatedBy: .equal, toItem: bkView, attribute: .centerX, multiplier: 1, constant: 0));

        let btnLeft:UIButton = UIButton(type: .custom);
        btnLeft.translatesAutoresizingMaskIntoConstraints = false;
        btnLeft.backgroundColor = UIColor.clear;
        btnLeft.addTarget(self, action: #selector(btnLeftClicked), for: .touchUpInside);
        btnLeft.imageEdgeInsets = UIEdgeInsetsMake(19, 27, 44, 23);
        btnLeft.setImage(UIImage(named: "videoshare"), for: UIControlState());
        btnLeft.setTitle("视频分享", for: UIControlState());
        btnLeft.titleLabel?.font = UIFont.systemFont(ofSize: 15);
        btnLeft.titleEdgeInsets = UIEdgeInsetsMake(29, -132, 0, -16);
        btnLeft.setTitleColor(UIColor.darkGray, for: UIControlState());
        bkView.addSubview(btnLeft);
        
        views["btnLeft"] = btnLeft;
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnLeft(80)]-20-[lblCenter]", options:  NSLayoutFormatOptions(), metrics: nil, views: views));
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[btnLeft(80)]-150-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        let btnRight:UIButton = UIButton(type: .custom);
        btnRight.translatesAutoresizingMaskIntoConstraints = false;
        btnRight.backgroundColor = UIColor.clear;
        btnRight.addTarget(self, action: #selector(btnRightClicked), for: .touchUpInside);
        btnRight.imageEdgeInsets = UIEdgeInsetsMake(12, 28, 44, 27);
        btnRight.setImage(UIImage(named: "wordshare"), for: UIControlState());
        btnRight.setTitle("图文分享", for: UIControlState());
        btnRight.titleLabel?.font = UIFont.systemFont(ofSize: 15);
        btnRight.titleEdgeInsets = UIEdgeInsetsMake(29, -130, 0, -10);
        btnRight.setTitleColor(UIColor.darkGray, for: UIControlState());
        bkView.addSubview(btnRight);
        
        views["btnRight"] = btnRight;
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[lblCenter]-20-[btnRight(80)]", options:  NSLayoutFormatOptions(), metrics: nil, views: views));
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[btnRight(80)]-150-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        //取消按钮
        let btnCancel:UIButton = UIButton(type: .custom);
        btnCancel.translatesAutoresizingMaskIntoConstraints = false;
        btnCancel.backgroundColor = UIColor.clear;
        btnCancel.addTarget(self, action: #selector(btnCancelClicked), for: .touchUpInside);
        btnCancel.imageEdgeInsets = UIEdgeInsetsMake(12, 9, 13, 56);
        btnCancel.setImage(UIImage(named: "close1"), for: UIControlState());
        btnCancel.setTitle("取消", for: UIControlState());
        btnCancel.titleLabel?.font = UIFont.systemFont(ofSize: 16);
        btnCancel.titleEdgeInsets = UIEdgeInsetsMake(0, -37, 0, 0);
        btnCancel.setTitleColor(UIColor.black, for: UIControlState());
        bkView.addSubview(btnCancel);
        
        views["btnCancel"] = btnCancel;
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnCancel(80)]", options:  NSLayoutFormatOptions(), metrics: nil, views: views));
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[btnCancel(40)]-50-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        bkView.addConstraint(NSLayoutConstraint(item: btnCancel, attribute: .centerX, relatedBy: .equal, toItem: bkView, attribute: .centerX, multiplier: 1, constant: 0));
        
        
        
    }
    
    func btnCancelClicked(_ sender:UIButton) -> Void {
        print("btnLeftClicked in");
        self.disMiss();
    }
    
    func btnLeftClicked(_ sender:UIButton) -> Void {
        print("btnLeftClicked in");
        self.callBack(true as AnyObject);
        self.disMiss();
    }
    
    func btnRightClicked(_ sender:UIButton) -> Void {
        print("btnRightClicked in");
        self.callBack(false as AnyObject);
        self.disMiss();
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
//    override func show(inView v: UIView, callBack call: ((String) -> Void)){
//        
//    }
//    

}
