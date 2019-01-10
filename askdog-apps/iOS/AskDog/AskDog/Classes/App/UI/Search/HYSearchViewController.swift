//
//  HYSearchViewController.swift
//  AskDog
//
//  Created by Hooying_zhaody on 16/8/29.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import MJRefresh
import IQKeyboardManager

class HYSearchViewController: HYBaseViewController, UITextFieldDelegate, UITableViewDelegate, UITableViewDataSource {
    
    enum ListType {
        case bridfLsit
        case detailList
    }
    
    //Font
    let Fount_Title:CGFloat             = 17.0
    let Fount_Content:CGFloat           = 15.0
    /**
     *待确认常量
     */
    
    
    @IBOutlet weak var historyView: UITableView!
    @IBOutlet weak var tableView2: UITableView!
    @IBOutlet weak var tableView1: UITableView!
    var searchBkView:UIView!
    var listType                    = ListType.bridfLsit
    var timer: Timer!
    var searchValue                 = ""
    var pageNum: Int                = 0
    let header = MJRefreshNormalHeader()
    let footer = MJRefreshAutoNormalFooter()
    var dataSouce:[HYSearchActionModel] = [HYSearchActionModel]();
    var searchTextField:UITextField!
    var isSearch: Bool              = false
    var total: Int                  = 0
    
    var historyKeys: [String]!
    
    var isNavPush: Bool = true
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if let arr:[String] = UserDefaults.standard.object(forKey: "history") as! [String]? {
            self.historyKeys = arr;
        } else {
            self.historyKeys = [String]()
        }
        
