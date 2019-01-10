//
//  HYChanneclsHeaderImage.swift
//  AskDog
//
//  Created by Hooying_zhaody on 16/9/22.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYChanneclsHeaderImage: UIView {

    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */
    
    var headerImage: UIImageView!
    var footerImage: UIImageView!
    var btn: UIButton!
    
    override init(frame: CGRect) {
        super.init(frame: frame);
        self.setupViews();
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func setupViews() -> Void {
        headerImage = UIImageView(frame: CGRect.zero);
        self.headerImage.translatesAutoresizingMaskIntoConstraints = false;
        self.addSubview(headerImage);
        footerImage = UIImageView(frame: CGRect.zero);
        self.footerImage.translatesAutoresizingMaskIntoConstraints = false;
        self.addSubview(footerImage);
        
        btn = UIButton(type: UIButtonType.custom);
        btn.backgroundColor = UIColor .clear;
        btn.translatesAutoresizingMaskIntoConstraints = false;
        self.addSubview(btn);
        
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["headerImage"] = headerImage;
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-6-[headerImage]-6-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-6-[headerImage]-6-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        views["footerImage"] = footerImage;
//        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-5-[footerImage]-5-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[footerImage(5)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//        bottomView.addConstraint(NSLayoutConstraint(item: lblCenter, attribute: .centerX, relatedBy: .equal, toItem: bottomView, attribute: .centerX, multiplier: 1, constant: 0));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[footerImage(5)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraint(NSLayoutConstraint(item: footerImage, attribute: .width, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1.0, constant: 5.0));

        self.addConstraint(NSLayoutConstraint(item: footerImage, attribute: .centerX, relatedBy: .equal, toItem: headerImage, attribute: .centerX, multiplier: 1, constant: 0));
        
        views["btn"] = btn;
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[btn]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[btn]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));

    }
    
    
    

}
