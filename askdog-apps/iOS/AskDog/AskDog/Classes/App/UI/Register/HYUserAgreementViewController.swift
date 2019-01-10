//
//  HYUserAgreementViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/5.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

enum UserAgreeMentReturnStyle  : Int {
    case userAgreeMentReturnStyle_dismiss
    case userAgreeMentReturnStyle_pop_animated
    case userAgreeMentReturnStyle_pop_no_animated
}

class HYUserAgreementViewController: HYBaseViewController {

    var returnStyle:UserAgreeMentReturnStyle = .userAgreeMentReturnStyle_dismiss;
    @IBOutlet weak var textViewContent: UITextView!
    var showReturn:Bool = true;
    override func viewDidLoad() {
        stringNavBarTitle = "用户协议";
        super.viewDidLoad()
        textViewContent.text = "";
        
        let fm:FileManager = FileManager.default;
        let path = Bundle.main.path(forResource: "user", ofType: "txt");
        if(fm.fileExists(atPath: path!)){
            let data: Data = fm.contents(atPath: path!)!;
            let strResult = NSString(data: data, encoding: String.Encoding.utf8.rawValue) as? String;
            textViewContent.text = strResult;
        }
        
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func setReturnTyle(_ style:UserAgreeMentReturnStyle){
        if(UserAgreeMentReturnStyle.userAgreeMentReturnStyle_pop_animated == style){
            showReturn = false;
            returnStyle = style;
        }
    }
    
    @IBAction func btnAgreeClicked(_ sender: UIButton) {
        
        
        if(returnStyle == .userAgreeMentReturnStyle_pop_animated){
                    AskDogGetCategoriesListDataRequest().startRequest(viewController:self,completionHandler: {
                        (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                        if(true == beOk){
            
                            var array:[HYCategoryDataModel] = [HYCategoryDataModel]();
            
                            for item in arr!{
                                if let obj:AnyObject = item as AnyObject?{
                                    let dic:NSDictionary = obj as! NSDictionary;
                                    let data:HYCategoryDataModel = HYCategoryDataModel(jsonDic: dic);
                                    array.append(data);
                                }
                            }
            
            
                            let vc:HYDingyuePindaoViewController = HYDingyuePindaoViewController(nibName: "HYDingyuePindaoViewController", bundle: Bundle.main);
                            vc.dataModelArray = array;
                            self.navigationController?.pushViewController(vc, animated: true);
                        }
                    });
        }else{
            self.dismiss(animated: true, completion: nil);
        }
    }
    
    override func btnReturnClicked(_ sender: UIButton) {
        self.dismiss(animated: true, completion: nil);
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    override func isShowReturnBtn() -> Bool {
        return showReturn;
    }
    
    override func isShowLoginBtn() -> Bool {
        return false;
    }
    
    override func isShowNotifyBtn() -> Bool {
        return false;
    }

}
