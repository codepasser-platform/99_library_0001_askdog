//
//  HYNotifyViewController.swift
//  AskDog
//
//  Created by Symond on 16/8/29.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import MJRefresh

class HYNotifyViewController: HYBaseViewController,UITableViewDelegate,UITableViewDataSource,HYNofityListTableViewCellDelegate {
    
    var dataModel:HYNotifyResultListDataModel?

    @IBOutlet weak var tableView: UITableView!
    let header1 = MJRefreshNormalHeader();
    let footer1 = MJRefreshAutoNormalFooter();
    var nPageNumber:Int = 0;
    let strPageSize:String = "20";
    
    
    override func viewDidLoad() {
        stringNavBarTitle = "通知";
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let cellID1:String = "HYNofityListTableViewCell";
        let nib1:UINib = UINib(nibName: "HYNofityListTableViewCell", bundle: nil);
        tableView.register(nib1, forCellReuseIdentifier: cellID1);
        
        if tableView.responds(to: #selector(setter: UITableViewCell.separatorInset)){
            tableView.separatorInset = UIEdgeInsets.zero;
        }
        if tableView.responds(to: #selector(setter: UIView.layoutMargins)){
            tableView.layoutMargins = UIEdgeInsets.zero;
        }
        
        tableView.estimatedRowHeight = 50;
        tableView.rowHeight = UITableViewAutomaticDimension;
        
        //设置上下拉刷新
        header1.setRefreshingTarget(self, refreshingAction: #selector(header1Refresh));
        tableView.header = header1;
        footer1.setRefreshingTarget(self, refreshingAction: #selector(footer1Refresh))
        tableView.footer = footer1;
        
        
        self.loadData();
    }
    
    func loadData() -> Void {
        
        self.nPageNumber = 0;
        let strPage = String(self.nPageNumber);
        
        let dicPar:[String: AnyObject] = ["page":strPage as AnyObject,"size":strPageSize as AnyObject];
        
        AskDogGetUserNotifyListDataRequest().startRequest(requestParmer: dicPar, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    self.dataModel = HYNotifyResultListDataModel(jsonDic: dic!);
                    self.tableView.reloadData();
                    
                    if (true == self.dataModel!.last){
                        //self.tableView.footer.noticeNoMoreData();
                        self.tableView.footer.endRefreshingWithNoMoreData();
                    }else{
                        self.tableView.footer.resetNoMoreData();
                    }
                }
            }
        });
        self.tableView.header.endRefreshing();
    }
    
    func header1Refresh() -> Void {
        print("header1Refresh");
        
        
        self.loadData();
        
    }
    
    func footer1Refresh() -> Void {
        print("footer1Refresh");
        
        let nPage = self.nPageNumber + 1;
        let nSize:Int = Int(PAGE_SIZE)!;
        
        let strPage = String(nPage * nSize);
        let dicPar:[String: AnyObject]? = ["page":strPage as AnyObject,"size":strPageSize as AnyObject];
        
        AskDogGetUserNotifyListDataRequest().startRequest(requestParmer: dicPar, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            if(true == beOk){
                if(nil != dic){
                    
                    let data:HYNotifyResultListDataModel = HYNotifyResultListDataModel(jsonDic: dic!);
                    self.dataModel?.last = data.last;
                    self.dataModel?.page = data.page;
                    self.dataModel?.size = data.size;
                    self.dataModel?.total = data.total;
                    
                    if let arrar:[HYNotifyListDataModel] = data.result{
                        if(arrar.count > 0){
                            self.dataModel?.result?.append(contentsOf: arrar);
                            self.tableView.footer.endRefreshing();
                            self.tableView.reloadData();
                            self.nPageNumber += 1;
                        }
                        if (true == data.last){
                           /// self.tableView.footer.noticeNoMoreData();
                            self.tableView.footer.endRefreshingWithNoMoreData();
                        }else{
                            self.tableView.footer.resetNoMoreData();
                        }
                    }
                }
            }else{
                self.tableView.footer.endRefreshing();
            }
        });
        
    }
    
    //MARK:UITableViewDelegate
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        
        tableView.deselectRow(at: indexPath, animated: true);
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        let height:CGFloat = tableView.fd_heightForCell(withIdentifier: "HYNofityListTableViewCell", cacheBy: indexPath, configuration: {
            (obj:Any!) -> Void in

            let temp:HYNofityListTableViewCell = obj as! HYNofityListTableViewCell;
            if let array = self.dataModel?.result{
                let da:HYNotifyListDataModel = array[(indexPath as NSIndexPath).section] as HYNotifyListDataModel;
                if let arr:[HYNotifyGroupDataDataModel] = da.group_data{
                    let d:HYNotifyGroupDataDataModel = arr[(indexPath as NSIndexPath).row];
                    temp.setData(d);
                }
            }
            
        })
        
        
        
        return height + 20;
        
        //return 50;
        
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
        if let array = dataModel?.result{
            print("section =  \(array.count)");
            return array.count;
        }
        return 0;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        if let array = dataModel?.result{
            let da:HYNotifyListDataModel = array[section] as HYNotifyListDataModel;
            if let arr:[HYNotifyGroupDataDataModel] = da.group_data{
                return arr.count;
            }
        }
        return 0;
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cellID:String! = "HYNofityListTableViewCell";
        
        let cell:HYNofityListTableViewCell = tableView.dequeueReusableCell(withIdentifier: cellID) as! HYNofityListTableViewCell;
        cell.delegate = self;
        cell.indexPath = indexPath;
        if let array = dataModel?.result{
            let da:HYNotifyListDataModel = array[(indexPath as NSIndexPath).section] as HYNotifyListDataModel;
            if let arr:[HYNotifyGroupDataDataModel] = da.group_data{
                let d:HYNotifyGroupDataDataModel = arr[(indexPath as NSIndexPath).row];
                cell.setData(d);
                let n = arr.count - 1;
                if(n == (indexPath as NSIndexPath).row){
                    cell.cornerType = CornerMaskType.cornerMaskType_Bottom;
                }else{
                    cell.cornerType = CornerMaskType.cornerMaskType_None;
                }
            }
            
            
        }
        
