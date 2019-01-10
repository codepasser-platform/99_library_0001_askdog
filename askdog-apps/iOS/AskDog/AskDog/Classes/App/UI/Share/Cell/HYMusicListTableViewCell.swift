//
//  HYMusicListTableViewCell.swift
//  AskDog
//
//  Created by Symond on 16/9/21.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import Alamofire


class HYMusicListTableViewCell: UITableViewCell {
    
    var player:AudioPlayer?
    
    var dataModel:HYMusicListDataModel?


    @IBOutlet weak var loading: UIActivityIndicatorView!
    @IBOutlet weak var lblLine: UILabel!
    @IBOutlet weak var btnDownload: UIButton!
    @IBOutlet weak var lblName: UILabel!
    @IBOutlet weak var btnPlay: UIButton!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        self.btnPlay.setTitle("", for: .normal);
        
        NotificationCenter.default.addObserver(self, selector: #selector(self.selectedNotify(_:)), name: NSNotification.Name(rawValue: "musicSelectedNotify"), object: nil);
        
    }
    
    func selectedNotify(_ notif: Notification){
        let obj = notif.object;
        let v:HYMusicListTableViewCell = obj as! HYMusicListTableViewCell!
        if(v != self){
            self.btnPlay.isSelected = false;
            if let pl:AudioPlayer = player{
                pl.pause();
            }
        }
        
    }

    @IBAction func btnProgressViewStopClicked(_ sender: EVCircularProgressView) {
    }
    @IBAction func btnPlayClicked(_ sender: UIButton) {
        if let pl:AudioPlayer = player{
            
            if(false == self.btnPlay.isSelected){
                pl.play();
                self.btnPlay.isSelected = true;
                NotificationCenter.default.post(name: NSNotification.Name(rawValue: "musicSelectedNotify"), object: self);
            }else{
                pl.pause();
                self.btnPlay.isSelected = false;
            }
        }
    }
    
    @IBAction func btnDownloadClicked(_ sender: UIButton) {
        //self.btnDownload.isHidden = true;
        if let url = self.dataModel?.url{
            var fileName:String = "";
            if let name = self.dataModel?.name{
                fileName = name;
            }else{
                //FIXME:这里要随机生成名称
                fileName = "未命名";
            }

            HYMusicFileManager.sharedInstance.downloadMusic(musicUrl: url, fileName: fileName,progressCallBack:{
                (strProgress:String) -> Void in
                self.btnDownload.setTitle(strProgress, for: .normal);
                },resultCallBack :{
                    (beResult:Bool,fileUrl:URL) -> Void in
                    if(true == beResult){
                        log.ln("下载完成")/;
                        self.btnDownload.isEnabled = false;
                    }else{
                        log.ln("下载失败")/;
                        self.btnDownload.isEnabled = true;
                        self.btnDownload.setTitle("下载", for: .normal);
                    }
            });
        }
        
        
//        if let url = self.dataModel?.url{
//            Alamofire.download(url).downloadProgress { (prog) in
//                //log.ln("total = \(prog.totalUnitCount)  cur = \(prog.completedUnitCount)")/;
//                
//                //let nProg:Int = prog.totalUnitCount;
//                self.btnDownload.setTitle(prog.localizedDescription, for: .normal);
//                
//                }.responseData { (resData) in
//                    log.ln("下载完成")/;
//                    
//                    var fileName:String = "";
//                    if let name = self.dataModel?.name{
//                        fileName = name;
//                    }else{
//                        //FIXME:这里要随机生成名称
//                        fileName = "未命名";
//                    }
//                    
//                    if let data = resData.result.value{
//                        if(true == HYMusicFileManager.sharedInstance.saveMusic(fileName: fileName, fileData: data)){
//                            //保存文件信息
//                            self.btnDownload.isEnabled = false;
//                        }
//                    }
//                    
//            };
//        }
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func setData(_ da:HYMusicListDataModel){
        self.dataModel = da;
        if let name = da.name{
            self.lblName.text = name;
        }

        if let url = da.url{
            //判断音乐是否缓存
            if(true == HYMusicFileManager.sharedInstance.ifFileHasCached(musicUrl: url)){
                //本地播放
                self.btnDownload.isEnabled = false;
                player = AudioPlayer();
                let strUrl:String = HYMusicFileManager.sharedInstance.getLocalFilePath(musicUrl: url);
                log.ln("local file url = \(strUrl)")/;
                player?.setAudioLocalFilePath(audioPath: strUrl);
            }else{
                //网络播放
                self.btnDownload.isEnabled = true;
                player = AudioPlayer();
                player?.setAudioUrl(audioUrl: url);
            }

        }
    }
    
    deinit {
        log.ln("1312319999999")/;

        NotificationCenter.default.removeObserver(self);
        player?.pause();
        player?.releasePlayer();
    }
    
}
