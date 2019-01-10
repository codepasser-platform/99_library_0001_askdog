//
//  HYVideoShareViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/22.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
//import AssetsLibrary
import MobileCoreServices
import Photos
import PhotosUI
import SVProgressHUD


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



class HYVideoShareViewController: HYBaseViewController ,UITableViewDelegate,UITableViewDataSource,UINavigationControllerDelegate,UIImagePickerControllerDelegate,
HYVideoShareUploadVideoTableViewCellDelegate,HYVideoSharePriceTableViewCellDelegate,HYCustomPickerDelegate,HYCustomPickerDataSource{

    @IBOutlet weak var tableView: UITableView!
    var isUploading:Bool = false;
    var strCurUploadingFilePath:String = "";
    //合成后的视频
    var strCurComposeFilePath:String = "";
    //视频是否合成
    var isVideoComposeSuccess = false;
    //正在转码中
    var isVideoComposing = false;
    
    /// 视频的缩略图
    var videoImg:UIImage = UIImage();
    
    var isVideoUploadSuccess = false;
    
    /// 不管用户是相册还是照相选择一个视频后，就不能进行第二次选择
    var isVideoSelected = false;
    
    var menuDataArray:[[String:AnyObject]] = [[String:AnyObject]]();
    var selectMenuDataArray:[[String:AnyObject]] = [[String:AnyObject]]();
    
    
    var beShowVideoPrice:Bool = false;
    
    var channelDataArray:[HYChannelInfoDataModel] = [HYChannelInfoDataModel]();
    //var categoryDataArray:[HYCategoryDataModel] = [HYCategoryDataModel]();
    var categoryDataArray:[HYCategoryTreeDataModel] = [HYCategoryTreeDataModel]();
    
    var channelNameListArray:[String] = [String]();
    var categoryNameListArray:[String] = [String]();
    
    var channelSelectedIndex:Int = -1;
    var categorySelectedIndex:Int = -1;
    
    var channelID:String!
    var categoryID:String = "";
    
    var curSelectedLeftIndex:Int = 0;
    var curSelectedRightIndex:Int = 0;
    
    var leftCategoryDataArray:[HYCategoryTreeDataModel]!
    var rightCategoryDataArray:[HYCategoryTreeDataModel]!
    
    var video_link_id:String!
    
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
        
//        AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
//            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
//            if(true == beOk){
//                
//                self.categoryDataArray.removeAll();
//                self.categoryNameListArray.removeAll();
//                for item in arr!{
//                    if let obj:AnyObject = item{
//                        let dic:NSDictionary = obj as! NSDictionary;
//                        let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
//                        self.categoryDataArray.append(data);
//                        self.categoryNameListArray.append(data.name!);
//                    }
//                }
//                
//                
//            }
//        });
        
        //获取分类信息
        AskDogGetAllCategoryDataRequest().startRequest(viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                
                if(nil != arr && arr?.count > 0){
                    for item in arr!{
                        let tempD:NSDictionary = item as! NSDictionary;
                        let tempData:HYCategoryTreeDataModel = HYCategoryTreeDataModel(jsonDic: tempD);
                        self.categoryDataArray.append(tempData);
                    }
                }
               
//                self.categoryDataArray.removeAll();
//                self.categoryNameListArray.removeAll();
//                for item in arr!{
//                    if let obj:AnyObject = item{
//                        let dic:NSDictionary = obj as! NSDictionary;
//                        let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
//                        self.categoryDataArray.append(data);
//                        self.categoryNameListArray.append(data.name!);
//                    }
//                }
                
                
            }
        });
        
    }
    
    override func btnReturnClicked(_ sender: UIButton) {
        
        if(true == self.isUploading || true == self.isVideoUploadSuccess){
            
            CommonTools.showModalMessage("确定删除视频吗？", title: "提示", vc: self, hasCancelBtn: true, hr: {
                (beOK:Bool) -> Void in
                if(true == beOK){
                    HYUploadVideoManager.shared().stopUpload();
                    HYUploadVideoManager.shared().deleteFile(self.strCurUploadingFilePath);
                    
                    _ = self.navigationController?.popViewController(animated: true);
                }
                
            });
            
        }else{
            
            if(true == self.isVideoSelected){
                CommonTools.showModalMessage("确定删除视频吗？", title: "提示", vc: self, hasCancelBtn: true, hr: {
                    (beOK:Bool) -> Void in
                    if(true == beOK){
                        if(true == self.isVideoComposeSuccess){
                            CommonTools.deleteFile(filePath: self.strCurComposeFilePath);
                            CommonTools.deleteFile(filePath: self.strCurUploadingFilePath);
                        }else{
                            CommonTools.deleteFile(filePath: self.strCurUploadingFilePath);
                        }
                        
                        _ = self.navigationController?.popViewController(animated: true);
                    }
                    
                });
            }
            
            _ = self.navigationController?.popViewController(animated: true);
        }
        
        
    }
    
    override func viewDidLoad() {
        stringNavBarTitle = "视频分享";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let btnSend:UIButton = UIButton(type: UIButtonType.custom);
        btnSend.frame = CGRect.zero;
        btnSend.setTitle("发布", for: UIControlState());
        btnSend.titleLabel?.font = UIFont.systemFont(ofSize: 13);
        btnSend.setTitleColor(UIColor.white, for: .normal);
        btnSend.titleEdgeInsets = UIEdgeInsetsMake(3, 0, -3, 0);
        btnSend.addTarget(self, action: #selector(btnSendClicked), for: UIControlEvents.touchUpInside);
        btnSend.backgroundColor = UIColor.clear;
        btnSend.translatesAutoresizingMaskIntoConstraints = false;
        navBarView.addSubview(btnSend);
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["btnSend"] = btnSend;
        
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnSend(50)]-10-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-20-[btnSend]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        if tableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tableView.separatorInset = UIEdgeInsets.zero;
        }
        if tableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tableView.layoutMargins = UIEdgeInsets.zero;
        }
        
        let cellID1:String = "HYVideoShareUploadVideoTableViewCell";
        let nib1:UINib = UINib(nibName: "HYVideoShareUploadVideoTableViewCell", bundle: nil);
        tableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        let cellID2:String = "HYVideoShareInputTitleTableViewCell";
        let nib2:UINib = UINib(nibName: "HYVideoShareInputTitleTableViewCell", bundle: nil);
        tableView.register(nib2, forCellReuseIdentifier: cellID2);
        
        let cellID3:String = "HYVideoShareSelectedChannelTableViewCell";
        let nib3:UINib = UINib(nibName: "HYVideoShareSelectedChannelTableViewCell", bundle: nil);
        tableView.register(nib3, forCellReuseIdentifier: cellID3);
        
        let cellID4:String = "HYVideoSharePriceTableViewCell";
        let nib4:UINib = UINib(nibName: "HYVideoSharePriceTableViewCell", bundle: nil);
        tableView.register(nib4, forCellReuseIdentifier: cellID4);
        
