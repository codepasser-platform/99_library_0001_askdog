//
//  CommonTools.swift
//  AskDog
//
//  Created by Symond on 16/7/27.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import SVProgressHUD

enum ValidatedType {
    case email
    case phoneNumber
}

class CommonTools: NSObject {
    
    //pod 'UITableView+FDTemplateLayoutCell', '~> 1.3'
    
    static let errInfo:[String:String] = ["SRV_CONFLICT_USER_NAME":"用户已存在！","SRV_NOT_FOUND_CHANNEL":"未找到频道！",
                                          "SRV_NOT_FOUND_EXPERIENCE":"未找到经验！","SRV_CONFLICT_USER_MAIL":"该邮箱已注册！",
                                          "AUTH_FAILED":"认证失败！","Internal Server Error":"服务器出错！",
                                          "RT_RUNTIME":"服务器正忙，请稍后再试"];
    
    static func getStringRect(_ string:String!,font:UIFont,size:CGSize) -> CGRect{
        //let font:UIFont = UIFont.systemFontOfSize(13);
        let attributes = [NSFontAttributeName: font];
        //let size:CGSize = CGSizeMake(10000, 100);
        let option = NSStringDrawingOptions.usesLineFragmentOrigin.rawValue | NSStringDrawingOptions.usesFontLeading.rawValue;
        //let option = [NSStringDrawingOptions.UsesLineFragmentOrigin, NSStringDrawingOptions.UsesFontLeading];
        //let option = NSStringDrawingOptions.UsesLineFragmentOrigin.rawValue; //NSStringDrawingOptions(rawValue: option)
        let rect:CGRect = string.boundingRect(with: size, options: NSStringDrawingOptions(rawValue: option), attributes: attributes, context: nil)
        print("rect=\(rect)");
        return rect;
    }

    static func ValidateText(validatedType type: ValidatedType, validateString: String) -> Bool {
        do {
            let pattern: String
            if type == ValidatedType.email {
                pattern = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$"
            }
            else {
                pattern = "^1[0-9]{10}$"
            }
            
            let regex: NSRegularExpression = try NSRegularExpression(pattern: pattern, options: NSRegularExpression.Options.caseInsensitive)
            let matches = regex.matches(in: validateString, options: NSRegularExpression.MatchingOptions.reportProgress, range: NSMakeRange(0, validateString.characters.count))
            return matches.count > 0
        }
        catch {
            return false
        }
    }
    static func EmailIsValidated(_ vStr: String) -> Bool {
        return ValidateText(validatedType: ValidatedType.email, validateString: vStr)
    }
    static func PhoneNumberIsValidated(_ vStr: String) -> Bool {
        return ValidateText(validatedType: ValidatedType.phoneNumber, validateString: vStr)
    }
    
    
    static func stringToNSDate(_ dateString:String) -> Date{
        let df:DateFormatter = DateFormatter();
        df.locale = Locale.current;
        df.dateFormat = "yyyy-MM-dd";
        let d:Date = df.date(from: dateString)!;
        return d;
    }
    
    static func nsDateToString(_ date:Date,withFormat format:String!) -> String{
        let df:DateFormatter = DateFormatter();
        df.locale = Locale.current;
        df.dateFormat = format;
        let str:String = df.string(from: date);
        return str;
    }
    
    static func nsDateToString(_ date:Date) -> String{
        
        return nsDateToString(date, withFormat: "yyyy-MM-dd");
//        
//        let df:NSDateFormatter = NSDateFormatter();
//        df.locale = NSLocale.currentLocale();
//        df.dateFormat = "yyyy-MM-dd";
//        let str:String = df.stringFromDate(date);
//        return str;
    }
    
