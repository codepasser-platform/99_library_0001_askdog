//
//  HYInputViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/10.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
fileprivate func < <T : Comparable>(lhs: T?, rhs: T?) -> Bool {
  switch (lhs, rhs) {
  case let (l?, r?):
    return l < r
  case (nil, _?):
    return true
  default:
    return false
  }
}


typealias inputCallBack = (_ beSave:Bool,_ strContent:String)->Void;

class HYInputViewController: HYBaseViewController, HYLimitTextDelegate , UITextViewDelegate{

    var callBackFun:inputCallBack?
    var contentString:String?
    var titleString:String?
    var labCount: UILabel?
    let limit = HYLimitText()

    @IBOutlet weak var textViewInput: PlaceholderTextView!
    override func viewDidLoad() {
        
        stringNavBarTitle = titleString;
        
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let btnSave:UIButton = UIButton(type: UIButtonType.custom);
        btnSave.frame = CGRect.zero;
        btnSave.setTitle("保存", for: UIControlState());
        btnSave.titleLabel?.textColor = Color_greyInputText;
        btnSave.titleLabel?.font = UIFont.systemFont(ofSize: 13);
        btnSave.titleEdgeInsets = UIEdgeInsetsMake(3, 0, -3, 0);
        btnSave.addTarget(self, action: #selector(btnSaveClicked), for: UIControlEvents.touchUpInside);
        btnSave.backgroundColor = UIColor.clear;
        btnSave.translatesAutoresizingMaskIntoConstraints = false;
        navBarView.addSubview(btnSave);
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["btnSave"] = btnSave;
        
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnSave(50)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-20-[btnSave]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));

                self.textViewInput.delegate = self
        self.textViewInput.placeholderColor = UIColor(red: 135/255, green: 146/255, blue: 164/255, alpha: 1)
        self.textViewInput.placeholder = "请输入个性签名"
        self.textViewInput.textColor = Color_greyInputText;
        if 0 < contentString?.characters.count {
            self.textViewInput.text = contentString;
        }
        
        limit.delegate = self
        limit.maxLength = Signature_Count  //个性签名字数限制
        limit.addNotTextView(self.textViewInput)
        

    }
    
    func btnSaveClicked(_ sender: UIButton) -> Void {
        print("btn btnSaveClicked clicked");
        self.callBackFun!(true,self.textViewInput.text);
        _ = self.navigationController?.popViewController(animated: true);
    }
    
    override func btnReturnClicked(_ sender: UIButton) {
        self.callBackFun!(false,"");
        _ = self.navigationController?.popViewController(animated: true);
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?,title strTitle:String?,content strContent:String?,callBack:@escaping ((Bool,String) -> Void)) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil);
        
        contentString = strContent;
        titleString = strTitle;
        callBackFun = callBack;
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func limitTextLimitInput(_ textLimitInput: HYLimitText!, text: String!) {
        contentString = text
        print("\(text)")
    }
    
    func limitTextLimitInputOverStop(_ textLimitInput: HYLimitText!) {
        print("超出字数限制")
    }
    
   

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
