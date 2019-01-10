//
//  BMPlayer.swift
//  Pods
//
//  Created by BrikerMan on 16/4/28.
//
//

import UIKit
import SnapKit
import MediaPlayer
import ReachabilitySwift


enum BMPlayerState {
    case notSetURL      // 未设置URL
    case readyToPlay    // 可以播放
    case buffering      // 缓冲中
    case bufferFinished // 缓冲完毕
    case playedToTheEnd // 播放结束
    case error          // 出现错误
}

/// 枚举值，包含水平移动方向和垂直移动方向
enum BMPanDirection: Int {
    case horizontal = 0
    case vertical   = 1
}

enum BMPlayerItemType {
    case url
    case bmPlayerItem
}


open class BMPlayer: UIView {
    
    weak var parmaVc:UIViewController?

    open var backBlock:(() -> Void)?
    
    var videoItem: BMPlayerItem!
    
    var currentDefinition = 0
    
    //add code by wzc
    var parentView:UIView!
    var landPlayView:UIView!
    var cellBkPlayView:UIView!
    
    /// 是否显示全屏按钮
    fileprivate var showFullScreenBtn  = true;
    
    var playerLayer: BMPlayerLayerView?
    
    var controlView: BMPlayerCustomControlView!
    
    fileprivate var customControllView: BMPlayerCustomControlView?
    
    var playerItemType = BMPlayerItemType.url
    
    var videoItemURL: URL!
    
    var videoTitle = ""
    
    var isFullScreen:Bool {
        get {
            return UIApplication.shared.statusBarOrientation.isLandscape
        }
    }
    
    /// 滑动方向
    fileprivate var panDirection = BMPanDirection.horizontal
    /// 音量滑竿
    fileprivate var volumeViewSlider: UISlider!
    
    fileprivate let BMPlayerAnimationTimeInterval:Double                = 4.0
    fileprivate let BMPlayerControlBarAutoFadeOutTimeInterval:Double    = 0.5
    
    /// 用来保存时间状态
    fileprivate var sumTime         : TimeInterval = 0
    fileprivate var totalDuration   : TimeInterval = 0
    //modify to public by wzc
    open var currentPosition : TimeInterval = 0
    fileprivate var shouldSeekTo    : TimeInterval = 0
    
    fileprivate var isURLSet        = false
    fileprivate var isSliderSliding = false
    fileprivate var isPauseByUser   = false
    fileprivate var isVolume        = false
    fileprivate var isMaskShowing   = false
    fileprivate var isSlowed        = false
    fileprivate var isMirrored      = false
    
    
    // MARK: - Public functions
    /**
     直接使用URL播放
     
     - parameter url:   视频URL
     - parameter title: 视频标题
     */
    open func playWithURL(_ url: URL, title: String = "") {
        
        
        playerItemType              = BMPlayerItemType.url
        videoItemURL                = url
        controlView.playerTitleLabel?.text = title
        
        if BMPlayerConf.shouldAutoPlay {
            playerLayer?.videoURL   = videoItemURL
            isURLSet                = true
        } else {
            controlView.hideLoader()
        }
    }
    
    open func showCover(coverImg img:UIImage) -> Void {
        controlView.showCoverWithImage(img);
    }
    
    open func playWithURLDirect(_ url: URL, title: String = "") {
        
        self.volumeViewSlider.value = 0;
        self.pause();
        let draggedTime = CMTimeMake(Int64(0), 1)
        self.playerLayer?.player?.seek(to: draggedTime, toleranceBefore: kCMTimeZero, toleranceAfter: kCMTimeZero, completionHandler: { (finished) in
            
        })
        playerLayer?.playerItem?.cancelPendingSeeks();
        playerLayer?.playerItem?.asset.cancelLoading();
        self.playerLayer?.player?.replaceCurrentItem(with: nil);
        isURLSet = false;
        
        playerItemType              = BMPlayerItemType.url
        videoItemURL                = url
        controlView.playerTitleLabel?.text = title
        
        if BMPlayerConf.shouldAutoPlay {
            playerLayer?.videoURL   = videoItemURL
            isURLSet                = true
        } else {
            controlView.hideLoader()
        }
        
        

    }
    
