//
//  HYResetPasswordViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/6.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYResetPasswordViewController:HYBaseViewController {
    @IBOutlet weak var tfNewPwd: UITextField!
    
    //@IBOutlet weak var lblErrorMsg: UILabel!
    @IBOutlet weak var tfReCheckPwd: UITextField!
    @IBOutlet weak var btnReset: UIButton!
    var strMail:String!
    var strCode:String!
    
    /// 0  邮箱找回  1 手机密码找回
    var nType:Int = 0;

    @IBAction func btnResetClicked(_ sender: UIButton) {
        
        self.tfNewPwd.resignFirstResponder();
        self.tfReCheckPwd.resignFirstResponder();
        
        guard self.tfNewPwd.text != "" else{
//            self.lblErrorMsg.text = "新密码不能为空!";
//            self.lblErrorMsg.hidden = false;
//            self.tfNewPwd.becomeFirstResponder();
//            self.hideErrorMsg();
            
            CommonTools.showMessage("新密码不能为空!",  vc: self,  hr: {
                ()->Void in
                
                
            });
            return;
        }
        
        guard self.tfReCheckPwd.text != "" else{
//            self.lblErrorMsg.text = "确认密码不能为空!";
//            self.lblErrorMsg.hidden = false;
//            self.tfReCheckPwd.becomeFirstResponder();
//            self.hideErrorMsg();
            
            CommonTools.showMessage("确认密码不能为空!",  vc: self,  hr: {
                ()->Void in
                
                
            });
            return;
        }
        
        guard self.tfNewPwd.text == self.tfReCheckPwd.text else{
//            self.lblErrorMsg.text = "两次输入密码不一致!";
//            self.lblErrorMsg.hidden = false;
//            self.tfNewPwd.resignFirstResponder();
//            self.tfReCheckPwd.resignFirstResponder();
//            self.hideErrorMsg();
            
            CommonTools.showMessage("两次输入密码不一致!",  vc: self,  hr: {
                ()->Void in
                
                
            });
            return;
        }
        

        if(0 == self.nType){
            self.doMail();
        }else if(1 == self.nType){
            self.doPhone();
        }

        
    }
    
    func doMail() -> Void {
        print("reset pwd mail = \(strMail)");
        let dic:[String: AnyObject] = ["mail":strMail as AnyObject,"token":"" as AnyObject,"password":self.tfNewPwd.text! as AnyObject];
        
        AskDogResetPasswordDataRequest().startRequest(requestParmer: dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                print("AskDogRegisterDataRequest 密码重置成功");
                CommonTools.showMessage("密码重置成功!",  vc: self,  hr: {
                    ()->Void in
                    
                    
                });
            }
        });
    }
    
    func doPhone() -> Void {
        let dic:[String: AnyObject] = ["phone":strMail as AnyObject,"code":strCode as AnyObject,"password":self.tfNewPwd.text! as AnyObject];
        
        AskDogChangePhonePwdDataRequest().startRequest(requestParmer: dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                print("AskDogChangePhonePwdDataRequest 密码重置成功");
                CommonTools.showMessage("密码重置成功!",  vc: self,  hr: {
                    ()->Void in
                    Global.sharedInstance.beNeedDoLogin = true;
                    _ = self.navigationController?.popToRootViewController(animated: true);
                    
                });
            }
        });
    }
    
//    func hideErrorMsg() -> Void {
//        NSTimer.scheduledTimerWithTimeInterval(2, target: self, selector: #selector(timerFired), userInfo: nil, repeats: false);
//    }
//    
//    func timerFired() -> Void {
//        self.lblErrorMsg.text = "";
//        self.lblErrorMsg.hidden = true;
//    }
    
    @IBAction func btnShowNewPwd(_ sender: UIButton) {
        let be:Bool = self.tfNewPwd.isSecureTextEntry;
        self.tfNewPwd.isSecureTextEntry = !be;
    }
    
    @IBAction func btnShowCheckPwd(_ sender: UIButton) {
        let be:Bool = self.tfReCheckPwd.isSecureTextEntry;
        self.tfReCheckPwd.isSecureTextEntry = !be;
    }
    override func viewDidLoad() {
        stringNavBarTitle = "重置密码";
//        lblErrorMsg.text = "";
//        lblErrorMsg.hidden = true;
        super.viewDidLoad()

        // Do any additional setup after loading the view.
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

}
