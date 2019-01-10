//
//  HYThirdAuthViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/31.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYThirdAuthViewController: HYBaseViewController {
    
    
    @IBOutlet weak var webView: UIWebView!
    

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let url:URL = URL(string: "http://192.168.1.122:8280/login/weibo?request=http://redirect_back_url")!;
        self.webView.loadRequest(URLRequest(url: url));
        
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
