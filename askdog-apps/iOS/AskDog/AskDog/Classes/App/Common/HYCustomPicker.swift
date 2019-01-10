//
//  HYCustomPicker.swift
//  AskDog
//
//  Created by Symond on 16/8/9.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYCustomPickerDataSource:class {
    func numberOfComponentsInHYCustomPickerView(_ pickerView: HYCustomPicker) -> Int;
    func hyCustomPicker(_ pickerView: HYCustomPicker, numberOfRowsInComponent component: Int) -> Int;
    
    
}

protocol HYCustomPickerDelegate:class {
    func hyCustomPicker(_ pickerView: HYCustomPicker, titleForRow row: Int, forComponent component: Int) -> String?
    func hyCustomPicker(_ pickerView: HYCustomPicker, didSelectRow row: Int, inComponent component: Int);
}

class HYCustomPicker: UIView,UIPickerViewDelegate,UIPickerViewDataSource {
    
    var picker:UIPickerView!

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
    
    weak var delegate:HYCustomPickerDelegate?
    weak var dataSource:HYCustomPickerDataSource?
    
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
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[bottomView(260)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));

        
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
        

        //底部加uipikerview
        var views2:[String:AnyObject] = [String:AnyObject]();
        
        picker = UIPickerView(frame: CGRect.zero);
        picker.translatesAutoresizingMaskIntoConstraints = false;
        picker.delegate = self;
        //picker.backgroundColor = UIColor.whiteColor();
        
        bottomView.addSubview(picker);
        
        views2["picker"] = picker;
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[picker]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views2));
        //bottomView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("V:[picker(216)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views2));
        
        //取消按钮
        let btnCancel:UIButton = UIButton(type: .custom);
        btnCancel.translatesAutoresizingMaskIntoConstraints = false;
        btnCancel.backgroundColor = UIColor.clear;
        btnCancel.addTarget(self, action: #selector(btnCancelClicked), for: .touchUpInside);
        btnCancel.setTitle("取消", for: UIControlState());
        btnCancel.titleLabel?.font = UIFont.systemFont(ofSize: 16);
        btnCancel.setTitleColor(UIColor.darkGray, for: UIControlState());
        bottomView.addSubview(btnCancel);
        
        views2["btnCancel"] = btnCancel;
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[btnCancel(60)]", options: NSLayoutFormatOptions(), metrics: nil, views: views2));
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[btnCancel]-0-[picker(216)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views2));
        
        //确定按钮
        let btnOK:UIButton = UIButton(type: .custom);
        btnOK.translatesAutoresizingMaskIntoConstraints = false;
        btnOK.backgroundColor = UIColor.clear;
        btnOK.addTarget(self, action: #selector(btnOKClicked), for: .touchUpInside);
        btnOK.setTitle("确定", for: UIControlState());
        btnOK.titleLabel?.font = UIFont.systemFont(ofSize: 16);
        btnOK.setTitleColor(UIColor.darkGray, for: UIControlState());
        bottomView.addSubview(btnOK);
        
        views2["btnOK"] = btnOK;
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnOK(60)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views2));
        bottomView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[btnOK]-0-[picker(216)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views2));
        
        
    }
    
//    func selectedToValue(val:(colLeft:Int,colRight:Int)) -> Void {
//        picker.selectRow(colLeft, inComponent: 1, animated: true)
//        picker.reloadComponent(1)
//        picker.selectRow(0, inComponent: 2, animated: true)
//    }
    
    func btnOKClicked() -> Void {
        print("btnOKClicked in ");
        self.callBack(1 as AnyObject);
        self.disMiss();
    }
    
    func btnCancelClicked() -> Void {
        print("btnCancelClicked in ");
        self.callBack(0 as AnyObject);
        self.disMiss();
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func show(inView v:UIView,callBack call:@escaping ((AnyObject)->Void)){
        
        v.addSubview(self);
        
        callBack = call;
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["HYCustomPicker"] = self;
        
        v.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[HYCustomPicker]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        v.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[HYCustomPicker]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
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
    
    func reloadComponent(_ component: Int){
        picker.reloadComponent(component);
    }
    
    func reloadAllComponents(){
        picker.reloadAllComponents();
    }
    
    // selection. in this case, it means showing the appropriate row in the middle
    func selectRow(_ row: Int, inComponent component: Int, animated: Bool){
        picker.selectRow(row, inComponent: component, animated: animated);
    }
    
    //MARK:UIPickerViewDelegate
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {

        
        delegate?.hyCustomPicker(self, didSelectRow: row, inComponent: component);
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {

        return delegate?.hyCustomPicker(self, titleForRow: row, forComponent: component);
    }
    
    //MARK:UIPickerViewDataSource
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return (dataSource?.numberOfComponentsInHYCustomPickerView(self))!;
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return (dataSource?.hyCustomPicker(self, numberOfRowsInComponent: component))!;
    }
    
    deinit{
        print("HYShareView deinit in");
    }

}
