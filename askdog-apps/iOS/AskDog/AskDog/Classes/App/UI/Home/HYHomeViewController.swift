//
//  HYHomeViewController.swift
//  AskDog
//
//  Created by Symond on 16/7/25.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import MJRefresh
import SDWebImage

class HYHomeViewController: HYBaseViewController,UITableViewDelegate,UITableViewDataSource,HYUserCenterHomeTopTableViewCellDelegate,HYTopScrollMenuViewDataSource,
HYHomePageViewDelegate,iConsoleDelegate, HYHomeOtherPageViewDelegate, HYHomeChanneclsTableViewCellDelegate, HYHomeChannelListCellDelegate {


    var curTabShowIndex:NSInteger = -1;
    var btnTabArray:[[String: AnyObject]]!;
    
    var tab1DataArray:NSMutableArray = NSMutableArray();
    var tab2DataArray:NSMutableArray = NSMutableArray();
    var tab4DataArray:NSMutableArray = NSMutableArray();
    
    var menuDataArray:[AnyObject] = [AnyObject]() ;
    var menuDataArray1:[[String:String]] = [[String:String]]();
    var menuDataArray2:[[String:String]] = [[String:String]]();
    var menuDataArray3:[[String:String]] = [[String:String]]();

    
    var tab1DataModel:HYHomeListResultDataModel?
    var tab2DataModel:HYHomeChanneltDataModel?
    var tab4DataModel:HYChannelRecommendDataModel?
    
    var tab1FirstLoad:Bool = false;
    var tab2FirstLoad:Bool = false;
    var tab4FirstLoad:Bool = false;
    
    @IBOutlet weak var view2TitleLab: UILabel!
    @IBOutlet weak var view4HeaderView: UIView!

    @IBOutlet weak var view1: UIView!
    @IBOutlet weak var topV1: HYTopScrollMenuView!
    var categoryDataArray:[HYCategoryTreeDataModel] = [HYCategoryTreeDataModel]();
    var homePageTitles:[Any] = ["热门"];
    
    @IBOutlet weak var view2: UIView!
    @IBOutlet weak var view3: UIView!
    @IBOutlet weak var view4: UIView!
    @IBOutlet weak var view5: UIView!
    @IBOutlet weak var tabBtn1: UIButton!
    @IBOutlet weak var tabBtn2: UIButton!
    @IBOutlet weak var tabBtn3: UIButton!
    @IBOutlet weak var tabBtn4: UIButton!
    @IBOutlet weak var tabBtn5: UIButton!
    
    @IBOutlet weak var tabBtn1Width: NSLayoutConstraint!
    @IBOutlet weak var tabBtn2Width: NSLayoutConstraint!
    @IBOutlet weak var tabBtn4Width: NSLayoutConstraint!
    @IBOutlet weak var tabBtn5Width: NSLayoutConstraint!
    @IBOutlet weak var tabBtn3Width: NSLayoutConstraint!
    @IBOutlet weak var tabBtn3Height: NSLayoutConstraint!
    
    @IBOutlet weak var tab1TableView: HYBaseUITableView!
    @IBOutlet weak var tab2TableView: HYBaseUITableView!
    @IBOutlet weak var tab4TableView: HYBaseUITableView!
    @IBOutlet weak var tab5TableView: HYBaseUITableView!
    var appDelegate:AppDelegate!
    
    var searchBkView:UIView!
    
    let header1 = MJRefreshNormalHeader();
    let footer1 = MJRefreshAutoNormalFooter();
    
    let header2 = MJRefreshNormalHeader();
    let footer2 = MJRefreshAutoNormalFooter();
    
    let header4 = MJRefreshNormalHeader();
    let footer4 = MJRefreshAutoNormalFooter();
    
    var channecdata:[HYChannelListDataModel]?

    
    var nTab1PageNumber:Int = 0;
    var nTab2PageNumber:Int = 0;
    var nTab4PageNumber:Int = 0;
    
    var isNewMessage = false;
    
    func doLogin() -> Void {
        self.btnLoginClicked(btnLogin);
    }
    
    @IBAction func tabBtn1Clicked(_ sender: UIButton) {
        showTabWithIndex(showIndex: 0);
    }
    
    @IBAction func tabBtn2Clicked(_ sender: UIButton) {
        CommonTools.checkLogin(viewController: self, afterLoginCall:nil,callBack: {
            () -> Void in
            self.showTabWithIndex(showIndex: 1);
        });
    }
    
    @IBAction func tabBtn3Clicked(_ sender: UIButton) {
        
        

        

        //showTabWithIndex(showIndex: 2);
        //        CommonTools.showMessage("请到PC端添加经验！", vc: self, hr: {
        //            () -> Void in
        //
        //        });
        
//        AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
//            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
//            if(true == beOk){
//                
//                var array:[HYCategoryDataModel] = [HYCategoryDataModel]();
//                
//                for item in arr!{
//                    if let obj:AnyObject = item{
//                        let dic:NSDictionary = obj as! NSDictionary;
//                        let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
//                        array.append(data);
//                    }
//                }
//                
//                
//                let vc:HYDingyuePindaoViewController = HYDingyuePindaoViewController(nibName: "HYDingyuePindaoViewController", bundle: NSBundle.mainBundle());
//                vc.dataModelArray = array;
//                self.navigationController?.pushViewController(vc, animated: true);
//            }
//        });
//        
//        return;
        
       // let vc:HYVideoShareViewController = HYVideoShareViewController(nibName: "HYVideoShareViewController", bundle: Bundle.main);
       // self.navigationController?.pushViewController(vc, animated: true);
        
        CommonTools.checkLogin(viewController: self, afterLoginCall:nil,callBack: {
            () -> Void in
        

            //FIXME:  临时屏蔽掉图文经验
            
            let vc:HYVideoShareViewController = HYVideoShareViewController(nibName: "HYVideoShareViewController", bundle: Bundle.main);
            self.navigationController?.pushViewController(vc, animated: true);
            
        });
        
            
//            let v:HYExperiencePopView = HYExperiencePopView(frame: CGRectZero);
//            v.show(inView: self.view, callBack: {
//                (obj:AnyObject) -> Void in
//                
//                let beLeftBtnClicked:Bool = obj as! Bool;
//                if(true == beLeftBtnClicked){
//                    print("视频分享 selected");
//                    let vc:HYVideoShareViewController = HYVideoShareViewController(nibName: "HYVideoShareViewController", bundle: NSBundle.mainBundle());
//                    self.navigationController?.pushViewController(vc, animated: true);
//                }else{
//                    print("图文分享 selected");
//                    let vc:HYTuwenShareViewController = HYTuwenShareViewController(nibName: "HYTuwenShareViewController", bundle: NSBundle.mainBundle());
//                    self.navigationController?.pushViewController(vc, animated: true);
//                }
//                
//            });
            
       
        
//        if(false == Global.sharedInstance.isLogined){
//            let login:HYLoginViewController = HYLoginViewController(nibName: "HYLoginViewController", bundle: NSBundle.mainBundle());
//            login.callBackLoginedFun = {
//                (beOK:Bool)-> Void in
//                print("temp login success");
//            };
//            
//            login.callWeixinFun = {
//                (beOK:Bool)-> Void in
//                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "HYThirdAuthViewController", bundle: NSBundle.mainBundle());
//                self.navigationController?.pushViewController(vc, animated: true);
//            };
//            login.callQQFun = {
//                (beOK:Bool)-> Void in
//                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "HYThirdAuthViewController", bundle: NSBundle.mainBundle());
//                self.navigationController?.pushViewController(vc, animated: true);
//            };
//            login.callWeiboFun = {
//                (beOK:Bool)-> Void in
//                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "HYThirdAuthViewController", bundle: NSBundle.mainBundle());
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
//            let v:HYExperiencePopView = HYExperiencePopView(frame: CGRectZero);
//            v.show(inView: self.view, callBack: {
//                (obj:AnyObject) -> Void in
//                
//                let beLeftBtnClicked:Bool = obj as! Bool;
//                if(true == beLeftBtnClicked){
//                    print("视频分享 selected");
//                    let vc:HYVideoShareViewController = HYVideoShareViewController(nibName: "HYVideoShareViewController", bundle: NSBundle.mainBundle());
//                    self.navigationController?.pushViewController(vc, animated: true);
//                }else{
//                    print("图文分享 selected");
//                    let vc:HYTuwenShareViewController = HYTuwenShareViewController(nibName: "HYTuwenShareViewController", bundle: NSBundle.mainBundle());
//                    self.navigationController?.pushViewController(vc, animated: true);
//                }
//                
//            });
//        }
        
        

    }
    
    @IBAction func tabBtn4Clicked(_ sender: UIButton) {
        self.showTabWithIndex(showIndex: 3);

        
    }
    
    @IBAction func tabBtn5Clicked(_ sender: UIButton) {
        self.loadUserCenterData();
        self.loadData5();
        
    }
    
    func showTabWithIndex(showIndex index:NSInteger) -> Void {
        
        if(index < 0 || index >= btnTabArray.count){
            return;
        }
        
        if(0 == index){
            if(false == Global.sharedInstance.isLogined){
                btnLogin.isHidden = false;
            }
          //  searchBkView.isHidden = false;
            if(false == self.tab1FirstLoad){
                self.loadData1();
                self.tab1FirstLoad = true;
            }
            
        }else if(1 == index){
            if(false == Global.sharedInstance.isLogined){
                btnLogin.isHidden = false;
            }
          //  searchBkView.isHidden = false;
            
            if(false == self.tab2FirstLoad){
                self.loadData2();
                self.tab2FirstLoad = true;
            }
        }else if(3 == index){
            if(false == Global.sharedInstance.isLogined){
                btnLogin.isHidden = false;
            }
         //   searchBkView.isHidden = false;
            if(false == self.tab4FirstLoad){
                self.loadData4();
                //FIXME: 发现是否登录判断
//                self.tab4FirstLoad = true;
            }
        }else if(4 == index){
            btnLogin.isHidden = true;
         //   searchBkView.isHidden = true;
        }
        
        if (curTabShowIndex == index){
            return;
        }
        
        btnLogin.isHidden = true;
        
        if(curTabShowIndex >= 0 && curTabShowIndex < btnTabArray.count){
            let dic:[String:AnyObject] = btnTabArray[curTabShowIndex];
            let oldBtn:UIButton = dic["btn"] as! UIButton;
            let v:UIView = dic["view"] as! UIView;
            
            oldBtn.isSelected = false;
            v.isHidden = true;
        }
        
        let dicNew:[String:AnyObject] = btnTabArray[index];
        let newBtn:UIButton = dicNew["btn"] as! UIButton;
        let vNew:UIView = dicNew["view"] as! UIView;
        let newTitle:String = dicNew["title"] as! String;
        
        setNavBarTitle(newTitle);
        newBtn.isSelected = true;
        vNew.isHidden = false;
        
        
        curTabShowIndex = index;
        
        self.refreshNavBarBtnStatus();
    }
    
    func loadData1() -> Void {
        
        self.nTab1PageNumber = 0;
        let strPage = String(self.nTab1PageNumber);
  
        let dic:[String: AnyObject]? = ["type":"homeSearch" as AnyObject,"from":strPage  as AnyObject,"size":PAGE_SIZE as AnyObject];
        AskDogHomeSearchDataRequest().startRequest(requestParmer: dic, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){

                    
                    self.tab1DataModel = HYHomeListResultDataModel(jsonDic: dic!);
                    self.tab1TableView.reloadData();
                    if (true == self.tab1DataModel?.last){
                        self.tab1TableView.footer.endRefreshingWithNoMoreData();
                    }else{
                        self.tab1TableView.footer.resetNoMoreData();
                    }

                }
            }
            
        });
        
        self.tab1TableView.header.endRefreshing();
        
    }
    
    func loadData2() -> Void {
        
        self.nTab2PageNumber = 0;
        let strPage = String(self.nTab2PageNumber);
        
        
        let dic:[String: AnyObject]? = ["from":strPage as AnyObject,"size":PAGE_SIZE as AnyObject];
        AskDogGetMySubscribedChannelsUnreadDataRequest().startRequest(requestParmer: dic, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){

                    self.tab2DataModel = HYHomeChanneltDataModel(jsonDic: dic!);
                    
                    let indexSet:NSIndexSet = NSIndexSet(index: 1);
                    self.tab2TableView.reloadSections(indexSet as IndexSet, with: UITableViewRowAnimation.automatic);
                    
                    if (true == self.tab2DataModel?.last){
                        self.tab2TableView.footer.endRefreshingWithNoMoreData();
                    }else{
                        self.tab2TableView.footer.resetNoMoreData();
                    }
                }
            }
            
        });
    
        self.tab2TableView.header.endRefreshing();
        self.loadChanneclsListData();
    }
    
    func loadData4() -> Void {
        self.nTab4PageNumber = 0;
        let strPage = String(self.nTab4PageNumber);
        
        let dic: [String:AnyObject] = ["type":"channel_recommend" as AnyObject, "from":strPage as AnyObject, "size":PAGE_SIZE as AnyObject]
        print("HYSearchViewController --- network -- :\(dic)")
        
        AskDogGetHomeSearchActionRequest().startRequest(requestParmer: dic, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            
            if beOk == true {
                if dic != nil {
                    self.tab4DataModel = HYChannelRecommendDataModel(jsonDic: dic!)
                    self.tab4TableView.reloadData();
                    if (true == self.tab1DataModel?.last){
                        self.tab4TableView.footer.endRefreshingWithNoMoreData();
                    }else{
                        self.tab4TableView.footer.resetNoMoreData();
                    }
                }
            }
        });

        self.tab4TableView.header.endRefreshing();
    }
    
    func loadData5() -> Void {
        
        if(true == Global.sharedInstance.isLogined){
            AskDogGetPreviewRequest().startRequest(viewController:self, completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                
                if beOk == true {
                    if arr != nil {
                        if (arr?.count)! > 0 {
                            self.isNewMessage = true
                        }
                    }
                }
                self.tab5TableView.reloadData();
            });
        }
        

    }

    
    func loadUserCenterData() -> Void {
        if(false == Global.sharedInstance.isLogined){
            let login:HYLoginViewController = HYLoginViewController(nibName: "HYLoginViewController", bundle: Bundle.main);
            login.isTempLogin = true;
            login.callBackLoginedFun = {
                (beOK:Bool)-> Void in
                
                print("temp login success");
                self.showTabWithIndex(showIndex: 4);
                self.shouldUpdateData();
  
            };
            
            login.callWeixinFun = {
                (beOK:Bool)-> Void in
                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: Bundle.main);
                self.navigationController?.pushViewController(vc, animated: true);
            };
            login.callQQFun = {
                (beOK:Bool)-> Void in
                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: Bundle.main);
                self.navigationController?.pushViewController(vc, animated: true);
            };
            login.callWeiboFun = {
                (beOK:Bool)-> Void in
                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "", bundle: Bundle.main);
                self.navigationController?.pushViewController(vc, animated: true);
            };
            
            login.callBackFunForgetPwd = {
                (beOK:Bool)-> Void in
                if(true == beOK){
                    let vc:HYForgetPasswordViewController = HYForgetPasswordViewController(nibName: "HYForgetPasswordViewController", bundle: Bundle.main);
                    self.navigationController?.pushViewController(vc, animated: true);
                }
            };
            
            login.reloadLoginedHomePageDataCallBackFun = {
                (beOK:Bool)-> Void in
                if(true == beOK){
                    self.shouldUpdateData();
                }
            };
            
            login.callBackGoCategories = {
                (beOK:Bool)-> Void in
                if(true == beOK){
                    AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
                            
                            var array:[HYCategoryDataModel] = [HYCategoryDataModel]();
                            
                            for item in arr!{
                                if let obj:AnyObject = item as AnyObject?{
                                    let dic:NSDictionary = obj as! NSDictionary;
                                    let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
                                    array.append(data);
                                }
                            }
                            
                            
                            let vc:HYDingyuePindaoViewController = HYDingyuePindaoViewController(nibName: "HYDingyuePindaoViewController", bundle: Bundle.main);
                            vc.dataModelArray = array;
                            self.navigationController?.pushViewController(vc, animated: true);
                        }
                    });
                }
            }
            
            //   login.callBackGoXieyi = {
            //       (beOK:Bool)-> Void in
            //       if(true == beOK){
            //           let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: NSBundle.mainBundle());
            //           vc.setReturnTyle(UserAgreeMentReturnStyle.UserAgreeMentReturnStyle_pop_animated);
            //           self.navigationController?.pushViewController(vc, animated: true);
            //           //self.presentViewController(vc, animated: true, completion: nil);
            //       }
            //   }
            
            
            self.present(login, animated: true, completion: nil);
        }else{
            self.showTabWithIndex(showIndex: 4);
        }
    }

    
    func loadChanneclsListData() -> Void {
        //TODO 订阅列表
        let dic:[String: AnyObject]? = ["size":PAGE_SIZE as AnyObject, "page":"0" as AnyObject];
        AskDogGetMySubscribedChannelListDataRequest().startRequest(requestParmer: dic, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            
            if(true == beOk){
                if(nil != dic){
                    
                    let dm: HYChannelListResultDataModel = HYChannelListResultDataModel(jsonDic: dic!)
                    if let res = dm.result {
                        self.channecdata = res;
                        let indexSet:NSIndexSet = NSIndexSet(index: 0);
                        self.tab2TableView.reloadSections(indexSet as IndexSet, with: UITableViewRowAnimation.automatic);
                        
                        if true == dm.last {
                            self.tab2TableView.footer.endRefreshingWithNoMoreData()
                        } else {
                            self.tab2TableView.footer.resetNoMoreData()
                        }
                    }
                }
            }
        });
    }
    
    override func shouldUpdateData() {
        super.shouldUpdateData();
        // 登录成功后 重新加载主页数据
        
        self.showTabWithIndex(showIndex: self.curTabShowIndex);
        

        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        if(true == Global.sharedInstance.beNeedDoLogin){
            self.doLogin();
            Global.sharedInstance.beNeedDoLogin = false;
        }else if(true == Global.sharedInstance.beNeedReloadHomePage){
            Global.sharedInstance.beNeedReloadHomePage = false;
            shouldUpdateData();
        }
        

    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated);
        print("viewWillAppear viewWillAppear");
       
        self.loadData4();
        self.loadData5();
        
        if(true == Global.sharedInstance.isLogined){
            
            //AskDogGetUserDetailInfoDataRequest
            //AskDogGetUserInfoDataRequest
            AskDogGetUserDetailInfoDataRequest().startRequest( viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    
                    if(nil != dic){
                        print("AskDogGetUserInfoDataRequest ok");
                        if let resultDic:NSDictionary = dic{
                            let userInfo:HYUserInfoDataModel = HYUserInfoDataModel(jsonDic: resultDic);
                            Global.sharedInstance.userInfo = userInfo;
                            //更新头像的URL 
                            if let url:String = userInfo.avatar{
                                UserDefaults.standard.set(url, forKey: LOGIN_USER_HEADER_IMG_KEY);
                            }else{
                                UserDefaults.standard.removeObject(forKey: LOGIN_USER_HEADER_IMG_KEY);
                            }
                        }

                        self.setNotifyInfo(Global.sharedInstance.userInfo?.notice_count);
                        
                        //刷新个人信息
                        self.tab5TableView.beginUpdates();
                        let indexs:[IndexPath] = [IndexPath(row: 0, section: 0)];
                        self.tab5TableView.reloadRows(at: indexs, with: .none);
                        self.tab5TableView.endUpdates();
                        
                        //5秒钟后 缓存一下用户的头像图片
                       // NSTimer.scheduledTimerWithTimeInterval(5, target: self, selector: #selector(self.saveUserHeaderImgToDoucment), userInfo: nil, repeats: false);
                        
                    }
                }
            });
            
        }
        

    }
    
    func saveUserHeaderImgToDoucment() -> Void {
        
        
        
        
        let cell:HYUserCenterHomeTopTableViewCell = self.tab5TableView.cellForRow(at: IndexPath(row: 0, section: 0)) as! HYUserCenterHomeTopTableViewCell;
        if let img = cell.headerImgV.image{
            
            UserDefaults.standard.set(img, forKey: LOGIN_USER_HEADER_IMG_KEY);
            
            print("保存用户头像成功");
            
            //let da:NSData = UIImageJPEGRepresentation(img, 1.0)!;
            
//            CommonTools.saveFile("cahceHeader.png", data: da, hr: {
//                (beOK:Bool,path:String) -> Void in
//                if(true == beOK){
//                    //LOGIN_USER_HEADER_IMG_KEY
//                    print("保存用户头像成功");
//                    NSUserDefaults.standardUserDefaults().setObject(path, forKey: LOGIN_USER_HEADER_IMG_KEY);
//                }
//                
//            });
            
//            let docDirs = NSSearchPathForDirectoriesInDomains(.CachesDirectory, .UserDomainMask, true);
//            let filePath:String = "\(docDirs)/cahceHeader.png";
//            do {
//                try da.writeToFile(filePath, options: NSDataWritingOptions.DataWritingAtomic);
//                print("success cache file at \(filePath)");
//            } catch let error as NSError? {
//                print("error saving core data: \(error)")
//            }
            
            
//            
//            CommonTools.saveFile("header/cahceHeader.png", data: da, hr: {
//                (beOK:Bool,path:String) -> Void in
//                
//            });
        }
        
        
    }
    
    override func viewDidLoad() {
        stringNavBarTitle = "主页";
        super.viewDidLoad()
        
        if(true == OPEN_ICONSOLE){
            iConsole.shared().delegate = self;
        }
        
        //初始化通知信息数量
        self.setNotifyInfo(0);
        
        //UIScreen.mainScreen().bounds.size.width/5;
        let f:CGFloat = (UIScreen.main.bounds.size.width - 77)/4;
        
        tabBtn1Width.constant = f;
        tabBtn2Width.constant = f;
        tabBtn3Width.constant = 77;
        tabBtn3Height.constant = 77;
        tabBtn4Width.constant = f;
        tabBtn5Width.constant = f;
        
        tabBtn3.layer.borderColor = UIColor.white.cgColor;
        tabBtn3.layer.cornerRadius = tabBtn3Height.constant/2;
        tabBtn3.layer.borderWidth = 5;
        //调整按钮中图片和文字的关系
        if(true == IS_IPHONE6S_PLUS_DEV){
            print("IS_IPHONE6S_PLUS_DEV IN");
            tabBtn1.imageEdgeInsets = UIEdgeInsetsMake(14, 32, 29, 31);
            tabBtn2.imageEdgeInsets = UIEdgeInsetsMake(14, 32, 29, 31);
            tabBtn4.imageEdgeInsets = UIEdgeInsetsMake(14, 32, 29, 31);
            tabBtn5.imageEdgeInsets = UIEdgeInsetsMake(14, 32, 29, 31);
            
            tabBtn1.titleEdgeInsets = UIEdgeInsetsMake(27, -80, 1, -2);
            tabBtn2.titleEdgeInsets = UIEdgeInsetsMake(27, -80, 1, -2);
            tabBtn4.titleEdgeInsets = UIEdgeInsetsMake(27, -80, 1, -2);
            tabBtn5.titleEdgeInsets = UIEdgeInsetsMake(27, -80, 1, -2);
        }else if(true == IS_IPHONE66S_DEV){
            print("IS_IPHONE66S_DEV IN");
            tabBtn1.imageEdgeInsets = UIEdgeInsetsMake(16, 29, 30, 28);
            tabBtn2.imageEdgeInsets = UIEdgeInsetsMake(16, 29, 30, 28);
            tabBtn4.imageEdgeInsets = UIEdgeInsetsMake(16, 29, 30, 28);
            tabBtn5.imageEdgeInsets = UIEdgeInsetsMake(16, 29, 30, 28);
            
            tabBtn1.titleEdgeInsets = UIEdgeInsetsMake(27, -80, 1, -2);
            tabBtn2.titleEdgeInsets = UIEdgeInsetsMake(27, -80, 1, -2);
            tabBtn4.titleEdgeInsets = UIEdgeInsetsMake(27, -80, 1, -2);
            tabBtn5.titleEdgeInsets = UIEdgeInsetsMake(27, -80, 1, -2);
        
        }else if(true == IS_IPHONE55S_DEV){
            print("IS_IPHONE55S_DEV IN");
            tabBtn1.imageEdgeInsets = UIEdgeInsetsMake(14, 21, 30, 21);
            tabBtn2.imageEdgeInsets = UIEdgeInsetsMake(14, 21, 30, 21);
            tabBtn4.imageEdgeInsets = UIEdgeInsetsMake(14, 21, 30, 21);
            tabBtn5.imageEdgeInsets = UIEdgeInsetsMake(14, 21, 30, 21);
            
            tabBtn1.titleEdgeInsets = UIEdgeInsetsMake(27, -80, 1, -2);
            tabBtn2.titleEdgeInsets = UIEdgeInsetsMake(27, -80, 1, -2);
            tabBtn4.titleEdgeInsets = UIEdgeInsetsMake(27, -80, 1, -2);
            tabBtn5.titleEdgeInsets = UIEdgeInsetsMake(27, -80, 1, -2);

        }
        

        
        btnTabArray = [["view":view1,"btn":tabBtn1,"title":"首页" as AnyObject],["view":view2,"btn":tabBtn2,"title":"订阅" as AnyObject],
                       ["view":view3,"btn":tabBtn3,"title":"" as AnyObject],["view":view4,"btn":tabBtn4,"title":"推荐" as AnyObject],["view":view5,"btn":tabBtn5,"title":"个人中心" as AnyObject]];
        
        // Do any additional setup after loading the view.
        view1.isHidden = true;
        view2.isHidden = true;
        view3.isHidden = true;
        view4.isHidden = true;
        view5.isHidden = true;
        

        //顶部搜索框
        searchBkView = UIView(frame: CGRect.zero);
        searchBkView.backgroundColor = UIColor.white;//UIColor(red: 242/255, green: 244/255, blue: 249/255, alpha: 1);
        //grayView.backgroundColor = UIColor.yellowColor();
        searchBkView.translatesAutoresizingMaskIntoConstraints = false;
        searchBkView.layer.cornerRadius = 5;
        view4HeaderView.addSubview(searchBkView);
        
//        searchBkView.isHidden = true;
        
        //约束
        var views:[String:AnyObject] = [String:AnyObject]();
        views["searchBkView"] = searchBkView;
        view4HeaderView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-20-[searchBkView]-20-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        view4HeaderView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-7-[searchBkView]-11-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
//        let bgV = UIView(frame: CGRect.zero);
//        bgV.translatesAutoresizingMaskIntoConstraints = false;
//        searchBkView.addSubview(bgV);
//        bgV.addConstraint(NSLayoutConstraint(item: bgV, attribute: .width, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1.0, constant: 100));
//        bgV.addConstraint(NSLayoutConstraint(item: bgV, attribute: .height, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1.0, constant: 20));
//        bgV.addConstraint(NSLayoutConstraint(item: bgV, attribute: .centerX, relatedBy: .equal, toItem: <#T##Any?#>, attribute: <#T##NSLayoutAttribute#>, multiplier: <#T##CGFloat#>, constant: <#T##CGFloat#>)
        
        ///放大镜图标
        let zoomImageV:UIImageView = UIImageView(frame: CGRect.zero);
        zoomImageV.image = UIImage(named: "wenhao");
        //zoomImageV.backgroundColor = UIColor.redColor();
        zoomImageV.translatesAutoresizingMaskIntoConstraints = false;
        searchBkView.addSubview(zoomImageV);
        var views1:[String:AnyObject] = [String:AnyObject]();
        views1["zoomImageV"] = zoomImageV;
        searchBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-8-[zoomImageV(18)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        searchBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-8-[zoomImageV(18)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        

        let searchPlaceHolderTitle:UILabel = UILabel(frame: CGRect.zero);
        //searchPlaceHolderTitle.backgroundColor = UIColor.redColor();
        searchPlaceHolderTitle.translatesAutoresizingMaskIntoConstraints = false;
        searchPlaceHolderTitle.text = "搜索经验";
        searchPlaceHolderTitle.textColor = UIColor(red: 193/255, green: 200/255, blue: 218/255, alpha: 1);
        searchBkView.addSubview(searchPlaceHolderTitle);
        views1["searchPlaceHolderTitle"] = searchPlaceHolderTitle;
        searchBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-29-[searchPlaceHolderTitle(80)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        searchBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-6-[searchPlaceHolderTitle(20)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        
        //覆盖一个按钮
        let btn:UIButton = UIButton(type: UIButtonType.custom);
        btn.frame = CGRect.zero;
        btn.setTitle("", for: UIControlState());
        //btnNavBarBack.titleEdgeInsets = UIEdgeInsetsMake(0,0,0,80);
        btn.addTarget(self, action: #selector(btnSearchClicked), for: UIControlEvents.touchUpInside);
        btn.backgroundColor = UIColor.clear;
        btn.translatesAutoresizingMaskIntoConstraints = false;
        searchBkView.addSubview(btn);
        views1["btn"] = btn;
        searchBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[btn]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        searchBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[btn]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        

        
        //解决分割线不对齐问题
        if tab1TableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tab1TableView.separatorInset = UIEdgeInsets.zero;
        }
        if tab1TableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tab1TableView.layoutMargins = UIEdgeInsets.zero;
        }
        if tab2TableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tab2TableView.separatorInset = UIEdgeInsets.zero;
        }
        if tab2TableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tab2TableView.layoutMargins = UIEdgeInsets.zero;
        }
        if tab4TableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tab4TableView.separatorInset = UIEdgeInsets.zero;
        }
        if tab4TableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tab4TableView.layoutMargins = UIEdgeInsets.zero;
        }
        
        if tab5TableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tab5TableView.separatorInset = UIEdgeInsets.zero;
        }
        if tab5TableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tab5TableView.layoutMargins = UIEdgeInsets.zero;
        }
        
        menuDataArray1 = [["icon":"mychannel","title":"我的频道"],
                          ["icon":"subscribelist","title":"订阅频道"],
                          ["icon":"BrowsingHistory","title":"历史浏览"],];
        menuDataArray2 = [["icon":"bell","title":"我的消息"],];
        menuDataArray3 = [ ["icon":"income","title":"我的收入"],
                           ["icon":"safe","title":"安全中心"],
                           ["icon":"System","title":"系统设置"],];
        menuDataArray = [menuDataArray1 as AnyObject, menuDataArray2 as AnyObject, menuDataArray3 as AnyObject]
        
        
        self.view.bringSubview(toFront: navBarView);
        
        let cellID1:String = "cell1";
        let nib1:UINib = UINib(nibName: "HYHomeListTableViewCell", bundle: nil);
        tab1TableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        let cellID2:String = "cell2";
        let nib2:UINib = UINib(nibName: "HYHomeListTableViewCell", bundle: nil);
        tab2TableView.register(nib2, forCellReuseIdentifier: cellID2);
        
        let cellID3:String = "cell3";
        let nib3:UINib = UINib(nibName: "HYHomeChannelListCellTableViewCell", bundle: nil);
        tab4TableView.register(nib3, forCellReuseIdentifier: cellID3);
        
        let cellID4:String = "cell4";
        let nib4:UINib = UINib(nibName: "HYUserCenterHomeTopTableViewCell", bundle: nil);
        tab5TableView.register(nib4, forCellReuseIdentifier: cellID4);
        
        let cellID5:String = "cell5";
        let nib5:UINib = UINib(nibName: "HYUserCenterHomePageListTableViewCell", bundle: nil);
        tab5TableView.register(nib5, forCellReuseIdentifier: cellID5);
        
        //设置上下拉刷新
        header1.setRefreshingTarget(self, refreshingAction: #selector(header1Refresh));
        tab1TableView.header = header1;
        footer1.setRefreshingTarget(self, refreshingAction: #selector(footer1Refresh))
        tab1TableView.footer = footer1;
        tab1TableView.footer.isAutomaticallyHidden = false;
        
        header2.setRefreshingTarget(self, refreshingAction: #selector(header2Refresh));
        tab2TableView.header = header2;
        footer2.setRefreshingTarget(self, refreshingAction: #selector(footer2Refresh))
        tab2TableView.footer = footer2;
        tab2TableView.footer.isAutomaticallyHidden = false;
        
        header4.setRefreshingTarget(self, refreshingAction: #selector(header4Refresh));
        tab4TableView.header = header4;
        footer4.setRefreshingTarget(self, refreshingAction: #selector(footer4Refresh))
        tab4TableView.footer = footer4;
        tab4TableView.footer.isAutomaticallyHidden = false;
        
        
        
        
        self.tab1TableView.addNoDataStatusView(showImg: "errorloading",showTitle:"加载错误\n点击屏幕刷新",refreshCallBack: {
            [unowned self] () -> Void in
            
            self.loadData1();
            
            
        });
        
        self.tab2TableView.addNoDataStatusView(showImg: "errorloading",showTitle:"加载错误\n点击屏幕刷新",refreshCallBack: {
            [unowned self] () -> Void in
            
            self.loadData2();
            
        });
        
        self.tab4TableView.addNoDataStatusView(showImg: "errorloading",showTitle:"加载错误\n点击屏幕刷新",refreshCallBack: {
            [unowned self] () -> Void in
            
            self.loadData4();
            
        });
        
        //先不调用作何请求 只调用status
        self.loadUserStatus();
 
    }
    
    func loadUserStatus() -> Void {
        AskDogGetUserStatusDataRequest().startRequest(viewController:self,autoShowErrorAlertView:false,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                
                
                //加载分类
                self.loadCategory();
                self.tab1TableView.isHidden = true;
                self.topV1.dataSource = self;
                self.showTabWithIndex(showIndex: 0);
            }
        });
        

    }
    
    func loadCategory() -> Void {
        //获取分类信息
        AskDogGetAllCategoryDataRequest().startRequest(viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                
                if(nil != arr && (arr?.count)! > 0){
                    for item in arr!{
                        let tempD:NSDictionary = item as! NSDictionary;
                        let tempData:HYCategoryTreeDataModel = HYCategoryTreeDataModel(jsonDic: tempD);
                        self.categoryDataArray.append(tempData);
                        if let na = tempData.name{
                            self.homePageTitles.append(na);
                        }else{
                            self.homePageTitles.append("无标题");
                        }
                    }
                }
                
                //self.topV1.reloadTopScrollView();
                self.topV1.setupViews();
            }
        });
    }
    
    func header1Refresh() -> Void {
        print("header1Refresh");
        
        self.loadData1();
    }
    
    func footer1Refresh() -> Void {
        print("footer1Refresh");
        
        let nPage = self.nTab1PageNumber + 1;
        
        let nSize:Int = Int(PAGE_SIZE)!;
  
        let strPage = String(nPage * nSize);
        
        let dic:[String: AnyObject]? = ["type":"homeSearch" as AnyObject,"from":strPage as AnyObject,"size":PAGE_SIZE as AnyObject];
        AskDogHomeSearchDataRequest().startRequest(requestParmer: dic, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    
                    
                    let dm:HYHomeListResultDataModel = HYHomeListResultDataModel(jsonDic: dic!);
                    self.tab1DataModel?.last = dm.last;
                    self.tab1DataModel?.total = dm.total;
                    
                    if let res = dm.result{
                        if res.count > 0{
                            self.tab1DataModel?.result?.append(contentsOf: res);
                            self.nTab1PageNumber += 1;
                            self.tab1TableView.reloadData();
                        }
                    }

                    if (true == self.tab1DataModel?.last){
                        self.tab1TableView.footer.endRefreshingWithNoMoreData();
                    }else{
                        self.tab1TableView.footer.resetNoMoreData();
                    }
                    
                }
            }
            
            self.tab1TableView.footer.endRefreshing();
        });

    }
    
    func header2Refresh() -> Void {
        print("header2Refresh");
        
        self.loadData2();
        
    }
    
    func footer2Refresh() -> Void {
        print("footer2Refresh");
        
        let nPage = self.nTab2PageNumber + 1;
        
        let nSize:Int = Int(PAGE_SIZE)!;
        let strPage = String(nPage * nSize);
        
        let dic:[String: AnyObject]? = ["from":strPage as AnyObject,"size":PAGE_SIZE as AnyObject];
        AskDogGetMySubscribedChannelsUnreadDataRequest().startRequest(requestParmer: dic, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    let dm:HYHomeChanneltDataModel = HYHomeChanneltDataModel(jsonDic: dic!);
                    self.tab2DataModel?.last = dm.last;
                    self.tab2DataModel?.total = dm.total;
                    
                    if let res = dm.result{
                        if res.count > 0{
                            self.tab2DataModel?.result?.append(contentsOf: res);
                            self.nTab2PageNumber += 1;
                            let indexSet:NSIndexSet = NSIndexSet(index: 1);
                            self.tab2TableView.reloadSections(indexSet as IndexSet, with: UITableViewRowAnimation.automatic);
                        }
                    }
                    
                    if (true == self.tab2DataModel?.last){
                        self.tab2TableView.footer.endRefreshingWithNoMoreData();
                    }else{
                        self.tab2TableView.footer.resetNoMoreData();
                    }
                    
                }


            }
            
            self.tab2TableView.footer.endRefreshing();
        });
        self.loadChanneclsListData();
    }
    
    func header4Refresh() -> Void {
        print("header4Refresh");
        
        self.loadData4();
        
    }
    
    func footer4Refresh() -> Void {
        print("footer4Refresh");
        
        let nPage = self.nTab4PageNumber + 1;
        let nSize:Int = Int(PAGE_SIZE)!;
        
        let strPage = String(nPage * nSize);
        
        let dic: [String:AnyObject] = ["type":"channel_recommend" as AnyObject, "from":strPage as AnyObject, "size":PAGE_SIZE as AnyObject]
        
        AskDogGetHomeSearchActionRequest().startRequest(requestParmer: dic, showProgressHUD:false, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            
            if beOk == true {
                if dic != nil {
                    if(nil != dic){
                        let dm:HYChannelRecommendDataModel = HYChannelRecommendDataModel(jsonDic: dic!);
                        self.tab4DataModel?.last = dm.last;
                        self.tab4DataModel?.total = dm.total;
                        
                        if let res = dm.result{
                            if res.count > 0{
                                self.tab4DataModel?.result?.append(contentsOf: res);
                                self.nTab4PageNumber += 1;
                                self.tab4TableView.reloadData();
                            }
                        }
                        
                        if (true == self.tab4DataModel?.last){
                            self.tab4TableView.footer.endRefreshingWithNoMoreData();
                        }else{
                            self.tab4TableView.footer.resetNoMoreData();
                        }
                        
                    }}
            }
        });
        self.tab4TableView.footer.endRefreshing();
    }
    

    
    @objc func btnSearchClicked(_ sender: UIButton) -> Void {
        //搜索页
        
        let controller: HYSearchViewController = HYSearchViewController()
        controller.isNavPush = false;
//        self.present(controller, animated: false) {
//            
//        }
        self.navigationController?.pushViewController(controller, animated: false);


//
//        let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: NSBundle.mainBundle());
//        self.navigationController?.pushViewController(vc, animated: true);
        
    }
    
