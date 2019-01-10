//
//  HYVideoSharePriceTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/22.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYVideoSharePriceTableViewCellDelegate  :class{
    func hyVideoSharePriceTableViewCellDelegateSwitchValueChanged(cell ce:HYVideoSharePriceTableViewCell,inexPath index:IndexPath,value be:Bool) -> Void ;
    func hyVideoSharePriceTableViewCellDelegateTextFieldEdited(cell ce:HYVideoSharePriceTableViewCell,inexPath index:IndexPath,editText str:String) -> Void ;
    func hyVideoSharePriceTableViewCellDelegateTextFieldDidBeginEdit(cell ce:HYVideoSharePriceTableViewCell,inexPath index:IndexPath) -> Void ;
    func hyVideoSharePriceTableViewCellDelegateRadioBtnClicked(cell ce:HYVideoSharePriceTableViewCell,inexPath index:IndexPath) -> Void ;

}

class HYVideoSharePriceTableViewCell: UITableViewCell,UITextFieldDelegate {
    
    var cornerType:CornerMaskType = CornerMaskType.cornerMaskType_None;
    //var masklayer:CAShapeLayer!;
    
    @IBOutlet weak var bkView: UIView!
    var cellIndex:IndexPath!
    
    @IBOutlet weak var switchPrice: UISwitch!
    var dataDic:[String:AnyObject]!
    @IBOutlet weak var lblTitle: UILabel!
    
    @IBOutlet weak var textFieldPrice: UITextField!
    @IBOutlet weak var btnRadio: UIButton!
    
    weak var delegate:HYVideoSharePriceTableViewCellDelegate?
    
    
//    menuDataArray = [["title":"视频费用","type":0,"boolValue":false,"value":"0"],
//    ["title":"1元","type":1,"boolValue":false,"value":"0"],
//    ["title":"10元","type":1,"boolValue":false,"value":"0"],
//    ["title":"自定义价格","type":2,"boolValue":false,"value":"0"]];
    
    @IBAction func btnClicked(_ sender: UIButton) {
        delegate?.hyVideoSharePriceTableViewCellDelegateRadioBtnClicked(cell: self, inexPath: cellIndex);
    }
    @IBAction func valueChanged(_ sender: UISwitch) {
        delegate?.hyVideoSharePriceTableViewCellDelegateSwitchValueChanged(cell: self, inexPath: cellIndex, value: self.switchPrice.isOn);
    }
    
    func setData(_ dic:[String:AnyObject]){
        dataDic = dic;
        
        self.textFieldPrice.isHidden = true;
        self.switchPrice.isHidden = true;
        self.btnRadio.isHidden = true;
        
        CommonTools.stringCheckNullObject(dataDic["title"], callBack: {
            (str:String) -> Void in
            self.lblTitle.text = str;
            
        });
        
        CommonTools.intCheckNullObject(dataDic["type"], callBack: {
            (n:Int) -> Void in
            
            if(0 == n){
                self.switchPrice.isHidden = false;
                CommonTools.boolCheckNullObject(self.dataDic["boolValue"], callBack: {
                    (be:Bool) -> Void in
                    self.switchPrice.isOn = be;
                });
                
                
            }else if(1 == n){
                self.btnRadio.isHidden = false;
                CommonTools.boolCheckNullObject(self.dataDic["boolValue"], callBack: {
                    (be:Bool) -> Void in
                    self.btnRadio.isSelected = be;
                });
                
            }else{
                self.textFieldPrice.isHidden = false;
                CommonTools.stringCheckNullObject(self.dataDic["value"], callBack: {
                    (str:String) -> Void in
                    self.textFieldPrice.text = str;
                    
                });
            }
            
        });
    }
    
    override func layoutSubviews() {
        super.layoutSubviews();
        
//        
//        masklayer.removeFromSuperlayer();
//        
//        let re:CGRect = CGRect(x: 0, y: 0, width: self.bounds.size.width-8, height: self.bounds.size.height);
//        
//        var corner:UIRectCorner = [UIRectCorner.topLeft,UIRectCorner.topRight];
//        var size:CGSize = CGSize(width: 0, height: 0);
//        
//        if(cornerType == CornerMaskType.cornerMaskType_Top){
//            corner = [UIRectCorner.topLeft,UIRectCorner.topRight];
//            size = CGSize(width: 5, height: 5);
//        }else if(cornerType == CornerMaskType.cornerMaskType_Bottom){
//            corner = [UIRectCorner.bottomLeft,UIRectCorner.bottomRight];
//            size = CGSize(width: 5, height: 5);
//            
//            self.layer.shadowColor = UIColor.black.cgColor;
//            self.layer.shadowOffset = CGSize(width: 3, height: 2);
//            self.layer.shadowOpacity = 0.3;
//            self.layer.shadowRadius = 3;
//            
//        }else if(cornerType == CornerMaskType.cornerMaskType_All){
//            corner = UIRectCorner.allCorners;
//            size = CGSize(width: 5, height: 5);
//            
//            self.layer.shadowColor = UIColor.black.cgColor;
//            self.layer.shadowOffset = CGSize(width: 3, height: 2);
//            self.layer.shadowOpacity = 0.3;
//            self.layer.shadowRadius = 3;
//            
//        }else{
//            corner = UIRectCorner.allCorners;
//        }
//        
//        let bezierPath:UIBezierPath = UIBezierPath(roundedRect: re, byRoundingCorners: corner, cornerRadii: size);
//        masklayer.frame = re;
//        masklayer.path = bezierPath.cgPath;
//        
//        bkView.layer.mask = masklayer;
        
        
    }
    


    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
      //  masklayer = CAShapeLayer();
  
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    //MARK:UITextFieldDelegate
    func textFieldDidBeginEditing(_ textField: UITextField) {
        delegate?.hyVideoSharePriceTableViewCellDelegateTextFieldDidBeginEdit(cell: self, inexPath: cellIndex);
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        delegate?.hyVideoSharePriceTableViewCellDelegateTextFieldEdited(cell: self, inexPath: cellIndex, editText: self.textFieldPrice.text!);
    }
    
}