//        menuDataArray = [["title":"视频费用" as AnyObject,"type":0 as AnyObject,"boolValue":false as AnyObject,"value":"0" as AnyObject],
//        ["title":"1元" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"0" as AnyObject],
//        ["title":"10元" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"0" as AnyObject],
//        ["title":"自定义价格" as AnyObject,"type":2 as AnyObject,"boolValue":false as AnyObject,"value":"0" as AnyObject]];
        
        menuDataArray = [["title":"视频费用" as AnyObject,"type":0 as AnyObject,"boolValue":false as AnyObject,"value":"0" as AnyObject],
                         ["title":"1元" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"0" as AnyObject]];
        
        selectMenuDataArray = [["title":"选择频道" as AnyObject,"valueTitle":"未选择" as AnyObject,"type":0 as AnyObject],["title":"选择分类" as AnyObject,"valueTitle":"未选择" as AnyObject,"type":1 as AnyObject]];
        
        
        self.loadData();
        

    }
    
    
    func btnSendClicked() -> Void{
        
//        let cell:HYVideoShareUploadVideoTableViewCell = self.tableView.cellForRow(at: IndexPath(row: 0, section: 0)) as! HYVideoShareUploadVideoTableViewCell;
//        cell.hideUI();
//        cell.setProgressViewVlaue(Int(50));
//        
//        return;
        

     //   HYVideoShareInputTitleTableViewCell
        
        let cellTitle:HYVideoShareInputTitleTableViewCell = self.tableView.cellForRow(at: IndexPath(row: 1, section: 0)) as! HYVideoShareInputTitleTableViewCell;
        cellTitle.textFiledTitle.resignFirstResponder();
        
//        if(true == self.beShowVideoPrice){
//            let cellPrice:HYVideoSharePriceTableViewCell = self.tableView.cellForRow(at: IndexPath(row: 3, section: 2)) as! HYVideoSharePriceTableViewCell;
//            cellPrice.textFieldPrice.resignFirstResponder();
//        }
        
        
//        if(false == isVideoUploadSuccess){
//            CommonTools.showMessage("未上传视频！", vc: self, hr: {
//                () -> Void in
//                
//            });
//            return;
//        }
        
        if(false == self.isVideoSelected){
            CommonTools.showMessage("请选择视频!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
//        

        if(true == self.isVideoComposing){
            CommonTools.showMessage("视频正在合并中！", vc: self, hr: {
                () -> Void in
        
            });
            return;
        }
        
        if(true == isUploading){
            CommonTools.showMessage("视频正在上传中！", vc: self, hr: {
                () -> Void in
        
            });
            return;
        }
        
        guard cellTitle.textFiledTitle.text != "" else{
            CommonTools.showMessage("标题不能为空!",  vc: self,  hr: {
                ()->Void in
                cellTitle.textFiledTitle.becomeFirstResponder();
            });
            return;
        }
        
        if(-1 == self.channelSelectedIndex){
            CommonTools.showMessage("请选择频道！", vc: self, hr: {
                ()-> Void in
            });
            return;
        }
        if("" == self.categoryID){
            CommonTools.showMessage("请选择分类！", vc: self, hr: {
                ()-> Void in
            });
            return;
        }
        
        var d1:[String:AnyObject] = self.menuDataArray[0];
        var beHavePrice:Bool = false;
        CommonTools.boolCheckNullObject(d1["boolValue"], callBack: {
            (be:Bool) -> Void in
            beHavePrice = be;
        });
        
        //如果有价钱
        var price:Int = 0;
        if(true == beHavePrice){
            
            var beGetValue:Bool = false;
            
            let d1 = self.menuDataArray[1];
            //let d2 = self.menuDataArray[2];
            //let d3 = self.menuDataArray[3];
            //var beSelected:Bool = false;
            CommonTools.boolCheckNullObject(d1["boolValue"], callBack: {
                (be:Bool) -> Void in
                if(true == be){
                    if(false == beGetValue){
                        price = 1;
                        beGetValue = true;
                    }
                }
            });
//            CommonTools.boolCheckNullObject(d2["boolValue"], callBack: {
//                (be:Bool) -> Void in
//                if(true == be){
//                    if(false == beGetValue){
//                        price = 10;
//                        beGetValue = true;
//                    }
//                }
//            });
            //再检测输入框
//            CommonTools.stringCheckNullObject(d3["value"], callBack: {
//                (str:String) -> Void in
//                
//                if(false == beGetValue){
//                    price = Int(str)!;
//                    beGetValue = true;
//                }
//            });
            
        }
        
        print("售价==\(price)");
        
        
        //先上传视频
        var strPath = "";
        if(self.isVideoComposeSuccess == true){
            //上传压缩后的视频
            strPath = self.strCurComposeFilePath;
        }else{
            //上传原视频
            strPath = self.strCurUploadingFilePath;
        }
        
        let cell:HYVideoShareUploadVideoTableViewCell = self.tableView.cellForRow(at: IndexPath(row: 0, section: 0)) as! HYVideoShareUploadVideoTableViewCell;
        cell.setUIState(state: 2);
        
        
        
        self.uploadVideo(strPath, resultCallBack: {
            (be:Bool) -> Void in
            if(true == be){
                
                let d:[String:AnyObject] = ["type":"VIDEO" as AnyObject,
                                            "video_link_id":self.video_link_id as AnyObject];
                let publishDic:[String: AnyObject] = ["subject":cellTitle.textFiledTitle.text! as AnyObject,
                                                      "channel_id":self.channelID as AnyObject,
                                                      "category_id":self.categoryID as AnyObject,
                                                      "price":price * 100 as AnyObject,
                                                      "content":d as AnyObject];
                
                
                AskDogCreateTextExperienceDataRequest().startRequest(requestParmer:publishDic,viewController:self,completionHandler: {
                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                    if(true == beOk){
                        if(nil != dic){
                            
                            CommonTools.showMessage("发布成功！", vc: self, hr: {
                                ()-> Void in
                                
                                let data:HYExperienceDetailDataModel = HYExperienceDetailDataModel(jsonDic: dic!);
                                
                                let vc:HYContenDetailListViewController = HYContenDetailListViewController(nibName: "HYContenDetailListViewController", bundle: Bundle.main,data: data);
                                vc.isPopToRoot = true;
                                vc.isPushFromSharedVideoView = true;
                                self.navigationController?.pushViewController(vc, animated: true);
                                
                                
                            });
                            
                            
                        }
                    }
                });
            }else{
            
            }
            
        });
        
        
//        if(true == CommonTools.compareDataInActivity()){
//            dic["activity"] = "MOON_FESTIVAL" as AnyObject?;
//        }
 
        //return;

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
//    override func btnReturnClicked(sender: UIButton) {
//        if (self.tfTitle.text != "" || self.tuwenEditView.textViewInput.text != ""){
//            CommonTools.showModalMessage("您分享的经验尚未发布，返回将失去您编辑的内容", title: "提示", vc: self, hasCancelBtn: true, OKBtnTitle:"离开",hr: {
//                (beOK:Bool) -> Void in
//                if(true == beOK){
//                    self.tfTitle.resignFirstResponder();
//                    self.tuwenEditView.stopEditing();
//                    
//                    self.navigationController?.popViewControllerAnimated(true);
//                }
//            });
//        }else{
//            self.navigationController?.popViewControllerAnimated(true);
//        }
//    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    func price1Selected() -> Void {
        let d:[String:AnyObject] = ["title":"1元" as AnyObject,"type":1 as AnyObject,"boolValue":true as AnyObject,"value":"0" as AnyObject];
        //let d1:[String:AnyObject] = ["title":"10元" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"0" as AnyObject];
        
        self.menuDataArray[1] = d;
        self.tableView.reloadRows(at: [IndexPath(row: 1, section: 2)], with: .fade);
       // self.menuDataArray[2] = d1;
        
       // self.tableView.reloadRows(at: [IndexPath(row: 1, section: 2),IndexPath(row: 2, section: 2)], with: .fade);
    }
    
    func price2Selected() -> Void {
        let d:[String:AnyObject] = ["title":"1元" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"0" as AnyObject];
       // let d1:[String:AnyObject] = ["title":"10元" as AnyObject,"type":1 as AnyObject,"boolValue":true as AnyObject,"value":"0" as AnyObject];
        
        self.menuDataArray[1] = d;
        self.tableView.reloadRows(at: [IndexPath(row: 1, section: 2)], with: .fade);
       // self.menuDataArray[2] = d1;
        
       // self.tableView.reloadRows(at: [IndexPath(row: 1, section: 2),IndexPath(row: 2, section: 2)], with: .fade);
    }
    
    func setPickerDefaultValue(_ picker:HYCustomPicker,left leftName:String,right rightName:String) -> Void {
        
        
        for (index,value) in categoryDataArray.enumerated(){
            let data1:HYCategoryTreeDataModel = value as HYCategoryTreeDataModel;
            if let name1 = data1.name{
                if(name1 == leftName){
                    picker.selectRow(index, inComponent: 0, animated: true);
                    
                    self.curSelectedLeftIndex = index;
                    
                    if let arr:[HYCategoryTreeDataModel] = data1.children{
                        for (ind,va) in arr.enumerated(){
                            let data2:HYCategoryTreeDataModel = va as HYCategoryTreeDataModel;
                            if let name2 = data2.name{
                                if(name2 == rightName){
                                    self.curSelectedRightIndex = ind;
                                    self.rightCategoryDataArray = arr;
                                    picker.selectRow(ind, inComponent: 1, animated: true);
                                }
                            }
                        }
                    }
                    
                }
            }
        }
    }
    
    //MARK:UITableViewDelegate
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(0 == (indexPath as NSIndexPath).section){
            if(0 == (indexPath as NSIndexPath).row){
                
                if(false == self.isVideoSelected){
                    //调出视频相册
                    self.openVideoList();
                }


            }
        }else if(1 == (indexPath as NSIndexPath).section){
            if(0 == (indexPath as NSIndexPath).row){
                let alonePicker: LWPickerView = LWPickerView(aDataSource: self.channelNameListArray, aTitle: "请选择频道");
                alonePicker.show()
                alonePicker.didClickDoneForTypeAloneHandler { (selectedRow, result) in
                    print("selectedRow:\(selectedRow)")
                    print("result:\(result)")
                    self.channelSelectedIndex = selectedRow;
                    
                    let d:[String:AnyObject] = ["title":"选择频道" as AnyObject,"valueTitle":result as AnyObject,"type":0 as AnyObject];
                    
                    self.selectMenuDataArray[0] = d;
                    
                    self.tableView.reloadRows(at: [IndexPath(row: 0, section: 1)], with: .fade);
                    
                    let data:HYChannelInfoDataModel = self.channelDataArray[selectedRow];
                    self.channelID = data.id;
                    
                    
                }
                alonePicker.didClickCancelHandler {
                    print("dismiss")
                }
            }else{
                
                self.leftCategoryDataArray = self.categoryDataArray;
                self.rightCategoryDataArray = self.leftCategoryDataArray[0].children;
                
                self.curSelectedRightIndex = 0;
                self.curSelectedLeftIndex = 0;
                
                let v:HYCustomPicker = HYCustomPicker(frame: CGRect.zero);
                v.delegate = self;
                v.dataSource = self;
                //v.selectedToValue((0,0));
                
                v.show(inView: self.view, callBack: {
                    (obj:AnyObject) -> Void in
                    
                    let index:Int = obj as! Int;
                    if(1 == index){
                        //取选择的值
                        let leftData:HYCategoryTreeDataModel = self.leftCategoryDataArray[self.curSelectedLeftIndex];
                        let rightData:HYCategoryTreeDataModel = self.rightCategoryDataArray[self.curSelectedRightIndex];
                        
                        
                        var leftString:String = "";
                        var rightString:String = "";
                        
                        if let name = leftData.name{
                            leftString = name;
                        }
                        if let name = rightData.name{
                            rightString = name;
                        }
                        
                        //self.categoryID
                        if let categoryID:String = rightData.id{
                            self.categoryID = categoryID;
                        }
                        
                        let str:String = "\(leftString) \(rightString)";
                        let d:[String:AnyObject] = ["title":"选择分类" as AnyObject,"valueTitle":str as AnyObject,"type":1 as AnyObject];
                        self.selectMenuDataArray[1] = d;
                        self.tableView.reloadRows(at: [IndexPath(row: 1, section: 1)], with: .fade);
                        

                    }
                    
                });
//                //活动期间加标题
//                if(true == CommonTools.compareDataInActivity()){
//                    
//                    self.setPickerDefaultValue(v, left: "美食", right: "美食比拼");
//                }
                
                //self.setPickerDefaultValue(v, left: "综合", right: "科学");
                
//                return;
//                
//                let alonePicker: LWPickerView = LWPickerView(aDataSource: self.categoryNameListArray, aTitle: "请选择分类");
//                alonePicker.show()
//                alonePicker.didClickDoneForTypeAloneHandler { (selectedRow, result) in
//                    print("selectedRow:\(selectedRow)")
//                    print("result:\(result)")
//                    self.categorySelectedIndex = selectedRow;
//                    
//                    let d:[String:AnyObject] = ["title":"选择分类","valueTitle":result,"type":1];
//                    
//                    self.selectMenuDataArray[1] = d;
//                    
//                    self.tableView.reloadRowsAtIndexPaths([NSIndexPath(forRow: 1, inSection: 1)], withRowAnimation: .Fade);
//                    
////                    if let data:HYCategoryDataModel = self.categoryDataArray[selectedRow]{
////                        self.categoryID = data.id;
////                    }
//                    
//                }
//                alonePicker.didClickCancelHandler {
//                    print("dismiss")
//                }
            }
        }else{
            if(1 == (indexPath as NSIndexPath).row){
                self.price1Selected();
            }else if(2 == (indexPath as NSIndexPath).row){
                self.price2Selected();
            }
        }
        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if(1 == section){
            return 3;
        }else if(2 == section){
            return 5;
        }
        return 0;
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerV:UIView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.size.width, height: 5));
        headerV.backgroundColor = UIColor.clear;
        return headerV;
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        
        if(0 == (indexPath as NSIndexPath).section){
            if(0 == (indexPath as NSIndexPath).row){
                if(true == IS_IPHONE6S_PLUS_DEV){
                    return 233;
                }else if(true == IS_IPHONE66S_DEV){
                    return 211.0;
                }else if(true == IS_IPHONE55S_DEV){
                    return 180.0;
                }
            }else {
                return 50;
            }
        }else if(1 == (indexPath as NSIndexPath).section){
            return 50;
        }else{
            return 50;
        }
        
        

        return 0;
    }
    
    //解决分割线不对齐问题
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        if cell.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            cell.separatorInset = UIEdgeInsets.zero;
        }
        if cell.responds(to: #selector(setter: UIView.layoutMargins)){
            cell.layoutMargins = UIEdgeInsets.zero;
        }
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 3;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        if(0 == section){
            return 2;
        }else if(1 == section){
            return 2;
        }else{
            if(beShowVideoPrice == false){
                return 1;
            }
            return menuDataArray.count;
        }
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cellID:String! = "";
        if(0 == (indexPath as NSIndexPath).section){
            if(0 == (indexPath as NSIndexPath).row){
                cellID = "HYVideoShareUploadVideoTableViewCell";
            }else {
                cellID = "HYVideoShareInputTitleTableViewCell";
            }
        }else if(1 == (indexPath as NSIndexPath).section){
            cellID = "HYVideoShareSelectedChannelTableViewCell";
        }else{
            cellID = "HYVideoSharePriceTableViewCell";
        }
        
        
        
        
        if(0 == (indexPath as NSIndexPath).section){
            
            if(0 == (indexPath as NSIndexPath).row){
            let cell:UITableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID)!;
                
            let temp:HYVideoShareUploadVideoTableViewCell = cell as! HYVideoShareUploadVideoTableViewCell;
                print("\(temp)");
                temp.delegate = self;
                if(true == self.isVideoSelected){
                    if(true == self.isVideoComposeSuccess){
                        //cell1.player.playWithURL(URL(fileURLWithPath: self.strCurComposeFilePath));
                        temp.setVideoData(videoUrl: self.strCurComposeFilePath,videoImg:self.videoImg);
                    }else{
                        temp.setVideoData(videoUrl: self.strCurUploadingFilePath,videoImg:self.videoImg);
                    }
                    temp.setUIState(state: 3);
                }
                return temp;
            }else {
                let cell:UITableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID)!;
                return cell;
            }
        }else if(1 == (indexPath as NSIndexPath).section){
            let cell:UITableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID)!;
            let temp:HYVideoShareSelectedChannelTableViewCell = cell as! HYVideoShareSelectedChannelTableViewCell;
            if(0 == (indexPath as NSIndexPath).row){
                temp.cornerType = CornerMaskType.cornerMaskType_Top;
            }else{
                temp.cornerType = CornerMaskType.cornerMaskType_Bottom;
            }
            let d:[String:AnyObject] = self.selectMenuDataArray[(indexPath as NSIndexPath).row];
            temp.setData(d);
            return cell;
        }else{
            let cell:UITableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID)!;
            let temp:HYVideoSharePriceTableViewCell = cell as! HYVideoSharePriceTableViewCell;
            temp.delegate = self;
            temp.cellIndex = indexPath;
            if(0 == (indexPath as NSIndexPath).row){
                temp.cornerType = CornerMaskType.cornerMaskType_Top;
            }else if(3 == (indexPath as NSIndexPath).row){
                temp.cornerType = CornerMaskType.cornerMaskType_Bottom;
            }
            
            let d:[String:AnyObject] = self.menuDataArray[(indexPath as NSIndexPath).row];
            temp.setData(d);
            return cell;
        }
        

        
        
        
        //return nil;
    }
    
    

    //MARK:oss上传视频
    func useAliyunOSSUploadVideo(_ data:HYAccessTokenDataModel,uploadFilePath filePath:String,resultCallBack call:@escaping ((Bool) -> Void)) -> Void {
//        let upload:VODUploadClient = VODUploadClient();
//        
//        var strExpire = "";
//        if let expire = data.expire{
//            strExpire = String(expire);
//        }
//        
//        upload.initWithAK(data.OSSAccessKeyId, accessKeySecret: data.secret_key, secretToken: "", expireTime: "", onUploadSucceed: nil, onUploadFailed: nil, onUploadProgress: nil, onUploadTokenExpired: nil);
        

        
//        upload.initWithAK(data.OSSAccessKeyId, accessKeySecret: data.secret_key, secretToken: "", expireTime: strExpire, onUploadSucceed: {
//            (str) -> Void in
//            
//            print("onUploadSucceed file:\(str)");
//            
//            }, onUploadFailed: {
//                (filePath,code,msg) -> Void in
//                
//                print("onUploadFailed filePath=\(filePath) code=\(code) msg=\(msg)");
//                
//            }, onUploadProgress: {
//                (filePath,uploadedSize,totalSize) -> Void in
//                
//                print("onUploadFailed filePath=\(filePath) uploadedSize=\(uploadedSize) totalSize=\(totalSize)");
//                
//            }, onUploadTokenExpired: {
//                () -> Void in
//                
//                print("onUploadTokenExpired");
//        });
        
//        upload.addFile(filePath, endpoint: data.endpoint, bucket: data.bucket, object: data.key);
//        upload.startUpload();
        
        
        var strExpire = "";
        if let expire = data.expire{
            strExpire = String(expire);
        }
        
        
        
        HYUploadVideoManager.shared().uploadVideo(usingAliyunOSS: data.OSSAccessKeyId, accessKeySecret: data.secret_key, secretToken: "", expireTime: strExpire, bucketName: data.bucket, endpoint: data.endpoint, uploadFilePath: filePath, uploadFileKey: data.key, onUploadSucceed: {
                (str) -> Void in
            
            print("onUploadSucceed file:\(str)");
            
            self.isUploading = false;
            self.isVideoUploadSuccess = true;
            
            DispatchQueue.main.async(execute: {
                () -> Void in
                
                let cell:HYVideoShareUploadVideoTableViewCell = self.tableView.cellForRow(at: IndexPath(row: 0, section: 0)) as! HYVideoShareUploadVideoTableViewCell;
                cell.setUIState(state: 3);

                //发布视频
                call(true);
                
            });
            
            }, onUploadFailed: {
                (filePath,code,msg) -> Void in
                
                self.isUploading = false;
                self.isVideoUploadSuccess = false;
                
                print("onUploadFailed filePath=\(filePath) code=\(code) msg=\(msg)");
                
                DispatchQueue.main.async(execute: {
                    () -> Void in
                    
                    let cell:HYVideoShareUploadVideoTableViewCell = self.tableView.cellForRow(at: IndexPath(row: 0, section: 0)) as! HYVideoShareUploadVideoTableViewCell;
                    
                    
                    if(code == -1006) {
                        // user cancel, do something...
                        
                        
                        log.ln("stop upload video")/;
                        cell.setProgressViewVlaue(0);
                        cell.setUIState(state: 3);
                        CommonTools.showMessage("上传已取消!", vc: self, hr: {
                            ()-> Void in
                            
                        });
                    }else{
                        cell.setUIState(state: 3);
                        CommonTools.showMessage("上传失败，请重新上传!", vc: self, hr: {
                            ()-> Void in
                            
                        });
                    }
                    

                    
                    
                    
                });
                
            }, onUploadProgress: {
                (filePath,uploadedSize,totalSize) -> Void in
                
                
                DispatchQueue.main.async(execute: {
                    () -> Void in
                    
                    print("onUploadFailed filePath=\(filePath) uploadedSize=\(uploadedSize) totalSize=\(totalSize)");
                    
                    let f:Float = Float(uploadedSize) / Float(totalSize);
                    
                    let n:Int = Int(f * 100);
                    
                    let cell:HYVideoShareUploadVideoTableViewCell = self.tableView.cellForRow(at: IndexPath(row: 0, section: 0)) as! HYVideoShareUploadVideoTableViewCell;
                    cell.setUIState(state: 2);
                    cell.setProgressViewVlaue(n);
                    
                });
                
            }, onUploadTokenExpired: {
                () -> Void in
                
                self.isUploading = false;
                
                DispatchQueue.main.async(execute: {
                    () -> Void in
                    
                    let cell:HYVideoShareUploadVideoTableViewCell = self.tableView.cellForRow(at: IndexPath(row: 0, section: 0)) as! HYVideoShareUploadVideoTableViewCell;
                    cell.setUIState(state: 3);
                    cell.player.isHidden = false;
                    
                    cell.setUIState(state: 3);
                    CommonTools.showMessage("上传失败，请重新上传!", vc: self, hr: {
                        ()-> Void in
                        
                    });
                    
                });
                
                print("onUploadTokenExpired");
                
        });
        
        self.isUploading = true;
        self.isVideoUploadSuccess = false;

    }
    
    func uploadVideo(_ filePath:String,resultCallBack call:@escaping ((Bool) -> Void)) -> Void {
        let dic:[String: AnyObject]? = ["extention":"mov" as AnyObject,"type":"EXPERIENCE_VIDEO" as AnyObject];
        //var fileLinkID:String = "";
       //z var fileUrl:String = "";
        
        AskDogGetAccessTokenDataRequest().startRequest(requestParmer: dic,  viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    let data:HYAccessTokenDataModel = HYAccessTokenDataModel(jsonDic: dic!);
                    self.video_link_id = data.link_id;
                    self.useAliyunOSSUploadVideo(data,uploadFilePath:filePath,resultCallBack:{
                        (be:Bool) -> Void in
                        
                        call(be);
                        
                    });
                    
                } 
            }
        });
    }
    
    func openVideoList(){
        let canCamera:Bool = UIImagePickerController.isSourceTypeAvailable(.camera);
        let canAlbum:Bool = UIImagePickerController.isSourceTypeAvailable(.photoLibrary);
        
        if(false == canAlbum && false == canCamera){
            return;
        }
        
        let actionSheet:UIAlertController = UIAlertController(title: "上传视频", message: "", preferredStyle: UIAlertControllerStyle.actionSheet);
        
        if(canCamera){
            let cameraAction:UIAlertAction = UIAlertAction(title: "录视频", style: UIAlertActionStyle.destructive, handler: {
                (action:UIAlertAction) -> Void in
                print("camera in");
                
                let pc:UIImagePickerController = UIImagePickerController();
                pc.sourceType = UIImagePickerControllerSourceType.camera;
                pc.delegate = self;
                pc.allowsEditing = true;
                pc.cameraDevice = UIImagePickerControllerCameraDevice.rear;
                pc.cameraFlashMode = UIImagePickerControllerCameraFlashMode.off;
                pc.modalTransitionStyle = UIModalTransitionStyle.coverVertical;
                pc.mediaTypes = [kUTTypeMovie as String];
                pc.videoQuality = .typeIFrame1280x720;
                pc.videoMaximumDuration = 10 * 60;
                self.present(pc, animated: true, completion: {
                    () -> Void in
                    print("UIImagePickerController camera in");
                })
                
            })
            
            actionSheet.addAction(cameraAction);
        }
        if(canAlbum){
            let albumAction:UIAlertAction = UIAlertAction(title: "从相册中选择", style: UIAlertActionStyle.default, handler: {
                (action:UIAlertAction) -> Void in
                print("album in");
                
                let pc:UIImagePickerController = UIImagePickerController();
                pc.sourceType = UIImagePickerControllerSourceType.photoLibrary;
                pc.delegate = self;
                pc.allowsEditing = true;
                pc.modalTransitionStyle = UIModalTransitionStyle.coverVertical;
                pc.mediaTypes = [kUTTypeMovie as String];
                pc.videoQuality = .typeIFrame1280x720;
                pc.videoMaximumDuration = 60 * 10;
                self.present(pc, animated: true, completion: {
                    () -> Void in
                    print("UIImagePickerController camera in");
                })
                
            })
            
            actionSheet.addAction(albumAction);
        }
        
        let cancelAction:UIAlertAction = UIAlertAction(title: "取消", style: UIAlertActionStyle.cancel, handler: {
            (action:UIAlertAction) -> Void in
            print("cancelAction in");
            
            self.dismiss(animated: true, completion: nil);
            
        })
        
        actionSheet.addAction(cancelAction);
        
        
        //            let popOver:UIPopoverPresentationController = actionSheet.popoverPresentationController!;
        //            popOver.sourceView = sender;
        //            popOver.sourceRect = sender.bounds;
        //            popOver.permittedArrowDirections = UIPopoverArrowDirection.Any;
        
        self.present(actionSheet, animated: true, completion: {
            () -> Void in
            print("ok");
        })
    }
    
    //MARK:UIImagePickerControllerDelegate
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]){
        
        if let type = info[UIImagePickerControllerMediaType]{
            let strType = type as! String;
            if (strType == kUTTypeMovie as String){
                

                if let pathObj = info[UIImagePickerControllerMediaURL]{
                    let url:URL = pathObj as! URL;
                    
                    strCurUploadingFilePath = url.path;
                    print("url = \(strCurUploadingFilePath)");
                    //let d:NSData = NSData(contentsOfFile: strPath)!;
                    // print("data size = \(d.length)");
                    

                    
                    //取视图的缩略图
                    let avAsset = AVAsset(url: url);
                    let generator = AVAssetImageGenerator(asset: avAsset);
                    
                    let time:CMTime = CMTimeMakeWithSeconds(0.0, 10);
                    var actualTime:CMTime = CMTimeMake(0, 0);
                    let imageRef:CGImage = try! generator.copyCGImage(at: time, actualTime: &actualTime);
                    videoImg = UIImage(cgImage: imageRef);
                    print("frame img = \(videoImg)")
                    let cell:HYVideoShareUploadVideoTableViewCell = self.tableView.cellForRow(at: IndexPath(row: 0, section: 0)) as! HYVideoShareUploadVideoTableViewCell;

                    //MARK:选择视频成功

                    
                    self.isVideoSelected = true;
                    cell.setVideoData(videoUrl: strCurUploadingFilePath, videoImg: videoImg);
                    cell.setUIState(state: 3);
                    //self.videoCell.btnAddMusic.isHidden = false;
                    
                    //FIXME:TEST
                    //合并音视频
//                    let filePath:String = Bundle.main.path(forResource: "ahq", ofType: "mp3")!;
//                    MediaTools.sharedInstance.videoComposeVideoAudio(video: strCurUploadingFilePath, audio: filePath, callBack: {
//                        (url:URL) -> Void in
//                        
//                        
//                        
//                        
//                    });
                    
                    
                    

                   // self.uploadVideo(strCurUploadingFilePath);
                    
                    
//========================================华丽分割线========================================
                    
//                    if let videoPath = url.path{
//
//                        strCurUploadingFilePath = videoPath as String;
//                        print("url = \(strCurUploadingFilePath)");
//                        //let d:NSData = NSData(contentsOfFile: strPath)!;
//                       // print("data size = \(d.length)");
//                        
//                        //取视图的缩略图
//                        let avAsset = AVAsset(url: url);
//                        let generator = AVAssetImageGenerator(asset: avAsset);
//                        
//                        let time:CMTime = CMTimeMakeWithSeconds(0.0, 10);
//                        var actualTime:CMTime = CMTimeMake(0, 0);
//                        let imageRef:CGImage = try! generator.copyCGImage(at: time, actualTime: &actualTime);
//                        let frameImg:UIImage = UIImage(cgImage: imageRef);
//                        print("frame img = \(frameImg)")
//                        let cell:HYVideoShareUploadVideoTableViewCell = self.tableView.cellForRow(at: IndexPath(row: 0, section: 0)) as! HYVideoShareUploadVideoTableViewCell;
//                        cell.imgView.image = frameImg;
//                        cell.imgView.isHidden = false;
//                        self.uploadVideo(strCurUploadingFilePath);
//                        
////                        CommonTools.saveFile("upload.mov", data: d,hr: {
////                            (beCreated:Bool,filePath:String) -> Void in
////                            if(true == beCreated){
////                                self.uploadVideo(filePath);
////                            }
////                        });
//                        
//                        //保存到相册
////                        let createVideoRequest = PHAssetChangeRequest.creationRequestForAssetFromVideoAtFileURL(url);
////                        let assetPlaceHolder = createVideoRequest?.placeholderForCreatedAsset;
////                        let albumChangeRequest = PHAssetCollectionChangeRequest(forAssetCollection: .album);
//                        
////                        do {
////                            try PHPhotoLibrary.sharedPhotoLibrary().performChangesAndWait({
////                                () -> Void in
////                                PHAssetChangeRequest.creationRequestForAssetFromVideoAtFileURL(url);
////                                CommonTools.showMessage("视频保存成功！", vc: self, hr: {
////                                    () -> Void in
////                                    
////                                });
////                            });
////                        } catch let error as NSError? {
////                            print("error saving core data: \(error)")
////                        }
//                        
//                        
//                        
////                        let docDirs = NSSearchPathForDirectoriesInDomains(.DocumentDirectory, .UserDomainMask, true);
////                        let path = "\(docDirs)/v.mov";
////                        print("video path = \(path)");
////                        NSFileManager.defaultManager().createFileAtPath(path, contents: d, attributes: nil);
//                        
//                        //let filePath:String! = NSBundle.mainBundle().pathForResource("1", ofType: "mp4");
//                        
//
//                    }
 
                }
            }
        }
        
        
//        var image:UIImage!
//        if(picker.allowsEditing){
//            image = info[UIImagePickerControllerEditedImage] as! UIImage;
//        }else{
//            image = info[UIImagePickerControllerOriginalImage] as! UIImage;
//        }
//        
//        let cell:HYUserInfoListTopTableViewCell = tableView.cellForRowAtIndexPath(NSIndexPath(forRow: 0, inSection: 0)) as! HYUserInfoListTopTableViewCell;
//        
//        cell.headerImgV.image = image;
        
        
        picker.dismiss(animated: true, completion: {
            () -> Void in
            
          //  print("开始上传头像");
        });
        

    }
    
    //MARK:HYVideoShareUploadVideoTableViewCellDelegate
    func hyVideoShareUploadVideoTableViewCellAddMusicBtnClicked(cell ce: HYVideoShareUploadVideoTableViewCell, sectionIndex section: Int) {
        
        print("\(ce)");
        
        ce.player.pause();
        
        log.ln("打开添加音乐")/;
        let vc:HYSelecteVideoMusicListViewController = HYSelecteVideoMusicListViewController(nibName: "HYSelecteVideoMusicListViewController", bundle: Bundle.main);
        vc.selectCallBack = {
            (data:HYMusicListDataModel) -> Void in
            log.ln("开始合并音乐")/;
            
//            if(true == self.isVideoComposeSuccess){
//                ce.player.prepareToDealloc();
//                ce.player.reNewPlayer();
//            }
            
            if let url = data.url{
                let strUrl:String = HYMusicFileManager.sharedInstance.getLocalFilePath(musicUrl: url);
                log.ln("music file path = \(strUrl)")/;
                
                self.isVideoComposing = true;
                
                //合并音视频
                MediaTools.sharedInstance.videoComposeVideoAudio(video: self.strCurUploadingFilePath, audio: strUrl, callBack: {
                    (url:URL) -> Void in
                    
                    self.isVideoComposeSuccess = true;
                    self.strCurComposeFilePath = url.path;
                    log.ln("合成后的视频 = \(url)")/;
                    
                    DispatchQueue.main.async(execute: {
                        () -> Void in
                        
                        SVProgressHUD.dismiss();
                        ce.setVideoData(videoUrl: self.strCurComposeFilePath,videoImg:self.videoImg);
                        ce.setUIState(state: 3);
                        
                        self.isVideoComposing = false;
                        
                        //self.tableView.reloadSections(IndexSet(integer: 0), with: .fade);
                        //ce.delegate = nil;
                        
                        
                        
                        //self.tableView.reloadRows(at: [IndexPath(row: 0, section: 0)], with: .fade);
                        
                        
//                        //到这里就成功了 播放合成后的视频
//                        let cell:HYVideoShareUploadVideoTableViewCell = self.tableView.cellForRow(at: IndexPath(row: 0, section: 0)) as! HYVideoShareUploadVideoTableViewCell;
//                        
//                        cell.player.prepareToDealloc();
//                        cell.player.playWithURL(url);
//                        cell.player.play();
                        
                    });

                });
 
            }
            
        };
        self.present(vc, animated: true, completion: {
            () -> Void in
            
        });
    }
    
    func hyVideoShareUploadVideoTableViewCellStopBtnClicked(cell ce: HYVideoShareUploadVideoTableViewCell, sectionIndex section: Int) {
        CommonTools.showModalMessage("确定停止上传吗？", title: "提示", vc: self, hasCancelBtn: true, hr: {
            (beOK:Bool) -> Void in
            if(true == beOK){
                HYUploadVideoManager.shared().stopUpload();
                HYUploadVideoManager.shared().deleteFile(self.strCurUploadingFilePath);
                //进度清0
                
            }
            
        });
    }
    
    //MARK:HYVideoSharePriceTableViewCellDelegate
    func hyVideoSharePriceTableViewCellDelegateSwitchValueChanged(cell ce: HYVideoSharePriceTableViewCell, inexPath index: IndexPath, value be: Bool) {
        print("value change to \(be)");
        self.beShowVideoPrice = be;
        
        let dic:[String:AnyObject] = ["title":"视频费用" as AnyObject,"type":0 as AnyObject,"boolValue":be as AnyObject,"value":"0" as AnyObject];
        let d:[String:AnyObject] = ["title":"1元" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"0" as AnyObject];
        
        
        self.menuDataArray[0] = dic;
        self.menuDataArray[1] = d;
        
        self.tableView.reloadSections(IndexSet(integer: 2), with: .fade);
    }
    
    func hyVideoSharePriceTableViewCellDelegateTextFieldEdited(cell ce: HYVideoSharePriceTableViewCell, inexPath index: IndexPath, editText str: String) {
        print("text change to \(str)");
        
        var strValue = str;
        
        if("" == strValue){
            strValue = "0";
        }
        
        let dic:[String:AnyObject] = ["title":"自定义价格" as AnyObject,"type":2 as AnyObject,"boolValue":false as AnyObject,"value":strValue as AnyObject]
        self.menuDataArray[3] = dic;
    }
    
    func hyVideoSharePriceTableViewCellDelegateTextFieldDidBeginEdit(cell ce: HYVideoSharePriceTableViewCell, inexPath index: IndexPath) {
        print("开始编辑自定义价格");
        let d:[String:AnyObject] = ["title":"1元" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"0" as AnyObject];
        let d1:[String:AnyObject] = ["title":"10元" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"0" as AnyObject];
        
        self.menuDataArray[1] = d;
        self.menuDataArray[2] = d1;
        
        self.tableView.reloadRows(at: [IndexPath(row: 1, section: 2),IndexPath(row: 2, section: 2)], with: .fade);
    }
    
    func hyVideoSharePriceTableViewCellDelegateRadioBtnClicked(cell ce: HYVideoSharePriceTableViewCell, inexPath index: IndexPath) {
        if(1 == (index as NSIndexPath).row){
            self.price1Selected();
        }else if(2 == (index as NSIndexPath).row){
            self.price2Selected();
        }
    }
    
    //MARK:HYCustomPickerDelegate
    
    func hyCustomPicker(_ pickerView: HYCustomPicker, titleForRow row: Int, forComponent component: Int) -> String?{
        if(0 == component){
            
            let da:HYCategoryTreeDataModel = self.categoryDataArray[row];
            if let name = da.name{
                return name;
            }
            return "";
        }else{
            let da:HYCategoryTreeDataModel = self.categoryDataArray[self.curSelectedLeftIndex];
            if let arr:[HYCategoryTreeDataModel] = da.children{
                let subData:HYCategoryTreeDataModel = arr[row];
                if let name = subData.name{
                    return name;
                }
            }
        }
        return "";
    }
    
    func hyCustomPicker(_ pickerView: HYCustomPicker, didSelectRow row: Int, inComponent component: Int){
        print("didSelectRow \(row)  component \(component)");
        if(0 == component){
            //let da:HYCategoryTreeDataModel = self.categoryDataArray[row];
            self.curSelectedLeftIndex = row;
            self.curSelectedRightIndex = 0;
//            pickerView.selectRow(0, inComponent: 1, animated: true)
//           // pickerView.reloadComponent(1)
            
            self.rightCategoryDataArray = self.leftCategoryDataArray[row].children;
            
            pickerView.reloadComponent(1);
            pickerView.selectRow(0, inComponent: 1, animated: true)
            //pickerView.reloadAllComponents();
            
            
        }else{
            self.curSelectedRightIndex = row;
        }
    }
    
    //MARK:HYCustomPickerDataSource
    func numberOfComponentsInHYCustomPickerView(_ pickerView: HYCustomPicker) -> Int{
        return 2;
    }
    
    func hyCustomPicker(_ pickerView: HYCustomPicker, numberOfRowsInComponent component: Int) -> Int{
        if(0 == component){
            return self.leftCategoryDataArray.count;
        }else{
            return self.rightCategoryDataArray.count;
        }
    }
    
//    func hyCustomPicker(pickerView: HYCustomPicker, numberOfRowsInComponent component: Int) -> Int{
//        if(0 == component){
//            return categoryDataArray.count;
//        }else{
//            let da:HYCategoryTreeDataModel = self.categoryDataArray[self.curSelectedLeftIndex];
//            if let arr:[HYCategoryTreeDataModel] = da.children{
//                return arr.count;
//            }
//        }
//        return 0;
//    }


}

