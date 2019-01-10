//
//  HYEditChannelViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/10.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import Alamofire

typealias editChannelCallBack = (_ beOK:Bool)->Void;

class HYEditChannelViewController: HYBaseViewController,UINavigationControllerDelegate,UIImagePickerControllerDelegate, HYLimitTextDelegate {
    
    @IBOutlet weak var editImgView: UIView!
    @IBOutlet weak var editNameView: UIView!
    @IBOutlet weak var editMiaoshuView: UIView!
    
    @IBOutlet weak var channelImgView: UIImageView!
    @IBOutlet weak var tfChannelName: UITextField!
    @IBOutlet weak var textViewMiaoshu: PlaceholderTextView!
    
    var beImgIsUploding:Bool = false;
    
    var channelInfoDataModel:HYChannelInfoDataModel!
    /// 0  新建   1  编辑
    var nEditType:Int = 0;  
    
    var fileLinkID:String! = "";
    var filtUrl:String! = "";
    var callBackFun:editChannelCallBack!
    var limit: HYLimitText = HYLimitText()

    
    func setChannelInfoData(_ data:HYChannelInfoDataModel){
        self.channelInfoDataModel = data;
    }

    override func viewDidLoad() {
        stringNavBarTitle = "编辑频道";
        super.viewDidLoad()
        
        let btnSave:UIButton = UIButton(type: UIButtonType.custom);
        btnSave.frame = CGRect.zero;
        btnSave.setTitle("确认", for: UIControlState());
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

        // Do any additional setup after loading the view.
        
//        editImgView.layer.shadowColor = UIColor.black.cgColor;
//        editImgView.layer.shadowOffset = CGSize(width: 2, height: 2);
//        editImgView.layer.shadowOpacity = 0.4;
//        editImgView.layer.shadowRadius = 3;
//        editImgView.layer.cornerRadius = 3;
//        
//        editNameView.layer.shadowColor = UIColor.black.cgColor;
//        editNameView.layer.shadowOffset = CGSize(width: 2, height: 2);
//        editNameView.layer.shadowOpacity = 0.4;
//        editNameView.layer.shadowRadius = 3;
//        editNameView.layer.cornerRadius = 3;
//        
//        editMiaoshuView.layer.shadowColor = UIColor.black.cgColor;
//        editMiaoshuView.layer.shadowOffset = CGSize(width: 2, height: 2);
//        editMiaoshuView.layer.shadowOpacity = 0.4;
//        editMiaoshuView.layer.shadowRadius = 3;
//        editMiaoshuView.layer.cornerRadius = 3;
        
        self.tfChannelName.placeholder = "请输入频道名称"
        if(1 == nEditType){
            if let name:String = self.channelInfoDataModel.name{
                self.tfChannelName.text = name;
            }
            if let des:String = self.channelInfoDataModel.Description{
                self.textViewMiaoshu.text = des;
            }
            if let url:String = self.channelInfoDataModel.thumbnail{
                self.channelImgView.sd_setImage(with: URL(string: url),placeholderImage: UIImage(named: "channelDefault"));
            }
        }
        
        self.textViewMiaoshu.placeholder = "请输入频道描述"
        limit.delegate = self
        limit.maxLength = 30 //TODO : 字数限制
        limit.addNotTextView(textViewMiaoshu)
    }
    
