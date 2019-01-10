//
//  HYRegisterViewController.swift
//  AskDog
//
//  Created by Symond on 16/7/27.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit


typealias registerCallBack = (_ beOK:Bool)->Void;



class HYRegisterViewController: HYBaseViewController {

    @IBOutlet weak var mailRegisterV: UIView!
    @IBOutlet weak var phoneRegisterV: UIView!
    
    @IBOutlet weak var textViewInPhone: UITextView!
    @IBOutlet weak var textViewInMail: UITextView!
    //@IBOutlet weak var btnXieyi: UIButton!
    @IBOutlet weak var tfUserName: UITextField!
    @IBOutlet weak var tfEmail: UITextField!
    @IBOutlet weak var tfPassword: UITextField!
    @IBOutlet weak var btnRegister: UIButton!
    //@IBOutlet weak var lblErrorMsg: UILabel!
    
    
    @IBOutlet weak var tfPhoneUsername: UITextField!
    @IBOutlet weak var tfPhoneNumber: UITextField!
    @IBOutlet weak var tfPhoneCheckCode: UITextField!
    @IBOutlet weak var tfPhonePwd: UITextField!
    
    @IBOutlet weak var btnSendSmsCode: UIButton!
    var callBackFun:registerCallBack?
    var timer:Timer!
    var nCountDown:Int = 60;
    