    /**
     *  弹出消息框
     *
     *  @param msg:String          要显示的消息
     *  @param title:String        消息杠标题
     *  @param vc:UIViewController 弹出页的VC指针
     *  @param Void?               返回空
     *
     *  @return 返回空
     */
    static func showModalMessage(_ msg:String,title:String,vc:UIViewController,
                                 hasCancelBtn:Bool,OKBtnTitle okti:String = "确定",CancelBtnTitle cancelti:String = "取消",
                                 hr: ((Bool) -> Void)?) -> Void{
        let actionSheet:UIAlertController = UIAlertController(title: title, message: msg, preferredStyle: UIAlertControllerStyle.alert);
        
        if(true == hasCancelBtn){
            let act1:UIAlertAction = UIAlertAction(title: cancelti, style: UIAlertActionStyle.destructive, handler: {
                (action:UIAlertAction) -> Void in
                hr!(false);
            })
            actionSheet.addAction(act1);
        }
        
        let act:UIAlertAction = UIAlertAction(title: okti, style: UIAlertActionStyle.default, handler: {
            (action:UIAlertAction) -> Void in
            hr!(true);
        })
        actionSheet.addAction(act);
        
        vc.present(actionSheet, animated: true, completion: {
            () -> Void in
            print("ok");
        })
    };
    
    static func showMessage(_ msg:String,vc:UIViewController,showTime duration:TimeInterval = 1,hr: ((Void) -> Void)?) -> Void{

        vc.view.makeToast(msg, duration: duration, position: .bottom, title: nil, image: nil, style: nil, completion: {
            
            (didTap: Bool) -> Void in
            
            if let callback = hr{
                callback();
            }
        });
        
    };
    
    static func showErrorMessage(inViewController vc:UIViewController,error:NSError!,handleMethod hr: (() -> Void)?)->Void{
        
        let dic:[AnyHashable: Any] = error.userInfo;
        
        var strCode:String!
        var strMessage:String;
        
        var msg:String! = "";
        
        if let codeObj = dic["code"] {
            
            if let messageObj = dic["message"]{
                strMessage = messageObj as! String;
                print("server return errmessage is = \(strMessage)");
            }
            
            
            strCode = codeObj as! String;
            if let msgObj = errInfo[strCode]{
                msg = msgObj;
            }
            
            let actionSheet:UIAlertController = UIAlertController(title: "错误", message: msg, preferredStyle: UIAlertControllerStyle.alert);
            let act:UIAlertAction = UIAlertAction(title: "确定", style: UIAlertActionStyle.default, handler: {
                (action:UIAlertAction) -> Void in
                hr!();
            })
            actionSheet.addAction(act);
            vc.present(actionSheet, animated: true, completion: {
                () -> Void in
                print("ok");
            })
            
            return;
        }
        
        
        if let partialObj = dic["partial"] {
            
            if let messageObj = dic["message"]{
                strMessage = messageObj as! String;
                print("server return err message is = \(strMessage)");
            }
            
            
            strCode = partialObj as! String;
            if let msgObj = errInfo[strCode]{
                msg = msgObj;
            }
            
            let actionSheet:UIAlertController = UIAlertController(title: "错误", message: msg, preferredStyle: UIAlertControllerStyle.alert);
            let act:UIAlertAction = UIAlertAction(title: "确定", style: UIAlertActionStyle.default, handler: {
                (action:UIAlertAction) -> Void in
                hr!();
            })
            actionSheet.addAction(act);
            vc.present(actionSheet, animated: true, completion: {
                () -> Void in
                print("ok");
            })
            
            return;
        }
        
        if let errorObj = dic["error"] {
            
            if let messageObj = dic["message"]{
                strMessage = messageObj as! String;
                print("server return errmessage is = \(strMessage)");
            }
            
            
            strCode = errorObj as! String;
            if let msgObj = errInfo[strCode]{
                msg = msgObj;
            }
            
            let actionSheet:UIAlertController = UIAlertController(title: "错误", message: msg, preferredStyle: UIAlertControllerStyle.alert);
            let act:UIAlertAction = UIAlertAction(title: "确定", style: UIAlertActionStyle.default, handler: {
                (action:UIAlertAction) -> Void in
                hr!();
            })
            actionSheet.addAction(act);
            vc.present(actionSheet, animated: true, completion: {
                () -> Void in
                print("ok");
            })
            
            return;
        }

    }
    
    
    static func compareTime(_ time:TimeInterval) -> String!{
        //取当前时间
        let date = Date();
        let curTimeinterval = date.timeIntervalSince1970 * 1000;
        let offset = (curTimeinterval - time)/1000;
        
        
        if(offset < 0 ){
            // 属于异常
            return "";
        }else if(offset >= 0 && offset < 60){
           let s = String(format: "%.0f", offset)
            return "\(s)秒钟前";
        }else if(offset >= 60 && offset < 3600 ){
            let min = Int(offset/60);
            return "\(min)分钟前";
        }else if(offset >= 3600 && offset < 86400){
            let hour = Int(offset/3600);
            return "\(hour)小时前";
        }else if(offset >= 86400 && offset < 604800){
            let day = Int(offset/86400);
            return "\(day)天前";
        }else if(offset >= 604800 && offset < 2592000){
            let week = Int(offset/604800);
            return "\(week)周前";
        }else if(offset >= 2592000 && offset < 31104000){
            let month = Int(offset/2592000);
            return "\(month)个月前";
        }else if(offset >= 31104000){
            let year = Int(offset/31104000);
            return "\(year)年前";
        }
        return "";
    }
    
