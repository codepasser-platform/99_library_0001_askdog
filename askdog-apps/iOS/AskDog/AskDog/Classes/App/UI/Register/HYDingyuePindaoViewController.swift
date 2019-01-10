//
//  HYDingyuePindaoViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/5.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYDingyuePindaoViewController: HYBaseViewController,UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout {

    @IBOutlet weak var btnDingyue: UIButton!
    @IBOutlet weak var btnSkip: UIButton!
    @IBOutlet weak var collectionView: UICollectionView!
    
    var dataModelArray:[HYCategoryDataModel]!
    
    @IBAction func btnDingyueClicked(_ sender: UIButton) {
        
        var dingyueArr:[String?] = [String!]();
        
        for item in dataModelArray{
            let da:HYCategoryDataModel = item ;
            if(true == da.isSelected){
                dingyueArr.append(da.code);
            }
        }
        
        let dic:[String:[String?]] = ["category_codes":dingyueArr];
        
        AskDogModifyPersonalCategoriesListDataRequest().startRequest(requestParmer: dic as [String : AnyObject]?,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                
                print("AskDogModifyPersonalCategoriesListDataRequest 注册成功");
                CommonTools.showMessage("订阅成功!", vc: self, hr: {
                    ()->Void in
                    //跳到用户协议
                    Global.sharedInstance.beNeedReloadHomePage = true;
                    _ = self.navigationController?.popToRootViewController(animated: true);
                });
            }
        });
        
    }
    func setCategoryDataModel(_ da:[HYCategoryDataModel]!){
        dataModelArray = da;
    }
    
    @IBAction func btnSkipClicked(_ sender: UIButton) {
        Global.sharedInstance.beNeedReloadHomePage = true;
        _ = self.navigationController?.popToRootViewController(animated: true);
    }
    override func viewDidLoad() {
        stringNavBarTitle = "订阅频道";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        btnDingyue.layer.cornerRadius = 5;

        
        btnSkip.layer.cornerRadius = 5;


        
        let cellID:String = "HYDingyuePindaoCollectionViewCell";
        let nib:UINib = UINib(nibName: "HYDingyuePindaoCollectionViewCell", bundle: nil);
        collectionView.register(nib, forCellWithReuseIdentifier: cellID);
        collectionView.allowsMultipleSelection = true;
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    override func isShowLoginBtn() -> Bool {
        return false;
    }
    
    //MARK:UICollectionViewDelegateFlowLayout
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize{
        
        let s:CGSize = UIScreen.main.bounds.size;
        
        let w:CGFloat = (s.width-35)/3;
        let h:CGFloat = w * 9 / 11;
        
        return CGSize(width: w, height: h);
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets{
        return UIEdgeInsetsMake(5, 5, 5, 5);
    }
    

    
    //MARK:UICollectionViewDelegate
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath){
        let d:HYCategoryDataModel = dataModelArray[(indexPath as NSIndexPath).row];
        let cell:HYDingyuePindaoCollectionViewCell = collectionView.cellForItem(at: indexPath) as! HYDingyuePindaoCollectionViewCell;
        
        d.isSelected = !d.isSelected;
        print("d.isSelected=\(d.isSelected)");
        cell.selectedImgV.isHidden = !d.isSelected;
        collectionView.deselectItem(at: indexPath, animated: true);
    }
    
    //MARK:UICollectionViewDataSource
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1;
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return dataModelArray.count;
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell
    {
        let cellID:String = "HYDingyuePindaoCollectionViewCell";

        let cell:HYDingyuePindaoCollectionViewCell = collectionView.dequeueReusableCell(withReuseIdentifier: cellID, for: indexPath) as! HYDingyuePindaoCollectionViewCell;
        
        let d:HYCategoryDataModel = dataModelArray[(indexPath as NSIndexPath).row];
        cell.setDataModel(d);
        
        
        return cell;
    }
    
    override func isShowNotifyBtn() -> Bool {
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
