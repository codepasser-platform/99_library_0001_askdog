//
//  HYBaseViewController.swift
//  AskDog
//
//  Created by Symond on 16/7/25.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import Alamofire
import SVProgressHUD



//UIViewControllerAnimatedTransitioning
class HYBaseViewController: UIViewController {
    
    var navBarView:UIView!
    var lblNavBarTitle:UILabel!
    var btnNavBarBack:UIButton!
    var btnLogin:UIButton!
    var btnNotify:UIButton!
    var notifyImgView:UIImageView!
    var notifyNumberImgView:UIImageView!
    var lblNotifyNumber:UILabel!
    
    var stringNavBarTitle:String!
    var stringNavBarBackBtnTitle:String!
    var navigationStyle:UINavigationControllerOperation!
    
    var transition:UIViewControllerAnimatedTransitioning!
    
    init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?,data da:AnyObject?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil);
        stringNavBarTitle = self.getNavBarTitle();
        stringNavBarBackBtnTitle = "返回";
    }
    
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?)
    {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil);
        stringNavBarTitle = self.getNavBarTitle();
        stringNavBarBackBtnTitle = "返回";
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    /**
     认证过时 重新登陆
     */
    func doRelogin() -> Void {
        print("doRelogin");
        Global.sharedInstance.isLogined = false;
        self.refreshNavBarBtnStatus();
    }
    
    func refreshNavBarBtnStatus() -> Void {
        navBarView.isHidden = !isShowNavBar();
        lblNavBarTitle.isHidden = !isShowNavBarTitle();
        btnNavBarBack.isHidden = !isShowReturnBtn();
        btnLogin.isHidden = !isShowLoginBtn();
        
        let beShowNotify = isShowNotifyBtn();
        if(true == beShowNotify){
            notifyImgView.isHidden = false;
            notifyNumberImgView.isHidden = false;
            lblNotifyNumber.isHidden = false;
            btnNotify.isHidden = false;
            //更新通知状态
            self.setNotifyInfo(Global.sharedInstance.userInfo?.notice_count);
        }else{
            notifyImgView.isHidden = true;
            notifyNumberImgView.isHidden = true;
            lblNotifyNumber.isHidden = true;
            btnNotify.isHidden = true;
        }
        

    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated);
        self.refreshNavBarBtnStatus();
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated);
        
        //self.navigationController?.interactivePopGestureRecognizer?.enabled = true;
    }
    
    func shouldUpdateData() -> Void {
        print("shouldUpdateData in self=\(self)");
    }


    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        transition = MyAnimator();

        // Do any additional setup after loading the view.
        
        //加一个自定义的导航栏
        

        //navBarView = UIView(frame: CGRectMake(0, 0, DEVICE_WIDTH, NAV_BAR_HEIGHT));
        navBarView = UIView(frame: CGRect.zero);
        navBarView.backgroundColor = CommonTools.rgbColor(red: 0, green: 170, blue: 239);
        navBarView.translatesAutoresizingMaskIntoConstraints = false;
        var views:[String:AnyObject] = [String:AnyObject]();
        views["navBarView"] = navBarView;
        self.view.addSubview(navBarView);
        //加阴影
        /**
         zhao.dy 9.23
         去阴影
         */