    static func rgbColor(red r:CGFloat,green g:CGFloat,blue b:CGFloat,alpha a:CGFloat = 1.0) -> UIColor{
        return UIColor(red: r/255.0, green: g/255.0, blue: b/255.0, alpha: a);
    }
    
    
    static func arrayCheckNullObject(_ obj:Any?,callBack:((NSArray) -> Void)) -> Void{
        
        if let letObj = obj{
            if letObj as! NSObject == NSNull(){
                callBack(NSArray());
            }else{
                callBack(obj as! NSArray);
            }
        }
        
    }
    
    
    /// 泛型array函数
    ///
    /// - parameter obj:      字典里的对像
    /// - parameter callBack: 回调
    static func arrayFanxingCheckNullObject<T:HYBaseDataModelProtocol>(_ obj:Any?,callBack:(([T]) -> Void)) -> Void{
    
        if let letObj = obj{
            if letObj as! NSObject == NSNull(){
                callBack([T]());
            }else{
                let tempArr:NSArray = obj as! NSArray;
                var arr:[T] = [T]();
                for item in tempArr{
                    let d:NSDictionary = item as! NSDictionary;
                    let da:T = T(jsonDic: d);
                    arr.append(da);
                }
                callBack(arr);
            }
        }
        
    }
    
    static func dicCheckNullObject(_ obj:Any?,callBack:((NSDictionary) -> Void)) -> Void{
        
        if let letObj = obj{
            if letObj as! NSObject == NSNull(){
                callBack(NSDictionary());
            }else{
                callBack(obj as! NSDictionary);
            }
        }
        
    }
    
    static func nsTimeIntervalCheckNullObject(_ obj:Any?,callBack:((TimeInterval) -> Void)) -> Void{
        
        if let letObj = obj{
            if letObj as! NSObject == NSNull(){
                callBack(0);
            }else{
                callBack(obj as! TimeInterval);
            }
        }
        
    }
    
    static func stringCheckNullObject(_ obj:Any?,callBack:((String) -> Void)) -> Void{
        
        if let letObj = obj{
            if letObj as! NSObject == NSNull(){
                callBack("");
            }else{
                callBack(obj as! String);
            }
        }

    }
    
    static func boolCheckNullObject(_ obj:Any?,callBack:((Bool) -> Void)) -> Void{
        
        if let letObj = obj{
            if letObj as! NSObject == NSNull(){
                callBack(false);
            }else{
                callBack(obj as! Bool);
            }
        }
    }
    
    static func floatCheckNullObject(_ obj:Any?,callBack:((Double) -> Void)) -> Void{
        
        if let letObj = obj{
            if letObj as! NSObject == NSNull(){
                callBack(0);
            }else{
                callBack(obj as! Double);
            }
        }
    }
    
    static func intCheckNullObject(_ obj:Any?,callBack:((Int) -> Void)) -> Void{
        
        if let letObj = obj{
            if letObj as! NSObject == NSNull(){
                callBack(0);
            }else{
                callBack(obj as! Int);
            }
        }
    }
    
    static func saveImage(_ currentImage:UIImage,newSize:CGSize,percent:CGFloat,imageName:String){
        //压缩图片尺寸
        UIGraphicsBeginImageContext(newSize);
        currentImage.draw(in: CGRect(x: 0, y: 0, width: newSize.width, height: newSize.height));
        let newImage:UIImage = UIGraphicsGetImageFromCurrentImageContext()!;
        UIGraphicsEndImageContext();
        //高保真压缩图片质量
        let imageData:Data = UIImageJPEGRepresentation(newImage, percent)!;
        //获取沙盒目录，将图片放在沙盒的documents文件夹中
        let fullPath:String = (NSHomeDirectory() + "/Documents/") + imageName;
        print("fullpath=\(fullPath)");
        try? imageData.write(to: URL(fileURLWithPath: fullPath), options: []);
        
    }
    
