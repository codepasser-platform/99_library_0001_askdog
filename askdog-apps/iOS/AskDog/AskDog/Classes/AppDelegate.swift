//
//  AppDelegate.swift
//  AskDog
//
//  Created by Symond on 16/7/25.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import CoreData
import IQKeyboardManager
import SVProgressHUD


@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate,WXApiDelegate, WeiboSDKDelegate, TencentSessionDelegate {

    var window: UIWindow?
    var blockRotation: Bool = false;
    
//    var tencentOAuth:TencentOAuth!
    var tentoken: String = ""
    //var adVc:HYSplashViewController?
    
    //FIXME:以后要加入检测版本更新
    func checkUpdate() -> Void {
        print("checkUpdate has not been implemented");
    }
    
    //当我们通过标签进入app时，就会在appdelegate中调用这样一个回调，我们可以获取shortcutItem的信息进行相关逻辑操作。
    //
    //这里有一点需要注意：我们在app的入口函数：
    //
    //
    //- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions;
    //
    //也需要进行一下判断，在launchOptions中有UIApplicationLaunchOptionsShortcutItemKey这样一个键，通过它，我们可以区别是否是从标签进入的app，如果是则处理结束逻辑后，返回NO，防止处理逻辑被反复回调。
//    func application(_ application: UIApplication, performActionFor shortcutItem: UIApplicationShortcutItem, completionHandler: @escaping (Bool) -> Void) {
//        
//        print("application performActionFor in");
//        completionHandler(true);
//        
//        switch shortcutItem.type {
//        case "video":
//            print("from 3dtouch video");
//            break
//        default:
//            break;
//        }
//        
//    }
    
    
    /// 添加3Dtouch菜单
    func add3DTouchMenu(app:UIApplication) -> Void {
        //[UIApplicationShortcutItem]?
        
        
        let item1:UIApplicationShortcutItem = UIApplicationShortcutItem(type: "video",
                                                                        localizedTitle: "发布经验",
                                                                        localizedSubtitle: "视频",
                                                                        icon: UIApplicationShortcutIcon(type: .add),
                                                                        userInfo: ["key":"addVideo"]);
        
        app.shortcutItems = [item1];
    }
    
    func checkServiceInfo() -> Void {
        
        let appInfoDic:[String:AnyObject] = Bundle.main.infoDictionary! as [String : AnyObject];
        let app_version:String = appInfoDic["CFBundleShortVersionString"] as! String;
        
        let dic:[String: AnyObject] = ["version":app_version as AnyObject];
        
        let vc = self.window?.rootViewController;
        
        print("检测服务器状态！");
        
        AskDogGetServiceInfoDataRequest().startRequest(requestParmer: dic,showProgressHUD:false,viewController:vc!,autoShowErrorAlertView:false,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    let data:HYServiceInfoDataModel = HYServiceInfoDataModel(jsonDic: dic!);
                    var msg:String = "抱歉，服务异常！";
                    
                    if let stst:String = data.status{
                        
                        if let m:String = data.message{
                            msg = m;
                        }
                        
                        
                        if("OK" != stst){
                            //说明服务器有问题 当前APP 不能继续运行
                            let actionSheet:UIAlertController = UIAlertController(title: "注意", message: msg, preferredStyle: UIAlertControllerStyle.alert);
                            

                            vc!.present(actionSheet, animated: true, completion: {
                                () -> Void in
                                print("ok");
                            })
                        }
                    }
                }
            }
        });
    }
    
    func showSplashVC() -> Void {
        let adVc:HYSplashViewController = HYSplashViewController(nibName: "HYSplashViewController", bundle: Bundle.main);
        adVc.view.frame = (self.window?.bounds)!;
        
        UIApplication.shared.keyWindow?.addSubview(adVc.view);
        
//        let v:UIView = UIApplication.sharedApplication().windows[0] as UIView;
//        v.addSubview(adVc.view);
        
        //self.window?.addSubview(adVc.view);
        
//        var views:[String:AnyObject] = [String:AnyObject]();
//        views["adVc"] = adVc!.view;
//        self.window?.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:|-0-[adVc]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//        self.window?.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("V:|-0-[adVc]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
    }
    
    func saveSplashShowInfo(appVersion ver:String,beShow be:Bool) -> Void {
        //记录显示信息
        let user:UserDefaults = UserDefaults.standard;
        let record:[String:AnyObject] = ["version":ver as AnyObject,"alreadyShow":be as AnyObject];
        user.set(record, forKey: WELCOME_VIEW_SHOW_KEY);
    }
    
    func showSplash() -> Void {
        //取当前app版本
        let appInfoDic:[String:AnyObject] = Bundle.main.infoDictionary! as [String : AnyObject];
        let app_version:String = appInfoDic["CFBundleShortVersionString"] as! String;
        //判断是否显示欢迎页
        let user:UserDefaults = UserDefaults.standard;
        
       // user.removeObjectForKey(WELCOME_VIEW_SHOW_KEY);
        //self.showSplashVC();
        
        if let infoObj = user.object(forKey: WELCOME_VIEW_SHOW_KEY){
            //先取出信息
            let dic:[String:AnyObject] = infoObj as! [String:AnyObject];
            let beShow:Bool = dic["alreadyShow"] as! Bool;
            if(false == beShow){
                //没显示 显示
                self.showSplashVC();
                //记录显示信息
                self.saveSplashShowInfo(appVersion: app_version, beShow: true);
            }else{
                //如果显示已显示 但是存储的版本 和当前版本不一样 也要重新显示
                let ver:String = dic["version"] as! String;
                if(ver != app_version){
                    self.showSplashVC();
                    //记录显示信息
                    self.saveSplashShowInfo(appVersion: app_version, beShow: true);
                }
            }
        }else{
            //证明从未显示过 直接显示
            self.showSplashVC();
            //记录显示信息
            self.saveSplashShowInfo(appVersion: app_version, beShow: true);
            
        }
    }
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        
        //_ = log.url("hhhhhhh ")/
        
        //FIXME:先关掉
        //添加3D TOUCH 菜单
        //self.add3DTouchMenu(app:application);
        
        let shouldPerformAdditionalDelegateHandling:Bool = true;
        