//        navBarView.layer.shadowColor = UIColor.black.cgColor;
//        navBarView.layer.shadowOffset = CGSize(width: 0, height: 1);
//        navBarView.layer.shadowOpacity = 0.3;
//        navBarView.layer.shadowRadius = 2;
        
        
        navBarView.isHidden = !isShowNavBar();
        self.view.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[navBarView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.view.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-[navBarView]", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.view.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-[navBarView(64)]", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        //lblNavBarTitle = UILabel(frame: CGRectMake(150, 20, DEVICE_WIDTH-150*2, NAV_BAR_HEIGHT-20));
        lblNavBarTitle = UILabel(frame: CGRect.zero);
        lblNavBarTitle.backgroundColor = UIColor.clear;
        lblNavBarTitle.textAlignment = NSTextAlignment.center;
        //lblNavBarTitle.textColor = CommonTools.rgbColor(red: 26, green: 26, blue: 26);
        lblNavBarTitle.textColor = UIColor.white;
        lblNavBarTitle.font = UIFont.systemFont(ofSize: 20);
        lblNavBarTitle.translatesAutoresizingMaskIntoConstraints = false;
        print("set+[\(self)]==lblNavBarTitle=\(stringNavBarTitle)");
        lblNavBarTitle.text = stringNavBarTitle;
        navBarView.addSubview(lblNavBarTitle);
        
        var views1:[String:AnyObject] = [String:AnyObject]();
        views1["lblNavBarTitle"] = lblNavBarTitle;
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-100-[lblNavBarTitle]-100-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-20-[lblNavBarTitle]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        //        navBarView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("V:|-[lblNavBarTitle(44)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        
        lblNavBarTitle.isHidden = !isShowNavBarTitle();

        
        btnNavBarBack = UIButton(type: UIButtonType.custom);
        //btnNavBarBack.frame = CGRectMake(0, 20, 150, NAV_BAR_HEIGHT-20);
        btnNavBarBack.frame = CGRect.zero;
        //btnNavBarBack.setTitle(stringNavBarBackBtnTitle, forState: UIControlState.Normal);
        btnNavBarBack.setTitleColor(UIColor.red, for: UIControlState());
        btnNavBarBack.imageEdgeInsets = UIEdgeInsetsMake(18,10,8,78);
        btnNavBarBack.addTarget(self, action: #selector(btnReturnClicked), for: UIControlEvents.touchUpInside);
        //btnNavBarBack.backgroundColor = UIColor.yellow;
        btnNavBarBack.translatesAutoresizingMaskIntoConstraints = false;
        btnNavBarBack.setImage(UIImage(named: "return"), for: UIControlState());
        navBarView.addSubview(btnNavBarBack);
        btnNavBarBack.isHidden = !isShowReturnBtn();
        views1["btnNavBarBack"] = btnNavBarBack;
        //navBarView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:|-[btnNavBarBack]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[btnNavBarBack(100)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-20-[btnNavBarBack(44)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        
        //登录按钮
        btnLogin = UIButton(type: UIButtonType.custom);
        btnLogin.frame = CGRect.zero;
        btnLogin.setTitle("登录", for: UIControlState());
        btnLogin.setTitleColor(CommonTools.rgbColor(red: 135, green: 146, blue: 164), for: UIControlState());
        btnLogin.addTarget(self, action: #selector(btnLoginClicked), for: UIControlEvents.touchUpInside);
        btnLogin.backgroundColor = UIColor.clear;
        btnLogin.translatesAutoresizingMaskIntoConstraints = false;
        navBarView.addSubview(btnLogin);
        views1["btnLogin"] = btnLogin;
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnLogin(60)]-4-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-27-[btnLogin]-7-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        
        btnLogin.isHidden = !isShowLoginBtn();
        
        //通知按钮
        //铃铛
        notifyImgView = UIImageView(frame: CGRect.zero);
        notifyImgView.translatesAutoresizingMaskIntoConstraints = false;
        notifyImgView.image = UIImage(named: "notify");
        navBarView.addSubview(notifyImgView);
        notifyImgView.isHidden = !isShowNotifyBtn();
        views1["notifyImgView"] = notifyImgView;
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[notifyImgView(30)]-15-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-32-[notifyImgView(30)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        //红圆点
        notifyNumberImgView = UIImageView(frame: CGRect.zero);
        notifyNumberImgView.translatesAutoresizingMaskIntoConstraints = false;
        notifyNumberImgView.image = UIImage(named: "redpoint");
        navBarView.addSubview(notifyNumberImgView);
        notifyNumberImgView.isHidden = !isShowNotifyBtn();
        views1["notifyNumberImgView"] = notifyNumberImgView;
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[notifyNumberImgView(20)]-13-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-26-[notifyNumberImgView(20)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        
        lblNotifyNumber = UILabel(frame: CGRect.zero);
        lblNotifyNumber.backgroundColor = UIColor.clear;
        lblNotifyNumber.textAlignment = NSTextAlignment.center;
        lblNotifyNumber.textColor = UIColor.white;
        lblNotifyNumber.font = UIFont.systemFont(ofSize: 8);
        lblNotifyNumber.adjustsFontSizeToFitWidth = true;
        lblNotifyNumber.translatesAutoresizingMaskIntoConstraints = false;
        lblNotifyNumber.text = "99";
        navBarView.addSubview(lblNotifyNumber);
        lblNotifyNumber.isHidden = !isShowNotifyBtn();
        views1["lblNotifyNumber"] = lblNotifyNumber;
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[lblNotifyNumber(14)]-19-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-27-[lblNotifyNumber(14)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        
        
        btnNotify = UIButton(type: UIButtonType.custom);
        btnNotify.frame = CGRect.zero;
        btnNotify.setTitle("", for: UIControlState());
        btnNotify.addTarget(self, action: #selector(btnNotifyClicked), for: UIControlEvents.touchUpInside);
        btnNotify.backgroundColor = UIColor.clear;
        btnNotify.translatesAutoresizingMaskIntoConstraints = false;
        //btnNotify.imageEdgeInsets = UIEdgeInsetsMake(0, 20, -10, 0);
        navBarView.addSubview(btnNotify);
        views1["btnNotify"] = btnNotify;
        btnNotify.isHidden = !isShowNotifyBtn();
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnNotify(50)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-20-[btnNotify]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @objc func btnReturnClicked(_ sender: UIButton) -> Void {
       // self.navigationController?.popViewController(animated: true);
        _ = self.navigationController?.popViewController(animated: true);
    }
    
    @objc func btnLoginClicked(_ sender: UIButton) -> Void {
        print("btn login clicked");
        let login:HYLoginViewController = HYLoginViewController(nibName: "HYLoginViewController", bundle: Bundle.main);
//        login.callBackFun = {
//            (beOK:Bool)-> Void in
//            if(true == beOK){
//                let register:HYRegisterViewController = HYRegisterViewController(nibName: "HYRegisterViewController", bundle: NSBundle.mainBundle());
//                self.navigationController?.pushViewController(register, animated: true);
//            }
//        };
        
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
    }
    
    @objc func btnNotifyClicked(_ sender: UIButton) -> Void {
        print("btn notify clicked");
        let vc:HYNotifyViewController = HYNotifyViewController(nibName: "HYNotifyViewController", bundle: Bundle.main);
        self.navigationController?.pushViewController(vc, animated: true);
    }
    
    func isShowReturnBtn() -> Bool {
        return true;
    }
    
    func isShowNavBarTitle() -> Bool {
        return true;
    }
    
    func isShowNavBar() -> Bool {
        return true;
    }
    
    func isShowLoginBtn() -> Bool {
        
        return !Global.sharedInstance.isLogined;
    }
    
    func isShowNotifyBtn() -> Bool {
        
        return false;
        
    }
    
    func isNeedShowNotifyBtn() -> Bool {
        return false;
    }
    
    func getNavBarTitle() -> String {
        return "";
    }
    
    func setNavBarTitle(_ title:String) -> Void {
        lblNavBarTitle.text = title;
    }
    
    func setNotifyInfo(_ nNoticeCount:Int!) -> Void {
        
        if let n = nNoticeCount{
            if(n <= 0){
                lblNotifyNumber.isHidden = true;
                notifyNumberImgView.isHidden = true;
            }else if(n > 0 && n <= 99){
                let str:String = String(n);
                lblNotifyNumber.text = str;
                lblNotifyNumber.isHidden = false;
                notifyNumberImgView.isHidden = false;
            }else{
                lblNotifyNumber.text = "99+";
                lblNotifyNumber.isHidden = false;
                notifyNumberImgView.isHidden = false;
            }
        }else{
            lblNotifyNumber.isHidden = true;
            notifyNumberImgView.isHidden = true;
        }
        

    }
    
    //MARK:UINavigationControllerDelegate
//    func navigationController(navigationController: UINavigationController, animationControllerForOperation operation: UINavigationControllerOperation, fromViewController fromVC: UIViewController, toViewController toVC: UIViewController) -> UIViewControllerAnimatedTransitioning?{
//        return transition;
//    }
    
//    //MARK:UIViewControllerAnimatedTransitioning
//    func transitionDuration(transitionContext: UIViewControllerContextTransitioning?) -> NSTimeInterval
//    {
//        return 1;
//    }
//    
//    func animateTransition(transitionContext: UIViewControllerContextTransitioning){
//        
//        let containerView = transitionContext.containerView()
//        let toViewController = transitionContext.viewControllerForKey(UITransitionContextToViewControllerKey)
//        let fromViewController = transitionContext.viewControllerForKey(UITransitionContextFromViewControllerKey)
//        
//        var destView: UIView!
//        var destTransform: CGAffineTransform!
//        if navigationStyle == UINavigationControllerOperation.Push {
//            containerView!.insertSubview(toViewController!.view, aboveSubview: fromViewController!.view)
//            destView = toViewController!.view
//            destView.transform = CGAffineTransformMakeScale(0.1, 0.1)
//            destTransform = CGAffineTransformMakeScale(1, 1)
//        } else if navigationStyle == UINavigationControllerOperation.Pop {
//            containerView!.insertSubview(toViewController!.view, belowSubview: fromViewController!.view)
//            destView = fromViewController!.view
//            // 如果IDE是Xcode6 Beta4+iOS8SDK，那么在此处设置为0，动画将不会被执行(不确定是哪里的Bug)
//            destTransform = CGAffineTransformMakeScale(0.1, 0.1)
//        }
//        UIView.animateWithDuration(transitionDuration(transitionContext), animations: {
//            destView.transform = destTransform
//            }, completion: ({completed in
//                transitionContext.completeTransition(true)
//            }))
//        
//        
////        UIView.beginAnimations(nil, context: nil)
////        UIView.setAnimationDuration(2.0)
////        imageView.center = CGPointMake(250, 250)
////        UIView.setAnimationCurve(UIViewAnimationCurve.EaseOut) //设置动画相对速度
////        UIView.commitAnimations()
//
//    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
