//
//  HYContenListTopTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/7/29.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import NVActivityIndicatorView

enum SubScribableState  : Int {
    case subScribable_Can
    case subScribable_Already
    case subScribable_Disable
}

enum VoteState  : Int {
    case voteState_Up
    case voteState_Down
    case voteState_None
}


protocol HYContenListTopTableViewCellDelegate  :class{
    func hyContenListTopTableViewCellShareBtnClicked(cell ce:HYContenListTopTableViewCell,sectionIndex section:Int) -> Void ;
    func hyContenListTopTableViewCellHeaderBtnClicked(cell ce:HYContenListTopTableViewCell,sectionIndex section:Int) -> Void ;
    func hyContenListTopTableViewCellReportBtnClicked(cell ce:HYContenListTopTableViewCell,sectionIndex section:Int) -> Void ;
    func hyContenListTopTableViewCellZanBtnClicked(cell ce:HYContenListTopTableViewCell,sectionIndex section:Int) -> Void ;
    func hyContenListTopTableViewCellCaiBtnClicked(cell ce:HYContenListTopTableViewCell,sectionIndex section:Int) -> Void ;
    func hyContenListTopTableViewCellSubscriptionBtnClicked(cell ce:HYContenListTopTableViewCell,sectionIndex section:Int) -> Void ;
    func hyContenListTopTableViewCellWebViewDidFinishedLoad(cell ce:HYContenListTopTableViewCell,sectionIndex section:Int,height h:CGFloat) -> Void ;
    func hyContenListTopTableViewCellWebViewReachBottom(cell ce:HYContenListTopTableViewCell,sectionIndex section:Int) -> Void ;
}


class HYContenListTopTableViewCell: UITableViewCell,UIWebViewDelegate,UIScrollViewDelegate {

    @IBOutlet weak var webView: UIWebView!
    @IBOutlet weak var textBkView: UIView!
    @IBOutlet weak var contentbkView: UIView!
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var dingyueBkView: UIView!
    @IBOutlet weak var headerImgV: UIImageView!
    
    @IBOutlet weak var thumbnailImageView: UIImageView!
    @IBOutlet weak var btnCai: UIButton!
    @IBOutlet weak var btnZan: UIButton!
    @IBOutlet weak var btnSubScribable: UIButton!
    @IBOutlet weak var vipImageView: UIImageView!
    @IBOutlet weak var lblSubject: UILabel!
    @IBOutlet weak var lblDingyueSepatorLine: UILabel!
    @IBOutlet weak var lblName: UILabel!
    @IBOutlet weak var lblViewInfo: UILabel!
    
    @IBOutlet weak var headerImgWidth: NSLayoutConstraint!
    @IBOutlet weak var lblCai: UILabel!
    @IBOutlet weak var lblZan: UILabel!
    @IBOutlet weak var lblDingyueNumber: UILabel!
    @IBOutlet weak var lblChannelName: UILabel!
    var dataDic:NSDictionary = NSDictionary();
    var dataModel:HYExperienceDetailDataModel!
    
    var scribableState:SubScribableState = .subScribable_Disable;
    var voteState:VoteState = .voteState_None;
    
     weak var delegate:HYContenListTopTableViewCellDelegate?
    

    
//    required init?(coder aDecoder: NSCoder) {
//        super.init(coder: aDecoder);
//    }
    
