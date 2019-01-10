//
//  HYLoginViewController.swift
//  AskDog
//
//  Created by Symond on 16/7/27.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

enum LoginReturnStyle  : Int {
    case loginReturnStyle_dismiss
    case loginReturnStyle_pop_animated
    case loginReturnStyle_pop_no_animated
}


typealias loginCallBack = (_ beOK:Bool)->Void;

class HYLoginViewController: HYBaseViewController, WBHttpRequestDelegate {


    @IBOutlet weak var imgLogo: UIImageView!
    @IBOutlet weak var btnWeibo: UIButton!
    @IBOutlet weak var btnQQ: UIButton!
    @IBOutlet weak var btnWeixin: UIButton!
    @IBOutlet weak var buttonRegister: UIButton!
    @IBOutlet weak var buttonLogin: UIButton!
    @IBOutlet weak var btnForgetPwd: UIButton!
    @IBOutlet weak var tfPassword: UITextField!
    @IBOutlet weak var tfUsername: UITextField!
    @IBOutlet weak var logoImgWidth: NSLayoutConstraint!
    
    var callBackLoginedFun:loginCallBack?
    var callBackFunForgetPwd:loginCallBack?
    var reloadLoginedHomePageDataCallBackFun:loginCallBack?
   // var callBackGoXieyi:loginCallBack?
    var callBackGoCategories:loginCallBack?
    var callWeixinFun:loginCallBack?
    var callWeiboFun:loginCallBack?   
    var callQQFun:loginCallBack?
    var callAfterLoginFun:loginCallBack?
    
    var isNeedGoXieyi:Bool = false;
    var isNeedGoCategory:Bool = false;
    var isTempLogin:Bool = false;
    var returnStyle:LoginReturnStyle = .loginReturnStyle_dismiss;
    var thirdLog = false
    
    //MARK: - sina
    @IBAction func btnWeiboClicked(_ sender: UIButton) {
       
//        self.dismissViewControllerAnimated(false, completion: {
//            ()->Void in
//            print("dismissViewControllerAnimated ok");
//            if let fun:loginCallBack = self.callWeiboFun{
//                fun(beOK:true);
//            }
//        })
        
        HYWBApiManager.sharedInstance.wbSSORequest { (beOK, response) in
            if beOK == true {
                self.wbLoginSuccess()
            } else {
                self.wbLoginFailure()
            }
        }
        
//        let wbtoken = NSUserDefaults.standardUserDefaults().valueForKey(WB_TOKEN) as? String
//        WeiboSDK .logOutWithToken(wbtoken, delegate: self, withTag: "user")
        
    }
    
    //获取新浪微博权限成功
    func wbLoginSuccess( ) {
        
        let wbtoken = UserDefaults.standard.value(forKey: WB_TOKEN) as? String
        let wbuserID = UserDefaults.standard.value(forKey: WB_USERID) as? String
        
        //获取sina用户信息
        _ = WBHttpRequest(forUserProfile: wbuserID, withAccessToken: wbtoken, andOtherProperties: nil, queue: nil, withCompletionHandler: {
            (httpRequest, result, error) in
            if error == nil {
                //TODO 设置头像，昵称等用户信息
                let userInfo = result as! WeiboUser
                print(userInfo.name, userInfo.profileImageUrl)
            } else {
                print("获取sina用户信息获取sina用户信息error = \(error)")
            }

       })
    }
    
    

    
    //获取新浪微博权限失败
    func wbLoginFailure() {

    }
    
