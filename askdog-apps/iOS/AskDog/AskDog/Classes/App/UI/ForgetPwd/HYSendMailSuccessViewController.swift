//
//  HYSendMailSuccessViewController.swift
//  AskDog
//
//  Created by Symond on 16/7/29.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYSendMailSuccessViewController: HYBaseViewController {
    
    var strMail:String!

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(timerFired), userInfo: nil, repeats: false);
    }
    
    func timerFired() -> Void {
//        let vc:HYResetPasswordViewController = HYResetPasswordViewController(nibName: "HYResetPasswordViewController", bundle: NSBundle.mainBundle());
//        vc.strMail = strMail;
//        vc.nType = 0;
//        self.navigationController?.pushViewController(vc, animated: true);
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func isShowLoginBtn() -> Bool {
        
        return false;
    }
    
    override func btnReturnClicked(_ sender: UIButton) {
        _ = self.navigationController?.popToRootViewController(animated: true);
    }
    

    @IBAction func btnLoginRightNowClicked(_ sender: UIButton) {
        
        Global.sharedInstance.beNeedDoLogin = true;
        _ = self.navigationController?.popToRootViewController(animated: false);
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
