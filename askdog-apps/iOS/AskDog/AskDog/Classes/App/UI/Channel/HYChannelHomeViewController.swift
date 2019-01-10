//
//  HYChannelHomeViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/11.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import MJRefresh

class HYChannelHomeViewController: HYBaseViewController,HYChannelHomeTableViewCellDelegate,HYChannelHomeTopTableViewCellDelegate {

    @IBOutlet weak var tableView: HYBaseUITableView!
    var channelInfoDataModel:HYChannelInfoDataModel!
    var header = MJRefreshNormalHeader()
    var footer = MJRefreshAutoNormalFooter()
    var pageNum = 0

    var beShowDescription:Bool = false;
    var beCanDeleteChannel:Bool!
    var channelListID:String?
    /// 是否可以编辑频道信息  只可以编辑自己的
    var beCanEditChannelInfo:Bool = false;
    
    
    var dataArray:[HYChannelExperienceListDataModel] = [HYChannelExperienceListDataModel]();
    
    func setChannelInfo(_ data:HYChannelInfoDataModel){
        channelInfoDataModel = data;
        print("channelInfoDataModel saved");
        
        
    }
    
    init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?,canDelete delete:Bool = false) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
        beCanDeleteChannel = delete;
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        stringNavBarTitle = "频道主页";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let cellID1:String = "cell1";
        let nib1:UINib = UINib(nibName: "HYChannelHomeTopTableViewCell", bundle: nil);
        tableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        let cellID2:String = "cell2";
        let nib2:UINib = UINib(nibName: "HYChannelHomeSecondTableViewCell", bundle: nil);
        tableView.register(nib2, forCellReuseIdentifier: cellID2);
        
        let cellID3:String = "cell3";
        let nib3:UINib = UINib(nibName: "HYChannelHomeTableViewCell", bundle: nil);
        tableView.register(nib3, forCellReuseIdentifier: cellID3);
        
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
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated);
        self.loadData();
    }
    
    func headerRefresh() {
        self.loadData()
        self.tableView.header.endRefreshing()
    }
    
    func footerRefresh() {
        let page = self.pageNum + 1
        

        if let id:String = channelInfoDataModel.id{
            
            let nSize:Int = Int(PAGE_SIZE)!;
            
            let dic:[String: AnyObject]? = ["channelId":id as AnyObject,"size":PAGE_SIZE as AnyObject,"page":String(page * nSize) as AnyObject];
            
            AskDogGetChannelExperinceListDataRequest().startRequest(requestParmer: dic,  viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                
                if(true == beOk){
                    if(nil != dic){
                        
                        let dm: HYChannelExperienceListResultDataModel = HYChannelExperienceListResultDataModel(jsonDic: dic!)
                        
                        if let res = dm.result {
                            if res.count > 0 {
                                self.pageNum += 1
                                
                                let arr:NSArray = res as NSArray;
                                for item in arr{
                                    self.dataArray.append(item as! HYChannelExperienceListDataModel);
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
        }
        
        self.tableView.footer.endRefreshing()
    }
    
    
    func loadData() -> Void {
       
        pageNum = 0
        
        if let id:String = channelInfoDataModel.id{
            
            let nSize:Int = Int(PAGE_SIZE)!;
            
            let dic:[String: AnyObject]? = ["channelId":id as AnyObject,"size":PAGE_SIZE as AnyObject,"page":String(pageNum * nSize) as AnyObject];
            
            AskDogGetChannelExperinceListDataRequest().startRequest(requestParmer: dic,  viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    
                    if(nil != dic){
                        self.dataArray.removeAll()
                        let dm: HYChannelExperienceListResultDataModel = HYChannelExperienceListResultDataModel(jsonDic: dic!)
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
                            print("频道主页- 无数据")
                        }
                    }
                }
            });
        }
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
    func tableView(_ tableView: UITableView, didSelectRowAtIndexPath indexPath: IndexPath) {
        
        self.tableView.delayCellClicked(clickedMethod: {
            () -> Void in
            
            if(1 == (indexPath as NSIndexPath).row){
                self.beShowDescription = !self.beShowDescription;
                let indexs:[IndexPath] = [IndexPath(row: 1, section: 0)];
                tableView.beginUpdates();
                tableView.reloadRows(at: indexs, with: .fade);
                tableView.endUpdates();
            }else if (0 == (indexPath as NSIndexPath).row){
                
            }else{
                let item:HYChannelExperienceListDataModel = self.dataArray[(indexPath as NSIndexPath).row-2];
                if let id = item.id{
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
            }
            
        });
        

        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAtIndexPath indexPath: IndexPath) -> CGFloat {
        
        if(0 == (indexPath as NSIndexPath).row){
            return 97;
        }else if(1 == (indexPath as NSIndexPath).row){
            if(true == self.beShowDescription){
                
                let height:CGFloat = tableView.fd_heightForCell(withIdentifier: "cell2", cacheBy: indexPath, configuration: {
                    (obj:Any) -> Void in
                    
                    
                    let temp:HYChannelHomeSecondTableViewCell = obj as! HYChannelHomeSecondTableViewCell;
                    
                    temp.lblContent.text = "";
                    if let str:String = self.channelInfoDataModel.Description{
                        temp.lblContent.text = str;
                    }
                    
                    if(true == self.beShowDescription){
                        temp.lblContent.isHidden = false;
                        temp.imgV.isHidden = true;
                    }else{
                        temp.lblContent.isHidden = true;
                        temp.imgV.isHidden = false;
                    }
                    
                    
                   
                })
                
                return height + 10;
            }
            return 30;
        }else{
            return 257;
        }
        
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
        
        return self.dataArray.count + 2;
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAtIndexPath indexPath: IndexPath) -> UITableViewCell {
        
        var cellID:String! = "";
        
        if(0 == (indexPath as NSIndexPath).row){
            cellID = "cell1";
        }else if(1 == (indexPath as NSIndexPath).row){
            cellID = "cell2";
        }else{
            cellID = "cell3";
        }
        
        let cell:UITableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID)!;
        
        if(0 == (indexPath as NSIndexPath).row){
            let temp:HYChannelHomeTopTableViewCell = cell as! HYChannelHomeTopTableViewCell;
            temp.delegate = self;
            print("channelInfoDataModel = \(channelInfoDataModel)");
            temp.setChannelInfoData(channelInfoDataModel);
        }else if(1 == (indexPath as NSIndexPath).row){
            let temp:HYChannelHomeSecondTableViewCell = cell as! HYChannelHomeSecondTableViewCell;
            
            temp.lblContent.text = "";
            if let str:String = channelInfoDataModel.Description{
                temp.lblContent.text = str;
            }
            
            if(true == beShowDescription){
                temp.lblContent.isHidden = false;
                temp.imgV.isHidden = true;
            }else{
                temp.lblContent.isHidden = true;
                temp.imgV.isHidden = false;
            }
            
        }else{
            let temp:HYChannelHomeTableViewCell = cell as! HYChannelHomeTableViewCell;
            if(true == self.beCanDeleteChannel){
                temp.delegate = self;
                temp.btnMenu.isHidden = false;
            }else{
                temp.btnMenu.isHidden = true;
            }
            temp.cellIndex = (indexPath as NSIndexPath).row;
            
            let data:HYChannelExperienceListDataModel = self.dataArray[(indexPath as NSIndexPath).row - 2];
            temp.setExpDataModel(data);
        }
        
        
        return cell;
    }
    
    //MARK:HYChannelHomeTableViewCellDelegate
    func hyChannelHomeTableViewCellMenuBtnClicked(cell ce: HYChannelHomeTableViewCell, index row: Int) {
        let actionSheet:UIAlertController = UIAlertController(title: "提示", message: "", preferredStyle: UIAlertControllerStyle.actionSheet);
        
        let cameraAction:UIAlertAction = UIAlertAction(title: "删除经验", style: UIAlertActionStyle.destructive, handler: {
            (action:UIAlertAction) -> Void in
            
            let data:HYChannelExperienceListDataModel = self.dataArray[row-2];
            
            if let id:String = data.id{
                //取消
                
                let strExUrl:String = "/\(id)";
                
                print("strExUrl=\(strExUrl)");
                
                AskDogDeleteExperinceDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                    if(true == beOk){
                        print("AskDogDeleteExperinceDataRequest  ok");
                        self.loadData();
                        
                    }else{
                        
                    }
                });
            }
            
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
    
    //MARK:HYChannelHomeTopTableViewCellDelegate
    func hyChannelHomeTopTableViewCellChannelImgClicked(cell ce: HYChannelHomeTopTableViewCell, index row: Int) {
        
        if(false == self.beCanEditChannelInfo){
            return;
        }
        
        //编辑频道
        let vc:HYEditChannelViewController = HYEditChannelViewController(nibName: "HYEditChannelViewController", bundle: Bundle.main);
        vc.nEditType = 1;
        vc.setChannelInfoData(self.channelInfoDataModel);
        vc.callBackFun = {
            (beOK:Bool) -> Void in
            if(true == beOK){
                //重新加载频道信息
                
                if let id:String = self.channelListID{
                    let strParmr:String = "/" + id;
                    print("share id = \(strParmr)");
                    
                    AskDogGetChannelInfoDataRequest().startRequest(exUrl: strParmr, viewController:self,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            
                            let data:HYChannelInfoDataModel = HYChannelInfoDataModel(jsonDic: dic!);
                            self.channelInfoDataModel = data;
                            
                            //更新 0  1行
                            
                            self.tableView.reloadRows(
                                at: [IndexPath(row: 0, section: 0),
                                IndexPath(row: 1, section: 0)],
                                with: .fade);
                        }
                    });
                }
                
            }
        }
        self.navigationController?.pushViewController(vc, animated: true);
    }
    
    func hyChannelHomeTopTableViewCellSubscriptionBtnClicked(cell ce: HYChannelHomeTopTableViewCell, index row: Int) {
        
        if let id:String = channelInfoDataModel.id{
            if(ce.scribableState == SubScribableState.subScribable_Already){
                //取消
                let strExUrl:String = "/\(id)/subscription";
                
                print("strExUrl=\(strExUrl)");
                
                AskDogCancelSubscriptionDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                    if(true == beOk){
                        print("AskDogSubscriptionDataRequest  ok");
                        ce.setSubScribableStateAndCount(.subScribable_Can, beAdd: false);
                        
                        if let n = self.channelInfoDataModel.subscriber_count{
                            var cnt = n - 1;
                            cnt = cnt < 0 ? 0: cnt;
                            self.channelInfoDataModel.subscriber_count = cnt;
                        }
                    }else{
                        
                    }
                });
            }else if(ce.scribableState == SubScribableState.subScribable_Can){
                //订阅
                let strExUrl:String = "/\(id)/subscription";
                
                print("strExUrl=\(strExUrl)");
                
                AskDogSubscriptionDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                    if(true == beOk){
                        print("AskDogSubscriptionDataRequest  ok");
                        ce.setSubScribableStateAndCount(.subScribable_Already, beAdd: true);
                        
                        if let n = self.channelInfoDataModel.subscriber_count{
                            let cnt = n + 1;
                            self.channelInfoDataModel.subscriber_count = cnt;
                            
                        }
                    }else{
                        
                    }
                });
            }
        }
        
        
    }

}
