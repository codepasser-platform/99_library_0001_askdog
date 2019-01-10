//
//  HYNofityListTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/30.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

protocol HYNofityListTableViewCellDelegate:class {
    func hyNofityListTableViewCellDelegateLinkClicked(cell ce:HYNofityListTableViewCell,indexPath index:IndexPath,data da:HYNotifyGroupDataContentUserDataModel) -> Void ;
}

class HYNofityListTableViewCell: UITableViewCell,UITextViewDelegate {
    
    
    let NOTIFY_TYPE_CODE_DIC:[String:String] = ["CREATE_EXPERIENCE_COMMENT":"评论了经验",
                                                "DELETE_EXPERIENCE_COMMENT":"删除了评论",
                                            "CREATE_EXPERIENCE_COMMENT_COMMENT":"回复了评论"];
    
    var dataModel:HYNotifyGroupDataDataModel?
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var textViewContent: UITextView!
    @IBOutlet weak var lblLine: UILabel!
    
    weak var delegate:HYNofityListTableViewCellDelegate?
    
    var cornerType:CornerMaskType = CornerMaskType.cornerMaskType_None;
    var masklayer:CAShapeLayer!;
    var indexPath:IndexPath!
    
    let linkUrl:String = "goUserPage";
    
    
    func setData(_ da:HYNotifyGroupDataDataModel) -> Void {
        dataModel = da;
        
        if let d:HYNotifyGroupDataDataModel = self.dataModel{
            self.textViewContent.text = d.content?.user?.name;
            
            if let ct:HYNotifyGroupDataContentDataModel = d.content{
                
                var usname:String = "";
                if let user = ct.user?.name{
                    usname = user;
                }
                
                var action:String = "";
                if let tp = ct.type{
                    action = NOTIFY_TYPE_CODE_DIC[tp]!;
                }
                
                var des:String = "";
                if let de = ct.target?.Description{
                    des = de;
                }
                
                var userid:String = "";
                if let id = ct.user?.id{
                    userid = "\(linkUrl):\(id)";
                }
                
                //用富文本的方式
                let str:String = "\(usname)\(action)\(des)";
                let attrString:NSMutableAttributedString = NSMutableAttributedString(string: str);
                
                let rangeName = str.range(of: "\(usname)");
                let locName:Int = str.characters.distance(from: str.startIndex, to: (rangeName?.lowerBound)!);
                let distanceName:Int = str.characters.distance(from: (rangeName?.lowerBound)!, to: (rangeName?.upperBound)!);
               // let distanceName:Int = (<#T##String.CharacterView corresponding to your index##String.CharacterView#>.distance(from: (rangeName?.lowerBound)!, to: (rangeName?.upperBound)!));
                // print("\(range?.startIndex)  \(range?.endIndex)  \(distance)  \(loc)");
                let rName:NSRange = NSMakeRange(locName, distanceName);
               // attrString.addAttributes([NSForegroundColorAttributeName:CommonTools.rgbColor(red: 57, green: 78, blue: 97)], range: rName);
                attrString.addAttributes([NSLinkAttributeName:URL(string: userid)!], range: rName);
                
                
                let rangeType = str.range(of: "\(action)");
                let locType:Int = str.characters.distance(from: str.startIndex, to: (rangeType?.lowerBound)!);
                let distanceType:Int = str.characters.distance(from: (rangeType?.lowerBound)!, to: (rangeType?.upperBound)!);
               // let distanceType:Int = (<#T##String.CharacterView corresponding to your index##String.CharacterView#>.distance(from: (rangeType?.lowerBound)!, to: (rangeType?.upperBound)!));
                // print("\(range?.startIndex)  \(range?.endIndex)  \(distance)  \(loc)");
                let rType:NSRange = NSMakeRange(locType, distanceType);
                attrString.addAttributes([NSForegroundColorAttributeName:UIColor.lightGray], range: rType);
                
                self.textViewContent.attributedText = attrString;
                self.textViewContent.linkTextAttributes = [NSForegroundColorAttributeName:CommonTools.rgbColor(red: 57, green: 78, blue: 97)];

            }
            
        }
        
    }
    