//        let data:HYHistoryDataModel = self.dataArray[indexPath.row];
//        cell.delegate = self;
//        cell.cellIndex = indexPath.row;
//        
//        cell.setChannelData(data);
        
        return cell;
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {

        return 50;
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerV:UIView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.size.width, height: 50));
        //headerV.translatesAutoresizingMaskIntoConstraints = false;
        headerV.backgroundColor = UIColor.clear;
        
        let bkView:UIView = UIView(frame: CGRect.zero);
        bkView.backgroundColor = UIColor.white;
        bkView.translatesAutoresizingMaskIntoConstraints = false;
        
        let masklayer:CAShapeLayer = CAShapeLayer();
        let re:CGRect = CGRect(x: 2, y: 0, width: headerV.frame.size.width - 8, height: headerV.frame.size.height);
        let corner:UIRectCorner = [UIRectCorner.topLeft,UIRectCorner.topRight];
        let size:CGSize = CGSize(width: 5, height: 5);
        let bezierPath:UIBezierPath = UIBezierPath(roundedRect: re, byRoundingCorners: corner, cornerRadii: size);
        masklayer.frame = re;
        masklayer.path = bezierPath.cgPath;
        bkView.layer.mask = masklayer;
        
        headerV.addSubview(bkView);
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["bkView"] = bkView;
        headerV.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[bkView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        headerV.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-5-[bkView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        let lblDate:UILabel = UILabel(frame: CGRect.zero);
        lblDate.backgroundColor = UIColor.clear;
        lblDate.textAlignment = NSTextAlignment.left;
        lblDate.textColor = UIColor.black;
        lblDate.font = UIFont.systemFont(ofSize: 14);
        lblDate.translatesAutoresizingMaskIntoConstraints = false;
        lblDate.text = "";
        bkView.addSubview(lblDate);
        
        var views1:[String:AnyObject] = [String:AnyObject]();
        views1["lblDate"] = lblDate;
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-10-[lblDate]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[lblDate]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        
        let lblLine:UILabel = UILabel(frame: CGRect.zero);
        lblLine.text = "";
        lblLine.translatesAutoresizingMaskIntoConstraints = false;
        lblLine.backgroundColor = CommonTools.rgbColor(red: 240, green: 242, blue: 247);
        //lblLine.backgroundColor = UIColor.redColor();
        bkView.addSubview(lblLine);
        
        views1["lblLine"] = lblLine;
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[lblLine]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        bkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[lblLine(1)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        
        if let array = dataModel?.result{
            let da:HYNotifyListDataModel = array[section] as HYNotifyListDataModel;
            if let temp:HYNotifyDateDataModel = da.group_date{
                var nY = 0;
                var nM = 0;
                var nD = 0;
                if let y = temp.y{
                    nY = y;
                }
                if let m = temp.m{
                    nM = m;
                }
                if let d = temp.d{
                    nD = d;
                }
                lblDate.text = "\(nY).\(nM).\(nD)";
            }
        }
        
        return headerV;
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    //MARK:HYNofityListTableViewCellDelegate
    func hyNofityListTableViewCellDelegateLinkClicked(cell ce: HYNofityListTableViewCell, indexPath index: IndexPath, data da: HYNotifyGroupDataContentUserDataModel) {
        let vc:HYOtherUserChannelInfoHomeViewController = HYOtherUserChannelInfoHomeViewController(nibName: "HYOtherUserChannelInfoHomeViewController", bundle: Bundle.main, data: da,loadType:2);
        self.navigationController?.pushViewController(vc, animated: true);
        
        
//        if let d:HYNotifyGroupDataContentUserDataModel = da{
//            let vc:HYOtherUserChannelInfoHomeViewController = HYOtherUserChannelInfoHomeViewController(nibName: "HYOtherUserChannelInfoHomeViewController", bundle: Bundle.main, data: d,loadType:2);
//            self.navigationController?.pushViewController(vc, animated: true);
//        }
    }

}
