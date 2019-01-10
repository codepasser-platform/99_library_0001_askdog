//
//  AudioPlayer.swift
//  AskDog
//
//  Created by Symond on 16/9/22.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import AVFoundation

class AudioPlayer: NSObject {
    
    var player:AVPlayer!
    var playerItem:AVPlayerItem!
    var isPlaying:Bool = false;
    
    override init() {
        super.init();
        
        
    }
    
    
    /// 播放本地音乐
    ///
    /// - parameter path: 本地音乐路径
    func setAudioLocalFilePath(audioPath path:String) -> Void {
        let ur:URL = URL(fileURLWithPath: path);
        let movieAsset:AVAsset = AVAsset(url: ur);
        playerItem = AVPlayerItem(asset: movieAsset);
        player = AVPlayer(playerItem: playerItem);
        //        playerItem.addObserver(self, forKeyPath: "playbackBufferEmpty", options:
        //            NSKeyValueObservingOptions.new,
        //                               context: nil);
        //        playerItem.addObserver(self, forKeyPath: "playbackLikelyToKeepUp", options:
        //            NSKeyValueObservingOptions.new,
        //                               context: nil);
    }
    
    /// 播放网络音乐
    ///
    /// - parameter url: 网络音乐url
    func setAudioUrl(audioUrl url:String) -> Void {
        let ur:URL = URL(string: url)!;
        let movieAsset:AVAsset = AVAsset(url: ur);
        playerItem = AVPlayerItem(asset: movieAsset);
        player = AVPlayer(playerItem: playerItem);
//        playerItem.addObserver(self, forKeyPath: "playbackBufferEmpty", options:
//            NSKeyValueObservingOptions.new,
//                               context: nil);
//        playerItem.addObserver(self, forKeyPath: "playbackLikelyToKeepUp", options:
//            NSKeyValueObservingOptions.new,
//                               context: nil);
    }
    
//    override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
//        if(keyPath == "playbackLikelyToKeepUp"){
//            log.ln("playbackLikelyToKeepUp")/;
//        }else if(keyPath == "playbackBufferEmpty"){
//            log.ln("playbackBufferEmpty")/;
//        }
//    }
    
    func play() -> Void {
        player.play();
        self.isPlaying = true;
        
    }
    
    func pause() -> Void {
        player.pause();
        self.isPlaying = false;
    }
    
    func releasePlayer() -> Void {
        playerItem.cancelPendingSeeks();
        playerItem.asset.cancelLoading();
//        self.playerItem.removeObserver(self, forKeyPath: "playbackBufferEmpty", context: nil);
//        self.playerItem.removeObserver(self, forKeyPath: "playbackLikelyToKeepUp", context: nil);
    }
    
    deinit {
        log.ln("deinit \(self)")/;
    }

}
