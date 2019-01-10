//
//  MyVideoPlayer.swift
//  AskDog
//
//  Created by Symond on 16/8/8.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit
import AVKit
import AVFoundation

class MyVideoPlayer: UIView {
    
    var player:AVPlayer!
    var playerLayer:AVPlayerLayer!
    var playControlBkView: UIView!
    var playerView: UIView!
    var btnPlay:UIButton!
    var btnPause:UIButton!
    var sliderView:UIView!

    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    
    func setupAvPlayer() -> Void {
        
        let filePath:String! = Bundle.main.path(forResource: "test", ofType: "mp4");
        
        let url:URL = URL(fileURLWithPath: filePath);
        
        let movieAsset:AVAsset = AVAsset(url: url);
        let playerItem:AVPlayerItem = AVPlayerItem(asset: movieAsset);
        player = AVPlayer(playerItem: playerItem);
        playerLayer = AVPlayerLayer(player: player);
        playerLayer.frame = CGRect.zero;
        //playerLayer.frame = CGRectMake(0, 0, 312, 175);
        print("frame=\(playerView.bounds)");
        playerLayer.videoGravity = AVLayerVideoGravityResizeAspect;
        playerView.layer.addSublayer(playerLayer);
        

       // player.play();
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame);
        
        self.backgroundColor = UIColor.yellow;
        self.translatesAutoresizingMaskIntoConstraints = false;
        
        playerView = UIView(frame: CGRect.zero);
        playerView.translatesAutoresizingMaskIntoConstraints = false;
        playerView.backgroundColor = UIColor.clear;
        self.addSubview(playerView);
        
        self.setupAvPlayer();
        
        var views:[String:AnyObject] = [String:AnyObject]();
        views["playerView"] = playerView;
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[playerView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[playerView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        
        //下面的控件条
        playControlBkView = UIView(frame: CGRect.zero);
        playControlBkView.translatesAutoresizingMaskIntoConstraints = false;
        playControlBkView.backgroundColor  = UIColor.black;
        playControlBkView.alpha = 0.8;
        self.addSubview(playControlBkView);
        
        views["playControlBkView"] = playControlBkView;
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[playControlBkView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        self.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[playControlBkView(35)]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
        //播放按钮
        btnPlay = UIButton(type: UIButtonType.custom);
        btnPlay.frame = CGRect.zero;
        btnPlay.addTarget(self, action: #selector(btnPlayClicked), for: UIControlEvents.touchUpInside);
        //btnPlay.backgroundColor = UIColor.yellowColor();
        btnPlay.translatesAutoresizingMaskIntoConstraints = false;
        btnPlay.setImage(UIImage(named: "player_play"), for: UIControlState());
        playControlBkView.addSubview(btnPlay);
        
        var viewControls:[String:AnyObject] = [String:AnyObject]();
        viewControls["btnPlay"] = btnPlay;
        //navBarView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:|-[btnNavBarBack]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        playControlBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[btnPlay(35)]", options: NSLayoutFormatOptions(), metrics: nil, views: viewControls));
        playControlBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[btnPlay(35)]", options: NSLayoutFormatOptions(), metrics: nil, views: viewControls));
        //暂停按钮
        btnPause = UIButton(type: UIButtonType.custom);
        btnPause.frame = CGRect.zero;
        btnPause.addTarget(self, action: #selector(btnPauseClicked), for: UIControlEvents.touchUpInside);
        //btnPlay.backgroundColor = UIColor.yellowColor();
        btnPause.translatesAutoresizingMaskIntoConstraints = false;
        btnPause.setImage(UIImage(named: "player_pause"), for: UIControlState());
        playControlBkView.addSubview(btnPause);
        btnPause.isHidden = true;
        
        viewControls["btnPause"] = btnPause;
        //navBarView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:|-[btnNavBarBack]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        playControlBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[btnPause(35)]", options: NSLayoutFormatOptions(), metrics: nil, views: viewControls));
        playControlBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[btnPause(35)]", options: NSLayoutFormatOptions(), metrics: nil, views: viewControls));
        
        //进度条背景view
        sliderView = UIView(frame: CGRect.zero);
        sliderView.backgroundColor = UIColor.red;
        playControlBkView.addSubview(sliderView);
        
        viewControls["sliderView"] = sliderView;
        sliderView.translatesAutoresizingMaskIntoConstraints = false;
        //navBarView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:|-[btnNavBarBack]", options: NSLayoutFormatOptions(), metrics: nil, views: views1));
        playControlBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-35-[sliderView]-80-|", options: NSLayoutFormatOptions(), metrics: nil, views: viewControls));
        playControlBkView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[sliderView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: viewControls));
        
        //左侧已播放进度条
        

    }
    
    func btnPlayClicked(_ sender: UIButton) -> Void {
        print("btnPlayClicked");
        player.play();
        btnPlay.isHidden = true;
        btnPause.isHidden = false;
    }
    
    func btnPauseClicked(_ sender: UIButton) -> Void {
        print("btnPauseClicked");
        player.pause();
        btnPlay.isHidden = false;
        btnPause.isHidden = true;
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder);

        //fatalError("init(coder:) has not been implemented")
    }
    
    override func layoutSubviews() {
        super.layoutSubviews();
        playerLayer.frame = playerView.bounds;
    }
    
//    class func creatView() -> HYVideoPlayerView {
//        
//    }

}
