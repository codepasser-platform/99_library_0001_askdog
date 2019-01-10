//
//  HYSplashViewController.swift
//  AskDog
//
//  Created by Symond on 16/9/5.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class HYSplashViewController: HYBaseViewController,UIScrollViewDelegate,HYSplashViewDelegate,CAAnimationDelegate {
    
    
    @IBOutlet weak var lblCountDownWidth: NSLayoutConstraint!
    @IBOutlet weak var lblCountDown: UILabel!
    @IBOutlet weak var btnSkip: UIButton!
    @IBOutlet weak var pageCtrl: UIPageControl!
    @IBOutlet weak var scrollView: UIScrollView!
    var dataArray:NSMutableArray = NSMutableArray();
    var timer:Timer!
    var nCountDown:Int = 5;

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.dataArray.add(["img":"app1","url":"http://www.baidu.com","hasBtn":false]);
        self.dataArray.add(["img":"app2","url":"http://www.baidu.com","hasBtn":false]);
        self.dataArray.add(["img":"app3","url":"http://www.baidu.com","hasBtn":false]);
        self.dataArray.add(["img":"app4","url":"http://www.baidu.com","hasBtn":true]);

        // Do any additional setup after loading the view.
        
        self.scrollView.showsVerticalScrollIndicator = false;
        self.scrollView.showsHorizontalScrollIndicator = false;
        let nCnt:Int = self.dataArray.count > 0 ? self.dataArray.count : 1 ;
        self.scrollView.contentSize = CGSize(width: DEVICE_WIDTH * CGFloat(nCnt), height: DEVICE_HEIGHT);
        //添加页面
        for (index,value) in self.dataArray.enumerated(){
            let dic:NSDictionary = value as! NSDictionary;
            let fX:CGFloat = CGFloat(index) * DEVICE_WIDTH;
            let fr:CGRect = CGRect(x: fX, y: 0, width: DEVICE_WIDTH, height: DEVICE_HEIGHT);
            let v:HYSplashView = HYSplashView(frame: fr);
            v.setData(dic);
            v.delegate = self;
            self.scrollView.addSubview(v);
        }
        self.pageCtrl.numberOfPages = nCnt;
        
        self.lblCountDown.text = "\(self.nCountDown)秒";
        //self.lblCountDown.layer.cornerRadius = lblCountDownWidth.constant/2.0;
        //self.lblCountDown.layer.masksToBounds = true;
        self.lblCountDown.layer.borderColor = UIColor.white.cgColor;
        self.lblCountDown.layer.borderWidth = 1;
        
        //timer = NSTimer(timeInterval: 1, target: self, selector: #selector(timerFiredMethod), userInfo: nil, repeats: true);
        
        self.timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(timerFiredMethod), userInfo: nil, repeats: true);
        
        //let runloop:NSRunLoop = NSRunLoop();
        
    }
    
    func timerFiredMethod() -> Void {
        nCountDown -= 1;
        if(nCountDown < 0){
            self.btnSkipClicked(self.btnSkip);
        }else{
            let str:String = "\(nCountDown)秒";
            print(str);
            self.lblCountDown.text = str;
        }
    }

    @IBAction func btnSkipClicked(_ sender: UIButton) {
        print("btnSkipClicked");
        
        self.lblCountDown.text = "";
        self.lblCountDown.isHidden = true;
        self.btnSkip.isHidden = true;
        self.pageCtrl.isHidden = true;
        if let tm:Timer = timer{
            tm.invalidate();
            self.timer = nil;
        }

        let animation:CATransition = CATransition();
        animation.delegate = self;
        animation.duration = 0.7;
        animation.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseInEaseOut);
        animation.type = kCATransitionReveal;
        animation.subtype = kCATransitionFromRight;
        self.scrollView.isHidden = true;
        self.view.layer.add(animation, forKey: "animation");
        self.perform(#selector(removeSelfFromSuper), with: nil, afterDelay: 0.7);
        
    }
    
    func removeSelfFromSuper() -> Void {
        self.view.removeFromSuperview();
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    override func isShowNavBar() -> Bool {
        return false;
    }
    
    //MARK:UIScrollViewDelegate
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        
        let fW:CGFloat = DEVICE_WIDTH;
        let f:CGFloat = (scrollView.contentOffset.x - fW/2)/fW;
        let d = floor(f);
        let page = d + 1;
        self.pageCtrl.currentPage = Int(page);
        //let page:Int = (floorf((scrollView.contentOffset.x - fW/2)/fW) + 1) as Int;
    }
    
    //MARK:HYSplashViewDelegate
    func hySplashViewbtnClicked(_ v: HYSplashView) {
        self.btnSkipClicked(self.btnSkip);
    }
    
    deinit{
        print("deinit HYSplashViewController");
    }

}
