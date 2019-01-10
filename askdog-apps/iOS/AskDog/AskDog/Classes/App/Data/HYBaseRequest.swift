//
//  HYBaseRequest.swift
//  AskDog
//
//  Created by Symond on 16/7/27.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import Foundation
import Alamofire
import SVProgressHUD

//import ASIHTTPRequest

open class HYBaseRequest{
    

    var resultDic:NSDictionary!
    var resultArray:NSArray!
    var exUrl:String?
    
    fileprivate func saveCookie() -> Void {
        let cok:HTTPCookieStorage = HTTPCookieStorage.shared;
        let arrCookie:[HTTPCookie] = cok.cookies!;
        
//        for item in arrCookie{
//            log.ln("cookie = \(item)")/;
//            let c1:Data = NSKeyedArchiver.archivedData(withRootObject: item);
//            let strResult:String = (NSString(data: c1, encoding: String.Encoding.utf8.rawValue) as? String)!;
//            log.ln("cookie111 = \(strResult)")/;
//        }
        
        /*
         * 把cookie进行归档并转换为NSData类型
         * 注意：cookie不能直接转换为NSData类型，否则会引起崩溃。
         * 所以先进行归档处理，再转换为Data
         */
        let cookieData:Data = NSKeyedArchiver.archivedData(withRootObject: arrCookie);
        UserDefaults.standard.set(cookieData, forKey: "cookie");
        UserDefaults.standard.synchronize();
    }
    
    fileprivate func useCookie() -> [String:String]? {
        
        if let obj = UserDefaults.standard.object(forKey: "cookie"){
            let arrCookie:[HTTPCookie] = NSKeyedUnarchiver.unarchiveObject(with: obj as! Data) as! [HTTPCookie];
            
            let cok:HTTPCookieStorage = HTTPCookieStorage.shared;
            for item in arrCookie{
                cok.setCookie(item);
            }
            
//            log.ln("打印cookie看是否设备置成功了")/;
//            
//            for it in arrCookie{
//                log.ln("cookie = \(it)")/;
//            }
            if(arrCookie.count > 0){
                let dic:[String:String] = HTTPCookie.requestHeaderFields(with: arrCookie);
                let strCookie:String = dic["Cookie"]!;
                log.ln("Cookie = \(strCookie)")/;
                return ["Cookie":strCookie];
            }
        }
        
        return nil;
    }
    
    fileprivate func deleteCookie() -> Void {
        let cok:HTTPCookieStorage = HTTPCookieStorage.shared;
        let arrCookie:[HTTPCookie] = cok.cookies!;
        
        for item in arrCookie{
            //log.ln("cookie = \(item)")/;
            cok.deleteCookie(item);
        }
    }
    