    /**
     播放可切换清晰度的视频
     
     - parameter items: 清晰度列表
     - parameter title: 视频标题
     - parameter definitionIndex: 起始清晰度
     */
    open func playWithPlayerItem(_ item:BMPlayerItem, definitionIndex: Int = 0) {
        playerItemType              = BMPlayerItemType.bmPlayerItem
        videoItem                   = item
        controlView.playerTitleLabel?.text = item.title
        currentDefinition           = definitionIndex
        controlView.prepareChooseDefinitionView(item.resource, index: definitionIndex)
        
        if BMPlayerConf.shouldAutoPlay {
            playerLayer?.videoURL   = videoItem.resource[currentDefinition].playURL
            isURLSet                = true
        } else {
            //modify code by wzc
            if("" != item.cover){
                controlView.showCoverWithLink(item.cover)
            }
        }
    }
    
    
    /**
     使用自动播放，参照pause函数
     */
    open func autoPlay() {
        if !isPauseByUser && isURLSet {
            self.play()
        }
    }
    
    
    /**
     手动播放
     */
    
    func pl() -> Void {
        if !self.isURLSet {
            if self.playerItemType == BMPlayerItemType.bmPlayerItem {
                self.playerLayer?.videoURL       = self.videoItem.resource[self.currentDefinition].playURL
            } else {
                self.playerLayer?.videoURL       = self.videoItemURL
            }
            self.controlView.hideCoverImageView()
            self.isURLSet                = true
        }
        self.playerLayer?.play()
        self.isPauseByUser = false
    }
    
    open func play() {
        
        let beWifiPlay = UserDefaults.standard.bool(forKey: WIFI_PLAY_KEY);
        if(true == beWifiPlay){
            //说明只可以在WIFI情况下播放 这时候检测网络状态
            
//            do{
//
//            }catch let error as NSError{
//                print("reachability 失败：\(error)");
//                
//            }catch{
//                
//            }
            
            let reachability = Reachability()!;
            if reachability.isReachableViaWiFi{
                //是WIFI 可以播放
                self.pl();
                //                    //提示不可以播放
                //                    CommonTools.showModalMessage("您现在要使用流量观看视频吗？", title: "注意", vc: self.parmaVc!, hasCancelBtn: true, hr: {
                //                        (beOK:Bool) -> Void in
                //                        if(true == beOK){
                //                            self.pl();
                //                            NSUserDefaults.standardUserDefaults().setBool(false, forKey: WIFI_PLAY_KEY);
                //                        }
                //                    });
            }else if reachability.isReachableViaWWAN{
                //移动网络
                //提示不可以播放
                CommonTools.showModalMessage("您现在要使用流量观看视频吗？", title: "注意", vc: self.parmaVc!, hasCancelBtn: true, hr: {
                    (beOK:Bool) -> Void in
                    if(true == beOK){
                        self.pl();
                        UserDefaults.standard.set(false, forKey: WIFI_PLAY_KEY);
                    }
                });
                
            }else{
                //无网络
                //提示检查网络
                CommonTools.showModalMessage("请检查网络！", title: "注意", vc: self.parmaVc!, hasCancelBtn: false, hr: {
                    (beOK:Bool) -> Void in
                    if(true == beOK){
                        
                    }
                });
            }
        }else{
            self.pl();
        }
        
    }
    
    
//
//    /**
//     手动播放
//     */
//    public func play() {
//
//
//       // var beCanPlay:Bool = true;
//        
//        dataSource?.isCanPlay(self, callBack: {
//            (beOK:Bool) -> Void in
//            if(false == beOK){
//                return;
//            }
//        });
// 
//        
//        if !isURLSet {
//            if playerItemType == BMPlayerItemType.BMPlayerItem {
//                playerLayer?.videoURL       = videoItem.resource[currentDefinition].playURL
//            } else {
//                playerLayer?.videoURL       = videoItemURL
//            }
//            controlView.hideCoverImageView()
//            isURLSet                = true
//        }
//        playerLayer?.play()
//        isPauseByUser = false
//    }
    