//        print("launchOptions = \(launchOptions)");
//        if(nil != launchOptions){
//            
//            let item:UIApplicationShortcutItem = launchOptions?[UIApplicationLaunchOptionsKey.shortcutItem] as! UIApplicationShortcutItem;
//           // application.shortcutItems = item;
//            print("Shortcut: \(item)")
//            shouldPerformAdditionalDelegateHandling = false;
//        }

        //注册微信
        WXApi.registerApp(WEIXIN_APP_ID);
        //注册新浪
        WeiboSDK.enableDebugMode(false) //TODO
        WeiboSDK.registerApp(WEIBO_APPKEY) 
        
        //注册腾讯 TODO
        HYQQApiManager.sharedInstance.tencentOAuth = TencentOAuth(appId: TENCENT_APPID, andDelegate: self)
        
        //初始化一些系统设置
        self.initState();
        
        let screenFrame:CGRect = UIScreen.main.bounds;
        
        if(true == OPEN_ICONSOLE){
            self.window = iConsoleWindow(frame: screenFrame);
        }else{
            self.window = UIWindow(frame: screenFrame);
        }
        
        
        var rootV:HYNavigationViewController!
        
        let home:HYHomeViewController! = HYHomeViewController(nibName: "HYHomeViewController", bundle: Bundle.main);
        rootV = HYNavigationViewController(rootViewController: home);
        rootV.setNavigationBarHidden(true, animated: false);
        self.window!.rootViewController = rootV;
        
        //let adVc:HYSplashViewController = HYSplashViewController(nibName: "HYSplashViewController", bundle: NSBundle.mainBundle());
        //self.window!.rootViewController = adVc;

        
        
        self.window!.makeKeyAndVisible();
        
        self.showSplash();
        
        //处理键盘
        IQKeyboardManager.shared().isEnabled = true;
        IQKeyboardManager.shared().shouldResignOnTouchOutside = true;
        //设置HUD样式
        SVProgressHUD.setDefaultStyle(.dark);
        SVProgressHUD.setDefaultMaskType(.none);
        SVProgressHUD.setDefaultAnimationType(.native);
        SVProgressHUD.setMinimumDismissTimeInterval(5);
        
        //检测版本
        self.checkUpdate();
        //self.checkServiceInfo();
        
        return shouldPerformAdditionalDelegateHandling
    }
    
    func application(_ application: UIApplication, supportedInterfaceOrientationsFor window: UIWindow?) -> UIInterfaceOrientationMask {
        if self.blockRotation{
            return UIInterfaceOrientationMask.all
        } else {
            return UIInterfaceOrientationMask.portrait
        }
    }
    
    //weixin api
    func application(_ application: UIApplication, handleOpen url: URL) -> Bool {
        if url.scheme == WEIXIN_APP_ID{
            return WXApi.handleOpen(url, delegate: self);
        }
        else if url.scheme == "tencent1105654292" {
            return TencentOAuth.handleOpen(url)
        }
        else if url.scheme == "QQ41e6f214" {
            return QQApiInterface.handleOpen(url, delegate: HYQQShareDelegate.sharedManger())
        }
        else if url.scheme == "wb691533910" {
            return WeiboSDK.handleOpen(url, delegate: self)
        }
        else {
            return true
        }
    }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplicationOpenURLOptionsKey : Any]) -> Bool {
        if url.scheme == WEIXIN_APP_ID{
            return WXApi.handleOpen(url, delegate: self);
        }
        else if url.scheme == "tencent1105654292" {
            return TencentOAuth.handleOpen(url)
        }
        else if url.scheme == "QQ41e6f214" {
            return QQApiInterface.handleOpen(url, delegate: HYQQShareDelegate.sharedManger())
        }
        else if url.scheme == "wb691533910" {
            return WeiboSDK.handleOpen(url, delegate: self)
        }
        else {
            return true
        }
    }
    
    func application(_ application: UIApplication, open url: URL, sourceApplication: String?, annotation: Any) -> Bool {
        if url.scheme == WEIXIN_APP_ID{
            return WXApi.handleOpen(url, delegate: self);
        }
        else if url.scheme == "tencent1105654292" {
            return TencentOAuth.handleOpen(url)
        }
        else if url.scheme == "QQ41e6f214" {
            return QQApiInterface.handleOpen(url, delegate: HYQQShareDelegate.sharedManger())
        }
        else if url.scheme == "wb691533910" {
            return WeiboSDK.handleOpen(url, delegate: self)
        }
        else {
            return true
        }
    }
    
    
    //MARK:WXApiDelegate
    func onReq(_ req: BaseReq!) {
        print("onReq in");
    }
    
    func onResp(_ resp: BaseResp!) {
        print("onResp in");
        
        HYWXApiManager.sharedInstance.receiveResp(resp);
    }
    
    
    //MARK: - weibo Delegate
    func didReceiveWeiboRequest(_ request: WBBaseRequest!) {
        
    }
    
    func didReceiveWeiboResponse(_ response: WBBaseResponse) {
        
        HYWBApiManager.sharedInstance.sinaBack(response)
    }
    
    
    //MARK: - Tencent Delegate
    func tencentDidLogin() {
        HYQQApiManager.sharedInstance.tencentBack(true, cancelled:true)
    }
    
    func tencentDidNotLogin(_ cancelled: Bool) {
        HYQQApiManager.sharedInstance.tencentBack(false, cancelled:cancelled)
    }
    
    func tencentDidNotNetWork() {
        HYQQApiManager.sharedInstance.tencentNotNetWorkBack()
    }
    
    func tencentDidUpdate(_ tencentOAuth: TencentOAuth) {
        HYQQApiManager.sharedInstance.tencentUpdate(tencentOAuth)
    }
    
    func getUserInfoResponse(_ response: APIResponse) {
        HYQQApiManager.sharedInstance.userInfoResponse(response)
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
        self.checkServiceInfo();
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
        // Saves changes in the application's managed object context before the application terminates.
        self.saveContext()
    }

    // MARK: - Core Data stack

    lazy var applicationDocumentsDirectory: URL = {
        // The directory the application uses to store the Core Data store file. This code uses a directory named "com.hooying.AskDog" in the application's documents Application Support directory.
        let urls = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        return urls[urls.count-1]
    }()

    lazy var managedObjectModel: NSManagedObjectModel = {
        // The managed object model for the application. This property is not optional. It is a fatal error for the application not to be able to find and load its model.
        let modelURL = Bundle.main.url(forResource: "AskDog", withExtension: "momd")!
        return NSManagedObjectModel(contentsOf: modelURL)!
    }()

    lazy var persistentStoreCoordinator: NSPersistentStoreCoordinator = {
        // The persistent store coordinator for the application. This implementation creates and returns a coordinator, having added the store for the application to it. This property is optional since there are legitimate error conditions that could cause the creation of the store to fail.
        // Create the coordinator and store
        let coordinator = NSPersistentStoreCoordinator(managedObjectModel: self.managedObjectModel)
        let url = self.applicationDocumentsDirectory.appendingPathComponent("SingleViewCoreData.sqlite")
        var failureReason = "There was an error creating or loading the application's saved data."
        do {
            try coordinator.addPersistentStore(ofType: NSSQLiteStoreType, configurationName: nil, at: url, options: nil)
        } catch {
            // Report any error we got.
            var dict = [String: AnyObject]()
            dict[NSLocalizedDescriptionKey] = "Failed to initialize the application's saved data" as AnyObject?
            dict[NSLocalizedFailureReasonErrorKey] = failureReason as AnyObject?

            dict[NSUnderlyingErrorKey] = error as NSError
            let wrappedError = NSError(domain: "YOUR_ERROR_DOMAIN", code: 9999, userInfo: dict)
            // Replace this with code to handle the error appropriately.
            // abort() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
            NSLog("Unresolved error \(wrappedError), \(wrappedError.userInfo)")
            abort()
        }
        
        return coordinator
    }()

    lazy var managedObjectContext: NSManagedObjectContext = {
        // Returns the managed object context for the application (which is already bound to the persistent store coordinator for the application.) This property is optional since there are legitimate error conditions that could cause the creation of the context to fail.
        let coordinator = self.persistentStoreCoordinator
        var managedObjectContext = NSManagedObjectContext(concurrencyType: .mainQueueConcurrencyType)
        managedObjectContext.persistentStoreCoordinator = coordinator
        return managedObjectContext
    }()

    // MARK: - Core Data Saving support

    func saveContext () {
        if managedObjectContext.hasChanges {
            do {
                try managedObjectContext.save()
            } catch {
                // Replace this implementation with code to handle the error appropriately.
                // abort() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                let nserror = error as NSError
                NSLog("Unresolved error \(nserror), \(nserror.userInfo)")
                abort()
            }
        }
    }
    
    func initState() -> Void {
        let user:UserDefaults = UserDefaults.standard;
        if(nil == user.object(forKey: WIFI_PLAY_KEY)){
            user.set(true, forKey: WIFI_PLAY_KEY);
        }
    }
    


}

