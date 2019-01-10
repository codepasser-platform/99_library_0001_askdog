//
//  GlobalData.swift
//  AskDog
//
//  Created by Symond on 16/7/27.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import Foundation
import UIKit

class Global {
    static let sharedInstance = Global();
    fileprivate init(){};
    
    var isLogined:Bool = false;
    var beNeedDoLogin:Bool = false;
    var beNeedReloadHomePage:Bool = false;
    var userInfo:HYUserInfoDataModel?
    let arrSex:[[String:String]] = [["name":"男","value":"male"],["name":"女","value":"famale"]];
    var textContentUrl:String?


}
