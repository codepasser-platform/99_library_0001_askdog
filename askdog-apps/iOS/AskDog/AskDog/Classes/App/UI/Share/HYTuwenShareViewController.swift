//
//  HYTuwenShareViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/18.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import IQKeyboardManager

class HYTuwenShareViewController: HYBaseViewController {

    @IBOutlet weak var tuwenEditView: HYTuwenEditView!
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var textViewBottom: NSLayoutConstraint!
    @IBOutlet weak var tfTitle: UITextField!
    
    
    override func viewDidLoad() {
        stringNavBarTitle = "图文分享";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        self.tuwenEditView.parentVc = self;
        self.tuwenEditView.layer.borderColor = UIColor.lightGray.cgColor;
        self.tuwenEditView.layer.borderWidth = 1;
        
        let btnNext:UIButton = UIButton(type: UIButtonType.system);
        btnNext.frame = CGRect.zero;
        btnNext.setTitle("下一步", for: UIControlState());
        btnNext.titleLabel?.font = UIFont.systemFont(ofSize: 13);
        btnNext.titleEdgeInsets = UIEdgeInsetsMake(3, 0, -3, 0);
        btnNext.addTarget(self, action: #selector(btnNextClicked), for: UIControlEvents.touchUpInside);
        btnNext.backgroundColor = UIColor.clear;
        btnNext.translatesAutoresizingMaskIntoConstraints = false;
        navBarView.addSubview(btnNext);
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["btnNext"] = btnNext;
        
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnNext(50)]-10-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-20-[btnNext]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        self.registerForKeyboardNotifications();
        
        self.tfTitle.becomeFirstResponder();
    }
    
    func btnNextClicked() -> Void {
        print("btnNextClicked");
        
        self.tfTitle.resignFirstResponder();
        self.tuwenEditView.stopEditing();
        
        guard self.tfTitle.text != "" else{
            CommonTools.showMessage("标题不能为空!",  vc: self,  hr: {
                ()->Void in
                self.tfTitle.becomeFirstResponder();
            });
            return;
        }
        
        
        let vc:HYShareSelectCategoryViewController = HYShareSelectCategoryViewController(nibName: "HYShareSelectCategoryViewController", bundle: Bundle.main);
        let result = self.tuwenEditView.getContent();
        
        vc.subject = self.tfTitle.text;
        vc.contentString = result.content;
        vc.linkIds = result.linkIds;
        
        self.navigationController?.pushViewController(vc, animated: true);
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated);
        IQKeyboardManager.shared().isEnableAutoToolbar = false;
        IQKeyboardManager.shared().isEnabled = false;
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillAppear(animated);
        IQKeyboardManager.shared().isEnableAutoToolbar = true;
        IQKeyboardManager.shared().isEnabled = true;
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func registerForKeyboardNotifications() -> Void {
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardDidShowNotification), name: NSNotification.Name.UIKeyboardDidShow, object: nil);
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillHideNotification), name: NSNotification.Name.UIKeyboardWillHide, object: nil);
    }
    
    func keyboardDidShowNotification(_ notify:Notification){
        print("keyboardDidShowNotification in");
        
        let keyboardinfo = (notify as NSNotification).userInfo![UIKeyboardFrameBeginUserInfoKey];
        let keyboardheight:CGFloat = ((keyboardinfo as AnyObject).cgRectValue.size.height)
        
        print("keyboardheight \(keyboardheight)");
        self.textViewBottom.constant = keyboardheight;
    }
    
    func keyboardWillHideNotification(_ notify:Notification){
        print("keyboardWillHideNotification in");
        self.textViewBottom.constant = 8;
    }
    
    deinit{
        
        print("\(self)  removeObserver");
        NotificationCenter.default.removeObserver(self);
    }
    
    override func btnReturnClicked(_ sender: UIButton) {
        if (self.tfTitle.text != "" || self.tuwenEditView.textViewInput.text != ""){
            CommonTools.showModalMessage("您分享的经验尚未发布，返回将失去您编辑的内容", title: "提示", vc: self, hasCancelBtn: true, OKBtnTitle:"离开",hr: {
                (beOK:Bool) -> Void in
                if(true == beOK){
                    self.tfTitle.resignFirstResponder();
                    self.tuwenEditView.stopEditing();
                    
                    _ = self.navigationController?.popViewController(animated: true);
                }
            });
        }else{
            _ = self.navigationController?.popViewController(animated: true);
        }
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
