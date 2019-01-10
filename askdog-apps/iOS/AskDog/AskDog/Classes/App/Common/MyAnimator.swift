//
//  MyAnimator.swift
//  AskDog
//
//  Created by Symond on 16/8/5.
//  Copyright © 2016年 Hooying. All rights reserved.
//

import UIKit

class MyAnimator: NSObject,UIViewControllerAnimatedTransitioning{
    
    let animationDuration = 2.0;
    var operation:UINavigationControllerOperation = .push
    weak var storedContext: UIViewControllerContextTransitioning?
    
    func transitionDuration(using transitionContext: UIViewControllerContextTransitioning?) -> TimeInterval{
        return animationDuration;
    }
    func animateTransition(using transitionContext: UIViewControllerContextTransitioning){
        storedContext = transitionContext;
        
        //let fromVC = transitionContext.viewControllerForKey(UITransitionContextFromViewControllerKey) as! HYBaseViewController;
        let toVC = transitionContext.viewController(forKey: UITransitionContextViewControllerKey.to) as! HYBaseViewController;
        
        transitionContext.containerView.addSubview(toVC.view);
        
        let s:CGSize = UIScreen.main.bounds.size;
        let fromFrme:CGRect = CGRect(x: 0, y: 0-s.height, width: s.width, height: s.height);
        let toFrame:CGRect = CGRect(x: 0, y: 0, width: s.width, height: s.height);
        
        let animation = CABasicAnimation(keyPath: "frame");
        animation.fromValue = NSValue(cgRect:fromFrme);
        animation.toValue = NSValue(cgRect:toFrame);
        
        animation.duration = animationDuration;
       // animation.delegate = self;
        animation.fillMode = kCAFillModeForwards;
        animation.isRemovedOnCompletion = false;
        animation.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseIn);
        toVC.view.layer.add(animation, forKey: "movein")
        
    }
    
    
}
