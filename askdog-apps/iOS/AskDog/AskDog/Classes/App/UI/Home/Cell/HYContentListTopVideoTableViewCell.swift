//
//  HYContentListTopVideoTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/12.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import NVActivityIndicatorView


protocol HYContentListTopVideoTableViewCellDelegate  :class{
    func hyContentListTopVideoTableViewCellShareBtnClicked(cell ce:HYContentListTopVideoTableViewCell,sectionIndex section:Int) -> Void ;
    func hyContentListTopVideoTableViewCellPayBtnClicked(cell ce:HYContentListTopVideoTableViewCell,sectionIndex section:Int) -> Void ;
    func hyContentListTopVideoTableViewCellHeaderBtnClicked(cell ce:HYContentListTopVideoTableViewCell,sectionIndex section:Int) -> Void ;
    func hyContentListTopVideoTableViewCellReportBtnClicked(cell ce:HYContentListTopVideoTableViewCell,sectionIndex section:Int) -> Void ;
    func hyContentListTopVideoTableViewCellZanBtnClicked(cell ce:HYContentListTopVideoTableViewCell,sectionIndex section:Int) -> Void ;
    func hyContentListTopVideoTableViewCellCaiBtnClicked(cell ce:HYContentListTopVideoTableViewCell,sectionIndex section:Int) -> Void ;
    func hyContentListTopVideoTableViewCellSubscriptionBtnClicked(cell ce:HYContentListTopVideoTableViewCell,sectionIndex section:Int) -> Void ;
}




class HYContentListTopVideoTableViewCell: UITableViewCell {
    
    
    @IBOutlet weak var zhezhaoView: UIView!
    @IBOutlet weak var lblPriceInfo: UILabel!
    @IBOutlet weak var lblBtnPay: UIButton!
    
    
    @IBOutlet weak var bkView: UIView!
    @IBOutlet weak var lblSubject: UILabel!
    @IBOutlet weak var contentBkView: UIView!
    @IBOutlet weak var playerPlayView: UIView!
    @IBOutlet weak var bmPlayer: BMPlayer!
    @IBOutlet weak var codingView: UIView!
    
    @IBOutlet weak var headerImgWidth: NSLayoutConstraint!
    @IBOutlet weak var headerImgV: UIImageView!
    @IBOutlet weak var lblUserName: UILabel!
    @IBOutlet weak var lblSharedInfo: UILabel!
    @IBOutlet weak var lblChannelName: UILabel!
    @IBOutlet weak var dingyueBkView: UIView!
    @IBOutlet weak var btnDingyue: UIButton!
    @IBOutlet weak var lblDingyueNumber: UILabel!
    @IBOutlet weak var lblZanNumber: UILabel!
    @IBOutlet weak var btnZan: UIButton!
    @IBOutlet weak var lblCaiNumber: UILabel!
    @IBOutlet weak var btnCai: UIButton!
    @IBOutlet weak var lblDingyueSepatorLine: UILabel!
    @IBOutlet weak var vipImageView: UIImageView!
    
    var dataModel:HYExperienceDetailDataModel!
    
    var scribableState:SubScribableState = .subScribable_Disable;
    var voteState:VoteState = .voteState_None;
    var avPlayer:MyVideoPlayer!
    weak var vc:UIViewController!
    
    weak var delegate:HYContentListTopVideoTableViewCellDelegate?
    
    func videoPause() -> Void {
        self.bmPlayer.pause();
    }
    
