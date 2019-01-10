//
//  HYMyIncomeHomeViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/15.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYMyIncomeHomeViewController: HYBaseViewController {

    @IBOutlet weak var topBkView: UIView!
    @IBOutlet weak var lblCashNumber: UILabel!
    @IBOutlet weak var btnCash: UIButton!
    @IBOutlet weak var secondBkView: UIView!
    var dataModel:HYIncomeDataModel!
    
    
    
    @IBAction func btnCashDetailClicked(_ sender: UIButton) {
        let vc:HYCashDetailListViewController = HYCashDetailListViewController(nibName: "HYCashDetailListViewController", bundle: Bundle.main);
        vc.nTotalIncome = self.dataModel.withdrawal_total!;
        self.navigationController?.pushViewController(vc, animated: true);
    }
    
    @IBAction func btnCashClicked(_ sender: UIButton) {
        let vc:HYCashViewController = HYCashViewController(nibName: "HYCashViewController", bundle: Bundle.main,data:dataModel);
        self.navigationController?.pushViewController(vc, animated: true);
    }
    
    @IBAction func btnIncomeDetailClicked(_ sender: UIButton) {
        let vc:HYIncomeDetailListViewController = HYIncomeDetailListViewController(nibName: "HYIncomeDetailListViewController", bundle: Bundle.main);
        vc.nTotalIncome = self.dataModel.total!;
        self.navigationController?.pushViewController(vc, animated: true);
    }


    override func viewDidLoad() {
        stringNavBarTitle = "我的收入";
        super.viewDidLoad()
        self.lblCashNumber.text = "￥ 0";
        
        //btnCash.hidden = true;

        // Do any additional setup after loading the view.
        
//        topBkView.layer.shadowColor = UIColor.black.cgColor;
//        topBkView.layer.shadowOffset = CGSize(width: 2, height: 2);
//        topBkView.layer.shadowOpacity = 0.3;
//        topBkView.layer.shadowRadius = 3;
//        topBkView.layer.cornerRadius = 3;
        
//        secondBkView.layer.shadowColor = UIColor.black.cgColor;
//        secondBkView.layer.shadowOffset = CGSize(width: 2, height: 2);
//        secondBkView.layer.shadowOpacity = 0.3;
//        secondBkView.layer.shadowRadius = 3;
//        secondBkView.layer.cornerRadius = 3;
        
        btnCash.layer.cornerRadius = 3;
        

    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated);
        //取个人账户信息
        
        self.loadData();
    }
    
    func loadData() -> Void {

        AskDogGetUserAccountInfoDataRequest().startRequest(viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    self.dataModel = HYIncomeDataModel(jsonDic: dic!);
                    if let n:Float = self.dataModel.balance{
                        let s:String = String(format: "%d",Int(n));
                        
                        self.lblCashNumber.text = "￥ \(s)";
                    }

                }
            }
        });
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
