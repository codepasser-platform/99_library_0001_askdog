//
//  HYViewHistoryViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/10.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import MJRefresh

class HYViewHistoryViewController: HYBaseViewController,HYViewHistoryListTableViewCellDelegate,UITableViewDelegate,UITableViewDataSource {

    @IBOutlet weak var tableView: HYBaseUITableView!
    var dataArray:[HYHistoryDataModel] = [HYHistoryDataModel]();
    var header = MJRefreshNormalHeader()
    var footer = MJRefreshAutoNormalFooter()
    var pageNum = 0
    
    override func viewDidLoad() {
        stringNavBarTitle = "历史记录";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let btnDeleteAll:UIButton = UIButton(type: UIButtonType.custom);
        btnDeleteAll.frame = CGRect.zero;
        btnDeleteAll.setTitle("全部删除", for: UIControlState());
        btnDeleteAll.setTitleColor(UIColor.darkGray, for: UIControlState());
        btnDeleteAll.setTitleColor(UIColor.lightGray, for: .highlighted);
        btnDeleteAll.titleLabel?.font = UIFont.systemFont(ofSize: 13);
        btnDeleteAll.titleEdgeInsets = UIEdgeInsetsMake(3, 0, -3, 0);
        btnDeleteAll.addTarget(self, action: #selector(btnDeleteAllClicked), for: UIControlEvents.touchUpInside);
        btnDeleteAll.backgroundColor = UIColor.clear;
        btnDeleteAll.translatesAutoresizingMaskIntoConstraints = false;
        navBarView.addSubview(btnDeleteAll);
        
        btnDeleteAll.isHidden = true;
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["btnDeleteAll"] = btnDeleteAll;
        
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnDeleteAll(70)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-20-[btnDeleteAll]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        let cellID1:String = "cell1";
        let nib1:UINib = UINib(nibName: "HYViewHistoryListTableViewCell", bundle: nil);
        tableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        if tableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tableView.separatorInset = UIEdgeInsets.zero;
        }
        if tableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tableView.layoutMargins = UIEdgeInsets.zero;
        }
        
        header.setRefreshingTarget(self, refreshingAction: #selector(headerRefresh))
        tableView.header = header
        footer.setRefreshingTarget(self, refreshingAction: #selector(footerRefresh))
        tableView.footer = footer
        
        self.loadData();
        
    }
    
    func btnDeleteAllClicked(_ sender: UIButton) -> Void{
        print("btnDeleteAllClicked");
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func headerRefresh() {
        self.loadData()
        self.tableView.header.endRefreshing()
    }
    
    func footerRefresh() {
        
        let page = self.pageNum + 1;
        let nSize:Int = Int(PAGE_SIZE)!;

        let dic:[String: AnyObject]? = ["size":PAGE_SIZE as AnyObject,"page":String(page * nSize) as AnyObject];
        AskDogGetViewHistoryDataRequest().startRequest(requestParmer: dic,  viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
           
            if(true == beOk){
                if(nil != dic){
                    
                    let dm: HYHistoryResultDataModel = HYHistoryResultDataModel(jsonDic: dic!)
                    
                    if let res = dm.result {
                        if res.count > 0 {
                            self.pageNum += 1
                            
                            let arr:NSArray = res as NSArray;
                            for item in arr{
                                self.dataArray.append(item as! HYHistoryDataModel);
                            }
                            
                            self.tableView.reloadData()
                            
                            if true == dm.last {
                                self.tableView.footer.endRefreshingWithNoMoreData()
                            } else {
                                self.tableView.footer.resetNoMoreData()
                            }
                        }
                    }
                }
            }
        });
        
        self.tableView.footer.endRefreshing()
    }

    
    func loadData() -> Void {
        
        pageNum = 0
        
        let dic:[String: AnyObject]? = ["size":PAGE_SIZE as AnyObject,"page":String(pageNum) as AnyObject];
        AskDogGetViewHistoryDataRequest().startRequest(requestParmer: dic,  viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    self.dataArray.removeAll()
                    
                    let dm: HYHistoryResultDataModel = HYHistoryResultDataModel(jsonDic: dic!)
                    if let res = dm.result {
                        self.dataArray = res
                        
                        self.tableView.reloadData()
                        
                        if true == dm.last {
                            self.tableView.footer.endRefreshingWithNoMoreData()
                        } else {
                            self.tableView.footer.resetNoMoreData()
                        }
                        
                    }
                    
                    if self.dataArray.count == 0 {
                        print("历史记录- 无数据")
                    }
                }
            }
        });
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    //MARK:UITableViewDelegate
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        self.tableView.delayCellClicked(clickedMethod: {
            () -> Void in
            
            let data:HYHistoryDataModel = self.dataArray[(indexPath as NSIndexPath).row];
            
            if let id = data.id{
                let strParmr:String = "/" + id;
                print("share id = \(strParmr)");
                AskDogGetExperienceDetailDataRequest().startRequest(exUrl: strParmr, viewController:self,completionHandler: {
                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                    if(true == beOk){
                        
                        //let data:HYPinglunDataModel = self.makePinglunDataModel(dic!);
                        
                        let data:HYExperienceDetailDataModel = HYExperienceDetailDataModel(jsonDic: dic!);
                        
                        let vc:HYContenDetailListViewController = HYContenDetailListViewController(nibName: "HYContenDetailListViewController", bundle: Bundle.main,data: data);
                        self.navigationController?.pushViewController(vc, animated: true);
                    }
                });
            }
            
        });
        

        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        return 90;
        
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
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return dataArray.count;
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cellID:String! = "cell1";
        
        let cell:HYViewHistoryListTableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID) as! HYViewHistoryListTableViewCell;
        
        let data:HYHistoryDataModel = self.dataArray[(indexPath as NSIndexPath).row];
        cell.delegate = self;
        cell.cellIndex = (indexPath as NSIndexPath).row;
        
        cell.setChannelData(data);
        
        return cell;
    }
    
    
    //MARK:HYViewHistoryListTableViewCellDelegate
    func hyViewHistoryListTableViewCellMenuBtnClicked(cell ce: HYViewHistoryListTableViewCell, index row: Int) {
        let actionSheet:UIAlertController = UIAlertController(title: "提示", message: "", preferredStyle: UIAlertControllerStyle.actionSheet);
        
        let cameraAction:UIAlertAction = UIAlertAction(title: "删除记录", style: UIAlertActionStyle.destructive, handler: {
            (action:UIAlertAction) -> Void in
            
//            let data:HYHistoryDataModel = self.dataArray[row];
//            
//            //取消
//            
//            let strExUrl:String = "/\(data.id!)";
//            
//            print("strExUrl=\(strExUrl)");
//            
//            AskDogDeleteChannelListDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
//                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
//                if(true == beOk){
//                    print("AskDogDeleteChannelListDataRequest  ok");
//                    self.loadData();
//                    
//                }else{
//                    
//                }
//            });
            
        })
        
        actionSheet.addAction(cameraAction);
        
        let cancelAction:UIAlertAction = UIAlertAction(title: "取消", style: UIAlertActionStyle.cancel, handler: {
            (action:UIAlertAction) -> Void in
            print("cancelAction in");
            
            self.dismiss(animated: true, completion: nil);
            
        })
        
        actionSheet.addAction(cancelAction);
        
        
        
        self.present(actionSheet, animated: true, completion: {
            () -> Void in
            print("ok");
        })
    }

}