    @IBAction func btnEditPicClicked(_ sender: UIButton) {
        setHeader();
    }
    func btnSaveClicked(_ sender: UIButton) -> Void {
        print("btn btnSaveClicked clicked");
        
        self.tfChannelName.resignFirstResponder();
        self.textViewMiaoshu.resignFirstResponder();
        
        if (true == self.beImgIsUploding){
            CommonTools.showMessage("图片正在上传中!", vc: self, hr: {
                () -> Void in
            });
            return;
        }
        
        //let id:Int = Int(self.fileLinkID)!;
        
        if(0 == nEditType){
            
            guard self.tfChannelName.text != "" else{
                CommonTools.showMessage("频道名称不能为空!", vc: self,  hr: {
                    ()->Void in
                    self.tfChannelName.becomeFirstResponder();
                });
                return;
            }
            
            guard self.textViewMiaoshu.text != "" else{
                CommonTools.showMessage("频道描述不能为空!",  vc: self,  hr: {
                    ()->Void in
                    self.textViewMiaoshu.becomeFirstResponder();
                });
                return;
            }
            
//            guard self.fileLinkID != "" else{
//                CommonTools.showMessage("请为频道选择一张图片!", vc: self,  hr: {
//                    ()->Void in
//                });
//                return;
//            }
            
            
            let dic:[String: AnyObject] = ["name":self.tfChannelName.text! as AnyObject,"description":self.textViewMiaoshu.text! as AnyObject,"cover_image_link_id":self.fileLinkID as AnyObject,
                                           "tags":[] as AnyObject];
            
            AskDogCreateChannelListDataRequest().startRequest(requestParmer: dic, viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    print("AskDogCreateChannelListDataRequest 频道创建成功");
                    self.callBackFun(true);
                    CommonTools.showMessage("频道创建成功!", vc: self, hr: {
                        () -> Void in
                        _ = self.navigationController?.popViewController(animated: true);
                    });
                    
                }
            });
        }else{
            if let id:String = self.channelInfoDataModel.id{
                let strExUrl:String = "/\(id)";
                
                var dic:[String: AnyObject]!;
                if("" == self.fileLinkID){
                    dic = ["name":self.tfChannelName.text! as AnyObject,"description":self.textViewMiaoshu.text! as AnyObject];
                }else{
                    dic = ["name":self.tfChannelName.text! as AnyObject,"description":self.textViewMiaoshu.text! as AnyObject,"cover_image_link_id":self.fileLinkID as AnyObject];
                }

                
                AskDogEditChannelInfoDataRequest().startRequest(requestParmer: dic,exUrl:strExUrl, viewController:self,completionHandler: {
                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                    if(true == beOk){
                        print("AskDogCreateChannelListDataRequest 频道修改成功");
                        self.callBackFun(true);
                        CommonTools.showMessage("频道修改成功!", vc: self, hr: {
                            () -> Void in
                            _ = self.navigationController?.popViewController(animated: true);
                        });
                    }
                });
            }

        }
        
    }
    
    
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
        
        self.channelImgView.image = image;
        
        //CommonTools.saveImage(image, newSize: CGSize(width: 500,height: 500), percent: 0.5, imageName: "uploadChannelImage.png");
        
        //self.saveImage(image, newSize: CGSize(width: 256,height: 256), percent: 0.5, imageName: "currentImage.png");
        
        picker.dismiss(animated: true, completion: {
            () -> Void in
        });
        
        //开始上传图片
        self.beImgIsUploding = true;
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
        
        let dic:[String: AnyObject]? = ["extention":"png" as AnyObject,"type":"CHANNEL_THUMBNAIL" as AnyObject];
        
        AskDogGetAccessTokenDataRequest().startRequest(requestParmer: dic,  viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    let data:HYAccessTokenDataModel = HYAccessTokenDataModel(jsonDic: dic!);
                    
                    let dataUpload = UIImageJPEGRepresentation(img, 0.5);
 
                    let dicP:NSDictionary = ["policy":data.policy!,"signature":data.signature!,"callback":data.callback!,
                    "OSSAccessKeyId":data.OSSAccessKeyId!,"key":data.key!];
                    
                    AskDogUploadImageDataRequest().uploadFile(uploadUrl: data.host!, fileData: dataUpload!, parma: dicP,viewController: self, completionHandler: {
                        (be:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?) -> Void in
                        
                        if let resultDic:NSDictionary = dic{
                            
                            CommonTools.stringCheckNullObject(resultDic["linkId"], callBack: {
                                (str:String) -> Void in
                                self.fileLinkID = str;
                            });
                            CommonTools.stringCheckNullObject(resultDic["url"], callBack: {
                                (str:String) -> Void in
                                self.filtUrl = str;
                            });
                            
                            CommonTools.showMessage("图片上传成功!", vc: self, hr: {
                                () -> Void in
                                self.beImgIsUploding = false;
                            });

                            
                        }
                        
                    });
                }
            }
        });
    }
    
    
    // MARK: - limitTextDelegate
    func limitTextLimitInputOverStop(_ textLimitInput: HYLimitText!) {
        //超出字数
        print("频道描述超出字数")
    }
    
    func limitTextLimitInput(_ textLimitInput: HYLimitText!, text: String!) {
        //输入内容
        print("频道描述输入内容 \(text)")
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
