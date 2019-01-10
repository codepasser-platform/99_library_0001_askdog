//
//  HYSelecteVideoMusicListViewController.swift
//  AskDog
//
//  Created by Symond on 16/9/21.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import MJRefresh

typealias selectMusicCallback = (_ data:HYMusicListDataModel)->Void;

class HYSelecteVideoMusicListViewController: HYBaseViewController,UITableViewDataSource,UITableViewDelegate {
    
    
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var tableView: HYBaseUITableView!
    var nPageNumber:Int = 0;
    var dataModel:HYMusicListResultDataModel?
    
    var selectCallBack : selectMusicCallback?

    override func viewDidLoad() {
        stringNavBarTitle = "请选择音乐";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let btnOK:UIButton = UIButton(type: UIButtonType.custom);
        btnOK.frame = CGRect.zero;
        btnOK.setTitle("完成", for: UIControlState());
        btnOK.titleLabel?.font = UIFont.systemFont(ofSize: 13);
        btnOK.setTitleColor(UIColor.white, for: .normal);
        btnOK.titleEdgeInsets = UIEdgeInsetsMake(3, 0, -3, 0);
        btnOK.addTarget(self, action: #selector(btnOKClicked), for: UIControlEvents.touchUpInside);
        btnOK.backgroundColor = UIColor.clear;
        btnOK.translatesAutoresizingMaskIntoConstraints = false;
        navBarView.addSubview(btnOK);
        btnOK.isHidden = true;
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["btnOK"] = btnOK;
        
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[btnOK(50)]-10-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-20-[btnOK]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        if tableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tableView.separatorInset = UIEdgeInsets.zero;
        }
        if tableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tableView.layoutMargins = UIEdgeInsets.zero;
        }
        
        let cellID1:String = "HYMusicListTableViewCell";
        let nib1:UINib = UINib(nibName: "HYMusicListTableViewCell", bundle: nil);
        tableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        let header = MJRefreshNormalHeader();
        let footer = MJRefreshAutoNormalFooter();
        
        //设置上下拉刷新
        header.setRefreshingTarget(self, refreshingAction: #selector(headerRefresh));
        tableView.header = header;
        footer.setRefreshingTarget(self, refreshingAction: #selector(footerRefresh))
        tableView.footer = footer;
        tableView.footer.isAutomaticallyHidden = false;
        
        
        self.tableView.addNoDataStatusView(showImg: "errorloading",showTitle:"加载错误\n点击屏幕刷新",refreshCallBack: {
            [unowned self] () -> Void in
            
            self.loadData();
            
            
        });
        
        self.loadData();
    }
    
    func loadData() -> Void {
        
        self.nPageNumber = 0;
        let strPage = String(self.nPageNumber);
        
        
        let dic:[String: AnyObject]? = ["page":strPage as AnyObject,"size":PAGE_SIZE as AnyObject];
        
        AskDogGetMusicListDataRequest().startRequest(requestParmer: dic,viewController:self,autoShowErrorAlertView:false,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    
                    self.dataModel = HYMusicListResultDataModel(jsonDic: dic!);
                    
                    self.tableView.reloadData();
                    if (true == self.dataModel?.last){
                        self.tableView.footer.endRefreshingWithNoMoreData();
                    }else{
                        self.tableView.footer.resetNoMoreData();
                    }
                    
                   
                }
            }
        });
        self.tableView.header.endRefreshing();
    }
    
    func headerRefresh() -> Void {
        print("header2Refresh");
        
        self.loadData();
        
    }
    
    func footerRefresh() -> Void {
        print("footer2Refresh");
        
        let nPage = self.nPageNumber + 1;
        
        let nSize:Int = Int(PAGE_SIZE)!;
        let strPage = String(nPage * nSize);
        
         let dic:[String: AnyObject]? = ["page":strPage as AnyObject,"size":PAGE_SIZE as AnyObject];
        
        AskDogGetMusicListDataRequest().startRequest(requestParmer: dic,viewController:self,autoShowErrorAlertView:false,completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    
                    let dm:HYMusicListResultDataModel = HYMusicListResultDataModel(jsonDic: dic!);
                    self.dataModel?.last = dm.last;
                    self.dataModel?.total = dm.total;
                    
                    if let res = dm.result{
                        if res.count > 0{
                            self.dataModel?.result?.append(contentsOf: res);
                            self.nPageNumber += 1;
                            self.tableView.reloadData();
                        }
                    }
                    
                    if (true == self.dataModel?.last){
                        self.tableView.footer.endRefreshingWithNoMoreData();
                    }else{
                        self.tableView.footer.resetNoMoreData();
                    }
                    
                }
            }
        });
        self.tableView.footer.endRefreshing();

    }

    
    func btnOKClicked() -> Void {
        self.dismiss(animated: true, completion: {
            () -> Void in
        });
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func btnReturnClicked(_ sender: UIButton) {
        self.dismiss(animated: true, completion: {
            () -> Void in
        });
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    //MARK:UITableViewDelegate
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        self.tableView.delayCellClicked(clickedMethod: {
            () -> Void in
            
            let temp:HYMusicListTableViewCell = tableView.cellForRow(at: indexPath) as! HYMusicListTableViewCell;
            if(false == temp.btnDownload.isEnabled){
                //已下载的 可直接选
                var musicName:String = "";
                let data:HYMusicListDataModel = (self.dataModel?.result![(indexPath as NSIndexPath).row])!;
                if let name = data.name{
                    musicName = name;
                }
                let str:String = "选择<\(musicName)>做为背景音乐?";
                CommonTools.showModalMessage(str, title: "注意", vc: self, hasCancelBtn: true, hr: {
                    (be:Bool) -> Void in
                    if(true == be){
                        if let call = self.selectCallBack{
                            call(data);
                            self.dismiss(animated: true, completion: nil);
                        }
                    }
                });
            }else{
                CommonTools.showMessage("请先下载音乐，才可做为背景音乐！", vc: self, hr: {
                    ()-> Void in
                    
                });
            }
            
            
        });
        
        
        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        

        return 40.0;
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
    
    //MARK:UITableViewDataSource
    //    func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
    //        return true;
    //    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if let arr:[HYMusicListDataModel] = self.dataModel?.result{
            let n = arr.count;
            if(0 == n){
                self.tableView.showNoDataStatusView(beShow: true);
                self.tableView.setNoDataStatusViewTapEnable(canTap: true);
                self.tableView.header.isHidden = true;
                self.tableView.footer.isHidden = true;
            }else{
                self.tableView.showNoDataStatusView(beShow: false);
                self.tableView.setNoDataStatusViewTapEnable(canTap: false);
                self.tableView.header.isHidden = false;
                self.tableView.footer.isHidden = false;
            }
            return n;
        }else{
            return 0;
        }
        
        //return 0;
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell:UITableViewCell!
        let cellID:String! = "HYMusicListTableViewCell";
        
        
        cell = tableView.dequeueReusableCell(withIdentifier: cellID);
        let temp:HYMusicListTableViewCell = cell as! HYMusicListTableViewCell;
        let data:HYMusicListDataModel = (self.dataModel?.result![(indexPath as NSIndexPath).row])!;
        temp.setData(data);
        
        
        
        return cell;
    }
    
    deinit {
        log.ln("deinit \(self)")/;
    }


}
