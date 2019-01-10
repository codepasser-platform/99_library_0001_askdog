//
//  HYEditUserInfoViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/9.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit


class HYEditUserInfoViewController: HYBaseViewController,UITableViewDelegate,UITableViewDataSource,UINavigationControllerDelegate,UIImagePickerControllerDelegate {
    
    
    @IBOutlet weak var tableView: UITableView!
    
    var beUploadingImg:Bool = false;
    var signature  = ""
    
    override func viewDidLoad() {
        stringNavBarTitle = "个人信息";
        super.viewDidLoad()
        
        let btnSave:UIButton = UIButton(type: UIButtonType.custom);
        btnSave.frame = CGRect.zero;
        btnSave.setTitle("保存", for: UIControlState());
        btnSave.titleLabel?.textColor = Color_WhiteTitle;
        btnSave.titleLabel?.font = UIFont.systemFont(ofSize: 13);
        btnSave.titleEdgeInsets = UIEdgeInsetsMake(3, 0, -3, 0);
        btnSave.addTarget(self, action: #selector(btnSaveClicked), for: UIControlEvents.touchUpInside);
        btnSave.backgroundColor = UIColor.clear;
        btnSave.translatesAutoresizingMaskIntoConstraints = false;
        navBarView.addSubview(btnSave);
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["btnSave"] = btnSave;

        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnSave(50)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-20-[btnSave]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        let cellID1:String = "cell1";
        let nib1:UINib = UINib(nibName: "HYUserInfoListTopTableViewCell", bundle: nil);
        tableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        let cellID2:String = "cell2";
        let nib2:UINib = UINib(nibName: "HYUserInfoEditListTableViewCell", bundle: nil);
        tableView.register(nib2, forCellReuseIdentifier: cellID2);
        
        if tableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tableView.separatorInset = UIEdgeInsets.zero;
        }
        if tableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tableView.layoutMargins = UIEdgeInsets.zero;
        }

