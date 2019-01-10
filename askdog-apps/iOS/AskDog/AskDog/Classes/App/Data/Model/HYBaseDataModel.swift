//
//  HYBaseDataModel.swift
//  AskDog
//
//  Created by Symond on 16/8/4.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

public protocol HYBaseDataModelProtocol {
    //func dataProcess(_ dic:NSDictionary!) -> Void;
    init(jsonDic dic: NSDictionary);
}

class HYBaseDataModel: NSObject,HYBaseDataModelProtocol {
    
    override internal init(){
        
    }
    
    required init(jsonDic dic: NSDictionary){
        super.init();
        self.dataProcess(dic);
    }
    
    func dataProcess(_ dic:NSDictionary!) -> Void {
        
    }

}