    func videoPlay() -> Void {
        self.bmPlayer.play();
    }
    
    
    func setZanCaiDic(_ dic:NSDictionary) -> Void {
        if let up_vote_count = dic["up_vote_count"]{
            let nCnt = up_vote_count as! Int;
            dataModel.up_vote_count = nCnt;
            
            let str:String = String(nCnt);
            lblZanNumber.text = str;
        }
        if let down_vote_count = dic["down_vote_count"]{
            let nCnt = down_vote_count as! Int;
            dataModel.down_vote_count = nCnt;
            
            let str:String = String(nCnt);
            lblCaiNumber.text = str;
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
        
        let dic:[String:String] = ["LD":"流畅","SD":"标清","HD":"高清","FHD":"全高清","UHD":"超清"];
        
        dataModel = da;
        print("type = \(dataModel.content?.type)");
        
//        //先判断视频价钱
//        if let price = dataModel.price{
//            
//            
//            if(price > 0){
//                self.lblBtnPay.hidden = false;
//                self.lblPriceInfo.hidden = false;
//                self.zhezhaoView.hidden = false;
//                let f:Float = Float(price) / 100;
//                //显示价钱
//                let s:String = String(format: "%.2f",f);
//                self.lblPriceInfo.text = "经验无价,\(s)元学习";
//            }else{
//                self.lblBtnPay.hidden = false;
//                self.zhezhaoView.hidden = false;
//                self.lblPriceInfo.text = "";
//                self.lblPriceInfo.hidden = true;
//            }
//        }
        
        //先判断收费
        if (false == dataModel.isPayed){
            if let price = dataModel.price{
                self.lblBtnPay.isHidden = false;
                self.lblPriceInfo.isHidden = false;
                self.zhezhaoView.isHidden = false;
                let f:Float = Float(price) / 100;
                //显示价钱
                let s:String = String(format: "%.2f",f);
                self.lblPriceInfo.text = "经验无价,\(s)元学习";
            }
        }else{
            self.lblBtnPay.isHidden = true;
            self.lblPriceInfo.isHidden = true;
            self.zhezhaoView.isHidden = true;
        }
        
//        if let price = dataModel.price{
//            if(price > 0){
//                self.lblBtnPay.hidden = false;
//                self.lblPriceInfo.hidden = false;
//                self.zhezhaoView.hidden = false;
//                let f:Float = Float(price) / 100;
//                //显示价钱
//                let s:String = String(format: "%.2f",f);
//                self.lblPriceInfo.text = "经验无价,\(s)元学习";
//                
//            }
//        }

        //准备视频播放资源
        
        if(0 == dataModel.content?.transcode_videos?.count){
            //视频在转码
            bmPlayer.isHidden = true;
            self.codingView.isHidden = false;
            
        }else{
            bmPlayer.isHidden = false;
            self.codingView.isHidden = true;
            
            var resources:[BMPlayerItemDefinitionProtocol] = [BMPlayerItemDefinitionProtocol]();
            
            for item in (dataModel.content?.transcode_videos)!{
                print("url = \(item.url)");
                
                var strQingxidu:String = "";
                CommonTools.stringCheckNullObject(dic[item.resolution!], callBack: {
                    (str:String) -> Void in
                    strQingxidu = str;
                });
                
                let res = BMPlayerItemDefinitionItem(url: URL(string: item.url!)!,
                                                     definitionName: strQingxidu);
                resources.append(res);
            }
            
            //取视频截图
            var coverUrl:String = "";
            
            for item in (dataModel.content?.snapshots)!{
                coverUrl = item.url!;
                break;
            }
            
            
            print("coverUrl = \(coverUrl)");
            
            //let url1:NSURL = NSURL(string: "http://baobab.wdjcdn.com/1457162012752491010143.mp4")!;
            
            let item    = BMPlayerItem(title: dataModel.subject!,
                                       resource: resources,
                                       cover: coverUrl);
            
            bmPlayer.playWithPlayerItem(item);
        }
        
        
        if let subject:String = dataModel.subject{
//            
//            if(true == CommonTools.compareDataInActivity()){
//                
//                let attrString:NSMutableAttributedString = NSMutableAttributedString(string: subject);
//                
//                if let rFind:Range = subject.range(of: "#2016中秋#"){
//                    let locType:Int = subject.characters.distance(from: subject.startIndex, to: (rFind.lowerBound));
//                    let distanceType:Int = (<#T##String.CharacterView corresponding to your index##String.CharacterView#>.distance(from: rFind.lowerBound, to: (rFind.upperBound)));
//                    // print("\(range?.startIndex)  \(range?.endIndex)  \(distance)  \(loc)");
//                    let rType:NSRange = NSMakeRange(locType, distanceType);
//                    
//                    attrString.addAttributes([NSForegroundColorAttributeName:CommonTools.rgbColor(red: 0, green: 170, blue: 239)], range: rType)
//                    
//                    lblSubject.attributedText = attrString;
//                }else{
//                    let rType:NSRange = NSMakeRange(0, subject.characters.count);
//                    attrString.addAttributes([NSForegroundColorAttributeName:CommonTools.rgbColor(red: 50, green: 87, blue: 121)], range: rType)
//                    lblSubject.attributedText = attrString;
//                }
//                
//            }else{
//                lblSubject.text = subject;
//            }
            
             lblSubject.text = subject;

        }
        
        if let author:HYAuthorDataModel = dataModel.author{
            if let avatarUrl:String = author.avatar{
                headerImgV.sd_setImage(with: URL(string: avatarUrl),placeholderImage:UIImage(named: "AvatarDefault"));
            }
            if let name:String = author.name{
                lblUserName.text = name;
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
        lblSharedInfo.text = "分享于 " + strTm + " 浏览数 " + String(nViewCount);
        
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
                if let subscribed:Bool = channel.subscribed{
                    if(true == subscribed){
                        // 已经订阅，显示取消订阅按钮
                        setSubScribableState(.subScribable_Already);
                    }else{
                        if let subscribable:Bool = channel.subscribable{
                            if(true == subscribable){
                                //可以订阅，显示订阅按钮
                                setSubScribableState(.subScribable_Can);
                            }else{
                                // 不能订阅，显示灰色按钮
                                setSubScribableState(.subScribable_Disable);
                            }
                        }
                    }
                }
            }
            
            
//            if(false == Global.sharedInstance.isLogined){
//                setSubScribableState(.SubScribable_Can);
//            }else{
//                if let mineObj:Bool = channel.mine{
//                    if(true == mineObj){
//                        setSubScribableState(.SubScribable_Disable);
//                    }else{
//                        if let subscribable:Bool = channel.subscribable{
//                            if(true == subscribable){
//                                setSubScribableState(.SubScribable_Can);
//                            }else{
//                                setSubScribableState(.SubScribable_Already);
//                            }
//                        }
//                    }
//                }else{
//                    if let subscribable:Bool = channel.subscribable{
//                        if(true == subscribable){
//                            setSubScribableState(.SubScribable_Can);
//                        }else{
//                            setSubScribableState(.SubScribable_Already);
//                        }
//                    }
//                }
//            }
            
            
        }
        //设置踩赞状态
        if let up_vote_count = dataModel.up_vote_count{
            lblZanNumber.text = String(up_vote_count);
        }
        if let down_vote_count = dataModel.down_vote_count{
            lblCaiNumber.text = String(down_vote_count);
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
            btnDingyue.setTitle("取消订阅", for: UIControlState());
            dingyueBkView.layer.borderColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164).cgColor;
            lblDingyueSepatorLine.backgroundColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164);
            btnDingyue.backgroundColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164);
            lblDingyueNumber.textColor = CommonTools.rgbColor(red: 135, green: 146, blue: 164);
            btnDingyue.isEnabled = true;
        case .subScribable_Can:
            btnDingyue.setTitle("订阅频道", for: UIControlState());
            dingyueBkView.layer.borderColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231).cgColor;
            lblDingyueSepatorLine.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231);
            btnDingyue.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231);
            lblDingyueNumber.textColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231);
            btnDingyue.isEnabled = true;
        case .subScribable_Disable:
            btnDingyue.setTitle("订阅频道", for: UIControlState());
            dingyueBkView.layer.borderColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5).cgColor;
            lblDingyueSepatorLine.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5);
            btnDingyue.backgroundColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5);
            lblDingyueNumber.textColor = CommonTools.rgbColor(red: 78, green: 97, blue: 231, alpha: 0.5);
            btnDingyue.isEnabled = false;
        }
    }
    
    func setVoteStatus(_ status:VoteState) -> Void {
        switch status {
        case .voteState_Up:
            btnZan.isSelected = true;
            btnCai.isSelected = false;
            
            lblCaiNumber.textColor = UIColor.darkGray;
            lblZanNumber.textColor = CommonTools.rgbColor(red: 0, green: 170, blue: 239);
        case .voteState_Down:
            btnZan.isSelected = false;
            btnCai.isSelected = true;
            
            lblZanNumber.textColor = UIColor.darkGray;
            lblCaiNumber.textColor = CommonTools.rgbColor(red: 0, green: 170, blue: 239);
        case .voteState_None:
            btnZan.isSelected = false;
            btnCai.isSelected = false;
            
            lblZanNumber.textColor = UIColor.darkGray;
            lblCaiNumber.textColor = UIColor.darkGray;
        }
    }
    
    func setPtr(_ vc:UIViewController) -> Void {
        self.vc = vc;
        self.bmPlayer.parmaVc = self.vc;
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        BMPlayerConf.allowLog = false;
        BMPlayerConf.shouldAutoPlay = false;
        BMPlayerConf.tintColor = UIColor.white;
        BMPlayerConf.topBarShowInCase = BMPlayerTopBarShowCase.horizantalOnly;
        BMPlayerConf.loaderType  = NVActivityIndicatorType.ballRotateChase;
        
        //默认收费界面隐藏
        self.lblBtnPay.layer.cornerRadius = 5;
        self.lblBtnPay.isHidden = true;
        self.zhezhaoView.isHidden = true;
        self.lblPriceInfo.isHidden = true;
        
        self.bmPlayer.parentView = self.playerPlayView;
        self.bmPlayer.cellBkPlayView = self.contentBkView;
        self.bmPlayer.setBackBtnState(beShow: false);
        
        
        headerImgV.layer.cornerRadius = headerImgWidth.constant/2.0;
        headerImgV.layer.masksToBounds = true;
        
        lblDingyueNumber.text = "";
        lblSubject.text = "";
        lblUserName.text = "";
        lblSharedInfo.text = "";
        lblChannelName.text = "";
        lblZanNumber.text = "0";
        lblCaiNumber.text = "0";
        
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
    }


    @IBAction func btnPayClicked(_ sender: UIButton) {
        delegate?.hyContentListTopVideoTableViewCellPayBtnClicked(cell: self, sectionIndex: 0);
    }
    
    @IBAction func btnHeaderClicked(_ sender: UIButton) {
        delegate?.hyContentListTopVideoTableViewCellHeaderBtnClicked(cell: self, sectionIndex: 0);
    }
    @IBAction func btnReportClicked(_ sender: UIButton) {
        delegate?.hyContentListTopVideoTableViewCellReportBtnClicked(cell: self, sectionIndex: 0);
    }
    @IBAction func btnEditClicked(_ sender: UIButton) {
    }
    @IBAction func btnSharedClicked(_ sender: UIButton) {
        delegate?.hyContentListTopVideoTableViewCellShareBtnClicked(cell: self, sectionIndex: 0);
    }
    @IBAction func btnCaiClicked(_ sender: UIButton) {
         delegate?.hyContentListTopVideoTableViewCellCaiBtnClicked(cell: self, sectionIndex: 0);
    }
    @IBAction func btnZanClicked(_ sender: UIButton) {
        delegate?.hyContentListTopVideoTableViewCellZanBtnClicked(cell: self, sectionIndex: 0);
    }
    @IBAction func btnDingyueClicked(_ sender: UIButton) {
        delegate?.hyContentListTopVideoTableViewCellSubscriptionBtnClicked(cell: self, sectionIndex: 0);
    }
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    

    
    deinit {
        // 使用手势返回的时候，调用下面方法手动销毁
        bmPlayer.pause();
        bmPlayer.prepareToDealloc();
        print("HYContenListTopTableViewCell Deinit")
        
    }
    
}