        // Do any additional setup after loading the view.
    }
    
    func btnSaveClicked(_ sender: UIButton) -> Void {
        print("btn btnSaveClicked clicked");
        
        if(true == self.beUploadingImg){
            CommonTools.showMessage("头像正在上传中!", vc: self, hr: {
                () -> Void in
            });
            return;
        }
        
        var cell:HYUserInfoEditListTableViewCell!
        
        //姓名
        cell = tableView.cellForRow(at: IndexPath(row: 0, section: 1)) as! HYUserInfoEditListTableViewCell;
        let name = cell.lblEditInput.text;
        guard name != "" else{
            CommonTools.showMessage("姓名不能为空!", vc: self, hr: {
                ()-> Void in
                
            });
            return;
        }
        
        //性别
        cell = tableView.cellForRow(at: IndexPath(row: 1, section: 1)) as! HYUserInfoEditListTableViewCell;
        let sex = cell.lblEditContent.text;
        
        guard sex != "" else{
            CommonTools.showMessage("性别不能为空!", vc: self, hr: {
                ()-> Void in
                
            });
            return;
        }
        
        var strSex = "";
        if("男" == sex){
            strSex = "MALE";
        }else{
            strSex = "FEMALE";
        }
        
        //手机号
        cell = tableView.cellForRow(at: IndexPath(row: 2, section: 1)) as! HYUserInfoEditListTableViewCell;
        let tel = cell.lblEditInput.text;
        guard tel != "" else{
            CommonTools.showMessage("手机号不能为空!", vc: self, hr: {
                ()-> Void in
                
            });
            return;
        }
        
        if(!CommonTools.PhoneNumberIsValidated(tel!)){
            
            CommonTools.showMessage("手机号格式错误!", vc: self, hr: {
                () -> Void in
                
            });
            return;
        }
        
        //职业
        cell = tableView.cellForRow(at: IndexPath(row: 3, section: 1)) as! HYUserInfoEditListTableViewCell;
        let job = cell.lblEditInput.text;

        
        //学校
        cell = tableView.cellForRow(at: IndexPath(row: 4, section: 1)) as! HYUserInfoEditListTableViewCell;
        let school = cell.lblEditInput.text;
        
        //专业
        cell = tableView.cellForRow(at: IndexPath(row: 5, section: 1)) as! HYUserInfoEditListTableViewCell;
        let zhuanye = cell.lblEditInput.text;
        
        
        //所在地区
        cell = tableView.cellForRow(at: IndexPath(row: 6, section: 1)) as! HYUserInfoEditListTableViewCell;
        let area = cell.lblEditContent.text;
        var city:String!
        var province:String!
        guard area != "" else{
            CommonTools.showMessage("所在地区不能为空!", vc: self, hr: {
                ()-> Void in
                
            });
            return;
        }
        let areaArray = area?.components(separatedBy: " ");
        if(2 == areaArray?.count){
            province = areaArray![0];
            city = areaArray![1];
        }
        let areaDic:[String:String] = ["province":province,"city":city];
        
        //签名
//        cell = tableView.cellForRowAtIndexPath(NSIndexPath(forRow: 0, inSection: 2)) as! HYUserInfoEditListTableViewCell;
//        let sign = cell.lblPushTipText.text;
        
        
        
        let dic:[String: AnyObject]? = ["name":name! as AnyObject,
                                        "phone":tel! as AnyObject,
                                        "gender":strSex as AnyObject,
                                        "address":areaDic as AnyObject,
                                        "occupation":job! as AnyObject,
                                        "major":zhuanye! as AnyObject,
                                        "school":school! as AnyObject,
                                        "signature":self.signature as AnyObject];
        
        
        
        AskDogModifyUserInfoDataRequest().startRequest(requestParmer: dic, viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                CommonTools.showMessage("用户信息修改成功!", vc: self, hr: {
                    ()->Void in
                    //self.navigationController?.popViewController(animated: true);
                    _ = self.navigationController?.popViewController(animated: true);
                });
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
    
    //MARK:UITableViewDelegate
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        if(1 == (indexPath as NSIndexPath).section){
            let cell:HYUserInfoEditListTableViewCell = tableView.cellForRow(at: indexPath) as! HYUserInfoEditListTableViewCell;
            
            
            switch (indexPath as NSIndexPath).row {
            case 1:

                let dataSource:[String] = ["男","女"];
                let alonePicker: LWPickerView = LWPickerView(aDataSource: dataSource, aTitle: "请选择性别");
                alonePicker.show()
                //alonePicker.showSelectedRow(3, animated: true)
                alonePicker.didClickDoneForTypeAloneHandler { (selectedRow, result) in
                    print("selectedRow:\(selectedRow)")
                    print("result:\(result)")
                    
                    cell.setContentValue(result as AnyObject!);
                }
                alonePicker.didClickCancelHandler {
                    print("dismiss")
                }
                
            case 6:
                
                let areaPicker = LWPickerView(anAreaType: .provinceCity, aTitle: "请选择城市")
                areaPicker.show()
                areaPicker.didClickDoneForTypeAreaHandler { (province, city, district) in
                    print("province:\(province)")
                    print("city:\(city)")
                    print("district:\(district)")
                    
                    let dic:NSDictionary = ["province":province!,"city":city!];
                    cell.setContentValue(dic);

                }
                areaPicker.didClickCancelHandler {
                    print("dismiss")
                }
//            case 1:
//                print("default inin");
//            case 2:
//                print("default inin");
//            case 3:
//                print("default inin");
//            case 4:
//                print("default inin");
            default:
                print("default inin");
            }
        }else if(2 == (indexPath as NSIndexPath).section){
            let cell:HYUserInfoEditListTableViewCell = tableView.cellForRow(at: indexPath) as! HYUserInfoEditListTableViewCell;
            
            var sign:String = "";
            if let obj = Global.sharedInstance.userInfo?.signature{
                sign = obj;
                self.signature = obj;
            }
            let vc:HYInputViewController = HYInputViewController(nibName: "HYInputViewController", bundle: Bundle.main, title:"个性签名",content: sign,callBack: {
                (beSave:Bool,strContent:String) -> Void in
                if(true == beSave){
                    print("return value beSave:\(beSave) content:\(strContent)");
                    if 0 == strContent.characters.count {
                        cell.lblPushTipText.text = "未填写";
                    } else {
                        cell.lblPushTipText.text = strContent;
                    }
                    self.signature = strContent;
                }


            });
            
            self.navigationController?.pushViewController(vc, animated: true);
        }else{
            setHeader();
        }
        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        if(0 == (indexPath as NSIndexPath).section){
            return 70;
        }else if(1 == (indexPath as NSIndexPath).section){
            return 40;
        }else if(2 == (indexPath as NSIndexPath).section){
            return 40;
        }
        
        return 0.0;
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if(0 == section){
            return 0;
        }else if(1 == section){
            return 5;
        }else{
            return 5;
        }

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
    
    //MARK:UITableViewDataSource
    //    func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
    //        return true;
    //    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 3;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(0 == section){
            return 1;
        }else if(1 == section){
            return 7;
        }else if(2 == section){
            return 1;
        }
        return 0;
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        let headerV:UIView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.size.width, height: 5));
        //headerV.backgroundColor = CommonTools.rgbColor(red: 243, green: 244, blue: 249);
        headerV.backgroundColor = UIColor.clear;
        return headerV;
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        var cell:UITableViewCell!
        var cellID:String! = "";
        
        if(0 == (indexPath as NSIndexPath).section){
            cellID = "cell1";
            cell = tableView.dequeueReusableCell(withIdentifier: cellID);
            let temp:HYUserInfoListTopTableViewCell = cell as! HYUserInfoListTopTableViewCell;
            temp.setData();
            
        }else if(1 == (indexPath as NSIndexPath).section){
            cellID = "cell2";
            cell = tableView.dequeueReusableCell(withIdentifier: cellID);
            let temp:HYUserInfoEditListTableViewCell = cell as! HYUserInfoEditListTableViewCell;
            
            if(0 == (indexPath as NSIndexPath).row){
                temp.cornerType = CornerMaskType.cornerMaskType_Top;
                temp.setCellEditType(UserInfoCellEditType.userInfoCellEditType_Input)
            }else if(6 == (indexPath as NSIndexPath).row){
                temp.cornerType = CornerMaskType.cornerMaskType_Bottom;
                temp.setCellEditType(UserInfoCellEditType.userInfoCellEditType_Area)
            }else if(1 == (indexPath as NSIndexPath).row){
                temp.setCellEditType(UserInfoCellEditType.userInfoCellEditType_Picker)
            }else if(2 == (indexPath as NSIndexPath).row){
                temp.setCellEditType(UserInfoCellEditType.userInfoCellEditType_Tel)
            }else if(3 == (indexPath as NSIndexPath).row){
                temp.setCellEditType(UserInfoCellEditType.userInfoCellEditType_Input)
            }else if(4 == (indexPath as NSIndexPath).row){
                temp.setCellEditType(UserInfoCellEditType.userInfoCellEditType_Input)
            }else if(5 == (indexPath as NSIndexPath).row){
                temp.setCellEditType(UserInfoCellEditType.userInfoCellEditType_Input)
            }

            temp.setCellIndexValue((indexPath as NSIndexPath).row,section: (indexPath as NSIndexPath).section);

        }else{
            
            cellID = "cell2";
            cell = tableView.dequeueReusableCell(withIdentifier: cellID);
            let temp:HYUserInfoEditListTableViewCell = cell as! HYUserInfoEditListTableViewCell;
            temp.setCellEditType(UserInfoCellEditType.userInfoCellEditType_Push);
            
            temp.setCellIndexValue((indexPath as NSIndexPath).row,section: (indexPath as NSIndexPath).section);
            
            temp.cornerType = CornerMaskType.cornerMaskType_All;
            
            if let obj = Global.sharedInstance.userInfo?.signature{
                self.signature = obj;
            }
        }
        
        return cell;
    }
    
    func setHeader(){
        let canCamera:Bool = UIImagePickerController.isSourceTypeAvailable(.camera);
        let canAlbum:Bool = UIImagePickerController.isSourceTypeAvailable(.photoLibrary);
        
        if(false == canAlbum && false == canCamera){
            return;
        }
        
        let actionSheet:UIAlertController = UIAlertController(title: "设置头像", message: "", preferredStyle: UIAlertControllerStyle.actionSheet);
        
        if(canCamera){
            let cameraAction:UIAlertAction = UIAlertAction(title: "拍照", style: UIAlertActionStyle.destructive, handler: {
                (action:UIAlertAction) -> Void in
                print("camera in");
                
                let pc:UIImagePickerController = UIImagePickerController();
                pc.sourceType = UIImagePickerControllerSourceType.camera;
                pc.delegate = self;
                pc.allowsEditing = true;
                pc.cameraDevice = UIImagePickerControllerCameraDevice.rear;
                pc.cameraFlashMode = UIImagePickerControllerCameraFlashMode.off;
                pc.modalTransitionStyle = UIModalTransitionStyle.coverVertical;
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
        
        var image:UIImage!
        if(picker.allowsEditing){
            image = info[UIImagePickerControllerEditedImage] as! UIImage;
        }else{
            image = info[UIImagePickerControllerOriginalImage] as! UIImage;
        }
        
        let cell:HYUserInfoListTopTableViewCell = tableView.cellForRow(at: IndexPath(row: 0, section: 0)) as! HYUserInfoListTopTableViewCell;
        
        cell.headerImgV.image = image;
        
        //self.saveImage(image, newSize: CGSize(width: 256,height: 256), percent: 0.5, imageName: "currentImage.png");
        
        picker.dismiss(animated: true, completion: {
            () -> Void in
            
            print("开始上传头像");
        });
        
        self.beUploadingImg = true;
        self.uploadImage(image);
        
        //        if(picker.sourceType == UIImagePickerControllerSourceType.Camera){
        //            if(picker.allowsEditing){
        //                image = info[UIImagePickerControllerEditedImage] as! UIImage;
        //            }
        //        }else{
        //            let image = info[UIImagePickerControllerOriginalImage] as! UIImage;
        //            headerImgV.image = image;
        //            picker.dismissViewControllerAnimated(true, completion: {
        //                () -> Void in
        //            })
        //        }
    }
    
    func uploadImage(_ img:UIImage) -> Void {
        
        let dic:[String: AnyObject]? = ["extention":"png" as AnyObject,"type":"USER_AVATAR" as AnyObject];
        var fileLinkID:String = "";
        var fileUrl:String = "";
        
        AskDogGetAccessTokenDataRequest().startRequest(requestParmer: dic,  viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    let data:HYAccessTokenDataModel = HYAccessTokenDataModel(jsonDic: dic!);
                    
                    let dataUpload = UIImageJPEGRepresentation(img, 0.5);
                    
                    let dicP:NSDictionary = ["policy":data.policy!,"signature":data.signature!,"callback":data.callback!,
                        "OSSAccessKeyId":data.OSSAccessKeyId!,"key":data.key!];
                    
                    AskDogUploadImageDataRequest().uploadFile(uploadUrl: data.host!, fileData: dataUpload!, parma: dicP, viewController:self,completionHandler: {
                        (be:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?) -> Void in
                        
                        if let resultDic:NSDictionary = dic{
                            
                            CommonTools.stringCheckNullObject(resultDic["linkId"], callBack: {
                                (str:String) -> Void in
                                fileLinkID = str;
                            });
                            CommonTools.stringCheckNullObject(resultDic["url"], callBack: {
                                (str:String) -> Void in
                                fileUrl = str;
                            });
                            //修改用户头像
                            let strParmr:String = "?linkId=" + fileLinkID;
                            print("share id = \(strParmr)");
                            
                           // let dic:[String: AnyObject]? = ["linkId":fileLinkID];

                            
                            AskDogModifyUserHeaderImgDataRequest().startRequest(exUrl: strParmr, viewController:self,completionHandler: {
                                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                                if(true == beOk){
                                    print("用户头像修改成功!");
                                    //更新头像
                                    Global.sharedInstance.userInfo?.avatar = fileUrl;
                                    //刷新个人信息
                                    self.tableView.beginUpdates();
                                    let indexs:[IndexPath] = [IndexPath(row: 0, section: 0)];
                                    self.tableView.reloadRows(at: indexs, with: .none);
                                    self.tableView.endUpdates();
                                    self.beUploadingImg = false;
                                }
                            });
                            
                            
                        }
                        
                    });
                }
            }
        });
    }

}
