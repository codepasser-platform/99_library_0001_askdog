//
//  HYDingyueChannelListViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/10.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import MJRefresh

class HYDingyueChannelListViewController: HYBaseViewController {

    
    @IBOutlet weak var tableView: HYBaseUITableView!
    var dataArray:[HYChannelListDataModel] = [HYChannelListDataModel]();
    let header = MJRefreshNormalHeader()
    let footer = MJRefreshAutoNormalFooter()
    var pageNum = 0
    
    override func viewDidLoad() {
        stringNavBarTitle = "订阅频道";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        let cellID1:String = "cell1";
        let nib1:UINib = UINib(nibName: "HYDingyueChannelListTableViewCell", bundle: nil);
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
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated);
        self.loadList()
    }
    
    func headerRefresh() {
        self.loadList()
        self.tableView.header.endRefreshing()
    }

    func footerRefresh() {
        
        let page = self.pageNum + 1
        let nSize:Int = Int(PAGE_SIZE)!;
        
        let dic:[String: AnyObject]? = ["size":PAGE_SIZE as AnyObject, "page":String(page * nSize) as AnyObject];
        AskDogGetMySubscribedChannelListDataRequest().startRequest(requestParmer: dic, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    
                    let dm: HYChannelListResultDataModel = HYChannelListResultDataModel(jsonDic: dic!)
                    
                    if let res = dm.result {
                        if res.count > 0 {
                            self.pageNum += 1
                            
                            let arr:NSArray = res as NSArray;
                            for item in arr{
                                self.dataArray.append(item as! HYChannelListDataModel);
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
    
    
    func loadList() -> Void {
        self.pageNum = 0
        
        let dic:[String: AnyObject]? = ["size":PAGE_SIZE as AnyObject, "page":String(pageNum) as AnyObject];
        AskDogGetMySubscribedChannelListDataRequest().startRequest(requestParmer: dic, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            
            if(true == beOk){
                if(nil != dic){
                    self.dataArray.removeAll()
                    
                    let dm: HYChannelListResultDataModel = HYChannelListResultDataModel(jsonDic: dic!)
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
                        print("订阅频道- 无数据")
                    }
                }
            }
        });
    }

    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    //MARK:UITableViewDelegate
    func tableView(_ tableView: UITableView, didSelectRowAtIndexPath indexPath: IndexPath) {
        
        self.tableView.delayCellClicked(clickedMethod: {
            () -> Void in
            
            let data:HYChannelListDataModel = self.dataArray[(indexPath as NSIndexPath).row];
            if let id:String = data.id{
                let strParmr:String = "/" + id;
                print("share id = \(strParmr)");
                
                AskDogGetChannelInfoDataRequest().startRequest(exUrl: strParmr, viewController:self,completionHandler: {
                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                    if(true == beOk){
                        
                        let data:HYChannelInfoDataModel = HYChannelInfoDataModel(jsonDic: dic!);
                        
                        let vc:HYChannelHomeViewController = HYChannelHomeViewController(nibName: "HYChannelHomeViewController", bundle: Bundle.main);
                        vc.channelListID = id;
                        vc.setChannelInfo(data);
                        self.navigationController?.pushViewController(vc, animated: true);
                        
                        
                        
                    }
                });
            }
            
        });
        

        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAtIndexPath indexPath: IndexPath) -> CGFloat {
        
        return 80;
        
    }
    
    //解决分割线不对齐问题
    func tableView(_ tableView: UITableView, willDisplayCell cell: UITableViewCell, forRowAtIndexPath indexPath: IndexPath) {
        if cell.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            cell.separatorInset = UIEdgeInsets.zero;
        }
        if cell.responds(to: #selector(setter: UIView.layoutMargins)){
            cell.layoutMargins = UIEdgeInsets.zero;
        }
    }
    
    func numberOfSectionsInTableView(_ tableView: UITableView) -> Int {
        return 1;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return dataArray.count;
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAtIndexPath indexPath: IndexPath) -> UITableViewCell {

        let cellID:String! = "cell1";
        
        let cell:HYDingyueChannelListTableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID) as! HYDingyueChannelListTableViewCell;
        
        let data:HYChannelListDataModel = self.dataArray[(indexPath as NSIndexPath).row];
        cell.setChannelData(data);
        
        return cell;
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
