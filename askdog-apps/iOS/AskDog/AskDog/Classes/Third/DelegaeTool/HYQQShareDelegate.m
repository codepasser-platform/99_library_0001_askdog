//
//  HYQQShareDelegate.m
//  AskDog
//
//  Created by Hooying_zhaody on 16/9/6.
//  Copyright © 2016年 Hooying. All rights reserved.
//

#import "HYQQShareDelegate.h"

@implementation HYQQShareDelegate

+(HYQQShareDelegate *) sharedManger {
    static HYQQShareDelegate * qqManger = nil;
    
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        qqManger = [[self alloc] init];
    });
    return qqManger;
}

-(void)onReqWithBlock:(QQShareOnReqBlock)block {
    self.onReqBlock = block;
}

-(void)onRespWithBlock:(QQShareOnRespBlock )block {
    self.onRespBlock = block;
}

/**
 处理来至QQ的请求
 */
- (void)onReq:(QQBaseReq *)req {
    self.onReqBlock(req);

}

/**
 处理来至QQ的响应
 */
- (void)onResp:(QQBaseResp *)resp {
    self.onRespBlock(resp);
}

/**
 处理QQ在线状态的回调
 */
- (void)isOnlineResponse:(NSDictionary *)response {
    
}

@end
