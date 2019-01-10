//
//  log.swift
//  AskDog
//
//  Created by Symond on 16/9/20.
//  Copyright © 2016年 Hooying. All rights reserved.
//


//
//  Log.swift
//
//
//  Created by Andyy Hope on 14/04/2016.
//  http://www.andyyhope.com
//  @andyyhope


import Foundation

enum log {
    case ln(_: String)
    case url(_: String)
    case nserror(_: NSError)
    case error(_: Error)
    case date(_: NSDate)
    case obj(_: AnyObject)
    case any(_: Any)
}



//enum print{
//    
//}


postfix operator /

postfix func / (target: log?) {
    
    guard let target = target else { return }
    
    func log<T>(emoji: String, _ object: T) {
        let strLog:String = String(describing: object);
        
        let str = emoji + " " + strLog;
        
        if(true == OPEN_ICONSOLE){
            iConsole.info("%@", args: getVaList([str as NSString]));
        }else{
            
            print(str);
        }
        
        
    }
    
    switch target {
    case .ln(let line):
        log(emoji:"✏️", line)
        break;
        
    case .url(let url):
        log(emoji:"🌏", url)
        break;
        
    case .error(let error):
        log(emoji:"❗️", error)
        break;
        
    case .nserror(let error):
        log(emoji:"❗️", error)
        break;
        
    case .any(let any):
        log(emoji:"⚪️", any)
        break;
        
    case .obj(let obj):
        log(emoji:"◽️", obj)
        break;
        
    case .date(let date):
        log(emoji:"🕒", date)
        break;
    }
}