        self.setupHYSearchBar()
        self.setupTableView()
        self.historyView.reloadData()
    }
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated);
        IQKeyboardManager.shared().isEnableAutoToolbar = false;
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillAppear(animated);
        IQKeyboardManager.shared().isEnableAutoToolbar = true;
    }
    
    // MARK: - setupNavigation
    override func isShowNavBarTitle() -> Bool {
        return false
    }
    
    override func isShowReturnBtn() -> Bool {
        return false
    }
    
    override func isShowLoginBtn() -> Bool {
        return false
    }
    
    // MARK: - setUpViews
    func setupHYSearchBar() {
        //顶部搜索框
        searchBkView = UIView(frame:CGRect.zero)
        searchBkView.backgroundColor = UIColor.white
        searchBkView.translatesAutoresizingMaskIntoConstraints = false
        searchBkView.layer.cornerRadius = 5
        navBarView.addSubview(searchBkView)
        var views:[String:AnyObject] = [String:AnyObject]()
        views["searchBkView"] = searchBkView
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-20-[searchBkView]-64-|", options: NSLayoutFormatOptions(), metrics: nil, views: views))
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-27-[searchBkView]-7-|", options: NSLayoutFormatOptions(), metrics: nil, views: views))
        
        //放大镜🔍
        let zoomImageV: UIImageView = UIImageView(frame: CGRect.zero)
        zoomImageV.image = UIImage(named: "wenhao")
        zoomImageV.translatesAutoresizingMaskIntoConstraints = false
        searchBkView.addSubview(zoomImageV)
        var views1:[String:AnyObject] = [String:AnyObject]()
        views1["zoomImageV"] = zoomImageV
        searchBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-8-[zoomImageV(18)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1))
        searchBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-8-[zoomImageV(18)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1))
        
        //输入框
        searchTextField = UITextField(frame: CGRect.zero)
        searchTextField.delegate = self
        searchTextField.clearButtonMode = UITextFieldViewMode.whileEditing
        searchTextField.returnKeyType = UIReturnKeyType.search
        searchTextField.font = UIFont.systemFont(ofSize: Fount_Content)
        searchTextField.placeholder = "请输入搜索内容"
        searchTextField.translatesAutoresizingMaskIntoConstraints = false
        searchBkView.addSubview(searchTextField);
        views1["searchTextField"] = searchTextField;
        searchBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-29-[searchTextField]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views1))
        searchBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-6-[searchTextField(20)]", options: NSLayoutFormatOptions(), metrics: nil, views: views1))
        
        //取消Button
        let searchCancelBtn: UIButton = UIButton(type: UIButtonType.custom)
        searchCancelBtn.frame = CGRect.zero
        searchCancelBtn.setTitle("取消", for: UIControlState())
        searchCancelBtn.setTitleColor(Color_WhiteTitle, for: UIControlState())
        searchCancelBtn.titleLabel?.font = UIFont.systemFont(ofSize: Fount_Content)
        searchCancelBtn.translatesAutoresizingMaskIntoConstraints = false
        searchCancelBtn.addTarget(self, action: #selector(searchCancelAction), for: UIControlEvents.touchUpInside)
        navBarView.addSubview(searchCancelBtn)
        var navVews:[String:AnyObject] = [String:AnyObject]()
        navVews["searchCancelBtn"] = searchCancelBtn
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[searchCancelBtn(50)]-8-|", options: NSLayoutFormatOptions(), metrics: nil, views: navVews))
        navBarView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-32-[searchCancelBtn(18)]", options: NSLayoutFormatOptions(), metrics: nil, views: navVews))
        
        //textFile变化通知
        let notif = NotificationCenter.default
        let operationQueue = OperationQueue.main
        notif.addObserver(forName: NSNotification.Name.UITextFieldTextDidChange, object: nil, queue: operationQueue) { Void in
            print(self.searchTextField.text)
            self.textFileChanged((self.searchTextField.text!))
        }
        
        searchTextField.becomeFirstResponder()
        
    }
    
    func setupTableView()  {
        
        tableView1.tag = 1
        tableView1.bounces = false
        tableView1.isHidden = true

        
        tableView2.tag = 2
        tableView2.bounces = true
        tableView2.isHidden = true
        //设置上下拉刷新
        header.setRefreshingTarget(self, refreshingAction: #selector(headerRefresh))
        tableView2.header = header
        footer.setRefreshingTarget(self, refreshingAction: #selector(footerRefresh))
        tableView2.footer = footer
        
        //注册Cell
        let detailCell:String = "detailCell"
        let nib1:UINib = UINib(nibName: "HYHomeListTableViewCell", bundle: nil);
        tableView2.register(nib1, forCellReuseIdentifier: detailCell);
        let headerCell:String = "headerCell"
        let nib2:UINib = UINib(nibName: "HYSearchHeardTableViewCell", bundle: nil);
        tableView2.register(nib2, forCellReuseIdentifier: headerCell);
        
        historyView.separatorStyle = UITableViewCellSeparatorStyle.none;
        historyView.tag = 3
        historyView.bounces = false
        historyView.isHidden = false
        
        self.automaticallyAdjustsScrollViewInsets = false
    }
    
    
    // MARK: - UITextFileDelegate
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        historyView.isHidden = false
        tableView1.isHidden = true
        tableView2.isHidden = true
        listType = ListType.bridfLsit
        historyView.reloadData();
        return true
    }
    
    func textFileChanged(_ sender: String) {
        if sender.characters.count == 0 {
            historyView.isHidden = false
            tableView1.isHidden = true
            tableView2.isHidden = true
            listType = ListType.bridfLsit
        } else {
            historyView.isHidden = true
            tableView1.isHidden = false
            tableView2.isHidden = true
            listType = ListType.bridfLsit
        }

        //输入框改变启动请求
        if self.isSearch == false {
            self.seachNetWork()
        }
    }

    func textFieldDidEndEditing(_ textField: UITextField) {
        searchValue = textField.text!
        self.setHistoryData(key: textField.text!)
    }

    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        searchValue = textField.text!
        pageNum = 0
        self.seachNetWork()
        textField.resignFirstResponder()
        return true
    }
    
    
    // MARK: - UITableViewDataSouce
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if tableView.tag == 3 {
            let header = UILabel(frame: CGRect(x: 0, y: 0, width: self.view.frame.width, height: 44));
            header.text = "    搜索记录";
            header.textColor = Color_greyListText;
            header.backgroundColor = Color_greyHeaderBg;
            header.font = UIFont.systemFont(ofSize: Fount_Content);
            let lin: UIView = UIView(frame: CGRect(x: 0, y: 43, width: header.frame.width, height: 1))
            lin.backgroundColor = Color_greyLine;
            header.addSubview(lin)
            return header;
        }
       return UIView()
    }
    
    func tableView(_ numberOfSectionsInTableView: Int) -> Int{
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if tableView.tag == 3 {
            return historyKeys!.count
        }
        //无结果
        if dataSouce.count == 0 {
            if searchValue.characters.count > 0 {
                return 1
            }
            return 0
        }
        //缩略最多五条
        if tableView.tag == 1 {
            if dataSouce.count > 5 {
                return 6
            }
            return (dataSouce.count + 1)
 
        } else {
            //更多结果
            return (self.dataSouce.count + 1)
        }
        
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if tableView.tag == 3 {
            var cell = tableView.dequeueReusableCell(withIdentifier: "cell3")
            if cell == nil {
                cell = UITableViewCell (style: UITableViewCellStyle.default, reuseIdentifier: "cell3")
                
                var rect:CGRect = (cell?.contentView.frame)!
                rect.origin.y = rect.size.height
                rect.size.height = 1.0
                rect.origin.x = 15.0
                let line = UIView(frame: rect)
                line.backgroundColor = Color_greyLine;
                cell?.contentView .addSubview(line)
                cell?.contentView .bringSubview(toFront: line)
            }
            
            let title: String = historyKeys![indexPath.row] as String
            cell?.textLabel?.text = title as String
            cell?.textLabel?.textColor = Coler_Blue
            cell?.textLabel?.font = UIFont.systemFont(ofSize: Fount_Content)
            
            return cell!
        }

        //无搜索结果
        if dataSouce.count == 0 {
            let cell = UITableViewCell (style: UITableViewCellStyle.default, reuseIdentifier: "noCell")
            cell.textLabel?.text = "无搜索结果"
            cell.textLabel?.font = UIFont.systemFont(ofSize: Fount_Content)
            cell.textLabel?.textColor = Coler_Blue
            return cell
            
        }
        
        if tableView.tag == 1 {
            //简略列表
            let rowNum = dataSouce.count > 5 ? 6 : (dataSouce.count)+1
            if  (indexPath as NSIndexPath).row < rowNum-1 {
                let model:HYSearchActionModel = self.dataSouce[(indexPath as NSIndexPath).row] 
                var cell = tableView.dequeueReusableCell(withIdentifier: "cell")
                if cell == nil {
                    cell = UITableViewCell (style: UITableViewCellStyle.default, reuseIdentifier: "cell")
                    
                    var rect:CGRect = (cell?.contentView.frame)!
                    rect.origin.y = rect.size.height
                    rect.size.height = 1.0
                    rect.origin.x = 15.0
                    let line = UIView(frame: rect)
                    line.backgroundColor = Color_greyLine
                    cell?.contentView .addSubview(line)
                    cell?.contentView .bringSubview(toFront: line)
                }
                
                let title: NSString = model.subject! as NSString
                let attr = HYMutableAttributedString()
                cell?.textLabel?.attributedText = attr.markString(title)
                cell?.textLabel?.numberOfLines = 2
                cell?.textLabel?.font = UIFont.systemFont(ofSize: Fount_Content)
                
                return cell!
                
            } else {
                
                let cell = UITableViewCell (style: UITableViewCellStyle.default, reuseIdentifier: "footerCell")
                cell.textLabel?.text = "查看全部搜索结果"
                cell.textLabel?.font = UIFont.systemFont(ofSize: Fount_Content)
                cell.textLabel?.textColor = Coler_Blue
                
                return cell
            }
            
        } else {
            
            if (indexPath as NSIndexPath).row == 0 {
                let cell:HYSearchHeardTableViewCell = tableView.dequeueReusableCell(withIdentifier: "headerCell") as! HYSearchHeardTableViewCell;
                cell.cellRelodData(String(self.total), searchVale: searchValue)
                return cell
            }
            //详细列表
            let cell:HYHomeListTableViewCell = tableView.dequeueReusableCell(withIdentifier: "detailCell") as! HYHomeListTableViewCell;
            
            let model:HYSearchActionModel = self.dataSouce[(indexPath as NSIndexPath).row - 1]
            cell.setSearchData(model)
            return cell
        }
    }

    
    // MARK: - UITableViewDelegate
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if tableView.tag == 3 {
            return 44;
        }
        return 0;
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if tableView.tag == 3 {
            return 44.0
        }
        if tableView.tag == 1 {
            return 54.0
        }
        if (indexPath as NSIndexPath).row == 0 {
            return 44.0
        } else {
            
            if(true == IS_IPHONE6S_PLUS_DEV){
                return 310.0;
            }else if(true == IS_IPHONE66S_DEV){
                return 280.0;
            }else if(true == IS_IPHONE55S_DEV){
                return 260.0;
            }
            return 320.0;
        }
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView .deselectRow(at: indexPath, animated: true)
        if tableView.tag == 3 {
            self.searchValue = historyKeys[indexPath.row]
            self.textFileChanged(historyKeys[indexPath.row])
            self.searchTextField.text = historyKeys[indexPath.row]
            self.setHistoryData(key: historyKeys[indexPath.row])
            return;
        }
        if dataSouce.count == 0 {
            return
        }
        
        if tableView.tag == 1 {
            let cell = tableView .cellForRow(at: indexPath);
            if cell?.reuseIdentifier == "footerCell" {
                tableView1.isHidden = true
                tableView2.isHidden = false
                listType = ListType.detailList
                tableView2.reloadData()
            } else {
                // 详情
                let model:HYSearchActionModel = self.dataSouce[(indexPath as NSIndexPath).row]
                self.pushContentView(model.id!)
            }
        }
        if tableView.tag == 2 {
            // 详情
            if (indexPath as NSIndexPath).row > 0 {
                let model:HYSearchActionModel = self.dataSouce[(indexPath as NSIndexPath).row - 1] 
                self.pushContentView(model.id!)                
            }
        }
    }
    
    
    func pushContentView(_ idStr:String)  {
            let strParmr:String = "/" + idStr
            AskDogGetExperienceDetailDataRequest().startRequest(exUrl: strParmr, viewController:self,completionHandler: {
                (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
                if(true == beOk){
                    let data:HYExperienceDetailDataModel = HYExperienceDetailDataModel(jsonDic: dic!);
                    
                    let vc:HYContenDetailListViewController = HYContenDetailListViewController(nibName: "HYContenDetailListViewController", bundle: Bundle.main,data: data);
                    self.navigationController?.pushViewController(vc, animated: true);
                }
            });
    }
    
    
    
    // MARK: - Actions
    //取消搜索返回首页
    func searchCancelAction() {
        if isNavPush == true {
            _ = self.navigationController?.popViewController(animated: true)
        } else {
           _ = self.navigationController?.popViewController(animated: false)
        }
    }
    
    //下拉刷新
    func headerRefresh() -> Void {
        pageNum = 0
        self.seachNetWork()
        tableView2.header.endRefreshing();
        
    }
    //上拉加载
    func footerRefresh() -> Void {

        let page = pageNum + 1
        let nSize:Int = Int(PAGE_SIZE)!;
        
        searchValue = self.searchTextField.text!
        
        let dic: [String:AnyObject] = ["type":"experience_search" as AnyObject, "key":searchValue as AnyObject, "from":String(page * nSize) as AnyObject, "size":PAGE_SIZE as AnyObject]
        print("HYSearchViewController --- network -- :\(dic)")
        
        isSearch = true
        AskDogGetHomeSearchActionRequest().startRequest(requestParmer: dic, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            
            if beOk == true {
                if dic != nil {
                    let model: HYSearchActionResultModel = HYSearchActionResultModel(jsonDic: dic!)
                    self.total = model.total!
                    
                    if let res = model.result {
                        if res.count > 0 {
                            self.pageNum += 1
                            let arr: NSArray = res as NSArray
                            for item in arr {
                                self.dataSouce.append(item as! HYSearchActionModel)
                            }
                            
                            self.tableView2.reloadData()
                            
                            if true == model.last {
                                self.tableView2.footer.endRefreshingWithNoMoreData()
                            } else {
                                self.tableView2.footer.resetNoMoreData()
                            }
                        }
                    }
                }
            }
            self.isSearch = false
        });
        self.tableView2.footer.endRefreshing()
    }
    
    
    // MARK: - NetWork
    func seachNetWork( ) {
        pageNum = 0
        searchValue = self.searchTextField.text!

        let dic: [String:AnyObject] = ["type":"experience_search" as AnyObject, "key":searchValue as AnyObject, "from":String(pageNum) as AnyObject, "size":PAGE_SIZE as AnyObject]
        print("HYSearchViewController --- network -- :\(dic)")
        
        isSearch = true
        AskDogGetHomeSearchActionRequest().startRequest(requestParmer: dic, showProgressHUD:false, viewController:self, completionHandler: {
            (beOk:Bool?,arr:NSArray?,dic:NSDictionary?,error:NSError?)->Void in
            
            if beOk == true {
                if dic != nil {
                    self.dataSouce.removeAll()
                    let model: HYSearchActionResultModel = HYSearchActionResultModel(jsonDic: dic!)
                    self.total = model.total!
                    if let res = model.result {
                        self.dataSouce = res
                    }
                    
                    if self.listType == ListType.bridfLsit {
                        self.tableView1.reloadData()
                    } else {
                        self.tableView2.reloadData()
                        
                        if true == model.last {
                            self.tableView2.footer.endRefreshingWithNoMoreData()
                        } else {
                            self.tableView2.footer.resetNoMoreData()
                        }
                    }
                    
                    self.instantSearch()
                }
            }
            self.isSearch = false
        });
    }
    
    func instantSearch() {
        //递归，判断当前输入框字符是否与上次请求字符相同，不同进行再次请求
        if self.searchValue != self.searchTextField.text {
            self.pageNum = 0
            self.seachNetWork()
        }
    }
    

    func setHistoryData(key: String) {
//        print(key)
//        print("key")
//        if historyKeys.count == 6 {
//            var arr:[String] = [String]()
//            arr += [key]
//
//            for ins in 5...1{
//                arr += [historyKeys[ins]]
//            }
//            historyKeys = arr
//        } else {
//            var arr:[String] = [String]()
//            arr += [key]
//            if historyKeys.count > 0 {
//                for ins in (self.historyKeys.count - 1) ... 0{
//                    arr += [historyKeys[ins]]
//                }
//            }
//            historyKeys = arr
//        }
        if key.characters.count == 0
        {
            return;
        }
        var arr: [String] = [String]()

        if historyKeys.contains(key) {
            arr.append(key);
            for  obj in historyKeys {
                if obj != key {
                    arr.append(obj)
                }
            }
        } else {
            //TODO: 历史纪录条数
            if historyKeys.count == 10 {
                historyKeys.remove(at: 9)
            }
            arr.append(key)
            arr += historyKeys
        }
        historyKeys = arr
        historyView.reloadData()
        UserDefaults.standard.set(historyKeys, forKey: "history");
    }
    
    override func delete(_ sender: Any?) {
        super.delete(sender)
        NotificationCenter.default.removeObserver(self)
    }
}
