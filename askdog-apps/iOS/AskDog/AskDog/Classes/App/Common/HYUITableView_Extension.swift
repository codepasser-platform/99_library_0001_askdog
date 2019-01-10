//
//  HYUITableView_Extension.swift
//  AskDog
//
//  Created by Symond on 16/9/7.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import Foundation
import UIKit



extension UITableView{
    
    
    typealias CallBack = (Void)->Void;
    
    fileprivate struct CallBackClassKey {
        static var key = "CallBackClass"
    }
    
    fileprivate class CallBackClass{
        var callBackMethod:CallBack?
        var noDataView:UIView?
        var tap:UITapGestureRecognizer?
    }
    
    fileprivate var callBackFun:CallBackClass? {
        get{
            if let value = objc_getAssociatedObject(self, &CallBackClassKey.key){
                return value as? CallBackClass;
            }
            return nil;
        }
        
        set(newValue){
            objc_setAssociatedObject(self, &CallBackClassKey.key, newValue, .OBJC_ASSOCIATION_RETAIN_NONATOMIC);
        }
    }
    
    /**
     是否显示无数据视图
     
     - parameter be: bool
     */
    func showNoDataStatusView(beShow be:Bool) -> Void {
        //self.backgroundView?.hidden = !be;
        self.callBackFun?.noDataView?.isHidden = !be;
    }
    
    /**
     是否响应无数据视图的触摸事件
     
     - parameter be: bool
     */
    func setNoDataStatusViewTapEnable(canTap be:Bool) -> Void {
        self.callBackFun?.tap?.isEnabled = be;
    }
    
    
    func addNoDataStatusViewWithView(_ addView:UIView,refreshCallBack call:((Void)->Void)?) -> Void {
        //保存回调
        
        self.callBackFun = CallBackClass();
        self.callBackFun!.callBackMethod = call;
    }
    
    
    func addNoDataStatusView(showImg imgName:String,
                                     imgToTop t1:Float = 100.0,
                                              imgWidth imgW:Float = 50.0,
                                                       imgHeight imgH:Float = 50.0,
                                                                 showTitle ti:String?,
                                                                 refreshCallBack call:((Void)->Void)?) -> Void {
        

        //保存回调
        
        self.callBackFun = CallBackClass();
        self.callBackFun!.callBackMethod = call;
        
        let metrics:[String : AnyObject] = ["imgToTop":t1 as AnyObject,
                                            "imgWidth":imgW as AnyObject,
                                            "imgHeight":imgH as AnyObject];
        
        let noDataView:UIView = UIView(frame: self.bounds);
        //noDataView.translatesAutoresizingMaskIntoConstraints = false;
        noDataView.backgroundColor = UIColor.white;
        //noDataView.hidden = true;
        self.backgroundView = noDataView;
        self.callBackFun?.noDataView = noDataView;
        
        //中间加一个图标
        let middleImgView:UIImageView = UIImageView(image: UIImage(named: imgName));
        middleImgView.translatesAutoresizingMaskIntoConstraints = false;
        noDataView.addSubview(middleImgView);
        
        //下面中间加文字
//        let lblInfo:UILabel = UILabel(frame: CGRectZero);
//        lblInfo.text = ti;
//        lblInfo.translatesAutoresizingMaskIntoConstraints = false;
//        lblInfo.font = UIFont.systemFontOfSize(15);
//        lblInfo.textAlignment = .Center;
//        lblInfo.backgroundColor = UIColor.redColor();
//        lblInfo.textColor = CommonTools.rgbColor(red: 186, green: 194, blue: 214);
//        noDataView.addSubview(lblInfo);
        
        let textView:UITextView = UITextView(frame: CGRect.zero);
        textView.isEditable = false;
        textView.isSelectable = false;
        textView.text = ti;
        textView.translatesAutoresizingMaskIntoConstraints = false;
        textView.textAlignment = .center;
        textView.backgroundColor = UIColor.clear;
        textView.textColor = CommonTools.rgbColor(red: 186, green: 194, blue: 214);
        noDataView.addSubview(textView);
        
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["middleImgView"] = middleImgView;
        views["textView"] = textView;
        
        noDataView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[middleImgView(imgWidth)]", options:  NSLayoutFormatOptions(), metrics: metrics, views: views));
        //noDataView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("V:|-(imgToTop)-[middleImgView(imgHeight)]", options: NSLayoutFormatOptions(), metrics: metrics, views: views));
        noDataView.addConstraint(NSLayoutConstraint(item: middleImgView, attribute: .centerX, relatedBy: .equal, toItem: noDataView, attribute: .centerX, multiplier: 1, constant: 0));
        
        noDataView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-10-[textView]-10-|", options:  NSLayoutFormatOptions(), metrics: metrics, views: views));
        noDataView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-(imgToTop)-[middleImgView(imgHeight)]-10-[textView(100)]", options: NSLayoutFormatOptions(), metrics: metrics, views: views));
        
        //加一个触摸
        //加一个触摸关闭
        let tap:UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(tapToRefresh));
        self.addGestureRecognizer(tap);
        self.callBackFun?.tap = tap;
                
        //        var views:[String:AnyObject] = [String:AnyObject]();
        //        views["noDataView"] = noDataView;
        //        tabV.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:|-0-[noDataView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        //        tabV.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("V:|-0-[noDataView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
    }
    
    func tapToRefresh(_ tap: UIGestureRecognizer) -> Void {
        print("tapToRefresh in");
        if let call = self.callBackFun?.callBackMethod{
            call();
        }
    }
    
    
    fileprivate func reloadCallBack() -> Void{
        
    }
    
}
