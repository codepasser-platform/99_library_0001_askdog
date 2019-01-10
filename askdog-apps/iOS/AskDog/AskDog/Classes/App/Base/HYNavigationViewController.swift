//
//  HYNavigationViewController.swift
//  AskDog
//
//  Created by Symond on 16/7/25.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import SVProgressHUD

class HYNavigationViewController: UINavigationController,UIGestureRecognizerDelegate,UINavigationControllerDelegate {

    override func viewDidLoad() {
        super.viewDidLoad()
        self.interactivePopGestureRecognizer?.delegate = self;
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override  func pushViewController(_ viewController: UIViewController, animated: Bool){
        
        self.interactivePopGestureRecognizer?.isEnabled = true;
        
        if(self.viewControllers.count > 0){
            let curTopVC:HYBaseViewController = self.topViewController as! HYBaseViewController;
            let title:String = curTopVC.stringNavBarTitle;
            curTopVC.navigationStyle = UINavigationControllerOperation.push;
            let newVC:HYBaseViewController = viewController as! HYBaseViewController;
            newVC.stringNavBarBackBtnTitle = title;
        }
        
        super.pushViewController(viewController, animated: animated);
    }
    
    override func popViewController(animated: Bool) -> UIViewController? {
        SVProgressHUD.dismiss();
        return super.popViewController(animated: animated);
    }
    
    override func popToRootViewController(animated: Bool) -> [UIViewController]? {
        SVProgressHUD.dismiss();
        return super.popToRootViewController(animated: animated);
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
