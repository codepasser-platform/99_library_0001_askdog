//
//  HYMutableAttributedString.swift
//  AskDog
//
//  Created by 赵德杨 on 16/9/3.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

extension NSMutableAttributedString {
    //墨蓝
    var moranString: NSMutableAttributedString {
        
        let moranColor = UIColor(red: 70/255, green: 103/255, blue: 132/255, alpha: 1)
        self .addAttribute(NSForegroundColorAttributeName,
                           value: moranColor,
                           range: NSMakeRange(0, string.characters.count))
        return self
    }
    
    //灰色
    var grayString: NSMutableAttributedString {
        
        let grayColor = UIColor(red: 135/255, green: 146/255, blue: 164/255, alpha: 1)
        self .addAttribute(NSForegroundColorAttributeName,
                           value:grayColor,
                           range: NSMakeRange(0, string.characters.count))
        return self
    }
}



class HYMutableAttributedString: NSObject {

    lazy var rangeArr: NSMutableArray = {return NSMutableArray()}()

    internal func markString(_ string:NSString) -> NSMutableAttributedString {
        
        var attributed = NSMutableAttributedString(string: string as String).moranString
        let range = string .range(of: "<mark>") as NSRange
        
        if range.location != NSNotFound {
            let obj = self.remMark(string)
            attributed = NSMutableAttributedString(string: obj as String ).moranString
            for  range in rangeArr {
                
                if let markRange: NSRange = range as? NSRange {
                    attributed.addAttribute(NSForegroundColorAttributeName, value: UIColor.red, range: markRange)
                }
            }
            rangeArr.removeAllObjects()
        }
        return attributed
    }
    
    fileprivate func remMark(_ string: NSString) -> (NSString) {
        
        var str: NSString = string
        var range1: NSRange = str .range(of: "<mark>") as NSRange
        let range2: NSRange = str .range(of: "</mark>") as NSRange
        let loc = range2.location - range1.location - range1.length
        let range3: NSRange = NSMakeRange(range1.location, loc)
        str = str.replacingCharacters(in: range2, with: "") as NSString
        str = str.replacingCharacters(in: range1, with: "") as NSString
        rangeArr .add(range3)
        
        range1 = str .range(of: "<mark>") as NSRange
        if range1.location != NSNotFound {
            str = remMark(str)
        }
        return (str)
    }
}
