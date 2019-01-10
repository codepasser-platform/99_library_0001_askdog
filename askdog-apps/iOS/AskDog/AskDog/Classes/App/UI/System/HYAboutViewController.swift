//
//  HYAboutViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/11.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYAboutViewController: HYBaseViewController {

    @IBOutlet weak var lblVersion: UILabel!
    
    
    @IBAction func btnGotoWebPageClicked(_ sender: UIButton) {
        UIApplication.shared.openURL(URL(string: "http://askdog.com")!);
    }
    override func viewDidLoad() {
        stringNavBarTitle = "关于";
        super.viewDidLoad()
        
        let appInfoDic:[String:AnyObject] = Bundle.main.infoDictionary! as [String : AnyObject];
        let app_version:String = appInfoDic["CFBundleShortVersionString"] as! String;
        let app_build:String = appInfoDic["CFBundleVersion"] as! String;
        self.lblVersion.text = "版本 \(app_version) Build \(app_build)";

        // Do any additional setup after loading the view.
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