    func setZanCaiDic(_ dic:NSDictionary) -> Void {
        if let up_vote_count = dic["up_vote_count"]{
            let nCnt = up_vote_count as! Int;
            dataModel.up_vote_count = nCnt;
            
            let str:String = String(nCnt);
            lblZan.text = str;
        }
        if let down_vote_count = dic["down_vote_count"]{
            let nCnt = down_vote_count as! Int;
            dataModel.down_vote_count = nCnt;
            
            let str:String = String(nCnt);
            lblCai.text = str;
        }
        
        if let vote_direction = dic["vote_direction"]{
            let direction:String = vote_direction as! String;
            if("UP" == direction){
                dataModel.up_voted = true;
                setVoteStatus(.voteState_Up);
                
            }else if("DOWN" == direction){
                dataModel.down_voted = true;
                setVoteStatus(.voteState_Down);
            }else{
                dataModel.up_voted = false;
                dataModel.down_voted = false;
                setVoteStatus(.voteState_None);
            }
            
        }else{
            dataModel.up_voted = false;
            dataModel.down_voted = false;
            setVoteStatus(.voteState_None);
        }
    }
    
    
    func setPinglunDataModel(_ da:HYExperienceDetailDataModel) -> Void {
        
        
        dataModel = da;
        print("type = \(dataModel.content?.type)");

        if let subject:String = dataModel.subject{
            lblSubject.text = subject;
        }
        
        if let author:HYAuthorDataModel = dataModel.author{
            if let avatarUrl:String = author.avatar{
                headerImgV.sd_setImage(with: URL(string: avatarUrl),placeholderImage:UIImage(named: "AvatarDefault"));
               // headerImgV.setImageWithURL

            }
            if let name:String = author.name{
                lblName.text = name;
            }
            if let v:Bool = author.vip{
                vipImageView.isHidden = !v;
            }
        }
        
        var strTm:String = "";
        if let createTm:TimeInterval = dataModel.creation_time{
            strTm = CommonTools.nsDateToString(Date(timeIntervalSince1970: createTm/1000), withFormat: "yyyy/MM/dd")
        }
        var nViewCount:Int = 0;
        if let viewCnt:Int = dataModel.view_count{
            nViewCount = viewCnt;
        }
        lblViewInfo.text = "分享于 " + strTm + " 浏览数 " + String(nViewCount);
        
        if let channel:HYChannelDataModel = dataModel.channel{
            if let name:String = channel.name{
                lblChannelName.text = name;
            }
            if let count:Int = channel.subscriber_count{
                lblDingyueNumber.text = String(count);
            }
            
            //是否可以订阅
            if(false == Global.sharedInstance.isLogined){
                setSubScribableState(.subScribable_Can);
            }else{
                if let mineObj:Bool = channel.mine{
                    if(true == mineObj){
                        setSubScribableState(.subScribable_Disable);
                    }else{
                        if let subscribable:Bool = channel.subscribable{
                            if(true == subscribable){
                                setSubScribableState(.subScribable_Can);
                            }else{
                                setSubScribableState(.subScribable_Already);
                            }
                        }
                    }
                }else{
                    if let subscribable:Bool = channel.subscribable{
                        if(true == subscribable){
                            setSubScribableState(.subScribable_Can);
                        }else{
                            setSubScribableState(.subScribable_Already);
                        }
                    }
                }
            }
            
            
        }
        //设置踩赞状态
        if let up_vote_count = dataModel.up_vote_count{
            lblZan.text = String(up_vote_count);
        }
        if let down_vote_count = dataModel.down_vote_count{
            lblCai.text = String(down_vote_count);
        }
        
        if let up_voted:Bool = dataModel.up_voted{
            if(true == up_voted){
                setVoteStatus(.voteState_Up);
            }
        }
        
        if let down_voted:Bool = dataModel.down_voted{
            if(true == down_voted){
                setVoteStatus(.voteState_Down);
            }
        }
        
        
    }
    
