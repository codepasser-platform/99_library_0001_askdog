//
//  HYTuwenEditView.swift
//  AskDog
//
//  Created by Symond on 16/8/19.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYTuwenEditView: UIView,UITextViewDelegate,UINavigationControllerDelegate,UIImagePickerControllerDelegate {

    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    
    var textViewInput:UITextView!
    weak var parentVc:UIViewController!
    //var imgDicArrar:[[String:AnyObject]] = [[String:AnyObject]]();
    
    var imgDic:[Int:[String:AnyObject]] = [:];
    
    
    
    func getContent() -> (content:String,linkIds:[String]) {
        
        let att:NSMutableAttributedString = self.textViewInput.textStorage as NSMutableAttributedString;
        let newString:String = self.convertAttributStringToString(att);
        
        var arr:[String] = [String]();
        
        for value in self.imgDic.values{
            let dic:[String:AnyObject] = value as [String:AnyObject];
            let strId:String = dic["linkId"] as! String;
            arr.append(strId);
        }
        return (newString,arr);
    }
    
    
    func setupView() -> Void {
        
        
        self.translatesAutoresizingMaskIntoConstraints = false;
        
        //添加一个uitextview
        var views:[String:AnyObject] = [String:AnyObject]();
        
        //添加输入框
        textViewInput = UITextView(frame: CGRect.zero);
        textViewInput.translatesAutoresizingMaskIntoConstraints = false;
        self.addSubview(textViewInput);
        self.textViewInput.layoutManager.allowsNonContiguousLayout = false;
        
        
        views["textViewInput"] = textViewInput;
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[textViewInput]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[textViewInput]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        textViewInput.delegate = self;
        
        //给键盘加一个工具条
        self.addTooBar();
        
       // self.textViewInput.becomeFirstResponder();
        
    }
    
