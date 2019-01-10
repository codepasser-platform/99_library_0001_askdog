//
//  HYPopView.swift
//  AskDog
//
//  Created by Symond on 16/8/17.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYPopView: UIView {

    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    
    var bkView:UIView!
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
        
        //高斯模糊
        let blurEffect = UIBlurEffect(style: .light);
        let blurView = UIVisualEffectView(effect: blurEffect);
        blurView.translatesAutoresizingMaskIntoConstraints = false;
        bkView.addSubview(blurView);
        
        var viewsBkView:[String:AnyObject] = [String:AnyObject]();
        viewsBkView["blurView"] = blurView;
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[blurView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: viewsBkView));
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[blurView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: viewsBkView));
        
        
        //加一个触摸关闭
        let tap:UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(tapToClose));
        self.addGestureRecognizer(tap);
        
    }
    
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func show(inView v:UIView,callBack call:@escaping ((AnyObject)->Void)){

        v.addSubview(self);
        
        callBack = call;
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["HYPopView"] = self;
        
        v.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[HYPopView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        v.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[HYPopView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
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
        print("HYReplyView deinit in");
    }

}
