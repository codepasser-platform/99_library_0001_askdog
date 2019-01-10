//
//  HYReportExperienceViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/25.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYReportExperienceViewController: HYBaseViewController,HYReportTypeTableViewCellDelegate,HYReportDetailTableViewCellDelegate {
    
    
    @IBOutlet weak var tableView: UITableView!
    
    var menuDataArray:[[String:AnyObject]] = [[String:AnyObject]]();
    var experiencesId:String!

    override func viewDidLoad() {
        stringNavBarTitle = "举报";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let btnReport:UIButton = UIButton(type: UIButtonType.custom);
        btnReport.frame = CGRect.zero;
        btnReport.setTitle("提交", for: UIControlState());
        btnReport.titleLabel?.textColor = Color_WhiteTitle;
        btnReport.titleLabel?.font = UIFont.systemFont(ofSize: 13);
        btnReport.titleEdgeInsets = UIEdgeInsetsMake(3, 0, -3, 0);
        btnReport.addTarget(self, action: #selector(btnReportClicked), for: UIControlEvents.touchUpInside);
        btnReport.backgroundColor = UIColor.clear;
        btnReport.translatesAutoresizingMaskIntoConstraints = false;
        navBarView.addSubview(btnReport);
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["btnReport"] = btnReport;
        
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnReport(50)]-10-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-20-[btnReport]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        
        self.menuDataArray = [["title":"我要举报该经验的理由是" as AnyObject,"type":0 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"" as AnyObject],
        ["title":"违法内容" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"BREAK_LAW" as AnyObject],
        ["title":"违规内容" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"BREAK_RULE" as AnyObject],
        ["title":"语言攻击" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"VERBAL_ATTACK" as AnyObject],
        ["title":"广告" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"ADVERTISEMENT" as AnyObject],
        ["title":"无关内容" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"UNRELATED" as AnyObject],
        ["title":"其他" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"OTHER" as AnyObject],
        ["title":"详情" as AnyObject,"type":2 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"" as AnyObject]];
        
        self.menuDataArray = [["title":"违法内容" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"BREAK_LAW" as AnyObject],
                              ["title":"违规内容" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"BREAK_RULE" as AnyObject],
                              ["title":"语言攻击" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"VERBAL_ATTACK" as AnyObject],
                              ["title":"广告" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"ADVERTISEMENT" as AnyObject],
                              ["title":"无关内容" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"UNRELATED" as AnyObject],
                              ["title":"其他" as AnyObject,"type":1 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"OTHER" as AnyObject],
                              ["title":"详情" as AnyObject,"type":2 as AnyObject,"boolValue":false as AnyObject,"value":"" as AnyObject,"key":"" as AnyObject]];

        
        
        let cellID1:String = "HYReportTypeTableViewCell";
        let nib1:UINib = UINib(nibName: "HYReportTypeTableViewCell", bundle: nil);
        tableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        let cellID2:String = "HYReportDetailTableViewCell";
        let nib2:UINib = UINib(nibName: "HYReportDetailTableViewCell", bundle: nil);
        tableView.register(nib2, forCellReuseIdentifier: cellID2);
        
        
        
        if tableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tableView.separatorInset = UIEdgeInsets.zero;
        }
        if tableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tableView.layoutMargins = UIEdgeInsets.zero;
        }
    }
    
    func getReporyCategory() -> String! {
        
        var resultString:String = "";
        
        for item in self.menuDataArray{
            var dic:[String:AnyObject] = item as [String:AnyObject];
            let be:Bool = dic["boolValue"] as! Bool;
            if(true == be){
                if let obj = dic["key"]{
                    resultString = obj as! String;
                    break;
                }
            }
        }
        return resultString;
    }
    
    func btnReportClicked() -> Void {
        
        let n = self.menuDataArray.count - 1;
        
        let cell:HYReportDetailTableViewCell = self.tableView.cellForRow(at: IndexPath(row: n, section: 0)) as! HYReportDetailTableViewCell;
        cell.textViewDetail.resignFirstResponder();
        
        if let id:String = self.experiencesId{
            //先获取选中的举报类别
            let result = self.getReporyCategory();

            if("" == result){
                CommonTools.showMessage("请选择一个举报理由!", vc: self, hr: {
                    () -> Void in
                    
                });
                
                return;
            }
            
            let d = self.menuDataArray[n];
            
            
            var strContent:String = "";
            if let detailObj = d["value"]{
                strContent = detailObj as! String;
            }
            
            var strRestult:String = "";
            if let str:String = result{
                strRestult = str;
            }
            
            let dic:[String: AnyObject]? = ["type":strRestult as AnyObject,"message":strContent as AnyObject];
            let strParmr:String = "/\(id)/report";
            print("share id = \(strParmr)");
            
            AskDogReportExperienceDataRequest().startRequest(requestParmer:dic,exUrl: strParmr,viewController: self, completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    print("经验举报成功 id = \(id)");
                    
                    CommonTools.showMessage("经验举报成功!", vc: self, hr: {
                        () -> Void in
                        _ = self.navigationController?.popViewController(animated: true);
                    });
                    
                    
                }
            });

        }
        

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func selectAtIndex(_ index:Int) -> Void {
        
        var needRefreshIndexArray:[Int] = [];
        
        for (ins,value) in self.menuDataArray.enumerated(){
            var dic:[String:AnyObject] = value as [String:AnyObject];
            let be:Bool = dic["boolValue"] as! Bool;
            
            if(ins == index){
                //先判断是不是已经选中
                if(true == be){
                    return;
                }else{
                    //记下这个索引  要刷新它
                    needRefreshIndexArray.append(ins);
                    dic["boolValue"] = true as AnyObject?;
                }
            }else{
                if(true == be){
                    dic["boolValue"] = false as AnyObject?;
                    //记下这个索引  要刷新它
                    needRefreshIndexArray.append(ins);
                }
            }
            self.menuDataArray[ins] = dic;
        }
        //refresh table
        var indexs:[IndexPath] = [];
        for item in needRefreshIndexArray {
            indexs.append(IndexPath(row: item, section: 0));
        }
        self.tableView.reloadRows(at: indexs, with: .fade);
    }
    
    //MARK:UITableViewDelegate
    func tableView(_ tableView: UITableView, didSelectRowAtIndexPath indexPath: IndexPath) {
        
        let n = self.menuDataArray.count - 1;
        
        //        if((indexPath as NSIndexPath).row != 0 && (indexPath as NSIndexPath).row != n){
        if((indexPath as NSIndexPath).row != n){
            
            self.selectAtIndex((indexPath as NSIndexPath).row);
            //print("self.menuDataArray = \(self.menuDataArray)");
        }
        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAtIndexPath indexPath: IndexPath) -> CGFloat {
        
        let n = self.menuDataArray.count - 1;
        
        if(n == (indexPath as NSIndexPath).row){
            return 120;
        }
        return 50;
        
    }
    
    //解决分割线不对齐问题
    func tableView(_ tableView: UITableView, willDisplayCell cell: UITableViewCell, forRowAtIndexPath indexPath: IndexPath) {
        if cell.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            cell.separatorInset = UIEdgeInsets.zero;
        }
        if cell.responds(to: #selector(setter: UIView.layoutMargins)){
            cell.layoutMargins = UIEdgeInsets.zero;
        }
    }
    
    func numberOfSectionsInTableView(_ tableView: UITableView) -> Int {
        return 1;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return menuDataArray.count;
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAtIndexPath indexPath: IndexPath) -> UITableViewCell {
        
        var cellID:String! = "";
        
        let n = self.menuDataArray.count - 1;
        
        if(n == (indexPath as NSIndexPath).row){
            cellID = "HYReportDetailTableViewCell";
        }else{
            cellID = "HYReportTypeTableViewCell";
        }
        
        
        let cell:UITableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID)!;
        
        
        if(n == (indexPath as NSIndexPath).row){
            let temp:HYReportDetailTableViewCell = cell as! HYReportDetailTableViewCell;
            temp.delegate = self;
            temp.cellIndex = indexPath;
            temp.cornerType = CornerMaskType.cornerMaskType_Bottom;
        }else{
            let temp:HYReportTypeTableViewCell = cell as! HYReportTypeTableViewCell;
            temp.delegate = self;
            temp.cellIndex = indexPath;
//            if(0 == (indexPath as NSIndexPath).row){
//                temp.cornerType = CornerMaskType.cornerMaskType_Top;
//            }
            
            let d:[String:AnyObject] = self.menuDataArray[(indexPath as NSIndexPath).row];
            temp.setData(d);
        }
        
        
        
 
        return cell;
    }

    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    //MARK:HYReportTypeTableViewCellDelegate
    func hyReportTypeTableViewCellReportBtnClicked(cell ce: HYReportTypeTableViewCell, indexPath index: IndexPath) {
        let n = self.menuDataArray.count - 1;
        //            if((index as NSIndexPath).row != 0 && (index as NSIndexPath).row != n){

        if((index as NSIndexPath).row != n){

            self.selectAtIndex((index as NSIndexPath).row);
            //print("self.menuDataArray = \(self.menuDataArray)");
        }
    }
    
    //MARK:HYReportDetailTableViewCellDelegate
    func hyReportDetailTableViewCellReportDidEndEditing(cell ce: HYReportDetailTableViewCell, indexPath index: IndexPath, editString str: String) {
        print("detail value change to \(str)");
        let n = self.menuDataArray.count - 1;
        var dic = self.menuDataArray[n];
        dic["value"] = str as AnyObject?;
        self.menuDataArray[n] = dic;
    }
    
    override func isShowLoginBtn() -> Bool {
        return false
    }

}
