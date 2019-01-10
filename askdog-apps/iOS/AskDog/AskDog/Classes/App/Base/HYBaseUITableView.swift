//
//  HYBaseUITableView.swift
//  AskDog
//
//  Created by Symond on 16/9/13.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYBaseUITableView: UITableView {
    
    var didSelected:Bool = false;

    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    
    func restoreFlag() -> Void{
        self.didSelected = false;
    }
    
    func delayCellClicked(clickedMethod meth:((Void) -> Void)) -> Void {
        if(false == self.didSelected){
            self.didSelected = true;
            meth();
            self.perform(#selector(restoreFlag), with: nil, afterDelay: 1);
        }
    }

}
