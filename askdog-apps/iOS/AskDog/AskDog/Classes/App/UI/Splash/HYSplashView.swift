//
//  HYSplashView.swift
//  AskDog
//
//  Created by Symond on 16/9/5.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYSplashViewDelegate:class {
    func hySplashViewbtnClicked(_ v:HYSplashView) -> Void ;
}

class HYSplashView: UIView {
    
    var img:UIImageView?
    var btn:UIButton?
    
    weak var delegate:HYSplashViewDelegate?
    
    override init(frame: CGRect) {
        super.init(frame: frame);
        
        self.backgroundColor = UIColor.red;
        
        //self.translatesAutoresizingMaskIntoConstraints = false;
        
        var views:[String:AnyObject] = [String:AnyObject]();
        
        img = UIImageView(frame: self.bounds);
        img?.translatesAutoresizingMaskIntoConstraints = false;
        img?.isUserInteractionEnabled = true;
        self.addSubview(img!);
        
        views["img"] = img;
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[img]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[img]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        //下面中间加一个按钮
        
        btn = UIButton(type: .custom);
        btn?.frame = CGRect.zero;
        btn?.translatesAutoresizingMaskIntoConstraints = false;
        btn?.addTarget(self, action: #selector(btnClicked), for: .touchUpInside);
        btn?.setTitle("立即进入", for: UIControlState());
        btn?.layer.borderWidth = 1;
        btn?.layer.borderColor = UIColor.darkGray.cgColor;
        btn?.titleLabel?.font = UIFont.systemFont(ofSize: 12);
        btn?.setTitleColor(UIColor.darkGray, for: UIControlState());
        self.addSubview(btn!);
        
        views["btn"] = btn;
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btn(100)]", options:  NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[btn(35)]-80-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraint(NSLayoutConstraint(item: btn!, attribute: .centerX, relatedBy: .equal, toItem: self, attribute: .centerX, multiplier: 1, constant: 0));
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func btnClicked() -> Void {
        print("btnClicked in");
        delegate?.hySplashViewbtnClicked(self);
    }

    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    
    deinit{
        print("HYSplashView deinit");
    }
    
    func setData(_ dic:NSDictionary) -> Void {
        
        if let imgNameObj = dic["img"]{
            let imgName:String = imgNameObj as! String;
            img?.image = UIImage(named: imgName);
        }
        if let beObj = dic["hasBtn"]{
            let be:Bool = beObj as! Bool;
            btn?.isHidden = !be;
        }
    }

}
