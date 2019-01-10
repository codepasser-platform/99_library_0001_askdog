//
//  HYSafeCenterViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/11.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYSafeCenterViewController: HYBaseViewController {

    @IBOutlet weak var tableView: UITableView!
    override func viewDidLoad() {
        stringNavBarTitle = "安全中心";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let cellID1:String = "cell1";
        let nib1:UINib = UINib(nibName: "HYSafeCenterListTableViewCell", bundle: nil);
        tableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        if tableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tableView.separatorInset = UIEdgeInsets.zero;
        }
        if tableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tableView.layoutMargins = UIEdgeInsets.zero;
        }
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
    
    //MARK:UITableViewDelegate
    func tableView(_ tableView: UITableView, didSelectRowAtIndexPath indexPath: IndexPath) {
        
        if(0 == (indexPath as NSIndexPath).row){
            //修改密码
            let vc:HYModifyPasswordViewController = HYModifyPasswordViewController(nibName: "HYModifyPasswordViewController", bundle: Bundle.main);
            self.navigationController?.pushViewController(vc, animated: true);
        }else if(0 == (indexPath as NSIndexPath).row){
            HYWXApiManager.sharedInstance.openUrlInWeixinWebView(openUrl: "http://blog.csdn.net/woaifen3344/article/details/29368773", callBack: {
                (beOK:Bool?,respone:OpenWebviewResp?,needInstallWX:Bool?) -> Void in
                if(true == needInstallWX){
                    CommonTools.showModalMessage("未安装微信，或当前微信版本过低，请安装最新版的微信！",
                        title: "注意", vc: self, hasCancelBtn: true,OKBtnTitle:"去安装",CancelBtnTitle:"算了",hr: {
                            (beOK:Bool) -> Void in
                            if(beOK == true){
                                let url:URL = HYWXApiManager.sharedInstance.getWeixinAppstoreUrl();
                                UIApplication.shared.openURL(url);
                            }
                    });
                }else{
                    if(beOK == true){
                        print("open url in weixin's webview success");
                    }
                }
            });
        }
        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAtIndexPath indexPath: IndexPath) -> CGFloat {
        
        return 60;
        
    }
    
    //解决分割线不对齐问题
    func tableView(_ tableView: UITableView, willDisplayCell cell: UITableViewCell, forRowAtIndexPath indexPath: IndexPath) {
        if cell.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            cell.separatorInset = UIEdgeInsets.zero;
        }
        if cell.responds(to: #selector(setter: UIView.layoutMargins)){
            cell.layoutMargins = UIEdgeInsets.zero;
        }
    }
    
    func numberOfSectionsInTableView(_ tableView: UITableView) -> Int {
        return 1;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return 1;
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAtIndexPath indexPath: IndexPath) -> UITableViewCell {
        
        let cellID:String! = "cell1";
        
        let cell:HYSafeCenterListTableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID) as! HYSafeCenterListTableViewCell;
        
        if(1 == (indexPath as NSIndexPath).row){
            cell.lblTitle.text = "绑定微信";
            cell.imgViewArrow.isHidden = true;
            cell.lblContent.text = "未绑定";
            cell.lblLine.isHidden = false;
        }else{
            cell.lblTitle.text = "修改密码";
            cell.imgViewArrow.isHidden = false;
            cell.lblContent.isHidden = true;
            cell.lblLine.isHidden = true;
            
//            cell.layer.shadowColor = UIColor.black.cgColor;
//            cell.layer.shadowOffset = CGSize(width: 3, height: 3);
//            cell.layer.shadowOpacity = 0.4;
//            cell.layer.shadowRadius = 3;
            //cell.layer.cornerRadius = 3;
        }
        
        return cell;
    }

}