//    override init(frame: CGRect) {
//        super.init(frame: frame);
//
//        self.setupView();
//    }
    
    required init?(coder aDecoder: NSCoder) {
       // fatalError("init(coder:) has not been implemented")
        super.init(coder: aDecoder);
        self.setupView();
    }
    
    func addTooBar() -> Void {
        let fr:CGRect = CGRect(x: 0, y: 0, width: UIScreen.main.bounds.size.width, height: 44);
        let toobarView:UIView = UIView(frame: fr);
        toobarView.translatesAutoresizingMaskIntoConstraints = false;
        toobarView.backgroundColor = UIColor.white;
        self.textViewInput.inputAccessoryView = toobarView;
        
        var views:[String:AnyObject] = [String:AnyObject]();
        
        let btnLeft:UIButton = UIButton(type: UIButtonType.custom);
        btnLeft.translatesAutoresizingMaskIntoConstraints = false;
        btnLeft.frame = CGRect.zero;
        btnLeft.imageEdgeInsets = UIEdgeInsetsMake(13,14,11,14);
        btnLeft.addTarget(self, action: #selector(btnLeftClicked), for: UIControlEvents.touchUpInside);
        //btnLeft.backgroundColor = UIColor.yellowColor();
        btnLeft.translatesAutoresizingMaskIntoConstraints = false;
        btnLeft.setImage(UIImage(named: "photograph"), for: UIControlState());
        toobarView.addSubview(btnLeft);

        views["btnLeft"] = btnLeft;
        toobarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[btnLeft(50)]", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        toobarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-[btnLeft(44)]", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        let btnRight:UIButton = UIButton(type: UIButtonType.custom);
        btnRight.translatesAutoresizingMaskIntoConstraints = false;
        btnRight.frame = CGRect.zero;
        btnRight.imageEdgeInsets = UIEdgeInsetsMake(13,14,11,14);
        btnRight.addTarget(self, action: #selector(btnRightClicked), for: UIControlEvents.touchUpInside);
        //btnRight.backgroundColor = UIColor.yellowColor();
        btnRight.translatesAutoresizingMaskIntoConstraints = false;
        btnRight.setImage(UIImage(named: "keyboard"), for: UIControlState());
        toobarView.addSubview(btnRight);
        
        views["btnRight"] = btnRight;
        toobarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnRight(50)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        toobarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-[btnRight(44)]", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
    }
    
    func btnLeftClicked() -> Void{
        //打开相册
        if let v:UIViewController = self.parentVc{
            self.getPictrue(v);
        }
    }
    
    func stopEditing() -> Void {
        self.textViewInput.resignFirstResponder();
        
    }
    
    func startEditing() -> Void {
        self.textViewInput.becomeFirstResponder();
        
    }
    
    func convertAttributStringToString(_ attStr:NSMutableAttributedString) -> String {
        print("old NSMutableAttributedString = \(attStr.string)");
        
        let att:NSMutableAttributedString = NSMutableAttributedString(attributedString: attStr);
        att.beginEditing();
        var found = false;
        
        att.enumerateAttributes(in: NSMakeRange(0, att.length), options: .reverse, using: {
            (attrs,range,stop) -> Void in
            
            let obj = attrs["NSAttachment"];
            
            if (obj != nil){

                //print("range = \(range)");
                //先取出URl
                if let dic:[String:AnyObject] = self.imgDic[range.location]{
                    let url:String = dic["url"] as! String;
                    //print("textString old  = \(att.string) \r\n replace str = \(url) length = \(url.characters.count)");
                    att.replaceCharacters(in: NSMakeRange(range.location, range.length), with: url);
                    //print("textString new = \(att.string)");
                }
 
            }

            found = true;

        });
        
        
        if(false == found){
        
        
        }
        
        att.endEditing();
        
        
        print("new NSMutableAttributedString = \(att.string)");
        
        return att.string;
    }
    
    func btnRightClicked() -> Void{
        self.textViewInput.resignFirstResponder();
        

        
        
        
    }
    
//        //(NSDictionary *attrs, NSRange range, BOOL *stop)
////        att.enumerateAttributesInRange(NSMakeRange(0, att.length), options: .Reverse, usingBlock:(attrs:NSDictionary,range:NSRange, stop:UnsafeMutablePointer<ObjCBool>) -> Void in {
////            
////            
////        });
//        
//        let att1:NSMutableAttributedString = NSMutableAttributedString(attributedString: att);
//        
//        let str:String = att.string;
//        
//        let textString:NSMutableString = NSMutableString(string: str);
//        
//        print("textString = \(textString)");
//        
//        att.beginEditing();
//        
//       // var base = 0;
//        
//        var found = false;
//        
//        att.enumerateAttributesInRange(NSMakeRange(0, att.length), options: .Reverse, usingBlock: {
//            (attrs,range,stop) -> Void in
//            
//            if (["NSAttachment"]) != nil{
//                //let textAtt:NSTextAttachment = textObj as! NSTextAttachment;
//                //let img:UIImage = textAtt.image!;
//                //print("img size = \(img.size.width) \(img.size.height)");
//                
//                print("range = \(range)");
//                //先取出URl
//                if let dic:[String:AnyObject] = self.imgDic[range.location]{
//                    let url:String = dic["url"] as! String;
//                    
//                    print("textString old  = \(att.string) \r\n replace str = \(url) length = \(url.characters.count)");
//                    
//                    att.replaceCharactersInRange(NSMakeRange(range.location, range.length), withString: url);
//                    //base += url.characters.count - 1;
//                    
//                    print("textString new = \(att.string)");
//                }
//                
//                
//            }
//            
//            
//            found = true;
//            
//            
//        });
//        
//        
//        
////        att.enumerateAttribute(NSFontAttributeName, inRange: NSMakeRange(0, att.length), options: .Reverse) { (value, range, stop) -> Void in
////            
////                
////                
////            
////            
////    
////            
////            if (["NSAttachment"]) != nil{
////                //let textAtt:NSTextAttachment = textObj as! NSTextAttachment;
////                //let img:UIImage = textAtt.image!;
////                //print("img size = \(img.size.width) \(img.size.height)");
////                
////                print("range = \(range)");
////                //先取出URl
////                if let dic:[String:AnyObject] = self.imgDic[range.location]{
////                    let url:String = dic["url"] as! String;
////                    att.replaceCharactersInRange(NSMakeRange(range.location + base, range.length), withString: url);
////                    base += url.characters.count - 1;
////                }
////                
////                
////            }
////                
////                
//////                let newFont = oldFont.fontWithSize(oldFont.pointSize * 2)
//////                res.removeAttribute(NSFontAttributeName, range: range)
//////                res.addAttribute(NSFontAttributeName, value: newFont, range: range)
////                found = true
////            
////        }
//        
//        if(false == found){
//        
//        
//        }
//        
//        att.endEditing();
//        
//        
//        print("att= \(att.string)");
//        
//        print("ok");
//        
//        //(void (^)(NSDictionary<NSString *, id> *attrs, NSRange range, BOOL *stop))
    
    //MARK:UITextViewDelegate
    func textViewDidBeginEditing(_ textView: UITextView) {
        //出现工具条
        print("出现工具条");
        //self.addTooBar();
    }
    
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool{
        //print("shouldChangeTextInRange \(range)  replacementText = \(text)");
        if("" == text){
            if (self.imgDic[range.location] != nil){
                //说明此位置有图片
                print("删除 at  location = \(range)");
                self.imgDic[range.location] = nil;
            }
        }
        return true;
    }
    
    func getPictrue(_ vc:UIViewController){
        let canCamera:Bool = UIImagePickerController.isSourceTypeAvailable(.camera);
        let canAlbum:Bool = UIImagePickerController.isSourceTypeAvailable(.photoLibrary);
        
        if(false == canAlbum && false == canCamera){
            return;
        }
        
        let actionSheet:UIAlertController = UIAlertController(title: "插入图片", message: "", preferredStyle: UIAlertControllerStyle.actionSheet);
        
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
                vc.present(pc, animated: true, completion: {
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
                vc.present(pc, animated: true, completion: {
                    () -> Void in
                    print("UIImagePickerController camera in");
                })
                
            })
            
            actionSheet.addAction(albumAction);
        }
        
        let cancelAction:UIAlertAction = UIAlertAction(title: "取消", style: UIAlertActionStyle.cancel, handler: {
            (action:UIAlertAction) -> Void in
            print("cancelAction in");
            
            vc.dismiss(animated: true, completion: nil);
            
        })
        
        actionSheet.addAction(cancelAction);
        
        
        //            let popOver:UIPopoverPresentationController = actionSheet.popoverPresentationController!;
        //            popOver.sourceView = sender;
        //            popOver.sourceRect = sender.bounds;
        //            popOver.permittedArrowDirections = UIPopoverArrowDirection.Any;
        
        vc.present(actionSheet, animated: true, completion: {
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
        
        //let cell:HYUserInfoListTopTableViewCell = tableView.cellForRowAtIndexPath(NSIndexPath(forRow: 0, inSection: 0)) as! HYUserInfoListTopTableViewCell;
        
       // cell.headerImgV.image = image;
        
        //self.saveImage(image, newSize: CGSize(width: 256,height: 256), percent: 0.5, imageName: "currentImage.png");
        
        picker.dismiss(animated: true, completion: {
            () -> Void in
            
            print("开始上传头像");
        });
        

        print("image size = \(image.size)");
        let newImage = CommonTools.getImage(withImage: image, maxEdge: self.textViewInput.frame.size.width);
        print("newImage size = \(newImage?.size)");
        
        //上传用的图片
        let uploadNewImage = CommonTools.getImage(withImage: image, maxEdge: 1024);
        print("uploadNewImage size = \(uploadNewImage?.size)");
        
        //上传图像
        self.uploadImage(newImage!, uploadImg: uploadNewImage!, location: self.textViewInput.selectedRange.location);
 
 
    }
    
    func uploadImage(_ img:UIImage ,uploadImg upImg:UIImage,location loc:Int) -> Void {
        
        let dic:[String: AnyObject]? = ["extention":"png" as AnyObject,"type":"EXPERIENCE_PICTURE" as AnyObject];
        var fileLinkID:String = "";
        var fileUrl:String = "";
        
        AskDogGetAccessTokenDataRequest().startRequest(requestParmer: dic,  viewController:self.parentVc,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    let data:HYAccessTokenDataModel = HYAccessTokenDataModel(jsonDic: dic!);
                    
                    let dataUpload = UIImageJPEGRepresentation(upImg, 0.5);
                    
                    let dicP:NSDictionary = ["policy":data.policy!,"signature":data.signature!,"callback":data.callback!,
                        "OSSAccessKeyId":data.OSSAccessKeyId!,"key":data.key!];
                    
                    AskDogUploadImageDataRequest().uploadFile(uploadUrl: data.host!, fileData: dataUpload!, parma: dicP,viewController: self.parentVc, completionHandler: {
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
                            
                            let attachment:NSTextAttachment = NSTextAttachment();
                            attachment.image = img;
                            let attchmentString:NSAttributedString = NSAttributedString(attachment: attachment);
                            
                            self.textViewInput.textStorage.insert(attchmentString, at: loc);
                            
                            print("insertObj location = \(loc)");
                            
                            let sUrl = "![\(fileLinkID)](\(fileUrl))";
                            print("sUrl = \(sUrl)");
                            
                            let insertObj = ["url":sUrl,"linkId":fileLinkID];
                            
                            // imgDicArrar.append(insertObj);
                            self.imgDic[self.textViewInput.selectedRange.location] = insertObj as [String : AnyObject]?;
                            
                            
                            let returnAttchmentString:NSAttributedString = NSAttributedString(string: "\r");
                            self.textViewInput.textStorage.insert(returnAttchmentString, at: self.textViewInput.selectedRange.location+1);
                            
                            
                            let range:NSRange = NSMakeRange(self.textViewInput.selectedRange.location + 2, 0);
                            //range.length = 0;
                            // range.location = self.textViewInput.selectedRange.location + 1;
                            // self.textViewInput.textStorage.appendAttributedString(attchmentString);
                            //var oldAttchmentString:NSAttributedString = self.textViewInput.attributedText;
                            
                            self.textViewInput.becomeFirstResponder();
                            self.textViewInput.selectedRange = range;
                            
                            // self.textViewInput.scrollRectToVisible(CGRectMake(0, self.textViewInput.contentSize.height - 15, self.textViewInput.contentSize.width, 10), animated: false);
                            self.textViewInput.scrollRangeToVisible(NSMakeRange(self.textViewInput.text.characters.count, 1));
                            
                            
                        }
                        
                    });
                }
            }
        });
    }

    
    deinit{
        print("HYTuwenEditView deinit in");
    }
    

}
