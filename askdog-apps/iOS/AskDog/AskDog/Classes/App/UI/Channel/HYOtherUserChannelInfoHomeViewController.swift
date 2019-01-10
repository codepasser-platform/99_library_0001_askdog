//
//  HYOtherUserChannelInfoHomeViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/26.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYOtherUserChannelInfoHomeViewController: HYBaseViewController,UITableViewDelegate,UITableViewDataSource {
    
    var dataModel:HYAuthorDataModel!
    var linkDataModel:HYNotifyGroupDataContentUserDataModel!
    
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var vipImgV: UIImageView!
    @IBOutlet weak var headerImgV: UIImageView!
    @IBOutlet weak var lblName: UILabel!
    @IBOutlet weak var lblSign: UILabel!
    @IBOutlet weak var imgBkViewWith: NSLayoutConstraint!
    @IBOutlet weak var lblCenterLine: UILabel!
    
    @IBOutlet weak var btnLeft: UIButton!
    @IBOutlet weak var btnRight: UIButton!
    @IBOutlet weak var lblLeftTitle: UILabel!
    @IBOutlet weak var lblRightTitle: UILabel!
    @IBOutlet weak var lblLeftLine: UILabel!
    @IBOutlet weak var lblRightLine: UILabel!
    @IBOutlet weak var leftView: UIView!
    @IBOutlet weak var rightView: UIView!
    @IBOutlet weak var leftTableView: HYBaseUITableView!
    @IBOutlet weak var rightTableView: HYBaseUITableView!
    
    var loadType:Int = 1;
    
    
    var dataArrarLeft:[HYChannelListDataModel] = [];
    var dataArrarRight:[HYChannelListDataModel] = [];
    var curShowIndex:Int = -1;
    
    
    @IBAction func btnLeftClicked(_ sender: UIButton) {
        self.setSelectIndex(0);
    }
    
    @IBAction func btnRightClicked(_ sender: UIButton) {
        self.setSelectIndex(1);
    }
    
    //type1 正常加载  type2 是从点击超连接名字跳进来的 需要另取一下用户头像
    init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?, data da: AnyObject?,loadType ty:Int) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil, data: da);
        
        self.loadType = ty;
        
        if(1 == self.loadType){
            dataModel = da as? HYAuthorDataModel;
        }else{
            self.linkDataModel = da as? HYNotifyGroupDataContentUserDataModel;
        }
        
        

    }
    
    func setSelectIndex(_ index:Int) -> Void {
        curShowIndex  = index;
        if(0 == index){
            self.lblLeftLine.isHidden = false;
            self.lblLeftTitle.textColor = UIColor.black;
            
            self.lblRightLine.isHidden = true;
            self.lblRightTitle.textColor = UIColor.lightGray;
            
            self.leftView.isHidden = false;
            self.rightView.isHidden = true;
        }else{
            self.lblLeftLine.isHidden = true;
            self.lblLeftTitle.textColor = UIColor.lightGray;
            
            self.lblRightLine.isHidden = false;
            self.lblRightTitle.textColor = UIColor.black;
            
            self.leftView.isHidden = true;
            self.rightView.isHidden = false;
        }
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        
        lblLeftTitle.text = "频道";
        lblRightTitle.text = "订阅频道";
        
        if (1 == self.loadType){
            if let name = dataModel.name{
                stringNavBarTitle = name;
                
                lblLeftTitle.text = "\(name)的频道";
                self.lblName.text = name;
                
            }
            if let sign = dataModel.signature{
                lblSign.text = sign;
            }
            if let be:Bool = dataModel.vip{
                self.vipImgV.isHidden = !be;
            }
        }else{
            if let name = linkDataModel.name{
                stringNavBarTitle = name;
                
                lblLeftTitle.text = "\(name)的频道";
                self.lblName.text = name;
                
            }
            
            //FIXME: vip 如何判断
            self.vipImgV.isHidden = true;
            
            lblSign.text = "";
        }
        

        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        headerImgV.layer.cornerRadius = (imgBkViewWith.constant - 10)/2.0;
        headerImgV.layer.masksToBounds = true;
        

        
        if(1 == self.loadType){
            if let url:String = dataModel.avatar{
                headerImgV.sd_setImage(with: URL(string: url),placeholderImage: UIImage(named: "AvatarDefault"));
            }
        }
 
        lblCenterLine.isHidden = true;
    
        bkView.layer.shadowColor = UIColor.black.cgColor;
        bkView.layer.shadowOffset = CGSize(width: 2, height: 2);
        bkView.layer.shadowOpacity = 0.3;
        bkView.layer.shadowRadius = 3;
        bkView.layer.cornerRadius = 3;
        
        let cellID1:String = "HYDingyueChannelListTableViewCell1";
        let nib1:UINib = UINib(nibName: "HYDingyueChannelListTableViewCell", bundle: nil);
        leftTableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        let cellID2:String = "HYDingyueChannelListTableViewCell2";
        let nib2:UINib = UINib(nibName: "HYDingyueChannelListTableViewCell", bundle: nil);
        rightTableView.register(nib2, forCellReuseIdentifier: cellID2);
        
        if leftTableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            leftTableView.separatorInset = UIEdgeInsets.zero;
        }
        if leftTableView.responds(to: #selector(setter: UIView.layoutMargins)){
            leftTableView.layoutMargins = UIEdgeInsets.zero;
        }
        
        if rightTableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            rightTableView.separatorInset = UIEdgeInsets.zero;
        }
        if rightTableView.responds(to: #selector(setter: UIView.layoutMargins)){
            rightTableView.layoutMargins = UIEdgeInsets.zero;
        }
        
        
        
        self.setSelectIndex(0);
        
        self.loadData();
    
    }
    
    func loadData() -> Void {
        
        var userId:String = "";
        
        if(1 == self.loadType){
            if let id:String = dataModel.id{
                userId = id;
            }
        }else{
            if let id:String = linkDataModel.id{
                userId = id;
            }
        }
        
        if("" == userId){
            return;
        }
        
        
        //用户的头像
        if(2 == self.loadType){
            let strParmr:String = "/" + userId + "/user_info";
            print("user id = \(strParmr)");
            AskDogGetUserInfoByUserIdDataRequest().startRequest(exUrl: strParmr, viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    if(nil != dic){
                        let usrData:HYUserInfoDataModel = HYUserInfoDataModel(jsonDic: dic!);
                        
                        if let url:String = usrData.avatar{
                            self.headerImgV.sd_setImage(with: URL(string: url),placeholderImage: UIImage(named: "AvatarDefault"));
                        }
                    }
                    
                }
            });
        }
        
        //获取当前用户创建的频道列表
        //FIXME:分页未做
        let dic:[String: AnyObject]? = ["userId":userId as AnyObject,"page":"0" as AnyObject,"size":PAGE_SIZE as AnyObject];
        
        AskDogGetUserCreatedChannelListDataRequest().startRequest(requestParmer:dic,viewController:self,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    self.dataArrarLeft.removeAll();
                    if let resultObj = dic!["result"]{
                        let arr:NSArray = resultObj as! NSArray;
                        for item in arr{
                            let data:HYChannelListDataModel = HYChannelListDataModel(jsonDic: item as! NSDictionary);
                            self.dataArrarLeft.append(data);
                        }
                        self.leftTableView.reloadData();
                        
                    }
                }
            }
        });
        
        //获取用户订阅的频道
        //FIXME:分页未做
        let dicPar:[String: AnyObject]? = ["userId":userId as AnyObject,"page":"0" as AnyObject,"size":PAGE_SIZE as AnyObject];
        AskDogGetUserSubscribedChannelListDataRequest().startRequest(requestParmer: dicPar, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    self.dataArrarRight.removeAll();
                    if let resultObj = dic!["result"]{
                        let arr:NSArray = resultObj as! NSArray;
                        for item in arr{
                            let data:HYChannelListDataModel = HYChannelListDataModel(jsonDic: item as! NSDictionary);
                            self.dataArrarRight.append(data);
                        }
                    }
                    self.rightTableView.reloadData();
                }
            }
        });
 
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func isShowLoginBtn() -> Bool {
        return false;
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    //MARK:UITableViewDelegate
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let tabV:HYBaseUITableView = tableView as! HYBaseUITableView;
        
        var data:HYChannelListDataModel!;
        
        if(tableView == self.leftTableView){
            data = self.dataArrarLeft[(indexPath as NSIndexPath).row];
        }else if(tableView == self.rightTableView){
            data = self.dataArrarRight[(indexPath as NSIndexPath).row];
        }
        
        tabV.delayCellClicked(clickedMethod: {
            () -> Void in
            
            if let id:String = data.id{
                let strParmr:String = "/" + id;
                print("share id = \(strParmr)");
                
                AskDogGetChannelInfoDataRequest().startRequest(exUrl: strParmr, viewController:self,completionHandler: {
                    (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                    if(true == beOk){
                        
                        let data:HYChannelInfoDataModel = HYChannelInfoDataModel(jsonDic: dic!);
                        
                        let vc:HYChannelHomeViewController = HYChannelHomeViewController(nibName: "HYChannelHomeViewController", bundle: Bundle.main);
                        vc.channelListID = id;
                        vc.setChannelInfo(data);
                        self.navigationController?.pushViewController(vc, animated: true);
                        
                        
                        
                    }
                });
            }
            
        });
        

        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        return 80;
        
    }
    
    //解决分割线不对齐问题
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        if cell.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            cell.separatorInset = UIEdgeInsets.zero;
        }
        if cell.responds(to: #selector(setter: UIView.layoutMargins)){
            cell.layoutMargins = UIEdgeInsets.zero;
        }
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(tableView == self.leftTableView){
            return self.dataArrarLeft.count;
        }else if(tableView == self.rightTableView){
            return self.dataArrarRight.count;
        }
        return 0;
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        var cellID:String! = "";
        
        if(tableView == self.leftTableView){
            cellID = "HYDingyueChannelListTableViewCell1";
        }else if(tableView == self.rightTableView){
            cellID = "HYDingyueChannelListTableViewCell2";
        }
        
        
        let cell:HYDingyueChannelListTableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID) as! HYDingyueChannelListTableViewCell;
        
        if(tableView == self.leftTableView){

            
            let data:HYChannelListDataModel = self.dataArrarLeft[(indexPath as NSIndexPath).row];
            cell.setChannelData(data);
        }else if(tableView == self.rightTableView){

            
            let data:HYChannelListDataModel = self.dataArrarRight[(indexPath as NSIndexPath).row];
            cell.setChannelData(data);
        }

        
        return cell;
    }


}