    static func getImageWithSize(_ currentImage:UIImage,newSize:CGSize) -> UIImage!{
        //压缩图片尺寸
        UIGraphicsBeginImageContext(newSize);
        currentImage.draw(in: CGRect(x: 0, y: 0, width: newSize.width, height: newSize.height));
        let newImage:UIImage = UIGraphicsGetImageFromCurrentImageContext()!;
        UIGraphicsEndImageContext();
        
        return newImage;
    }
    
    static func getImage(withImage img:UIImage,maxEdge length:CGFloat) -> UIImage!{
        let oldSize:CGSize = img.size;
        var resultSize:CGSize = CGSize.zero;
        
        //如果全都小于要求的尺寸 则输入原图
        if((oldSize.height <= length) && (oldSize.width <= length)){
            return img;
        }else if(oldSize.height == oldSize.width){
            //正方形
            resultSize = CGSize(width: length, height: length);
            
        }else if(oldSize.width > oldSize.height){
            //横版 
            let fH:CGFloat = length * oldSize.height / oldSize.width;
            resultSize = CGSize(width: length, height: fH);
        }else{
            let fH:CGFloat = length * oldSize.height / oldSize.width;
            resultSize = CGSize(width: length, height: fH);
        }
        
        
        let img:UIImage = CommonTools.getImageWithSize(img, newSize: resultSize);
        return img;

    }
    
//    static func saveFile(fileName:String,data:NSData) -> Bool{
//        // 通过文件名 分析出 路径  和文件夹
//        
//        let f = "/abc.png";
//        
//        let fileManager = NSFileManager.defaultManager();
//        //用 / 分割字符串
//        let arr:[String] = f.componentsSeparatedByString("/");
//        if(1 == arr.count){
//            //说明只有一级文件名
//            let fullPath:String = NSHomeDirectory().stringByAppendingString("/Documents/").stringByAppendingString(f);
//        }
//        
//        
//        
//        return false;
//    }
    
    static func saveFile(_ fileName:String,data:Data,hr: ((Bool,String) -> Void)?) -> Void {
        //                        let docDirs = NSSearchPathForDirectoriesInDomains(.DocumentDirectory, .UserDomainMask, true);
        //                        let path = "\(docDirs)/v.mov";
        //                        print("video path = \(path)");
        //                        NSFileManager.defaultManager().createFileAtPath(path, contents: d, attributes: nil);
        
        let fullPath:String = (NSHomeDirectory() + "/Documents/") + fileName;
      //  fullPath = "file://\(fullPath)";
        
        print("fullpath=\(fullPath)");
        
        let fileManager = FileManager.default;
        if(fileManager.fileExists(atPath: fullPath)){
            do {
                try fileManager.removeItem(atPath: fullPath);
            } catch let error as NSError? {
                print("error saving core data: \(error)")
            }
        }
        
        let beCreate = fileManager.createFile(atPath: fullPath, contents: data, attributes: nil);
        if(true == beCreate){
            print("write \(fullPath) success");
            hr!(true,fullPath);
        }else{
            print("write \(fullPath) failed");
            hr!(false,fullPath);
        }
        
    }
    
    static func checkLogin(viewController parentVC:HYBaseViewController,afterLoginCall call:((Void) -> Void)?,callBack fun:((Void) -> Void)?) -> Void {
        if(false == Global.sharedInstance.isLogined){
            let login:HYLoginViewController = HYLoginViewController(nibName: "HYLoginViewController", bundle: Bundle.main);
            
            if (nil != call){
                login.isTempLogin = true;
                
                login.callBackLoginedFun = {
                    (beOK:Bool)-> Void in
                    print("temp login success");
                    if let callFun:((Void) -> Void) = call{
                        callFun();
                    }
                };
            }
            login.callWeixinFun = {
                (beOK:Bool)-> Void in
                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "HYThirdAuthViewController", bundle: Bundle.main);
                parentVC.navigationController?.pushViewController(vc, animated: true);
            };
            login.callQQFun = {
                (beOK:Bool)-> Void in
                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "HYThirdAuthViewController", bundle: Bundle.main);
                parentVC.navigationController?.pushViewController(vc, animated: true);
            };
            login.callWeiboFun = {
                (beOK:Bool)-> Void in
                let vc:HYThirdAuthViewController = HYThirdAuthViewController(nibName: "HYThirdAuthViewController", bundle: Bundle.main);
                parentVC.navigationController?.pushViewController(vc, animated: true);
            };
            
