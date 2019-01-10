//
//  HYSystemSetupViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/11.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import SDWebImage

typealias systemCallBack = (_ beOK:Bool)->Void;

class HYSystemSetupViewController: HYBaseViewController {
    
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var lblCancheNumber: UILabel!
    @IBOutlet weak var switchWifiPlay: UISwitch!
    @IBOutlet weak var btnLogout: UIButton!
    
    var callBackFun:systemCallBack?
    

    @IBAction func wifiPlayDidChange(_ sender: UISwitch) {
        print("wifi play = \(switchWifiPlay.isOn)");
        
        if(true == switchWifiPlay.isOn){
            UserDefaults.standard.set(false, forKey: WIFI_PLAY_KEY);
        }else{
            UserDefaults.standard.set(true, forKey: WIFI_PLAY_KEY);
        }
        
    }
    @IBAction func btnAboutClicked(_ sender: UIButton) {
        let vc:HYAboutViewController = HYAboutViewController(nibName: "HYAboutViewController", bundle: Bundle.main);
        self.navigationController?.pushViewController(vc, animated: true);
    }
    @IBAction func btnClearCacheClicked(_ sender: UIButton) {
        print("btnClearCacheClicked");
        
        SDImageCache.shared().clearDisk();
        
        CommonTools.showMessage("缓存清理完毕！", vc: self, hr: {
            ()-> Void in
            
        });
        
        self.getCacheSize();
    }
    @IBAction func btnLogoutClicked(_ sender: UIButton) {
        print("btnLogoutClicked");
        CommonTools.showModalMessage("确定退出当前账号吗?", title: "提示", vc: self, hasCancelBtn: true, hr: {
            (beOK:Bool) -> Void in
            
            if(true == beOK){
                
                AskDogLogoutDataRequest().startRequest(viewController:self,completionHandler: {
                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                    if(true == beOk){
                        Global.sharedInstance.isLogined = false;
                        _ = self.navigationController?.popToRootViewController(animated: true);
                        self.callBackFun!(true);
                    }
                        
                });
            }
            
        });
    }
    
    func getCacheSize() -> Void {
        //计算缓存大小
        let nSize:UInt = SDImageCache.shared().getSize();
        
        let total:Float = Float(nSize);
        
        if(total < 1024){
            lblCancheNumber.text = "\(total)B";
        }else if(total < 1024 * 1024){
            let f:Float = total/1024;
            let s:String = String(format: "%.0f",f);
            lblCancheNumber.text = "\(s)K";
        }else if(total < 1024 * 1024 * 1024){
            let f:Float = total/(1024 * 1024);
            let s:String = String(format: "%.2f",f);
            lblCancheNumber.text = "\(s)M";
        }else{
            let f:Float = total/(1024 * 1024 * 1024);
            let s:String = String(format: "%.2f",f);
            lblCancheNumber.text = "\(s)G";
        }
    }
    
    override func viewDidLoad() {
        stringNavBarTitle = "系统设置";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
//        bkView.layer.shadowColor = UIColor.black.cgColor;
//        bkView.layer.shadowOffset = CGSize(width: 2, height: 2);
//        bkView.layer.shadowOpacity = 0.3;
//        bkView.layer.shadowRadius = 3;
//        bkView.layer.cornerRadius = 3;
//        
//        btnLogout.layer.cornerRadius = 3;
//        btnLogout.layer.shadowColor = UIColor.black.cgColor;
//        btnLogout.layer.shadowOffset = CGSize(width: 2, height: 2);
//        btnLogout.layer.shadowOpacity = 0.3;
//        btnLogout.layer.shadowRadius = 3;
        
        let beWifiPlay:Bool = UserDefaults.standard.bool(forKey: WIFI_PLAY_KEY);
        if(true == beWifiPlay){
            //只允许 WIFI 播放
            switchWifiPlay.isOn = false;
        }else{
            switchWifiPlay.isOn = true;
        }
        
        lblCancheNumber.text = "0B";
        self.getCacheSize();

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
