//
//  HYVideoShareUploadVideoTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/8/22.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import NVActivityIndicatorView

protocol HYVideoShareUploadVideoTableViewCellDelegate  :class{
    func hyVideoShareUploadVideoTableViewCellStopBtnClicked(cell ce:HYVideoShareUploadVideoTableViewCell,sectionIndex section:Int) -> Void ;
    func hyVideoShareUploadVideoTableViewCellAddMusicBtnClicked(cell ce:HYVideoShareUploadVideoTableViewCell,sectionIndex section:Int) -> Void ;

}


class HYVideoShareUploadVideoTableViewCell: UITableViewCell {

    @IBOutlet weak var progressView: EAColourfulProgressView!
    @IBOutlet weak var lblProgress: UILabel!
    @IBOutlet weak var btnStop: UIButton!
    @IBOutlet weak var imgView: UIImageView!
    @IBOutlet weak var player: BMPlayer!
    @IBOutlet weak var tipInfoView: UIView!
    @IBOutlet weak var btnAddMusic: UIButton!
    
    @IBOutlet weak var uploadImgV: UIImageView!
    @IBOutlet weak var lblUploadTitle: UILabel!
    @IBOutlet weak var lblUploadInfo: UILabel!
    
    var pos:Int = 0;
    
    /// 0 初始状态 1视频播放状态 2 上传视频状态  3选择完视频 未播放状态 可以添加音乐
    var nState:Int = 0;
    
    
    weak var delegate:HYVideoShareUploadVideoTableViewCellDelegate?
    
    /// 0 初始状态 1视频播放状态 2 上传视频状态  3选择完视频 未播放状态 可以添加音乐 
    func setUIState(state n:Int) -> Void {
        nState = n;
        if(0 == n){
            //初始状态
            self.uploadImgV.isHidden = false;
            self.lblUploadTitle.isHidden = false;
            self.lblUploadInfo.isHidden = false;
            
            //上传进度
            self.lblProgress.isHidden = true;
            self.progressView.isHidden = true;
            self.btnStop.isHidden = true;
            
            self.tipInfoView.isHidden = false;
            self.player.isHidden = true;
            //选择音乐按钮
            self.btnAddMusic.isHidden = true;
            
            //视频封面
            //self.imgView.isHidden = true;
        
        }else if(1 == n){
            //1视频播放状态
            
            //初始状态
            self.uploadImgV.isHidden = true;
            self.lblUploadTitle.isHidden = true;
            self.lblUploadInfo.isHidden = true;
            
            //上传进度
            self.lblProgress.isHidden = true;
            self.progressView.isHidden = true;
            self.btnStop.isHidden = true;
            
            self.tipInfoView.isHidden = true;
            self.player.isHidden = false;
            //选择音乐按钮
            self.btnAddMusic.isHidden = false;
            
            //视频封面
            //self.imgView.isHidden = true;
            
        
        }else if(2 == n){
            //上传视频状态

            self.uploadImgV.isHidden = true;
            self.lblUploadTitle.isHidden = true;
            self.lblUploadInfo.isHidden = true;
            
            //上传进度
            self.lblProgress.isHidden = false;
            self.progressView.isHidden = false;
            self.btnStop.isHidden = false;
            
            self.tipInfoView.isHidden = false;
            self.player.isHidden = false;
            //选择音乐按钮
            self.btnAddMusic.isHidden = true;
            
            //视频封面
            //self.imgView.isHidden = false;
            self.player.pause();
            //self.player.autoFadeOutControlBar();
            
        }else if(3 == n){
            //选择完视频 未播放状态 可以添加音乐
            
            self.uploadImgV.isHidden = true;
            self.lblUploadTitle.isHidden = true;
            self.lblUploadInfo.isHidden = true;
            
            //上传进度不显示
            self.lblProgress.isHidden = true;
            self.progressView.isHidden = true;
            self.btnStop.isHidden = true;
            
            self.tipInfoView.isHidden = true;
            self.player.isHidden = false;
            //选择音乐按钮
            self.btnAddMusic.isHidden = false;
            
            //视频封面
            //self.imgView.isHidden = true;
        }
        
//        
//        else if(4 == n){
//            //4 合成完音乐 待播放 未上传
//            
//            self.uploadImgV.isHidden = true;
//            self.lblUploadTitle.isHidden = true;
//            self.lblUploadInfo.isHidden = true;
//            
//            //上传进度不显示
//            self.lblProgress.isHidden = true;
//            self.progressView.isHidden = true;
//            self.btnStop.isHidden = true;
//            
//            self.tipInfoView.isHidden = true;
//            self.player.isHidden = false;
//            //不显示选择音乐按钮
//            self.btnAddMusic.isHidden = false;
//            
//            //视频封面
//            self.imgView.isHidden = true;
//        }
    }
    