            login.callBackFunForgetPwd = {
                (beOK:Bool)-> Void in
                if(true == beOK){
                    let vc:HYForgetPasswordViewController = HYForgetPasswordViewController(nibName: "HYForgetPasswordViewController", bundle: Bundle.main);
                    parentVC.navigationController?.pushViewController(vc, animated: true);
                }
            };
            
            login.reloadLoginedHomePageDataCallBackFun = {
                (beOK:Bool)-> Void in
                if(true == beOK){
                    parentVC.shouldUpdateData();
                }
            };
            
            login.callBackGoCategories = {
                (beOK:Bool)-> Void in
                if(true == beOK){
//                    AskDogGetCategoriesListDataRequest().startRequest(viewController:parentVC,completionHandler: {
//                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
//                        if(true == beOk){
//                            
//                            var array:[HYCategoryDataModel] = [HYCategoryDataModel]();
//                            
//                            for item in arr!{
//                                if let obj:AnyObject = item as AnyObject?{
//                                    let dic:NSDictionary = obj as! NSDictionary;
//                                    let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
//                                    array.append(data);
//                                }
//                            }
//                            
//                            
//                            let vc:HYDingyuePindaoViewController = HYDingyuePindaoViewController(nibName: "HYDingyuePindaoViewController", bundle: Bundle.main);
//                            vc.dataModelArray = array;
//                            parentVC.navigationController?.pushViewController(vc, animated: true);
//                        }
//                    });
                }
            }
            
            //   login.callBackGoXieyi = {
            //       (beOK:Bool)-> Void in
            //       if(true == beOK){
            //           let vc:HYUserAgreementViewController = HYUserAgreementViewController(nibName: "HYUserAgreementViewController", bundle: NSBundle.mainBundle());
            //           vc.setReturnTyle(UserAgreeMentReturnStyle.UserAgreeMentReturnStyle_pop_animated);
            //           self.navigationController?.pushViewController(vc, animated: true);
            //           //self.presentViewController(vc, animated: true, completion: nil);
            //       }
            //   }
            
            parentVC.present(login, animated: true, completion: nil);
        }else{
            if let call:((Void) -> Void) = fun{
                call();
            }
        }
    }
    
    static func getRandomNum(maxValue maxV:UInt32,minVlaue minV:UInt32) -> UInt32{
        
        let n:UInt32 = maxV - minV;
        
        return arc4random_uniform(n) + minV;
    }
    
    static func compareDataInActivity() -> Bool {
        let now = Date()
        let sDateStr = "2016-09-15 00:00"
        let eDateStr = "2016-09-17 23:59"
        let dateForm = DateFormatter()
        dateForm.dateFormat = "yyyy-MM-dd HH:mm"
        
        if let sDate: Date = dateForm.date(from: sDateStr) {
            let date: Date = (sDate as NSDate).earlierDate(now)
            if date == now {
                //早于开始时间
                return false
            }
        }
        
        if let eDate: Date = dateForm.date(from: eDateStr) {
            let date: Date = (eDate as NSDate).laterDate(now)
            if date == now {
                //晚于结束时间
                return false
            }
        }
        return true
    }
    
    static func deleteFile(filePath path:String) -> Void {
        
        var isDirectory: ObjCBool = false
        if(true == FileManager.default.fileExists(atPath: path, isDirectory: &isDirectory)){
            if(true == FileManager.default.isDeletableFile(atPath: path)){
                do{
                    try FileManager.default.removeItem(atPath: path);
                }catch let err{
                    
                    log.error(err)/;
                    return;
                }
                
            }
        }
    }
    
    

}