    override func layoutSubviews() {
        super.layoutSubviews();
        
        
        masklayer.removeFromSuperlayer();
        
        let re:CGRect = CGRect(x: 0, y: 0, width: self.bounds.size.width-8, height: self.bounds.size.height);
        
        var corner:UIRectCorner = [UIRectCorner.topLeft,UIRectCorner.topRight];
        var size:CGSize = CGSize(width: 0, height: 0);
        
        if(cornerType == CornerMaskType.cornerMaskType_Top){
            corner = [UIRectCorner.topLeft,UIRectCorner.topRight];
            size = CGSize(width: 5, height: 5);
        }else if(cornerType == CornerMaskType.cornerMaskType_Bottom){
            corner = [UIRectCorner.bottomLeft,UIRectCorner.bottomRight];
            size = CGSize(width: 5, height: 5);
            
//            self.layer.shadowColor = UIColor.blackColor().CGColor;
//            self.layer.shadowOffset = CGSizeMake(0, 1);
//            self.layer.shadowOpacity = 0.3;
//            self.layer.shadowRadius = 3;
            
        }else if(cornerType == CornerMaskType.cornerMaskType_All){
            corner = UIRectCorner.allCorners;
            size = CGSize(width: 5, height: 5);
            
//            self.layer.shadowColor = UIColor.blackColor().CGColor;
//            self.layer.shadowOffset = CGSizeMake(3, 2);
//            self.layer.shadowOpacity = 0.3;
//            self.layer.shadowRadius = 3;
            
        }else if (cornerType == CornerMaskType.cornerMaskType_None){
            corner = UIRectCorner.allCorners;
        }else{
//            self.layer.shadowColor = UIColor.blackColor().CGColor;
//            self.layer.shadowOffset = CGSizeMake(0, -1);
//            self.layer.shadowOpacity = 0.3;
//            self.layer.shadowRadius = 3;
            corner = UIRectCorner.allCorners;
        }
        
        let bezierPath:UIBezierPath = UIBezierPath(roundedRect: re, byRoundingCorners: corner, cornerRadii: size);
        masklayer.frame = re;
        masklayer.path = bezierPath.cgPath;
        
        bkView.layer.mask = masklayer;
        
        
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        masklayer = CAShapeLayer();
        
        self.textViewContent.delegate = self;
        self.textViewContent.text = "";
        self.textViewContent.textContainer.lineFragmentPadding = 0;
        self.textViewContent.textContainerInset = UIEdgeInsetsMake(0, 0, 0, 0);
        
        self.textViewContent.addObserver(self, forKeyPath: "contentSize", options: NSKeyValueObservingOptions.new, context: nil);
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    //MARK:UITextViewDelegate
    func textView(_ textView: UITextView, shouldInteractWith URL: URL, in characterRange: NSRange) -> Bool{
      // print("shouldInteractWithURL \(URL.resourceSpecifier) \(URL.scheme)");
        
        if(URL.scheme == linkUrl){
            if let temp:HYNotifyGroupDataContentUserDataModel = dataModel?.content?.user{
                delegate?.hyNofityListTableViewCellDelegateLinkClicked(cell: self, indexPath: self.indexPath, data: temp);
            }
            
        }
        
        return false;
    }
    
    override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
       // print("observeValueForKeyPath in ");
        let textView:UITextView = object as! UITextView;
        var topCorrect:CGFloat = (textView.bounds.size.height - textView.contentSize.height * textView.zoomScale) / 2;
        topCorrect = (topCorrect < 0.0 ? 0.0 : topCorrect);
        textView.contentOffset = CGPoint(x: 0, y: 0 - topCorrect);
    }
    
    deinit{
        self.textViewContent.removeObserver(self, forKeyPath: "contentSize");
    }
    
}
