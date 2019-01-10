//
//  HYWBShareDelegate.h
//  AskDog
//
//  Created by Hooying_zhaody on 16/9/6.
//  Copyright © 2016年 Hooying. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AskDog-Bridging-Header.h"

typedef void (^QQShareOnReqBlock)(QQBaseReq *req);
typedef void (^QQShareOnRespBlock)(QQBaseResp *resp);

@interface HYQQShareDelegate : NSObject <QQApiInterfaceDelegate>
@property (nonatomic, copy) QQShareOnReqBlock onReqBlock;
@property (nonatomic, copy) QQShareOnRespBlock onRespBlock;


+(HYQQShareDelegate *) sharedManger;
-(void)onReqWithBlock:(QQShareOnReqBlock)block;
-(void)onRespWithBlock:(QQShareOnRespBlock)block;
@end