    @IBAction func btnXieyiClicked(_ sender: UIButton) {
        
        let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: Bundle.main);
        self.present(vc, animated: true, completion: {
            ()->Void in
            
        })
    }
    override func btnReturnClicked(_ sender: UIButton) -> Void {
        //print("tm = \(self.timer)");
        if let tm:Timer = timer{
            tm.invalidate();
            self.timer = nil;
        }
        self.dismiss(animated: true, completion: nil);
        
    }
    
    @IBAction func btnShowPhonePwdClicked(_ sender: UIButton) {
        let be:Bool = self.tfPhonePwd.isSecureTextEntry;
        self.tfPhonePwd.isSecureTextEntry = !be;
    }
    @IBAction func btnShowPasswordClicked(_ sender: UIButton) {
        let be:Bool = self.tfPassword.isSecureTextEntry;
        self.tfPassword.isSecureTextEntry = !be;
    }
    
    @IBAction func btnPhoneRegisterClicked(_ sender: UIButton) {
        
//        self.callBackFun!(true);
//        //跳到登陆
//        self.dismiss(animated: true, completion: {
//            ()->Void in
//            print("dismissViewControllerAnimated ok");
//            
//        })
        
        self.tfPhoneUsername.resignFirstResponder();
        self.tfPhoneNumber.resignFirstResponder();
        self.tfPhonePwd.resignFirstResponder();
        self.tfPhoneCheckCode.resignFirstResponder();
        
        guard self.tfPhoneUsername.text != "" else{

            CommonTools.showMessage("姓名/英文昵称不能为空!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
        
        guard self.tfPhoneNumber.text != "" else{

            CommonTools.showMessage("手机号不能为空!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
        
        if(!CommonTools.PhoneNumberIsValidated(self.tfPhoneNumber.text!)){

            CommonTools.showMessage("手机号格式错误!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
        
        guard self.tfPhonePwd.text != "" else{

            CommonTools.showMessage("密码不能为空!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
        
        guard self.tfPhoneCheckCode.text != "" else{
            
            CommonTools.showMessage("验证码不能为空!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
        
        //还要校验验证码 
        
        let dic:[String: AnyObject] = ["name":self.tfPhoneUsername.text! as AnyObject,
                                       "password":self.tfPhonePwd.text! as AnyObject,
                                       "phone":self.tfPhoneNumber.text! as AnyObject,
                                        "code":self.tfPhoneCheckCode.text! as AnyObject];
        
        AskDogRegisterWithPhoneNumberDataRequest().startRequest(requestParmer: dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                print("AskDogRegisterWithPhoneNumberDataRequest 注册成功");
                CommonTools.showMessage("注册成功,请重新登录!", vc: self, hr: {
                    ()->Void in
                    
                    self.callBackFun!(true);
                    //跳到登陆
                    self.dismiss(animated: true, completion: {
                        ()->Void in
                        print("dismissViewControllerAnimated ok");
                        
                    })
                });
            }
        });
        
    }
    
    @IBAction func btnRegisterClicked(_ sender: UIButton) {
        
//        self.callBackFun!(true);
//        //跳到登陆
//        self.dismiss(animated: true, completion: {
//            ()->Void in
//            print("dismissViewControllerAnimated ok");
//            
//        })
        
        self.tfUserName.resignFirstResponder();
        self.tfEmail.resignFirstResponder();
        self.tfPassword.resignFirstResponder();
        
        guard self.tfUserName.text != "" else{
//            self.lblErrorMsg.text = "姓名/英文昵称不能为空!";
//            self.lblErrorMsg.hidden = false;
//            //self.tfUserName.becomeFirstResponder();
//            self.hideErrorMsg();
            CommonTools.showMessage("姓名/英文昵称不能为空!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
        
        guard self.tfEmail.text != "" else{
//            self.lblErrorMsg.text = "邮箱不能为空!";
//            self.lblErrorMsg.hidden = false;
//            //self.tfEmail.becomeFirstResponder();
//            self.hideErrorMsg();
            CommonTools.showMessage("邮箱不能为空!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
        
        if(!CommonTools.EmailIsValidated(self.tfEmail.text!)){
//            self.lblErrorMsg.text = "邮箱格式错误!";
//            self.lblErrorMsg.hidden = false;
//            //self.tfEmail.becomeFirstResponder();
//            self.hideErrorMsg();
            CommonTools.showMessage("邮箱格式错误!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
        
        guard self.tfPassword.text != "" else{
//            self.lblErrorMsg.text = "密码不能为空!";
//            self.lblErrorMsg.hidden = false;
//            //self.tfPassword.becomeFirstResponder();
//            self.hideErrorMsg();
            CommonTools.showMessage("密码不能为空!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
    
        
        let dic:[String: AnyObject] = ["name":self.tfUserName.text! as AnyObject,"mail":self.tfEmail.text! as AnyObject,"password":self.tfPassword.text! as AnyObject];
        
        AskDogRegisterDataRequest().startRequest(requestParmer: dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                print("AskDogRegisterDataRequest 注册成功");
                CommonTools.showMessage("注册成功,请重新登录!", vc: self, hr: {
                    ()->Void in
                    
                    self.callBackFun!(true);
                        //跳到登陆
                    self.dismiss(animated: true, completion: {
                        ()->Void in
                        print("dismissViewControllerAnimated ok");
                        
                    })
                });
            }
        });

        
    }
    
    
//    func hideErrorMsg() -> Void {
//        NSTimer.scheduledTimerWithTimeInterval(2, target: self, selector: #selector(timerFired), userInfo: nil, repeats: false);
//    }
    
//    func timerFired() -> Void {
//        self.lblErrorMsg.text = "";
//        self.lblErrorMsg.hidden = true;
//    }
    
    override func viewDidLoad() {
        stringNavBarTitle = "用户注册";
        super.viewDidLoad()
        
//        self.lblErrorMsg.text = "";
//        self.lblErrorMsg.hidden = true;
        
        //self.tfUserName.text = "symond";
        //self.tfEmail.text = "wangzhch@hooying.com";
        //self.tfPassword.text = "123456";
        
        btnRegister.layer.cornerRadius = 5;
        //lblErrorMsg.hidden = true;
        
        //btnXieyi.setTitle("", forState: .Normal);

        // Do any additional setup after loading the view.
        self.btnSendSmsCode.layer.borderColor = CommonTools.rgbColor(red: 178, green: 189, blue: 208).cgColor;
        self.btnSendSmsCode.layer.borderWidth = 1;
        self.btnSendSmsCode.layer.cornerRadius = 5;
        
        
        //用富文本的方式
        let str:String = "同意并接受《Askdog用户协议》";
        let attrString:NSMutableAttributedString = NSMutableAttributedString(string: str);
        
        let rangeName = str.range(of: "《Askdog用户协议》");
        let locName:Int = str.characters.distance(from: str.startIndex, to: (rangeName?.lowerBound)!);
        let distanceName:Int = str.characters.distance(from: (rangeName?.lowerBound)!, to: (rangeName?.upperBound)!);
        //let distanceName:Int = (<#T##String.CharacterView corresponding to your index##String.CharacterView#>.distance(from: (rangeName?.lowerBound)!, to: (rangeName?.upperBound)!));
        let rName:NSRange = NSMakeRange(locName, distanceName);
        attrString.addAttributes([NSLinkAttributeName:URL(string: "go:userdelegate")!], range: rName);
        
        self.textViewInMail.attributedText = attrString;
        self.textViewInPhone.attributedText = attrString;
        self.textViewInMail.linkTextAttributes = [NSForegroundColorAttributeName:CommonTools.rgbColor(red: 0, green: 170, blue: 239)];
        self.textViewInPhone.linkTextAttributes = [NSForegroundColorAttributeName:CommonTools.rgbColor(red:0, green: 170, blue: 239)];
        
        //默认显示手机注册画面
        self.mailRegisterV.isHidden = true;
        self.phoneRegisterV.isHidden = false;
        self.mailRegisterV.alpha = 0;
        self.phoneRegisterV.alpha = 1;
        
        //取一下倒计时状态
        let tm:Double = UserDefaults.standard.double(forKey: REGISTER_SEND_SMS_COUNT_DOWN_TIME);//
        print("get tm = \(tm)");
        if(0 != tm){
            //说明记录过时间 用当前时间减去这个时间 看看超过 60 秒 按钮就可用，如果没超过  继续计时
            let tmNow:TimeInterval = Date().timeIntervalSince1970;
            let off:TimeInterval = tm - tmNow;
            if(off > 0 ){
                //开始计时
                nCountDown = Int(off);
                //60秒后按钮能可以继续点击
                let str:String = "\(nCountDown)秒后重新获取";
                print("HYRegisterViewController \(str)");
                self.btnSendSmsCode.setTitle(str, for: .disabled);
                
                self.timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.timerFiredMethod), userInfo: nil, repeats: true);
                self.btnSendSmsCode.isEnabled = false;
            }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func isShowLoginBtn() -> Bool {
        
        return false;
    }
    @IBAction func btnSendSmsCodeClicked(_ sender: UIButton) {
        
        self.tfPhoneUsername.resignFirstResponder();
        self.tfPhoneNumber.resignFirstResponder();
        self.tfPhonePwd.resignFirstResponder();
        self.tfPhoneCheckCode.resignFirstResponder();
        
        guard self.tfPhoneNumber.text != "" else{
            
            CommonTools.showMessage("手机号不能为空!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
        
        if(!CommonTools.PhoneNumberIsValidated(self.tfPhoneNumber.text!)){
            
            CommonTools.showMessage("手机号格式错误!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
        
        let dic:[String: AnyObject] = ["phone":self.tfPhoneNumber.text! as AnyObject];
        
        AskDogSendSMSDataRequest().startRequest(requestParmer: dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                print("AskDogSendSMSDataRequest 成功");
                CommonTools.showMessage("验证码发送成功,请注意查收验证码！", vc: self, hr: {
                    () -> Void in
                    
                });
                //先保存当前时间戳 防止用户返回页面 清空当前计时
                let tm:TimeInterval = Date().timeIntervalSince1970 + 60;
                //保存起来
                UserDefaults.standard.set(tm, forKey: REGISTER_SEND_SMS_COUNT_DOWN_TIME);
                //60秒后按钮能可以继续点击
                self.nCountDown = 60;
                self.timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.timerFiredMethod), userInfo: nil, repeats: true);
                self.btnSendSmsCode.isEnabled = false;
            }
        });
        
    }
    
    func timerFiredMethod() -> Void {
        nCountDown -= 1;
        if(nCountDown < 0){
            self.btnSendSmsCode.setTitle("发送验证码", for: UIControlState());
            self.btnSendSmsCode.isEnabled = true;
            self.timer.invalidate();
            self.timer = nil;
        }else{
            let str:String = "\(nCountDown)秒后重新获取";
            print("HYRegisterViewController \(str)");
            self.btnSendSmsCode.setTitle(str, for: .disabled);
        }
    }
    
    
    @IBAction func goPhoneClicked(_ sender: UIButton) {
        

        
        
//        UIView.beginAnimations(nil, context: nil);
//        UIView.setAnimationDuration(1.0);
//        //UIView.setAnimationTransition(.FlipFromRight, forView: self.mailRegisterV, cache: true);
//        //self.view.exchangeSubviewAtIndex(0, withSubviewAtIndex: 1);
//        UIView.setAnimationCurve(.EaseInOut);

        //UIView.commitAnimations();
        
        UIView.animate(withDuration: 0.5, animations: { () -> Void in
            
            
            self.phoneRegisterV.isHidden = false;
            
            self.mailRegisterV.alpha = 0;
            self.phoneRegisterV.alpha = 1;
            
        }, completion: { (finish) -> Void in
            self.mailRegisterV.isHidden = true;
        }) 
    }

    @IBAction func goMailClicked(_ sender: UIButton) {
        
        UIView.animate(withDuration: 0.5, animations: { () -> Void in
            
            self.mailRegisterV.isHidden = false;
            
            
            self.mailRegisterV.alpha = 1;
            self.phoneRegisterV.alpha = 0;
            
        }, completion: { (finish) -> Void in
            self.phoneRegisterV.isHidden = true;
        }) 
//        
//        UIView.beginAnimations(nil, context: nil);
//        UIView.setAnimationDuration(1.0);
//        UIView.setAnimationTransition(.FlipFromLeft, forView: self.phoneRegisterV, cache: true);
//        //self.view.exchangeSubviewAtIndex(0, withSubviewAtIndex: 1);
//        UIView.setAnimationCurve(.EaseInOut);
//
//        UIView.commitAnimations();
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    //MARK:UITextViewDelegate
    func textView(_ textView: UITextView, shouldInteractWithURL URL: Foundation.URL, inRange characterRange: NSRange) -> Bool{
     //   print("shouldInteractWithURL \(URL.resourceSpecifier) \(URL.scheme) \(URL.absoluteString)");
        
        if(URL.absoluteString == "go:userdelegate"){
            let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: Bundle.main);
            self.present(vc, animated: true, completion: {
                ()->Void in
                
            })
            
        }
        
        return false;
    }
    
    deinit{
        
        print("HYRegisterViewController deinit");
        
        if let tm:Timer = timer{
            tm.invalidate();
            self.timer = nil;
        }
    }

}