    func startRequest(baseUrl url:String? = nil,
                              requestParmer parameters: [String: AnyObject]? = nil,
                                            showProgressHUD beShow:Bool = true,
                                                            exUrl urlString:String? = nil,viewController vc:UIViewController,autoShowErrorAlertView show:Bool = true,
                                                                  completionHandler: @escaping (Bool?,NSArray?, NSDictionary?, NSError?) -> Void) -> Void {
        
        
        
        //header hds:[String: String]? = nil
        
        if(true == beShow){
            SVProgressHUD.show(withStatus: "加载中...");
        }
        
        // let hds:[String:String] = self.getHeaders()!;
        
//        print("\(self) request parma:\(parameters)");
//        print("\(self) request headers:\(self.getHeaders())");
        
        _ = log.ln("\(self) request parma:\(parameters)")/;
        _ = log.ln("\(self) request headers:\(self.getHeaders())")/;
        //看看有没有扩展的URL
        if let url:String = urlString{
            exUrl = url;
        }
        
        var requestUrl:Alamofire.URLConvertible = "";
        
        if url != nil{
            requestUrl = url!;
        }else{
            requestUrl = self.getRequestUrl();
        }
        
        
//        
//        Alamofire.request(requestUrl, method: self.getRequestMethod(), parameters: parameters, encoding: self.getParametersEncoding(), headers: self.getHeaders()).debugDescription
        
        Alamofire.request(requestUrl, method: self.getRequestMethod(), parameters: parameters, encoding: self.getParametersEncoding(), headers: self.getHeaders()).validate(statusCode: 200..<300).responseData { (resultObj) in
            
            
           // let requ:URLRequest? = resultObj.request;
            
            self.saveCookie();
//            self.deleteCookie();
//            self.useCookie();
            
            
            let res:HTTPURLResponse? = resultObj.response;
            let data:Data = resultObj.data!;
            
            switch resultObj.result{
                case .success(let value):
                    //判断http请求状态号
                    //print("res?.statusCode = \(res?.statusCode)");
                    _ = log.ln("res?.statusCode = \(res?.statusCode)")/;
                    
                    let strResult:String = (NSString(data: value, encoding: String.Encoding.utf8.rawValue) as? String)!;
                    print("requeset string = \(strResult)");
                    
                    
                    _ = log.url(strResult)/;
//                    if(true == OPEN_ICONSOLE){
//                        iConsole.info("%@", args: getVaList([strResult as NSString]));
//                    }
                    
                    if("" == strResult){
                        completionHandler(true,nil,nil,nil);
                        if(true == beShow){
                            SVProgressHUD.dismiss();
                        }
                        return;
                    }
                    
                    //如果只检测状态码 就不往下走了
                    if(true == self.isOnlyCheckHttpStatusCode()){
                        
                        completionHandler(true,nil,nil,nil);
                        if(true == beShow){
                            SVProgressHUD.dismiss();
                        }
                        return;
                    }
                    
                    
                    
//                    let s:String = "[[[[";
//                    let d   :Data = s.data(using: String.Encoding.utf8)!;
//                
//                    print("\(self) request success in");
                    
                    do{
                        let jsonObj:Any = try JSONSerialization.jsonObject(with: data, options: .allowFragments);
                        if(data[0] == 123){
                            // {
                            print("json is a dic obj");
                            self.resultDic = jsonObj as! NSDictionary;
                            completionHandler(true,nil,self.resultDic,nil);
                        }else if(data[0] == 91){
                            // [
                            print("json is a array obj");
                            self.resultArray = jsonObj as! NSArray;
                            completionHandler(true,self.resultArray,nil,nil);
                        }
                    }catch let error as NSError?{
                        print("error to json obj: \(error)")
                        
                        //数据请求成功 但是没有JSON数据
                        print("数据请求成功 但是没有JSON数据");
                        
                        let errDic:[AnyHashable: Any] = ["message":"retrun data is not a json object!",
                                                         "code":"myCode",
                                                         "type":"myType",
                                                         "partial":"myPartial"];
                        
                        let myError:NSError = NSError(domain: "my custom error", code: -9999, userInfo: errDic);
                        print("\(self) retrun data is not a json object! but statusCode is ok");
                        if(true == show){
                            self.dealWithError(err: myError,viewController: vc);
                        }
                        
                        completionHandler(false,nil,nil,myError);

                        //completionHandler(true,nil,nil,nil);
                    }
                    
                    break;
                case .failure(let error):
                    //print("\(self) request failed in errtype=\(error)");
                    
                     _ = log.error(error)/;
                    
                    
                    let strJson:String = (NSString(data: data, encoding: String.Encoding.utf8.rawValue) as? String)!;
                    //print("requeset error string = \(strJson)");
                    
                   
                    
//                    if(true == OPEN_ICONSOLE){
//                        iConsole.info("%@", args: getVaList([strJson as NSString]));
//                    }
                    
                    log.ln("requeset error string = \(strJson)")/;
                    
                    //var errDic:NSMutableDictionary = NSMutableDictionary();
                    
                    var errDic:[AnyHashable : Any] = [AnyHashable : Any]();
                    
                    do{
                        let jsonObj:Any = try JSONSerialization.jsonObject(with: data, options: .allowFragments);
                        if(data[0] == 123){
                            print("json is a dic obj");
                            errDic = jsonObj as! [AnyHashable : Any];
                        }else if(data[0] == 91){
                            print("json is a array obj");
                            
                        }
                    }catch _ as NSError?{
                        
                        print("error to json obj: \(error)")
                        
                        let err:Error = error as Error;
                        errDic["message"] = err.localizedDescription;
                        
//                        let sysErrDic = err.userInfo;
//                        //errDic = NSMutableDictionary();
//                        if let strObj:AnyObject = sysErrDic["NSLocalizedDescription"] as AnyObject?{
//                            let strMsg:String = strObj as! String;
//                            //errDic.setValue(strMsg, forKey: "message");
//                            errDic["message"] = strMsg;
//                        }else{
//                            //errDic.setValue("", forKey: "message");
//                            errDic["message"] = "";
//                        }
//                        //errDic.setValue(String(err.code), forKey: "code");
//                        errDic["code"] = String(err.code);
                    }
                    
                    //解析错误信息 并抛出
                    let myError:NSError = NSError(domain: "server return error", code: -9998, userInfo: errDic);
                    if(true == show){
                        self.dealWithError(err: myError,viewController: vc);
                    }
                    completionHandler(false,nil,nil,myError);
                    
                    break;
                
                
               
            }
            
            if(true == beShow){
                SVProgressHUD.dismiss();
            }
            
        };
//.debugDescription
       // log.ln("debugDescription = \(tt)")/;
        
    }
    