//    @objc func btnLoginClicked(sender: UIButton) -> Void {
//        print("btnLoginClicked");
//    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func isShowReturnBtn() -> Bool {
        return false;
    }
    override func isShowNavBarTitle() -> Bool {
        return false;
    }
    override func isNeedShowNotifyBtn() -> Bool {
//        if(4 == self.curTabShowIndex){
//            return false;
//        }
        return false;
    }
    
    override func isShowLoginBtn() -> Bool {
        return false;
    }
    
    override func isShowNotifyBtn() -> Bool {
        
        let be:Bool = self.isNeedShowNotifyBtn();
        if(true == be){
            return Global.sharedInstance.isLogined;
        }
        return false;
        
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
        
        

        if(tableView == tab1TableView){
            self.tab1TableView.delayCellClicked(clickedMethod: {
                () -> Void in
                
                let data:HYHomeListDataModel = (self.tab1DataModel?.result![(indexPath as NSIndexPath).row])!;
                if let id:String = data.id{
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

        }else if(tableView == tab2TableView){
            if indexPath.section != 0 {
                self.tab2TableView.delayCellClicked(clickedMethod: {
                    () -> Void in
                    
                    let data:HYHomeChannelModel = (self.tab2DataModel?.result![(indexPath as NSIndexPath).row])!;
                    if let id:String = data.id{
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
            
            
        }else if(tableView == tab4TableView){
            
            self.tab4TableView.delayCellClicked(clickedMethod: {
                () -> Void in
                
                let data:HYChannelRecommendModel = (self.tab4DataModel?.result![(indexPath as NSIndexPath).row])!;
                if let id:String = data.id {
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
            
            
        }else if(tableView == tab5TableView){
            
            self.tab5TableView.delayCellClicked(clickedMethod: {
                () -> Void in
                
                if 1 == (indexPath as NSIndexPath).section {
                    if(0 == (indexPath as NSIndexPath).row){
                        let vc:HYMyChannelListViewController = HYMyChannelListViewController(nibName: "HYMyChannelListViewController", bundle: Bundle.main);
                        self.navigationController?.pushViewController(vc, animated: true);
                    }else if(1 == (indexPath as NSIndexPath).row){
                        let vc:HYDingyueChannelListViewController = HYDingyueChannelListViewController(nibName: "HYDingyueChannelListViewController", bundle: Bundle.main);
                        self.navigationController?.pushViewController(vc, animated: true);
                    }else if(2 == (indexPath as NSIndexPath).row){
                        let vc:HYViewHistoryViewController = HYViewHistoryViewController(nibName: "HYViewHistoryViewController", bundle: Bundle.main);
                        self.navigationController?.pushViewController(vc, animated: true);
                    }
                    
                } else if 2 == (indexPath as NSIndexPath).section {
                    if(0 == (indexPath as NSIndexPath).row){
                        let vc:HYNotifyViewController = HYNotifyViewController(nibName: "HYNotifyViewController", bundle: Bundle.main);
                        self.navigationController?.pushViewController(vc, animated: true);
                    }
                    
                } else if 3 == (indexPath as NSIndexPath).section {
                    if(0 == (indexPath as NSIndexPath).row){
                        let vc:HYMyIncomeHomeViewController = HYMyIncomeHomeViewController(nibName: "HYMyIncomeHomeViewController", bundle: Bundle.main);
                        self.navigationController?.pushViewController(vc, animated: true);
                    }else if(1 == (indexPath as NSIndexPath).row){
                        let vc:HYSafeCenterViewController = HYSafeCenterViewController(nibName: "HYSafeCenterViewController", bundle: Bundle.main);
                        self.navigationController?.pushViewController(vc, animated: true);
                    }else if(2 == (indexPath as NSIndexPath).row){
                        let vc:HYSystemSetupViewController = HYSystemSetupViewController(nibName: "HYSystemSetupViewController", bundle: Bundle.main);
                        vc.callBackFun = {
                            (be:Bool) -> Void in
                            self.showTabWithIndex(showIndex: 0);
                        };
                        self.navigationController?.pushViewController(vc, animated: true);
                    }
                }

                
//                if(1 == (indexPath as NSIndexPath).row){
//                    let vc:HYMyChannelListViewController = HYMyChannelListViewController(nibName: "HYMyChannelListViewController", bundle: Bundle.main);
//                    self.navigationController?.pushViewController(vc, animated: true);
//                }else if(2 == (indexPath as NSIndexPath).row){
//                    let vc:HYDingyueChannelListViewController = HYDingyueChannelListViewController(nibName: "HYDingyueChannelListViewController", bundle: Bundle.main);
//                    self.navigationController?.pushViewController(vc, animated: true);
//                }else if(3 == (indexPath as NSIndexPath).row){
//                    let vc:HYViewHistoryViewController = HYViewHistoryViewController(nibName: "HYViewHistoryViewController", bundle: Bundle.main);
//                    self.navigationController?.pushViewController(vc, animated: true);
//                }else if(5 == (indexPath as NSIndexPath).row){
//                    let vc:HYSafeCenterViewController = HYSafeCenterViewController(nibName: "HYSafeCenterViewController", bundle: Bundle.main);
//                    self.navigationController?.pushViewController(vc, animated: true);
//                }else if(6 == (indexPath as NSIndexPath).row){
//                    let vc:HYSystemSetupViewController = HYSystemSetupViewController(nibName: "HYSystemSetupViewController", bundle: Bundle.main);
//                    vc.callBackFun = {
//                        (be:Bool) -> Void in
//                        self.showTabWithIndex(showIndex: 0);
//                    };
//                    self.navigationController?.pushViewController(vc, animated: true);
//                }else if(4 == (indexPath as NSIndexPath).row){
//                    let vc:HYMyIncomeHomeViewController = HYMyIncomeHomeViewController(nibName: "HYMyIncomeHomeViewController", bundle: Bundle.main);
//                    self.navigationController?.pushViewController(vc, animated: true);
//                }
                
            });
            

        }
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if tableView == tab4TableView {
            return 44;
        }
        return 0;
    }
    
    func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        if tableView == tab5TableView {
            return 15
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        if(tableView == tab5TableView){
            if(0 == (indexPath as NSIndexPath).section){
                return 220;
            }
            return 55;
        }else{
            if tableView == tab2TableView {
                if(0 == (indexPath as NSIndexPath).section){
                    return 64;
                }
            } else if tableView == tab4TableView {
                if(0 == (indexPath as NSIndexPath).section){
                    return 64;
                }
            }

            if(true == IS_IPHONE6S_PLUS_DEV){
                return 330.0;
            }else if(true == IS_IPHONE66S_DEV){
                return 310.0;
            }else if(true == IS_IPHONE55S_DEV){
                return 280.0;
            }
        }
        return 340.0;
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
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if tableView == tab4TableView {
            let header = UILabel(frame: CGRect(x: 0, y: 0, width: self.view.frame.width, height: 44));
            header.text = "    推荐频道";
            header.textColor = Color_greyListText;
            header.backgroundColor = Color_greyHeaderBg;
            header.font = UIFont.systemFont(ofSize: 15.0);
            let lin: UIView = UIView(frame: CGRect(x: 0, y: 44, width: self.view.frame.width, height: 1))
            lin.backgroundColor = Color_greyLine;
            header.addSubview(lin)
            return header;
        }
        return UIView()
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        if(tableView == self.tab2TableView){
            return 2;
        }
        if tableView == self.tab5TableView {
            if self.menuDataArray.count > 0 {
                return self.menuDataArray.count + 1;
            }
            return 0
        }
        return 1;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(tableView == self.tab1TableView){
            if let arr:[HYHomeListDataModel] = self.tab1DataModel?.result{
                
                let n = arr.count;
                if(0 == n){
                    self.tab1TableView.showNoDataStatusView(beShow: true);
                    self.tab1TableView.setNoDataStatusViewTapEnable(canTap: true);
                    self.tab1TableView.header.isHidden = true;
                    self.tab1TableView.footer.isHidden = true;
                }else{
                    self.tab1TableView.setNoDataStatusViewTapEnable(canTap: false);
                    self.tab1TableView.showNoDataStatusView(beShow: false);
                    self.tab1TableView.header.isHidden = false;
                    self.tab1TableView.footer.isHidden = false;
                }
                return n;
            }
            
            // return self.tab1DataArray.count;
        }else if(tableView == self.tab2TableView){
            if section == 0 {
                if let arr:[HYChannelListDataModel] = self.channecdata {
                    if arr.count == 0 {
                        self.tab2TableView.showNoDataStatusView(beShow: true);
                        self.tab2TableView.setNoDataStatusViewTapEnable(canTap: true);
                        self.tab2TableView.header.isHidden = true;
                        self.tab2TableView.footer.isHidden = true;
                        
                        return 0;
                    }else {
                        self.tab2TableView.showNoDataStatusView(beShow: false);
                        self.tab2TableView.setNoDataStatusViewTapEnable(canTap: false);
                        self.tab2TableView.header.isHidden = false;
                        self.tab2TableView.footer.isHidden = false;
                       
                        return 1;
                    }
                }
            } else {
                if let arr:[HYHomeChannelModel] = self.tab2DataModel?.result {
                    let n = arr.count;
                    //                    if(0 == n){
                    //                        self.tab2TableView.showNoDataStatusView(beShow: true);
                    //                        self.tab2TableView.setNoDataStatusViewTapEnable(canTap: true);
                    //                        self.tab2TableView.header.isHidden = true;
                    //                        self.tab2TableView.footer.isHidden = true;
                    //                    }else{
                    //                        self.tab2TableView.showNoDataStatusView(beShow: false);
                    //                        self.tab2TableView.setNoDataStatusViewTapEnable(canTap: false);
                    //                        self.tab2TableView.header.isHidden = false;
                    //                        self.tab2TableView.footer.isHidden = false;
                    //                    }
                    return n;
                }
            }

            //return self.tab2DataArray.count;
        }else if(tableView == self.tab4TableView){
            if let arr:[HYChannelRecommendModel] = self.tab4DataModel?.result{
                let n = arr.count;
                if(0 == n){
                    self.tab4TableView.showNoDataStatusView(beShow: true);
                    self.tab4TableView.setNoDataStatusViewTapEnable(canTap: true);
                    self.tab4TableView.header.isHidden = true;
                    self.tab4TableView.footer.isHidden = true;
                }else{
                    self.tab4TableView.showNoDataStatusView(beShow: false);
                    self.tab4TableView.setNoDataStatusViewTapEnable(canTap: false);
                    self.tab4TableView.header.isHidden = false;
                    self.tab4TableView.footer.isHidden = false;
                }
                return n;
            }
            //return self.tab4DataArray.count;
        }else if(tableView == self.tab5TableView){
            if section == 0 {
                return 1;
            } else {
                let arr:[[String:String]]  = self.menuDataArray[section - 1] as! [[String:String]];
                return arr.count;
            }
//            return menuDataArray.count + 1;
        }
        return 0;
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        let cell:UITableViewCell!
        var cellID:String! = "";
        
        if(tableView == tab1TableView){
            cellID = "cell1";
        }else if(tableView == tab2TableView){
            cellID = "cell2";
        }else if(tableView == tab4TableView){
            cellID = "cell3";
        }else if(tableView == tab5TableView){
            if(0 == (indexPath as NSIndexPath).section){
                cellID = "cell4";
            }else{
                cellID = "cell5";
            }
        }
    
        cell = tableView.dequeueReusableCell(withIdentifier: cellID);
        
        if(tableView == tab1TableView){

            let temp:HYHomeListTableViewCell = cell as! HYHomeListTableViewCell;
            
            let data:HYHomeListDataModel = (self.tab1DataModel?.result![(indexPath as NSIndexPath).row])!;
            temp.setData(data);
            
            //                let dic:[String:AnyObject] = self.tab1DataArray[indexPath.row] as! [String : AnyObject];
            //                temp.setDataDic(dic);
        }else if(tableView == tab2TableView){
            
            if indexPath.section == 0 {
                let headerCell = HYHomeChanneclsTableViewCell(style: UITableViewCellStyle.default, reuseIdentifier: "headerCell");
                headerCell.selectionStyle = UITableViewCellSelectionStyle.none;
                headerCell.delegate = self;
                headerCell.setChannelData(channecdata!);
                return headerCell;
            } else {
                let temp:HYHomeListTableViewCell = cell as! HYHomeListTableViewCell;
                let data:HYHomeChannelModel = (self.tab2DataModel?.result![(indexPath as NSIndexPath).row])!;
                temp.setChannelData(data);
            }
        }else if(tableView == tab4TableView){

            let temp:HYHomeChannelListCellTableViewCell = cell as! HYHomeChannelListCellTableViewCell;
            let data:HYChannelRecommendModel = (self.tab4DataModel?.result![(indexPath as NSIndexPath).row])!;
            temp.reloadCell(model: data, index: indexPath);
            temp.delegate = self;

            
        }else if(tableView == tab5TableView){
            if(0 == (indexPath as NSIndexPath).section){
                let temp:HYUserCenterHomeTopTableViewCell = cell as! HYUserCenterHomeTopTableViewCell;
                temp.setupData();
                temp.delegate = self;
            }else{
                let temp:HYUserCenterHomePageListTableViewCell = cell as! HYUserCenterHomePageListTableViewCell;
                let arr:[[String:String]]  = menuDataArray[(indexPath as NSIndexPath).section - 1] as! [[String:String]];

                let dic:[String:AnyObject] = arr[indexPath.row] as [String:AnyObject];
                temp.setCellInfo(dic as NSDictionary);
                if(1 == (indexPath as NSIndexPath).row){
                    temp.cornerType = CornerMaskType.cornerMaskType_Top;
                }else if(menuDataArray.count == (indexPath as NSIndexPath).row){
                    temp.cornerType = CornerMaskType.cornerMaskType_Bottom;
                }
                
                temp.redPoint.isHidden = true
                if indexPath.section == 2 {
                    temp.redPoint.isHidden = !self.isNewMessage
                    self.isNewMessage = false
                }
                
            }
        }
        
        
        
        return cell;
    }
    

    //MARK:HYUserCenterHomeTopTableViewCellDelegate
    
    func hyUserCenterHomeTopTableViewCellEditBtnClicked(cell ce: HYUserCenterHomeTopTableViewCell, sectionIndex section: Int) {
        //压入编辑画面
        let vc:HYEditUserInfoViewController = HYEditUserInfoViewController(nibName: "HYEditUserInfoViewController", bundle: Bundle.main);
        self.navigationController?.pushViewController(vc, animated: true);
        
    }

    //MARK:iConsoleDelegate
    
    func handleConsoleCommand(_ command: String) -> Void {
        
        if command == "version" {
            
            iConsole.info("%@ version %@", args:getVaList([
                Bundle.main.object(forInfoDictionaryKey: "CFBundleName") as! NSString,
                Bundle.main.object(forInfoDictionaryKey: "CFBundleVersion") as! NSString]))
            
        } else {
            
            iConsole.error("unrecognised command, try 'version' instead", args:getVaList([]))

        }
    }

    //MARK:HYTopScrollMenuViewDataSource
    func numberOfViewsInTopScrollMenuView(menuView mv:HYTopScrollMenuView) -> Int {
        return self.categoryDataArray.count + 1; //0  是固定的热门
    }
    func menuViewTitlesForTopScrollMenuView(menuView mv:HYTopScrollMenuView) -> [Any] {
        return self.homePageTitles;
    }
    func topScrollMenuView(menuView mv:HYTopScrollMenuView,index ins:Int) -> UIView{
        if(0 == ins){
            let v:HYHomePageView = HYHomePageView(frame: CGRect.zero);
            v.parentVC = self;
            v.delegate = self;
            v.backgroundColor = UIColor.white;
            return v;
        }else{
            let v:HYHomeOtherPageView = HYHomeOtherPageView(frame: CGRect.zero);
            v.categoryModel = categoryDataArray[ins - 1];
            v.parentVC = self;
            v.delegate = self;
            return v;
        }
    }
    //MARK:HYHomePageViewDelegate
    func hyHomePageViewDidSearchBtnClicked(pageView v: HYHomePageView) {
        //搜索页
        let controller: HYSearchViewController = HYSearchViewController()
        self.navigationController?.pushViewController(controller, animated: true)
    }

    func hyHomePageView(pageView v: HYHomePageView, dataModel da: HYHomeListDataModel) {
    
        if let id:String = da.id{
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
    
    //MARK:HYHomeOtherPageViewDelegate
    func hyHomeOtherPageView(pageView v: HYHomeOtherPageView, dataModel da: HYHomeListDataModel) {
        
        if let id:String = da.id{
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
    
   //MARK: HYHomeChanneclsTableViewCellDelegate
    func channeclsTableViewCellSeclectMore(cell:HYHomeChanneclsTableViewCell) -> Void{
        //更多
        let vc:HYDingyueChannelListViewController = HYDingyueChannelListViewController(nibName: "HYDingyueChannelListViewController", bundle: Bundle.main,data: nil);
        self.navigationController?.pushViewController(vc, animated: true);
    }
    
    func channeclsTableViewCell(cell:HYHomeChanneclsTableViewCell, didSelectedAtInndex ins:Int) -> Void {
        
        let data:HYChannelListDataModel = self.channecdata![ins];
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
    }
    
    //MARK: HYHomeChannelListCellDelegate
    
    func channeclsListDidSelectedTableViewCell(cell: HYHomeChannelListCellTableViewCell, model: HYChannelRecommendModel, index: IndexPath) {
        
        CommonTools.checkLogin(viewController: self, afterLoginCall:nil,callBack: {
            () -> Void in
            
            //处理订阅
            if  let id: String = model.id {
                if  let isSubscribed: Bool = model.subscribed{
                    if isSubscribed == true {
                        //已订阅，取消订阅
                        let strExUrl:String = "/\(id)/subscription";
                        
                        print("strExUrl=\(strExUrl)");
                        
                        AskDogCancelSubscriptionDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                            if(true == beOk){
                                model.subscribed = false;
                                self.tab4DataModel?.result![(index as NSIndexPath).row] = model;
                                self.tab4TableView.reloadData();
                                log.ln("－tab4TableView－ 取消订阅成功")/;
                            }else{
                                log.ln("－tab4TableView－ 取消订阅失败")/;
                            }
                        });
                        
                    } else {
                        //未订阅，订阅
                        let strExUrl:String = "/\(id)/subscription";
                        
                        print("strExUrl=\(strExUrl)");
                        
                        AskDogSubscriptionDataRequest().startRequest(exUrl: strExUrl, viewController:self,autoShowErrorAlertView:false,completionHandler: {
                            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                            if(true == beOk){
                                model.subscribed = true;
                                self.tab4DataModel?.result![(index as NSIndexPath).row] = model;
                                self.tab4TableView.reloadData();
                                log.ln("－tab4TableView－ 订阅成功")/;
                            }else{
                                log.ln("－tab4TableView－ 订阅失败")/;
                            }
                        });
                        
                    }
                }
            }
            
        });
        
    }
    
}
