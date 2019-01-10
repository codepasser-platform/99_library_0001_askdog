//
//  HYContenDetailListViewController.swift
//  AskDog
//
//  Created by Symond on 16/7/29.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import UITableView_FDTemplateLayoutCell

import MJRefresh

typealias reFreshExperienceDetailFun =  (Void) -> Void;

class HYContenDetailListViewController: HYBaseViewController,UITableViewDelegate,UITableViewDataSource,HYContentListHeaderTableViewCellDelegate,
    HYPingLunReplyTableViewCellDelegate,HYPingLunSubReplyTableViewCellDelegate,HYContenListTopTableViewCellDelegate,HYContentListTopVideoTableViewCellDelegate,
HYInputPingLunTableViewCellDelegate{
    
    
    @IBOutlet weak var tableView: HYBaseUITableView!
    

    var isFullScreenModel = false;
    var isPlaying:Bool! = false;
    var isShowXiangguanFenxiang:Bool = false;
    var isShowXiangguanPinglun:Bool = false;
    var comment_count:UInt = 0;
    var comments:NSArray = NSArray();
    var dataModel:HYExperienceDetailDataModel?
    var appDelegate:AppDelegate!
    var isVideo:Bool = false;
    var contentCellHeight:CGFloat = 100;
    var isUpWebView:Bool = false;
    var isPopToRoot:Bool = false;
    var isMySelfExperience:Bool = false;
    
    //如果是发布完视频进入到详情页，则禁用滑动返回
    var isPushFromSharedVideoView = false;
    
    let header1 = MJRefreshNormalHeader();
    let footer1 = MJRefreshAutoNormalFooter();
    
    var nTab1PageNumber:Int = 0;
    
    var videoCell:HYContentListTopVideoTableViewCell!
    
    //评论列表
    var commentsDataModel:HYPinglunResultDataModel?
    
    var dataArray:[HYExperienceListDataModel] = [HYExperienceListDataModel]();
    
    var checkExperienceDetailStatus:reFreshExperienceDetailFun = {
        () -> Void in
    }
    
    //    var checkVideoPayStatus:getVideoPayStatusFun = {
    //        () -> Void in
    //
    //        //取视频的支付状态
    //        if let id = self.dataModel?.id{
    //
    //            let strExUrl:String = "/\(id)";
    //
    //            AskDogGetExperiencePayStatusRequest().startRequest(exUrl:strExUrl,viewController:self,completionHandler: {
    //                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
    //                if(true == beOk){
    //                    if(nil != dic){
    //                        //"FREE",   // FREE, NOT_PAY,  PREPAY, PAID
    //                        let data:HYPayStatusDataModel = HYPayStatusDataModel(jsonDic: dic!);
    //
    //                        let arry:[String] = ["FREE","PAID"];
    //                        if let status = data.pay_status{
    //                            if arry.contains(status){
    //                                //说明不要收费了
    //                                self.dataModel?.isPayed = true;
    //
    //                            }else{
    //                                self.dataModel?.isPayed = false;
    //                            }
    //                        }
    //                        self.videoCell.setPinglunDataModel(self.dataModel!);
    //                    }
    //                }
    //            });
    //        }
    //
    //    }
    
    
    @IBOutlet weak var landPlayerBkView: UIView!
    //    func setDetailDataModel(da:HYPinglunDataModel) -> Void {
    //        dataModel = da;
    //    }
    
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?, data da: AnyObject?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil, data: da);
        
        dataModel = da as? HYExperienceDetailDataModel;
        
        
        //取返回的数据  来确认现在显示的是视频还是图文  如果视频 可宣传屏幕
        
        if let type:String = dataModel?.content?.type{
            if("VIDEO" == type){
                isVideo = true;
                
                
                //支持横屏
                appDelegate = UIApplication.shared.delegate as! AppDelegate
                appDelegate.blockRotation = true;
                
                
                checkExperienceDetailStatus = {
                    [unowned self] () -> Void in
                    
                    //重新取经验详情
                    if let idObj:AnyObject = self.dataModel!.id as AnyObject?{
                        let id:String = idObj as! String;
                        //let dic:[String: AnyObject]? = ["experienceId":id];
                        let strParmr:String = "/" + id;
                        print("experience id = \(strParmr)");
                        
                        AskDogGetExperienceDetailDataRequest().startRequest(exUrl: strParmr, viewController:self,completionHandler: {
                            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                            if(true == beOk){
                                let data:HYExperienceDetailDataModel = HYExperienceDetailDataModel(jsonDic: dic!);
                                self.dataModel = data;
                                //重置页面装态
                                self.videoCell.setPinglunDataModel(self.dataModel!);
                                //判断一下是不是自己的视频 如果是 不用收费  如果不是 收费
                               var myId:String = "";
                               if let id1:String = Global.sharedInstance.userInfo?.id{
                                   myId = id1;
                               }
                               if let obj:HYAuthorDataModel = self.dataModel?.author{
                                   if let id:String = obj.id{
                                       if(id == myId){
                                           self.isMySelfExperience = true;
                                       }
                                   }
                               }
                               if(false == self.isMySelfExperience){
                                   //取视频的支付状态
                                   if let id = self.dataModel?.id{
                               
                                       let strExUrl:String = "/\(id)";
                               
                                       AskDogGetExperiencePayStatusRequest().startRequest(exUrl:strExUrl,viewController:self,completionHandler: {
                                           (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                                           if(true == beOk){
                                               if(nil != dic){
                                                   //"FREE",   // FREE, NOT_PAY,  PREPAY, PAID
                                                   let data:HYPayStatusDataModel = HYPayStatusDataModel(jsonDic: dic!);
                               
                                                   let arry:[String] = ["FREE","PAID"];
                                                   if let status = data.pay_status{
                                                       if arry.contains(status){
                                                           //说明不要收费了
                                                           self.dataModel?.isPayed = true;
                               
                                                       }else{
                                                           self.dataModel?.isPayed = false;
                                                       }
                                                   }
                                                   self.videoCell.setPinglunDataModel(self.dataModel!);
                                                   
                                               }
                                           }
                                       });
                                   }
                               }
                            }
                        });
                    }
                    
                    
//                    if(true == self.isMySelfExperience){
//                        //说明不要收费了
//                        self.dataModel?.isPayed = true;
//                        self.videoCell.setPinglunDataModel(self.dataModel!);
//                    }else{
//                        //判断视频是不是自己的
//                        var myId:String = "";
//                        if let id1:String = Global.sharedInstance.userInfo?.id{
//                            myId = id1;
//                        }
//                        if let obj:HYAuthorDataModel = self.dataModel?.author{
//                            if let id:String = obj.id{
//                                if(id == myId){
//                                    self.isMySelfExperience = true;
//                                }
//                            }
//                        }
//                        
//                        if(false == self.isMySelfExperience){
//                            //取视频的支付状态
//                            if let id = self.dataModel?.id{
//                                
//                                let strExUrl:String = "/\(id)";
//                                
//                                AskDogGetExperiencePayStatusRequest().startRequest(exUrl:strExUrl,viewController:self,completionHandler: {
//                                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
//                                    if(true == beOk){
//                                        if(nil != dic){
//                                            //"FREE",   // FREE, NOT_PAY,  PREPAY, PAID
//                                            let data:HYPayStatusDataModel = HYPayStatusDataModel(jsonDic: dic!);
//                                            
//                                            let arry:[String] = ["FREE","PAID"];
//                                            if let status = data.pay_status{
//                                                if arry.contains(status){
//                                                    //说明不要收费了
//                                                    self.dataModel?.isPayed = true;
//                                                    
//                                                }else{
//                                                    self.dataModel?.isPayed = false;
//                                                }
//                                            }
//                                            self.videoCell.setPinglunDataModel(self.dataModel!);
//                                            
//                                        }
//                                    }
//                                });
//                            }
//                        }
//                        
//                    }
                    
                }
                
            }else{
                isVideo = false;
                
                //支持横屏
                appDelegate = UIApplication.shared.delegate as! AppDelegate
                appDelegate.blockRotation = false;
                
                if let id = dataModel!.id{
                    
                    let url:String = "http://\(REQUEST_TEXT_BASE_URL)/#/view/\(id)";
                    print("markdown url = \(url)");
                    Global.sharedInstance.textContentUrl = url;
                }
            }
        }
        
        
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func reloadExperienceDetail() -> Void {
        print("reloadExperienceDetail in");
        
        if let idObj:AnyObject = dataModel!.id as AnyObject?{
            let id:String = idObj as! String;
            //let dic:[String: AnyObject]? = ["experienceId":id];
            let strParmr:String = "/" + id;
            print("experience id = \(strParmr)");
            
            AskDogGetExperienceDetailDataRequest().startRequest(exUrl: strParmr, viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    let data:HYExperienceDetailDataModel = HYExperienceDetailDataModel(jsonDic: dic!);
                    self.dataModel = data;
                }
            });
        }
    }
    
    func loadData() -> Void {
        
        self.nTab1PageNumber = 0;
        
        if let id = dataModel?.id{
            
            let nSize:Int = Int(PAGE_SIZE)!;
            let strPage = String(self.nTab1PageNumber * nSize);
            
            //取一级经验评论
            let dicComment:[String: AnyObject]? = ["size":PAGE_SIZE as AnyObject,"page":strPage as AnyObject,"experienceId":id as AnyObject];
            
            AskDogGetFirstLevelCommentsDataRequest().startRequest(requestParmer:dicComment,viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    if(nil != dic){
                        
                        self.commentsDataModel = HYPinglunResultDataModel(jsonDic: dic!);
                        
                    }
                }
            });
            
            //FIXME: 分页未做
            //取相关分享
            let dic:[String: AnyObject]? = ["size":PAGE_SIZE as AnyObject,"page":"0" as AnyObject,"type":"experience_related" as AnyObject,"experienceId":id as AnyObject];
            
            AskDogExperienceRelatedDataRequest().startRequest(requestParmer:dic,viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    if(nil != dic){
                        self.dataArray.removeAll();
                        if let resultObj = dic!["result"]{
                            let arr:NSArray = resultObj as! NSArray;
                            for item in arr{
                                let da:HYExperienceListDataModel = HYExperienceListDataModel(jsonDic: item as! NSDictionary);
                                self.dataArray.append(da);
                            }
                            
                            self.tableView.reloadSections(IndexSet(integer: 1), with: .fade);
                        }
                    }
                }
            });
            
        }
        
        
    }
    
    
    
    override func viewDidLoad() {
        
        stringNavBarTitle = "经验详情";
        super.viewDidLoad()
        
        let cellIDTop:String = "HYContenListTopTableViewCell";
        let nibTop:UINib = UINib(nibName: "HYContenListTopTableViewCell", bundle: nil);
        tableView.register(nibTop, forCellReuseIdentifier: cellIDTop);
        
        let cellIDTopVideo:String = "HYContentListTopVideoTableViewCell";
        let nibTopVideo:UINib = UINib(nibName: "HYContentListTopVideoTableViewCell", bundle: nil);
        tableView.register(nibTopVideo, forCellReuseIdentifier: cellIDTopVideo);
        
        videoCell = tableView.dequeueReusableCell(withIdentifier: cellIDTopVideo) as! HYContentListTopVideoTableViewCell;
        videoCell.delegate = self;
        videoCell.setPtr(self);
        videoCell.bmPlayer.landPlayView = self.landPlayerBkView;
        
        
        let cellID:String = "HYPingLunReplyTableViewCell";
        let nib:UINib = UINib(nibName: "HYPingLunReplyTableViewCell", bundle: nil);
        tableView.register(nib, forCellReuseIdentifier: cellID);
        
        let cellID1:String = "HYPingLunSubReplyTableViewCell";
        let nib1:UINib = UINib(nibName: "HYPingLunSubReplyTableViewCell", bundle: nil);
        tableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        //解决分割线不对齐问题
        if tableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tableView.separatorInset = UIEdgeInsets.zero;
        }
        if tableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tableView.layoutMargins = UIEdgeInsets.zero;
        }
        tableView.estimatedRowHeight = 400;
        tableView.rowHeight = UITableViewAutomaticDimension;
        
        //设置上下拉刷新
        header1.setRefreshingTarget(self, refreshingAction: #selector(header1Refresh));
        self.tableView.header = header1;
        footer1.setRefreshingTarget(self, refreshingAction: #selector(footer1Refresh))
        self.tableView.footer = footer1;
        
        //默认都不显示
        self.header1.isHidden = true;
        self.footer1.isHidden = true;
        
        self.tableView.footer.isAutomaticallyHidden = false;
        
        self.loadData();
        
        if(true == isVideo){
            
            if(true == self.isMySelfExperience){
                //说明不要收费了
                self.dataModel?.isPayed = true;
                self.videoCell.setPinglunDataModel(self.dataModel!);
            }else{
                
                //如果已经登录  要取一下视频的收费状态
                if (true == Global.sharedInstance.isLogined){
                    
                    //判断视频是不是自己的
                    var myId:String = "";
                    if let id1:String = Global.sharedInstance.userInfo?.id{
                        myId = id1;
                    }
                    if let obj:HYAuthorDataModel = self.dataModel?.author{
                        if let id:String = obj.id{
                            if(id == myId){
                                self.isMySelfExperience = true;
                            }
                        }
                    }
                    
                    if(true == self.isMySelfExperience){
                        //说明不要收费了
                        self.dataModel?.isPayed = true;
                        self.videoCell.setPinglunDataModel(self.dataModel!);
                    }else{
                        //取视频的支付状态
                        if let id = self.dataModel?.id{
                            
                            let strExUrl:String = "/\(id)";
                            
                            AskDogGetExperiencePayStatusRequest().startRequest(exUrl:strExUrl,viewController:self,completionHandler: {
                                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                                if(true == beOk){
                                    if(nil != dic){
                                        //"FREE",   // FREE, NOT_PAY,  PREPAY, PAID
                                        let data:HYPayStatusDataModel = HYPayStatusDataModel(jsonDic: dic!);
                                        
                                        let arry:[String] = ["FREE","PAID"];
                                        if let status = data.pay_status{
                                            if arry.contains(status){
                                                //说明不要收费了
                                                self.dataModel?.isPayed = true;
                                                
                                            }else{
                                                self.dataModel?.isPayed = false;
                                            }
                                        }
                                        self.videoCell.setPinglunDataModel(self.dataModel!);
                                    }
                                }
                            });
                        }
                    }
                }
            }
            
        }
 
    }
    
    
    func header1Refresh() -> Void {
        print("header1Refresh");
        
        if let id = dataModel?.id{
            self.nTab1PageNumber = 0;
            let nSize:Int = Int(PAGE_SIZE)!;
            
            let strPage = String(self.nTab1PageNumber * nSize);
            
            //取一级经验评论
            let dicComment:[String: AnyObject]? = ["size":PAGE_SIZE as AnyObject,"page":strPage as AnyObject,"experienceId":id as AnyObject];
            
            AskDogGetFirstLevelCommentsDataRequest().startRequest(requestParmer:dicComment,viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    if(nil != dic){
                        
                        self.commentsDataModel = HYPinglunResultDataModel(jsonDic: dic!);
                        if(self.commentsDataModel!.last == true){
                            // self.footer1.hidden = true;
                            self.tableView.footer.endRefreshingWithNoMoreData();
                        }else{
                            //self.footer1.hidden = false;
                            self.tableView.footer.resetNoMoreData();
                        }
                        self.tableView.reloadData();
                    }
                }
                self.tableView.header.endRefreshing();
            });
        }
    }
    
    func footer1Refresh() -> Void {
        print("footer1Refresh");
        
        if let id = dataModel?.id{
            let nPage = self.nTab1PageNumber + 1;
            let nSize:Int = Int(PAGE_SIZE)!;
            
            let strPage = String(nPage * nSize);
            
            //取一级经验评论
            let dicComment:[String: AnyObject]? = ["size":PAGE_SIZE as AnyObject,"page":strPage as AnyObject,"experienceId":id as AnyObject];
            
            AskDogGetFirstLevelCommentsDataRequest().startRequest(requestParmer:dicComment,viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    if(nil != dic){
                        
                        let temp:HYPinglunResultDataModel = HYPinglunResultDataModel(jsonDic: dic!);
                        
                        for item in temp.result!{
                            self.commentsDataModel!.result?.append(item);
                        }
                        
                        self.commentsDataModel!.size = temp.size;
                        self.commentsDataModel!.page = temp.page;
                        self.commentsDataModel!.total = temp.total;
                        self.commentsDataModel!.last = temp.last;
                        
                        self.tableView.footer.endRefreshing();
                        self.tableView.reloadData();
                        self.nTab1PageNumber += 1;
                        
                        if(true == temp.last){
                            //self.footer1.hidden = true;
                            self.tableView.footer.endRefreshingWithNoMoreData();
                        }else{
                            //self.footer1.hidden = false;
                            self.tableView.footer.resetNoMoreData();
                        }
                    }
                }
                self.tableView.footer.endRefreshing();
            });
            
        }
        
    }
    
    //    @objc func btnSearchClicked(sender: UIButton) -> Void {
    //        print("btnSearchClicked");
    //    }
    
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
        
        if(1 == (indexPath as NSIndexPath).section){
            
            self.tableView.delayCellClicked(clickedMethod: {
                () -> Void in
                
                let d:HYExperienceListDataModel = self.dataArray[(indexPath as NSIndexPath).row];
                
                if let id = d.id{
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

        }
        
        
        
        
        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(0 == (indexPath as NSIndexPath).section){
            if(true == isVideo){
                if(true == IS_IPHONE6S_PLUS_DEV){
                    return 450.0;
                }else if(true == IS_IPHONE66S_DEV){
                    return 420.0;
                }else if(true == IS_IPHONE55S_DEV){
                    return 390.0;
                }else{
                    return 370.0;
                }
            }else{
                //var h:CGFloat = 0;
                
                let height:CGFloat = tableView.fd_heightForCell(withIdentifier: "HYContenListTopTableViewCell", cacheBy: indexPath, configuration: {
                    (obj:Any!) -> Void in
                    
                    
                    
                    let temp:HYContenListTopTableViewCell = obj as! HYContenListTopTableViewCell;
                    temp.delegate = self;
                    temp.setPinglunDataModel(self.dataModel!);
                    
                })
                
                
                return height;
                //return height + self.contentCellHeight;
                
                //  return self.contentCellHeight + 200;
            }
            
            
        }else if(1 == (indexPath as NSIndexPath).section){
            return 90;
        }else if(2 == (indexPath as NSIndexPath).section){
            if(0 == (indexPath as NSIndexPath).row){
                return 150;
            }
            return 0;
        }else{
            if(0 == (indexPath as NSIndexPath).row){
                let height:CGFloat = tableView.fd_heightForCell(withIdentifier: "HYPingLunReplyTableViewCell", cacheBy: indexPath, configuration: {
                    (obj:Any!) -> Void in

                    
                    
                    
                    let temp:HYPingLunReplyTableViewCell = obj as! HYPingLunReplyTableViewCell;
                    temp.indexPath = indexPath;
                    
                    if let cmsModeal:HYPinglunResultDataModel = self.commentsDataModel{
                        let da:HYPinglunDataModel = cmsModeal.result![(indexPath as NSIndexPath).section-3];
                        temp.setCommentsDataModel(da);
//                        if let da:HYPinglunDataModel = cmsModeal.result![(indexPath as NSIndexPath).section-3]{
//                            temp.setCommentsDataModel(da);
//                        }
                    }
                    
                    temp.delegate = self;
                })
                
                return height + 10;
            }else{
                let height:CGFloat = tableView.fd_heightForCell(withIdentifier: "HYPingLunSubReplyTableViewCell", cacheBy: indexPath, configuration: {
                    (obj:Any!) -> Void in

                    let temp:HYPingLunSubReplyTableViewCell = obj as! HYPingLunSubReplyTableViewCell;
                    temp.indexPath = indexPath;
                    
                    if let cmsModeal:HYPinglunResultDataModel = self.commentsDataModel{
                        let da:HYPinglunDataModel = cmsModeal.result![(indexPath as NSIndexPath).section-3];
                        if let arr:[HYPinglunDataModel] = da.comments?.result{
                            let d:HYPinglunDataModel = arr[(indexPath as NSIndexPath).row-1]
                            temp.setSubDataModel(d);
                        }
                        
//                        if let da:HYPinglunDataModel = cmsModeal.result![(indexPath as NSIndexPath).section-3]{
//                            if let arr:[HYPinglunDataModel] = da.comments?.result{
//                                if let d:HYPinglunDataModel = arr[(indexPath as NSIndexPath).row-1]{
//                                    temp.setSubDataModel(d);
//                                }
//                            }
//                        }
                    }
                    
                    temp.delegate = self;
                    
                    //                    if let obj1:AnyObject = self.comments[indexPath.section-3]{
                    //                        let dic:NSDictionary = obj1 as! NSDictionary;
                    //                        if let subObj:AnyObject = dic["comments"]{
                    //                            let arr:NSArray = subObj as! NSArray;
                    //                            if let subObj:AnyObject = arr[indexPath.row - 1]{
                    //                                let subDic:NSDictionary = subObj as! NSDictionary;
                    //                                temp.setData(subDic);
                    //                            }
                    //                        }
                    //                    }
                    
                })
                
                return height + 10;
            }
        }
        
    }
    
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if(1 == section){
            return 50;
        }else if(2 == section){
            return 50;
        }
        return 0;
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        var headerV:UIView!
        if(1 == section){
            let cellID:String = "headerCell1";
            let nib:UINib = UINib(nibName: "HYContentListHeaderTableViewCell", bundle: nil);
            tableView.register(nib, forCellReuseIdentifier: cellID);
            let cell:HYContentListHeaderTableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID) as! HYContentListHeaderTableViewCell;
            cell.delegate = self;
            cell.setTheSectionIndex(section);
            //cell.sectionIndex = section;
            cell.lblTitle.text = "相关分享";
            
            if(true == isShowXiangguanFenxiang){
                cell.setCellBtnImage(UIImage(named: "up")!);
            }else{
                cell.setCellBtnImage(UIImage(named: "down")!);
            }
            cell.lineView.isHidden = true;
            
            let fr:CGRect = CGRect(x: 0, y: 0, width: tableView.frame.size.width, height: 50);
            cell.frame = fr;
            headerV = UIView(frame: fr);
            headerV.backgroundColor = UIColor.clear;
            headerV.addSubview(cell);
            return headerV;
            
        }else if(2 == section){
            let cellID:String = "headerCell2";
            let nib:UINib = UINib(nibName: "HYContentListHeaderTableViewCell", bundle: nil);
            tableView.register(nib, forCellReuseIdentifier: cellID);
            let cell:HYContentListHeaderTableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID) as! HYContentListHeaderTableViewCell;
            cell.delegate = self;
            cell.setTheSectionIndex(section);
            //cell.sectionIndex = section;
            
            if(true == isShowXiangguanPinglun){
                cell.setCellBtnImage(UIImage(named: "up")!);
                cell.lineView.isHidden = false;
            }else{
                cell.setCellBtnImage(UIImage(named: "down")!);
                cell.lineView.isHidden = true;
            }
            
            //            if let comment_cnt:Int = dataModel?.comment_count{
            //                cell.lblTitle.text = "评论(\(comment_cnt))";
            //            }else{
            //                cell.lblTitle.text = "评论(0)";
            //            }
            
            cell.lblTitle.text = "评论";
            
            
            let fr:CGRect = CGRect(x: 0, y: 0, width: tableView.frame.size.width, height: 50);
            cell.frame = fr;
            headerV = UIView(frame: fr);
            headerV.backgroundColor = UIColor.clear;
            headerV.addSubview(cell);
            return headerV;
        }
        
        return headerV;
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
        
        
        if(true == isShowXiangguanPinglun){
            
            if let cmsModeal:HYPinglunResultDataModel = self.commentsDataModel{
                let cms:[HYPinglunDataModel] = cmsModeal.result!;
                return 3 + cms.count
                
//                if let cms:[HYPinglunDataModel] = cmsModeal.result!{
//                    return 3 + cms.count
//                }
            }
            return 3;
        }else{
            return 3;
        }
        
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        
        if(section == 0){
            
            return 1;
        }else if(section == 1){
            if(true == isShowXiangguanFenxiang){
                return self.dataArray.count;
            }
            return 0;
        }else if(section == 2){
            if(true == isShowXiangguanPinglun){
                return 1;
            }
            return 0;
        }else{
            
            if let cmsModeal:HYPinglunResultDataModel = self.commentsDataModel{
                let cms:HYPinglunDataModel = cmsModeal.result![section-3];
                if let subCms:[HYPinglunDataModel] = cms.comments?.result{
                    if(subCms.count > 2){
                        if(false == cms.ifOpen){
                            return 2+1;
                        }else{
                            return subCms.count + 1;
                        }
                    }else{
                        return subCms.count + 1;
                    }
                }else{
                    return 1;
                }
            }
            return 0;
            
            
            //            if let obj:AnyObject = self.comments[section-3]{
            //                let dic:NSDictionary = obj as! NSDictionary;
            //                if let subObj:AnyObject = dic["comments"]{
            //                    let arr:NSArray = subObj as! NSArray;
            //                    return arr.count+1;
            //                }
            //            }
            //            return 0;
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell:UITableViewCell!
        if(0 == (indexPath as NSIndexPath).section){
            if(true == self.isVideo){
                //let cellID:String = "HYContentListTopVideoTableViewCell";
                //cell = tableView.dequeueReusableCellWithIdentifier(cellID) as! HYContentListTopVideoTableViewCell;
                //let temp:HYContentListTopVideoTableViewCell = cell as! HYContentListTopVideoTableViewCell;
                //temp.delegate = self;
                //temp.setPinglunDataModel(dataModel!);
                //temp.setPtr(self);
                
                //temp.bmPlayer.landPlayView = self.landPlayerBkView;
                
                videoCell.setPinglunDataModel(dataModel!);
                
                return videoCell;
            }else{
                let cellID:String = "HYContenListTopTableViewCell";
                cell = tableView.dequeueReusableCell(withIdentifier: cellID) as! HYContenListTopTableViewCell;
                let temp:HYContenListTopTableViewCell = cell as! HYContenListTopTableViewCell;
                temp.delegate = self;
                temp.setPinglunDataModel(dataModel!);
                
                return cell;
            }
        }else if(1 == (indexPath as NSIndexPath).section){
            let cellID:String = "cell1";
            let nib:UINib = UINib(nibName: "HYContentListFenxiangTableViewCell", bundle: nil);
            tableView.register(nib, forCellReuseIdentifier: cellID);
            cell = tableView.dequeueReusableCell(withIdentifier: cellID) as! HYContentListFenxiangTableViewCell;
            let temp:HYContentListFenxiangTableViewCell = cell as! HYContentListFenxiangTableViewCell;
            let d:HYExperienceListDataModel = self.dataArray[(indexPath as NSIndexPath).row];
            temp.setData(d);
            
            return cell;
        }else if(2 == (indexPath as NSIndexPath).section){
            
            let cellID:String = "cell21";
            let nib:UINib = UINib(nibName: "HYInputPingLunTableViewCell", bundle: nil);
            tableView.register(nib, forCellReuseIdentifier: cellID);
            cell = tableView.dequeueReusableCell(withIdentifier: cellID) as! HYInputPingLunTableViewCell;
            let temp:HYInputPingLunTableViewCell = cell as! HYInputPingLunTableViewCell;
            temp.delegate = self;
            temp.indexPath = indexPath;
            temp.updateData();
            return cell;
            
        }else{
            if(0 == (indexPath as NSIndexPath).row){
                let cellID:String = "HYPingLunReplyTableViewCell";
                cell = tableView.dequeueReusableCell(withIdentifier: cellID) as! HYPingLunReplyTableViewCell;
                let temp:HYPingLunReplyTableViewCell = cell as! HYPingLunReplyTableViewCell;
                
                temp.indexPath = indexPath;
                
                if let cmsModeal:HYPinglunResultDataModel = self.commentsDataModel{
                    let da:HYPinglunDataModel = cmsModeal.result![(indexPath as NSIndexPath).section-3];
                    temp.setCommentsDataModel(da);
                    
                }
                
                temp.delegate = self;
                
                return cell;
            }else{
                
                let cellID:String = "HYPingLunSubReplyTableViewCell";
                cell = tableView.dequeueReusableCell(withIdentifier: cellID) as! HYPingLunSubReplyTableViewCell;
                let temp:HYPingLunSubReplyTableViewCell = cell as! HYPingLunSubReplyTableViewCell;
                
                temp.indexPath = indexPath;
                
                if let cmsModeal:HYPinglunResultDataModel = self.commentsDataModel{
                    let da:HYPinglunDataModel = cmsModeal.result![(indexPath as NSIndexPath).section-3];
                        if let arr:[HYPinglunDataModel] = da.comments?.result{
                            let d:HYPinglunDataModel = arr[(indexPath as NSIndexPath).row-1];
                                temp.setSubDataModel(d);
                            
                        }
                    
                }
                
                temp.delegate = self;
                
                return cell;
            }
        }
        
    }
    
    //MARK:HYContentListHeaderTableViewCellDelegate
    func hyContentListHeaderTableViewCellBtnClicked(cell ce: HYContentListHeaderTableViewCell, sectionIndex section: Int) {
        print("hyContentListHeaderTableViewCellBtnClicked at index=\(section)");
        if(1 == section){
            isShowXiangguanFenxiang = !isShowXiangguanFenxiang;
            if(true == isShowXiangguanFenxiang){
                ce.setCellBtnImage(UIImage(named: "up")!);
            }else{
                ce.setCellBtnImage(UIImage(named: "down")!);
            }
            
            self.tableView.beginUpdates();
            
            let ran:NSRange = NSMakeRange(1, 1);
            //tableView.insertSections(NSIndexSet(indexesInRange: ran), withRowAnimation: .Fade);
            tableView.reloadSections(IndexSet(integersIn: ran.toRange() ?? 0..<0), with: .fade);
            self.tableView.endUpdates();
            
        }else if(2 == section){
            
            if let cmsModeal:HYPinglunResultDataModel = self.commentsDataModel{
                isShowXiangguanPinglun = !isShowXiangguanPinglun;
                if(true == isShowXiangguanPinglun){
                    ce.setCellBtnImage(UIImage(named: "up")!);
                    ce.lineView.isHidden = true;
                    
                    self.header1.isHidden = false;
                    self.footer1.isHidden = false;
                    if(cmsModeal.last == true){
                        //self.footer1.hidden = true;
                        self.tableView.footer.endRefreshingWithNoMoreData();
                    }else{
                        //self.footer1.hidden = false;
                        self.tableView.footer.resetNoMoreData();
                    }
                    
                    
                    //                    self.tableView.beginUpdates();
                    //
                    //                    let ran:NSRange = NSMakeRange(section+1, cmsModeal.result!.count);
                    //                    //tableView.insertSections(NSIndexSet(indexesInRange: ran), withRowAnimation: .Fade);
                    //                    tableView.reloadSections(NSIndexSet(indexesInRange: ran), withRowAnimation: .Fade);
                    //                    self.tableView.endUpdates();
                    
                }else{
                    ce.setCellBtnImage(UIImage(named: "down")!);
                    ce.lineView.isHidden = false;
                    
                    self.header1.isHidden = true;
                    self.footer1.isHidden = true;
                }
                tableView.reloadData();
            }
        }
    }
    
    //MARK:HYPingLunReplyTableViewCellDelegate