    func dealWithError(err:NSError,viewController vc:UIViewController) -> Void {
        
        let errDic:[AnyHashable : Any] = err.userInfo;
        var strCode:String = "";
        CommonTools.stringCheckNullObject(errDic["code"], callBack: {
            (str:String) -> Void in
            strCode = str;
            //FIXME:test
            //strCode = "WEB_ACCESS_ACCOUNT_EXPIRED";
            //判断如果是需要重新登陆的 就要处理
            let reLoginArr:[String] = ["WEB_ACCESS_ACCOUNT_EXPIRED","WEB_ACCESS_CREDENTIALS_EXPIRED"];
            if(reLoginArr.contains(strCode)){
                //重新登录
                //自动处理错误信息
                CommonTools.stringCheckNullObject(errDic["message"], callBack: {
                    (str:String) -> Void in
                    
                    //vc.view.makeToast(str, duration: 3, position: .Bottom);
                    
                    CommonTools.showMessage(str, vc: vc,showTime:2, hr: {
                        () -> Void in
                        //FIXEME:试试 HYBaseViewController.self
                        //                                        if(vc.isKind(of: HYBaseViewController())){
                        //                                            let baseVc:HYBaseViewController = vc as! HYBaseViewController;
                        //                                            baseVc.doRelogin();
                        //                                        }
                    });
                    
                });
                
            }else{
                //自动处理错误信息
                CommonTools.stringCheckNullObject(errDic["message"], callBack: {
                    (str:String) -> Void in
                    
                    vc.view.makeToast(str, duration: TimeInterval(3), position: .bottom);
                    
                });
            }
        });

        

    }
    
    func getRequestMethod() -> Alamofire.HTTPMethod {
        return .get;
    }
    
    func getRequestUrl() -> Alamofire.URLConvertible {
        return self.makeUrl();
    }
    
    func getHttpHttps() -> String! {
        return "http";
    }
    
    func getBaseUrl() -> String! {
        return REQUEST_BASE_URL;
    }
    
    func getMethodUrl() -> String! {
        return "";
    }
    
    func getHeaders() -> [String:String]? {

        return self.useCookie();
       // return nil;
    }
    
    func makeUrl() -> Alamofire.URLConvertible! {
        if let tempUrl:String = exUrl{
            let url:Alamofire.URLConvertible = self.getHttpHttps()+"://"+self.getBaseUrl()+"/"+self.getMethodUrl()+tempUrl;
            print("base request:\(self)-->URL:\(url)");
            return url;
        }else{
            let url:Alamofire.URLConvertible = self.getHttpHttps()+"://"+self.getBaseUrl()+"/"+self.getMethodUrl();
            print("base request:\(self)-->URL:\(url)");
            return url;
        }
    }
    
