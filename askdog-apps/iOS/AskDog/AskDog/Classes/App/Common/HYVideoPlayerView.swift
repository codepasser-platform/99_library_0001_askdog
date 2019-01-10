//
//  HYVideoPlayerView.swift
//  TestSwift
//
//  Created by Symond on 16/8/7.
//  Copyright © 2016年 WZC. All rights reserved.
//

//http://www.jianshu.com/p/6d73d3b16918

import UIKit
import AVKit
import AVFoundation
//import SnapKit

class HYVideoPlayerView: UIView {
    
    @IBOutlet weak var contentView: UIView!
    var view:UIView!
    var filePath:String!
    var player:AVPlayer!
    var playerLayer:AVPlayerLayer!
    @IBOutlet weak var playControlBkView: UIView!

    @IBOutlet weak var playerView: UIView!
    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    
    override func awakeFromNib() {
        filePath = Bundle.main.path(forResource: "test", ofType: "mp4");
        //self.setupAvPlayer();
        
//        playerView.snp_makeConstraints(closure: {
//            (make:ConstraintMaker) -> Void in
//            make.size.equalTo(CGSizeMake(200, 100));
//            make.center.equalTo(self.view);
//        })
        self.view.bringSubview(toFront: playControlBkView);
    }
    
    func initialFromXib() -> UIView {
        //let view = NSBundle.mainBundle().loadNibNamed("HYVideoPlayerView", owner: nil, options: nil).first as! UIView;
        
        self.translatesAutoresizingMaskIntoConstraints = false;
        let bundle = Bundle(for: type(of: self))
        let nib = UINib(nibName: String(describing: type(of: self)), bundle: bundle)
        let view = nib.instantiate(withOwner: self, options: nil)[0] as! UIView
        return view
        

    
    }
    
    func setupAvPlayer() -> Void {
        
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

        player.play();
    }
    
    override func layoutSubviews() {
        super.layoutSubviews();
        
        view.frame = bounds;
//        playerView.frame = view.bounds;
//        playerLayer.frame = playerView.bounds;
////        print("frame=\(view.bounds)");
////        print("frame1111=\(playerView.bounds)");
        playControlBkView.frame = CGRect(x: 0, y: 0, width: view.frame.size.width, height: 44);//playerView.frame.size.height-44
    }
    
    func setupSubViews()->Void{
        view = initialFromXib();
        //view.translatesAutoresizingMaskIntoConstraints = false;
        view.autoresizingMask = [UIViewAutoresizing.flexibleWidth, UIViewAutoresizing.flexibleHeight];
        addSubview(view);
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame);
        setupSubViews();
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder);
        setupSubViews();
        //fatalError("init(coder:) has not been implemented")
    }
    
    class func creatView() -> HYVideoPlayerView {
        return Bundle.main.loadNibNamed("HYVideoPlayerView", owner: nil, options: nil)!.first as! HYVideoPlayerView
    }

}