    func setSubScribableStateAndCount(_ state:SubScribableState,beAdd:Bool) -> Void {
        setSubScribableState(state);
        if(true == beAdd){
            if let n = dataModel.channel?.subscriber_count{
                let cnt = n + 1;
                dataModel.channel?.subscriber_count = cnt;
                lblDingyueNumber.text = String(cnt);
            }
        }else{
            if let n = dataModel.channel?.subscriber_count{
                var cnt = n - 1;
                cnt = cnt < 0 ? 0: cnt;
                dataModel.channel?.subscriber_count = cnt;
                lblDingyueNumber.text = String(cnt);
            }
        }
    }
    
    
    //1 已订阅 2未订阅 3禁止订阅
    func setSubScribableState(_ state:SubScribableState) -> Void {
        self.scribableState = state;
        
        switch state {
        case .subScribable_Already:
            btnSubScribable.setTitle("取消订阅", for: UIControlState());
            dingyueBkView.layer.borderColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164).cgColor;
            lblDingyueSepatorLine.backgroundColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164);
            btnSubScribable.backgroundColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164);
            lblDingyueNumber.textColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164);
            btnSubScribable.isEnabled = true;
        case .subScribable_Can:
            btnSubScribable.setTitle("订阅频道", for: UIControlState());
            dingyueBkView.layer.borderColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231).cgColor;
            lblDingyueSepatorLine.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231);
            btnSubScribable.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231);
            lblDingyueNumber.textColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231);
            btnSubScribable.isEnabled = true;
        case .subScribable_Disable:
            btnSubScribable.setTitle("订阅频道", for: UIControlState());
            dingyueBkView.layer.borderColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5).cgColor;
            lblDingyueSepatorLine.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5);
            btnSubScribable.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5);
            lblDingyueNumber.textColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5);
            btnSubScribable.isEnabled = false;
        }
    }
    
    
    @IBAction func btnShareClicked(_ sender: UIButton) {
        delegate?.hyContenListTopTableViewCellShareBtnClicked(cell: self, sectionIndex: 0);
    }
    
    @IBAction func btnHeaderClicked(_ sender: UIButton) {
        delegate?.hyContenListTopTableViewCellHeaderBtnClicked(cell: self, sectionIndex: 0);
    }
    @IBAction func btnReportClicked(_ sender: UIButton) {
        delegate?.hyContenListTopTableViewCellReportBtnClicked(cell: self, sectionIndex: 0);
    }
    @IBAction func btnCaiClicked(_ sender: UIButton) {
        
        delegate?.hyContenListTopTableViewCellCaiBtnClicked(cell: self, sectionIndex: 0);
        
    }
    @IBAction func btnZanClicked(_ sender: UIButton) {
        
        delegate?.hyContenListTopTableViewCellZanBtnClicked(cell: self, sectionIndex: 0);
        
    }
    @IBAction func btnSubScribableClicked(_ sender: UIButton) {
        
        delegate?.hyContenListTopTableViewCellSubscriptionBtnClicked(cell: self, sectionIndex: 0);
        
        
//        if(self.scribableState == .SubScribable_Already){
//            setSubScribableState(.SubScribable_Can);
//        }else if(self.scribableState == .SubScribable_Can){
//            setSubScribableState(.SubScribable_Already);
//        }
    }
    
    func setVoteStatus(_ status:VoteState) -> Void {
        switch status {
        case .voteState_Up:
            btnZan.isSelected = true;
            btnCai.isSelected = false;
            
            lblCai.textColor = UIColor.darkGray;
            lblZan.textColor = CommonTools.rgbColor(red: 0, green: 170, blue: 239);
            
        case .voteState_Down:
            btnZan.isSelected = false;
            btnCai.isSelected = true;
            
            lblZan.textColor = UIColor.darkGray;
            lblCai.textColor = CommonTools.rgbColor(red: 0, green: 170, blue: 239);
        case .voteState_None:
            btnZan.isSelected = false;
            btnCai.isSelected = false;
            
            lblZan.textColor = UIColor.darkGray;
            lblCai.textColor = UIColor.darkGray;
        }
    }
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
//        
//        avPlayer = MyVideoPlayer(frame: CGRectZero);
//        avPlayer.translatesAutoresizingMaskIntoConstraints = false;
//        var views:[String:AnyObject] = [String:AnyObject]();
//        views["avPlayer"] = avPlayer;
//        contentbkView.addSubview(avPlayer);
//
//        contentbkView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:|-0-[avPlayer]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//        contentbkView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("V:|-0-[avPlayer]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        
        headerImgV.layer.cornerRadius = headerImgWidth.constant/2.0;
        headerImgV.layer.masksToBounds = true;
        
        lblDingyueNumber.text = "";
        lblSubject.text = "";
        lblName.text = "";
        lblViewInfo.text = "";
        lblChannelName.text = "";
        lblZan.text = "0";
        lblCai.text = "0";
        
        setVoteStatus(.voteState_None);
        
        //加阴影
//        bkView.layer.shadowColor = UIColor.black.cgColor;
//        bkView.layer.shadowOffset = CGSize(width: 3, height: 3);
//        bkView.layer.shadowOpacity = 0.4;
//        bkView.layer.shadowRadius = 3;
//        bkView.layer.cornerRadius = 5;
        
        //订阅按钮圆角处理
        dingyueBkView.backgroundColor = UIColor.clear;
        dingyueBkView.layer.cornerRadius = 5;
        dingyueBkView.layer.borderColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231).cgColor;
        dingyueBkView.layer.borderWidth = 1;
        dingyueBkView.clipsToBounds = true;
        
        lblDingyueSepatorLine.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231);
        
        
        //let filePath:String! = NSBundle.mainBundle().pathForResource("test", ofType: "mp4");
        

        
        self.webView.delegate = self;
        self.webView.scrollView.delegate = self;
        
        self.webView.loadRequest(URLRequest(url: URL(string: Global.sharedInstance.textContentUrl!)!));
        self.sizeToFit();
        
    }
    


    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    deinit {


    }
    
    //MARK:UIWebViewDelegate
    func webViewDidFinishLoad(_ webView: UIWebView) {
        let h:CGFloat = self.webView.scrollView.contentSize.height;
        delegate?.hyContenListTopTableViewCellWebViewDidFinishedLoad(cell: self, sectionIndex: 0, height: h);
    }
    
    //MARK:UIScrollViewDelegate
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        let offset = scrollView.contentOffset;
        let bounds = scrollView.bounds;
        let size = scrollView.contentSize;
        let inset = scrollView.contentInset;
        
        let currentOffset = offset.y + bounds.size.height - inset.bottom;
        let maxOffset = size.height;
        
        if(maxOffset == currentOffset ){
           // print("reach bottom");
            delegate?.hyContenListTopTableViewCellWebViewReachBottom(cell: self, sectionIndex: 0);
        }
    }
    
}