    func getParametersEncoding() -> Alamofire.ParameterEncoding {
        return URLEncoding.default;
    }
    
    func processRequestData(_ obj:AnyObject) -> Void {
        print("processRequestData");
    }
    
    //如果只检测状态码 就可以判断成功失败的 请重写本函数
    func isOnlyCheckHttpStatusCode() -> Bool {
        return false;
    }
    
    //MARK:UPLOAD
//    func uploadFile(uploadUrl url:String,fileData data:Data,parma parmaDic:NSDictionary ,completionHandler: @escaping (Bool?,NSArray?,NSDictionary?,NSError?) -> Void) -> Void {
//        
//        Alamofire.upload(.POST, URL(string: url)!, multipartFormData: {
//                (multipartFormData: MultipartFormData) -> Void in
//            
//            UIApplication.sharedApplication().networkActivityIndicatorVisible = true;
//            
//            for (key,value) in parmaDic{
//                //assert(value is String, "参数必须能够转换为NSData的类型，比如String");
//                multipartFormData.appendBodyPart(data: value.dataUsingEncoding(NSUTF8StringEncoding)!, name: key as! String ) ;
//            }
//            
//            multipartFormData.appendBodyPart(data: data, name: "file",fileName: "picture",mimeType: "image/png");
//            
//            }, encodingCompletion: {
//                (result:SessionManager.MultipartFormDataEncodingResult) -> Void in
//                
//                switch result {
//                case .Success( let request, _, _):
//                    print("upload file success");
//                    request.validate(statusCode: 200..<300).response(completionHandler: {
//                        (request:NSURLRequest?,res:NSHTTPURLResponse?,data:NSData?,error:NSError?)->Void in
//                        
//                        print("res?.statusCode = \(res?.statusCode)");
//                        
//                        let strResult = NSString(data: data!, encoding: NSUTF8StringEncoding) as? String;
//                        print("requeset string = \(strResult)");
//                        
//                        if(nil == error){
//                            //请求成功
//                            print("request success in");
//                            let jsonObj:AnyObject! = try? NSJSONSerialization.JSONObjectWithData(data!, options: .AllowFragments);
//                            if(nil == jsonObj){
//                                
//                                let errDic:[AnyHashable: Any] = ["message":"retrun data is not a json object!",
//                                    "code":"myCode",
//                                    "type":"myType",
//                                    "partial":"myPartial"];
//                                
//                                let myError:NSError = NSError(domain: "my custom error", code: -9999, userInfo: errDic);
//                                print("\(self) retrun data is not a json object! but statusCode is ok");
//                                //print("myerror is \(myError)");
//                                completionHandler(false,nil,nil,myError);
//                                UIApplication.sharedApplication().networkActivityIndicatorVisible = false;
//                                return;
//                            }
//                            
//                            let beArray:Bool = jsonObj.isKindOfClass(NSArray);
//                            let beDic:Bool = jsonObj.isKindOfClass(NSDictionary);
//                            if(true == beDic){
//                                self.resultDic = jsonObj as! NSDictionary;
//                            }else if(true == beArray){
//                                self.resultArray = jsonObj as! NSArray;
//                            }
//                            
//                            completionHandler(true,self.resultArray,self.resultDic,error);
//                            UIApplication.sharedApplication().networkActivityIndicatorVisible = false;
//                            
//                        }else{
//                            
//                            //请求失败
//                            print("request failed in");
//                            let jsonObj:AnyObject! = try? NSJSONSerialization.JSONObjectWithData(data!, options: .AllowFragments);
//                            if(nil == jsonObj){
//                                
//                                let errDic:[AnyHashable: Any] = ["message":"retrun data is not a json object!",
//                                    "code":"myCode",
//                                    "type":"myType",
//                                    "partial":"myPartial"];
//                                
//                                let myError:NSError = NSError(domain: "my custom error", code: -9999, userInfo: errDic);
//                                print("\(self) retrun data is not a json object!");
//                                //print("myerror is \(myError)");
//                                completionHandler(false,nil,nil,myError);
//                                UIApplication.sharedApplication().networkActivityIndicatorVisible = false;
//                                return;
//                            }
//                            
//                            let errDic:[AnyHashable: Any] = jsonObj as! [AnyHashable: Any];
//                            
//                            let myError:NSError = NSError(domain: "server return error", code: -9998, userInfo: errDic);
//                            
//                            completionHandler(false,nil,nil,myError);
//                            
//                            UIApplication.sharedApplication().networkActivityIndicatorVisible = false;
//                        }
//                        
//                    });
//                    
//                case .Failure(let err):
//                    completionHandler(false,nil,nil,err);
//                    
//                    UIApplication.sharedApplication().networkActivityIndicatorVisible = false;
//                    
//                }
//                
//        });
//        
//    }
    
