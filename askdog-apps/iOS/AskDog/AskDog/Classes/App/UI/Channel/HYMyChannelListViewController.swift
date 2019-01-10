//
//  HYMyChannelListViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/10.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYMyChannelListViewController: HYBaseViewController,UITableViewDelegate,UITableViewDataSource,HYMyChannelListTableViewCellDelegate {

    @IBOutlet weak var tableView: HYBaseUITableView!
    var dataArray:[HYChannelListDataModel] = [HYChannelListDataModel]();
    
    override func viewDidLoad() {
        stringNavBarTitle = "我的频道";
        super.viewDidLoad()
        
        let cellID1:String = "cell1";
        let nib1:UINib = UINib(nibName: "HYMyChannelListTableViewCell", bundle: nil);
        tableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        let cellID2:String = "cell2";
        let nib2:UINib = UINib(nibName: "HYMyChannelListAddTableViewCell", bundle: nil);
        tableView.register(nib2, forCellReuseIdentifier: cellID2);
        
        
        if tableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tableView.separatorInset = UIEdgeInsets.zero;
        }
        if tableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tableView.layoutMargins = UIEdgeInsets.zero;
        }

        // Do any additional setup after loading the view.
        self.tableView.addNoDataStatusView(showImg: "subscribe",imgToTop:160, imgWidth:20,imgHeight:20,showTitle: "当前没有创建频道", refreshCallBack: {
            [unowned self] () -> Void in
            self.loadData();
        });
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated);
        self.loadData();
    }
    
    
    func loadData() -> Void {
        //FIXME:分页未做
        let dic:[String: AnyObject]? = ["userId":(Global.sharedInstance.userInfo?.id)! as AnyObject,"size":"100" as AnyObject, "page":"0" as AnyObject];
        AskDogGetUserOwnedChannelListDataRequest().startRequest(requestParmer: dic,  viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    self.dataArray.removeAll()
                    if let resultObj = dic!["result"]{
                        let arr:NSArray = resultObj as! NSArray;
                        for item in arr{
                            let data:HYChannelListDataModel = HYChannelListDataModel(jsonDic: item as! NSDictionary);
                            self.dataArray.append(data);
                        }
                    }
                    self.tableView.reloadData();
                }
            }
        });
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
            
            if(self.dataArray.count == (indexPath as NSIndexPath).row){
                //增加频道
                let vc:HYEditChannelViewController = HYEditChannelViewController(nibName: "HYEditChannelViewController", bundle: Bundle.main);
                vc.nEditType = 0;
                vc.callBackFun = {
                    (beOK:Bool) -> Void in
                    if(true == beOK){
                        self.loadData();
                    }
                }
                self.navigationController?.pushViewController(vc, animated: true);
            }else{
                
                let data:HYChannelListDataModel = self.dataArray[(indexPath as NSIndexPath).row];
                if let id:String = data.id{
                    let strParmr:String = "/" + id;
                    print("share id = \(strParmr)");
                    
                    AskDogGetChannelInfoDataRequest().startRequest(exUrl: strParmr, viewController:self,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            
                            let data:HYChannelInfoDataModel = HYChannelInfoDataModel(jsonDic: dic!);
                            
                            let vc:HYChannelHomeViewController = HYChannelHomeViewController(nibName: "HYChannelHomeViewController", bundle: Bundle.main,canDelete:true);
                            vc.beCanEditChannelInfo = true;
                            vc.channelListID = id;
                            vc.setChannelInfo(data);
                            self.navigationController?.pushViewController(vc, animated: true);
                            
                        }
                    });
                }
                
            }
            
        });
        

        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {

        return 80;

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

        let n = dataArray.count;
        if (0 == n){
            self.tableView.showNoDataStatusView(beShow: true);
            self.tableView.isScrollEnabled = false;
            self.tableView.setNoDataStatusViewTapEnable(canTap: false);
        }else{
            self.tableView.showNoDataStatusView(beShow: false);
            self.tableView.isScrollEnabled = true;
            self.tableView.setNoDataStatusViewTapEnable(canTap: false);
        }
        
        return n + 1;
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        var cell:UITableViewCell!
        var cellID:String! = "";
        
        if(dataArray.count == (indexPath as NSIndexPath).row){
            cellID = "cell2";
            cell = tableView.dequeueReusableCell(withIdentifier: cellID);
            
        }else{
            cellID = "cell1";
            cell = tableView.dequeueReusableCell(withIdentifier: cellID);
            
            let temp:HYMyChannelListTableViewCell = cell as! HYMyChannelListTableViewCell;
            temp.index = (indexPath as NSIndexPath).row;
            temp.delegate = self;
            let data:HYChannelListDataModel = self.dataArray[(indexPath as NSIndexPath).row];
            temp.setChannelData(data);
            
        }
        
        return cell;
    }
    
    //MARK:HYMyChannelListTableViewCellDelegate
    func hyMyChannelListTableViewCellMenuBtnClicked(cell ce: HYMyChannelListTableViewCell, index row: Int) {
        

        
        let actionSheet:UIAlertController = UIAlertController(title: "提示", message: "", preferredStyle: UIAlertControllerStyle.actionSheet);
        
        let cameraAction:UIAlertAction = UIAlertAction(title: "删除频道", style: UIAlertActionStyle.destructive, handler: {
            (action:UIAlertAction) -> Void in
            
            let data:HYChannelListDataModel = self.dataArray[row];
            
            //取消
            
            let strExUrl:String = "/\(data.id!)";
            
            print("strExUrl=\(strExUrl)");
            
            AskDogDeleteChannelListDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    print("AskDogDeleteChannelListDataRequest  ok");
                    self.loadData();
                    
                }else{
                    
                }
            });
            
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
