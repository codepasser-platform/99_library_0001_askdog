//
//  HYForgetPasswordViewController.swift
//  AskDog
//
//  Created by Symond on 16/7/29.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYForgetPasswordViewController: HYBaseViewController {

    @IBOutlet weak var tfEmail: UITextField!
   // @IBOutlet weak var lblErrorMsg: UILabel!
    override func viewDidLoad() {
        stringNavBarTitle = "密码重置";
        super.viewDidLoad()
        
        //self.tfEmail.text = "wangzhch@hooying.com";
        
//        self.lblErrorMsg.text = "";
//        self.lblErrorMsg.hidden = true;

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func isShowLoginBtn() -> Bool {
        
        return false;
    }
    
    func doMail() -> Void {
        let dic:[String: AnyObject] = ["mail":self.tfEmail.text! as AnyObject,];
        
        AskDogSendFindPwdEmailDataRequest().startRequest(requestParmer: dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                print("AskDogSendFindPwdEmailDataRequest 发送邮件成功");
                
                //
                
                CommonTools.showMessage("重置密码邮件已发送到您的邮箱,请前往邮箱完成找回密码！", vc: self,showTime:5, hr: {
                    ()->Void in
                    Global.sharedInstance.beNeedDoLogin = true;
                    _ = self.navigationController?.popToRootViewController(animated: true);
                });
                
//                let vc:HYSendMailSuccessViewController = HYSendMailSuccessViewController(nibName: "HYSendMailSuccessViewController", bundle: NSBundle.mainBundle());
//                vc.strMail = self.tfEmail.text;
//                self.navigationController?.pushViewController(vc, animated: true);
                
                //                CommonTools.showMessage("注册成功!", title: "注意", vc: self, hasCancelBtn: false, hr: {
                //                    (beOkClicked:Bool)->Void in
                //
                //                });
            }
        });
    }
    
    func doPhone() -> Void {
        //先检测手机号合法性
        
       // let dic:[String: AnyObject] = ["phone":self.tfEmail.text!];
        if let tel:String = self.tfEmail.text{
            
            let str:String = "?phone=\(tel)";
            
            AskDogCheckPhoneNumberDataRequest().startRequest(exUrl:str,viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    print("AskDogCheckPhoneNumberDataRequest 检测手机号合法性成功");
                    //进入下一个画面
                    let vc:HYResetPhonePwdViewController = HYResetPhonePwdViewController(nibName: "HYResetPhonePwdViewController", bundle: Bundle.main);
                    vc.strTel = tel;
                    self.navigationController?.pushViewController(vc, animated: true);
                    
                }
            });
        }
        

    }
    

    @IBAction func btnSendMailClicked(_ sender: UIButton) {
        
        self.tfEmail.resignFirstResponder();
        
        
        guard self.tfEmail.text != "" else{
//            self.lblErrorMsg.text = "邮箱不能为空!";
//            self.lblErrorMsg.hidden = false;
//            //self.tfEmail.becomeFirstResponder();
//            self.hideErrorMsg();
            
            CommonTools.showMessage("手机号/邮箱地址不能为空!", vc: self, hr: {
                () -> Void in
            });
            
            
            return;
        }
        
        var beMail:Bool = false;
        var bePhone:Bool = false;
        
        if(CommonTools.EmailIsValidated(self.tfEmail.text!)){
            beMail = true;
        }
        
        if(CommonTools.PhoneNumberIsValidated(self.tfEmail.text!)){
            bePhone = true;
        }
        
        if(false == beMail && false == bePhone){
            CommonTools.showMessage("手机/邮箱地址格式错误!",  vc: self,  hr: {
                ()->Void in
                
            });
            return;
        }
        
        if(true == beMail){
            self.doMail();
        }else if(true == bePhone){
            self.doPhone();
        }
        
    }
    
//    func hideErrorMsg() -> Void {
//        NSTimer.scheduledTimerWithTimeInterval(2, target: self, selector: #selector(timerFired), userInfo: nil, repeats: false);
//    }
//    
//    func timerFired() -> Void {
//        self.lblErrorMsg.text = "";
//        self.lblErrorMsg.hidden = true;
//    }
    
    override func btnReturnClicked(_ sender: UIButton) {
        _ = self.navigationController?.popViewController(animated: true);
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
