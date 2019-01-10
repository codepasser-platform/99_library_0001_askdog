//
//  HYUserInfoEditListTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/9.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

enum UserInfoCellEditType  : Int {
    case userInfoCellEditType_Picker
    case userInfoCellEditType_Tel
    case userInfoCellEditType_Input
    case userInfoCellEditType_Push
    case userInfoCellEditType_Area

}

class HYUserInfoEditListTableViewCell: UITableViewCell,UITextFieldDelegate {
    
    var editType:UserInfoCellEditType! = .userInfoCellEditType_Input;
    var cornerType:CornerMaskType = CornerMaskType.cornerMaskType_None;
    
//    var ty:UserInfoCellEditType {
//        get {
//            return self.ty;
//        }
//        
//        set{
//            self.ty = newValue;
//        }
//    }
    
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var lblEditTitle: UILabel!
    @IBOutlet weak var lblEditInput: UITextField!
    @IBOutlet weak var lblEditContent: UILabel!
    @IBOutlet weak var lblPushTipText: UILabel!
    @IBOutlet weak var lblPushArrow: UIImageView!
    var masklayer:CAShapeLayer!;
    var cellIndex:Int!
    var cellSection:Int!
    
    override func layoutSubviews() {
        super.layoutSubviews();
        
        
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
//        bkView.layer.mask = masklayer;
        
        
    }
    
    func setContentValue(_ obj:AnyObject!) -> Void {
        if(1 == cellIndex){
            CommonTools.stringCheckNullObject(obj, callBack: {
                (str:String) -> Void in
                self.lblEditContent.text = str;
//                if(str == "男"){
//                    Global.sharedInstance.userInfo?.sex = "male";
//                }else if(str == "女"){
//                    Global.sharedInstance.userInfo?.sex = "female";
//                }
            });
        }else if(6 == cellIndex) {
            CommonTools.dicCheckNullObject(obj, callBack: {
                (dic:NSDictionary) -> Void in
                var province:String = "";
                var city:String = "";
                CommonTools.stringCheckNullObject(dic["province"], callBack: {
                    (str:String) -> Void in
                    province = str;
                });
                CommonTools.stringCheckNullObject(dic["city"], callBack: {
                    (str:String) -> Void in
                    city = str;
                });
                self.lblEditContent.text = "\(province) \(city)";
                //Global.sharedInstance.userInfo?.area = dic;
            });
        }
       
    }
    
    func setCellEditType(_ type:UserInfoCellEditType){
        
        self.editType = type;
        
        if(type == UserInfoCellEditType.userInfoCellEditType_Picker || type == UserInfoCellEditType.userInfoCellEditType_Area){
            lblEditContent.isHidden = false;
            lblEditInput.isHidden = true;
            lblEditInput.isEnabled = false;
            lblPushArrow.isHidden = true;
            lblPushTipText.isHidden = true;
        }else if(type == UserInfoCellEditType.userInfoCellEditType_Tel || type == UserInfoCellEditType.userInfoCellEditType_Input){
            lblEditContent.isHidden = true;
            lblEditInput.isHidden = false;
            lblEditInput.isEnabled = true;
            lblPushArrow.isHidden = true;
            lblPushTipText.isHidden = true;
        }else{
            //push
            
            lblEditContent.isHidden = false;
            lblEditInput.isHidden = true;
            lblEditInput.isEnabled = false;
            lblPushArrow.isHidden = false;
            lblPushTipText.isHidden = false;
        }
    }
    