    @IBAction func btnAddMusicClicked(_ sender: UIButton) {
        delegate?.hyVideoShareUploadVideoTableViewCellAddMusicBtnClicked(cell: self, sectionIndex: 0);
    }
    
    @IBAction func btnStopClicked(_ sender: UIButton) {
        delegate?.hyVideoShareUploadVideoTableViewCellStopBtnClicked(cell: self, sectionIndex: 0);
    }
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        BMPlayerConf.allowLog = false;
        BMPlayerConf.shouldAutoPlay = false;
        BMPlayerConf.tintColor = UIColor.white;
        BMPlayerConf.topBarShowInCase = BMPlayerTopBarShowCase.always;
        BMPlayerConf.loaderType  = NVActivityIndicatorType.ballRotateChase;
        
        self.player.setFullScreenBtnState(beShow: false);
        self.player.setBackBtnState(beShow: false);

        
        
        self.progressView.maximumValue = 100;
        self.lblProgress.text = "";
        self.lblProgress.isHidden = true;
        self.progressView.isHidden = true;
        self.btnStop.isHidden = true;
        self.progressView.currentValue = 100;
        self.progressView.layer.borderColor = CommonTools.rgbColor(red: 0, green: 170, blue: 239).cgColor;
        //self.imgView.isHidden = true;
        
        //NSTimer.scheduledTimerWithTimeInterval(1, target: self, selector: #selector(timerFired), userInfo: nil, repeats: true);
        
        self.setUIState(state: 0);
        
    }
    
    func setVideoData(videoUrl url:String,videoImg img:UIImage) -> Void {

        
        //self.player.reNewPlayer();
        
        self.player.seek(0);
        self.player.playWithURLDirect(URL(fileURLWithPath: url));
        self.player.showCover(coverImg: img);
        
        
//        let resource0 = BMPlayerItemDefinitionItem(url: URL(fileURLWithPath: url),
//                                                   definitionName: "");
//        let item    = BMPlayerItem(title: "",
//                                   resource: [resource0],
//                                   cover: "");
//        self.player.playWithPlayerItem(item);
        
        //self.imgView.image = img;
    }
    
    func timerFired() -> Void {
        
        self.lblProgress.isHidden = false;
        self.progressView.isHidden = false;
        self.btnStop.isHidden = false;
        
        
        print("timerFired in \(pos)");
        
        pos += 1;
        
        let str:String = "\(pos)%";
        
        print("upload video \(str)");
        
        self.lblProgress.text = "上传\(str)";
        
        self.progressView.update(toCurrentValue: 100 - pos, animated: true);
        
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    
    func setProgressViewVlaue(_ value:Int,animated be:Bool = true) -> Void {
        
        self.lblProgress.isHidden = false;
        self.progressView.isHidden = false;
        self.btnStop.isHidden = false;
        
        
        let currentPos = 100 - value;
        
        let str:String = "\(value)%";
        
        print("upload video \(str)");
        
        self.lblProgress.text = "上传\(str)";
        
        self.progressView.update(toCurrentValue: currentPos, animated: true);

    }
    
//    func hideDefaultUI() -> Void {
//        self.uploadImgV.isHidden = true;
//        self.lblUploadTitle.isHidden = true;
//        self.lblUploadInfo.isHidden = true;
//    }
    
//    /// 隐藏上传界面
//    func hideUploadUI() -> Void {
//        
//        self.tipInfoView.isHidden = true;
//        self.imgView.isHidden = true;
//        
////        self.lblProgress.isHidden = true;
////        self.progressView.isHidden = true;
////        self.btnStop.isHidden = true;
//        
//    }
    
//    func showUploadUI() -> Void {
//        self.tipInfoView.isHidden = false;
//        self.imgView.isHidden = false;
//        
//        self.lblProgress.isHidden = false;
//        self.progressView.isHidden = false;
//        self.btnStop.isHidden = false;
//        
////        self.lblProgress.isHidden = true;
////        self.progressView.isHidden = true;
////        self.btnStop.isHidden = true;
//        
//    }
    
    override func layoutSubviews() {
        super.layoutSubviews();
       // print("h = \(self.frame.size.height)  w == \(self.frame.size.width)");
    }
    
    deinit {
        player.pause();
        player.prepareToDealloc();
        print("HYVideoShareUploadVideoTableViewCell Deinit")
    }
    
}
