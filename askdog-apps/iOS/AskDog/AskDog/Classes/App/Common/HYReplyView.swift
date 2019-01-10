//
//  HYReplyView.swift
//  AskDog
//
//  Created by Symond on 16/8/15.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit


class HYReplyView: UIView ,UITextViewDelegate{

    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    
    var bkBlackView:UIView!
    var inputBkView:UIView!
    var textViewInput:UITextView!
    var btnClose:UIButton!
    
    var callBack:((String) -> Void)!
    
    override init(frame: CGRect) {
        super.init(frame: frame);
        
        print("\(self) alloc frame=\(frame)");
        
        //self.registerForKeyboardNotifications();
        self.backgroundColor = UIColor.clear;
        self.translatesAutoresizingMaskIntoConstraints = false;
        
        //添加半透明的背景view
        bkBlackView = UIView(frame: CGRect.zero);
        bkBlackView.alpha = 0.0;
        bkBlackView.backgroundColor = UIColor.black;
        bkBlackView.translatesAutoresizingMaskIntoConstraints = false;
        var views:[String:AnyObject] = [String:AnyObject]();
        self.addSubview(bkBlackView);
        
        views["bkBlackView"] = bkBlackView;
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[bkBlackView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[bkBlackView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        //加一个触摸关闭
        let tap:UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(tapToClose));
        self.addGestureRecognizer(tap);
        
        //输入背景VIEW
        inputBkView = UIView(frame: CGRect.zero);
        inputBkView.translatesAutoresizingMaskIntoConstraints = false;
        inputBkView.backgroundColor = UIColor.clear;
        self.addSubview(inputBkView);
        views["inputBkView"] = inputBkView;
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[inputBkView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[inputBkView(150)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        var viewsInput:[String:AnyObject] = [String:AnyObject]();
        
        let inputV:UIView = UIView(frame: CGRect.zero);
        inputV.backgroundColor = UIColor.white;
        inputV.translatesAutoresizingMaskIntoConstraints = false;
        inputBkView.addSubview(inputV);
        viewsInput["inputV"] = inputV;
        inputBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[inputV]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: viewsInput));
        inputBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-50-[inputV]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: viewsInput));
        
        //添加输入框
        textViewInput = UITextView(frame: CGRect.zero);
        textViewInput.translatesAutoresizingMaskIntoConstraints = false;
        inputBkView.addSubview(textViewInput);
        textViewInput.returnKeyType = .send;
        
        viewsInput["textViewInput"] = textViewInput;
        inputBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-4-[textViewInput]-4-|", options: NSLayoutFormatOptions(), metrics: nil, views: viewsInput));
        inputBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-50-[textViewInput]-4-|", options: NSLayoutFormatOptions(), metrics: nil, views: viewsInput));
        
        textViewInput.delegate = self;
        textViewInput.becomeFirstResponder();
        
        //关闭按钮
        btnClose = UIButton(type: UIButtonType.custom);
        btnClose.frame = CGRect.zero;
        btnClose.imageEdgeInsets = UIEdgeInsetsMake(25,25,5,5);
        btnClose.addTarget(self, action: #selector(btnCloseClicked), for: UIControlEvents.touchUpInside);
        //btnClose.backgroundColor = UIColor.yellowColor();
        btnClose.translatesAutoresizingMaskIntoConstraints = false;
        btnClose.setImage(UIImage(named: "delete"), for: UIControlState());
        inputBkView.addSubview(btnClose);

        viewsInput["btnClose"] = btnClose;
        //navBarView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:|-[btnNavBarBack]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        inputBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnClose(50)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: viewsInput));
        inputBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[btnClose(50)]", options: NSLayoutFormatOptions(), metrics: nil, views: viewsInput));
    }
    
    func tapToClose(_ tap: UIGestureRecognizer) -> Void {
        print("tapToClose in");
        self.disMiss();
    }
    
    func btnCloseClicked() -> Void {
        self.disMiss();
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func show(inView v:UIView,callBack call:@escaping ((String)->Void)){
        v.addSubview(self);
        
        callBack = call;
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["HYReplyView"] = self;
        
        v.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[HYReplyView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        v.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[HYReplyView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        UIView.animate(withDuration: 0.2, animations: { () -> Void in
            self.bkBlackView.alpha = 0.5;
            
        }, completion: { (finish) -> Void in
            
        }) 
        
    }
    
    func disMiss() -> Void {
        
        
        textViewInput.resignFirstResponder();
        
        UIView.animate(withDuration: 0.2, animations: { () -> Void in
            self.bkBlackView.alpha = 0.0;
            
        }, completion: { (finish) -> Void in
            self.removeFromSuperview();
        }) 
        
    }
    
    deinit{
        print("HYReplyView deinit in");
    }
    
    func registerForKeyboardNotifications() -> Void {
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardDidShowNotification), name: NSNotification.Name.UIKeyboardDidShow, object: nil);
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillHideNotification), name: NSNotification.Name.UIKeyboardWillHide, object: nil);
    }
    
    func keyboardDidShowNotification(_ notify:Notification){
        print("keyboardDidShowNotification in");
    
    }
    
    func keyboardWillHideNotification(_ notify:Notification){
        
    }
    
    //MARK: UITextViewDelegate
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool{
        if text == "\n"{
            print("按回车了");
            textView.resignFirstResponder();
            self.disMiss();
            self.callBack(textViewInput.text);
            return false;
        }
        return true;
    }

}
