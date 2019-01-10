//
//  HYCashViewController.swift
//  AskDog
//
//  Created by Symond on 16/9/2.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
fileprivate func < <T : Comparable>(lhs: T?, rhs: T?) -> Bool {
  switch (lhs, rhs) {
  case let (l?, r?):
    return l < r
  case (nil, _?):
    return true
  default:
    return false
  }
}

fileprivate func > <T : Comparable>(lhs: T?, rhs: T?) -> Bool {
  switch (lhs, rhs) {
  case let (l?, r?):
    return l > r
  default:
    return rhs < lhs
  }
}


class HYCashViewController: HYBaseViewController {
    
    @IBOutlet weak var btnCash: UIButton!
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var lblCashMoneyNumber: UILabel!
    var cashMoneyNumber:Float = 0.0;
    
    @IBOutlet weak var textFiledCashMoneyNumber: UITextField!
    var dataModel:HYIncomeDataModel!

    @IBAction func btnCashClicked(_ sender: UIButton) {
        
        self.textFiledCashMoneyNumber.resignFirstResponder();
        
        if 0 == self.textFiledCashMoneyNumber.text!.characters.count  {
            CommonTools.showMessage("请输入提现金额！", vc: self, hr: { () in
            })
            return
        }
        
        let nMoney:Float = Float(self.textFiledCashMoneyNumber.text!)!;
        
        if (nMoney > dataModel.balance){
            CommonTools.showMessage("超出可提现金额!", vc: self, hr: {
                ()-> Void in
                
            });
            return;
        }
        
        
        
        
        let dic:[String: AnyObject]? = ["withdrawal_way":PAY_WAY_WEIXIN as AnyObject,
                                        "withdrawal_amount":nMoney * 100 as AnyObject];
        
        AskDogCashDataRequest().startRequest(requestParmer:dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    
                    CommonTools.showMessage("提现成功！", vc: self, hr: {
                        ()-> Void in
                        
                        let f:Float = self.dataModel.balance! - nMoney;
                        self.dataModel.balance = f;
                        let s:String = String(format: "%.2f",self.dataModel.balance!);
                        self.lblCashMoneyNumber.text = "\(s)元";
                        self.textFiledCashMoneyNumber.text = "";
                        
                    });
                    
                    
                }
            }
        });
        
        
    }
    
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?,data da:AnyObject?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil);
        self.dataModel = da as! HYIncomeDataModel;
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    
    override func viewDidLoad() {

        stringNavBarTitle = "提现";
        super.viewDidLoad()
        
        bkView.layer.shadowColor = UIColor.black.cgColor;
        bkView.layer.shadowOffset = CGSize(width: 2, height: 2);
        bkView.layer.shadowOpacity = 0.3;
        bkView.layer.shadowRadius = 3;
        bkView.layer.cornerRadius = 3;
        
        btnCash.layer.cornerRadius = 3;
        
        self.textFiledCashMoneyNumber.layer.borderColor = UIColor.lightGray.cgColor;
        self.textFiledCashMoneyNumber.layer.borderWidth = 1;
                
        if let cashMoneyNumber = self.dataModel.balance{
            let s:String = String(format: "%.2f",cashMoneyNumber);
            self.lblCashMoneyNumber.text = "\(s)元";
        }

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
