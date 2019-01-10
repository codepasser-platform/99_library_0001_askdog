//
//  HYClassificationView.h
//  textMenu
//
//  Created by Hooying_zhaody on 16/9/19.
//  Copyright © 2016年 Hooying_zhaody. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void (^itemClickBlock)(NSInteger i);

@interface HYClassificationView : UIView

@property (copy, nonatomic) itemClickBlock itemClick;   //Item点击事件的回调block

//初始化方法
-(id)initWithFrame:(CGRect)frame andItems:(NSArray *)items
             index:(NSInteger)index
 andItemClickBlock:(itemClickBlock)click;

@end