    func uploadFile(uploadUrl url:String,fileData data:Data,parma parmaDic:NSDictionary ,viewController vc:UIViewController,completionHandler: @escaping (Bool?,NSArray?,NSDictionary?,NSError?) -> Void) -> Void {
        
        
        Alamofire.upload(multipartFormData: {
            (multipartFormData: MultipartFormData) -> Void in
            
            UIApplication.shared.isNetworkActivityIndicatorVisible = true;
            
            for (key,value) in parmaDic{
                //assert(value is String, "参数必须能够转换为NSData的类型，比如String");
                //multipartFormData.append(data: (value as AnyObject).dataUsingEncoding(String.Encoding.utf8)!, name: key as! String ) ;
                multipartFormData.append((value as! String).data(using: String.Encoding.utf8)!, withName: key as! String);
            }
            
            multipartFormData.append(data, withName: "file",fileName: "picture",mimeType: "image/png");
            
            },to:url,
              encodingCompletion: {
                (result:SessionManager.MultipartFormDataEncodingResult) -> Void in
                
                switch result {
                case .success( let request, _, _):
                    print("upload file success");
                    request.validate(statusCode: 200..<201).responseData(completionHandler: { (resultObj)->Void  in
                       // let request:URLRequest? = resultObj.request;
                        let res:HTTPURLResponse? = resultObj.response;
                        let data:Data = resultObj.data!;
                        
                        print("res?.statusCode = \(res?.statusCode)");
                        
                        let strResult:String = (NSString(data: data, encoding: String.Encoding.utf8.rawValue) as? String)!;
                        
                        print("requeset string = \(strResult)");
                        
                        do {
                            let jsonObj:Any = try JSONSerialization.jsonObject(with: data, options: .allowFragments);
                            
                            if(data[0] == 123){
                                // {
                                print("json is a dic obj");
                                self.resultDic = jsonObj as! NSDictionary;
                                completionHandler(true,nil,self.resultDic,nil);
                            }else if(data[0] == 91){
                                // [
                                print("json is a array obj");
                                self.resultArray = jsonObj as! NSArray;
                                completionHandler(true,self.resultArray,nil,nil);
                            }
                            UIApplication.shared.isNetworkActivityIndicatorVisible = false;
                        } catch let error as NSError? {
                            print("error to json obj: \(error)")
                            
                            let errDic:[AnyHashable: Any] = ["message":"retrun data is not a json object!",
                                                             "code":"myCode",
                                                             "type":"myType",
                                                             "partial":"myPartial"];
                            
                            let myError:NSError = NSError(domain: "my custom error", code: -9999, userInfo: errDic);
                            print("\(self) retrun data is not a json object! but statusCode is ok");
                            
                            self.dealWithError(err: myError,viewController: vc);
                            
                            completionHandler(false,nil,nil,myError);
                        }
                        
                    });
                    
//                    request.validate(statusCode: 200..<300).response(completionHandler: {
//                        
//                        (resultObj)->Void in
//                        
//                        let request:URLRequest? = resultObj.request;
//                        let res:HTTPURLResponse? = resultObj.response;
//                        let data:Data = resultObj.data!;
//                        
//                        print("res?.statusCode = \(res?.statusCode)");
//                        
//                        let strResult = NSString(data: data!, encoding: NSUTF8StringEncoding) as? String;
//                        print("requeset string = \(strResult)");
//                        
//                        if(nil == error){
//                            //请求成功
//                            print("request success in");
//                            let jsonObj:AnyObject! = try? NSJSONSerialization.JSONObjectWithData(data!, options: .AllowFragments);
//                            if(nil == jsonObj){
//                                
//                                let errDic:[AnyHashable: Any] = ["message":"retrun data is not a json object!",
//                                                                 "code":"myCode",
//                                                                 "type":"myType",
//                                                                 "partial":"myPartial"];
//                                
//                                let myError:NSError = NSError(domain: "my custom error", code: -9999, userInfo: errDic);
//                                print("\(self) retrun data is not a json object! but statusCode is ok");
//                                //print("myerror is \(myError)");
//                                completionHandler(false,nil,nil,myError);
//                                UIApplication.sharedApplication().networkActivityIndicatorVisible = false;
//                                return;
//                            }
//                            
//                            let beArray:Bool = jsonObj.isKindOfClass(NSArray);
//                            let beDic:Bool = jsonObj.isKindOfClass(NSDictionary);
//                            if(true == beDic){
//                                self.resultDic = jsonObj as! NSDictionary;
//                            }else if(true == beArray){
//                                self.resultArray = jsonObj as! NSArray;
//                            }
//                            
//                            completionHandler(true,self.resultArray,self.resultDic,error);
//                            UIApplication.sharedApplication().networkActivityIndicatorVisible = false;
//                            
//                        }else{
//                            
//                            //请求失败
//                            print("request failed in");
//                            let jsonObj:AnyObject! = try? NSJSONSerialization.JSONObjectWithData(data!, options: .AllowFragments);
//                            if(nil == jsonObj){
//                                
//                                let errDic:[AnyHashable: Any] = ["message":"retrun data is not a json object!",
//                                                                 "code":"myCode",
//                                                                 "type":"myType",
//                                                                 "partial":"myPartial"];
//                                
//                                let myError:NSError = NSError(domain: "my custom error", code: -9999, userInfo: errDic);
//                                print("\(self) retrun data is not a json object!");
//                                //print("myerror is \(myError)");
//                                completionHandler(false,nil,nil,myError);
//                                UIApplication.sharedApplication().networkActivityIndicatorVisible = false;
//                                return;
//                            }
//                            
//                            let errDic:[AnyHashable: Any] = jsonObj as! [AnyHashable: Any];
//                            
//                            let myError:NSError = NSError(domain: "server return error", code: -9998, userInfo: errDic);
//                            
//                            completionHandler(false,nil,nil,myError);
//                            
//                            UIApplication.sharedApplication().networkActivityIndicatorVisible = false;
//                        }
//                        
//                    });
                    
                case .failure(let error):
                    
                    print("\(self) request failed in errtype=\(error)");
                    
                    let strJson:String = (NSString(data: data, encoding: String.Encoding.utf8.rawValue) as? String)!;
                    print("requeset error string = \(strJson)");
                    
                    //var errDic:NSMutableDictionary = NSMutableDictionary();
                    
                    var errDic:[AnyHashable : Any] = [AnyHashable : Any]();
                    
                    do{
                        let jsonObj:Any = try JSONSerialization.jsonObject(with: data, options: .allowFragments);
                        if(data[0] == 123){
                            print("json is a dic obj");
                            errDic = jsonObj as! [AnyHashable : Any];
                        }else if(data[0] == 91){
                            print("json is a array obj");
                            
                        }
                    }catch _ as NSError?{
                        
                        print("error to json obj: \(error)")
                        errDic["message"] = error.localizedDescription;

                    }
                    
                    //解析错误信息 并抛出
                    let myError:NSError = NSError(domain: "server return error", code: -9998, userInfo: errDic);
                    self.dealWithError(err: myError,viewController: vc);
                    completionHandler(false,nil,nil,myError);

//                    self.dealWithError(err: myError,viewController: vc);
//                    completionHandler(false,nil,nil,err as NSError?);
                    UIApplication.shared.isNetworkActivityIndicatorVisible = false;
                    
                }
                
        });
        
    }
    
}

