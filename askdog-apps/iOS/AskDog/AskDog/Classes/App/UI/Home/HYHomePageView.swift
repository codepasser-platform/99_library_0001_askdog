//
//  HYHomePageView.swift
//  AskDog
//
//  Created by Symond on 16/9/20.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import MJRefresh
import SDWebImage

protocol HYHomePageViewDelegate  :class{
    func hyHomePageViewDidSearchBtnClicked(pageView v:HYHomePageView) -> Void;
    func hyHomePageView(pageView v:HYHomePageView,dataModel da:HYHomeListDataModel) -> Void;
    
}



//UITableViewDelegate,UITableViewDataSource
class HYHomePageView: UIView,UITableViewDelegate,UITableViewDataSource {
    
    var parentVC:UIViewController!
    var tableView:HYBaseUITableView!
    var dataModel:HYHomeListResultDataModel?
    weak var delegate:HYHomePageViewDelegate?
    
    var nPageNumber:Int = 0;
    
    
    let header = MJRefreshNormalHeader();
    let footer = MJRefreshAutoNormalFooter();
    
    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */
    
    override func layoutSubviews() {
        super.layoutSubviews();
        self.setupView();
        //FIXME:加载数据时机
        self.loadData2();
    }
    
    func setupView() -> Void {
        //self.translatesAutoresizingMaskIntoConstraints = false;

        tableView = HYBaseUITableView(frame: CGRect.zero, style: .plain);
        tableView.translatesAutoresizingMaskIntoConstraints = false;
        tableView.separatorStyle = .none;
        tableView.delegate = self;
        tableView.dataSource = self;
        self.addSubview(tableView);
        
        if tableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tableView.separatorInset = UIEdgeInsets.zero;
        }
        if tableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tableView.layoutMargins = UIEdgeInsets.zero;
        }
        
        let cellID1:String = "HYHomeListTableViewCell";
        let nib1:UINib = UINib(nibName: "HYHomeListTableViewCell", bundle: nil);
        tableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        
        //设置上下拉刷新
        header.setRefreshingTarget(self, refreshingAction: #selector(headerRefresh));
        tableView.header = header;
        footer.setRefreshingTarget(self, refreshingAction: #selector(footerRefresh))
        tableView.footer = footer;
        
        //约束
        var views:[String:AnyObject] = [String:AnyObject]();
        views["tableView"] = tableView;
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[tableView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[tableView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-20-[btn]-20-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-4-[btn(30)]", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[tableView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-4-[searchBkView(30)]-4-[tableView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        self.tableView.addNoDataStatusView(showImg: "errorloading",showTitle:"加载错误\n点击屏幕刷新",refreshCallBack: {
            [unowned self] () -> Void in
            
            self.loadData2();
            
            
        });
        
    }
    
    func loadData2() -> Void {
        
        self.nPageNumber = 0;
        let strPage = String(self.nPageNumber);
        
        
        let dic:[String: AnyObject]? = ["type":"experience_hot" as AnyObject,"from":strPage as AnyObject,"size":PAGE_SIZE as AnyObject];
        AskDogHotShareDataRequest().startRequest(requestParmer: dic, viewController:self.parentVC, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    // let nTotal:Int = dic!["total"] as! Int;
                    
                    
                    self.dataModel = HYHomeListResultDataModel(jsonDic: dic!);
                    self.tableView.reloadData();
                    if (true == self.dataModel?.last){
                        self.tableView.footer.endRefreshingWithNoMoreData();
                    }else{
                        self.tableView.footer.resetNoMoreData();
                    }
                }
            }
            self.tableView.header.endRefreshing();
        });
    }
    
    func headerRefresh() -> Void {
        print("header2Refresh");
        
        self.loadData2();
        
    }
    
    func footerRefresh() -> Void {
        print("footer2Refresh");
        
        let nPage = self.nPageNumber + 1;
        
        let nSize:Int = Int(PAGE_SIZE)!;
        let strPage = String(nPage * nSize);
        
        let dic:[String: AnyObject]? = ["type":"experience_hot" as AnyObject,"from":strPage as AnyObject,"size":PAGE_SIZE as AnyObject];
        AskDogHotShareDataRequest().startRequest(requestParmer: dic, viewController:self.parentVC, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    let dm:HYHomeListResultDataModel = HYHomeListResultDataModel(jsonDic: dic!);
                    self.dataModel?.last = dm.last;
                    self.dataModel?.total = dm.total;
                    
                    if let res = dm.result{
                        if res.count > 0{
                            self.dataModel?.result?.append(contentsOf: res);
                            self.nPageNumber += 1;
                            self.tableView.reloadData();
                        }
                    }
                    
                    if (true == self.dataModel?.last){
                        self.tableView.footer.endRefreshingWithNoMoreData();
                    }else{
                        self.tableView.footer.resetNoMoreData();
                    }
                    
                }
            }
            
            self.tableView.footer.endRefreshing();
        });
    }


    func btnSearchClicked(_ sender: UIButton) -> Void{
        print("HYHomePageView btnSearchClicked in");
        delegate?.hyHomePageViewDidSearchBtnClicked(pageView: self);
    }
    
    //MARK:UITableViewDelegate
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        self.tableView.delayCellClicked(clickedMethod: {
            () -> Void in
            
            let data:HYHomeListDataModel = (self.dataModel?.result![(indexPath as NSIndexPath).row])!;
            delegate?.hyHomePageView(pageView: self, dataModel: data);
            
        });
        

        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        if(true == IS_IPHONE6S_PLUS_DEV){
            return 310.0;
        }else if(true == IS_IPHONE66S_DEV){
            return 280.0;
        }else if(true == IS_IPHONE55S_DEV){
            return 260.0;
        }
        return 320.0;
    }

    
    //解决分割线不对齐问题
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        if cell.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            cell.separatorInset = UIEdgeInsets.zero;
        }
        if cell.responds(to: #selector(setter: UIView.layoutMargins)){
            cell.layoutMargins = UIEdgeInsets.zero;
        }
    }
    
    //MARK:UITableViewDataSource
    //    func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
    //        return true;
    //    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1;
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        let headerV:UIView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.size.width, height: 44));
        //headerV.backgroundColor = CommonTools.rgbColor(red: 243, green: 244, blue: 249);
        headerV.backgroundColor = UIColor.white;
        //headerV.translatesAutoresizingMaskIntoConstraints = false;
        
        //顶部搜索框
        let searchBkView:UIView = UIView(frame: CGRect(x: 5, y: 5, width: tableView.frame.size.width - 10, height: 44 - 10));
        //let searchBkView:UIView = UIView(frame: CGRect.zero);
        searchBkView.backgroundColor = UIColor(red: 242/255, green: 244/255, blue: 249/255, alpha: 1);
        //searchBkView.backgroundColor = UIColor.yellow;
        //searchBkView.translatesAutoresizingMaskIntoConstraints = false;
        searchBkView.layer.cornerRadius = 5;
        headerV.addSubview(searchBkView);
        
        let zoomImageV:UIImageView = UIImageView(frame: CGRect(x: tableView.frame.size.width / 2 - 18 - 40, y: (34 - 18) / 2, width: 18, height: 18));
        zoomImageV.image = UIImage(named: "wenhao");
        //zoomImageV.backgroundColor = UIColor.redColor();
        //zoomImageV.translatesAutoresizingMaskIntoConstraints = false;
        searchBkView.addSubview(zoomImageV);
        
        let searchPlaceHolderTitle:UILabel = UILabel(frame: CGRect(x: tableView.frame.size.width / 2 - 38, y: (34 - 20) / 2, width: 80, height:20));
        //searchPlaceHolderTitle.backgroundColor = UIColor.redColor();
        //searchPlaceHolderTitle.translatesAutoresizingMaskIntoConstraints = false;
        searchPlaceHolderTitle.text = "搜索经验";
        searchPlaceHolderTitle.textColor = UIColor(red: 193/255, green: 200/255, blue: 218/255, alpha: 1);
        searchBkView.addSubview(searchPlaceHolderTitle);
        
       //覆盖一个按钮
       let btn:UIButton = UIButton(type: UIButtonType.custom);
       btn.frame = searchBkView.bounds;
       btn.setTitle("", for: UIControlState());
       //btnNavBarBack.titleEdgeInsets = UIEdgeInsetsMake(0,0,0,80);
       btn.addTarget(self, action: #selector(btnSearchClicked), for: UIControlEvents.touchUpInside);
       btn.backgroundColor = UIColor.clear;
       //btn.translatesAutoresizingMaskIntoConstraints = false;
       searchBkView.addSubview(btn);
        
        
//        var views:[String:AnyObject] = [String:AnyObject]();
//        views["searchBkView"] = searchBkView;
//        headerV.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-20-[searchBkView]-20-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//        headerV.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-4-[searchBkView]-4-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        
        return headerV;
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {

        return 44;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if let arr:[HYHomeListDataModel] = self.dataModel?.result{
            let n = arr.count;
            if(0 == n){
                self.tableView.showNoDataStatusView(beShow: true);
                self.tableView.setNoDataStatusViewTapEnable(canTap: true);
                self.tableView.header.isHidden = true;
                self.tableView.footer.isHidden = true;
            }else{
                self.tableView.showNoDataStatusView(beShow: false);
                self.tableView.setNoDataStatusViewTapEnable(canTap: false);
                self.tableView.header.isHidden = false;
                self.tableView.footer.isHidden = false;
            }
            return n;
        }else{
            return 0;
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell:UITableViewCell!
        let cellID:String! = "HYHomeListTableViewCell";
        

        cell = tableView.dequeueReusableCell(withIdentifier: cellID);
        let temp:HYHomeListTableViewCell = cell as! HYHomeListTableViewCell;
        let data:HYHomeListDataModel = (self.dataModel?.result![(indexPath as NSIndexPath).row])!;
        temp.setData(data);
        
        
        
        
        
        
        return cell;
    }
    

}
