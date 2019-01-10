//
//  HYTopScrollMenuView.swift
//  AskDog
//
//  Created by Symond on 16/9/20.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYTopScrollMenuViewDelegate  :class{
    func topScrollMenuView(menuView mv:HYTopScrollMenuView,didSelectedAtInndex ins:Int) -> Void;
    
}

protocol HYTopScrollMenuViewDataSource  :class{
    func numberOfViewsInTopScrollMenuView(menuView mv:HYTopScrollMenuView) -> Int ;
    func menuViewTitlesForTopScrollMenuView(menuView mv:HYTopScrollMenuView) -> [Any] ;
    func topScrollMenuView(menuView mv:HYTopScrollMenuView,index ins:Int) -> UIView;
    
}




class HYTopScrollMenuView: UIView,SGTopScrollMenuDelegate,UIScrollViewDelegate{
    
    weak var dataSource:HYTopScrollMenuViewDataSource?
    weak var delegate:HYTopScrollMenuViewDelegate?
    
    var bgScrollView:UIScrollView!
    var titles:[Any]!
    var menuView:SGTopScrollMenu!
    var views:[UIView] = [UIView]();
    
    
    override init(frame: CGRect) {
        super.init(frame: frame);
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder);
        //self.setupViews();
    }
    
    override func layoutSubviews() {
        super.layoutSubviews();
        
        print("\(self.frame.size.width)  \(self.frame.size.height)");
        //self.setupViews();
    }
    
    func setupViews() -> Void {
        
        self.titles = dataSource?.menuViewTitlesForTopScrollMenuView(menuView: self);
        
        menuView = SGTopScrollMenu(frame: CGRect(x: 0, y: 0, width: self.frame.size.width, height: 44));
        //self.menuView = SGTopScrollMenu(frame: CGRect.zero);
        //self.menuView.translatesAutoresizingMaskIntoConstraints = false;
        menuView.staticTitleArr = titles;
        menuView.backgroundColor = UIColor.clear;
        menuView.topScrollMenuDelegate = self;
        self.addSubview(menuView);
        
        let numberViews:Int = (dataSource?.numberOfViewsInTopScrollMenuView(menuView: self))!;
//        if(numberViews != self.titles.count){
//            print("TopScrollMenuView error:  views count != titles count !");
//            return;
//        }
        
        self.bgScrollView = UIScrollView(frame: CGRect.zero);
        self.bgScrollView.translatesAutoresizingMaskIntoConstraints = false;
        self.bgScrollView.backgroundColor = UIColor.clear;
        self.bgScrollView.isPagingEnabled = true;
        self.bgScrollView.bounces = false;
        self.bgScrollView.showsHorizontalScrollIndicator = false;
        self.bgScrollView.delegate = self;
        self.bgScrollView.contentSize = CGSize(width: self.frame.size.width * CGFloat(numberViews), height: 0);
        self.addSubview(self.bgScrollView);
        
        var views:[String:AnyObject] = [String:AnyObject]();
        //views["menuView"] = menuView;
        views["bgScrollView"] = bgScrollView;
        //self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[menuView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        //self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[menuView(44)]", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[bgScrollView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-44-[bgScrollView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        for index in 0 ..< numberViews
        {
            let v:UIView = (dataSource?.topScrollMenuView(menuView: self, index: index))!;
            v.frame = CGRect(x: CGFloat(index) * self.frame.size.width, y: 0, width: self.frame.size.width, height: self.frame.size.height);
            self.bgScrollView.addSubview(v);
            self.views.append(v);
            
        }
    }

    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */
    
    //MARK:SGTopScrollMenuDelegate
    
    func sgTopScrollMenu(_ topScrollMenu: SGTopScrollMenu!, didSelectTitleAt index: Int) {
        let offsetX:CGFloat = CGFloat(index) * self.frame.size.width;
        self.bgScrollView.contentOffset = CGPoint(x: offsetX, y: 0);
        delegate?.topScrollMenuView(menuView: self, didSelectedAtInndex: index);
    }
    
    //MARK:UIScrollViewDelegate
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        let nIndex:Int = Int(scrollView.contentOffset.x) / Int(scrollView.frame.size.width);
        let lbl:UILabel = self.menuView.allTitleLabel[nIndex] as! UILabel;
        
        if(self.titles.count > 4){
            self.menuView.scrollTitleLabelSelecteded(lbl);
            self.menuView.scrollTitleLabelSelectededCenter(lbl);
        }else{
            self.menuView.staticTitleLabelSelecteded(lbl);
        }
        
        delegate?.topScrollMenuView(menuView: self, didSelectedAtInndex: nIndex);
        
    }
    
    func reloadTopScrollView() -> Void {
        self.menuView.removeFromSuperview();
        self.bgScrollView.removeFromSuperview();
       // self.views.removeAll();
       // self.setNeedsLayout();
    }
    
//    func reloadTopScrollView() -> Void {
//        for v in self.views{
//            v.removeFromSuperview();
//        }
//        
//        let numberViews:Int = (dataSource?.numberOfViewsInTopScrollMenuView(menuView: self))!;
//        self.bgScrollView.contentSize = CGSize(width: self.frame.size.width * CGFloat(numberViews), height: 0);
//        
//        for index in 0 ..< numberViews
//        {
//            let v:UIView = (dataSource?.topScrollMenuView(menuView: self, index: index))!;
//            v.frame = CGRect(x: CGFloat(index) * self.frame.size.width, y: 0, width: self.frame.size.width, height: self.frame.size.height);
//            self.bgScrollView.addSubview(v);
//            self.views.append(v);
//            
//        }
//    }
   
    

}