    //MARK: - tencent
    @IBAction func btnQQClicked(_ sender: UIButton) {

//        if !thirdLog {
//            self.dismissViewControllerAnimated(false, completion: {
//                ()->Void in
//                print("dismissViewControllerAnimated ok");
//                if let fun:loginCallBack = self.callWeiboFun{
//                    fun(beOK:true);
//                }
//                
//            })
//            return
//        }
        HYQQApiManager.sharedInstance.tencentLoginRequest { (beOk:Bool?, tencentOAuth: TencentOAuth?) in
            /*
             1.如果需要保存授权信息，
             需要保存登录完成后返回的accessToken，openid 和 expirationDate三个数据，
             下次登录的时候直接将这三个数据是设置到TencentOAuth对象中
             
             2.调用getUserInfo接口获得该用户的头像、昵称并显示在界面上
             */
            if beOk == true {
//                let qqtoken = NSUserDefaults.standardUserDefaults().valueForKey(QQ_TOKEN) as? String
                
                self.reloadLoginedHomePageDataCallBackFun!(true);
                self.dismiss(animated: true, completion: nil);
                
            } else {
                //TODO
            }

        }
        

    }
    
    
    
    //MARK: - Weixin
    @IBAction func btnWeixinClicked(_ sender: UIButton) {
        

//        self.dismissViewControllerAnimated(false, completion: {
//            ()->Void in
//            print("dismissViewControllerAnimated ok");
//            if let fun:loginCallBack = self.callWeixinFun{
//                fun(beOK:true);
//            }
//            
//        })
        

        HYWXApiManager.sharedInstance.sendAuthRequest({
            (beOK:Bool?,respone:SendAuthResp?,needInstallWX:Bool?) -> Void in
            
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
                //取token
                let dic:[String: AnyObject] = ["appid":WEIXIN_APP_ID as AnyObject,"secret":WEIXIN_APP_SECRET as AnyObject,"code":respone!.code as AnyObject,"grant_type":"authorization_code" as AnyObject];
                
                
                AskDogGetWeixinTokenByCodeDataRequest().startRequest(requestParmer: dic, viewController:self, completionHandler: {
                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                    if(true == beOk){
                        if(nil != dic){
                            let data:HYWeiXinAuthDataModel = HYWeiXinAuthDataModel(jsonDic: dic!);
                            
                            let dicInfo:[String: AnyObject] = ["access_token":data.access_token! as AnyObject,"openid":data.openid! as AnyObject];
                            
                            
                            AskDogGetWeixinUserInfoDataRequest().startRequest(requestParmer: dicInfo, viewController:self, completionHandler: {
                                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                                if(true == beOk){
                                    if(nil != dic){
                                        let userInfo:HYWeiXinUserInfoDataModel = HYWeiXinUserInfoDataModel(jsonDic: dic!);
                                        
                                        print("userinfo = \(userInfo)");
                                    }
                                }
                            });
                            
                        }
                    }
                });
            }
 
        });
    }
    
    @IBAction func btnShowPasswordClicked(_ sender: UIButton) {
        let be:Bool = self.tfPassword.isSecureTextEntry;
        self.tfPassword.isSecureTextEntry = !be;
    }
    
    @IBAction func btnForgetClicked(_ sender: UIButton) {

        
        self.dismiss(animated: false, completion: {
            ()->Void in
            print("dismissViewControllerAnimated ok");
            self.callBackFunForgetPwd!(true);
        })
    }
    
    override func btnReturnClicked(_ sender: UIButton) -> Void {
        if(returnStyle == .loginReturnStyle_dismiss){
            self.dismiss(animated: true, completion: nil);
        }else if(returnStyle == .loginReturnStyle_pop_animated){
            _ = self.navigationController?.popViewController(animated: true);
        }else{
            _ = self.navigationController?.popViewController(animated: false);
        }
        
    }
    
    @IBAction func buttonRegisterClicked(_ sender: UIButton) {
//        self.dismissViewControllerAnimated(false, completion: {
//            ()->Void in
//            print("dismissViewControllerAnimated ok");
//            self.callBackFun!(beOK:true);
//        })
        
        let register:HYRegisterViewController = HYRegisterViewController(nibName: "HYRegisterViewController", bundle: Bundle.main);
        register.callBackFun = {
            (beOK:Bool) -> Void in
//            if(true == beOK){
//                //self.isNeedGoXieyi = true;
//                self.isNeedGoCategory = true;
//            }else{
//                self.isNeedGoXieyi = false;
//            }
        }
        self.present(register, animated: true, completion: nil);
        
    }
    
    func setLogo(userName name:String) -> Void {
        //取一下用户保存的头像
        
        //user.removeObjectForKey(LOGIN_USER_HEADER_IMG_KEY);
        
        let user:UserDefaults = UserDefaults.standard;
        if let username = user.object(forKey: LOGIN_USERNAME_KEY){
            
            let na:String = username as! String;
            //说明有头像
            if(na == name){
                
                if let path = user.object(forKey: LOGIN_USER_HEADER_IMG_KEY){
                    let str:String = path as! String;
                    
                    self.imgLogo.sd_setImage(with: URL(string: str),placeholderImage: UIImage(named: "logo"));
                    self.imgLogo.layer.cornerRadius = self.logoImgWidth.constant / 2.0;
                    self.imgLogo.layer.masksToBounds = true;
                }
            }else{

                self.imgLogo.image = UIImage(named: "logo");
            }
        }
    }
    
    func textFieldValueChanged() -> Void {
        

        if(nil == self.tfUsername.markedTextRange){
           // print("change to \(self.tfUsername.text)");
            
            //匹配当前存储的帐号  如果匹配 就取头像  如果 不匹配  显示默认 logo
            self.setLogo(userName:self.tfUsername.text!);

//            }
        }
        

    }
    
    override func viewDidLoad() {
        stringNavBarTitle = "账户登录";

        super.viewDidLoad()
        
        NotificationCenter.default.addObserver(self, selector: #selector(textFieldValueChanged), name: NSNotification.Name.UITextFieldTextDidChange, object: self.tfUsername);

        // Do any additional setup after loading the view.
        buttonLogin.layer.cornerRadius = 5;
        buttonRegister.layer.cornerRadius = 5;
        buttonRegister.layer.borderWidth = 1;
        buttonRegister.layer.borderColor = UIColor(red: 50/255, green: 87/255, blue: 121/255, alpha: 1.0).cgColor;
        
        //init data for debug
        self.tfUsername.text = DEFAULT_USERNAME;
        //self.tfUsername.text = "270827162@qq.com";
        self.tfPassword.text = DEFAULT_PASSWORD;
        
        //读取保存的账号密码
        let user:UserDefaults = UserDefaults.standard;
        if((nil != user.object(forKey: LOGIN_USERNAME_KEY)) && (nil != user.object(forKey: LOGIN_PASSWORD_KEY))){
            
            self.tfUsername.text = user.string(forKey: LOGIN_USERNAME_KEY);
            self.tfPassword.text = user.string(forKey: LOGIN_PASSWORD_KEY);
        }
        
        
       // self.tfUsername.text = "";
      //  self.tfPassword.text = "";
        
        self.setLogo(userName: self.tfUsername.text!);
        
        self.btnQQ.isHidden = true;
        self.btnWeibo.isHidden = true;
        self.btnWeixin.isHidden = true;
        
        
    }
    
    @IBAction func buttonLoginClicked(_ sender: UIButton) {
//        let regiser:HYRegisterViewController = HYRegisterViewController(nibName: "HYRegisterViewController", bundle: NSBundle.mainBundle());
//        self.navigationController?.pushViewController(regiser, animated: true);
        
        guard self.tfUsername.text != "" else{
            CommonTools.showMessage("手机/邮箱地址不能为空!",  vc: self,  hr: {
                ()->Void in
                self.tfUsername.becomeFirstResponder();
                self.tfPassword.resignFirstResponder();
            });
            return;
        }
        
        var beMail:Bool = false;
        var bePhone:Bool = false;
        
        if(CommonTools.EmailIsValidated(self.tfUsername.text!)){
            beMail = true;
        }
        
        if(CommonTools.PhoneNumberIsValidated(self.tfUsername.text!)){
            bePhone = true;
        }
        
        if(false == beMail && false == bePhone){
            CommonTools.showMessage("手机/邮箱地址格式错误!",  vc: self,  hr: {
                ()->Void in
                self.tfPassword.becomeFirstResponder();
                self.tfUsername.resignFirstResponder();
            });
            return;
        }
        
        guard self.tfPassword.text != "" else{
            CommonTools.showMessage("登录密码不能为空!",  vc: self,  hr: {
                ()->Void in
                self.tfPassword.becomeFirstResponder();
                self.tfUsername.resignFirstResponder();
            });
            return;
        }
        
        self.tfPassword.resignFirstResponder();
        self.tfUsername.resignFirstResponder();
        

        let dic:[String: AnyObject] = ["username":self.tfUsername.text! as AnyObject,"password":self.tfPassword.text! as AnyObject,
                                       "remember_me":true as AnyObject];
        
        AskDogLoginDataRequest().startRequest(requestParmer: dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                
                
//                //取个人信息
//                //然后取个人信息
//                AskDogGetUserInfoDataRequest().startRequest( viewController:self,completionHandler: {
//                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
//                    if(true == beOk){
//                        
//                        if(nil != dic){
//                            print("AskDogGetUserInfoDataRequest ok");
//                            if let resultDic:NSDictionary = dic{
//                                let userInfo:HYUserInfoDataModel = HYUserInfoDataModel(jsonDic: resultDic);
//                                Global.sharedInstance.userInfo = userInfo;
//                            }
//                           // let indexs:[NSIndexPath] = [NSIndexPath(forRow: 0, inSection: 0)];
//                           // self.tab5TableView.reloadRowsAtIndexPaths(indexs, withRowAnimation: .None);
//                            
//                            Global.sharedInstance.isLogined = true;
//                            
//                            CommonTools.showMessage("登录成功!",  vc: self,  hr: {
//                                ()->Void in
//                                if(true == self.isNeedGoXieyi){
//                                    self.callBackGoXieyi!(beOK:true);
//                                    self.dismissViewControllerAnimated(false, completion: nil);
//                                }else if(true == self.isTempLogin){
//                                    self.callBackLoginedFun!(beOK:true);
//                                    self.dismissViewControllerAnimated(true, completion: nil);
//                                }else{
//                                    self.reloadLoginedHomePageDataCallBackFun!(beOK:true);
//                                    self.dismissViewControllerAnimated(true, completion: nil);
//                                }
//                            });
//                            
//                            self.setNotifyInfo(Global.sharedInstance.userInfo?.notice_count);
//                        }
//                    }
//                });

                self.view.makeToast("登录成功!", duration: 1, position: .bottom, title: nil, image: nil, style: nil, completion: {
                    
                    (didTap: Bool) -> Void in
                    
                    Global.sharedInstance.isLogined = true;
                    
                    //保存账号密码
                    UserDefaults.standard.set(self.tfUsername.text, forKey: LOGIN_USERNAME_KEY);
                    UserDefaults.standard.set(self.tfPassword.text, forKey: LOGIN_PASSWORD_KEY);
                    
                    
                    if(true == self.isNeedGoCategory){
                         //self.callBackGoCategories!(true);
                         self.dismiss(animated: false, completion: nil);
                    }else if(true == self.isNeedGoXieyi){
                       // self.callBackGoXieyi!(beOK:true);
                       self.dismiss(animated: false, completion: nil);
                    }else if(true == self.isTempLogin){
                        self.callBackLoginedFun!(true);
                        self.dismiss(animated: true, completion: nil);
                    }else{
                        self.reloadLoginedHomePageDataCallBackFun!(true);
                        self.dismiss(animated: true, completion: nil);
                    }
                });
                
                
                
            
                
//                CommonTools.showMessage("登录成功!", title: "提示", vc: self, hasCancelBtn: false, hr: {
//                    (beOkClicked:Bool)->Void in
//                    
//
//                });

                
                
            }
        });
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func isShowLoginBtn() -> Bool {
        
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
    
    deinit {
        NotificationCenter.default.removeObserver(self);
    }
}