//    func hyPingLunReplyTableViewCellOpenBtnClicked(cell ce:HYPingLunReplyTableViewCell) -> Void{
//        
////        //先取出数据
////        
////        dataModel.ifOpen = !dataModel.ifOpen;
////        print("dataModel.ifOpen chage to  = \(dataModel.ifOpen)");
////        if(true == dataModel.ifOpen){
////            btnOpen.setTitle("收起", forState: .Normal);
////        }else{
////            btnOpen.setTitle("展开", forState: .Normal);
////        }
////        
//        
//        
//        if(true == ce.dataModel.ifOpen){
//            
//            //先取回来数据 才能插入到列表中
//            if(false == ce.dataModel.ifCacheCommentsData){
//                //取二级经验评论
//                if let id = ce.dataModel.id{
//                    let dicComment:[String: AnyObject]? = ["commentId":id];
//                    
//                    AskDogGetSecondLevelCommentsDataRequest().startRequest(requestParmer:dicComment,viewController:self,completionHandler: {
//                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
//                        if(true == beOk){
//                            
//                            if (arr != nil){
//                                
//                                if let da:HYPinglunDataModel = self.commentsDataModel!.result![ce.indexPath.section-3]{
//                                    
//                                    //let beOpen:Bool = da.ifOpen;
//                                    
//                                    if let arrCms:[HYPinglunDataModel] = da.comments?.result{
//                                        
//                                        var arrComs:[HYPinglunDataModel] = arrCms;
//                                        
//                                        let startN = arrCms.count;
//                                        var ins:[NSIndexPath] = [NSIndexPath]();
//                                        
//                                        let nEnd = arr?.count;
//                                        
//                                        for index in startN ..< nEnd!{
//                                            let d:NSDictionary = arr![index] as! NSDictionary;
//                                            let model:HYPinglunDataModel = HYPinglunDataModel(jsonDic: d);
//                                            arrComs.append(model);
//                                            
//                                            let path:NSIndexPath = NSIndexPath(forRow: index, inSection: ce.indexPath.section);
//                                            ins.append(path);
//                                            //startN += 1;
//                                        }
//                                        
//                                        //                                        for item in arr!{
//                                        //                                            let d:NSDictionary = item as! NSDictionary;
//                                        //                                            let model:HYPinglunDataModel = HYPinglunDataModel(jsonDic: d);
//                                        //                                            arrComs.append(model);
//                                        //
//                                        //                                            let path:NSIndexPath = NSIndexPath(forRow: startN, inSection: ce.indexPath.section);
//                                        //                                            ins.append(path);
//                                        //                                            startN += 1;
//                                        //                                        }
//                                        
//                                        da.comments?.result = arrComs;
//                                        da.ifCacheCommentsData = true;
//                                        self.commentsDataModel!.result![ce.indexPath.section-3] = da;
//                                        
//                                        
//                                        self.tableView.beginUpdates();
//                                        self.tableView.insertRowsAtIndexPaths(ins, withRowAnimation: .Bottom);
//                                        self.tableView.endUpdates();
//                                        
//                                        //tableView.scrollToRowAtIndexPath(NSIndexPath(forRow: 0, inSection: ce.indexPath.section), atScrollPosition: .Top, animated: true);
//                                    }
//                                }
//                                
//                                
//                                
//                                
//                                
//                            }
//                            
//                            
//                            //                            if(nil != dic){
//                            //
//                            //                                let temp:HYPinglunResultDataModel = HYPinglunResultDataModel(jsonDic: dic!);
//                            //                                print("123123");
//                            //
//                            //                            }
//                        }
//                    });
//                }
//            }
//            
//            if let da:HYPinglunDataModel = self.commentsDataModel!.result![ce.indexPath.section-3]{
//                if let arr:[HYPinglunDataModel] = da.comments?.result{
//                    let n = arr.count;
//                    
//                    var ins:[NSIndexPath] = [NSIndexPath]();
//                    
//                    for index in 2..<n{
//                        let path:NSIndexPath = NSIndexPath(forRow: index, inSection: ce.indexPath.section);
//                        ins.append(path);
//                    }
//                    //tableView.beginUpdates();
//                    tableView.insertRowsAtIndexPaths(ins, withRowAnimation: .Bottom);
//                    //tableView.endUpdates();
//                    
//                    //tableView.scrollToRowAtIndexPath(NSIndexPath(forRow: 0, inSection: ce.indexPath.section), atScrollPosition: .Top, animated: true);
//                }
//            }
//            
//        }else{
//            
//            if let da:HYPinglunDataModel = self.commentsDataModel!.result![ce.indexPath.section-3]{
//                if let arr:[HYPinglunDataModel] = da.comments?.result{
//                    let n = arr.count;
//                    
//                    var ins:[NSIndexPath] = [NSIndexPath]();
//                    
//                    for index in 2..<n{
//                        let path:NSIndexPath = NSIndexPath(forRow: index, inSection: ce.indexPath.section);
//                        ins.append(path);
//                    }
//                    tableView.beginUpdates();
//                    tableView.deleteRowsAtIndexPaths(ins, withRowAnimation: .Bottom);
//                    tableView.endUpdates();
//                }
//            }
//        }
//        
//        //let ins:NSIndexSet = NSIndexSet(index: ce.indexPath.section);
//        //tableView.reloadSections(ins, withRowAnimation: .Bottom);
//    }
    
    func hyPingLunReplyTableViewCellOpenBtnClicked(cell ce:HYPingLunReplyTableViewCell) -> Void{
        
        //        //先取出数据
        //
        //        dataModel.ifOpen = !dataModel.ifOpen;
        //        print("dataModel.ifOpen chage to  = \(dataModel.ifOpen)");
        //        if(true == dataModel.ifOpen){
        //            btnOpen.setTitle("收起", forState: .Normal);
        //        }else{
        //            btnOpen.setTitle("展开", forState: .Normal);
        //        }
        //
        
        let firstCommentData:HYPinglunDataModel = self.commentsDataModel!.result![ce.indexPath.section-3];
        if(false == firstCommentData.ifOpen){
            //就是要展开列表
            if(false == ce.dataModel.ifCacheCommentsData){
                //要先取回数据
                //取二级经验评论
                if let id = ce.dataModel.id{
                    let dicComment:[String: AnyObject]? = ["commentId":id as AnyObject];
                    
                    AskDogGetSecondLevelCommentsDataRequest().startRequest(requestParmer:dicComment,viewController:self,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            
                            
                            if (arr != nil){
                                //let beOpen:Bool = da.ifOpen;
                                
                                if let arrCms:[HYPinglunDataModel] = firstCommentData.comments?.result{
                                    
                                    var arrComs:[HYPinglunDataModel] = arrCms;
                                    
                                    arrComs.removeAll();
                                    for item in arr!{
                                        let d:NSDictionary = item as! NSDictionary;
                                        let model:HYPinglunDataModel = HYPinglunDataModel(jsonDic: d);
                                        arrComs.append(model);
                                    }
                                    firstCommentData.comments?.result = arrComs;
                                    firstCommentData.ifCacheCommentsData = true;
                                    firstCommentData.ifOpen = true;
                                    self.commentsDataModel!.result![ce.indexPath.section-3] = firstCommentData;
                                    self.tableView.reloadSections(IndexSet(integer: ce.indexPath.section), with: .fade);
                                }
                            }
                        }
                    });
                }
            }else{
                firstCommentData.ifOpen = true;
                self.commentsDataModel!.result![ce.indexPath.section-3] = firstCommentData;
                //刷新section
                self.tableView.beginUpdates();
                self.tableView.reloadSections(IndexSet(integer: ce.indexPath.section), with: .fade);
                self.tableView.endUpdates();
            }
        }else{
            //就是要关闭列表
            firstCommentData.ifOpen = false;
            self.commentsDataModel!.result![ce.indexPath.section-3] = firstCommentData;
            //刷新section
            self.tableView.beginUpdates();
            self.tableView.reloadSections(IndexSet(integer: ce.indexPath.section), with: .fade);
            self.tableView.endUpdates();
        }
        

    }

    
    func hyPingLunReplyTableViewCellRrplyBtnClicked(cell ce:HYPingLunReplyTableViewCell) -> Void
    {
        
        CommonTools.checkLogin(viewController: self, afterLoginCall:nil,callBack: {
            () -> Void in
            
            let v:HYReplyView = HYReplyView(frame: CGRect.zero);//UIScreen.mainScreen().bounds
            v.show(inView: self.view,callBack: {
                (strInput:String!) -> Void in
                print("input string = \(strInput)");
                
                
                if let experienceID = self.dataModel?.id{
                    let strParmr:String = "?experienceId=" + experienceID;
                    print("share id = \(strParmr)");
                    
                    if let strID = ce.dataModel.id{
                        let dic:[String: AnyObject] = ["content":strInput as AnyObject,"reply_comment_id":strID as AnyObject];
                        //发送评论
                        AskDogCreateCommentDataRequest().startRequest(requestParmer: dic,exUrl:strParmr,viewController:self,completionHandler: {
                            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                            if(true == beOk){
                                print("AskDogCreateCommentDataRequest 评论发送成功");
                                CommonTools.showMessage("评论回复成功!", vc: self, hr: {
                                    () -> Void in
                                    
                                    //先取出一级评论数组数据
                                    let nFirsTIndex = ce.indexPath.section - 3;
                                    let firstCommentData:HYPinglunDataModel = (self.commentsDataModel!.result![nFirsTIndex]);
                                    //先判断是否已经通过网络把二级评论取回来了
                                    if(true == firstCommentData.ifCacheCommentsData){
                                        //如果取来了 就要判断 一共有多少评论
                                        //要刷新评论列表
                                        if let returnDic = dic{
                                            let data:HYPinglunDataModel = HYPinglunDataModel(jsonDic: returnDic);
                                            //先取出一级评论数组数据
                                            if let arrObj = firstCommentData.comments?.result{
                                                var array:[HYPinglunDataModel] = arrObj as [HYPinglunDataModel];
                                                array.append(data);
                                                firstCommentData.comments!.result = array;
                                            }else{
                                                var newArr:[HYPinglunDataModel] = [HYPinglunDataModel]();
                                                newArr.append(data);
                                                firstCommentData.comments!.result = newArr;
                                            }
                                            
                                            self.commentsDataModel!.result![nFirsTIndex] = firstCommentData;
                                            
                                            //刷新section
                                            self.tableView.beginUpdates();
                                            self.tableView.reloadSections(IndexSet(integer: ce.indexPath.section), with: .fade);
                                            self.tableView.endUpdates();
                                        }
                                    }else{
                                        //如果没取来  要先把二级评论取回来
                                        if let firstLevelId:String = firstCommentData.id{
                                            let dicComment:[String: AnyObject]? = ["commentId":firstLevelId as AnyObject];
                                            
                                            AskDogGetSecondLevelCommentsDataRequest().startRequest(requestParmer:dicComment,viewController:self,completionHandler: {
                                                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                                                if(true == beOk){
                                                    
                                                    if (arr != nil){
                                                        //let beOpen:Bool = da.ifOpen;
                                                        
                                                        if let arrCms:[HYPinglunDataModel] = firstCommentData.comments?.result{
                                                            
                                                            var arrComs:[HYPinglunDataModel] = arrCms;
                                                            
                                                            arrComs.removeAll();
                                                            for item in arr!{
                                                                let d:NSDictionary = item as! NSDictionary;
                                                                let model:HYPinglunDataModel = HYPinglunDataModel(jsonDic: d);
                                                                arrComs.append(model);
                                                            }
                                                            firstCommentData.comments?.result = arrComs;
                                                            firstCommentData.ifCacheCommentsData = true;
                                                            self.commentsDataModel!.result![nFirsTIndex] = firstCommentData;
                                                            self.tableView.reloadSections(IndexSet(integer: ce.indexPath.section), with: .fade);
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
 
                                });
                            }
                        });
                    }
                }
            });
            
        });
        
        
        //
        //        if(false == Global.sharedInstance.isLogined){
        //            let login:HYLoginViewController = HYLoginViewController(nibName: "HYLoginViewController", bundle: NSBundle.mainBundle());
        //            login.callBackLoginedFun = {
        //                (beOK:Bool)-> Void in
        //                print("temp login success");
        //            };
        //
        //            login.callWeixinFun = {
        //                (beOK:Bool)-> Void in
        //                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
        //                self.navigationController?.pushViewController(vc, animated: true);
        //            };
        //            login.callQQFun = {
        //                (beOK:Bool)-> Void in
        //                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
        //                self.navigationController?.pushViewController(vc, animated: true);
        //            };
        //            login.callWeiboFun = {
        //                (beOK:Bool)-> Void in
        //                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
        //                self.navigationController?.pushViewController(vc, animated: true);
        //            };
        //
        //            login.callBackFunForgetPwd = {
        //                (beOK:Bool)-> Void in
        //                if(true == beOK){
        //                    let vc:HYForgetPasswordViewController = HYForgetPasswordViewController(nibName: "HYForgetPasswordViewController", bundle: NSBundle.mainBundle());
        //                    self.navigationController?.pushViewController(vc, animated: true);
        //                }
        //            };
        //
        //            login.reloadLoginedHomePageDataCallBackFun = {
        //                (beOK:Bool)-> Void in
        //                if(true == beOK){
        //                    self.shouldUpdateData();
        //                }
        //            };
        //
        //            login.callBackGoCategories = {
        //                (beOK:Bool)-> Void in
        //                if(true == beOK){
        //                    AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
        //                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
        //                        if(true == beOk){
        //
        //                            var array:[HYCategoryDataModel] = [HYCategoryDataModel]();
        //
        //                            for item in arr!{
        //                                if let obj:AnyObject = item{
        //                                    let dic:NSDictionary = obj as! NSDictionary;
        //                                    let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
        //                                    array.append(data);
        //                                }
        //                            }
        //
        //
        //                            let vc:HYDingyuePindaoViewController = HYDingyuePindaoViewController(nibName: "HYDingyuePindaoViewController", bundle: NSBundle.mainBundle());
        //                            vc.dataModelArray = array;
        //                            self.navigationController?.pushViewController(vc, animated: true);
        //                        }
        //                    });
        //                }
        //            }
        //
        //            //   login.callBackGoXieyi = {
        //            //       (beOK:Bool)-> Void in
        //            //       if(true == beOK){
        //            //           let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: NSBundle.mainBundle());
        //            //           vc.setReturnTyle(UserAgreeMentReturnStyle.UserAgreeMentReturnStyle_pop_animated);
        //            //           self.navigationController?.pushViewController(vc, animated: true);
        //            //           //self.presentViewController(vc, animated: true, completion: nil);
        //            //       }
        //            //   }
        //
        //            self.presentViewController(login, animated: true, completion: nil);
        //        }else{
        //            let v:HYReplyView = HYReplyView(frame: CGRectZero);//UIScreen.mainScreen().bounds
        //            v.show(inView: self.view,callBack: {
        //                (strInput:String!) -> Void in
        //                print("input string = \(strInput)");
        //
        //
        //                if let experienceID = self.dataModel?.id{
        //                    let strParmr:String = "?experienceId=" + experienceID;
        //                    print("share id = \(strParmr)");
        //
        //                    if let strID = ce.dataModel.id{
        //                        let dic:[String: AnyObject] = ["content":strInput,"reply_comment_id":strID];
        //                        //发送评论
        //                        AskDogCreateCommentDataRequest().startRequest(requestParmer: dic,exUrl:strParmr,viewController:self,completionHandler: {
        //                            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
        //                            if(true == beOk){
        //                                print("AskDogCreateCommentDataRequest 评论发送成功");
        //                                CommonTools.showMessage("评论回复成功!", vc: self, hr: {
        //                                    () -> Void in
        //
        //                                    //要刷新评论列表
        //                                    if let returnDic = dic{
        //                                         let data:HYPinglunDataModel = HYPinglunDataModel(jsonDic: returnDic);
        //                                        //先取出一级评论数组数据
        //                                        let nFirsTIndex = ce.indexPath.section - 3;
        //                                        let firstCommentData:HYPinglunDataModel = (self.commentsDataModel!.result![nFirsTIndex]);
        //                                        if let arrObj = firstCommentData.comments?.result{
        //                                            var array:[HYPinglunDataModel] = arrObj as [HYPinglunDataModel];
        //                                            array.append(data);
        //                                            self.commentsDataModel!.result![nFirsTIndex].comments!.result = array;
        //                                        }else{
        //                                            var newArr:[HYPinglunDataModel] = [HYPinglunDataModel]();
        //                                            newArr.append(data);
        //                                            self.commentsDataModel!.result![nFirsTIndex].comments!.result = newArr;
        //                                        }
        //
        //                                        //刷新section
        //                                        self.tableView.beginUpdates();
        //                                        self.tableView.reloadSections(NSIndexSet(index: ce.indexPath.section), withRowAnimation: .Fade);
        //                                        self.tableView.endUpdates();
        //                                    }
        //
        //
        //                                });
        //                            }
        //                        });
        //                    }
        //                }
        //            });
        //        }
        
        
    }
    
    //MARK:HYPingLunSubReplyTableViewCellDelegate
    func hyPingLunSubReplyTableViewCellReplyBtnClicked(cell ce:HYPingLunSubReplyTableViewCell) -> Void{
        
        CommonTools.checkLogin(viewController: self, afterLoginCall:nil,callBack: {
            () -> Void in
            
            let v:HYReplyView = HYReplyView(frame: CGRect.zero);
            v.show(inView: self.view, callBack: {
                (strInput:String!) -> Void in
                print("input string = \(strInput)");
                
                if let experienceID = self.dataModel?.id{
                    let strParmr:String = "?experienceId=" + experienceID;
                    print("share id = \(strParmr)");
                    
                    if let strID = ce.dataModel.id{
                        let dic:[String: AnyObject] = ["content":strInput as AnyObject,"reply_comment_id":strID as AnyObject];
                        //发送评论
                        AskDogCreateCommentDataRequest().startRequest(requestParmer: dic,exUrl:strParmr,viewController:self,completionHandler: {
                            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                            if(true == beOk){
                                print("AskDogCreateCommentDataRequest 评论发送成功");
                                CommonTools.showMessage("评论回复成功!", vc: self, hr: {
                                    () -> Void in
                                    
                                    if (nil != dic){
                                        //let data:HYPinglunDataModel = HYPinglunDataModel(jsonDic: returnDic);
                                        //先取出一级评论数组数据
                                        let nFirsTIndex = ce.indexPath.section - 3;
                                        let firstCommentData:HYPinglunDataModel = (self.commentsDataModel!.result![nFirsTIndex]);
                                        //先判断是否已经通过网络把二级评论取回来了
                                        if(true == firstCommentData.ifCacheCommentsData){
                                            //如果取来了 就要判断 一共有多少评论
                                            if let returnDic = dic{
                                                let data:HYPinglunDataModel = HYPinglunDataModel(jsonDic: returnDic);
                                                //先取出一级评论数组数据
                                                if let arrObj = firstCommentData.comments?.result{
                                                    var array:[HYPinglunDataModel] = arrObj as [HYPinglunDataModel];
                                                    array.append(data);
                                                    firstCommentData.comments!.result = array;
                                                }else{
                                                    var newArr:[HYPinglunDataModel] = [HYPinglunDataModel]();
                                                    newArr.append(data);
                                                    firstCommentData.comments!.result = newArr;
                                                }
                                                self.commentsDataModel!.result![nFirsTIndex] = firstCommentData;
                                                
                                                //刷新section
                                                self.tableView.beginUpdates();
                                                self.tableView.reloadSections(IndexSet(integer: ce.indexPath.section), with: .fade);
                                                self.tableView.endUpdates();
                                            }
                                        }else{
                                            //如果没取来  要先把二级评论取回来
                                            if let firstLevelId:String = firstCommentData.id{
                                                let dicComment:[String: AnyObject]? = ["commentId":firstLevelId as AnyObject];
                                                
                                                AskDogGetSecondLevelCommentsDataRequest().startRequest(requestParmer:dicComment,viewController:self,completionHandler: {
                                                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                                                    if(true == beOk){
                                                        
                                                        if (arr != nil){
                                                            //let beOpen:Bool = da.ifOpen;
                                                            
                                                            if let arrCms:[HYPinglunDataModel] = firstCommentData.comments?.result{
                                                                
                                                                var arrComs:[HYPinglunDataModel] = arrCms;
                                                                
                                                                arrComs.removeAll();
                                                                for item in arr!{
                                                                    let d:NSDictionary = item as! NSDictionary;
                                                                    let model:HYPinglunDataModel = HYPinglunDataModel(jsonDic: d);
                                                                    arrComs.append(model);
                                                                }
                                                                firstCommentData.comments?.result = arrComs;
                                                                firstCommentData.ifCacheCommentsData = true;
                                                                self.tableView.reloadSections(IndexSet(integer: ce.indexPath.section), with: .fade);
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                    

                                    
//                                    //先判断当前这条评论是否有多余两条的评论
//                                    if let returnDic = dic{
//                                        let data:HYPinglunDataModel = HYPinglunDataModel(jsonDic: returnDic);
//                                        //先取出一级评论数组数据
//                                        let nFirsTIndex = ce.indexPath.section - 3;
//                                        let firstCommentData:HYPinglunDataModel = (self.commentsDataModel!.result![nFirsTIndex]);
//                                        
//                                    }
                                    
//                                    //要刷新评论列表
//                                    if let returnDic = dic{
//                                        let data:HYPinglunDataModel = HYPinglunDataModel(jsonDic: returnDic);
//                                        //先取出一级评论数组数据
//                                        let nFirsTIndex = ce.indexPath.section - 3;
//                                        let firstCommentData:HYPinglunDataModel = (self.commentsDataModel!.result![nFirsTIndex]);
//                                        if let arrObj = firstCommentData.comments?.result{
//                                            var array:[HYPinglunDataModel] = arrObj as [HYPinglunDataModel];
//                                            array.append(data);
//                                            self.commentsDataModel!.result![nFirsTIndex].comments!.result = array;
//                                        }else{
//                                            var newArr:[HYPinglunDataModel] = [HYPinglunDataModel]();
//                                            newArr.append(data);
//                                            self.commentsDataModel!.result![nFirsTIndex].comments!.result = newArr;
//                                        }
//                                        
//                                        // print("result=\(self.commentsDataModel!.result![nFirsTIndex].comments!.result)");
//                                        
//                                        //判断数量 是否展开 才可以插入
//                                        let count = firstCommentData.comments?.result?.count;
//                                        self.tableView.beginUpdates();
//                                        if(true == firstCommentData.ifOpen){
//                                            //刷新section
//                                            
//                                            
//                                            let ins:NSIndexPath = NSIndexPath(forRow: count!, inSection: ce.indexPath.section);
//                                            
//                                            
//                                            self.tableView.insertRowsAtIndexPaths([ins], withRowAnimation: .Fade);
//                                            
//                                            //self.tableView.reloadSections(NSIndexSet(index: ce.indexPath.section), withRowAnimation: .Fade);
//                                            
//                                        }else{
//                                            if(count <= 2){
//                                                //刷新section
//                                                
//                                                
//                                                let ins:NSIndexPath = NSIndexPath(forRow: count!, inSection: ce.indexPath.section);
//                                                
//                                                self.tableView.insertRowsAtIndexPaths([ins], withRowAnimation: .Fade);
//                                                //self.tableView.reloadSections(NSIndexSet(index: ce.indexPath.section), withRowAnimation: .Fade);
//                                                
//                                            }
//                                        }
//                                        let ins0:NSIndexPath = NSIndexPath(forRow: 0, inSection: ce.indexPath.section);
//                                        self.tableView.reloadRowsAtIndexPaths([ins0], withRowAnimation: .Fade);
//                                        self.tableView.endUpdates();
//                                    }
                                    
                                    
                                });
                            }
                        });
                    }
                }
                
            });
            
        });
        
        
    }
    
//    func hyPingLunSubReplyTableViewCellReplyBtnClicked(cell ce:HYPingLunSubReplyTableViewCell) -> Void{
//        
//        CommonTools.checkLogin(viewController: self, afterLoginCall:nil,callBack: {
//            () -> Void in
//            
//            let v:HYReplyView = HYReplyView(frame: CGRectZero);
//            v.show(inView: self.view, callBack: {
//                (strInput:String!) -> Void in
//                print("input string = \(strInput)");
//                
//                if let experienceID = self.dataModel?.id{
//                    let strParmr:String = "?experienceId=" + experienceID;
//                    print("share id = \(strParmr)");
//                    
//                    if let strID = ce.dataModel.id{
//                        let dic:[String: AnyObject] = ["content":strInput,"reply_comment_id":strID];
//                        //发送评论
//                        AskDogCreateCommentDataRequest().startRequest(requestParmer: dic,exUrl:strParmr,viewController:self,completionHandler: {
//                            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
//                            if(true == beOk){
//                                print("AskDogCreateCommentDataRequest 评论发送成功");
//                                CommonTools.showMessage("评论回复成功!", vc: self, hr: {
//                                    () -> Void in
//                                    
//                                    //要刷新评论列表
//                                    if let returnDic = dic{
//                                        let data:HYPinglunDataModel = HYPinglunDataModel(jsonDic: returnDic);
//                                        //先取出一级评论数组数据
//                                        let nFirsTIndex = ce.indexPath.section - 3;
//                                        let firstCommentData:HYPinglunDataModel = (self.commentsDataModel!.result![nFirsTIndex]);
//                                        if let arrObj = firstCommentData.comments?.result{
//                                            var array:[HYPinglunDataModel] = arrObj as [HYPinglunDataModel];
//                                            array.append(data);
//                                            self.commentsDataModel!.result![nFirsTIndex].comments!.result = array;
//                                        }else{
//                                            var newArr:[HYPinglunDataModel] = [HYPinglunDataModel]();
//                                            newArr.append(data);
//                                            self.commentsDataModel!.result![nFirsTIndex].comments!.result = newArr;
//                                        }
//                                        
//                                       // print("result=\(self.commentsDataModel!.result![nFirsTIndex].comments!.result)");
//                                        
//                                        //判断数量 是否展开 才可以插入
//                                        let count = firstCommentData.comments?.result?.count;
//                                        self.tableView.beginUpdates();
//                                        if(true == firstCommentData.ifOpen){
//                                            //刷新section
//
//
//                                            let ins:NSIndexPath = NSIndexPath(forRow: count!, inSection: ce.indexPath.section);
//                                            
//                                            
//                                            self.tableView.insertRowsAtIndexPaths([ins], withRowAnimation: .Fade);
//                                            
//                                            //self.tableView.reloadSections(NSIndexSet(index: ce.indexPath.section), withRowAnimation: .Fade);
//
//                                        }else{
//                                            if(count <= 2){
//                                                //刷新section
//                                                
//                                                
//                                                let ins:NSIndexPath = NSIndexPath(forRow: count!, inSection: ce.indexPath.section);
//                                                
//                                                self.tableView.insertRowsAtIndexPaths([ins], withRowAnimation: .Fade);
//                                                //self.tableView.reloadSections(NSIndexSet(index: ce.indexPath.section), withRowAnimation: .Fade);
//                                                
//                                            }
//                                        }
//                                        let ins0:NSIndexPath = NSIndexPath(forRow: 0, inSection: ce.indexPath.section);
//                                        self.tableView.reloadRowsAtIndexPaths([ins0], withRowAnimation: .Fade);
//                                        self.tableView.endUpdates();
//                                    }
//                                    
//                                    
//                                });
//                            }
//                        });
//                    }
//                }
//                
//            });
//            
//        });
//
//        
//    }
    
    //MARK:HYInputPingLunTableViewCellDelegate
    func hyInputPingLunTableViewCellReplyBtnClicked(cell ce: HYInputPingLunTableViewCell) {
        
        CommonTools.checkLogin(viewController: self, afterLoginCall:nil,callBack: {
            () -> Void in
            
            if("" == ce.textViewContent.text){
                CommonTools.showMessage("请输入评论!", vc: self, hr: {
                    () -> Void in
                });
                return;
            }
            
            if let experienceID = self.dataModel?.id{
                let strParmr:String = "?experienceId=" + experienceID;
                print("share id = \(strParmr)");
                
                let dic:[String: AnyObject] = ["content":ce.textViewContent.text as AnyObject];
                //发送评论
                AskDogCreateCommentDataRequest().startRequest(requestParmer: dic,exUrl:strParmr,viewController:self,completionHandler: {
                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                    if(true == beOk){
                        print("AskDogCreateCommentDataRequest 评论发送成功");
                        CommonTools.showMessage("评论回复成功!", vc: self, hr: {
                            () -> Void in
                            
                            //要刷新评论列表
                            if let returnDic = dic{
                                let data:HYPinglunDataModel = HYPinglunDataModel(jsonDic: returnDic);
                                
                                if let cmsModeal:HYPinglunResultDataModel = self.commentsDataModel{
                                    if (0 != cmsModeal.result!.count){
                                        
                                        self.commentsDataModel!.result!.insert(data, at: 0);
                                    }else{
                                        var newArr:[HYPinglunDataModel] = [HYPinglunDataModel]();
                                        newArr.append(data);
                                        self.commentsDataModel!.result! = newArr;
                                    }
                                }
                                
                                print("self.commentsDataModel.result=\(self.commentsDataModel!.result)");
                                
                                //刷新section
                                self.tableView.beginUpdates();
                                self.tableView.insertSections(IndexSet(integer: 3), with: .fade);
                                self.tableView.endUpdates();
                                
                                ce.textViewContent.text = "";
                                
                            }
                            
                            
                        });
                    }
                });
            }
            
        });
        
        
        
        //        if(false == Global.sharedInstance.isLogined){
        //            let login:HYLoginViewController = HYLoginViewController(nibName: "HYLoginViewController", bundle: NSBundle.mainBundle());
        //            login.callBackLoginedFun = {
        //                (beOK:Bool)-> Void in
        //                print("temp login success");
        //            };
        //
        //            login.callWeixinFun = {
        //                (beOK:Bool)-> Void in
        //                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
        //                self.navigationController?.pushViewController(vc, animated: true);
        //            };
        //            login.callQQFun = {
        //                (beOK:Bool)-> Void in
        //                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
        //                self.navigationController?.pushViewController(vc, animated: true);
        //            };
        //            login.callWeiboFun = {
        //                (beOK:Bool)-> Void in
        //                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
        //                self.navigationController?.pushViewController(vc, animated: true);
        //            };
        //
        //            login.callBackFunForgetPwd = {
        //                (beOK:Bool)-> Void in
        //                if(true == beOK){
        //                    let vc:HYForgetPasswordViewController = HYForgetPasswordViewController(nibName: "HYForgetPasswordViewController", bundle: NSBundle.mainBundle());
        //                    self.navigationController?.pushViewController(vc, animated: true);
        //                }
        //            };
        //
        //            login.reloadLoginedHomePageDataCallBackFun = {
        //                (beOK:Bool)-> Void in
        //                if(true == beOK){
        //                    self.shouldUpdateData();
        //                }
        //            };
        //
        //            login.callBackGoCategories = {
        //                (beOK:Bool)-> Void in
        //                if(true == beOK){
        //                    AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
        //                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
        //                        if(true == beOk){
        //
        //                            var array:[HYCategoryDataModel] = [HYCategoryDataModel]();
        //
        //                            for item in arr!{
        //                                if let obj:AnyObject = item{
        //                                    let dic:NSDictionary = obj as! NSDictionary;
        //                                    let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
        //                                    array.append(data);
        //                                }
        //                            }
        //
        //
        //                            let vc:HYDingyuePindaoViewController = HYDingyuePindaoViewController(nibName: "HYDingyuePindaoViewController", bundle: NSBundle.mainBundle());
        //                            vc.dataModelArray = array;
        //                            self.navigationController?.pushViewController(vc, animated: true);
        //                        }
        //                    });
        //                }
        //            }
        //
        //            //   login.callBackGoXieyi = {
        //            //       (beOK:Bool)-> Void in
        //            //       if(true == beOK){
        //            //           let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: NSBundle.mainBundle());
        //            //           vc.setReturnTyle(UserAgreeMentReturnStyle.UserAgreeMentReturnStyle_pop_animated);
        //            //           self.navigationController?.pushViewController(vc, animated: true);
        //            //           //self.presentViewController(vc, animated: true, completion: nil);
        //            //       }
        //            //   }
        //
        //            self.presentViewController(login, animated: true, completion: nil);
        //        }else{
        //            if let experienceID = self.dataModel?.id{
        //                let strParmr:String = "?experienceId=" + experienceID;
        //                print("share id = \(strParmr)");
        //
        //                let dic:[String: AnyObject] = ["content":ce.textViewContent.text];
        //                //发送评论
        //                AskDogCreateCommentDataRequest().startRequest(requestParmer: dic,exUrl:strParmr,viewController:self,completionHandler: {
        //                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
        //                    if(true == beOk){
        //                        print("AskDogCreateCommentDataRequest 评论发送成功");
        //                        CommonTools.showMessage("评论回复成功!", vc: self, hr: {
        //                            () -> Void in
        //
        //                            //要刷新评论列表
        //                            if let returnDic = dic{
        //                                let data:HYPinglunDataModel = HYPinglunDataModel(jsonDic: returnDic);
        //
        //                                if let cmsModeal:HYPinglunResultDataModel = self.commentsDataModel{
        //                                    if (0 != cmsModeal.result!.count){
        //
        //                                        self.commentsDataModel!.result!.insert(data, atIndex: 0);
        //                                    }else{
        //                                        var newArr:[HYPinglunDataModel] = [HYPinglunDataModel]();
        //                                        newArr.append(data);
        //                                        self.commentsDataModel!.result! = newArr;
        //                                    }
        //                                }
        //
        //                                print("self.commentsDataModel.result=\(self.commentsDataModel!.result)");
        //
        //                                //刷新section
        //                                self.tableView.beginUpdates();
        //                                self.tableView.insertSections(NSIndexSet(index: 3), withRowAnimation: .Fade);
        //                                self.tableView.endUpdates();
        //
        //                                ce.textViewContent.text = "";
        //
        //                            }
        //
        //
        //                        });
        //                    }
        //                });
        //            }
        //        }
    }
    
    override func isShowLoginBtn() -> Bool {
        return false;
    }
    
    override func btnReturnClicked(_ sender: UIButton) {
        
        
        if(true == isPopToRoot){
            _ = self.navigationController?.popToRootViewController(animated: true);
        }else{
            _ = self.navigationController?.popViewController(animated: true);
        }
    }
    
    //关于旋转
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated);
        
        if(true == self.isPushFromSharedVideoView){
            self.navigationController?.interactivePopGestureRecognizer?.isEnabled = false;
        }
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated);
        if(true == self.isVideo){
            appDelegate.blockRotation = true;
        }
        
        //        if("VIDEO" == dataModel!.content?.type){
        //            appDelegate.blockRotation = true;
        //            let value = UIInterfaceOrientation.LandscapeLeft.rawValue | UIInterfaceOrientation.LandscapeRight.rawValue;
        //            UIDevice.currentDevice().setValue(value, forKey: "orientation");
        //        }
        
        
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillAppear(animated);
        
        if(true == self.isPushFromSharedVideoView){
            self.navigationController?.interactivePopGestureRecognizer?.isEnabled = true;
        }
        
        if(true == self.isVideo){
            self.videoCell.videoPause();
        }
        appDelegate.blockRotation = false;
        
        //        if("VIDEO" == dataModel!.content?.type){
        //            appDelegate.blockRotation = false;
        //            let value = UIInterfaceOrientation.Portrait.rawValue;
        //            UIDevice.currentDevice().setValue(value, forKey: "orientation");
        //        }
        
    }
    
    override var shouldAutorotate : Bool {
        return false;
    }
    
    
    
    override func willRotate(to toInterfaceOrientation: UIInterfaceOrientation, duration: TimeInterval) {
        
        //        let cell:UITableViewCell? = tableView.cellForRowAtIndexPath(NSIndexPath(forRow: 0, inSection: 0));
        //
        //        if let ce = cell{
        //
        //            let temp:HYContentListTopVideoTableViewCell = ce as! HYContentListTopVideoTableViewCell;
        //
        //            if(toInterfaceOrientation == .LandscapeLeft || toInterfaceOrientation == .LandscapeRight){
        //                temp.bmPlayer.goFullScreen(true);
        //            }else{
        //                temp.bmPlayer.goFullScreen(false);
        //            }
        //        }
        
        
        
        if(toInterfaceOrientation == .landscapeLeft || toInterfaceOrientation == .landscapeRight){
            isFullScreenModel = true;
            videoCell.bmPlayer.goFullScreen(true);
            videoCell.bmPlayer.setBackBtnState(beShow: true);
        }else{
            isFullScreenModel = false;
            videoCell.bmPlayer.goFullScreen(false);
            videoCell.bmPlayer.setBackBtnState(beShow: false);
        }
    }
    
    override var prefersStatusBarHidden: Bool{
        return self.isFullScreenModel;
    }
    
    deinit {
        
        print("VideoPlayViewController Deinit")
        
    }
    
    override func shouldUpdateData() {
        
        if let idObj:AnyObject = dataModel!.id as AnyObject?{
            let id:String = idObj as! String;
            //let dic:[String: AnyObject]? = ["experienceId":id];
            let strParmr:String = "/" + id;
            print("share id = \(strParmr)");
            
            
            AskDogGetExperienceDetailDataRequest().startRequest(exUrl: strParmr, viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    
                    //let data:HYPinglunDataModel = self.makePinglunDataModel(dic!);
                    
                    let data:HYExperienceDetailDataModel = HYExperienceDetailDataModel(jsonDic: dic!);
                    self.dataModel = data;
                    //self.setDetailDataModel(data);
                    self.tableView.reloadData();
                }
                //                else{
                //                    CommonTools.showErrorMessage(inViewController: self, error: error, handleMethod: {
                //                        ()->Void in
                //
                //                    });
                //                }
            });
        }
    }
    
    func goUserSpace() -> Void {
        if let d:HYAuthorDataModel = dataModel?.author{
            let vc:HYOtherUserChannelInfoHomeViewController = HYOtherUserChannelInfoHomeViewController(nibName: "HYOtherUserChannelInfoHomeViewController", bundle: Bundle.main, data: d,loadType:1);
            self.navigationController?.pushViewController(vc, animated: true);
        }
    }
    
    func doReport() -> Void {
        
//        CommonTools.checkLogin(viewController: self, afterLoginCall:self.checkExperienceDetailStatus,callBack: {
//            () -> Void in
        
            let vc:HYReportExperienceViewController = HYReportExperienceViewController(nibName: "HYReportExperienceViewController", bundle: Bundle.main);
            vc.experiencesId = self.dataModel?.id;
            self.navigationController?.pushViewController(vc, animated: true);
            
//        });
        
        
        //        if(false == Global.sharedInstance.isLogined){
        //            let login:HYLoginViewController = HYLoginViewController(nibName: "HYLoginViewController", bundle: NSBundle.mainBundle());
        //            login.callBackLoginedFun = {
        //                (beOK:Bool)-> Void in
        //                print("temp login success");
        //            };
        //
        //            login.callWeixinFun = {
        //                (beOK:Bool)-> Void in
        //                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
        //                self.navigationController?.pushViewController(vc, animated: true);
        //            };
        //            login.callQQFun = {
        //                (beOK:Bool)-> Void in
        //                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
        //                self.navigationController?.pushViewController(vc, animated: true);
        //            };
        //            login.callWeiboFun = {
        //                (beOK:Bool)-> Void in
        //                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
        //                self.navigationController?.pushViewController(vc, animated: true);
        //            };
        //
        //            login.callBackFunForgetPwd = {
        //                (beOK:Bool)-> Void in
        //                if(true == beOK){
        //                    let vc:HYForgetPasswordViewController = HYForgetPasswordViewController(nibName: "HYForgetPasswordViewController", bundle: NSBundle.mainBundle());
        //                    self.navigationController?.pushViewController(vc, animated: true);
        //                }
        //            };
        //
        //            login.reloadLoginedHomePageDataCallBackFun = {
        //                (beOK:Bool)-> Void in
        //                if(true == beOK){
        //                    self.shouldUpdateData();
        //                }
        //            };
        //
        //            login.callBackGoCategories = {
        //                (beOK:Bool)-> Void in
        //                if(true == beOK){
        //                    AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
        //                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
        //                        if(true == beOk){
        //
        //                            var array:[HYCategoryDataModel] = [HYCategoryDataModel]();
        //
        //                            for item in arr!{
        //                                if let obj:AnyObject = item{
        //                                    let dic:NSDictionary = obj as! NSDictionary;
        //                                    let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
        //                                    array.append(data);
        //                                }
        //                            }
        //
        //
        //                            let vc:HYDingyuePindaoViewController = HYDingyuePindaoViewController(nibName: "HYDingyuePindaoViewController", bundle: NSBundle.mainBundle());
        //                            vc.dataModelArray = array;
        //                            self.navigationController?.pushViewController(vc, animated: true);
        //                        }
        //                    });
        //                }
        //            }
        //
        //            //   login.callBackGoXieyi = {
        //            //       (beOK:Bool)-> Void in
        //            //       if(true == beOK){
        //            //           let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: NSBundle.mainBundle());
        //            //           vc.setReturnTyle(UserAgreeMentReturnStyle.UserAgreeMentReturnStyle_pop_animated);
        //            //           self.navigationController?.pushViewController(vc, animated: true);
        //            //           //self.presentViewController(vc, animated: true, completion: nil);
        //            //       }
        //            //   }
        //
        //            self.presentViewController(login, animated: true, completion: nil);
        //        }else{
        //            let vc:HYReportExperienceViewController = HYReportExperienceViewController(nibName: "HYReportExperienceViewController", bundle: NSBundle.mainBundle());
        //            vc.experiencesId = dataModel?.id;
        //            self.navigationController?.pushViewController(vc, animated: true);
        //        }
    }
    
    // 0 图文  1 视频
    func shareToWeixin(_ nType:Int) -> Void {
        CommonTools.checkLogin(viewController: self,afterLoginCall:self.checkExperienceDetailStatus, callBack: {
            () -> Void in
            
            let v:HYShareView = HYShareView(frame: CGRect.zero);
            
            v.show(inView: self.view, callBack: {
                (obj) in
                
                let index:Int = obj as! Int;
                
                if(0 == index){
                    //QQ
                    self.shareToQQ(0)
                    
                }else if(1 == index){
                    //微信
                    print("go weixin timeline share");
                    // 0 图文  1 视频
                    self.doShare(1,type: nType);
                    
                }else if(2 == index){
                    //微信好友
                    self.doShare(0,type: nType);
                }else{
                    //微博
                    self.shareToWB();
                }
                
            });
            
//            v.show(inView: self.view, callBack: {
//                (obj:AnyObject) -> Void in
//                
//                let index:Int = obj as! Int;
//                
//                if(0 == index){
//                    //QQ
//                    self.shareToQQ(0)
//                    
//                }else if(1 == index){
//                    //微信
//                    print("go weixin timeline share");
//                    // 0 图文  1 视频
//                    self.doShare(1,type: nType);
//                    
//                }else if(2 == index){
//                    //微信好友
//                    self.doShare(0,type: nType);
//                }else{
//                    //微博
//                    self.shareToWB();
//                }
//                
//            });
            
        });
    }
    
    //scent 0好友1朋友圈
    //type  0 图文  1 视频
    func doShare(_ scent:Int32,type:Int) -> Void {
        
        if let id:String = self.dataModel?.id{
            var des:String = "";
            if(0 == type){
                des = "图文来自ASKDOG经验分享社区";
            }else{
                des = "视频来自ASKDOG经验分享社区";
            }
            let img:UIImage!
            
            if let thumbnail:String = self.dataModel?.thumbnail{
                img = UIImage(data: try! Data(contentsOf: URL(string: thumbnail)!))!;
            }else{
                var category_img_name:String = "";
                if let category_code = self.dataModel!.category?.code{
                    let strUrl:String = "http://pic.askdog.cn/resources/category/thumbnail/abstract/\(category_code.lowercased()).png@312w_176h_1e_1c.png";
                    category_img_name = strUrl;
                    
                    img = UIImage(data: try! Data(contentsOf: URL(string: category_img_name)!))!;
                    
                    //
                    //                    if let name:String = CATEGORY_IMG_DIC[category_code]{
                    //                        category_img_name = name;
                    //                    }else{
                    //                        category_img_name = "channelDefault";
                    //                    }
                }else{
                    img = UIImage(named: "channelDefault");
                }
                
                // img = UIImage(named: category_img_name);
            }
            
            let url:String = "http://\(REQUEST_BASE_URL_SHARED)/#/exp/\(id)";
            print("share link = \(url)");
            
            HYWXApiManager.sharedInstance.shareToWeixin(linkUrl:url, title: (self.dataModel?.subject)!, Description: des, thumbImage: img,
                                                        shareScene:scent,callBack: {
                                                            (beOK:Bool?,respone:SendMessageToWXResp?,needInstallWX:Bool?) -> Void in
                                                            
                                                            if(true == needInstallWX){
                                                                CommonTools.showModalMessage("未安装微信，或当前微信版本过低，请安装最新版的微信！",
                                                                    title: "注意", vc: self, hasCancelBtn: true,OKBtnTitle:"去安装",CancelBtnTitle:"算了",hr: {
                                                                        (beOK:Bool) -> Void in
                                                                        if(beOK == true){
                                                                            let url:URL = HYWXApiManager.sharedInstance.getWeixinAppstoreUrl();
                                                                            UIApplication.shared.openURL(url);
                                                                        }
                                                                });
                                                            }else{
                                                                if(true == beOK){
                                                                    if(0 == respone?.errCode){
                                                                        CommonTools.showMessage("分享成功！", vc: self, hr: {
                                                                            () -> Void in
                                                                            
                                                                        });
                                                                    }
                                                                    
                                                                }
                                                            }
                                                            
            });
        }
    }
    
    func shareToQQ(_ scent:Int32) -> Void {
        //判断是否安装QQApp
        if HYQQApiManager.sharedInstance.userInstalledQQApp() == false {
            CommonTools.showModalMessage("未安装QQApp，或当前QQ版本过低，请安装最新版的QQ！",
                                         title: "注意", vc: self, hasCancelBtn: true,OKBtnTitle:"去安装",CancelBtnTitle:"算了",hr: {
                                            (beOK:Bool) -> Void in
                                            if(beOK == true){
                                                let url:URL = HYQQApiManager.sharedInstance.getQQDowmUrl()
                                                UIApplication.shared.openURL(url);
                                            }
            });
            
            return
        }
        
        
        if let id:String = self.dataModel?.id{
            let title: String = self.dataModel!.subject!
            let imageUrl: String = self.dataModel!.thumbnail!
            let description: String = "图文来自于ASKDOG经验分享社区"
            let url:String = "http://\(REQUEST_BASE_URL_SHARED)/#/exp/\(id)";
            print("share link = \(url)");
            
            if scent == 0 {
                //分享到空间
                HYQQApiManager.sharedInstance.tencentShareRequest(true , url: url, imageUrl: imageUrl, title: title, description: description, shareBlock: { (beOK, shareMessage) in
                    
                    print("QQ空间分享\(shareMessage)")
                    
                })
                
            } else {
                //好友
                HYQQApiManager.sharedInstance.tencentShareRequest(false , url: url, imageUrl: imageUrl, title: title, description: description, shareBlock: { (beOK, shareMessage) in
                    
                    print("QQ好友分享\(shareMessage)")
                })
            }
            
        }
    }
    
    func shareToWB() -> Void  {
        //判断是否安装微博App
        if HYWBApiManager.sharedInstance.userInstallationSinaApp() == false {
            CommonTools.showModalMessage("未安装新浪微博App，或当前新浪微博版本过低，请安装最新版的新浪微博！",
                                         title: "注意", vc: self, hasCancelBtn: true,OKBtnTitle:"去安装",CancelBtnTitle:"算了",hr: {
                                            (beOK:Bool) -> Void in
                                            if(beOK == true){
                                                let url:URL = HYWBApiManager.sharedInstance.getSinaDownUrl()
                                                UIApplication.shared.openURL(url);
                                            }
            });
            
            return
        }
        
        if let id:String = self.dataModel?.id{
            var title  = ""
            var imageUrl = ""
            title = self.dataModel!.subject!
            if let img: String = self.dataModel!.thumbnail {
                imageUrl = img
            }
            let description: String = "图文来自于ASKDOG经验分享社区"
            let url:String = "http://\(REQUEST_BASE_URL_SHARED)/#/exp/\(id)";
            let userInfo = ["": ""]
            print("share link = \(url)");
            
            HYWBApiManager.sharedInstance.weShareRequset(userInfo as NSDictionary, objectID: id, title: title, description: description, imgUrl:imageUrl, webpageUrl: url, shareBlock: { (beOK, response) in
                if beOK == true {
                    print("sina分享成功")
                    
                } else {
                    print("sina分享失败")
                    
                }
            })
        }
    }
    
    
    //MARK:HYContenListTopTableViewCellDelegate
    func hyContenListTopTableViewCellShareBtnClicked(cell ce: HYContenListTopTableViewCell, sectionIndex section: Int) {
        print("hyContenListTopTableViewCellShareBtnClicked in ");
        
        self.shareToWeixin(0);
    }
    
    func hyContenListTopTableViewCellHeaderBtnClicked(cell ce: HYContenListTopTableViewCell, sectionIndex section: Int) {
        print("hyContenListTopTableViewCellHeaderBtnClicked");
        
        self.goUserSpace();
        
        
    }
    func hyContenListTopTableViewCellReportBtnClicked(cell ce: HYContenListTopTableViewCell, sectionIndex section: Int) {
        self.doReport();
    }
    
    func hyContenListTopTableViewCellWebViewReachBottom(cell ce: HYContenListTopTableViewCell, sectionIndex section: Int) {
        if(false == isUpWebView){
            //isUpWebView = true;
            //tableView.scrollToRowAtIndexPath(NSIndexPath(forRow: 0, inSection: 1), atScrollPosition: .Middle, animated: true);
        }
    }
    
    func hyContenListTopTableViewCellWebViewDidFinishedLoad(cell ce: HYContenListTopTableViewCell, sectionIndex section: Int, height h: CGFloat) {
        
        //let strHeight:String = ce.webView.stringByEvaluatingJavaScriptFromString("document.body.scrollHeight")!;
        //let h1 = Int(strHeight);
        //self.contentCellHeight = CGFloat(h1!);
        // self.contentCellHeight = ce.webView.scrollView.contentSize.height;
        // tableView.reloadSections(NSIndexSet(index: 0), withRowAnimation: .Fade);
        
    }
    
    func hyContenListTopTableViewCellZanBtnClicked(cell ce:HYContenListTopTableViewCell,sectionIndex section:Int) -> Void{
        if(false == ce.btnZan.isSelected){
            
            CommonTools.checkLogin(viewController: self, afterLoginCall:nil,callBack: {
                () -> Void in
                
                if let strID:String = self.dataModel!.id{
                    
                    let strExUrl:String = "/\(strID)/vote";
                    
                    let dic:[String: AnyObject]? = ["direction":"UP" as AnyObject];
                    
                    print("strExUrl=\(strExUrl)");
                    
                    AskDogVoteDataRequest().startRequest(requestParmer: dic, exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            ce.setZanCaiDic(dic!);
                            print("AskDogVoteDataRequest  ok");
                        }else{
                            
                        }
                    });
                }
                
            });
            
            //            if(false == Global.sharedInstance.isLogined){
            //                let login:HYLoginViewController = HYLoginViewController(nibName: "HYLoginViewController", bundle: NSBundle.mainBundle());
            //                        login.callBackLoginedFun = {
            //                            (beOK:Bool)-> Void in
            //                            print("temp login success");
            //                        };
            //
            //                login.callWeixinFun = {
            //                    (beOK:Bool)-> Void in
            //                    let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
            //                    self.navigationController?.pushViewController(vc, animated: true);
            //                };
            //                login.callQQFun = {
            //                    (beOK:Bool)-> Void in
            //                    let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
            //                    self.navigationController?.pushViewController(vc, animated: true);
            //                };
            //                login.callWeiboFun = {
            //                    (beOK:Bool)-> Void in
            //                    let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
            //                    self.navigationController?.pushViewController(vc, animated: true);
            //                };
            //
            //                login.callBackFunForgetPwd = {
            //                    (beOK:Bool)-> Void in
            //                    if(true == beOK){
            //                        let vc:HYForgetPasswordViewController = HYForgetPasswordViewController(nibName: "HYForgetPasswordViewController", bundle: NSBundle.mainBundle());
            //                        self.navigationController?.pushViewController(vc, animated: true);
            //                    }
            //                };
            //
            //                login.reloadLoginedHomePageDataCallBackFun = {
            //                    (beOK:Bool)-> Void in
            //                    if(true == beOK){
            //                        self.shouldUpdateData();
            //                    }
            //                };
            //
            //                login.callBackGoCategories = {
            //                    (beOK:Bool)-> Void in
            //                    if(true == beOK){
            //                        AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
            //                            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            //                            if(true == beOk){
            //
            //                                var array:[HYCategoryDataModel] = [HYCategoryDataModel]();
            //
            //                                for item in arr!{
            //                                    if let obj:AnyObject = item{
            //                                        let dic:NSDictionary = obj as! NSDictionary;
            //                                        let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
            //                                        array.append(data);
            //                                    }
            //                                }
            //
            //
            //                                let vc:HYDingyuePindaoViewController = HYDingyuePindaoViewController(nibName: "HYDingyuePindaoViewController", bundle: NSBundle.mainBundle());
            //                                vc.dataModelArray = array;
            //                                self.navigationController?.pushViewController(vc, animated: true);
            //                            }
            //                        });
            //                    }
            //                }
            //
            //                //   login.callBackGoXieyi = {
            //                //       (beOK:Bool)-> Void in
            //                //       if(true == beOK){
            //                //           let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: NSBundle.mainBundle());
            //                //           vc.setReturnTyle(UserAgreeMentReturnStyle.UserAgreeMentReturnStyle_pop_animated);
            //                //           self.navigationController?.pushViewController(vc, animated: true);
            //                //           //self.presentViewController(vc, animated: true, completion: nil);
            //                //       }
            //                //   }
            //
            //                self.presentViewController(login, animated: true, completion: nil);
            //            }else{
            //                if let strID:String = dataModel!.id{
            //
            //                    let strExUrl:String = "/\(strID)/vote";
            //
            //                    let dic:[String: AnyObject]? = ["direction":"UP"];
            //
            //                    print("strExUrl=\(strExUrl)");
            //
            //                    AskDogVoteDataRequest().startRequest(requestParmer: dic, exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
            //                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            //                        if(true == beOk){
            //                            ce.setZanCaiDic(dic!);
            //                            print("AskDogVoteDataRequest  ok");
            //                        }else{
            //
            //                        }
            //                    });
            //                }
            //            }
        }else{
            //取消赞
            
            CommonTools.checkLogin(viewController: self, afterLoginCall:nil,callBack: {
                () -> Void in
                
                if let strID:String = self.dataModel!.id{
                    
                    let strExUrl:String = "/\(strID)/vote";
                    
                    //let dic:[String: AnyObject]? = ["direction":"UP"];
                    
                    print("strExUrl=\(strExUrl)");
                    
                    AskDogCancelVoteDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            ce.setZanCaiDic(dic!);
                            print("AskDogCancelVoteDataRequest  ok");
                        }else{
                            
                        }
                    });
                }
                
            });
            
            
            //            if(false == Global.sharedInstance.isLogined){
            //                let login:HYLoginViewController = HYLoginViewController(nibName: "HYLoginViewController", bundle: NSBundle.mainBundle());
            //                login.callBackLoginedFun = {
            //                    (beOK:Bool)-> Void in
            //                    print("temp login success");
            //                };
            //
            //                login.callWeixinFun = {
            //                    (beOK:Bool)-> Void in
            //                    let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
            //                    self.navigationController?.pushViewController(vc, animated: true);
            //                };
            //                login.callQQFun = {
            //                    (beOK:Bool)-> Void in
            //                    let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
            //                    self.navigationController?.pushViewController(vc, animated: true);
            //                };
            //                login.callWeiboFun = {
            //                    (beOK:Bool)-> Void in
            //                    let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
            //                    self.navigationController?.pushViewController(vc, animated: true);
            //                };
            //
            //                login.callBackFunForgetPwd = {
            //                    (beOK:Bool)-> Void in
            //                    if(true == beOK){
            //                        let vc:HYForgetPasswordViewController = HYForgetPasswordViewController(nibName: "HYForgetPasswordViewController", bundle: NSBundle.mainBundle());
            //                        self.navigationController?.pushViewController(vc, animated: true);
            //                    }
            //                };
            //
            //                login.reloadLoginedHomePageDataCallBackFun = {
            //                    (beOK:Bool)-> Void in
            //                    if(true == beOK){
            //                        self.shouldUpdateData();
            //                    }
            //                };
            //
            //                login.callBackGoCategories = {
            //                    (beOK:Bool)-> Void in
            //                    if(true == beOK){
            //                        AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
            //                            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            //                            if(true == beOk){
            //
            //                                var array:[HYCategoryDataModel] = [HYCategoryDataModel]();
            //
            //                                for item in arr!{
            //                                    if let obj:AnyObject = item{
            //                                        let dic:NSDictionary = obj as! NSDictionary;
            //                                        let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
            //                                        array.append(data);
            //                                    }
            //                                }
            //
            //
            //                                let vc:HYDingyuePindaoViewController = HYDingyuePindaoViewController(nibName: "HYDingyuePindaoViewController", bundle: NSBundle.mainBundle());
            //                                vc.dataModelArray = array;
            //                                self.navigationController?.pushViewController(vc, animated: true);
            //                            }
            //                        });
            //                    }
            //                }
            //
            //                //   login.callBackGoXieyi = {
            //                //       (beOK:Bool)-> Void in
            //                //       if(true == beOK){
            //                //           let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: NSBundle.mainBundle());
            //                //           vc.setReturnTyle(UserAgreeMentReturnStyle.UserAgreeMentReturnStyle_pop_animated);
            //                //           self.navigationController?.pushViewController(vc, animated: true);
            //                //           //self.presentViewController(vc, animated: true, completion: nil);
            //                //       }
            //                //   }
            //
            //                self.presentViewController(login, animated: true, completion: nil);
            //            }else{
            //                if let strID:String = dataModel!.id{
            //
            //                    let strExUrl:String = "/\(strID)/vote";
            //
            //                    //let dic:[String: AnyObject]? = ["direction":"UP"];
            //
            //                    print("strExUrl=\(strExUrl)");
            //
            //                    AskDogCancelVoteDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
            //                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            //                        if(true == beOk){
            //                            ce.setZanCaiDic(dic!);
            //                            print("AskDogCancelVoteDataRequest  ok");
            //                        }else{
            //
            //                        }
            //                    });
            //                }
            //            }
        }
    }
    func hyContenListTopTableViewCellCaiBtnClicked(cell ce:HYContenListTopTableViewCell,sectionIndex section:Int) -> Void{
        if(false == ce.btnCai.isSelected){
            
            CommonTools.checkLogin(viewController: self, afterLoginCall:nil,callBack: {
                () -> Void in
                
                if let strID:String = self.dataModel!.id{
                    
                    let strExUrl:String = "/\(strID)/vote";
                    
                    let dic:[String: AnyObject]? = ["direction":"DOWN" as AnyObject];
                    
                    print("strExUrl=\(strExUrl)");
                    
                    AskDogVoteDataRequest().startRequest(requestParmer: dic, exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            ce.setZanCaiDic(dic!);
                            print("AskDogVoteDataRequest  ok");
                        }else{
                            
                        }
                    });
                }
                
            });
            
            //            if(false == Global.sharedInstance.isLogined){
            //                let login:HYLoginViewController = HYLoginViewController(nibName: "HYLoginViewController", bundle: NSBundle.mainBundle());
            //                login.callBackLoginedFun = {
            //                    (beOK:Bool)-> Void in
            //                    print("temp login success");
            //                };
            //
            //                login.callBackFunForgetPwd = {
            //                    (beOK:Bool)-> Void in
            //                    if(true == beOK){
            //                        let vc:HYForgetPasswordViewController = HYForgetPasswordViewController(nibName: "HYForgetPasswordViewController", bundle: NSBundle.mainBundle());
            //                        self.navigationController?.pushViewController(vc, animated: true);
            //                    }
            //                };
            //
            //                login.callWeixinFun = {
            //                    (beOK:Bool)-> Void in
            //                    let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
            //                    self.navigationController?.pushViewController(vc, animated: true);
            //                };
            //                login.callQQFun = {
            //                    (beOK:Bool)-> Void in
            //                    let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
            //                    self.navigationController?.pushViewController(vc, animated: true);
            //                };
            //                login.callWeiboFun = {
            //                    (beOK:Bool)-> Void in
            //                    let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
            //                    self.navigationController?.pushViewController(vc, animated: true);
            //                };
            //
            //                login.reloadLoginedHomePageDataCallBackFun = {
            //                    (beOK:Bool)-> Void in
            //                    if(true == beOK){
            //                        self.shouldUpdateData();
            //                    }
            //                };
            //
            //                login.callBackGoCategories = {
            //                    (beOK:Bool)-> Void in
            //                    if(true == beOK){
            //                        AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
            //                            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            //                            if(true == beOk){
            //
            //                                var array:[HYCategoryDataModel] = [HYCategoryDataModel]();
            //
            //                                for item in arr!{
            //                                    if let obj:AnyObject = item{
            //                                        let dic:NSDictionary = obj as! NSDictionary;
            //                                        let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
            //                                        array.append(data);
            //                                    }
            //                                }
            //
            //
            //                                let vc:HYDingyuePindaoViewController = HYDingyuePindaoViewController(nibName: "HYDingyuePindaoViewController", bundle: NSBundle.mainBundle());
            //                                vc.dataModelArray = array;
            //                                self.navigationController?.pushViewController(vc, animated: true);
            //                            }
            //                        });
            //                    }
            //                }
            //
            //                //   login.callBackGoXieyi = {
            //                //       (beOK:Bool)-> Void in
            //                //       if(true == beOK){
            //                //           let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: NSBundle.mainBundle());
            //                //           vc.setReturnTyle(UserAgreeMentReturnStyle.UserAgreeMentReturnStyle_pop_animated);
            //                //           self.navigationController?.pushViewController(vc, animated: true);
            //                //           //self.presentViewController(vc, animated: true, completion: nil);
            //                //       }
            //                //   }
            //
            //                self.presentViewController(login, animated: true, completion: nil);
            //            }else{
            //                if let strID:String = dataModel!.id{
            //
            //                    let strExUrl:String = "/\(strID)/vote";
            //
            //                    let dic:[String: AnyObject]? = ["direction":"DOWN"];
            //
            //                    print("strExUrl=\(strExUrl)");
            //
            //                    AskDogVoteDataRequest().startRequest(requestParmer: dic, exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
            //                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            //                        if(true == beOk){
            //                            ce.setZanCaiDic(dic!);
            //                            print("AskDogVoteDataRequest  ok");
            //                        }else{
            //
            //                        }
            //                    });
            //                }
            //            }
        }else{
            //取消赞
            
            CommonTools.checkLogin(viewController: self,afterLoginCall:nil, callBack: {
                () -> Void in
                
                if let strID:String = self.dataModel!.id{
                    
                    let strExUrl:String = "/\(strID)/vote";
                    
                    //let dic:[String: AnyObject]? = ["direction":"UP"];
                    
                    print("strExUrl=\(strExUrl)");
                    
                    AskDogCancelVoteDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            ce.setZanCaiDic(dic!);
                            print("AskDogCancelVoteDataRequest  ok");
                        }else{
                            
                        }
                    });
                }
                
            });
            
            
            //            if(false == Global.sharedInstance.isLogined){
            //                let login:HYLoginViewController = HYLoginViewController(nibName: "HYLoginViewController", bundle: NSBundle.mainBundle());
            //                login.callBackLoginedFun = {
            //                    (beOK:Bool)-> Void in
            //                    print("temp login success");
            //                };
            //
            //                login.callWeixinFun = {
            //                    (beOK:Bool)-> Void in
            //                    let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
            //                    self.navigationController?.pushViewController(vc, animated: true);
            //                };
            //                login.callQQFun = {
            //                    (beOK:Bool)-> Void in
            //                    let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
            //                    self.navigationController?.pushViewController(vc, animated: true);
            //                };
            //                login.callWeiboFun = {
            //                    (beOK:Bool)-> Void in
            //                    let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
            //                    self.navigationController?.pushViewController(vc, animated: true);
            //                };
            //
            //                login.callBackFunForgetPwd = {
            //                    (beOK:Bool)-> Void in
            //                    if(true == beOK){
            //                        let vc:HYForgetPasswordViewController = HYForgetPasswordViewController(nibName: "HYForgetPasswordViewController", bundle: NSBundle.mainBundle());
            //                        self.navigationController?.pushViewController(vc, animated: true);
            //                    }
            //                };
            //
            //                login.reloadLoginedHomePageDataCallBackFun = {
            //                    (beOK:Bool)-> Void in
            //                    if(true == beOK){
            //                        self.shouldUpdateData();
            //                    }
            //                };
            //
            //                login.callBackGoCategories = {
            //                    (beOK:Bool)-> Void in
            //                    if(true == beOK){
            //                        AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
            //                            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            //                            if(true == beOk){
            //
            //                                var array:[HYCategoryDataModel] = [HYCategoryDataModel]();
            //
            //                                for item in arr!{
            //                                    if let obj:AnyObject = item{
            //                                        let dic:NSDictionary = obj as! NSDictionary;
            //                                        let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
            //                                        array.append(data);
            //                                    }
            //                                }
            //
            //
            //                                let vc:HYDingyuePindaoViewController = HYDingyuePindaoViewController(nibName: "HYDingyuePindaoViewController", bundle: NSBundle.mainBundle());
            //                                vc.dataModelArray = array;
            //                                self.navigationController?.pushViewController(vc, animated: true);
            //                            }
            //                        });
            //                    }
            //                }
            //
            //                //   login.callBackGoXieyi = {
            //                //       (beOK:Bool)-> Void in
            //                //       if(true == beOK){
            //                //           let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: NSBundle.mainBundle());
            //                //           vc.setReturnTyle(UserAgreeMentReturnStyle.UserAgreeMentReturnStyle_pop_animated);
            //                //           self.navigationController?.pushViewController(vc, animated: true);
            //                //           //self.presentViewController(vc, animated: true, completion: nil);
            //                //       }
            //                //   }
            //
            //                self.presentViewController(login, animated: true, completion: nil);
            //            }else{
            //                if let strID:String = dataModel!.id{
            //
            //                    let strExUrl:String = "/\(strID)/vote";
            //
            //                    //let dic:[String: AnyObject]? = ["direction":"UP"];
            //
            //                    print("strExUrl=\(strExUrl)");
            //
            //                    AskDogCancelVoteDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
            //                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            //                        if(true == beOk){
            //                            ce.setZanCaiDic(dic!);
            //                            print("AskDogCancelVoteDataRequest  ok");
            //                        }else{
            //
            //                        }
            //                    });
            //                }
            //            }
        }
    }
    
    func hyContenListTopTableViewCellSubscriptionBtnClicked(cell ce: HYContenListTopTableViewCell, sectionIndex section: Int) {
        
        CommonTools.checkLogin(viewController: self, afterLoginCall:nil,callBack: {
            () -> Void in
            
            if let strID:String = self.dataModel!.channel?.id{
                
                if(ce.scribableState == SubScribableState.subScribable_Already){
                    //取消
                    let strExUrl:String = "/\(strID)/subscription";
                    
                    print("strExUrl=\(strExUrl)");
                    
                    AskDogCancelSubscriptionDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            print("AskDogSubscriptionDataRequest  ok");
                            
                            ce.setSubScribableStateAndCount(.subScribable_Can, beAdd: false);
                        }else{
                            
                        }
                    });
                }else if(ce.scribableState == SubScribableState.subScribable_Can){
                    //订阅
                    let strExUrl:String = "/\(strID)/subscription";
                    
                    print("strExUrl=\(strExUrl)");
                    
                    AskDogSubscriptionDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            print("AskDogSubscriptionDataRequest  ok");
                            ce.setSubScribableStateAndCount(.subScribable_Already, beAdd: true);
                        }else{
                            
                        }
                    });
                }
            }
            
            
        });
        
        
        //        if(false == Global.sharedInstance.isLogined){
        //            let login:HYLoginViewController = HYLoginViewController(nibName: "HYLoginViewController", bundle: NSBundle.mainBundle());
        //            login.callBackLoginedFun = {
        //                (beOK:Bool)-> Void in
        //                print("temp login success");
        //            };
        //
        //            login.callWeixinFun = {
        //                (beOK:Bool)-> Void in
        //                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
        //                self.navigationController?.pushViewController(vc, animated: true);
        //            };
        //            login.callQQFun = {
        //                (beOK:Bool)-> Void in
        //                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
        //                self.navigationController?.pushViewController(vc, animated: true);
        //            };
        //            login.callWeiboFun = {
        //                (beOK:Bool)-> Void in
        //                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: NSBundle.mainBundle());
        //                self.navigationController?.pushViewController(vc, animated: true);
        //            };
        //
        //            login.callBackFunForgetPwd = {
        //                (beOK:Bool)-> Void in
        //                if(true == beOK){
        //                    let vc:HYForgetPasswordViewController = HYForgetPasswordViewController(nibName: "HYForgetPasswordViewController", bundle: NSBundle.mainBundle());
        //                    self.navigationController?.pushViewController(vc, animated: true);
        //                }
        //            };
        //
        //            login.reloadLoginedHomePageDataCallBackFun = {
        //                (beOK:Bool)-> Void in
        //                if(true == beOK){
        //                    self.shouldUpdateData();
        //                }
        //            };
        //
        //            login.callBackGoCategories = {
        //                (beOK:Bool)-> Void in
        //                if(true == beOK){
        //                    AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
        //                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
        //                        if(true == beOk){
        //
        //                            var array:[HYCategoryDataModel] = [HYCategoryDataModel]();
        //
        //                            for item in arr!{
        //                                if let obj:AnyObject = item{
        //                                    let dic:NSDictionary = obj as! NSDictionary;
        //                                    let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
        //                                    array.append(data);
        //                                }
        //                            }
        //
        //
        //                            let vc:HYDingyuePindaoViewController = HYDingyuePindaoViewController(nibName: "HYDingyuePindaoViewController", bundle: NSBundle.mainBundle());
        //                            vc.dataModelArray = array;
        //                            self.navigationController?.pushViewController(vc, animated: true);
        //                        }
        //                    });
        //                }
        //            }
        //
        //            //   login.callBackGoXieyi = {
        //            //       (beOK:Bool)-> Void in
        //            //       if(true == beOK){
        //            //           let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: NSBundle.mainBundle());
        //            //           vc.setReturnTyle(UserAgreeMentReturnStyle.UserAgreeMentReturnStyle_pop_animated);
        //            //           self.navigationController?.pushViewController(vc, animated: true);
        //            //           //self.presentViewController(vc, animated: true, completion: nil);
        //            //       }
        //            //   }
        //
        //            self.presentViewController(login, animated: true, completion: nil);
        //        }else{
        //            if let strID:String = dataModel!.channel?.id{
        //
        //                if(ce.scribableState == SubScribableState.SubScribable_Already){
        //                    //取消
        //                    let strExUrl:String = "/\(strID)/subscription";
        //
        //                    print("strExUrl=\(strExUrl)");
        //
        //                    AskDogCancelSubscriptionDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
        //                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
        //                        if(true == beOk){
        //                            print("AskDogSubscriptionDataRequest  ok");
        //
        //                            ce.setSubScribableStateAndCount(.SubScribable_Can, beAdd: false);
        //                        }else{
        //
        //                        }
        //                    });
        //                }else if(ce.scribableState == SubScribableState.SubScribable_Can){
        //                    //订阅
        //                    let strExUrl:String = "/\(strID)/subscription";
        //
        //                    print("strExUrl=\(strExUrl)");
        //
        //                    AskDogSubscriptionDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
        //                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
        //                        if(true == beOk){
        //                            print("AskDogSubscriptionDataRequest  ok");
        //                            ce.setSubScribableStateAndCount(.SubScribable_Already, beAdd: true);
        //                        }else{
        //
        //                        }
        //                    });
        //                }
        //            }
        //        }
        
    }
    
    //MARK:HYContentListTopVideoTableViewCellDelegate
    func hyContentListTopVideoTableViewCellPayBtnClicked(cell ce: HYContentListTopVideoTableViewCell, sectionIndex section: Int) {
        print("video pay in");
        
        
        CommonTools.checkLogin(viewController: self,
                               afterLoginCall:self.checkExperienceDetailStatus,
                               callBack: {
                                () -> Void in
                                //FIXME:微信支付关闭
                                CommonTools.showMessage("请访问askdog.com观看！", vc: self, hr: {
                                    () -> Void in
                                    
                                });
                                
                                
//                                //预支付接口
//                                
//                                var title:String = "";
//                                if let ti = self.dataModel?.subject{
//                                    title = ti;
//                                }
//                                
//                                if let id:String = self.dataModel!.id{
//                                    let strExUrl:String = "/\(id)/pay";
//                                    
//                                    let dic:[String: AnyObject] = ["pay_way":PAY_WAY_WEIXIN,"pay_way_detail":"APP","title":"APPDOG","product_description":title];
//                                    
//                                    AskDogPayBeforeRequest().startRequest(requestParmer: dic,exUrl:strExUrl, viewController:self,completionHandler: {
//                                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
//                                        if(true == beOk){
//                                            if(dic != nil){
//                                                print("AskDogPayBeforeRequest success");
//                                                
//                                                let payData:HYPayBeforeDataModel = HYPayBeforeDataModel(jsonDic: dic!);
//                                                HYWXApiManager.sharedInstance.useWeixinPay(payData: payData,callBack: {
//                                                    (beOK:Bool?,respone:PayResp?,needInstallWX:Bool?) -> Void in
//                                                    if(true == needInstallWX){
//                                                        CommonTools.showModalMessage("未安装微信，或当前微信版本过低，请安装最新版的微信！",
//                                                            title: "注意", vc: self, hasCancelBtn: true,OKBtnTitle:"去安装",CancelBtnTitle:"算了",hr: {
//                                                                (beOK:Bool) -> Void in
//                                                                if(beOK == true){
//                                                                    let url:NSURL = HYWXApiManager.sharedInstance.getWeixinAppstoreUrl();
//                                                                    UIApplication.sharedApplication().openURL(url);
//                                                                }
//                                                        });
//                                                    }else{
//                                                        if(beOK == true){
//                                                            
//                                                            //                                        //
//                                                            //                                        WXSuccess           = 0,    /**< 成功    */
//                                                            //                                        WXErrCodeCommon     = -1,   /**< 普通错误类型    */
//                                                            //                                        WXErrCodeUserCancel = -2,   /**< 用户点击取消并返回    */
//                                                            //                                        WXErrCodeSentFail   = -3,   /**< 发送失败    */
//                                                            //                                        WXErrCodeAuthDeny   = -4,   /**< 授权失败    */
//                                                            //                                        WXErrCodeUnsupport  = -5,   /**< 微信不支持    */
//                                                            
//                                                            if(0 == respone?.errCode){
//                                                                CommonTools.showMessage("支付成功！", vc: self, hr: {
//                                                                    () -> Void in
//                                                                    print("weixin pay is ok");
//                                                                    //允许用户播放视频
//                                                                    ce.lblBtnPay.hidden = true;
//                                                                    ce.lblPriceInfo.hidden = true;
//                                                                    ce.zhezhaoView.hidden = true;
//                                                                });
//                                                            }else{
//                                                                
//                                                                var info:String = "";
//                                                                if(-1 == respone?.errCode){
//                                                                    info = "支付出错！";
//                                                                }else if(-2 == respone?.errCode){
//                                                                    info = "支付已取消！";
//                                                                }else if(-3 == respone?.errCode){
//                                                                    info = "发送失败！";
//                                                                }else if(-4 == respone?.errCode){
//                                                                    info = "授权失败！";
//                                                                }else if(-5 == respone?.errCode){
//                                                                    info = "当前微信不支持！";
//                                                                }
//                                                                CommonTools.showMessage(info, vc: self, hr: {
//                                                                    () -> Void in
//                                                                    print("weixin pay is cancel");
//                                                                    
//                                                                });
//                                                                
//                                                            }
//                                                            
//                                                            
//                                                        }
//                                                    }
//                                                    
//                                                });
//                                                
//                                            }
//                                            //                    CommonTools.showMessage("频道修改成功!", vc: self, hr: {
//                                            //                        () -> Void in
//                                            //                        self.navigationController?.popViewControllerAnimated(true);
//                                            //                    });
//                                        }
//                                    });
//                                }
                                
                                
        });
        
        
    }
    
    func hyContentListTopVideoTableViewCellShareBtnClicked(cell ce: HYContentListTopVideoTableViewCell, sectionIndex section: Int) {
        self.shareToWeixin(1);
    }
    
    func hyContentListTopVideoTableViewCellHeaderBtnClicked(cell ce: HYContentListTopVideoTableViewCell, sectionIndex section: Int) {
        print("hyContentListTopVideoTableViewCellHeaderBtnClicked");
        
        self.goUserSpace();
    }
    func hyContentListTopVideoTableViewCellReportBtnClicked(cell ce: HYContentListTopVideoTableViewCell, sectionIndex section: Int) {
        self.doReport();
    }
    
    func hyContentListTopVideoTableViewCellCaiBtnClicked(cell ce: HYContentListTopVideoTableViewCell, sectionIndex section: Int) {
        
        
        CommonTools.checkLogin(viewController: self, afterLoginCall: self.checkExperienceDetailStatus, callBack: {
            () -> Void in
            if(false == ce.btnCai.isSelected){
                if let strID:String = self.dataModel!.id{
                    
                    let strExUrl:String = "/\(strID)/vote";
                    
                    let dic:[String: AnyObject]? = ["direction":"DOWN" as AnyObject];
                    
                    print("strExUrl=\(strExUrl)");
                    
                    AskDogVoteDataRequest().startRequest(requestParmer: dic, exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            ce.setZanCaiDic(dic!);
                            print("AskDogVoteDataRequest  ok");
                        }else{
                            
                        }
                    });
                }
            }else{
                if let strID:String = self.dataModel!.id{
                    
                    let strExUrl:String = "/\(strID)/vote";
                    
                    //let dic:[String: AnyObject]? = ["direction":"UP"];
                    
                    print("strExUrl=\(strExUrl)");
                    
                    AskDogCancelVoteDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            ce.setZanCaiDic(dic!);
                            print("AskDogCancelVoteDataRequest  ok");
                        }else{
                            
                        }
                    });
                }
            }
        
        
        });
        
    }
    
    func hyContentListTopVideoTableViewCellZanBtnClicked(cell ce: HYContentListTopVideoTableViewCell, sectionIndex section: Int) {
        
        CommonTools.checkLogin(viewController: self, afterLoginCall: self.checkExperienceDetailStatus, callBack: {
            () -> Void in
            
            if(false == ce.btnZan.isSelected){
                if let strID:String = self.dataModel!.id{
                    
                    let strExUrl:String = "/\(strID)/vote";
                    
                    let dic:[String: AnyObject]? = ["direction":"UP" as AnyObject];
                    
                    print("strExUrl=\(strExUrl)");
                    
                    AskDogVoteDataRequest().startRequest(requestParmer: dic, exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            ce.setZanCaiDic(dic!);
                            print("AskDogVoteDataRequest  ok");
                        }else{
                            
                        }
                    });
                }
            }else{
                if let strID:String = self.dataModel!.id{
                    
                    let strExUrl:String = "/\(strID)/vote";
                    
                    //let dic:[String: AnyObject]? = ["direction":"UP"];
                    
                    print("strExUrl=\(strExUrl)");
                    
                    AskDogCancelVoteDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            ce.setZanCaiDic(dic!);
                            print("AskDogCancelVoteDataRequest  ok");
                        }else{
                            
                        }
                    });
                }
            }
            
        });
        
        
    }
    
    func hyContentListTopVideoTableViewCellSubscriptionBtnClicked(cell ce: HYContentListTopVideoTableViewCell, sectionIndex section: Int) {
        
        CommonTools.checkLogin(viewController: self,afterLoginCall:self.checkExperienceDetailStatus, callBack: {
            () -> Void in
            
            if let strID:String = self.dataModel!.channel?.id{
                
                if(ce.scribableState == SubScribableState.subScribable_Already){
                    //取消
                    let strExUrl:String = "/\(strID)/subscription";
                    
                    print("strExUrl=\(strExUrl)");
                    
                    AskDogCancelSubscriptionDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            print("AskDogSubscriptionDataRequest  ok");
                            
                            ce.setSubScribableStateAndCount(.subScribable_Can, beAdd: false);
                        }else{
                            
                        }
                    });
                }else if(ce.scribableState == SubScribableState.subScribable_Can){
                    //订阅
                    let strExUrl:String = "/\(strID)/subscription";
                    
                    print("strExUrl=\(strExUrl)");
                    
                    AskDogSubscriptionDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            print("AskDogSubscriptionDataRequest  ok");
                            ce.setSubScribableStateAndCount(.subScribable_Already, beAdd: true);
                        }else{
                            
                        }
                    });
                }
            }
            
        });
        
    }
    
    override func isNeedShowNotifyBtn() -> Bool {
        return false;
    }
    
}
