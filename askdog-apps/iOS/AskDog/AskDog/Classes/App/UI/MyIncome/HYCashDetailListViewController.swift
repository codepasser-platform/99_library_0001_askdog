//
//  HYCashDetailListViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/15.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import MJRefresh

class HYCashDetailListViewController: HYBaseViewController,UITableViewDelegate,UITableViewDataSource {

    var dataArray:[HYCashDetailDataModel] = [HYCashDetailDataModel]();
    var header = MJRefreshNormalHeader()
    var footer = MJRefreshAutoNormalFooter()
    var pageNum = 0
    
    var nTotalIncome:Float? = 0;
    
    @IBOutlet weak var tableView: UITableView!
    override func viewDidLoad() {
        stringNavBarTitle = "提现记录";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        if tableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tableView.separatorInset = UIEdgeInsets.zero;
        }
        if tableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tableView.layoutMargins = UIEdgeInsets.zero;
        }
        
        let cellID1:String = "HYCashDetailTopTableViewCell";
        let nib1:UINib = UINib(nibName: "HYCashDetailTopTableViewCell", bundle: nil);
        tableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        let cellID2:String = "HYCashDetailTableViewCell";
        let nib2:UINib = UINib(nibName: "HYCashDetailTableViewCell", bundle: nil);
        tableView.register(nib2, forCellReuseIdentifier: cellID2);
        
        header.setRefreshingTarget(self, refreshingAction: #selector(headerRefresh))
        tableView.header = header
        footer.setRefreshingTarget(self, refreshingAction: #selector(footerRefresh))
        tableView.footer = footer
        
        self.loadData();
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
        
        let page = self.pageNum + 1
        let nSize:Int = Int(PAGE_SIZE)!;
        
        let d:String = CommonTools.nsDateToString(Date());
        
        let dic:[String: AnyObject]? = ["size":PAGE_SIZE as AnyObject,"page":String(page * nSize) as AnyObject,"from":"2016-01-01" as AnyObject,"to":d as AnyObject];
        
        AskDogGetUserAccountCashDetailDataRequest().startRequest(requestParmer:dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    
                    let dm: HYCashResultDetailDataModel = HYCashResultDetailDataModel(jsonDic: dic!)
                    
                    if let res = dm.result {
                        if res.count > 0 {
                            self.pageNum += 1
                            
                            let arr:NSArray = res as NSArray;
                            for item in arr{
                                self.dataArray.append(item as! HYCashDetailDataModel);
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

        let d:String = CommonTools.nsDateToString(Date());
        
        let dic:[String: AnyObject]? = ["size":PAGE_SIZE as AnyObject,"page":String(pageNum) as AnyObject,"from":"2016-01-01" as AnyObject,"to":d as AnyObject];
        
        AskDogGetUserAccountCashDetailDataRequest().startRequest(requestParmer:dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    self.dataArray.removeAll()
                    
                    let dm: HYCashResultDetailDataModel = HYCashResultDetailDataModel(jsonDic: dic!)
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
                        print("提现记录- 无数据")
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
        
        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        if(0 == (indexPath as NSIndexPath).row){
            return 120;
        }else{
            return 80;
        }
        
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
        
        return self.dataArray.count + 1;
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        var cellID:String! = "";
        if(0 == (indexPath as NSIndexPath).row){
            cellID = "HYCashDetailTopTableViewCell";
        }else{
            cellID = "HYCashDetailTableViewCell";
        }
        
        let cell:UITableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID)!;
        
        if(0 == (indexPath as NSIndexPath).row){
            let temp:HYCashDetailTopTableViewCell = cell as! HYCashDetailTopTableViewCell;
            if let n = self.nTotalIncome{
                temp.lblTotalMoneyNumber.text = "￥ \(n)";
            }
            
        }else{
            let temp:HYCashDetailTableViewCell = cell as! HYCashDetailTableViewCell;
            let d:HYCashDetailDataModel = self.dataArray[(indexPath as NSIndexPath).row - 1];
            temp.setCashData(d);
        }
        
        
        
        return cell;
    }
    


}
