//
//  HYHomeOtherPageView.swift
//  AskDog
//
//  Created by Symond on 16/9/20.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import MJRefresh
import SDWebImage

protocol HYHomeOtherPageViewDelegate  :class{
    func hyHomeOtherPageView(pageView v:HYHomeOtherPageView,dataModel da:HYHomeListDataModel) -> Void;
    
}

class HYHomeOtherPageView: UIView , UITableViewDelegate, UITableViewDataSource {
    
    var parentVC:UIViewController!
    var tableView:HYBaseUITableView!
    var dataModel:HYHomeListResultDataModel?
    var categoryModel:HYCategoryTreeDataModel?
    weak var delegate:HYHomeOtherPageViewDelegate?
    
    var codeIndex = 0;
    var itemsCode: [String] = [String]();
    var itemsId: [String] = [String]();
    var itemsName: [String] = [String]();

    
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
        self.dealModel();
        self.loadData();
    }
    
    func dealModel() -> Void {
        itemsCode.removeAll();
        for obj in (categoryModel?.children)! {
            itemsCode.append(obj.code!);
        }
        
        itemsId.removeAll();
        for obj in (categoryModel?.children)! {
            itemsId.append(obj.id!);
        }
        
        itemsName.removeAll();
        for obj in (categoryModel?.children)! {
            itemsName.append(obj.name!);
        }
    }
    

    
    func setupView() -> Void {
        
        self.tableView = HYBaseUITableView(frame: CGRect.zero, style: UITableViewStyle.plain);
        tableView.translatesAutoresizingMaskIntoConstraints = false;
        self.tableView.delegate = self;
        self.tableView.dataSource = self;
        self.tableView.separatorStyle = .none;
        self.addSubview(self.tableView);
        
        if self.tableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            self.tableView.separatorInset = UIEdgeInsets.zero;
        }
        if self.tableView.responds(to: #selector(setter: UIView.layoutMargins)){
            self.tableView.layoutMargins = UIEdgeInsets.zero;
        }
        
        let cellID:String = "HYHomeListTableViewCell";
        let nib:UINib = UINib(nibName: "HYHomeListTableViewCell", bundle: nil);
        self.tableView.register(nib, forCellReuseIdentifier: cellID);
        
        //MJ
        header.setRefreshingTarget(self, refreshingAction: #selector(headerRefresh));
        self.tableView.header = header;
        footer.setRefreshingTarget(self, refreshingAction: #selector(footerRefresh));
        self.tableView.footer = footer;
        
        //约束
        var views:[String:AnyObject] = [String:AnyObject]();
        views["tableView"] = tableView;
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[tableView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[tableView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
    }
    
    
    //MARK: UITableViewDataSource
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
    
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return CGFloat( 44 * (itemsName.count/3 + 1));
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        let h: CGFloat = CGFloat( 44 * (itemsName.count/3 + 1));
        let headerV:UIView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.size.width, height: h));
        headerV.backgroundColor = UIColor.white;
       
        let classification_H: CGFloat = CGFloat( 30 * (itemsName.count/3 + 1));
        let classification: HYClassificationView = HYClassificationView(frame: CGRect(x: 0, y: 5, width: tableView.frame.size.width, height: classification_H),
                                                                        andItems: itemsName,
                                                                        index: codeIndex) {[unowned self] (ins) in
            self.codeIndex = ins;
            self.loadData();
        }

        headerV.addSubview(classification);
        
        return headerV;
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if let arr:[HYHomeListDataModel] = self.dataModel?.result{
            let n = arr.count;
            //            if(0 == n){
            //                self.tab2TableView.showNoDataStatusView(beShow: true);
            //                self.tab2TableView.setNoDataStatusViewTapEnable(canTap: true);
            //                self.tab2TableView.header.isHidden = true;
            //                self.tab2TableView.footer.isHidden = true;
            //            }else{
            //                self.tab2TableView.showNoDataStatusView(beShow: false);
            //                self.tab2TableView.setNoDataStatusViewTapEnable(canTap: false);
            //                self.tab2TableView.header.isHidden = false;
            //                self.tab2TableView.footer.isHidden = false;
            //            }
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
    
    //解决分割线不对齐问题
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        if cell.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            cell.separatorInset = UIEdgeInsets.zero;
        }
        if cell.responds(to: #selector(setter: UIView.layoutMargins)){
            cell.layoutMargins = UIEdgeInsets.zero;
        }
    }
    
    //MARK: UITableViewDelegate
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        self.tableView.delayCellClicked(clickedMethod: {
            () -> Void in
            
            let data:HYHomeListDataModel = (self.dataModel?.result![(indexPath as NSIndexPath).row])!;
            delegate?.hyHomeOtherPageView(pageView: self, dataModel: data);
            
        });
        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    //MARK: MJ
    
    func headerRefresh () -> Void {
        print("headerRefresh");
        
        self.loadData();
    }
    
    func footerRefresh () -> Void {
        print("footerRefresh");
        
        let nPage = self.nPageNumber + 1;
        
        let nSize:Int = Int(PAGE_SIZE)!;
        let strPage = String(nPage * nSize);
       
        let dic:[String: AnyObject]? = ["categoryCode":itemsCode[codeIndex] as AnyObject,
                                        "type":"experience_category" as AnyObject,
                                        "from":strPage as AnyObject,
                                        "size":PAGE_SIZE as AnyObject];

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
    
    
    //MARK: LoadData
    func loadData() -> Void {
        
        self.nPageNumber = 0;
        self.nPageNumber = 0;
        let strPage = String(self.nPageNumber);
        
        let dic:[String:AnyObject]? = ["categoryCode":itemsCode[codeIndex] as AnyObject,
                                       "type":"experience_category" as AnyObject,
                                       "from": strPage as AnyObject,
                                       "size": PAGE_SIZE as AnyObject];
        
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
    
    //MARK: 点击分类
    func tableHeaderAction(index: Int) -> Void {

        
    }
    
}
