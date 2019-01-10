//
//  HYShareSelectCategoryViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/22.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYShareSelectCategoryViewController: HYBaseViewController {
    
    var contentString:(String)!
    var linkIds:[String] = []
    var subject:String!
    
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var lblChannelName: UILabel!
    @IBOutlet weak var lblCategoryName: UILabel!
    
    var channelDataArray:[HYChannelInfoDataModel] = [HYChannelInfoDataModel]();
    var categoryDataArray:[HYCategoryDataModel] = [HYCategoryDataModel]();
    
    var channelNameListArray:[String] = [String]();
    var categoryNameListArray:[String] = [String]();
    
    var channelSelectedIndex:Int = -1;
    var categorySelectedIndex:Int = -1;
    
    var channelID:String!
    var categoryID:String!
    

    @IBAction func btnSelectCategoryClicked(_ sender: UIButton) {
        let alonePicker: LWPickerView = LWPickerView(aDataSource: self.categoryNameListArray, aTitle: "请选择分类");
        alonePicker.show()
        alonePicker.didClickDoneForTypeAloneHandler { (selectedRow, result) in
            print("selectedRow:\(selectedRow)")
            print("result:\(result)")
            self.categorySelectedIndex = selectedRow;
            self.lblCategoryName.text = result;
            
            let data:HYCategoryDataModel = self.categoryDataArray[selectedRow];
                self.categoryID = data.id;
            
        }
        alonePicker.didClickCancelHandler {
            print("dismiss")
        }
    }
    @IBAction func btnSelectChannelClicked(_ sender: UIButton) {

        let alonePicker: LWPickerView = LWPickerView(aDataSource: self.channelNameListArray, aTitle: "请选择频道");
        alonePicker.show()
        alonePicker.didClickDoneForTypeAloneHandler { (selectedRow, result) in
            print("selectedRow:\(selectedRow)")
            print("result:\(result)")
            self.channelSelectedIndex = selectedRow;
            self.lblChannelName.text = result;
            
            let data:HYChannelInfoDataModel = self.channelDataArray[selectedRow];
            self.channelID = data.id;

        }
        alonePicker.didClickCancelHandler {
            print("dismiss")
        }
    }
    
    func loadData() -> Void {
        
        
        //FIXME:分页未做
        let dic:[String: AnyObject]? = ["userId":(Global.sharedInstance.userInfo?.id)! as AnyObject,"page":"0" as AnyObject,"size":PAGE_SIZE as AnyObject];
        
        AskDogGetUserCreatedChannelListDataRequest().startRequest(requestParmer:dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    self.channelDataArray.removeAll();
                    self.channelNameListArray.removeAll();
                    if let resultObj = dic!["result"]{
                        let arr:NSArray = resultObj as! NSArray;
                        for item in arr{
                            let data:HYChannelInfoDataModel = HYChannelInfoDataModel(jsonDic: item as! NSDictionary);
                            self.channelDataArray.append(data);
                            self.channelNameListArray.append(data.name!);
                        }
                        
                    }
                }
            }
        });
        
        AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                
                self.categoryDataArray.removeAll();
                self.categoryNameListArray.removeAll();
                for item in arr!{
                    if let obj:AnyObject = item as AnyObject?{
                        let dic:NSDictionary = obj as! NSDictionary;
                        let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
                        self.categoryDataArray.append(data);
                        self.categoryNameListArray.append(data.name!);
                    }
                }
                

            }
        });
        
    }
    
    override func viewDidLoad() {
        stringNavBarTitle = "经验分享";
        super.viewDidLoad()
        
        bkView.layer.shadowColor = UIColor.black.cgColor;
        bkView.layer.shadowOffset = CGSize(width: 2, height: 2);
        bkView.layer.shadowOpacity = 0.3;
        bkView.layer.shadowRadius = 3;
        bkView.layer.cornerRadius = 3;
        
        let btnSend:UIButton = UIButton(type: UIButtonType.system);
        btnSend.frame = CGRect.zero;
        btnSend.setTitle("发布", for: UIControlState());
        btnSend.titleLabel?.font = UIFont.systemFont(ofSize: 13);
        btnSend.titleEdgeInsets = UIEdgeInsetsMake(3, 0, -3, 0);
        btnSend.addTarget(self, action: #selector(btnSendClicked), for: UIControlEvents.touchUpInside);
        btnSend.backgroundColor = UIColor.clear;
        btnSend.translatesAutoresizingMaskIntoConstraints = false;
        navBarView.addSubview(btnSend);
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["btnSend"] = btnSend;
        
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnSend(50)]-10-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-20-[btnSend]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));

        // Do any additional setup after loading the view.
        self.loadData();
    }
    
    func btnSendClicked() -> Void {
        

        
        if(-1 == self.channelSelectedIndex){
            CommonTools.showMessage("请选择频道！", vc: self, hr: {
                ()-> Void in
            });
            return;
        }
        if(-1 == self.categorySelectedIndex){
            CommonTools.showMessage("请选择分类！", vc: self, hr: {
                ()-> Void in
            });
            return;
        }
        
        let d:[String:AnyObject] = ["type":"TEXT" as AnyObject,
                                 "content":self.contentString as AnyObject,
                                 "picture_link_ids":self.linkIds as AnyObject];
        
        let dic:[String: AnyObject]? = ["subject":self.subject as AnyObject,
                                        "channel_id":self.channelID as AnyObject,
                                        "category_id":self.categoryID as AnyObject,
                                        "content":d as AnyObject];
        
        AskDogCreateTextExperienceDataRequest().startRequest(requestParmer:dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    
                    CommonTools.showMessage("发布成功！", vc: self, hr: {
                        ()-> Void in
                        
                        let data:HYExperienceDetailDataModel = HYExperienceDetailDataModel(jsonDic: dic!);
                        
                        let vc:HYContenDetailListViewController = HYContenDetailListViewController(nibName: "HYContenDetailListViewController", bundle: Bundle.main,data: data);
                        vc.isPopToRoot = true;
                        self.navigationController?.pushViewController(vc, animated: true);
                        
                        
                    });
                    

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