    func setCellIndexValue(_ index:Int,section:Int) -> Void {
        cellIndex = index;
        cellSection = section;
        
        lblEditTitle.text = "";
        lblEditInput.text = "";
        lblPushArrow.isHidden = true;
        lblPushTipText.text = "";
        lblEditContent.text = "";
        
        if(1 == cellSection){
            switch cellIndex {
            case 0:
                lblEditTitle.text = "姓名";
                lblEditContent.isHidden = true;
                lblEditInput.placeholder = "请输入姓名";
                lblEditInput.isHidden = false;
                lblEditInput.isEnabled = true;
                lblEditInput.keyboardType = .default;
                lblEditInput.clearButtonMode = .whileEditing;
                var name:String = "";
                if let obj = Global.sharedInstance.userInfo?.name{
                    name = obj;
                }
                lblEditInput.text = name;
                editType = UserInfoCellEditType.userInfoCellEditType_Input;
            case 1:
                lblEditTitle.text = "性别";
                var sex:String = "";
                if let obj = Global.sharedInstance.userInfo?.gender{
                    let s = obj;
                    if(s == "MALE"){
                        sex = "男";
                    }else if(s == "FEMALE"){
                        sex = "女";
                    }else{
                        sex = "未匹配";
                    }
                }
                
                lblEditContent.isHidden = false;
                lblEditInput.isHidden = true;
                lblEditInput.isEnabled = false;
                
                lblEditContent.text = sex;
                editType = UserInfoCellEditType.userInfoCellEditType_Picker;
            case 2:
                lblEditTitle.text = "手机号";
                var tel:String = "";
                if let obj = Global.sharedInstance.userInfo?.phone{
                    tel = obj;
                }
                lblEditInput.placeholder = "请输入手机号";
                lblEditContent.isHidden = true;
                lblEditInput.isHidden = false;
                lblEditInput.isEnabled = true;
                lblEditInput.keyboardType = .phonePad;
                lblEditInput.clearButtonMode = .whileEditing;
                
                lblEditInput.text = tel;
                editType = UserInfoCellEditType.userInfoCellEditType_Input;
            case 3:
                lblEditTitle.text = "职业";
                lblEditInput.placeholder = "请输入职业";
                lblEditContent.isHidden = true;
                lblEditInput.isHidden = false;
                lblEditInput.isEnabled = true;
                lblEditInput.keyboardType = .default;
                lblEditInput.clearButtonMode = .whileEditing;
                var job:String = "";
                if let obj = Global.sharedInstance.userInfo?.occupation{
                    job = obj;
                }
                lblEditInput.text = job;
                editType = UserInfoCellEditType.userInfoCellEditType_Input;
            case 4:
                lblEditTitle.text = "学校";
                lblEditInput.placeholder = "请输入学校";
                lblEditContent.isHidden = true;
                lblEditInput.isHidden = false;
                lblEditInput.isEnabled = true;
                lblEditInput.keyboardType = .default;
                lblEditInput.clearButtonMode = .whileEditing;
                var school:String = "";
                if let obj = Global.sharedInstance.userInfo?.school{
                    school = obj;
                }
                lblEditInput.text = school;
                editType = UserInfoCellEditType.userInfoCellEditType_Input;
            case 5:
                lblEditTitle.text = "专业";
                lblEditInput.placeholder = "请输入专业";
                lblEditContent.isHidden = true;
                lblEditInput.isHidden = false;
                lblEditInput.isEnabled = true;
                lblEditInput.keyboardType = .default;
                lblEditInput.clearButtonMode = .whileEditing;
                var zhuanye:String = "";
                if let obj = Global.sharedInstance.userInfo?.major{
                    zhuanye = obj;
                }
                lblEditInput.text = zhuanye;
                editType = UserInfoCellEditType.userInfoCellEditType_Input;
            case 6:
                lblEditTitle.text = "所在地区";
                
                lblEditContent.isHidden = false;
                lblEditInput.isHidden = true;
                lblEditInput.isEnabled = false;
                
                var str:String = "";
                var province:String = "";
                var city:String = "";

                if let obj = Global.sharedInstance.userInfo?.address{
                    let address:HYUserInfoAddressDataModel = obj as HYUserInfoAddressDataModel;
                    
                    if let p = address.province{
                        province = p;
                    }
                    if let c = address.city{
                        city = c;
                    }
                }
                
                str = "\(province) \(city)";
                lblEditContent.text = str;
                editType = UserInfoCellEditType.userInfoCellEditType_Area;
            default:
                print("default in");
            }
        }else if(2 == cellSection){
            if(0 == cellIndex){
                
                lblEditTitle.text = "个性签名";
                lblPushArrow.isHidden = false;
                var sign:String = "未填写";
                
                if let obj = Global.sharedInstance.userInfo?.signature{
                    if 0 != obj.characters.count {
                        sign = obj;
                    }
                }
                lblPushTipText.text = sign;
            }
        }
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        masklayer = CAShapeLayer();
        lblEditTitle.text = "";
        lblEditInput.text = "";
        lblPushArrow.isHidden = true;
        lblPushTipText.text = "";
        lblEditContent.text = "";
        
//        bkView.layer.shadowColor = UIColor.black.cgColor;
//        bkView.layer.shadowOffset = CGSize(width: 3, height: 3);
//        bkView.layer.shadowOpacity = 0.4;
//        bkView.layer.shadowRadius = 3;
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    //MARK:UITextFieldDelegate
    
    
}
