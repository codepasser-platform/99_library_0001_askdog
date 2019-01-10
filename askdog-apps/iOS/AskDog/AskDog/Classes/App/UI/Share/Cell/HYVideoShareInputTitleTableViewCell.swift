//
//  HYVideoShareInputTitleTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/22.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYVideoShareInputTitleTableViewCell: UITableViewCell,UITextFieldDelegate {
    
    
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var textFiledTitle: UITextField!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
//        bkView.layer.shadowColor = UIColor.black.cgColor;
//        bkView.layer.shadowOffset = CGSize(width: 2, height: 2);
//        bkView.layer.shadowOpacity = 0.3;
//        bkView.layer.shadowRadius = 3;
//        bkView.layer.cornerRadius = 3;
        
//        //活动期间加标题
//        if(true == CommonTools.compareDataInActivity()){
//            
//            textFiledTitle.text = "#2016中秋#";
//        }
        
        
        
//        self.textFiledTitle.addTarget(self, action: #selector(textFieldValueChanged), forControlEvents: .EditingChanged);
        
//        if(true == CommonTools.compareDataInActivity()){
//            self.textFieldValueChanged();
//            NotificationCenter.default.addObserver(self, selector: #selector(textFieldValueChanged), name: NSNotification.Name.UITextFieldTextDidChange, object: nil);
//        }
        
        
    }
    
    func textFieldValueChanged() -> Void {
        print("change to \(textFiledTitle.text)");
        

        
        
        if(nil == self.textFiledTitle.markedTextRange){
            let oldStr:String = self.textFiledTitle.text!;
            let attrString:NSMutableAttributedString = NSMutableAttributedString(string: oldStr);
            
            if let rFind:Range = oldStr.range(of: "#2016中秋#"){
                let locType:Int = oldStr.characters.distance(from: oldStr.startIndex, to: (rFind.lowerBound));
                let distanceType:Int = oldStr.characters.distance(from: (rFind.lowerBound), to: (rFind.upperBound));
               // let distanceType:Int = (<#T##String.CharacterView corresponding to your index##String.CharacterView#>.distance(from: rFind.lowerBound, to: (rFind.upperBound)));
                // print("\(range?.startIndex)  \(range?.endIndex)  \(distance)  \(loc)");
                let rType:NSRange = NSMakeRange(locType, distanceType);
                
                attrString.addAttributes([NSForegroundColorAttributeName:CommonTools.rgbColor(red: 0, green: 170, blue: 239)], range: rType)
                
                textFiledTitle.attributedText = attrString;
            }else{
                let rType:NSRange = NSMakeRange(0, oldStr.characters.count);
                attrString.addAttributes([NSForegroundColorAttributeName:UIColor.black], range: rType)
                textFiledTitle.attributedText = attrString;
            }
        }
        
        


        

        
    }
    
    
    //MARK:UITextFieldDelegate
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
//        let str:String = self.textFiledTitle.text!;
//        
//        print("loc = \(range.location) len:\(range.length)");
        
        
        
        return true;
    }
    


    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    deinit{
        print("HYVideoShareInputTitleTableViewCell");
        
//        if(true == CommonTools.compareDataInActivity()){
//            NotificationCenter.default.removeObserver(self);
//        }
    }
    
}
