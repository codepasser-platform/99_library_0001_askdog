//
//  HYResetPhonePwdViewController.swift
//  AskDog
//
//  Created by Symond on 16/9/8.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYResetPhonePwdViewController: HYBaseViewController {
    
    var strTel:String?
    var timer:Timer!
    var nCountDown:Int = 60;
    
    

    @IBOutlet weak var lblTel: UILabel!
    @IBOutlet weak var btnSendSms: UIButton!
    @IBOutlet weak var tfCheckCode: UITextField!
    @IBOutlet weak var btnNext: UIButton!
    var isFirst:Bool = true;
    
    override func btnReturnClicked(_ sender: UIButton) -> Void {
        //print("tm = \(self.timer)");
        if let tm:Timer = timer{
            tm.invalidate();
            self.timer = nil;
        }
        
        _ = self.navigationController?.popViewController(animated: true);
        
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated);
        
        if let tel:String = strTel{
            self.lblTel.text = tel;
            
            //验证如果是手机号  就发送短信
            if(CommonTools.PhoneNumberIsValidated(tel)){
                
                //检测是不是60秒内已经发送过短信
                //取一下倒计时状态
                let tm:Double = UserDefaults.standard.double(forKey: RESETPWD_SEND_SMS_COUNT_DOWN_TIME);//
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
                        print("HYResetPhonePwdViewController \(str)");
                        self.btnSendSms.setTitle(str, for: .disabled);
                        
                        self.timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.timerFiredMethod), userInfo: nil, repeats: true);
                        self.btnSendSms.isEnabled = false;
                    }else{
                        print("发送找回短信1 to \(tel)");
                        if(true == self.isFirst){
                            self.sendSms();
                            self.isFirst = false;
                        }else{
                            self.btnSendSms.setTitle("发送验证码", for: UIControlState());
                            self.btnSendSms.isEnabled = true;
                        }
                        
                    }
                }else{
                    print("发送找回短信 to \(tel)");
                    if(true == self.isFirst){
                        self.sendSms();
                        self.isFirst = false;
                    }else{
                        self.btnSendSms.setTitle("发送验证码", for: UIControlState());
                        self.btnSendSms.isEnabled = true;
                    }
                }
                
                
                
            }
            
        }else{
            self.lblTel.text = "";
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated);
        
        if let tm:Timer = timer{
            tm.invalidate();
            self.timer = nil;
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        stringNavBarTitle = "密码重置";
        btnNext.layer.cornerRadius = 5;
        //lblErrorMsg.hidden = true;
        
        //btnXieyi.setTitle("", forState: .Normal);
        
        // Do any additional setup after loading the view.
        self.btnSendSms.layer.borderColor = CommonTools.rgbColor(red: 178, green: 189, blue: 208).cgColor;
        self.btnSendSms.layer.borderWidth = 1;
        self.btnSendSms.layer.cornerRadius = 5;

        // Do any additional setup after loading the view.

    }
    
    func sendSms() -> Void {
        
        self.tfCheckCode.resignFirstResponder();
        
        if let tel:String = strTel{
            
            let str:String = "?phone=\(tel)";
            
            AskDogSendRestPhonePwdCheckCodeDataRequest().startRequest(exUrl:str,viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    print("AskDogSendRestPhonePwdCheckCodeDataRequest 发送验证码成功");
                    
                    CommonTools.showMessage("验证码发送成功,请注意查收验证码！", vc: self, hr: {
                        () -> Void in
                        
                    });
                    //先保存当前时间戳 防止用户返回页面 清空当前计时
                    let tm:TimeInterval = Date().timeIntervalSince1970 + 60;
                    //保存起来
                    UserDefaults.standard.set(tm, forKey: RESETPWD_SEND_SMS_COUNT_DOWN_TIME);
                    //60秒后按钮能可以继续点击
                    self.nCountDown = 60;
                    self.timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.timerFiredMethod), userInfo: nil, repeats: true);
                    self.btnSendSms.isEnabled = false;
                    
                }
            });
        }
    }
    
    func timerFiredMethod() -> Void {
        nCountDown -= 1;
        if(nCountDown < 0){
            self.btnSendSms.setTitle("发送验证码", for: UIControlState());
            self.btnSendSms.isEnabled = true;
            self.timer.invalidate();
            self.timer = nil;
        }else{
            let str:String = "\(nCountDown)秒后重新获取";
            print("HYResetPhonePwdViewController \(str)");
            self.btnSendSms.setTitle(str, for: .disabled);
        }
    }
    
    @IBAction func btnNextClicked(_ sender: UIButton) {
        
        guard self.tfCheckCode.text != "" else{
            
            CommonTools.showMessage("验证码不能为空!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
        
        var tel:String = "";
        if let tl:String = self.strTel{
            tel = tl;
        }
        
        if let code:String = self.tfCheckCode.text{
            
            let str:String = "?phone=\(tel)&code=\(code)";
            
            AskDogCheckResetPwdPhoneCheckCodeDataRequest().startRequest(exUrl:str,viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    print("AskDogCheckResetPwdPhoneCheckCodeDataRequest 校验成功");
                    
                    CommonTools.showMessage("验证成功！", vc: self, hr: {
                        () -> Void in
                        let vc:HYResetPasswordViewController = HYResetPasswordViewController(nibName: "HYResetPasswordViewController", bundle: Bundle.main);
                        vc.nType = 1;
                        vc.strMail = tel;
                        vc.strCode = code;
                        self.navigationController?.pushViewController(vc, animated: true);
                    });
                    
                    
                }
            });
        }
        
        
    }
    
    @IBAction func btnSendSmsClicked(_ sender: UIButton) {
        self.sendSms();
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func isShowLoginBtn() -> Bool {
        return false;
    }
    
    override func isShowNotifyBtn() -> Bool {
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

}
