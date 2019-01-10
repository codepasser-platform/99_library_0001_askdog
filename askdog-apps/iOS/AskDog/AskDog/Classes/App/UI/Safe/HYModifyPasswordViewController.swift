//
//  HYModifyPasswordViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/11.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYModifyPasswordViewController: HYBaseViewController {
    
    @IBOutlet weak var bkView: UIView!
    
    @IBOutlet weak var tfOldPwd: UITextField!
    
    @IBOutlet weak var tfCheckPwd: UITextField!
    @IBOutlet weak var tfNewPwd: UITextField!

    @IBOutlet weak var btnModifyPwd: UIButton!
    @IBOutlet weak var lblErrorMsg: UILabel!
    
    
    @IBAction func btnModifyClicked(_ sender: UIButton) {
        
        self.tfOldPwd.resignFirstResponder();
        self.tfNewPwd.resignFirstResponder();
        self.tfCheckPwd.resignFirstResponder();
        
        guard self.tfOldPwd.text != "" else{
            self.lblErrorMsg.text = "原密码不能为空!";
            self.lblErrorMsg.isHidden = false;
            self.tfOldPwd.becomeFirstResponder();
            self.hideErrorMsg();
            return;
        }
        
        guard self.tfNewPwd.text != "" else{
            self.lblErrorMsg.text = "新密码不能为空!";
            self.lblErrorMsg.isHidden = false;
            self.tfNewPwd.becomeFirstResponder();
            self.hideErrorMsg();
            return;
        }
        
        guard self.tfCheckPwd.text != "" else{
            self.lblErrorMsg.text = "确认新密码不能为空!";
            self.lblErrorMsg.isHidden = false;
            self.tfCheckPwd.becomeFirstResponder();
            self.hideErrorMsg();
            return;
        }
        
        guard self.tfNewPwd.text == self.tfCheckPwd.text else{
            self.lblErrorMsg.text = "两次密码输入的不匹配!";
            self.lblErrorMsg.isHidden = false;
            self.hideErrorMsg();
            return;
        }
        
        guard self.tfNewPwd.text != self.tfOldPwd.text else{
            self.lblErrorMsg.text = "原密码与新密码不能相同!";
            self.lblErrorMsg.isHidden = false;
            self.hideErrorMsg();
            return;
        }
        
        //修改密码
        
        let dic:[String: AnyObject]? = ["origin_password":self.tfOldPwd.text! as AnyObject,"new_password":self.tfNewPwd.text! as AnyObject];
        
        AskDogModifyPasswordDataRequest().startRequest(requestParmer: dic, viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                CommonTools.showMessage("密码修改成功!", vc: self, hr: {
                    ()->Void in
                    
                    //保存密码
                    UserDefaults.standard.set(self.tfNewPwd.text, forKey: LOGIN_PASSWORD_KEY);
                    
                    _ = self.navigationController?.popViewController(animated: true);
                });
            }
        });
        
    }
    
    func hideErrorMsg() -> Void {
        Timer.scheduledTimer(timeInterval: 2, target: self, selector: #selector(timerFired), userInfo: nil, repeats: false);
    }
    
    func timerFired() -> Void {
        self.lblErrorMsg.text = "";
        self.lblErrorMsg.isHidden = true;
    }
    
    override func viewDidLoad() {
        stringNavBarTitle = "修改密码";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
//        bkView.layer.shadowColor = UIColor.black.cgColor;
//        bkView.layer.shadowOffset = CGSize(width: 2, height: 2);
//        bkView.layer.shadowOpacity = 0.3;
//        bkView.layer.shadowRadius = 3;
//        bkView.layer.cornerRadius = 3;
        
        btnModifyPwd.layer.cornerRadius = 3;
        
        lblErrorMsg.isHidden = true;
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

}