    /**
     暂停
     
     - parameter allowAutoPlay: 是否允许自动播放，默认不允许，若允许则在调用autoPlay的情况下开始播放。否则autoPlay不会进行播放。
     */
    open func pause(allowAutoPlay allow: Bool = false) {
        playerLayer?.pause()
        isPauseByUser = !allow
    }
    
    /**
     seek
     
     - parameter to: target time
     */
    open func seek(_ to:TimeInterval) {
        self.shouldSeekTo = to
        playerLayer?.seekToTime(to, completionHandler: {
            self.shouldSeekTo = 0
        })
    }
    
    /**
     开始自动隐藏UI倒计时
     */
    open func autoFadeOutControlBar() {
        NSObject.cancelPreviousPerformRequests(withTarget: self, selector: #selector(hideControlViewAnimated), object: nil)
        self.perform(#selector(hideControlViewAnimated), with: nil, afterDelay: BMPlayerAnimationTimeInterval)
    }
    
    /**
     取消UI自动隐藏
     */
    open func cancelAutoFadeOutControlBar() {
        NSObject.cancelPreviousPerformRequests(withTarget: self)
    }
    
    /**
     旋转屏幕时更新UI
     */
    open func updateUI(_ isFullScreen: Bool) {
        controlView.updateUI(isFullScreen)
    }
    
    /**
     准备销毁，适用于手动隐藏等场景
     */
    open func prepareToDealloc() {
        playerLayer?.prepareToDeinit()
    }
    
    //add code by wzc 
    open func reNewPlayer(){
        self.preparePlayer();
    }
    
    
    // MARK: - Action Response
    fileprivate func playStateDidChanged() {
        if isSliderSliding { return }
        if let player = playerLayer {
            if player.isPlaying {
                autoFadeOutControlBar()
                controlView.playerPlayButton?.isSelected = true
            } else {
                controlView.playerPlayButton?.isSelected = false
            }
        }
    }
    
    
    @objc fileprivate func hideControlViewAnimated() {
        UIView.animate(withDuration: BMPlayerControlBarAutoFadeOutTimeInterval, animations: {
            self.controlView.hidePlayerUIComponents()
            if self.isFullScreen {
               // UIApplication.shared.setStatusBarHidden(true, with: UIStatusBarAnimation.fade)
            }
        }, completion: { (_) in
            self.isMaskShowing = false
        }) 
    }
    
    @objc fileprivate func showControlViewAnimated() {
        UIView.animate(withDuration: BMPlayerControlBarAutoFadeOutTimeInterval, animations: {
            self.controlView.showPlayerUIComponents()
            //UIApplication.shared.setStatusBarHidden(false, with: UIStatusBarAnimation.fade)
        }, completion: { (_) in
            self.autoFadeOutControlBar()
            self.isMaskShowing = true
        }) 
    }
    
    @objc fileprivate func tapGestureTapped(_ sender: UIGestureRecognizer) {
        if isMaskShowing {
            hideControlViewAnimated()
            autoFadeOutControlBar()
        } else {
            showControlViewAnimated()
        }
    }
    
    @objc fileprivate func panDirection(_ pan: UIPanGestureRecognizer) {
        // 根据在view上Pan的位置，确定是调音量还是亮度
        let locationPoint = pan.location(in: self)
        
        // 我们要响应水平移动和垂直移动
        // 根据上次和本次移动的位置，算出一个速率的point
        let velocityPoint = pan.velocity(in: self)
        
        // 判断是垂直移动还是水平移动
        switch pan.state {
        case UIGestureRecognizerState.began:
            // 使用绝对值来判断移动的方向
            
            let x = fabs(velocityPoint.x)
            let y = fabs(velocityPoint.y)
            
            if x > y {
                self.panDirection = BMPanDirection.horizontal
                
                // 给sumTime初值
                if let player = playerLayer?.player {
                    let time = player.currentTime()
                    self.sumTime = TimeInterval(time.value) / TimeInterval(time.timescale)
                }
                
            } else {
                self.panDirection = BMPanDirection.vertical
                if locationPoint.x > self.bounds.size.width / 2 {
                    self.isVolume = true
                } else {
                    self.isVolume = false
                }
            }
            
        case UIGestureRecognizerState.changed:
            cancelAutoFadeOutControlBar()
            switch self.panDirection {
            case BMPanDirection.horizontal:
                self.horizontalMoved(velocityPoint.x)
            case BMPanDirection.vertical:
                self.verticalMoved(velocityPoint.y)
            }
        case UIGestureRecognizerState.ended:
            // 移动结束也需要判断垂直或者平移
            // 比如水平移动结束时，要快进到指定位置，如果这里没有判断，当我们调节音量完之后，会出现屏幕跳动的bug
            switch (self.panDirection) {
            case BMPanDirection.horizontal:
                controlView.hideSeekToView()
                isSliderSliding = false
                playerLayer?.seekToTime(self.sumTime, completionHandler: nil)
                // 把sumTime滞空，不然会越加越多
                self.sumTime = 0.0
                
            //                controlView.showLoader()
            case BMPanDirection.vertical:
                self.isVolume = false
            }
        default:
            break
        }
    }
    
    fileprivate func verticalMoved(_ value: CGFloat) {
        self.isVolume ? (self.volumeViewSlider.value -= Float(value / 10000)) : (UIScreen.main.brightness -= value / 10000)
    }
    
    fileprivate func horizontalMoved(_ value: CGFloat) {
        isSliderSliding = true
        if let playerItem = playerLayer?.playerItem {
            // 每次滑动需要叠加时间，通过一定的比例，使滑动一直处于统一水平
            self.sumTime = self.sumTime + TimeInterval(value) / 100.0 * (TimeInterval(self.totalDuration)/400)
            
            let totalTime       = playerItem.duration
            
            // 防止出现NAN
            if totalTime.timescale == 0 { return }
            
            let totalDuration   = TimeInterval(totalTime.value) / TimeInterval(totalTime.timescale)
            if (self.sumTime > totalDuration) { self.sumTime = totalDuration}
            if (self.sumTime < 0){ self.sumTime = 0}
            
            let targetTime      = formatSecondsToString(sumTime)
            
            controlView.playerTimeSlider?.value      = Float(sumTime / totalDuration)
            controlView.playerCurrentTimeLabel?.text       = targetTime
            controlView.showSeekToView(sumTime, isAdd: value > 0)
            
        }
    }
    
    @objc fileprivate func progressSliderTouchBegan(_ sender: UISlider)  {
        playerLayer?.onTimeSliderBegan()
        isSliderSliding = true
    }
    
    @objc fileprivate func progressSliderValueChanged(_ sender: UISlider)  {
        self.pause(allowAutoPlay: true)
        cancelAutoFadeOutControlBar()
    }
    
    @objc fileprivate func progressSliderTouchEnded(_ sender: UISlider)  {
        isSliderSliding = false
        autoFadeOutControlBar()
        let target = self.totalDuration * Double(sender.value)
        playerLayer?.seekToTime(target, completionHandler: nil)
        autoPlay()
    }
    
    @objc fileprivate func backButtonPressed(_ button: UIButton) {
        if isFullScreen {
            fullScreenButtonPressed(nil)
        } else {
            //playerLayer?.prepareToDeinit()
           // backBlock?()
        }
    }
    
    @objc fileprivate func slowButtonPressed(_ button: UIButton) {
        autoFadeOutControlBar()
        if isSlowed {
            self.playerLayer?.player?.rate = 1.0
            self.isSlowed = false
            self.controlView.playerSlowButton?.setTitle("慢放", for: UIControlState())
        } else {
            self.playerLayer?.player?.rate = 0.5
            self.isSlowed = true
            self.controlView.playerSlowButton?.setTitle("正常", for: UIControlState())
        }
    }
    
    @objc fileprivate func mirrorButtonPressed(_ button: UIButton) {
        autoFadeOutControlBar()
        if isMirrored {
            self.playerLayer?.transform = CGAffineTransform(scaleX: 1.0, y: 1.0)
            self.isMirrored = false
            self.controlView.playerMirrorButton?.setTitle("镜像", for: UIControlState())
        } else {
            self.playerLayer?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
            self.isMirrored = true
            self.controlView.playerMirrorButton?.setTitle("正常", for: UIControlState())
        }    }
    
    @objc fileprivate func replayButtonPressed() {
        playerLayer?.seekToTime(0, completionHandler: {
            
        })
        self.play()
    }
    
    @objc fileprivate func playButtonPressed(_ button: UIButton) {
        if button.isSelected {
            self.pause()
        } else {
            self.play()
        }
    }
    
    @objc fileprivate func onOrientationChanged() {
        self.updateUI(isFullScreen)
    }
    
    //add code by wzc 
    func setFullScreenBtnState(beShow show:Bool) -> Void {
        showFullScreenBtn = show;
        controlView.playerFullScreenButton?.isHidden = !showFullScreenBtn;
    }
    
    func setBackBtnState(beShow show:Bool) -> Void {

        controlView.playerBackButton?.isHidden = !show;
        controlView.playerTitleLabel?.isHidden = !show;
    }
    
    func goFullScreen(_ beFull:Bool) -> Void {
        if(true == beFull){
           // self.changePlayerScreenState(self.parentView, needRotation: CGFloat(M_PI_2), isfullscreen: true);
            
            UIView.animate(withDuration: 0.5, animations: { () -> Void in
                
                self.landPlayView.addSubview(self.parentView);
                
                var views:[String:AnyObject] = [String:AnyObject]();
                views["parentView"] = self.parentView;
                
                self.landPlayView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[parentView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
                self.landPlayView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[parentView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
                self.parentView.transform = CGAffineTransform.identity
                
                
            }, completion: { (finish) -> Void in
                
            }) 
        }else{
            UIView.animate(withDuration: 0.5, animations: { () -> Void in
                
                self.cellBkPlayView.addSubview(self.parentView);
                
                var views:[String:AnyObject] = [String:AnyObject]();
                views["parentView"] = self.parentView;
                
                self.cellBkPlayView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[parentView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
                self.cellBkPlayView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[parentView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
                self.parentView.transform = CGAffineTransform.identity
                
                
            }, completion: { (finish) -> Void in
                
            }) 
        }
    }
    
    @objc fileprivate func fullScreenButtonPressed(_ button: UIButton?) {
        if !isURLSet {
            //            self.play()
        }
        controlView.updateUI(!self.isFullScreen)

        if isFullScreen {
            
            UIView.animate(withDuration: 0.5, animations: { () -> Void in
                
                self.cellBkPlayView.addSubview(self.parentView);
                
                var views:[String:AnyObject] = [String:AnyObject]();
                views["parentView"] = self.parentView;
                
                self.cellBkPlayView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[parentView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
                self.cellBkPlayView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[parentView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
                self.parentView.transform = CGAffineTransform.identity
                
                
            }, completion: { (finish) -> Void in
                
            }) 
            
            
            UIDevice.current.setValue(UIInterfaceOrientation.portrait.rawValue, forKey: "orientation")
            //UIApplication.shared.setStatusBarHidden(false, with: UIStatusBarAnimation.fade)
            //FIXME:状态栏方向
           // UIApplication.shared.setStatusBarOrientation(UIInterfaceOrientation.portrait, animated: false)
        } else {
        
            
            //self.changePlayerScreenState(self.parentView, needRotation: CGFloat(M_PI_2), isfullscreen: true);
            
            UIView.animate(withDuration: 0.5, animations: { () -> Void in
                
                self.landPlayView.addSubview(self.parentView);
                
                var views:[String:AnyObject] = [String:AnyObject]();
                views["parentView"] = self.parentView;
                
                self.landPlayView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[parentView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
                self.landPlayView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[parentView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
                self.landPlayView.transform = CGAffineTransform.identity
                
                self.parentView.transform = CGAffineTransform.identity;
                self.parentView.transform = CGAffineTransform(rotationAngle: CGFloat(M_PI_2) );
                
//                self.landPlayView.addSubview(self.parentView);
//
//                var views:[String:AnyObject] = [String:AnyObject]();
//                views["parentView"] = self.parentView;
//            
//                self.landPlayView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("H:|-0-[parentView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//                self.landPlayView.addConstraints(NSLayoutConstraint.constraintsWithVisualFormat("V:|-0-[parentView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
//                self.parentView.transform = CGAffineTransformIdentity

                
            }, completion: { (finish) -> Void in
                
            }) 
            
            UIDevice.current.setValue(UIInterfaceOrientation.landscapeRight.rawValue, forKey: "orientation")
           // UIApplication.shared.setStatusBarHidden(false, with: UIStatusBarAnimation.fade)
            //FIXME:状态栏方向
           // UIApplication.shared.setStatusBarOrientation(UIInterfaceOrientation.landscapeRight, animated: false)
        }
    }
    
    func changePlayerScreenState(_ inView: UIView, needRotation angle: CGFloat?, isfullscreen: Bool?) {
        
        
        
        UIView.animate(withDuration: 0.5, animations: { () -> Void in
            
            
            self.landPlayView.addSubview(inView);
            //inView.addSubview(self)
            
            var views:[String:AnyObject] = [String:AnyObject]();
            views["inView"] = inView;
            
            

            self.landPlayView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-0-[inView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
            self.landPlayView.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-0-[inView]-0-|", options: NSLayoutFormatOptions(), metrics: nil, views: views));
            
            self.parentView.transform = CGAffineTransform.identity
            //self.parentView.transform = CGAffineTransformMakeRotation(angle ?? 0)
            //print("windows frame = \(inView.bounds)    w==\(UIApplication.sharedApplication().keyWindow!.frame)   fff=\(UIScreen.mainScreen().bounds)");
            //self.parentView.frame = inView.bounds;
            //self.playerLayer!.frame = inView.bounds;


            
        }, completion: { (finish) -> Void in
//            if isfullscreen != nil {
//                self.isFullscreen = isfullscreen!
//                NSNotificationCenter.defaultCenter().postNotificationName(
//                    isfullscreen! ? WLPlayerDidEnterFullscreenNotification : WLPlayerDidExitFullscreenNotification, object: nil)
//            }
        }) 
    }
    
    // MARK: - 生命周期
    deinit {
        playerLayer?.pause()
        playerLayer?.prepareToDeinit()
        NotificationCenter.default.removeObserver(self, name: NSNotification.Name.UIApplicationDidChangeStatusBarOrientation, object: nil)
    }
    
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required public init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        initUI()
        initUIData()
        configureVolume()
        preparePlayer()
    }
    
    public convenience init (customControllView: BMPlayerCustomControlView?) {
        self.init(frame:CGRect.zero)
        self.customControllView = customControllView
        initUI()
        initUIData()
        configureVolume()
        preparePlayer()
    }
    
    public convenience init() {
        self.init(customControllView:nil)
    }


    
    fileprivate func formatSecondsToString(_ secounds: TimeInterval) -> String {
        let Min = Int(secounds / 60)
        let Sec = Int(secounds.truncatingRemainder(dividingBy: 60))
        return String(format: "%02d:%02d", Min, Sec)
    }
    
    // MARK: - 初始化
    fileprivate func initUI() {
        self.backgroundColor = UIColor.black
        
        if let customView = customControllView {
            controlView = customView
        } else {
            controlView =  BMPlayerControlView()
        }
        
        addSubview(controlView.getView)
        controlView.updateUI(isFullScreen)
        controlView.delegate = self
        controlView.getView.snp.makeConstraints { (make) in
            make.edges.equalTo(self)
        }
        
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.tapGestureTapped(_:)))
        self.addGestureRecognizer(tapGesture)
        
        let panGesture = UIPanGestureRecognizer(target: self, action: #selector(self.panDirection(_:)))
        //        panGesture.delegate = self
        self.addGestureRecognizer(panGesture)
    }
    
    fileprivate func initUIData() {
        controlView.playerPlayButton?.addTarget(self, action: #selector(self.playButtonPressed(_:)), for: UIControlEvents.touchUpInside)
        controlView.playerFullScreenButton?.addTarget(self, action: #selector(self.fullScreenButtonPressed(_:)), for: UIControlEvents.touchUpInside)
        controlView.playerBackButton?.addTarget(self, action: #selector(self.backButtonPressed(_:)), for: UIControlEvents.touchUpInside)
        controlView.playerTimeSlider?.addTarget(self, action: #selector(progressSliderTouchBegan(_:)), for: UIControlEvents.touchDown)
        controlView.playerTimeSlider?.addTarget(self, action: #selector(progressSliderValueChanged(_:)), for: UIControlEvents.valueChanged)
        controlView.playerTimeSlider?.addTarget(self, action: #selector(progressSliderTouchEnded(_:)), for: [UIControlEvents.touchUpInside,UIControlEvents.touchCancel, UIControlEvents.touchUpOutside])
        controlView.playerSlowButton?.addTarget(self, action: #selector(slowButtonPressed(_:)), for: .touchUpInside)
        controlView.playerMirrorButton?.addTarget(self, action: #selector(mirrorButtonPressed(_:)), for: .touchUpInside)
        
        NotificationCenter.default.addObserver(self, selector: #selector(self.onOrientationChanged), name: NSNotification.Name.UIApplicationDidChangeStatusBarOrientation, object: nil)
        
    }
    
    fileprivate func configureVolume() {
        let volumeView = MPVolumeView()
        for view in volumeView.subviews {
            if let slider = view as? UISlider {
                self.volumeViewSlider = slider
            }
        }
    }
    
    fileprivate func preparePlayer() {
        playerLayer = BMPlayerLayerView()
        insertSubview(playerLayer!, at: 0)
        playerLayer!.snp.makeConstraints { (make) in
            make.edges.equalTo(self)
        }
        playerLayer!.delegate = self
        controlView.showLoader()
        self.layoutIfNeeded()
    }
}

extension BMPlayer: BMPlayerLayerViewDelegate {
    func bmPlayer(player: BMPlayerLayerView, playerIsPlaying playing: Bool) {
        playStateDidChanged()
    }
    
    func bmPlayer(player: BMPlayerLayerView ,loadedTimeDidChange  loadedDuration: TimeInterval , totalDuration: TimeInterval) {
        self.totalDuration = totalDuration
        BMPlayerManager.shared.log("loadedTimeDidChange - \(loadedDuration) - \(totalDuration)")
        controlView.playerProgressView?.setProgress(Float(loadedDuration)/Float(totalDuration), animated: true)
    }
    
    func bmPlayer(player: BMPlayerLayerView, playerStateDidChange state: BMPlayerState) {
        BMPlayerManager.shared.log("playerStateDidChange - \(state)")
        switch state {
        case BMPlayerState.readyToPlay:
            if shouldSeekTo != 0 {
                playerLayer?.seekToTime(shouldSeekTo, completionHandler: {
                    
                    
                    
                })
                shouldSeekTo = 0
            }
        case BMPlayerState.buffering:
            cancelAutoFadeOutControlBar()
            controlView.showLoader()
            playStateDidChanged()
        case BMPlayerState.bufferFinished:
            controlView.hideLoader()
            playStateDidChanged()
            autoPlay()
        case BMPlayerState.playedToTheEnd:
            self.pause()
            controlView.showPlayToTheEndView()
        default:
            break
        }
    }
    
    func bmPlayer(player: BMPlayerLayerView, playTimeDidChange currentTime: TimeInterval, totalTime: TimeInterval) {
        self.currentPosition = currentTime
        BMPlayerManager.shared.log("playTimeDidChange - \(currentTime) - \(totalTime)")
        totalDuration = totalTime
        if isSliderSliding {
            return
        }
        controlView.playerCurrentTimeLabel?.text = formatSecondsToString(currentTime)
        controlView.playerTotalTimeLabel?.text = formatSecondsToString(totalTime)
        
        controlView.playerTimeSlider?.value    = Float(currentTime) / Float(totalTime)
    }
}

extension BMPlayer: BMPlayerControlViewDelegate {
    public func controlViewDidChooseDefition(_ index: Int) {
        shouldSeekTo                = currentPosition
        playerLayer?.resetPlayer()
        playerLayer?.videoURL       = videoItem.resource[index].playURL
        currentDefinition           = index
    }
    
    public func controlViewDidPressOnReply() {
        replayButtonPressed()
    }
}

