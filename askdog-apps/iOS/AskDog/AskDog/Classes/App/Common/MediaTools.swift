//
//  MediaTools.swift
//  AskDog
//
//  Created by Symond on 16/9/21.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import Foundation
import Photos
import SVProgressHUD

/// 多媒体处理类
class MediaTools: NSObject {
    
    /// 计时器
    var composeVideoTimer : Timer?
    
    /// 合并音视频对像
    var assetExport:AVAssetExportSession!
    
    static let sharedInstance = MediaTools();
    fileprivate override init(){};
    
    
    func videoComposeVideoAudio(video vUrl:String,audio aUrl:String,callBack fun:@escaping ((URL) -> Void)) -> Void {
        
        
        let fileVideo:URL = URL(fileURLWithPath: vUrl);
        let fileAudio:URL = URL(fileURLWithPath: aUrl);
        
        let outputUrl:String  = (NSHomeDirectory() + "/Documents/tempOutPut.mp4");
        let outPut:URL! = URL(fileURLWithPath: outputUrl);
        //delete old video 
        var isDirectory: ObjCBool = false
        if(true == FileManager.default.fileExists(atPath: outPut.path, isDirectory: &isDirectory)){
            if(true == FileManager.default.isDeletableFile(atPath: outPut.path)){
                do{
                    try FileManager.default.removeItem(atPath: outPut.path);
                }catch let err{
                    
                    log.error(err)/;
                    return;
                }
                
            }
        }
        
        
        //video
        let videoAsset:AVURLAsset = AVURLAsset(url: fileVideo);
        let videoTmRange:CMTimeRange = CMTimeRange(start: kCMTimeZero, duration: videoAsset.duration);
        
        let mixCom:AVMutableComposition = AVMutableComposition();
        let videoCompTrack:AVMutableCompositionTrack = mixCom.addMutableTrack(withMediaType: AVMediaTypeVideo, preferredTrackID: kCMPersistentTrackID_Invalid);
        let tracksVideo:[AVAssetTrack] = videoAsset.tracks(withMediaType: AVMediaTypeVideo);
        if(tracksVideo.count <= 0){
            return;
        }
        
        do{
            try videoCompTrack.insertTimeRange(videoTmRange, of: tracksVideo[0], at: kCMTimeInvalid);
        }catch{
            return;
        }
        
        //audio
        let audioAsset:AVURLAsset = AVURLAsset(url: fileAudio);
        let audioTmRange:CMTimeRange = CMTimeRange(start: kCMTimeZero, duration: videoAsset.duration);// 取和视频一样的长度
        let audioCompTrack:AVMutableCompositionTrack = mixCom.addMutableTrack(withMediaType: AVMediaTypeAudio, preferredTrackID: kCMPersistentTrackID_Invalid);
        let tracksAudio:[AVAssetTrack] = audioAsset.tracks(withMediaType: AVMediaTypeAudio);
        if(tracksAudio.count <= 0){
            return;
        }
        do{
            try audioCompTrack.insertTimeRange(audioTmRange, of: tracksAudio[0], at: kCMTimeInvalid);
        }catch{
            return;
        }
        
        //output
        assetExport = AVAssetExportSession(asset: mixCom, presetName: AVAssetExportPresetMediumQuality);
        assetExport.outputFileType = AVFileTypeQuickTimeMovie;
        assetExport.outputURL = outPut;
        assetExport.shouldOptimizeForNetworkUse = true;
        
        SVProgressHUD.showProgress(0, status: "合成中...");
        //1秒一更新进度
        
        composeVideoTimer  = Timer.scheduledTimer(timeInterval: 1.0, target: self, selector: #selector(composeTimerFiredMethod), userInfo: nil, repeats: true);

        //start
        assetExport?.exportAsynchronously(completionHandler: {
            () -> Void in
            
            self.composeVideoTimer?.invalidate();
            SVProgressHUD.dismiss();
            
            if(self.assetExport.status == .waiting){
                log.ln("assetExport.status == .waiting...")/;
            }else if(self.assetExport.status == .exporting){
                log.ln("assetExport.status == .exporting...")/;
            }else if(self.assetExport.status == .completed){
                log.ln("assetExport.status == .completed")/;
                log.ln("合成视频OK new video url = \(outPut)")/;
                fun(outPut);
  
            }else if(self.assetExport.status == .failed){
                log.ln("assetExport.status == .failed")/;
                log.error(self.assetExport.error!)/;
            }else if(self.assetExport.status == .cancelled){
                log.ln("asself.setExport.status == .cancelled")/;
            }else if(self.assetExport.status == .unknown){
                log.ln("assetExport.status == .unknown")/;
            }
            
            
        });
        

        
    }
    
    func composeTimerFiredMethod() -> Void {
        SVProgressHUD.showProgress(self.assetExport.progress, status: "合成中...");
    }

}
